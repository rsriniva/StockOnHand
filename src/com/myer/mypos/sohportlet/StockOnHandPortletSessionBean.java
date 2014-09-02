package com.myer.mypos.sohportlet;

import com.myer.item.model.Item;
import com.myer.item.model.StockOnHand;
import com.myer.mypos.sohportlet.criteria.SearchCriteria;
import com.myer.mypos.sohportlet.exception.StockOnHandPortletError;

public class StockOnHandPortletSessionBean extends StockOnHandPortlet {
	private SearchCriteria _searchCriteria = new SearchCriteria();
	private Item[] _items = new Item[] {};
	private StockOnHand[] _stockOnHand = new StockOnHand[] {};
	private StockOnHandPortletError _error = null;
	private String _itemListHeading = "";

	public SearchCriteria getSearchCriteria() {
		return _searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		_searchCriteria = searchCriteria;
	}

	public Item[] getItems() {
		return _items;
	}

	public void setItems(Item[] items) {
		if (items == null)
			items = new Item[] {};
		_items = items;
	}

	public StockOnHand[] getStockOnHand() {
		return _stockOnHand;
	}

	public void setStockOnHand(StockOnHand[] stockOnHand) {
		_stockOnHand = stockOnHand;
	}
	
	public StockOnHandPortletError getError() {
		return _error;
	}
	
	public void setError(StockOnHandPortletError error) {
		_error = error;
	}
	
	public String getItemListHeading() {
		return _itemListHeading;
	}
	
	public void setItemListHeading(String itemListHeading) {
		_itemListHeading = itemListHeading;
	}
}
