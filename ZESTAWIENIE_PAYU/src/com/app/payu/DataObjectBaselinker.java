package com.app.payu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.json.baselinker.orders.Order;
import com.json.baselinker.orders.Product;

public class DataObjectBaselinker {

	// zestaw danych obiektu
	public String OrderID;
	public ArrayList<String> Transactions;
	public String UserID;
	public Double TotalCash;
	public Date OrderDate;
	public Long OrderDateUnixTime;
	public boolean Confirmed;
	
	// domy�lny konstruktor
	public DataObjectBaselinker()
	{
		OrderID = "-1";
		Transactions = new ArrayList<String>();
		UserID = "empty";
		OrderDate = new Date(0);
		OrderDateUnixTime = (long) 0;
		Confirmed = false;
	}
	
	// podstawowy konstruktor z parametrami
	public DataObjectBaselinker(String pid, String trs, String uid, String tcash, String date, boolean conf)
	{
		OrderID = pid;									// zapisz ID p�atno�ci
		UserID = uid;										// zapisz ID u�ytkownika
		Confirmed = conf;
		
		tcash = tcash.replace(',', '.');					// zamieniamy separator
		tcash = tcash.replaceAll("\\s+","");				// usuwamy bia�e znaki
		tcash = tcash.substring(0, tcash.length()-2);		// usuwamy znacznik waluty
		
		TotalCash = Double.parseDouble(tcash);				// zapisz kwot� wp�aty payu
		
		String[] tmp = trs.split(";");						// wydzielamy pojedyncze transakcje
		Transactions = new ArrayList<String>();
		
		for (int i = 0; i<tmp.length; i++)					// zapisujemy je do tablicy
		{
			Transactions.add(tmp[i]);
		}
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");	// format daty z csv payu
		try {
			OrderDate = dateFormat.parse(date);								// zapis daty wraz z unix time
			OrderDateUnixTime = (long) OrderDate.getTime()/1000;
		} catch (ParseException e) {
			System.out.println("(B��d) Nie uda�o si� przetworzy� formatu daty zam�wienia: "+pid);
			OrderDate = new Date(0);
			OrderDateUnixTime = (long) 0;
		}
	}
	
	// konstruktor tworzy uproszczony obiekt zam�wienia z BL na podstawie pe�nego POJO z JSON-a
	public DataObjectBaselinker(Order o)
	{
		OrderID = o.getOrderId();
		UserID = o.getUserLogin();
		OrderDateUnixTime = Long.parseLong(o.getDateConfirmed());
		TotalCash = o.getPaymentDone();
		OrderDate = new Date(OrderDateUnixTime*1000L);
		Confirmed = o.getOrderConfirmed();
		
		Transactions = new ArrayList<String>();
		List<Product> trs = o.getProducts();
		
		for(int i=0; i<trs.size(); i++)
		{
			try
			{
				Transactions.add(trs.get(i).getAuctionId());
			}
			catch(Exception e)
			{
				// b��d - brak transakcji na zam�wieniu (inne zr�d�o?)
				continue;
			}
		}
	}
	
	// wy�wietlamy obiekt na konsol�
	public void Show()
	{
		System.out.println("Zam�wienie numer - " + OrderID);
        System.out.println("---------------");
        System.out.println("Login kupuj�cego : " + UserID);
        System.out.println("Kwota wp�aty : " + TotalCash);
        System.out.print("Transakcje : ");
        for(int i = 0; i<Transactions.size(); i++)
        {
        	System.out.print(Transactions.get(i)+", ");
        }
        System.out.print("\n");
        
        System.out.println("Data wp�aty : " + OrderDate.toString());
        System.out.println("---------------\n\n");
	}
}
