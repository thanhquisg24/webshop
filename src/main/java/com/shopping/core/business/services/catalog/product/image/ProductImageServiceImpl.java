package com.shopping.core.business.services.catalog.product.image;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.image.ProductImageRepository;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.image.ProductImage;
import com.shopping.web.utils.UploadImageBean;

@Service("productImage")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
public class ProductImageServiceImpl
	implements ProductImageService {
	
	@Autowired
	private ProductImageRepository productImageRepository;

	@Autowired 
	private UploadImageBean productFileManager;
	
	public ProductImage getById(Long id) {

		return productImageRepository.findOne(id);
	}
	

	@Override
	public void saveOrUpdate(ProductImage productImage) throws ServiceException {
		
		productImageRepository.save(productImage);
		
	}

	
	//TODO get default product image

	

	
	
	
	@Override
	public List<ProductImage> getProductImages(Product product) throws ServiceException {
		return productImageRepository.getImages(product.getId());
	}
	
	@Override
	public void removeProductImage(ProductImage productImage) throws ServiceException, IOException {

		if(!StringUtils.isBlank(productImage.getProductImage())) {
			productFileManager.deleteProductImage(productImage.getProductImage());//managed internally
		}
		
		ProductImage p = this.getById(productImage.getId());
		productImageRepository.delete(p);
		
	}


	@Override
	public List<ProductImage> getProductImages(Long product_id)
			throws ServiceException {
		// TODO Auto-generated method stub
		return productImageRepository.getImages(product_id);
	}


	@Override
	public ProductImage getImage(Long imageId) {
		// TODO Auto-generated method stub
		return productImageRepository.getOne(imageId);
	}
}
