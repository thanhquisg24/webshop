package com.shopping.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.core.model.catalog.product.attribute.ProductOptionValue;

public interface ProductOptionValueRepository extends JpaRepository<ProductOptionValue, Long> {

	@Query("select p from ProductOptionValue p where p.id = ?1")
	ProductOptionValue findOne(Long id);
	


	
	@Query("select p from ProductOptionValue p  where  p.code = ?1")
	public ProductOptionValue findByCode( String optionValueCode);
	
	@Query("select p from ProductOptionValue p  where p.name like %?1% ")
	public List<ProductOptionValue> findByName( String name);
	
	
	@Query("select distinct p from ProductOptionValue p where p.productOptionDisplayOnly = ?1")
	public List<ProductOptionValue> findByReadOnly( boolean readOnly);
	

}
