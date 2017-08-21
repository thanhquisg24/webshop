/**
 * 
 */
package com.shopping.core.business.services.catalog.manufacture;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.product.manufacturer.Manufacturer;

/**
 * @author qui-r90270
 *
 */
public interface ManufactureService {

	Page<Manufacturer> findAll(String manufacture_name, Pageable pageRequest);

	Manufacturer getByCode(String code);
	
	Manufacturer getByID(Long id);

	void saveOrUpdate(Manufacturer manufacture) throws ServiceException ;
	

	Long getCountManufAttachedProducts( Manufacturer manufacturer )  throws ServiceException;
	
	void delete(Manufacturer manufacturer) throws ServiceException;
	

	/**
	 * List manufacturers by products from a given list of categories
	 * @param store
	 * @param ids
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	List<Manufacturer> listByProductsByCategoriesId(
			List<Long> ids) throws ServiceException;
	
}
