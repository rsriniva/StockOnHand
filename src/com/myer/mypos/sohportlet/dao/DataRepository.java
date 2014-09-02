package com.myer.mypos.sohportlet.dao;

import java.util.LinkedList;
import java.util.List;

import com.myer.mypos.sohportlet.model.Item;

public class DataRepository {
	
	/// <summary>
    /// Singleton collection of companies
    /// </summary>
    private static List ItemData = null;

    /// <summary>
    /// Method that returns all companies used in this example
    /// </summary>
    /// <returns>List of companies</returns>
    public static List GetItems()
    {
        if (ItemData == null)
        {
            ItemData = new LinkedList();
            ItemData.add(new Item("ADIDAS DURAMO MID RED ADP6050:RED:MEDIUM","MEDIUM","RED","ADP6050","155912140"));
            ItemData.add(new Item("ADIDAS NEWBG BLK RED ADH2793:RED:XLARGE","XLARGE","RED","ADH2793","155916100"));
            ItemData.add(new Item("BDS-RED RIDING HOOD COMBO PACK (INCL. DVD DIGITAL BD)","","","1000226032","968161880"));
            ItemData.add(new Item("DR DJANGO RED RACER NO. 8","","","MJ4RAC8R","183307780"));
            ItemData.add(new Item("DR DJANGO RED TRAIN","","","MJ4TRAXO","183308500"));
            ItemData.add(new Item("DVS-RED RIDING HOOD DVD","","","1000225718","968148560"));
            ItemData.add(new Item("MARQUISE APPLIQ TOP W FRILL SLEEVES S13PF505:RED:0","0","RED","","188041150"));
            ItemData.add(new Item("MARQUISE APPLIQ TOP W FRILL SLEEVES S13PF505:RED:00","0","RED","","188041060"));
            ItemData.add(new Item("MARQUISE APPLIQ TOP W FRILL SLEEVES S13PF505:RED:000","0","RED","","188040970"));
            ItemData.add(new Item("MAXUM POWER BLACK RED WATCH X1351G1:RED:MEDIUM","MEDIUM","RED","X1351G1","183075310"));
            ItemData.add(new Item("MAXUM SPARK RED WATCH X1349G1:GREY:MEDIUM","MEDIUM","GREY","X1349G1","183057580"));
            ItemData.add(new Item("MICROSOFT 3500WMM MOUSE FLAME RED","","","3500WMM","115876630"));
            ItemData.add(new Item("MOSCHINO CHEAP AND CHIC A05036126 3 BTN FITTED JACKET:RED:40","40","RED","A05036126","181225270"));
            ItemData.add(new Item("MOSCHINO CHEAP AND CHIC A05036126 3 BTN FITTED JACKET:RED:44","44","RED","A05036126","181225450"));
            ItemData.add(new Item("MOSCHINO CHEAP AND CHIC A05036126 3 BTN FITTED JACKET:RED:46","46","RED","A05036126","181225540"));
            ItemData.add(new Item("MOSCHINO CHEAP AND CHIC A05036126 3 BTN FITTED JACKET:RED:48","48","RED","A05036126","181225630"));
            ItemData.add(new Item("PRETEEN KANE CHRONICLES: THE RED PYRAMID RIORDAN R","","","","894798560"));
            ItemData.add(new Item("R AQUALUX G/WARE CANDLES RED PACK 6","","","5574002","757942580"));
            ItemData.add(new Item("RED BD","","","IFBD665RE","945657830"));
            ItemData.add(new Item("RED COMIC BOOK EDITION DVD","","","IFL665RE","946071290"));
            ItemData.add(new Item("RED HILL BD","","","BD72015","946145270"));
            ItemData.add(new Item("RED HILL DVD","","","D72015","946143020"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:L","L","RED PATENT","105730","922537010"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:M","M","RED PATENT","105730","922536920"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:S","S","RED PATENT","105730","922536830"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:XL","XL","RED PATENT","105730","922537100"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:XS","XS","RED PATENT","105730","922536740"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED PATENT:XXL","XXL","RED PATENT","105730","922537190"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:L","L","RED","105730","917245550"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:M","M","RED","105730","917245460"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:S","S","RED","105730","917245370"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:XL","XL","RED","105730","917245640"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:XS","XS","RED","105730","917245280"));
            ItemData.add(new Item("SCRAFT 105730 ESSENTAIL V-NECK TEE:RED:XXL","XXL","RED","105730","917245730"));
            ItemData.add(new Item("SK IL CREATIVO LARGE RED JOURNAL","LARGE","RED","SK0280004","962970680"));
            ItemData.add(new Item("SK IL CREATIVO MEDIUM RED JOURNAL","MEDIUM","RED","SK0270004","962965820"));
            ItemData.add(new Item("WIGGLES THE: HERE COMES BIG RED CAR + TOP OF THE TOTS (2ON1) HB DVD","","","R-111414-9","940420370"));
            ItemData.add(new Item("XMAS GIFT BOX T13 WATCH RED LARGE:RED:LARGE","LARGE","RED","T13","904761470"));
            ItemData.add(new Item("1/4SLV T-SHIRTS:RED:L","L","RED","U2A790","863671610"));
            ItemData.add(new Item("1/4SLV T-SHIRTS:RED:M","M","RED","U2A790","863671520"));
            ItemData.add(new Item("1/4SLV T-SHIRTS:RED:S","S","RED","U2A790","863671430"));
            ItemData.add(new Item("1/4SLV T-SHIRTS:RED:XL","XL","RED","U2A790","863671700"));
            ItemData.add(new Item("1/4SLV T-SHIRTS:RED:XS","XS","RED","U2A790","863671340"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:10","10","RED","1100827","124386940"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:12","12","RED","1100827","124387030"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:14","14","RED","1100827","124387120"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:16","16","RED","1100827","124387210"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:18","18","RED","1100827","124387300"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:6","6","RED","1100827","124386760"));
            ItemData.add(new Item("1100827 STRETCH SILK SHELL TOP:RED:8","8","RED","1100827","124386850"));
            

        }
        return ItemData;
    }

}
