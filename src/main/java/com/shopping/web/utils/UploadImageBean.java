package com.shopping.web.utils;

import java.awt.image.BufferedImage;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import javax.imageio.ImageIO;

import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class UploadImageBean {

	// private static final Logger logger = LoggerFactory.getLogger(UploadImageBean.class);
	 @Value("${productImage.upload.directory}")
	  private String productImageDirectory;
	 
	 @Value("${productImage.upload_delete.directory}")
	  private String productImage_deleteDirectory;
	 
	 
	 
	public void processProductImage(MultipartFile file) throws IOException{
		 //File imageDestination = new File(productImageDirectory);
		UUID newFilenameBase= UUID.randomUUID();
		InputStream input= file.getInputStream();
		String newFilename=String.format("%s.jpg", newFilenameBase);
		BufferedImage imBuff = ImageIO.read(input);
		saveProfileImage(imBuff,newFilename);
		imBuff.flush();
		
		
		String newLageFilename =  String.format("%s-large.jpg", newFilenameBase);
		BufferedImage bufferedLageImage =
	                Thumbnails.of(file.getInputStream())
	                        .size(268, 327)
	                        .allowOverwrite(true)
	                        .outputFormat("jpg")
	                        .asBufferedImage();
		saveProfileImage(bufferedLageImage,newLageFilename);
		
		
		
		String newMediumFilename =  String.format("%s-medium.jpg", newFilenameBase);
		BufferedImage bufferedMediumImage =
                Thumbnails.of(file.getInputStream())
                        .size(196, 240)
                        .allowOverwrite(true)
                        .outputFormat("jpg")
                        .asBufferedImage();
		saveProfileImage(bufferedMediumImage,newMediumFilename);
		
		String newSmallFilename =  String.format("%s-small.jpg", newFilenameBase);
		BufferedImage bufferedSmallImage =
                Thumbnails.of(file.getInputStream())
                        .size(160, 160)
                        .allowOverwrite(true)
                        .outputFormat("jpg")
                        .asBufferedImage();
		saveProfileImage(bufferedSmallImage,newSmallFilename);
		
		 
	}
	
	public void deleteProductImage(String image_name) throws IOException{//temp.jpg
		
		String spilit_name=FilenameUtils.getBaseName(image_name);
		System.out.println(spilit_name);
		this.copyAndDeleteImage(image_name);
	   
		
		String smallImage=String.format("%s-large.jpg", spilit_name);
		this.copyAndDeleteImage(smallImage);
		
		
		String mediumImage= String.format("%s-medium.jpg", spilit_name);
		this.copyAndDeleteImage(mediumImage);
		
		String largeImage=String.format("%s-small.jpg", spilit_name);
		this.copyAndDeleteImage(largeImage);
		
		
		
	}
	private void copyAndDeleteImage(String image_name) throws IOException{
		
	    File imageSource= new File(productImageDirectory + image_name);
		File imageCopyTo= new File(productImage_deleteDirectory + image_name);
		this.copyTo(imageSource,imageCopyTo);
		this.deleteFile(imageSource);
		
	}
	
	
	 private void saveProfileImage(BufferedImage bufferedImage, String filename) throws IOException {
		 	
	        File imageDestination = new File(productImageDirectory + filename);
	        ImageIO.write(bufferedImage, "jpg", imageDestination);
	    }
	 
	 
	 
	 
	 private void deleteFile(File file){
		 file.delete();
	 }
	 
	 private void copyTo(File source, File dest) throws IOException{
		 FileUtils.copyFile(source, dest);

	 }
}
