package com.shopping.core.business.repositories.catalog.manufacture;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.shopping.core.model.catalog.product.manufacturer.Manufacturer;



public interface ManufacturerRepository extends JpaRepository<Manufacturer, Long>{
	@Query("select count(distinct p) from Product as p where p.manufacturer.id=?1")
	Long countByProduct(Long manufacturerId);
	
	
	@Query("select distinct manufacturer from Product as p join p.manufacturer manufacturer join p.categories categs where categs.id in (?1)")
	List<Manufacturer> findByCategories(List<Long> categoryIds);
	
	@Query("select m from Manufacturer m  where m.code=?1 ")
	Manufacturer findByCode(String code);

	@Query("select m from Manufacturer m  where m.name like %:manufacture_name%")
	Page<Manufacturer> findAll(@Param("manufacture_name") String manufacture_name, Pageable pageRequest);
}
