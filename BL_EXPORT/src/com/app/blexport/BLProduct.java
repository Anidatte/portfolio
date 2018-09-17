package com.app.blexport;

public class BLProduct {
	String Id;
	String Name;
	String Price;
	String Quantity;
	String SKU;
	String EAN;
	String CsvStr;
	
	public BLProduct() {
		Id = "";
		Name = "";
		Price = "";
		Quantity = "";
		SKU = "";
		EAN = "";
		CsvStr = "";
	}
	
	public BLProduct(String i, String n, String p, String q, String s, String e) {
		Id = i;
		Name = n;
		Price = p;
		Quantity = q;
		SKU = s;
		EAN = e;
		CsvStr = "";
	}
	
	public void generateCsvStr() {
		if(!(Price.equals("0.0"))) {
			CsvStr = Id+";"+SKU+";"+Price+";"+Quantity;
		} else {
			CsvStr = Id+";"+SKU+";;"+Quantity;
		}
		
	}
}
