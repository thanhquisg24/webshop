package com.shopping.core.business.services.catalog.product.attribute;

import java.util.List;







import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.product.attribute.ProductOptionRepository;
import com.shopping.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.shopping.core.model.catalog.product.attribute.ProductAttribute;
import com.shopping.core.model.catalog.product.attribute.ProductOption;


@Service("productOptionService")
public class ProductOptionServiceImpl  implements ProductOptionService {

	
	@Autowired
	private ProductOptionRepository productOptionRepository;
	
	/*@Autowired
	private ProductAttributeService productAttributeService;*/
	
	
	@Override
	public List<ProductOption> listReadOnly() throws ServiceException {

		return productOptionRepository.findByReadOnly( true);

	}
	

	
	@Override
	public List<ProductOption> getByName( String name) throws ServiceException {
		
		try {
			return productOptionRepository.findByName( name);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	@Override
	public void saveOrUpdate(ProductOption entity) throws ServiceException {
		
		productOptionRepository.saveAndFlush(entity);
		//save or update (persist and attach entities
		/*if(entity.getId()!=null && entity.getId()>0) {
			super.update(entity);
		} else {
			super.save(entity);
		}*/
		
	}
	
	@Override
	public void delete(ProductOption entity) throws ServiceException {
		
		//remove all attributes having this option
		//List<ProductAttribute> attributes = productAttributeService.getByOptionId( entity.getId());
		
	//	for(ProductAttribute attribute : attributes) {
			//productAttributeService.delete(attribute);
		//}
		
		ProductOption option = this.getById(entity.getId());
		
		//remove option
		productOptionRepository.delete(option);
		
	}
	
	@Override
	public ProductOption getByCode( String optionCode) {
		return productOptionRepository.findByCode( optionCode);
	}

	@Override
	public ProductOption getById( Long optionId) {
		return productOptionRepository.findOne( optionId);
	}



	@Override
	public Page<ProductOption> findAll(String option_name, Pageable pageRequest) {
		// TODO Auto-generated method stub
		 return productOptionRepository.findAll(option_name,pageRequest);
	}
	

	




}
