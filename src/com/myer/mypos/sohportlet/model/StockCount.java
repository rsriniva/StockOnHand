package com.myer.mypos.sohportlet.model;

public class StockCount {
	private String _location;
	private String _locationName;
	private String _region;
	private int _stockOnHand;
	
	public StockCount(String location, String locationName, String region, int stockOnHand) {
		_location = location;
		_locationName = locationName;
		_region = region;
		_stockOnHand = stockOnHand;
	}
	public String getLocation() {
		return _location;
	}
	public void setLocation(String location) {
		_location = location;
	}
	public String getLocationName() {
		return _locationName;
	}
	public void setLocationName(String locationName) {
		_locationName = locationName;
	}
	public String getRegion() {
		return _region;
	}
	public void setRegion(String region) {
		_region = region;
	}
	public int getStockOnHand() {
		return _stockOnHand;
	}
	public void setStockOnHand(int stockOnHand) {
		_stockOnHand = stockOnHand;
	}
}
