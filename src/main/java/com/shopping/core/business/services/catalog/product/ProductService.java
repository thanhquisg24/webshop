package com.shopping.core.business.services.catalog.product;

import java.io.IOException;
import java.util.List;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.ProductCriteria;
import com.shopping.core.model.catalog.product.ProductList;




public interface ProductService {


	
	Product getProduct(long productId) throws ServiceException;
	
	List<Product> getProductsByCategory(Category category) throws ServiceException;

	List<Product> getProducts(List<Long> categoryIds) throws ServiceException;



	ProductList listBySearch(ProductCriteria criteria);



	Product getBySeUrl(String seUrl);

	/**
	 * Get a product by sku (code) field  and the language
	 * @param productCode
	 * @param language
	 * @return
	 */
	Product getByCode(String productCode);

	Product create(Product product) throws ServiceException;

	Product update(Product product) throws ServiceException;

	boolean exists(Long id);

	boolean exists(String sku);

	Product saveOrUpdate(Product persist);

	void delete(Product product) throws ServiceException, IOException;

	void delete(Long id) throws ServiceException, IOException;

	
}
	
