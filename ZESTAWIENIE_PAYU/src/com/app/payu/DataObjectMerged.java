package com.app.payu;

import java.util.ArrayList;
import java.util.Date;

public class DataObjectMerged {
	
	// zestaw danych obiektu
	public String PaymentID;
	public ArrayList<String> Transactions;
	public String UserID;
	public Double TotalCash;
	public Date PaymentDate;
	public Long PaymentDateUnixTime;
	public String OrderId;
	public String InvoiceId;
	public Integer MatchLevel;
	public String Warnings;
	
	// domyœlny konstruktor
	public DataObjectMerged()
	{
		PaymentID = "-1";
		Transactions = new ArrayList<String>();
		UserID = "empty";
		PaymentDate = new Date(0);
		PaymentDateUnixTime = (long) 0;
		OrderId = "-1";
		InvoiceId = "Brak FV";
		Warnings = "";
		MatchLevel = -1;
	}
	
	// podstawowy konstruktor z parametrami
	public DataObjectMerged(String pid, ArrayList<String> trs, String uid, Double tcash, Date date, Long unixdate, String oid, String iid)
	{
		PaymentID = pid;									
		UserID = uid;										
		Transactions = trs;
		TotalCash = tcash;
		PaymentDate = date;
		PaymentDateUnixTime = unixdate;
		OrderId = oid;
		InvoiceId = iid;
		Warnings = "";
	}
	
	public DataObjectMerged(DataObjectBaselinker ob, DataObjectPayU op)
	{
		PaymentID = op.PaymentID;
		UserID = op.UserID;
		Transactions = op.Transactions;
		TotalCash = op.TotalCash;
		PaymentDate = op.PaymentDate;
		PaymentDateUnixTime = op.PaymentDateUnixTime;
		OrderId = ob.OrderID;
		InvoiceId = "Brak FV";
		Warnings = "";
		
		Long diff = ob.OrderDateUnixTime-op.PaymentDateUnixTime;
		if(diff<0) {
			diff = -diff;
		}
		
		if(diff>604800)
		{
			AddWarning("Du¿e opóznienie wp³aty");
		}
		
		if(ob.Confirmed==false)
		{
			AddWarning("Zamówienie niepotwierdzone");
		}
	}
	
	public DataObjectMerged(DataObjectPayU op)
	{
		PaymentID = op.PaymentID;
		UserID = op.UserID;
		Transactions = op.Transactions;
		TotalCash = op.TotalCash;
		PaymentDate = op.PaymentDate;
		PaymentDateUnixTime = op.PaymentDateUnixTime;
		OrderId = "-";
		InvoiceId = "Brak FV";
		MatchLevel = 0;
		Warnings = "";
		AddWarning("Brak dopasowania");
	}
	
	public void SetInvoice(String inv)
	{
		InvoiceId = inv;
	}
	
	public void SetMatchLevel(Integer ml)
	{
		MatchLevel = ml;
	}
	
	public void AddWarning(String warn)
	{
		Warnings+=warn+"; ";
	}
	
	// wyœwietlamy obiekt na konsolê
	public void Show()
	{
		System.out.println("Transakcja numer - " + PaymentID);
        System.out.println("---------------");
        System.out.println("Login kupuj¹cego : " + UserID);
        System.out.println("Dopasowane zamówienie : " + OrderId);
        System.out.println("Dopasowana faktura : " + InvoiceId);
        System.out.println("Kwota wp³aty : " + TotalCash);
        System.out.print("Transakcje : ");
        for(int i = 0; i<Transactions.size(); i++)
        {
        	System.out.print(Transactions.get(i)+", ");
        }
        System.out.print("\n");
        
        System.out.println("Data wp³aty : " + PaymentDate.toString());
        System.out.println("---------------\n\n");
	}
}