package com.app.blexport;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.net.ftp.FTPSClient;


public class ExportFTP {
	public static int sendToFtp() throws IOException {
		
		System.out.println("Przesy�anie pliku na serwer FTP...");
		
		FTPSClient ftpClient = new FTPSClient();
		ftpClient.setControlKeepAliveTimeout(360);
		ftpClient.connect(BLExportConstants.FTP_HOST);
		ftpClient.login(BLExportConstants.FTP_LOGIN, BLExportConstants.FTP_PASSWORD);
		ftpClient.enterLocalPassiveMode();
		
	    int reply = ftpClient.getReplyCode();

	      if(!FTPReply.isPositiveCompletion(reply)) {
	        ftpClient.disconnect();
	        System.err.println("(B��d) Nie uda�o si� po��czy� z serwerem FTP.");
	        return 1;
	    }
	    
	    InputStream iStream = new FileInputStream(BLExportConstants.LOCAL_CSV_PATH);
	    
	    if((ftpClient.storeFile(BLExportConstants.FTP_DEST_PATH, iStream))==false) {
	    	System.out.println("(B��d) Nie uda�o si� przes�a� pliku na serwer.");
	    	iStream.close();
	    	return 2;
	    } else {
	    	System.out.println("Plik CSV zosta� przes�any na serwer.");
	    }
	    
	    iStream.close();
	    ftpClient.logout();
		ftpClient.disconnect();
		return 0;
	}
}
