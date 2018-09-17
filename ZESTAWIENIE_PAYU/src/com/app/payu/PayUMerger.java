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
		 * Pierwszy obieg p�tli pr�buje odnalez� pe�ne dopasowania, tj. z Match Level  = 3
		 * Musz� by� zgodne trzy elementy: login, transakcje, kwota
		 * 
		 * Pozosta�e wp�aty, dla kt�rych nie zostanie znalezione pe�ne dopasowanie, zostan� skierowane
		 * do kolejnych p�tli, z ni�szymi match levelami.
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie wp�aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			boolean found = false;
			
			for(int j=0; j<tb.size(); j++)	// szukamy dopasowania w baselinkerze
			{
				DataObjectBaselinker tempBL = tb.get(j);
				
				if(tempPayU.UserID.equals(tempBL.UserID))	// najpierw sprawdzamy login
				{
					boolean checkTransactions = true;		// teraz por�wnujemy list� transakcji
					
					for(int k=0; k<tempPayU.Transactions.size(); k++)
					{
						if(!checkTransactions)				// brak zgodno�ci - wyj�cie
						{
							break;
						}
						
						checkTransactions = false;
						
						for(int l=0; l<tempBL.Transactions.size(); l++)
						{
							if(tempPayU.Transactions.get(k).equals(tempBL.Transactions.get(l)))	// por�wnanie
							{
								checkTransactions = true;	// je�li dopasowanie znalezione, przechodzimy do nast�pnej transakcji
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
							System.out.println("Znaleziono dopasowanie (ML=3). Zam�wienie "+tempBL.OrderID+", P�atno�� "+tempPayU.PaymentID);
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
		
		System.out.println("(Debug) Pierwszy przebieg, ML=3 | Pozosta�o "+tp.size()+" wp�at (tp) i "+tb.size()+" zam�wie�. (tb)");
		
		/*
		 * Drugi obieg p�tli pr�buje odnalez� cz�ciowe dopasowania z Match Level  = 2
		 * Musz� by� zgodne dwa elementy: login, transakcje
		 * 
		 * Pozosta�e wp�aty, dla kt�rych nie zostanie znalezione pe�ne dopasowanie, zostan� skierowane
		 * do kolejnej p�tli z match level 1
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta�e wp�aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			boolean found = false;
			
			for(int j=0; j<tb.size(); j++)	// szukamy dopasowania w baselinkerze
			{
				DataObjectBaselinker tempBL = tb.get(j);
				
				if(tempPayU.UserID.equals(tempBL.UserID))	// najpierw sprawdzamy login
				{
					boolean checkTransactions = true;		// teraz por�wnujemy list� transakcji
					
					for(int k=0; k<tempPayU.Transactions.size(); k++)
					{
						if(!checkTransactions)				// brak zgodno�ci - wyj�cie
						{
							break;
						}
						
						checkTransactions = false;
						
						if(tempPayU.Transactions.size()==tempBL.Transactions.size())
						{
							for(int l=0; l<tempBL.Transactions.size(); l++)
							{
								if(tempPayU.Transactions.get(k).equals(tempBL.Transactions.get(l)))	// por�wnanie
								{
									checkTransactions = true;	// je�li dopasowanie znalezione, przechodzimy do nast�pnej transakcji
									break;
								}
							}
						}
					}
					
					if(checkTransactions)
					{
						DataObjectMerged toAdd = new DataObjectMerged(tempBL, tempPayU);
						toAdd.SetMatchLevel(2);
						toAdd.AddWarning("Niezgodna kwota wp�aty");
						ret.add(toAdd);
						System.out.println("Znaleziono dopasowanie (ML=2). Zam�wienie "+tempBL.OrderID+", P�atno�� "+tempPayU.PaymentID);
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
		
		System.out.println("(Debug) Drugi przebieg, ML=2 | Pozosta�o "+tp.size()+" wp�at (tp) i "+tb.size()+" zam�wie�. (tb)");
		
		/*
		 * Trzeci obieg p�tli pr�buje odnalez� cz�ciowe dopasowania z Match Level  = 1
		 * Musi by� zgodny jeden element: login
		 * 
		 * Pozosta�e wp�aty, dla kt�rych nie zostanie znalezione dopasowanie, zostan� skierowane
		 * do kolejnej p�tli z match level 0
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta�e wp�aty
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
					System.out.println("Znaleziono dopasowanie (ML=1). Zam�wienie "+tempBL.OrderID+", P�atno�� "+tempPayU.PaymentID);
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
		
		System.out.println("(Debug) Trzeci przebieg, ML=1 | Pozosta�o "+tp.size()+" wp�at (tp) i "+tb.size()+" zam�wie�. (tb)");
		
		/*
		 * Czwarty obieg przetwarza wp�aty, dla kt�rych nie znaleziono �adnego dopasowania.
		 * 
		 * Wp�aty s� przepisywane z ML=0 oraz informacj� o braku dopasowania.
		 * 
		 */
		
		for(int i = 0; i < tp.size(); i++)	// sprawdzamy wszystkie pozosta�e wp�aty
		{
			DataObjectPayU tempPayU = tp.get(i);
			DataObjectMerged toAdd = new DataObjectMerged(tempPayU);
			ret.add(toAdd);
			System.out.println("Brak dopasowania (ML=0). P�atno�� "+tempPayU.PaymentID);
		}
		
		/*
		 * W tym miejscu rozpoczynamy po��czenie z GT i pobieranie numer�w faktur dla zam�wie�
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
	            	System.out.println("Znaleziono faktur� "+(i+1)+". Order "+ret.get(i).OrderId+", P�atno�� "+ret.get(i).PaymentID+", Faktura "+ret.get(i).InvoiceId);
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
		
		System.out.println("Znaleziono dopasowanie zam�wie� dla "+ret.size()+" z "+totalPaymentsCount+" wp�at.");
		System.out.println("Znaleziono dopasowanie faktury dla "+FVcount+" z "+totalPaymentsCount+" wp�at.");
		return ret;
	}
}
