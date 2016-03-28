package org.hac.dropbox;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;

public class FileResize {
	
	public static InputStream scaleImage() {
		InputStream is = null;
        String pathToOriginalImage = "/home/amin/amroot/pics/cam1.jpg";
        String imageFormat = "jpg";
        Integer targetWidth = 400;
        Integer targetHeight = 600;
 
        File originalImageFile = new File(pathToOriginalImage);
        try {
			BufferedImage image = ImageIO.read(originalImageFile);
			BufferedImage scaledImg = Scalr.resize(image, Scalr.Method.QUALITY,
					Scalr.Mode.FIT_TO_WIDTH, targetWidth, targetHeight,
					Scalr.OP_ANTIALIAS);
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(scaledImg, "jpg", os);
			is = new ByteArrayInputStream(os.toByteArray());
		} catch (Exception e) {
			System.out.println("error in scaling image "+e.toString());
		}
		return is;
        
    }
	
	 public static void main(String[] args) throws IOException {
		 String pathToOriginalImage = "/home/amin/amroot/pics/cam1.jpg";
		 
		 Path target =Paths.get("/home/amin/amroot/pics/test1.jpg");
		 
		 InputStream in = ImageResizer.createResizedCopy(pathToOriginalImage, 1550);
		 Files.copy(in, target ,StandardCopyOption.REPLACE_EXISTING);
		 in.close();
	}
}
