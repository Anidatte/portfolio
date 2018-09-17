package com.app.payu;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PayUMerger {
	public static ArrayList<DataObjectMerged> Merge(ArrayList<DataObjectPayU> tp, ArrayList<DataObjectBaselinker> tb)
	{
		ArrayList<DataObjectMerged> ret = new ArrayList<DataObjectMerged>();
		int totalPaymentsCount = tp.size();
		
		/*
		 * Pierwszy obieg pêtli próbuje odnalezæ pe³ne dopasowania, tj. z Match Level  = 3
		 * Musz¹ byæ zgodne trzy elementy: login, transakcje, kwota
		 * 
		 * Pozosta³e wp³aty, dla których nie zostanie znalezione pe³ne dopasowanie, zostan¹ skierowane
		 * do kolejnych pêtli, z ni¿szymi match levelami.
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie wp³aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			boolean found = false;
			
			for(int j=0; j<tb.size(); j++)	// szukamy dopasowania w baselinkerze
			{
				DataObjectBaselinker tempBL = tb.get(j);
				
				if(tempPayU.UserID.equals(tempBL.UserID))	// najpierw sprawdzamy login
				{
					boolean checkTransactions = true;		// teraz porównujemy listê transakcji
					
					for(int k=0; k<tempPayU.Transactions.size(); k++)
					{
						if(!checkTransactions)				// brak zgodnoœci - wyjœcie
						{
							break;
						}
						
						checkTransactions = false;
						
						for(int l=0; l<tempBL.Transactions.size(); l++)
						{
							if(tempPayU.Transactions.get(k).equals(tempBL.Transactions.get(l)))	// porównanie
							{
								checkTransactions = true;	// jeœli dopasowanie znalezione, przechodzimy do nastêpnej transakcji
								break;
							}
						}
					}
					
					if(checkTransactions && tempPayU.Transactions.size()==tempBL.Transactions.size())
					{
						if(tempPayU.TotalCash.equals(tempBL.TotalCash))
						{
							DataObjectMerged toAdd = new DataObjectMerged(tempBL, tempPayU);
							toAdd.SetMatchLevel(3);
							ret.add(toAdd);
							System.out.println("Znaleziono dopasowanie (ML=3). Zamówienie "+tempBL.OrderID+", P³atnoœæ "+tempPayU.PaymentID);
							tb.remove(j);
							j--;
							found = true;
							break;
						}
					}
				}
			}
			
			if(found)
			{
				tp.remove(i);
				i--;
			}
		}
		
		System.out.println("(Debug) Pierwszy przebieg, ML=3 | Pozosta³o "+tp.size()+" wp³at (tp) i "+tb.size()+" zamówieñ. (tb)");
		
		/*
		 * Drugi obieg pêtli próbuje odnalezæ czêœciowe dopasowania z Match Level  = 2
		 * Musz¹ byæ zgodne dwa elementy: login, transakcje
		 * 
		 * Pozosta³e wp³aty, dla których nie zostanie znalezione pe³ne dopasowanie, zostan¹ skierowane
		 * do kolejnej pêtli z match level 1
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta³e wp³aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			boolean found = false;
			
			for(int j=0; j<tb.size(); j++)	// szukamy dopasowania w baselinkerze
			{
				DataObjectBaselinker tempBL = tb.get(j);
				
				if(tempPayU.UserID.equals(tempBL.UserID))	// najpierw sprawdzamy login
				{
					boolean checkTransactions = true;		// teraz porównujemy listê transakcji
					
					for(int k=0; k<tempPayU.Transactions.size(); k++)
					{
						if(!checkTransactions)				// brak zgodnoœci - wyjœcie
						{
							break;
						}
						
						checkTransactions = false;
						
						if(tempPayU.Transactions.size()==tempBL.Transactions.size())
						{
							for(int l=0; l<tempBL.Transactions.size(); l++)
							{
								if(tempPayU.Transactions.get(k).equals(tempBL.Transactions.get(l)))	// porównanie
								{
									checkTransactions = true;	// jeœli dopasowanie znalezione, przechodzimy do nastêpnej transakcji
									break;
								}
							}
						}
					}
					
					if(checkTransactions)
					{
						DataObjectMerged toAdd = new DataObjectMerged(tempBL, tempPayU);
						toAdd.SetMatchLevel(2);
						toAdd.AddWarning("Niezgodna kwota wp³aty");
						ret.add(toAdd);
						System.out.println("Znaleziono dopasowanie (ML=2). Zamówienie "+tempBL.OrderID+", P³atnoœæ "+tempPayU.PaymentID);
						tb.remove(j);
						j--;
						found = true;
						break;
					}
				}
			}
			
			if(found)
			{
				tp.remove(i);
				i--;
			}
		}
		
		System.out.println("(Debug) Drugi przebieg, ML=2 | Pozosta³o "+tp.size()+" wp³at (tp) i "+tb.size()+" zamówieñ. (tb)");
		
		/*
		 * Trzeci obieg pêtli próbuje odnalezæ czêœciowe dopasowania z Match Level  = 1
		 * Musi byæ zgodny jeden element: login
		 * 
		 * Pozosta³e wp³aty, dla których nie zostanie znalezione dopasowanie, zostan¹ skierowane
		 * do kolejnej pêtli z match level 0
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta³e wp³aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			boolean found = false;
			
			for(int j=0; j<tb.size(); j++)	// szukamy dopasowania w baselinkerze
			{
				DataObjectBaselinker tempBL = tb.get(j);
				
				if(tempPayU.UserID.equals(tempBL.UserID))	// najpierw sprawdzamy login
				{
					DataObjectMerged toAdd = new DataObjectMerged(tempBL, tempPayU);
					toAdd.SetMatchLevel(1);
					toAdd.AddWarning("Niezgodna lista transakcji");
					ret.add(toAdd);
					System.out.println("Znaleziono dopasowanie (ML=1). Zamówienie "+tempBL.OrderID+", P³atnoœæ "+tempPayU.PaymentID);
					tb.remove(j);
					j--;
					found = true;
					break;
				}
			}
			
			if(found)
			{
				tp.remove(i);
				i--;
			}
		}
		
		System.out.println("(Debug) Trzeci przebieg, ML=1 | Pozosta³o "+tp.size()+" wp³at (tp) i "+tb.size()+" zamówieñ. (tb)");
		
		/*
		 * Czwarty obieg przetwarza wp³aty, dla których nie znaleziono ¿adnego dopasowania.
		 * 
		 * Wp³aty s¹ przepisywane z ML=0 oraz informacj¹ o braku dopasowania.
		 * 
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta³e wp³aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			DataObjectMerged toAdd = new DataObjectMerged(tempPayU);
			ret.add(toAdd);
			System.out.println("Brak dopasowania (ML=0). P³atnoœæ "+tempPayU.PaymentID);
		}
		
		/*
		 * W tym miejscu rozpoczynamy po³¹czenie z GT i pobieranie numerów faktur dla zamówieñ
		 */
		
		String connectionString =  
	            "jdbc:sqlserver://"+PayUAppConstants.SGT_SQL_SERVER+";"  
	            + "database="+PayUAppConstants.SGT_SQL_DATABASE+";"  
	            + "user="+PayUAppConstants.SGT_SQL_USERNAME+";"  
	            + "password="+PayUAppConstants.SGT_SQL_PASSWORD+";"  
	            + "encrypt=false;"  
	            + "trustServerCertificate=false;"  
	            + "hostNameInCertificate=*.database.windows.net;"  
	            + "loginTimeout=30;";  

        // Declare the JDBC objects.  
        Connection connection = null;  
        Statement statement = null;
        ResultSet resultSet = null;
        int i=-1; 
        int FVcount=0;
		try {
			connection = DriverManager.getConnection(connectionString);
			
			for(i=0; i<ret.size(); i++)
			{
				if(ret.get(i).MatchLevel==0) {
					continue;
				}
				String oid = ret.get(i).OrderId;
				ret.get(i).SetInvoice("Brak FV");
				String selectSql = "SELECT T2.dok_NrPelny, T1.dok_NrPelny, T1.dok_NrPelnyOryg FROM UNIVERSAL.dbo.dok__Dokument T1 INNER JOIN UNIVERSAL.dbo.dok__Dokument T2 ON T1.dok_Id=T2.dok_DoDokId WHERE T1.dok_NrPelnyOryg LIKE '%"+oid+"%'";
				statement = connection.createStatement();
	            resultSet = statement.executeQuery(selectSql);
	            
	            while (resultSet.next())   
	            {
	            	String GTInvoiceId = resultSet.getString(1);
	            	ret.get(i).SetInvoice(GTInvoiceId);
	            	FVcount++;
	            	System.out.println("Znaleziono fakturê "+(i+1)+". Order "+ret.get(i).OrderId+", P³atnoœæ "+ret.get(i).PaymentID+", Faktura "+ret.get(i).InvoiceId);
	            }
	            
	            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
	            if (statement != null) try { statement.close(); } catch(Exception e) {}
			}
			
			
		 }  
        catch (Exception e) {  
            e.printStackTrace();  
        }  
        finally {  
            // Close the connections after the data has been handled.  
            if (resultSet != null) try { resultSet.close(); } catch(Exception e) {}  
            if (statement != null) try { statement.close(); } catch(Exception e) {}  
            if (connection != null) try { connection.close(); } catch(Exception e) {}  
        }
		
		System.out.println("Znaleziono dopasowanie zamówieñ dla "+ret.size()+" z "+totalPaymentsCount+" wp³at.");
		System.out.println("Znaleziono dopasowanie faktury dla "+FVcount+" z "+totalPaymentsCount+" wp³at.");
		return ret;
	}
}
