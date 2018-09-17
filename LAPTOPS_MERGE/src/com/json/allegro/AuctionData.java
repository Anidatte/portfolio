package com.json.allegro;

import java.util.ArrayList;

public class AuctionData {
	public String nrAukcji;
	public String accountId;
	public ArrayList <String> symbolsList;
	
	public AuctionData() {
		nrAukcji = "";
		accountId = "";
		symbolsList = new ArrayList<String>();
	}
}
