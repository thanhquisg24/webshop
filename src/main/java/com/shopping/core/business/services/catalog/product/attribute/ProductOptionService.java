package com.shopping.core.business.services.catalog.product.attribute;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.product.attribute.ProductOption;


public interface ProductOptionService {



	List<ProductOption> getByName( String name) throws ServiceException;

	void saveOrUpdate(ProductOption entity) throws ServiceException;


	List<ProductOption> listReadOnly()
			throws ServiceException;


	ProductOption getByCode( String optionCode);
	
	ProductOption getById( Long optionId);

	void delete(ProductOption entity) throws ServiceException;


	Page<ProductOption> findAll(String option_name, Pageable pageRequest);

	List<ProductOption> listAll();


	



}
