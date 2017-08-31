package com.shopping.core.business.services.catalog.product.attribute;

import java.util.List;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.attribute.ProductAttribute;


public interface ProductAttributeService  {

	void saveOrUpdate(ProductAttribute productAttribute)
			throws ServiceException;
	
	List<ProductAttribute> getByOptionId(Long id) throws ServiceException;

	List<ProductAttribute> getByOptionValueId(Long id) throws ServiceException;


	List<ProductAttribute> getByAttributeIds( Product product, List<Long> ids)
			throws ServiceException;

	List<ProductAttribute> getByProductId(Long productId) throws ServiceException;

	ProductAttribute getById(Long id);

	void delete(ProductAttribute attribute) throws ServiceException;
}
