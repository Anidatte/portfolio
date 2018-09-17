package com.platform.baselinker;

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

import com.app.payu.DataObjectBaselinker;
import com.app.payu.PayUAppConstants;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.baselinker.orders.Order;
import com.json.baselinker.orders.OrdersJSON;

public class BLTestApi {
	public static ArrayList<DataObjectBaselinker> GetOrders(Long startTime, Long endTime) throws JsonParseException, JsonMappingException, IOException
	{
		ArrayList<DataObjectBaselinker> ret = new ArrayList<DataObjectBaselinker>();
		ClientConfig clientConfig = new ClientConfig(); 
		Client client = ClientBuilder.newClient(clientConfig);
		WebTarget target = client.target("https://api.baselinker.com").path("/connector.php");
		
		boolean nextOrdersPack = true;
		boolean dateInRange = true;
		
		while(nextOrdersPack && dateInRange)
		{
			startTime+=1L;	// poprawka, by date_from siê nie nak³ada³o i nie dublowa³o zamówieñ pomiêdzy paczkami
			
			Form form = new Form();
			form.param("token", PayUAppConstants.TOKEN_BASELINKER);
			form.param("method", "getOrders");
			form.param("parameters", "{\"date_from\": "+startTime.toString()+", \"get_unconfirmed_orders\" : true}");
			
			String requestResult =
			target.request(MediaType.APPLICATION_JSON_TYPE)
			    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
			        String.class);
			
			
			ObjectMapper mapper = new ObjectMapper();
			OrdersJSON data = mapper.readValue(requestResult, OrdersJSON.class);
			
			if(!(data.getStatus().equals("SUCCESS")))
			{
				System.out.println("(B³¹d) Baselinker API returns error when calling getOrders method.");
				return null;
			}
			
			List<Order> ordersList = data.getOrders();
			DataObjectBaselinker objectBaselinker = null;
			
			for(int i=0; i<ordersList.size(); i++)
			{
				Long dateLimitCheck = Long.parseLong(ordersList.get(i).getDateAdd());
				
				if(dateLimitCheck>endTime)
				{
					dateInRange = false;
					break;
				}
				
				if(ordersList.get(i).getOrderSource().equals("allegro"))
				{
					objectBaselinker = new DataObjectBaselinker(ordersList.get(i));
					ret.add(objectBaselinker);
					startTime = Long.parseLong(ordersList.get(i).getDateAdd());
					//System.out.println("(Debug) Pobrano zamówienie allegro "+objectBaselinker.OrderID);
				}
				else
				{
					//System.out.println("(Debug) Pobrano zamówienie inne "+ordersList.get(i).getOrderId());
					startTime = Long.parseLong(ordersList.get(i).getDateAdd());
				}
			}
			
			
			if(ordersList.size()<100)
			{
				nextOrdersPack = false;
			}
		}
		
		return ret;
	}
}
