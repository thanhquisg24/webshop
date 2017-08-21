package com.shopping.core.business.repositories.catalog.product.attribute;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.shopping.core.model.catalog.product.attribute.ProductAttribute;

public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, Long> {

	@Query("select p from ProductAttribute p join fetch p.product pr left join fetch p.productOption po left join fetch p.productOptionValue pov  where p.id = ?1")
	ProductAttribute findOne(Long id);
	
	@Query("select p from ProductAttribute p join fetch p.product pr left join fetch p.productOption po left join fetch p.productOptionValue pov  where  po.id = ?1")
	List<ProductAttribute> findByOptionId( Long id);
	
	@Query("select p from ProductAttribute p join fetch p.product pr left join fetch p.productOption po left join fetch p.productOptionValue pov where  pov.id = ?1")
	List<ProductAttribute> findByOptionValueId( Long id);
	
	@Query("select p from ProductAttribute p join fetch p.product pr left join fetch p.productOption po left join fetch p.productOptionValue pov  where  pr.id = ?1 and p.id in ?2")
	List<ProductAttribute> findByAttributeIds( Long productId, List<Long> ids);
	
	@Query("select p from ProductAttribute p join fetch p.product pr left join fetch p.productOption po left join fetch p.productOptionValue pov  where  pr.id = ?1 ")
	List<ProductAttribute> findByProductId( Long productId);
}
