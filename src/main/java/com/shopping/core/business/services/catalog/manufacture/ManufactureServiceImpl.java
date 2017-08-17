package com.shopping.core.business.services.catalog.manufacture;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.business.repositories.catalog.manufacture.ManufacturerRepository;
import com.shopping.core.model.catalog.product.manufacturer.Manufacturer;




@Service("manufacturerService")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
public class ManufactureServiceImpl implements ManufactureService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ManufactureServiceImpl.class);
	@Autowired
	ManufacturerRepository repository;

	@Override
	public Page<Manufacturer> findAll(String manufacture_name,
			Pageable pageRequest) {
		// TODO Auto-generated method stub
		return repository.findAll(manufacture_name,pageRequest);
	}

	@Override
	public Manufacturer getByCode(String code) {
		// TODO Auto-generated method stub
		return repository.findByCode(code);
	}

	@Override
	public void saveOrUpdate(Manufacturer manufacture) throws ServiceException {
		LOGGER.debug("Creating Manufacturer");
		repository.saveAndFlush(manufacture);
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getCountManufAttachedProducts(Manufacturer manufacturer)
			throws ServiceException {
		// TODO Auto-generated method stub
		return repository.countByProduct(manufacturer.getId());
	}

	@Override
	public void delete(Manufacturer manufacturer) throws ServiceException {
		manufacturer =  repository.findOne(manufacturer.getId() );
		repository.delete(manufacturer);
		
	}

	@Override
	public List<Manufacturer> listByProductsByCategoriesId(List<Long> ids)
			throws ServiceException {
		// TODO Auto-generated method stub
		return repository.findByCategories(ids);
	}
	
}
