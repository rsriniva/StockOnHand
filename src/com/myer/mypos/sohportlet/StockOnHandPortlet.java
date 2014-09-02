package com.myer.mypos.sohportlet;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.GenericPortlet;
import javax.portlet.PortletException;
import javax.portlet.PortletRequest;
import javax.portlet.PortletRequestDispatcher;
import javax.portlet.PortletSession;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.apache.axis.AxisFault;

import com.myer.item.model.Item;
import com.myer.item.model.StockOnHand;
import com.myer.item.service.ItemSearch;
import com.myer.item.service.ItemSearchServiceLocator;
import com.myer.mypos.sohportlet.criteria.SearchCriteria;
import com.myer.mypos.sohportlet.exception.StockOnHandPortletError;

public class StockOnHandPortlet extends GenericPortlet {
	public static final String PROPS_FILE = "settings.properties";
	public static final int ITEM_SEARCH_MAX_ROWS = 50;
	public static final int ITEM_PARENT_SEARCH_MAX_ROWS = 200;
	
	public static final String JSP_FOLDER   = "/_StockOnHandPortlet/jsp/html/";  // JSP folder name
	public static final String VIEW_JSP     = "StockOnHandPortletView";          // JSP file name to be rendered on the view mode
	public static final String SESSION_BEAN = "StockOnHandPortletSessionBean";   // Bean name for the portlet session

	private ItemSearch _itemSearch;
	
	public void init() throws PortletException {
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
			throw new PortletException(e);
		}
	}
	
	public void doView(RenderRequest request, RenderResponse response) throws PortletException, IOException {
		response.setContentType(request.getResponseContentType());
		StockOnHandPortletSessionBean sessionBean = getSessionBean(request);
		String itemNumber = request.getParameter("itemNumber");
		
		// if an itemNumber is passed, then we are trying to display a specific
		// item, otherwise we just want to view the search screen
		if (itemNumber != null) {
			Item selectedItem = null;
			Item[] items = new Item[] {};
			StockOnHand[] stockOnHand = new StockOnHand[] {};
			StockOnHandPortletError error = null;
			try {
				items = _itemSearch.fieldSearch(itemNumber, null, -1, null, -1, -1, null, null, null, -1, "A", ITEM_SEARCH_MAX_ROWS);
				if (items == null || items.length == 0)
					error = new StockOnHandPortletError("Unable to find product information for "+itemNumber,StockOnHandPortletError.SEVERE);
				else {
					selectedItem = items[0];
				    
					// Start: AP-05953533, Date: 2010-08-06, Author: Richard Riviere
					// Description: Place limit of related items search
					// Old Code: items = _itemSearch.getChildren(selectedItem.getParentNumber(), "A");
					
					items = _itemSearch.getChildren(selectedItem.getParentNumber(),"A",ITEM_PARENT_SEARCH_MAX_ROWS);
					if (items != null && items.length == ITEM_PARENT_SEARCH_MAX_ROWS)
						error = new StockOnHandPortletError("Displaying first "+ITEM_PARENT_SEARCH_MAX_ROWS+" related products.", StockOnHandPortletError.WARNING);
					// End: AP-05953533
					
					stockOnHand = _itemSearch.getStockOnHand(itemNumber, -1, null);
				}
			}
			catch(AxisFault e) {
				e.printStackTrace();
				error = new StockOnHandPortletError("Search service unavailable, please contact IT support or try again later",
														StockOnHandPortletError.SEVERE);
			}
			sessionBean.setError(error);
			sessionBean.setItemListHeading("Related products");
			sessionBean.setItems(items);
			sessionBean.setStockOnHand(stockOnHand);
			sessionBean.getSearchCriteria().populate(selectedItem);
		}
		else {
			// no item number, so let's just display the empty search screen
		}
		
		PortletRequestDispatcher rd = getPortletContext().getRequestDispatcher(getJspFilePath(request, VIEW_JSP));
		rd.include(request, response);
	}

	public void processAction(ActionRequest request, ActionResponse response) throws PortletException, IOException {
		StockOnHandPortletSessionBean sessionBean = getSessionBean(request);
		// doing a search only ever populates the product list
		SearchCriteria criteria = sessionBean.getSearchCriteria();
		StockOnHandPortletError error = null;
		
		String errorMessage =  criteria.populate(request);
		if (errorMessage != null) 
			error = new StockOnHandPortletError(errorMessage,StockOnHandPortletError.SEVERE);
		
		Item[] items = new Item[] {};
		try {
			if (error == null) {
				// if there are keywords, then use the keyword search, otherwise use the field search
				if (criteria.getKeywords().length() != 0) 
					items = _itemSearch.keywordSearch(criteria.getParsedKeywords(), "A", ITEM_SEARCH_MAX_ROWS);				
				else 
					items = _itemSearch.fieldSearch(criteria.getItemNumber(), null, asInt(criteria.getClassGroup()), criteria.getAPN(), asInt(criteria.getClassNumber()), asInt(criteria.getSubclassNumber()), criteria.getItemDescription(), criteria.getBrandName(), criteria.getSupplierName(), asInt(criteria.getSupplierNumber()), "A", ITEM_SEARCH_MAX_ROWS);
				
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
		}		
				
		sessionBean.setError(error);
		sessionBean.setItemListHeading("Search Results");
		sessionBean.setItems(items);
		sessionBean.setStockOnHand(new StockOnHand[] {});
	    // Start: myer00062951 , Date: 2010-05-21, Author: Richard Riviere
		// Description: Leave search criteria as is to allow values to be returned to user
		// Old Code: sessionBean.getSearchCriteria().populate((Item)null);
		// End: myer00062951 
	}
	
	private static StockOnHandPortletSessionBean getSessionBean(PortletRequest request) {
		PortletSession session = request.getPortletSession();
		System.err.println("Portlet session id: " + session.getId());
		StockOnHandPortletSessionBean sessionBean = (StockOnHandPortletSessionBean) session.getAttribute(SESSION_BEAN);
		if (sessionBean == null) {
			sessionBean = new StockOnHandPortletSessionBean();
			session.setAttribute(SESSION_BEAN, sessionBean);
		}
		return sessionBean;
	}

	private static String getJspFilePath(RenderRequest request, String jspFile) {
		return JSP_FOLDER + jspFile + ".jsp";
	}

	private int asInt(Integer i) {
		return (i == null ? -1 : i.intValue());
	}
}
