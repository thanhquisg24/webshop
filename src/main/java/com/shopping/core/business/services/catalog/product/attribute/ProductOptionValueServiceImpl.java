package com.shopping.core.business.services.catalog.product.attribute;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.attribute.ProductOptionValueRepository;
import com.shopping.core.model.catalog.product.attribute.ProductOption;
import com.shopping.core.model.catalog.product.attribute.ProductOptionValue;


@Service("productOptionValueService")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
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

	@Override
	public Page<ProductOptionValue> findAll(String option_val_name,
			Pageable pageRequest) {
		// TODO Auto-generated method stub
		return productOptionValueRepository.findByName( option_val_name,pageRequest);
	}



}
