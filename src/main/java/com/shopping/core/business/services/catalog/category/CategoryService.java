package com.shopping.core.business.services.catalog.category;

import java.util.List;

import com.shopping.core.business.exception.ServiceException;
import com.shopping.core.model.catalog.category.Category;


public interface CategoryService  {

	List<Category> listByLineage( String lineage)throws ServiceException ;
	
	List<Category> listBySeUrl(String seoUrl) throws ServiceException  ;
	Category getBySeUrl(String seUrl);
	
	void addChild(Category parent, Category child) throws ServiceException ;

	List<Category> listByParent(Category category) ;
	

	List<Category> getByName( String name) throws ServiceException ;
	

	void saveOrUpdate(Category category)  throws ServiceException ;

	List<Category> listByDepth( int depth);

	/**
	 * Get root categories by store for a given language
	 * @param store
	 * @param depth
	 * @param language
	 * @return
	 */


	List<Object[]> countProductsByCategories(
			List<Integer> categoryIds) ;

	/**
	 * Returns a list of Category by category code for a given language
	 * @param store
	 * @param codes
	 * @param language
	 * @return
	 */

	/**
	 * List of Category by id
	 * @param store
	 * @param ids
	 * @param language
	 * @return
	 */
	List<Category> listByIds( List<Integer> ids);

	Category getById(Integer id);

	List<Category> listAll() throws ServiceException;



}
