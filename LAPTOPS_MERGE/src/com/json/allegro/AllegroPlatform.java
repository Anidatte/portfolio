package com.json.allegro;

import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.oauth.OAuth2Client;


public class AllegroPlatform {
	
	String accessToken;
	public ArrayList <AuctionData> auctionsListing;
	Integer limit;
	Integer offset;
	String IdWwwShoplet;
	String IdWww4Dell;
	String IdShopletPl;
	final String preLink = "https://allegro.pl/show_item.php?item=";
	JFrame frame;
	JProgressBar bar;
	
	public AllegroPlatform(JFrame p, JProgressBar b) throws Exception
	{
		accessToken = OAuth2Client.AuthorizeOAuth2();
		auctionsListing = new ArrayList<AuctionData>();
		limit = 100;
		offset = 0;
		IdShopletPl = "11501445";
		IdWww4Dell = "7413094";
		IdWwwShoplet = "4146518";
		frame = p;
		bar = b;
		
	}
	
	public void DebugShowAuctionsList()
	{
		for(int i = 0; i<auctionsListing.size(); i++)
		{
			System.out.println(auctionsListing.get(i).accountId + " / " + auctionsListing.get(i).nrAukcji);
		}
		System.out.println("Wczytano aukcji: "+auctionsListing.size());
	}
	
	public int ParseHTML(JProgressBar progressBar) throws IOException, InterruptedException
	{
		ArrayList <AuctionData> auctionsListingNew = new ArrayList<AuctionData>();
		
		bar.setMaximum(auctionsListing.size());
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setIndeterminate(false);
		for (int i = 0; i<auctionsListing.size(); i++)
		{
			AuctionData tmp = new AuctionData();
			
			tmp.accountId = auctionsListing.get(i).accountId;
			tmp.nrAukcji = auctionsListing.get(i).nrAukcji;
			tmp.symbolsList = DebugParseHTML(auctionsListing.get(i).nrAukcji, i);
			bar.setValue(i+1);
			
			auctionsListingNew.add(tmp);
		}
		
		auctionsListing = auctionsListingNew;
		bar.setStringPainted(false);
		return 0;
	}
	
	private ArrayList <String> DebugParseHTML(String nr, int count) throws IOException
	{
		ArrayList <String> ret = new ArrayList<>();
		Document doc = Jsoup.connect(preLink+nr).get();
		int size = auctionsListing.size();
		Elements el = doc.getElementsByClass("text-item");
		
		if(el.size()<1)
		{
			return ret;
		}
		
		Element nrmaglist = el.get(el.size()-1);
		String nrmaglists = nrmaglist.toString();
		String[] splitted = nrmaglists.split(":");
		
		if(splitted.length==2)
		{
			String tmp = splitted[1].toUpperCase().replace("</P>", "");
			tmp = tmp.replace("</SECTION>", "");
			tmp = tmp.replace("&NBSP;", "");
			tmp = tmp.replace("</B>", "");
			tmp = tmp.replace("<B>", "");
			tmp = tmp.replace("<P>", "");
			tmp = tmp.replace(System.getProperty("line.separator"), ",");
			tmp = tmp.trim();
			String[] splitted_in = tmp.split(",");
			
			System.out.print("Aukcja "+nr+" ("+count+"/"+size+"): ");
			for(int i=0; i<splitted_in.length; i++)
			{
				ret.add(splitted_in[i].trim());
				System.out.print(splitted_in[i].trim()+", ");
			}
			System.out.print("\n");
		}
		
		return ret;
	}
	
	public int ReadAuctionsListAll() throws JsonParseException, JsonMappingException, IOException
	{
		auctionsListing = new ArrayList<AuctionData>();
		bar.setMaximum(100);
		bar.setValue(0);
		bar.setStringPainted(true);
		bar.setIndeterminate(false);
		
		ReadAuctionsList(IdWwwShoplet);
		bar.setValue(34);
		  
        ReadAuctionsList(IdWww4Dell);
        bar.setValue(66);
            		
        ReadAuctionsList(IdShopletPl);
        bar.setValue(100);	
		
		return 0;
	}
	
	private int ReadAuctionsList(String userId) throws JsonParseException, JsonMappingException, IOException
	{
		ClientConfig clientConfig = new ClientConfig(); 
		Client client = ClientBuilder.newClient(clientConfig);
		 
		WebTarget webTarget = client.target("https://api.allegro.pl");
		WebTarget resourceWebTarget = webTarget.path("/offers/listing");
		WebTarget WebTargetWithQueryParam =
		        resourceWebTarget.queryParam("seller.id", userId).queryParam("limit", "2").queryParam("offset", "0");
		
		Invocation.Builder invocationBuilder =
		        WebTargetWithQueryParam.request(MediaType.TEXT_PLAIN_TYPE);
		invocationBuilder.header("Authorization", "Bearer "+accessToken).header("Accept", "application/vnd.allegro.public.v1+json");
		
		Response response = invocationBuilder.get();
		System.out.println("(Debug) Odpowiedz API: "+response.getStatus());
		
		String ReadedJSON = response.readEntity(String.class);
		ObjectMapper mapper = new ObjectMapper();
		AllegroObject data = mapper.readValue(ReadedJSON, AllegroObject.class);
		
		boolean nextOffset = true;
		int totalCount = data.getSearchMeta().getTotalCount();
		offset = 0;
		
		while(nextOffset)
		{
			// Tworzymy nowy target - na podstawie poprzedniego, zmieniamy tylko parametry
			
			WebTarget target2 = resourceWebTarget.queryParam("seller.id", userId).queryParam("limit", "100").queryParam("category.id", "491").queryParam("offset", offset.toString());
			Invocation.Builder inv = target2.request(MediaType.TEXT_PLAIN_TYPE);
			inv.header("Authorization", "Bearer "+accessToken).header("Accept", "application/vnd.allegro.public.v1+json");
			 
			// Wysy³amy GET i otrzymujemy zasób
			
			Response res = null;
			
			for (int i=0; i<3; i++)
			{
				// Na wszelki wypadek wykonujemy maksymalnie 3 próby pobrania zasobu
				res = inv.get();
				if(res.getStatus() == 200)
				{
					break;
				}
				// TODO: obs³uga b³êdu po trzech nieudanych próbach
			}
			
			// Mapujemy JSON na obiekt
			
			String ReadedJSON2 = res.readEntity(String.class);
			AllegroObject datatemp = mapper.readValue(ReadedJSON2, AllegroObject.class);
			
			// Sprawdzamy ile obiektów jest do zapisania, promowanych i niepromowanych
			
			int s_promoted = datatemp.getItems().getPromoted().size();
			int s_regular = datatemp.getItems().getRegular().size();
			
			System.out.println("(Debug) Promoted: "+s_promoted+" | Regular: "+s_regular);
			
			// Zapisujemy obiekty promowane
			
			for(int j=0; j < s_promoted; j++)
			{
				AuctionData ta_data = new AuctionData();
				ta_data.nrAukcji = datatemp.getItems().getPromoted().get(j).getId();
				ta_data.accountId = IdShopletPl;
				auctionsListing.add(ta_data);
			}
			
			// Zapisujemy obiekty niepromowane
			
			for(int j=0; j < s_regular; j++)
			{
				AuctionData ta_data = new AuctionData();
				ta_data.nrAukcji = datatemp.getItems().getRegular().get(j).getId();
				ta_data.accountId = IdShopletPl;
				auctionsListing.add(ta_data);
			}
			
			// Podnosimy offset i jeœli to konieczne, powtarzamy pêtlê by wczytaæ kolejne aukcje
			
			offset+=limit;
			if(offset >= totalCount)
			{
				nextOffset = false;
			}
		}
		return 0;
	}
}


