package com.myer.mypos.sohportlet.criteria;

import java.util.StringTokenizer;
import java.util.Vector;

import javax.portlet.PortletRequest;
import javax.servlet.http.HttpServletRequest;

import com.myer.item.model.Item;

public class SearchCriteria {
	private String _keywords = "";
	private String _itemNumber = "";
	private Integer _classGroup = null;
	private String _classGroupDescription = "";
	private String _apn = "";
	private Integer _classNumber = null;
	private Integer  _subclassNumber = null;
	private String _itemDescription = "";
	private String _brandName = "";
	private String _supplierName = "";
	private Integer _supplierNumber = null;
	
	public String getKeywords() {
		return _keywords;
	}
	public void setKeywords(String keywords) {
		_keywords = keywords;
	}
	public String[] getParsedKeywords() {
		Vector v = new Vector();
		StringTokenizer st = new StringTokenizer(getKeywords());
		while (st.hasMoreTokens())
			v.add(st.nextToken());
		String[] array = new String[v.size()];
		for (int i = 0; i < v.size(); i++)
			array[i] = (String)v.get(i);
		return array;
	}
	public String getAPN() {
		return _apn;
	}
	public void setAPN(String apn) {
		_apn = apn;
	}
	public String getBrandName() {
		return _brandName;
	}
	public void setBrandName(String brandName) {
		_brandName = brandName;
	}
	public Integer getClassGroup() {
		return _classGroup;
	}
	public String getClassGroupAsString() {
		return coalesce(_classGroup);
	}
	public void setClassGroup(Integer classGroup) {
		_classGroup = classGroup;
	}
	public String getClassGroupDescription() {
		return _classGroupDescription;
	}
	public void setClassGroupDescription(String classGroupDescription) {
		_classGroupDescription = classGroupDescription;
	}
	public Integer getClassNumber() {
		return _classNumber;
	}
	public String getClassNumberAsString() {
		return coalesce(_classNumber);
	}
	public void setClassNumber(Integer classNumber) {
		_classNumber = classNumber;
	}
	public String getItemDescription() {
		return _itemDescription;
	}
	public void setItemDescription(String itemDescription) {
		_itemDescription = itemDescription;
	}
	public String getItemNumber() {
		return _itemNumber;
	}
	public void setItemNumber(String itemNumber) {
		_itemNumber = itemNumber;
	}
	public Integer getSubclassNumber() {
		return _subclassNumber;
	}
	public String getSubclassNumberAsString() {
		return coalesce(_subclassNumber);
	}
	public void setSubclassNumber(Integer subclassNumber) {
		_subclassNumber = subclassNumber;
	}
	public String getSupplierName() {
		return _supplierName;
	}
	public void setSupplierName(String supplierName) {
		_supplierName = supplierName;
	}
	public Integer getSupplierNumber() {
		return _supplierNumber;
	}
	public String getSupplierNumberAsString() {
		return coalesce(_supplierNumber);
	}
	public void setSupplierNumber(Integer supplierNumber) {
		_supplierNumber = supplierNumber;
	}
	
	public String populate(PortletRequest request) {
		HttpServletRequest httpServletRequest 
			= (HttpServletRequest) request.getAttribute("javax.servlet.request");
		return populate(httpServletRequest);
	}
	
	public String populate(HttpServletRequest request) {
		try {
			setKeywords(coalesce(request.getParameter("keywords")));
			setItemNumber(coalesce(request.getParameter("itemNumber")));
			setClassGroup(asInt(request.getParameter("classGroup"), "Class Group"));
			setClassGroupDescription(coalesce(request.getParameter("classGroupDescription")));
			setAPN(coalesce(request.getParameter("apn")));
			setClassNumber(asInt(request.getParameter("classNumber"), "Class"));
			setSubclassNumber(asInt(request.getParameter("subclassNumber"), "Subclass"));
			setItemDescription(coalesce(request.getParameter("itemDescription")));
			setBrandName(coalesce(request.getParameter("brandName")));
			setSupplierName(coalesce(request.getParameter("supplierName")));
			setSupplierNumber(asInt(request.getParameter("supplierNumber"), "Supplier Number"));
		}
		catch(IllegalArgumentException e) {
			return e.getMessage();
		}

		String[] parsedKeywords = getParsedKeywords();
		if (parsedKeywords.length != 0) {
			boolean atLeastThreeOK = false;
			for (int i = 0; i < parsedKeywords.length; i++) {
				if (parsedKeywords[i].length() >= 3) {
					atLeastThreeOK = true;
					break;
				}
			}
			if (!atLeastThreeOK)
				return "Search criteria must have at least three characters";
		}

		boolean fieldSearch = 
			!"".equals(_itemNumber) ||
			_classGroup != null ||
			!"".equals(_apn) ||
			_classNumber != null ||
			_subclassNumber != null ||
			!"".equals(_itemDescription) ||
			!"".equals(_brandName) ||
			!"".equals(_supplierName) ||
			_supplierNumber != null;
			
		if (fieldSearch && parsedKeywords.length != 0)
			return "Please enter search criteria in either the keyword field OR other fields";
		
		if (!fieldSearch && parsedKeywords.length == 0)
			return "Please enter some search criteria";

		return null;
	}
	
	public void populate(Item item) {
		setKeywords("");
		if (item != null) {
			setItemNumber(coalesce(item.getItemNumber()));
			setClassGroup(item.getClassGroup());
			setClassGroupDescription(item.getClassGroupDescription());
			setAPN(coalesce(item.getAPN()));
			setClassNumber(item.getClassNumber());
			setSubclassNumber(item.getSubclassNumber());
			setItemDescription(coalesce(item.getItemDescription()));
			setBrandName(coalesce(item.getBrandName()));
			setSupplierName(coalesce(item.getSupplierName()));
			setSupplierNumber(item.getSupplierNumber());
		}
		else {
			setItemNumber("");
			setClassGroup(null);
			setClassGroupDescription("");
			setAPN("");
			setClassNumber(null);
			setSubclassNumber(null);
			setItemDescription("");
			setBrandName("");
			setSupplierName("");
			setSupplierNumber(null);
		}
	}
	
	private Integer asInt(String s, String fieldName) {
		try {
			return (s == null || "".equals(s)) ? null : Integer.valueOf(s);
		}
		catch(NumberFormatException e) {
			throw new IllegalArgumentException("Alphabetic input is not appropriate for "+fieldName);
		}
	}
	private String coalesce(String s) {
		return (s == null ? "" : s.trim());
	}
	private String coalesce(Integer i) {
		return (i == null ? "" : ""+i);
	}
}
