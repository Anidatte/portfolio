package com.app.merger;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.UpdateValuesResponse;
import com.google.api.services.sheets.v4.model.ValueRange;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class CloudPlatformWriter {
    private static final String APPLICATION_NAME = "Laptop Manager Extended";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FOLDER = "./credentials"; // Directory to store user credentials.

    private static final List<String> SCOPES = Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static final String CLIENT_SECRET_DIR = "./client_secret.json";

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = CloudPlatformWriter.class.getResourceAsStream(CLIENT_SECRET_DIR);
        in = new FileInputStream(new File(CLIENT_SECRET_DIR));
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(CREDENTIALS_FOLDER)))
                .setAccessType("offline")
                .build();
        return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
    }

    public static int WriteInventToGoogle(String sym, String loc) throws IOException, GeneralSecurityException {
        
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final String spreadsheetId = "";
        final String range = "LAPKI!A2:B";
        
        Sheets service = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        ValueRange response = service.spreadsheets().values()
                .get(spreadsheetId, range)
                .execute();
        
        List<List<Object>> values = response.getValues();
        int i = 1;
        boolean f = false;
        
        if (values == null || values.isEmpty()) {
            System.out.println("No data found.");
        } else {
            for (List row : values) {
            	i++;
            	if(row.size()==2 && row.get(0).toString().trim().equals(sym))
            	{
            		f = true;
            		break;
            	}
            	else if(row.size()==1)
            	{
            		continue;
            	}
            }
        }
        
        if(f)
        {
        	String rangeUpdate = "LAPKI!B"+Integer.toString(i);
            String valueInputOption = "USER_ENTERED";
            System.out.println(rangeUpdate);
            
            List<List<Object>> toSave = new ArrayList<List<Object>>();
            
        	toSave.add(Arrays.asList((Object)loc));
        	System.out.println(i);
            
            ValueRange requestBody = new ValueRange().setValues(toSave);
            
            Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, rangeUpdate, requestBody);
                request.setValueInputOption(valueInputOption);

            UpdateValuesResponse responseUpdate = request.execute();

            // TODO: Change code below to process the `response` object:
            System.out.println(responseUpdate);
        }
        else
        {
        	i++;
        	String rangeUpdate = "LAPKI!A"+Integer.toString(i)+":B"+Integer.toString(i);
            String valueInputOption = "USER_ENTERED";
            System.out.println(rangeUpdate);
            
            List<List<Object>> toSave = new ArrayList<List<Object>>();
            
        	toSave.add(Arrays.asList((Object)sym,(Object)loc));
        	System.out.println(i);
            
            ValueRange requestBody = new ValueRange().setValues(toSave);
            
            Sheets.Spreadsheets.Values.Update request = service.spreadsheets().values().update(spreadsheetId, rangeUpdate, requestBody);
                request.setValueInputOption(valueInputOption);

            UpdateValuesResponse responseUpdate = request.execute();

            // TODO: Change code below to process the `response` object:
            System.out.println(responseUpdate);
            
        	
        }
        
		return i;
    }
}