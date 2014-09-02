package com.myer.mypos.sohportlet.model;

import org.json.simple.JSONAware;
import org.json.simple.JSONObject;

public class Item implements JSONAware {
	
	static int nextID = 17;

    private int id;
    private String item;
    private String itemDescription;
    private String size;
    private String style;
    private String supplyStyle;
   
    public Item(    		 
    		String itemDescription, 
    		String size,
    		String style,
    		String supplyStyles,
    		String item)
    {
        id = nextID++;
        this.item = item; 
		this.itemDescription = itemDescription; 
		this.size = size;
		this.style = style;
		this.supplyStyle = supplyStyle;
    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getItem() {
		return item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public String getItemDescription() {
		return itemDescription;
	}

	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getStyle() {
		return style;
	}

	public void setStyle(String style) {
		this.style = style;
	}

	public String getSupplyStyle() {
		return supplyStyle;
	}

	public void setSupplyStyle(String supplyStyle) {
		this.supplyStyle = supplyStyle;
	}
	
	public String toJSONString() {
		JSONObject obj = new JSONObject();
		obj.put("itemDescription",itemDescription);
		obj.put("size", size);
		obj.put("style",style);
		obj.put("supplyStyle",supplyStyle);
		obj.put("item",item);
		return obj.toString();
	}
	
	public String toString() {
		StringBuffer company = new StringBuffer();
		company.append("Item [");
		company.append(" id: " + id);
		company.append(" item: " + item);
		company.append(" itemDescription: " + itemDescription);
		company.append(" size: " + size);
		company.append(" style: " + style);
		company.append(" supplyStyle: " + supplyStyle);		
		return company.toString();
	}	
}
