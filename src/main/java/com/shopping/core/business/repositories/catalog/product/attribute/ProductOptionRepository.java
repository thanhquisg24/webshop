package com.shopping.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping.core.model.catalog.product.attribute.ProductOption;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {

	@Query("select p from ProductOption p where p.id = ?1")
	ProductOption findOne(Long id);

	
	
	@Query("select p from ProductOption p  where p.name like %?1% ")
	public List<ProductOption> findByName( String name);
	
	@Query("select p from ProductOption p where p.code = ?1")
	public ProductOption findByCode( String optionCode);
	
	@Query("select distinct p from ProductOption p where p.readOnly = ?3")
	public List<ProductOption> findByReadOnly( boolean readOnly);


	@Query("select p from ProductOption p where p.name like %:option_name%")
	Page<ProductOption> findAll(@Param("option_name") String option_name, Pageable pageRequest);
	

}
