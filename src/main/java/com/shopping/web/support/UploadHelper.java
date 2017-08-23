package com.shopping.web.support;



import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public class UploadHelper {

	public static String doUpload(HttpServletRequest request,String folder, MultipartFile file) throws IOException {
		if (file.isEmpty()){
			return "";
		}
		
		String filename="";
    	ServletContext servl  = request.getServletContext();
		String pathsave="/upload/";
		String savePath =servl.getRealPath(pathsave+folder);
		  try {

	        	filename= saveUploadedFiles(Arrays.asList(file),savePath);

	        } catch (IOException e) {
	        	 throw e;
	        }
		
		return filename;
		
	}
	
	private static String saveUploadedFiles(List<MultipartFile> files,String folder) throws IOException {
	    	String filename="";
	        for (MultipartFile file : files) {

	            if (file.isEmpty()) {
	                continue; //next pls
	            }
	        	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				Calendar cal = Calendar.getInstance();
				////System.out.println(dateFormat.format(cal.getTime())); //2014/08/06 16:00:22
				String name =dateFormat.format(cal.getTime())+"_"+ file.getOriginalFilename();	
	            byte[] bytes = file.getBytes();
	            Path path = Paths.get(folder + File.separator+ name);
	            Files.write(path, bytes);
	            filename=name;

	        }
	        return filename;
	    }
}
