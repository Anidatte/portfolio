package com.app.blexport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;


public class ExportFTP {
	public static int sendToFtp() throws IOException {
		
		System.out.println("Przesy³anie pliku na serwer FTP...");
		
		FTPSClient ftpClient = new FTPSClient();
		ftpClient.setControlKeepAliveTimeout(360);
		ftpClient.connect(BLExportConstants.FTP_HOST);
		ftpClient.login(BLExportConstants.FTP_LOGIN, BLExportConstants.FTP_PASSWORD);
		ftpClient.enterLocalPassiveMode();
		
	    int reply = ftpClient.getReplyCode();

	      if(!FTPReply.isPositiveCompletion(reply)) {
	        ftpClient.disconnect();
	        System.err.println("(B³¹d) Nie uda³o siê po³¹czyæ z serwerem FTP.");
	        return 1;
	    }
	    
	    InputStream iStream = new FileInputStream(BLExportConstants.LOCAL_CSV_PATH);
	    
	    if((ftpClient.storeFile(BLExportConstants.FTP_DEST_PATH, iStream))==false) {
	    	System.out.println("(B³¹d) Nie uda³o siê przes³aæ pliku na serwer.");
	    	iStream.close();
	    	return 2;
	    } else {
	    	System.out.println("Plik CSV zosta³ przes³any na serwer.");
	    }
	    
	    iStream.close();
	    ftpClient.logout();
		ftpClient.disconnect();
		return 0;
	}
}
