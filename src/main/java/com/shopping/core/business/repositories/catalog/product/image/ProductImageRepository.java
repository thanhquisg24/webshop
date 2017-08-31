package com.shopping.core.business.repositories.catalog.product.image;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import com.shopping.core.model.catalog.product.image.ProductImage;



public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {


	@Query("select distinct p from ProductImage p inner join fetch p.product pp where p.id = ?1")
	ProductImage findOne(Long id);

	@Query("select distinct p from ProductImage p inner join fetch p.product pp where pp.id = ?1")
	List<ProductImage> getImages(Long product);
	
	
}
