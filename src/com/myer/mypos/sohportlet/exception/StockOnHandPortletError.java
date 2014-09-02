package com.myer.mypos.sohportlet.exception;

/*
 * Start: AP-05953533, Date: 2010-10-15, Author: Richard Riviere
 * Description: Created class to wrap all StockOnHandPortlet errors returned to the UI.
 * End: AP-05953533
 */
public class StockOnHandPortletError {
	public static final int SEVERE = 1;
	public static final int WARNING = 2;
	
	private int warningLevel = -1;
	private String errorMessage = "";
	
	public StockOnHandPortletError(String errorMessage,int warningLevel) {
		this.errorMessage = errorMessage;
		this.warningLevel = warningLevel;
	}

	public int getWarningLevel() {
		return warningLevel;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
}
