package com.shopping.core.business.repositories.catalog.category;

import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.shopping.core.model.catalog.category.Category;



public class CategoryRepositoryImpl implements CategoryRepositoryCustom {
	
	@PersistenceContext
    private EntityManager em;
	
	@Override
	public List<Object[]> countProductsByCategories( List<Integer> categoryIds) {

		
		StringBuilder qs = new StringBuilder();
		qs.append("select categories, count(product.id) from Product product ");
		qs.append("inner join product.categories categories ");
		qs.append("where categories.id in (:cid) ");
		qs.append("and product.available=true and product.dateAvailable<=:dt ");
		qs.append("group by categories.id");
		
    	String hql = qs.toString();
		Query q = this.em.createQuery(hql);

    	q.setParameter("cid", categoryIds);
    	q.setParameter("dt", new Date());


    	
    	@SuppressWarnings("unchecked")
		List<Object[]> counts =  q.getResultList();

    	
    	return counts;
		
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Category> listByStoreAndParent( Category category) {
		
		StringBuilder queryBuilder = new StringBuilder();
		queryBuilder.append("select c from Category c  ");
		
		
			if (category == null) {
				//query.from(qCategory)
				queryBuilder.append(" where c.parent IsNull ");
					//.where(qCategory.parent.isNull())
					//.orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			} else {
				//query.from(qCategory)
				queryBuilder.append(" join fetch c.parent cp where cp.id =:cid ");
					//.where(qCategory.parent.eq(category))
					//.orderBy(qCategory.sortOrder.asc(),qCategory.id.desc());
			}
		 
		queryBuilder.append(" order by c.sortOrder asc");
		
    	String hql = queryBuilder.toString();
		Query q = this.em.createQuery(hql);

    	q.setParameter("cid", category.getId());
    	
    	
		
		return q.getResultList();
	}

}
