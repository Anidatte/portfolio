package com.app.payu;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DataObjectPayU {
	
	// zestaw danych obiektu
	public String PaymentID;
	public ArrayList<String> Transactions;
	public String UserID;
	public Double TotalCash;
	public Date PaymentDate;
	public Long PaymentDateUnixTime;
	
	// domy�lny konstruktor
	public DataObjectPayU()
	{
		PaymentID = "-1";
		Transactions = new ArrayList<String>();
		UserID = "empty";
		PaymentDate = new Date(0);
		PaymentDateUnixTime = (long) 0;
	}
	
	// podstawowy konstruktor z parametrami
	public DataObjectPayU(String pid, String trs, String uid, String tcash, String date)
	{
		PaymentID = pid;									// zapisz ID p�atno�ci
		UserID = uid;										// zapisz ID u�ytkownika
		
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
			PaymentDate = dateFormat.parse(date);								// zapis daty wraz z unix time
			PaymentDateUnixTime = (long) PaymentDate.getTime()/1000;
		} catch (ParseException e) {
			System.out.println("(B��d) Nie uda�o si� przetworzy� formatu daty dla transakcji PayU: "+pid);
			PaymentDate = new Date(0);
			PaymentDateUnixTime = (long) 0;
		}
	}
	
	// wy�wietlamy obiekt na konsol�
	public void Show()
	{
		System.out.println("Transakcja numer - " + PaymentID);
        System.out.println("---------------");
        System.out.println("Login kupuj�cego : " + UserID);
        System.out.println("Kwota wp�aty : " + TotalCash);
        System.out.print("Transakcje : ");
        for(int i = 0; i<Transactions.size(); i++)
        {
        	System.out.print(Transactions.get(i)+", ");
        }
        System.out.print("\n");
        
        System.out.println("Data wp�aty : " + PaymentDate.toString());
        System.out.println("---------------\n\n");
	}
}