package com.app.blexport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.baselinker.productlist.JSONBLProductList;
import com.json.baselinker.productlist.Product;

public class ExportBL {

	public static ArrayList<BLProduct> getProductsFromBL() throws JsonParseException, JsonMappingException, IOException {
		ArrayList<BLProduct> ret = new ArrayList<BLProduct>();
		ClientConfig clientConfig = new ClientConfig(); 
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget target = client.target("https://api.baselinker.com").path("/connector.php");
		
		Form form = new Form();
		form.param("token", BLExportConstants.TOKEN_BASELINKER);
		form.param("method", "getProductsList");
		form.param("parameters", "{\"storage_id\": 1}");
		
		System.out.println("Pobieranie produktów z Baselinkera...");
		
		String requestResult =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
		        String.class);
		
		
		ObjectMapper mapper = new ObjectMapper();
		JSONBLProductList data = mapper.readValue(requestResult, JSONBLProductList.class);
		
		if(!(data.getStatus().equals("SUCCESS")))
		{
			System.out.println("(B³¹d) Baselinker API returns error when calling getProductsList method.");
			return null;
		}
		
		List<Product> productsList = data.getProducts();
		BLProduct objectBaselinker = null;
		
		for(int i=0; i<productsList.size(); i++)
		{
			objectBaselinker = new BLProduct();
			objectBaselinker.Id = productsList.get(i).getProductId();
			objectBaselinker.Name = productsList.get(i).getName();
			objectBaselinker.Price = productsList.get(i).getPriceBrutto().toString();
			objectBaselinker.EAN = productsList.get(i).getEan();
			objectBaselinker.SKU = productsList.get(i).getSku();
			objectBaselinker.Quantity = productsList.get(i).getQuantity().toString();
			objectBaselinker.generateCsvStr();
			//System.out.println("(Debug) "+objectBaselinker.CsvStr);
			ret.add(objectBaselinker);
		}
		
		return ret;

	}

}
