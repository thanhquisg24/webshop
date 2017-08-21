package com.shopping.core.business.services.catalog.product.attribute;

import java.util.List;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.attribute.ProductOptionValueRepository;
import com.shopping.core.model.catalog.product.attribute.ProductAttribute;
import com.shopping.core.model.catalog.product.attribute.ProductOptionValue;


@Service("productOptionValueService")
public class ProductOptionValueServiceImpl implements
		ProductOptionValueService {

	/*@Autowired
	private ProductAttributeService productAttributeService;*/
	
	
	@Autowired
	private ProductOptionValueRepository productOptionValueRepository;

	
	
	
	@Override
	public List<ProductOptionValue> listNoReadOnly() throws ServiceException {
		
		return productOptionValueRepository.findByReadOnly( false);
	}

	@Override
	public List<ProductOptionValue> getByName(String name) throws ServiceException {
		
		try {
			return productOptionValueRepository.findByName( name);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	@Override
	public void saveOrUpdate(ProductOptionValue entity) throws ServiceException {
		
		productOptionValueRepository.saveAndFlush(entity);
		
		//save or update (persist and attach entities
		/*if(entity.getId()!=null && entity.getId()>0) {

			super.update(entity);
			
		} else {
			
			super.save(entity);
			
		}*/
		
	}
	
	
	public void delete(ProductOptionValue entity) throws ServiceException {
		
		//remove all attributes having this option
		//List<ProductAttribute> attributes = productAttributeService.getByOptionValueId( entity.getId());
		
	//	for(ProductAttribute attribute : attributes) {
			//productAttributeService.delete(attribute);
		//}
		
		ProductOptionValue option = getById(entity.getId());
		
		//remove option
		productOptionValueRepository.delete(option);
		
	}
	
	@Override
	public ProductOptionValue getByCode(String optionValueCode) {
		return productOptionValueRepository.findByCode( optionValueCode);
	}


	@Override
	public ProductOptionValue getById(Long optionValueId) {
		return productOptionValueRepository.findOne( optionValueId);
	}



}
