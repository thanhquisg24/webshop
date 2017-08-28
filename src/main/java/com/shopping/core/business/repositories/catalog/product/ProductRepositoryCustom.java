package com.shopping.core.business.repositories.catalog.product;

import java.util.List;
import java.util.Set;

import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.ProductCriteria;
import com.shopping.core.model.catalog.product.ProductList;



public interface ProductRepositoryCustom {
	
	
	
    	boolean existsSKU(String sku);
		
		 Product getByFriendlyUrl(String seUrl);

		List<Product> getProductsListByCategories(@SuppressWarnings("rawtypes") Set categoryIds);


		Product getById(Long productId);

		Product getByCode(String productCode);
		ProductList listBySearch(ProductCriteria criteria);

}
