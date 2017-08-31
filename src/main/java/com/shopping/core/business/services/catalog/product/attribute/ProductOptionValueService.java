package com.shopping.core.business.services.catalog.product.attribute;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shopping.core.business.exception.ServiceException;

import com.shopping.core.model.catalog.product.attribute.ProductOptionValue;


public interface ProductOptionValueService  {

	void saveOrUpdate(ProductOptionValue entity) throws ServiceException;

	List<ProductOptionValue> getByName( String name) throws ServiceException;



	List<ProductOptionValue> listNoReadOnly() throws ServiceException;

	ProductOptionValue getByCode( String optionValueCode);
	
	ProductOptionValue getById(Long optionValueId);

	Page<ProductOptionValue> findAll(String option_val_name, Pageable pageRequest);

	List<ProductOptionValue> listAll();

}
