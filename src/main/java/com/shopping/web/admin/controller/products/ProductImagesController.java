package com.shopping.web.admin.controller.products;



import java.io.IOException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.shopping.web.admin.dto.ProductImageDTO;
import com.shopping.web.support.Message;
import com.shopping.web.support.Message.Type;
import com.shopping.web.utils.GenericResponse;
import com.shopping.web.utils.UploadImageBean;




@Controller
public class ProductImagesController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductImagesController.class);
	
	@Autowired
	UploadImageBean uploadImageBean;
	
	@GetMapping("/admin/product-image/upload")
	public String showFormUpload(){
		return "admin/catalogue/product/upload_demo";
	}
	
	@PostMapping("/admin/product-image/save")
	@ResponseBody
	public ResponseEntity<GenericResponse> saveAndUpload(@Valid ProductImageDTO productImageDTO) throws IOException{
		/* if(productImageDTO.getFile().getInputStream() ==null){
			 System.out.println("nulllll");
		 }*/
		 System.out.println(productImageDTO.getFile().getOriginalFilename());	
		uploadImageBean.processProductImage(productImageDTO.getFile());
		return new ResponseEntity<GenericResponse>(new GenericResponse("success"), HttpStatus.OK);
	}
	@GetMapping("/admin/product-image/test")
	public ResponseEntity<Message>  tt(){
		Message m=new Message("aaaa",Type.DANGER);
		 return new ResponseEntity<Message>(m, HttpStatus.OK);
		
	}
}
