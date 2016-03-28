package org.hac.dropbox;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class ClickPic {
	 static String dtFormat = "yyyy-MM-dd-HH-mm-ss"; 
	 static String flExt = ".jpg";
	// Define the path to the raspistill executable.
	   private static final String _raspistillPath = "/home/pi/mmal/pic";
	 //  private static final String _raspistillPath = "/home/pi/ammotion/pic";
	   // Define the amount of time that the camera will use to take a photo.
	   private static final int _picTimeout = 5000;
	   // Define the image quality.
	   private static final int _picQuality = 100;

	   // Specify a default image width.
	   private static int _picWidth = 1024;
	   // Specify a default image height.
	   private static int _picHeight = 768;
	 
	 
	   // Specify a default image encoding.
	   private static String _picType = "jpg";

	 
	 public static void main(String[] args) throws ParseException {
	
		 if(args.length == 0){
			 System.out.println("running default option click pic");
			 ClickPic.click();
		 }
		 else if(args.length > 0){
			 String commex = args[0];
			 if(commex.equalsIgnoreCase("pic")){
				 ClickPic.click();
			 }
			 else if(commex.equalsIgnoreCase("vid")){
				 if(args.length > 1 && isNumeric(args[1])){
					 int duration = Integer.parseInt(args[1]);
					 RecordVideo rv = new RecordVideo(duration);
					 rv.recVid();
				 }else{
					 RecordVideo rv = new RecordVideo();
					 rv.recVid();
				 }
				 
			 }else{
				 System.out.println("Invalid Command:"+args[0]);
			 }
		 }
		 
		 
	}
	
	 public static String click(){
		String resp = "";
		 try{
		 StringBuilder sb = new StringBuilder("raspistill");
		 String flName = getFileName();
         // Add parameters for no preview and burst mode.
        /* sb.append(" -n -bm");
         // Configure the camera timeout.
         sb.append(" -t " + _picTimeout);
         // Configure the picture width.
         sb.append(" -w " + _picWidth);
         // Configure the picture height.
         sb.append(" -h " + _picHeight);
         // Configure the picture quality.
         sb.append(" -q " + _picQuality);
         // Specify the image type.
         sb.append(" -e " + _picType);*/
         // Specify the name of the image.
         sb.append(" -n -o " + _raspistillPath+"/"+flName);

         // Invoke raspistill to take the photo.
         System.out.println("running .."+sb.toString());
        
         Runtime.getRuntime().exec(sb.toString());
         // Pause to allow the camera time to take the photo.
         Thread.sleep(_picTimeout);
         UploadFile.uploadToDropbox(_raspistillPath+"/"+flName);
         File fl = new File(_raspistillPath+"/"+flName);
         System.out.println("del :"+fl.getAbsolutePath());
         fl.delete();
         resp = "File:"+flName + "Uploaded.";
      }
      catch (Exception e)
      {
         System.out.println(e.toString());
         //System.exit(e.hashCode());
      }
		 return resp;
	 }
	 
	public static String getFileName() throws ParseException{
		 Date now = new Date();
		 String flName = "";
	       
	        SimpleDateFormat sdf = new SimpleDateFormat(dtFormat);
	        flName = sdf.format(now);
			
			System.out.println(flName+flExt);
	        return flName+flExt;
	}
	
	 public static boolean isNumeric(String str)
	    {
	        for (char c : str.toCharArray())
	        {
	            if (!Character.isDigit(c)) return false;
	        }
	        return true;
	    }
	
	

}
