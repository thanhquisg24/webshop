package com.shopping.core.business.services.catalog.product.image;

import java.io.IOException;
import java.util.List;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.image.ProductImage;




public interface ProductImageService {
	
	
	
	/**
	 * Add a ProductImage to the persistence and an entry to the CMS
	 * @param product
	 * @param productImage
	 * @param file
	 * @throws ServiceException
	 */
	/*void addProductImage(Product product, ProductImage productImage, ImageContentFile inputImage)
			throws ServiceException;*/


	/**
	 * Returns all Images for a given product
	 * @param product
	 * @return
	 * @throws ServiceException
	 */
	List<ProductImage> getProductImages(Product product)
			throws ServiceException;
	
	List<ProductImage> getProductImages(Long product_id)
			throws ServiceException;

	void removeProductImage(ProductImage productImage) throws ServiceException, IOException;

	void saveOrUpdate(ProductImage productImage) throws ServiceException;

	/**
	 * Returns an image file from required identifier. This method is
	 * used by the image servlet
	 * @param store
	 * @param product
	 * @param fileName
	 * @param size
	 * @return
	 * @throws ServiceException
	 */


	/*void addProductImages(Product product, List<ProductImage> productImages)
			throws ServiceException;*/
	
}
