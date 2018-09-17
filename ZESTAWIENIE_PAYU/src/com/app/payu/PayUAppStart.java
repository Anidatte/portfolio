package com.app.payu;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.parser.csv.ReadPayUFromCSV;
import com.platform.baselinker.BLTestApi;

public class PayUAppStart {

	public static void main(String[] args) {
		try {
			System.out.println("\n *** Program Zestawienia_PayU uruchomiony, wersja b180721. ***\n");
			String log = "\n *** Program Zestawienia_PayU uruchomiony, wersja b180721. ***\n\n";
			
			File folder = new File(PayUAppConstants.WORKING_DIR);
			File[] listOfFiles = folder.listFiles();
			String filename;
			String fn_splitted[] = null;
			
			if(listOfFiles.length>0)
			{
				filename = listOfFiles[0].getName();
				fn_splitted = filename.split("_");
			}
			
			String startTimeStr = fn_splitted[3];
			String endTimeStr = fn_splitted[4].substring(0, fn_splitted[4].length()-4);
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date startDate = dateFormat.parse(startTimeStr);
			Date endDate = dateFormat.parse(endTimeStr);// zapis daty wraz z unix time
			
			Long startTimeUnix = (long) (((startDate.getTime())/1000)-604800);
			Long endTimeUnix = (long) (endDate.getTime()/1000)+86400;
			
			System.out.println("Pobieranie zamówieñ z Baselinkera...");
			ArrayList<DataObjectBaselinker> t = BLTestApi.GetOrders(startTimeUnix, endTimeUnix);
			System.out.println("Pobrano "+t.size()+" zamówieñ z Baselinkera.");	

			for (File file : listOfFiles) {
			    if (file.isFile()) {
			    	filename = file.getName();
			    	fn_splitted = filename.split("\\.");
			    	
			    	if(fn_splitted[(fn_splitted.length)-1].equals("csv") || fn_splitted[(fn_splitted.length)-1].equals("CSV"))
			    	{
			    		String fn_splitted2[] = null;
			    		String fn_noextension = filename.substring(0, filename.length()-4);
			    		fn_splitted2 = fn_noextension.split("_");
			    		
			    		if(fn_splitted2.length != 5)
			    		{
			    			System.out.println("File "+filename+" probably is not a valid PayU file, skipping...");
				    		log += "\n\r"+filename+" probably is not a valid PayU file, skipping... \n\r";
				    		continue;
			    		}
			    		System.out.println("Processing file "+filename+" ... ");
			    		log += "\n\rProcessing file "+filename+" ... \n\r";
			    		
			    		System.out.println("Pobieranie informacji o wp³atach PayU...");
						ArrayList<DataObjectPayU> p = ReadPayUFromCSV.Read(PayUAppConstants.WORKING_DIR+"/"+filename);
						System.out.println("Pobrano "+p.size()+" wp³at PayU.");
						
						System.out.println("Dopasowywanie wp³at do zamówieñ...");
						ArrayList<DataObjectMerged> m = PayUMerger.Merge(p, t);
						
						SaveToXLSX.process(m);
			    	}
			    	else
			    	{
			    		System.out.println("File "+filename+" is not CSV, skipping...");
			    		log += "\n\r"+filename+" is not CSV, skipping... \n\r";
			    	}
			        
			    }
			}
			
			try (PrintWriter out = new PrintWriter(new OutputStreamWriter( new FileOutputStream( "log.txt", true ), "UTF-8" ))) {
			    out.print(log);
			} catch (UnsupportedEncodingException | FileNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException | ParseException e) {
			
			e.printStackTrace();
		}
	}

}
