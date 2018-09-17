package com.app.blexport;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ExportCSV {
	public static int exportToCSV(ArrayList<BLProduct> in) {
		
		System.out.println("Eksportowanie danych do pliku CSV...");
		
		String data = "\"produkt_id\";\"sku\";\"cena\";\"ilosc\"\n";
		
		for (int i=0; i<in.size(); i++) {
			data += in.get(i).CsvStr+"\n";
		}
		
		File f = new File(BLExportConstants.LOCAL_CSV_PATH);
		f.delete();
		try (PrintWriter out = new PrintWriter(new OutputStreamWriter( new FileOutputStream(BLExportConstants.LOCAL_CSV_PATH, true ), "UTF-8" ))) {
		    out.print(data);
		} catch (UnsupportedEncodingException | FileNotFoundException e) {
			System.out.println("(B³¹d) Nie uda³o siê zapisaæ pliku.");
			e.printStackTrace();
			return 1;
		}
		
		System.out.println("Plik zapisany.");
		return 0;
	}
}
