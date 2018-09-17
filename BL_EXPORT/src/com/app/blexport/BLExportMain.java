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
			System.out.println("Wyst�pi� wyj�tek w funkcji getProductsFromBL()");
			e.printStackTrace();
			return;
		}
		
		System.out.println("Pobrano "+list.size()+" produkt�w.");
		
		ExportCSV.exportToCSV(list);
		
		try {
			ExportFTP.sendToFtp();
		} catch (IOException e) {
			System.out.println("Wyst�pi� wyj�tek w funkcji sendToFtp()");
			e.printStackTrace();
		}
		
		
		try {
			System.out.println("Przesy�anie polecenia aktualizacji do importera...");
			Document doc = Jsoup.connect(BLExportConstants.ACTIVATE_IMPORT_URI).get();
			String response = doc.html();
			
			if(response.contains("<pre>Update qty:bool(true)")) {
				System.out.println("Pomy�lna aktualizacja importera.");
			} else {
				System.out.println("(B��d) Importer nie zaktualizowa� stan�w. Szczeg�y b��du: ");
				System.out.println(response);
			}
		} catch (IOException e) {
			System.out.println("(B��d) Niepowodzenie przesy�ania polecenia.");
			e.printStackTrace();
		}
	}
}
