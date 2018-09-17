package com.app.blexport;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class BLExportMain {

	public static void main(String[] args) {
		
		System.out.println("<<< BL Exporter -- mystery synchronization tool >>>\n   (wersja b180917)\n");
		
		ArrayList<BLProduct> list = new ArrayList<BLProduct>();
		try {
			list = ExportBL.getProductsFromBL();
		} catch (IOException e) {
			System.out.println("Wyst¹pi³ wyj¹tek w funkcji getProductsFromBL()");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Pobrano "+list.size()+" produktów.");
		
		ExportCSV.exportToCSV(list);
		
		try {
			ExportFTP.sendToFtp();
		} catch (IOException e) {
			System.out.println("Wyst¹pi³ wyj¹tek w funkcji sendToFtp()");
			e.printStackTrace();
		}
		
		
		try {
			System.out.println("Przesy³anie polecenia aktualizacji do importera...");
			Document doc = Jsoup.connect(BLExportConstants.ACTIVATE_IMPORT_URI).get();
			String response = doc.html();
			
			if(response.contains("<pre>Update qty:bool(true)")) {
				System.out.println("Pomyœlna aktualizacja importera.");
			} else {
				System.out.println("(B³¹d) Importer nie zaktualizowa³ stanów. Szczegó³y b³êdu: ");
				System.out.println(response);
			}
		} catch (IOException e) {
			System.out.println("(B³¹d) Niepowodzenie przesy³ania polecenia.");
			e.printStackTrace();
		}
	}
}
