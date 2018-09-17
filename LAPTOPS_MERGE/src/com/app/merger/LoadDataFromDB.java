package com.app.merger;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class LoadDataFromDB {
	public static ArrayList<String> process()
	{
		String connectionString =  
	            "jdbc:sqlserver://;"  
	            + "database=;"  
	            + "user=sa2;"  
	            + "password=;"  
	            + "encrypt=false;"  
	            + "trustServerCertificate=false;"  
	            + "hostNameInCertificate=*.database.windows.net;"  
	            + "loginTimeout=30;";  

        // Declare the JDBC objects.  
        Connection connection = null;  
        Statement statement = null;
        ResultSet resultSet = null;
        
        ArrayList<String> raw_data = new ArrayList<String>();
        
		try {
			connection = DriverManager.getConnection(connectionString);
			
			// Create and execute a SELECT SQL statement.
			// Select all product in LAPTOPY group
			
			String selectSql = "SELECT	UNIVERSAL.dbo.tw__Towar.tw_Id,\r\n" + 
					"		UNIVERSAL.dbo.tw__Towar.tw_Symbol,\r\n" + 
					"		UNIVERSAL.dbo.tw__Towar.tw_Opis,\r\n" + 
					"		UNIVERSAL.dbo.tw__Towar.tw_IdGrupa,\r\n" + 
					"		UNIVERSAL.dbo.tw_Cena.tc_CenaBrutto1,\r\n" + 
					"		UNIVERSAL.dbo.tw__Towar.tw_Pole1\r\n" + 
					"\r\n" + 
					"FROM UNIVERSAL.dbo.tw__Towar\r\n" + 
					"\r\n" + 
					"LEFT JOIN UNIVERSAL.dbo.tw_Stan\r\n" + 
					"ON UNIVERSAL.dbo.tw__Towar.tw_Id=UNIVERSAL.dbo.tw_Stan.st_TowId\r\n" + 
					"LEFT JOIN UNIVERSAL.dbo.tw_Cena\r\n" + 
					"ON UNIVERSAL.dbo.tw__Towar.tw_Id=UNIVERSAL.dbo.tw_Cena.tc_IdTowar \r\n" + 
					"\r\n" + 
					"WHERE\r\n" + 
					"tw_IdGrupa='5' and (st_Stan-st_StanRez)>0";
			statement = connection.createStatement();
            resultSet = statement.executeQuery(selectSql);

        	System.out.println("(SQL) Wykonywanie zapytania: "+selectSql+"...");
		
            // Get the Quantity of all INCOM products
            while (resultSet.next())   
            {
            	String temp = resultSet.getString(3);
        		String sym = resultSet.getString(2);
        		String cbrutto = resultSet.getString(5);
        		String id = resultSet.getString(6);
            	System.out.println("(Debug) Zapis pozycji: "+temp);
            	raw_data.add(sym);
            	raw_data.add(temp);
            	raw_data.add(cbrutto);
            	raw_data.add(id);
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
        
		return raw_data;
	}
}
