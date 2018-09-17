package com.platform.baselinker;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import org.glassfish.jersey.client.ClientConfig;

public class BLTestApi {
	public void RunTest()
	{
		ClientConfig clientConfig = new ClientConfig(); 
		Client client = ClientBuilder.newClient(clientConfig);
		 
		//WebTarget webTarget = client.target("https://api.allegro.pl");
		//WebTarget resourceWebTarget = webTarget.path("/offers/listing");
		
		WebTarget target = client.target("https://api.baselinker.com").path("/connector.php");
		
		Form form = new Form();
		form.param("token", "");
		form.param("method", "getOrders");
		form.param("parameters", "{\"date_from\": 1407341754}");

		String requestResult =
		target.request(MediaType.APPLICATION_JSON_TYPE)
		    .post(Entity.entity(form,MediaType.APPLICATION_FORM_URLENCODED_TYPE),
		        String.class);
		
		//Invocation.Builder invocationBuilder =
		//        WebTargetWithQueryParam.request(MediaType.TEXT_PLAIN_TYPE);
		//invocationBuilder.header("Authorization", "Bearer "+accessToken).header("Accept", "application/vnd.allegro.public.v1+json");
		
		//Response response = invocationBuilder.get();
		//System.out.println("(Debug) Odpowiedz API: "+response.getStatus());
		
		//String ReadedJSON = response.readEntity(String.class);
		System.out.println("Baselinker debug: \n"+requestResult);
	}
}
