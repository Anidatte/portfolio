package com.parser.csv;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import com.app.payu.DataObjectPayU;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


public class ReadPayUFromCSV {
    

    public static ArrayList<DataObjectPayU> Read(String SAMPLE_CSV_FILE_PATH) throws IOException {
    	
    	ArrayList<DataObjectPayU> ret = new ArrayList<DataObjectPayU>();
    	boolean skip = true;
    	
        try (
            Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH),Charset.forName("windows-1250"));
            CSVParser csvParser = new CSVParser(reader, CSVFormat.EXCEL.withQuote('"').withDelimiter(';'));
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing Values by Column Index
            	
                String pid = csvRecord.get(0);
                
                System.out.println("(Debug) Przetwarzanie p³atnoœci: "+pid);
                if(pid.trim().equals(""))
                {
                	break;
                }
                
                String trs = csvRecord.get(1);
                String uid = csvRecord.get(2);
                String tcash = csvRecord.get(6);
                String date = csvRecord.get(7);
                
                if(skip)
                {
                	skip = false;
                	continue;
                }
                
                DataObjectPayU objectPayU = new DataObjectPayU(pid, trs, uid, tcash, date);
                ret.add(objectPayU);
            }
        }
        
        return ret;
    }
}
