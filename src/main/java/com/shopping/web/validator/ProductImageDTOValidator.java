package com.shopping.web.validator;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.springframework.validation.Errors;

import org.springframework.validation.Validator;

import com.shopping.web.admin.dto.ProductImageDTO;

public class ProductImageDTOValidator implements Validator{

	private Long imageMaxSize= (long) (2 * 1024 * 1024);//2MB
	
	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return clazz.equals(ProductImageDTO.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		// TODO Auto-generated method stub
		ProductImageDTO dto=(ProductImageDTO)obj;
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "message.name", "name is required.");
		
		validateForMinMaxFileSize(errors,dto);
		validateFileType(errors,dto);
		
	}
	
	private void validateForMinMaxFileSize(Errors errors,ProductImageDTO dto){
		if(dto.getFile().getSize()==0){
			errors.rejectValue("file","file.empty","File is empty!");
			//errors.re
		}
		if(dto.getFile().getSize()>imageMaxSize){
			errors.rejectValue("file","file.too.large","File too large!");
		}
	}
	private void validateFileType(Errors errors,ProductImageDTO dto){
		try(InputStream input= dto.getFile().getInputStream()){
			try{
				ImageIO.read(input);
			}catch(Exception e){
				errors.rejectValue("file","file.not.an.image","File not an image!");
			}
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}
