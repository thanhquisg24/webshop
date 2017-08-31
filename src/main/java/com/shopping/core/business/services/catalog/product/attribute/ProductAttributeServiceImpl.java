package com.shopping.core.business.services.catalog.product.attribute;





import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.attribute.ProductAttributeRepository;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.attribute.ProductAttribute;


@Service("productAttributeService")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
public class ProductAttributeServiceImpl  implements ProductAttributeService {
	

	@Autowired
	private ProductAttributeRepository productAttributeRepository;


	
	@Override
	public ProductAttribute getById(Long id) {
		
		return productAttributeRepository.findOne(id);
		
	}
	
	
	@Override
	public List<ProductAttribute> getByOptionId(
			Long id) throws ServiceException {
		
		return productAttributeRepository.findByOptionId( id);
		
	}
	
	@Override
	public List<ProductAttribute> getByAttributeIds(Product product, List<Long> ids) throws ServiceException {
		
		return productAttributeRepository.findByAttributeIds( product.getId(), ids);
		
	}
	
	@Override
	public List<ProductAttribute> getByOptionValueId(Long id) throws ServiceException {
		
		return productAttributeRepository.findByOptionValueId( id);
		
	}
	
	/**
	 * Returns all product attributes
	 */
	
	@Override
	public List<ProductAttribute> getByProductId(Long productid) throws ServiceException {
		return productAttributeRepository.findByProductId( productid);
		
	}


	@Override
	public void saveOrUpdate(ProductAttribute productAttribute)
			throws ServiceException {
		//if(productAttribute.getId()!=null && productAttribute.getId()>0) {
		//	productAttributeRepository.update(productAttribute);
		//} else {
			productAttributeRepository.save(productAttribute);
		//}
		
	}
	
	@Override
	public void delete(ProductAttribute attribute) throws ServiceException {
		
		//override method, this allows the error that we try to remove a detached instance
		attribute = this.getById(attribute.getId());
		productAttributeRepository.delete(attribute);
		
	}

}
