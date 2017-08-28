package com.shopping.core.business.repositories.catalog.product;

import org.springframework.data.jpa.repository.JpaRepository;

import com.shopping.core.model.catalog.product.Product;





public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {





}
