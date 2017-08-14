package com.shopping.core.business.services.catalog.category;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.shopping.core.business.repositories.catalog.category.CategoryRepository;
//import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.shopping.core.model.catalog.category.Category;

//import com.salesmanager.core.model.catalog.product.Product;

import com.shopping.core.business.exception.ServiceException;

@Service("categoryService")
@Transactional(rollbackFor = com.shopping.core.business.exception.ServiceException.class)
public class CategoryServiceImpl implements CategoryService {
	
	 @Autowired
	 private CategoryRepository categoryRepository;
	
	 @PersistenceContext
	 private EntityManager em;
	/*  
	 @Inject
	 private ProductService productService;
	  */

	
	/*@Inject
	public CategoryServiceImpl(CategoryRepository categoryRepository) {
		super(categoryRepository);
		this.categoryRepository = categoryRepository;
	}*/
	
	public void create(Category category)  {
		
		categoryRepository.saveAndFlush(category);
		
		StringBuilder lineage = new StringBuilder();
		Category parent = category.getParent();
		if(parent!=null && parent.getId()!=null && parent.getId()!=0) {
			lineage.append(parent.getLineage()).append("/").append(parent.getId());
			category.setDepth(parent.getDepth()+1);
		} else {
			lineage.append("/");
			category.setDepth(0);
		}
		category.setLineage(lineage.toString());
		categoryRepository.saveAndFlush(category);
		
		
	}
	
	@Override
	public List<Object[]> countProductsByCategories(
			List<Integer> categoryIds)  {
		
		return categoryRepository.countProductsByCategories(categoryIds);
		
	}
	

	@Override
	public List<Category> listByIds(List<Integer> ids) {
		return categoryRepository.findByIds(ids);
	}
	
	@Override
	public void saveOrUpdate(Category category) throws ServiceException  {
		try {
		
		
		
		//save or update (persist and attach entities
		if(category.getId()!=null && category.getId()>0) {

			categoryRepository.saveAndFlush(category);
			
		} else {
			
			categoryRepository.saveAndFlush(category);
			
		}
		//ajust lineage and depth
		if(category.getParent()!=null && category.getParent().getId()!=-1) { 
		
			Category parent = new Category();
			parent.setId(category.getParent().getId());
			addChild(parent, category);
		
		}
		
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}

	@Override
	public List<Category> listByLineage( String lineage) throws ServiceException {
		try {
			return categoryRepository.findByLineage(lineage);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	@Override
	public List<Category> listBySeUrl( String seUrl)throws ServiceException  {
		
		try {
			return categoryRepository.listByFriendlyUrl(seUrl);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
	}
	@Override
	public List<Category> listAll()
			throws ServiceException {

		try {
			return categoryRepository.findAll();
		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	@Override
	public Category getBySeUrl(String seUrl) {
		return categoryRepository.findByFriendlyUrl(seUrl);
	}
	

	@Override
	public Category getById(Integer id) {

			return categoryRepository.findOne(id);
		
	}
	
	
	@Override
	public List<Category> listByParent(Category category) {
		Assert.notNull(category, "Category cannot be null");
		return categoryRepository.findByParent(category.getId());
	}


	
	//@Override
	public void delete(Category category)  {
		
		/*//get category with lineage (subcategories)
		StringBuilder lineage = new StringBuilder();
		lineage.append(category.getLineage()).append(category.getId()).append(Constants.SLASH);
		List<Category> categories = this.listByLineage(lineage.toString());
		
		Category dbCategory = this.getById( category.getId() );
		
		
		if(dbCategory != null && dbCategory.getId().longValue() == category.getId().longValue() ) {			
			
			
			categories.add(dbCategory);
			
			
			Collections.reverse(categories);
			
			List<Long> categoryIds = new ArrayList<Long>();
	
				
			for(Category c : categories) {		
					categoryIds.add(c.getId());
			}

			List<Product> products = productService.getProducts(categoryIds);
			org.hibernate.Session session = em.unwrap(org.hibernate.Session.class);//need to refresh the session to update all product categories

			
			for(Product product : products) {
				session.evict(product);//refresh product so we get all product categories
				Product dbProduct = productService.getById(product.getId());
				Set<Category> productCategories = dbProduct.getCategories();
				if(productCategories.size()>1) {
					for(Category c : categories) {
						productCategories.remove(c);
						productService.update(dbProduct);
					}
					
					if(product.getCategories()==null || product.getCategories().size()==0) {
						productService.delete(dbProduct);
					}
					
				} else {
					productService.delete(dbProduct);
				}
				
				
			}
			
			Category categ = this.getById(category.getId());
			categoryRepository.delete(categ);
			
		}*/
		
	}




	
	@Override
	public void addChild(Category parent, Category child) throws ServiceException  {
		
		try {
			
			if(parent==null) {
				
				//assign to root
				child.setParent(null);
				child.setDepth(0);
				//child.setLineage(new StringBuilder().append("/").append(child.getId()).append("/").toString());
				child.setLineage("/");
				
			} else {
				
				Category p = this.getById(parent.getId());//parent
				
				

				
				String lineage = p.getLineage();
				int depth = p.getDepth();//TODO sometimes null
				
				child.setParent(p);
				child.setDepth(depth+1);
				child.setLineage(new StringBuilder().append(lineage).append(p.getId()).append("/").toString());
				
				
			}
			

			categoryRepository.save(child);
			StringBuilder childLineage = new StringBuilder();
			childLineage.append(child.getLineage()).append(child.getId()).append("/");
			List<Category> subCategories = listByLineage(childLineage.toString());
			
			
			//ajust all sub categories lineages
			if(subCategories!=null && subCategories.size()>0) {
				for(Category subCategory : subCategories) {
					if(child.getId()!=subCategory.getId()) {
						addChild(child, subCategory);
					}
				}
				
			}
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		

	}
	
	@Override
	public List<Category> listByDepth( int depth) {
		return categoryRepository.findByDepth( depth);
	}
	

	@Override
	public List<Category> getByName( String name)  throws ServiceException  {
		
		try {
			return categoryRepository.findByName( name);
		} catch (Exception e) {
			throw new ServiceException(e);
		}
		
		
	}
	
	


	
	


}
