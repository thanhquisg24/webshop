package com.shopping.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;



import com.shopping.web.admin.dto.ProductDTO;


@Component
public class ProductDTOValidator implements Validator{

	@Override
	public boolean supports(final Class<?> clazz) {
		// TODO Auto-generated method stub
	      return ProductDTO.class.isAssignableFrom(clazz);
		// return clazz.equals(ProductDTO.class);
	}

	@Override
	public void validate(Object obj, Errors errors) {
		ProductDTO dto= (ProductDTO)obj;
		
		// TODO Auto-generated method stub
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "sku", "productdto.errors.sku", "sku is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "productdto.errors.name", "name is required.");
		/*ValidationUtils.rejectIfEmpty(errors, "manufacture_id", "productdto.errors.manufacture_id", "manufacture is required.");
		ValidationUtils.rejectIfEmpty(errors, "sortOrder", "productdto.errors.sortOrder", "sortOrder is required.");
		ValidationUtils.rejectIfEmpty(errors, "categories", "productdto.errors.categories", "categories is required.");*/
		
		if(dto.getCategories().size()==0){
			errors.rejectValue("categories", "productdto.errors.categories", "categories is required.");
		}
		if(dto.getManufacture_id()==null){
			errors.rejectValue("manufacture_id", "productdto.errors.manufacture_id", "manufacture is required.");
		}
		if(dto.getSortOrder()==null){
			errors.rejectValue( "sortOrder", "productdto.errors.sortOrder", "sortOrder is required.");
		}
		
	}

}
