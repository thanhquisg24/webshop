package com.shopping.core.business.repositories.catalog.category;

import java.util.List;

import com.shopping.core.model.catalog.category.Category;


public interface CategoryRepositoryCustom {

	List<Object[]> countProductsByCategories(
			List<Integer> categoryIds);

	List<Category> listByStoreAndParent( Category category);

}
