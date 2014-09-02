package com.myer.mypos.sohportlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.axis.AxisFault;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.myer.item.model.Item;
import com.myer.item.model.StockOnHand;
import com.myer.item.service.ItemSearch;
import com.myer.item.service.ItemSearchServiceLocator;
import com.myer.mypos.sohportlet.criteria.SearchCriteria;
import com.myer.mypos.sohportlet.dao.DataRepository;
import com.myer.mypos.sohportlet.exception.StockOnHandPortletError;
import com.myer.mypos.sohportlet.model.JQueryDataTableParamModel;
import com.myer.mypos.sohportlet.util.DataTablesParamUtility;

/**
 * Provides data to the JQuery DataTables in json format
 * 
 * @author      Richard Riviere
 */
public class SohAjaxServlet extends HttpServlet {

	private static final long serialVersionUID = 6753408549592906395L;
	public static final String SESSION_BEAN = "StockOnHandPortletSessionBean";   // Bean name for the portlet session
	public static final int ITEM_SEARCH_MAX_ROWS = 50;
	
	// item search service
	private ItemSearch _itemSearch;			


	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init()
	 */
	public void init() throws ServletException {	
		super.init();
		try {
			// read the settings.properties file to get the URL of the item search web service
			InputStream propsStream = getClass().getClassLoader().getResource("settings.properties").openStream();
			Properties props = new Properties();
			props.load(propsStream);
			propsStream.close();

			ItemSearchServiceLocator locator = new ItemSearchServiceLocator();
			_itemSearch = locator.getItemSearch(new URL(props.getProperty("itemSearch.location")));
		}
		catch(Exception e) {
			throw new ServletException(e);
		}
	}
	
	private static StockOnHandPortletSessionBean getSessionBean(HttpServletRequest request) {
		HttpSession session = request.getSession();
		StockOnHandPortletSessionBean sessionBean = (StockOnHandPortletSessionBean) session.getAttribute(SESSION_BEAN);
		if (sessionBean == null) {
			sessionBean = new StockOnHandPortletSessionBean();
			session.setAttribute(SESSION_BEAN, sessionBean);
		}
		return sessionBean;
	}
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doPost(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		doGet(request,response);
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void doGet(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException, IOException {
		
		JQueryDataTableParamModel param = DataTablesParamUtility.getParam(request);
		System.err.println(param);
		
		String sEcho = param.sEcho;
		int iTotalRecords; // total number of records (unfiltered)
		int iTotalDisplayRecords; //value will be set when code filters companies by keyword

		iTotalRecords = DataRepository.GetItems().size();
		System.err.println("iTotalRecords: " + iTotalRecords);
		
		List items = DataRepository.GetItems();
		System.err.println("Items: " + items);
		
		List itemsList = doKeywordSearch(request,response);
		if (itemsList != null){
			System.err.println("ItemsList size: " + items.size());
		}else{
			System.err.println("ItemsList is null");
		}
			
		
		/*for(Iterator i = companies.iterator(); i.hasNext();){
			Company c = (Company)i.next();
			if(	c.getName().toLowerCase().startsWith(param.sSearch.toLowerCase())
				||
				c.getAddress().toLowerCase().startsWith(param.sSearch.toLowerCase())
				||
				c.getTown().toLowerCase().startsWith(param.sSearch.toLowerCase()))
			{
				companies.add(c); // add company that matches given search criterion
			}
		}*/
		iTotalDisplayRecords = items.size();// number of companies that match search criterion should be returned
		System.err.println("iTotalDisplayRecords: " + iTotalDisplayRecords);
		
		
		final int sortColumnIndex = param.iSortColumnIndex;
		final int sortDirection = param.sSortDirection.equals("asc") ? -1 : 1;
		System.err.println("sortColumnIndex: " + sortColumnIndex);
		System.err.println("sortDirection: " + sortDirection);
		
		/*Collections.sort(items, new Comparator(){		
			public int compare(Object o1, Object o2) {
				Item c1 = (Item) o1;
				Item c2 = (Item) o2;
				switch(sortColumnIndex){
				case 0:
					return c1.getItemDescription().compareTo(c2.getItemDescription()) * sortDirection;
				case 1:
					return c1.getSize().compareTo(c2.getSize()) * sortDirection;
				case 2:
					return c1.getColour().compareTo(c2.getColour()) * sortDirection;
				case 3:
					return c1.getSupplyStyle().compareTo(c2.getSupplyStyle()) * sortDirection;
				case 4:
					return c1.getItemNumber().compareTo(c2.getItemNumber()) * sortDirection;					
				}
				return 0;
			}
		});*/
		
		if(items.size()< param.iDisplayStart + param.iDisplayLength) {
			items = items.subList(param.iDisplayStart, items.size());
		} else {
			items = items.subList(param.iDisplayStart, param.iDisplayStart + param.iDisplayLength);
		}
		System.err.println("companies subList: " + items.size());		
		JSONObject itemsInJsonRepresentation = 
			getItemsInJsonRepresentation(sEcho, iTotalRecords, iTotalDisplayRecords, items);
		
		try {				
			response.setContentType("application/Json");
			response.getWriter().print(itemsInJsonRepresentation);
			
		} catch (IOException e) {
			response.setContentType("text/html");
			response.getWriter().print(e.getMessage());
		}		
	}

	
	public List doKeywordSearch(
			HttpServletRequest request, 
			HttpServletResponse response) throws ServletException {
		
		StockOnHandPortletSessionBean sessionBean = getSessionBean(request);
		SearchCriteria criteria = sessionBean.getSearchCriteria();
		StockOnHandPortletError error = null;
		
		String errorMessage =  criteria.populate(request);
		if (errorMessage != null) 
			error = new StockOnHandPortletError(errorMessage,StockOnHandPortletError.SEVERE);
		
		Item[] items = new Item[] {};
		try {
			if (error == null) {
				// if there are keywords, then use the keyword search, otherwise use the field search
				 
				items = _itemSearch.keywordSearch(new String[]{"Red"}, "A", ITEM_SEARCH_MAX_ROWS);								
				if (items == null || items.length == 0)
					error = new StockOnHandPortletError("Items not found, please enter additional search criteria",StockOnHandPortletError.SEVERE);
				
				// bold assumption that if we return exactly MAX_ROWS records that
				// there are still more to come.
				if (items != null && items.length == ITEM_SEARCH_MAX_ROWS)
					error = new StockOnHandPortletError("Your search criteria matched more than "+ITEM_SEARCH_MAX_ROWS+" items. Please refine your search criteria",StockOnHandPortletError.SEVERE);
			}
		}
		catch(AxisFault e) {
			e.printStackTrace();
			error = new StockOnHandPortletError("Search service unavailable, please contact IT support or try again later",StockOnHandPortletError.SEVERE);
		}catch(RemoteException e) {
			e.printStackTrace();
			error = new StockOnHandPortletError("Search service unavailable, please contact IT support or try again later",StockOnHandPortletError.SEVERE);
		}		
				
		sessionBean.setError(error);
		sessionBean.setItemListHeading("Search Results");
		sessionBean.setItems(items);
		sessionBean.setStockOnHand(new StockOnHand[] {});
	    // Start: myer00062951 , Date: 2010-05-21, Author: Richard Riviere
		// Description: Leave search criteria as is to allow values to be returned to user
		// Old Code: sessionBean.getSearchCriteria().populate((Item)null);
		// End: myer00062951 
		return Arrays.asList(items);
	}	

	private JSONObject getItemsInJsonRepresentation (
			String sEcho, 
			int iTotalRecords,
			int iTotalDisplayRecords,
			List items ) {
		
		JSONObject obj = null;
		obj = new JSONObject();
		obj.put("sEcho", sEcho);
		obj.put("iTotalRecords", new Integer(iTotalRecords));
		obj.put("iTotalDisplayRecords", new Integer(iTotalDisplayRecords));
		    
		JSONArray data = new JSONArray();
		data.addAll(items);
		obj.put("aaData", data);
		return obj;
	}	
	
	
	private int asInt(Integer i) {
		return (i == null ? -1 : i.intValue());
	}	

}