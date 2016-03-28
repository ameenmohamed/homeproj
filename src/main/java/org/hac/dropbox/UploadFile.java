package org.hac.dropbox;
import com.dropbox.core.*;
import com.dropbox.core.v1.DbxClientV1;
import com.dropbox.core.v1.DbxEntry;
import com.dropbox.core.v1.DbxWriteMode;
import com.dropbox.core.DbxException;

import java.io.*;
import java.nio.file.Path;
import java.util.Locale;

public class UploadFile {
	public static void main(String[] args) {
		uploadToDropbox("/home/amin/amroot/pics/cam1.jpg");
	
	}
	
	
	public static void uploadToDropbox(String flLoc) {
		 final String APP_KEY = "qtpldsehgstmb4a";
	        final String APP_SECRET = "qtpldsehgstmb4a";
	        String ACCESS_TOKEN = "0oMJPCilPk8AAAAAAAAEPvCJEBOLgj1TeW92uxptYYKGQmAUsZVpBkKrbuJxstiv";
	        String DROPBOX_PIC_LOCATION = "/pi/shafipi";
	        String DROPBOX_VID_LOCATION = "/pi/shafipi/video";
	        final int RESIZE_VALUE = 1550;

	        DbxAppInfo appInfo = new DbxAppInfo(APP_KEY, APP_SECRET);

	        DbxRequestConfig config = new DbxRequestConfig(
	            "JavaTutorial/1.0", Locale.getDefault().toString());
	        DbxWebAuthNoRedirect webAuth = new DbxWebAuthNoRedirect(config, appInfo);
	        DbxClientV1 client = new DbxClientV1(config, ACCESS_TOKEN);
	        File inputFile = new File(flLoc);
	        
	        
	        
	        try {
	       //  inputStream = new FileInputStream(inputFile);
	        	InputStream inputStream =null;
	        	if(ImageResizer.isImage(inputFile)){
	        	 inputStream = ImageResizer.createResizedCopy(flLoc, RESIZE_VALUE);
	        	}else if(ImageResizer.isMp4(inputFile)){
	        		inputStream = new FileInputStream(inputFile);
	        	}
	            DbxEntry.File uploadedFile = client.uploadFile(DROPBOX_PIC_LOCATION+File.separator+inputFile.getName(), DbxWriteMode.add(), inputStream.available(), inputStream);
	            System.out.println("Uploaded: " + uploadedFile.toString());
	            inputStream.close();
	        } catch (DbxException e) {
				

				e.printStackTrace();
			} catch (IOException e) {
				

				e.printStackTrace();
			} 
	        
	}
	
}
