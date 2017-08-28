package com.shopping.core.business.repositories.catalog.product;


import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.catalog.product.ProductCriteria;
import com.shopping.core.model.catalog.product.ProductList;
import com.shopping.core.model.catalog.product.attribute.AttributeCriteria;




public class ProductRepositoryImpl implements ProductRepositoryCustom {

	
    @PersistenceContext
    private EntityManager em;
    
	@Override
	public Product getById(Long productId) {
		
		try {
			


			StringBuilder qs = new StringBuilder();
			qs.append("select distinct p from Product as p ");
			//qs.append("join fetch p.availabilities pa ");
			//qs.append("join fetch p.merchantStore merch ");
			//qs.append("join fetch p.descriptions pd ");
			qs.append("left join fetch p.categories categs ");
			//qs.append("left join fetch pa.prices pap ");
			//qs.append("left join fetch pap.descriptions papd ");
			//qs.append("left join fetch categs.descriptions categsd ");
			
			//images
			qs.append("left join fetch p.images images ");
			//options
			qs.append("left join fetch p.attributes pattr ");
			qs.append("left join fetch pattr.productOption po ");
			//qs.append("left join fetch po.descriptions pod ");
			qs.append("left join fetch pattr.productOptionValue pov ");
			//qs.append("left join fetch pov.descriptions povd ");
			qs.append("left join fetch p.relationships pr ");
			//other lefts
			qs.append("left join fetch p.manufacturer manuf ");
			//qs.append("left join fetch manuf.descriptions manufd ");
			//qs.append("left join fetch p.type type ");
			//qs.append("left join fetch p.taxClass tx ");
			
			qs.append("where p.id=:pid");
	
	
	    	String hql = qs.toString();
			Query q = this.em.createQuery(hql);
	
	    	q.setParameter("pid", productId);
	
	
	    	Product p = (Product)q.getSingleResult();
	
	
			return p;
		
		} catch(javax.persistence.NoResultException ers) {
			return null;
		}
		
	}
    
	
	@Override
	public Product getByCode(String productCode) {
		
		try {
			


			StringBuilder qs = new StringBuilder();
			qs.append("select distinct p from Product as p ");
			/*qs.append("join fetch p.availabilities pa ");
			qs.append("join fetch p.descriptions pd ");
			qs.append("join fetch p.merchantStore pm ");
			qs.append("left join fetch pa.prices pap ");
			qs.append("left join fetch pap.descriptions papd ");*/
			
			
			
			
			//images
			qs.append("left join fetch p.images images ");
			//options
			qs.append("left join fetch p.attributes pattr ");
			qs.append("left join fetch pattr.productOption po ");
			qs.append("left join fetch pattr.productOptionValue pov ");
			//qs.append("left join fetch pov.descriptions povd ");
			qs.append("left join fetch p.relationships pr ");
			//other lefts
			qs.append("left join fetch p.manufacturer manuf ");
			//qs.append("left join fetch p.type type ");
			//qs.append("left join fetch p.taxClass tx ");
			
			qs.append("where p.sku=:code ");
			//qs.append("and pd.language.id=:lang and papd.language.id=:lang");
			//this cannot be done on child elements from left join
			//qs.append("and pod.languageId=:lang and povd.languageId=:lang");

	    	String hql = qs.toString();
			Query q = this.em.createQuery(hql);

	    	q.setParameter("code", productCode);
	    	//q.setParameter("lang", language.getId());

	    	Product p = (Product)q.getSingleResult();


			return p;
		
		} catch(javax.persistence.NoResultException ers) {
			return null;
		}
		
	}
	@Override
    public Product getByFriendlyUrl(String seUrl) {
		
		
		/*List regionList = new ArrayList();
		regionList.add("*");
		regionList.add(locale.getCountry());*/
		

		StringBuilder qs = new StringBuilder();
		qs.append("select distinct p from Product as p ");
		/*qs.append("join fetch p.availabilities pa ");
		qs.append("join fetch p.descriptions pd ");
		qs.append("join fetch p.merchantStore pm ");
		qs.append("left join fetch pa.prices pap ");
		qs.append("left join fetch pap.descriptions papd ");*/
		
		
		//images
		qs.append("left join fetch p.images images ");
		//options
		qs.append("left join fetch p.attributes pattr ");
		qs.append("left join fetch pattr.productOption po ");
		//qs.append("left join fetch po.descriptions pod ");
		qs.append("left join fetch pattr.productOptionValue pov ");
		//qs.append("left join fetch pov.descriptions povd ");
		qs.append("left join fetch p.relationships pr ");
		//other lefts
		qs.append("left join fetch p.manufacturer manuf ");
		//qs.append("left join fetch manuf.descriptions manufd ");
		//qs.append("left join fetch p.type type ");
		//qs.append("left join fetch p.taxClass tx ");
		
		//qs.append("where pa.region in (:lid) ");
		qs.append("where pd.seUrl=:seUrl ");
		qs.append("and p.available=true and p.dateAvailable<=:dt ");
		qs.append("order by pattr.productOptionSortOrder ");


    	String hql = qs.toString();
		Query q = this.em.createQuery(hql);


    	//q.setParameter("lid", regionList);
    	q.setParameter("dt", new Date());
    	q.setParameter("seUrl", seUrl);

    	Product p = null;
    	
    	try {
    		p = (Product)q.getSingleResult();
    	} catch(javax.persistence.NoResultException ignore) {

    	}
    			
    			


		return p;
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Product> getProductsListByCategories(Set categoryIds) {
		

		//List regionList = new ArrayList();
		//regionList.add("*");
		//regionList.add(locale.getCountry());
		

		//TODO Test performance 
		/**
		 * Testing in debug mode takes a long time with this query
		 * running in normal mode is fine
		 */

		
		StringBuilder qs = new StringBuilder();
		qs.append("select distinct p from Product as p ");
		/*qs.append("join fetch p.merchantStore merch ");
		qs.append("join fetch p.availabilities pa ");
		qs.append("left join fetch pa.prices pap ");
		
		qs.append("join fetch p.descriptions pd ");*/
		qs.append("join fetch p.categories categs ");
		
		
		
		//qs.append("left join fetch pap.descriptions papd ");
		
		
		//images
		qs.append("left join fetch p.images images ");
		
		//options (do not need attributes for listings)
		qs.append("left join fetch p.attributes pattr ");
		qs.append("left join fetch pattr.productOption po ");
		//qs.append("left join fetch po.descriptions pod ");
		qs.append("left join fetch pattr.productOptionValue pov ");
		//qs.append("left join fetch pov.descriptions povd ");
		
		//other lefts
		qs.append("left join fetch p.manufacturer manuf ");
		//qs.append("left join fetch p.type type ");
	//	qs.append("left join fetch p.taxClass tx ");
		
		//qs.append("where pa.region in (:lid) ");
		qs.append("where categs.id in (:cid)");



    	String hql = qs.toString();
		Query q = this.em.createQuery(hql);

    	q.setParameter("cid", categoryIds);


    	
    	@SuppressWarnings("unchecked")
		List<Product> products =  q.getResultList();

    	
    	return products;


	}
	
	@Override
	public ProductList listBySearch( ProductCriteria criteria) {

		ProductList productList = new ProductList();

        
		StringBuilder countBuilderSelect = new StringBuilder();
		countBuilderSelect.append("select count(distinct p) from Product as p");
		
		StringBuilder countBuilderWhere = new StringBuilder();
		countBuilderWhere.append(" where p.id=p.id");
		
		if(!CollectionUtils.isEmpty(criteria.getProductIds())) {
			countBuilderWhere.append(" and p.id in (:pId)");
		}
		
		//countBuilderSelect.append(" inner join p.descriptions pd");
		//countBuilderWhere.append(" and pd.language.id=:lang");

		if(!StringUtils.isBlank(criteria.getProductName())) {
			countBuilderWhere.append(" and lower(pd.name) like:nm");
		}
		
		
		if(!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
			countBuilderSelect.append(" INNER JOIN p.categories categs");
			countBuilderWhere.append(" and categs.id in (:cid)");
		}
		
		if(criteria.getManufacturerId()!=null) {
			countBuilderSelect.append(" INNER JOIN p.manufacturer manuf");
			countBuilderWhere.append(" and manuf.id = :manufid");
		}
		
		if(!StringUtils.isBlank(criteria.getCode())) {
			
			countBuilderWhere.append(" and lower(p.sku) like :sku");
		}
		
		if(!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
		
			countBuilderSelect.append(" INNER JOIN p.attributes pattr");
			countBuilderSelect.append(" INNER JOIN pattr.productOption po");
			countBuilderSelect.append(" INNER JOIN pattr.productOptionValue pov ");
			//countBuilderSelect.append(" INNER JOIN pov.descriptions povd ");
			int count = 0;
			for(AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
				if(count==0) {
					countBuilderWhere.append(" and po.code =:").append(attributeCriteria.getAttributeCode());
					//countBuilderWhere.append(" and povd.description like :").append("val").append(count).append(attributeCriteria.getAttributeCode());
				} 
				count++;
			}
			//countBuilderWhere.append(" and povd.language.id=:lang");

		}
		
		
		if(criteria.getAvailable()!=null) {
			if(criteria.getAvailable().booleanValue()) {
				countBuilderWhere.append(" and p.available=true and p.dateAvailable<=:dt");
			} else {
				countBuilderWhere.append(" and p.available=false or p.dateAvailable>:dt");
			}
		}

		Query countQ = this.em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());

		//countQ.setParameter("mId", store.getId());
		
		
		if(!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
			countQ.setParameter("cid", criteria.getCategoryIds());
		}
		

		if(criteria.getAvailable()!=null) {
			countQ.setParameter("dt", new Date());
		}
		
		if(!StringUtils.isBlank(criteria.getCode())) {
			countQ.setParameter("sku", new StringBuilder().append("%").append(criteria.getCode().toLowerCase()).append("%").toString());
		}
		
		if(criteria.getManufacturerId()!=null) {
			countQ.setParameter("manufid", criteria.getManufacturerId());
		}
		
		if(!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
			int count = 0;
			for(AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
				countQ.setParameter(attributeCriteria.getAttributeCode(),attributeCriteria.getAttributeCode());
				countQ.setParameter("val" + count + attributeCriteria.getAttributeCode(),"%" + attributeCriteria.getAttributeValue() + "%");
				count ++;
			}
		}
		
	//	countQ.setParameter("lang", language.getId());
		
		if(!StringUtils.isBlank(criteria.getProductName())) {
			countQ.setParameter("nm", new StringBuilder().append("%").append(criteria.getProductName().toLowerCase()).append("%").toString());
		}
		
		if(!CollectionUtils.isEmpty(criteria.getProductIds())) {
			countQ.setParameter("pId", criteria.getProductIds());
		}

		Number count = (Number) countQ.getSingleResult ();

		productList.setTotalCount(count.intValue());
		
        if(count.intValue()==0)
        	return productList;

		
		StringBuilder qs = new StringBuilder();
		qs.append("select distinct p from Product as p ");
		//qs.append("join fetch p.merchantStore merch ");
		//qs.append("join fetch p.availabilities pa ");
		//qs.append("left join fetch pa.prices pap ");
		
		//qs.append("join fetch p.descriptions pd ");
		qs.append("left join fetch p.categories categs ");
		

		//images
		qs.append("left join fetch p.images images ");
		

		//other lefts
		qs.append("left join fetch p.manufacturer manuf ");
		//qs.append("left join fetch manuf.descriptions manufd ");
		//qs.append("left join fetch p.type type ");
		//qs.append("left join fetch p.taxClass tx ");
		
		
		//attributes
		if(!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
			qs.append(" inner join p.attributes pattr");
			qs.append(" inner join pattr.productOption po");
			//qs.append(" inner join po.descriptions pod");
			qs.append(" inner join pattr.productOptionValue pov ");
			//qs.append(" inner join pov.descriptions povd");
		} else {
			qs.append(" left join fetch p.attributes pattr");
			qs.append(" left join fetch pattr.productOption po");
			//qs.append(" left join fetch po.descriptions pod");
			qs.append(" left join fetch pattr.productOptionValue pov");
			//qs.append(" left join fetch pov.descriptions povd");
		}
		
		qs.append(" left join fetch p.relationships pr");
		

		qs.append(" where p.id=p.id");
	//	qs.append(" and pd.language.id=:lang");
		
		if(!CollectionUtils.isEmpty(criteria.getProductIds())) {
			qs.append(" and p.id in (:pId)");
		}
		
		if(!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
			qs.append(" and categs.id in (:cid)");
		}
		
		if(criteria.getManufacturerId()!=null) {
			qs.append(" and manuf.id = :manufid");
		}
		

		if(criteria.getAvailable()!=null) {
			if(criteria.getAvailable().booleanValue()) {
				qs.append(" and p.available=true and p.dateAvailable<=:dt");
			} else {
				qs.append(" and p.available=false and p.dateAvailable>:dt");
			}
		}
		
		if(!StringUtils.isBlank(criteria.getProductName())) {
			qs.append(" and lower(pd.name) like :nm");
		}
		
		if(!StringUtils.isBlank(criteria.getCode())) {
			qs.append(" and lower(p.sku) like :sku");
		}
		
		if(!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
			int cnt = 0;
			for(AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
				qs.append(" and po.code =:").append(attributeCriteria.getAttributeCode());
				//qs.append(" and povd.description like :").append("val").append(cnt).append(attributeCriteria.getAttributeCode());
				cnt++;
			}
			//qs.append(" and povd.language.id=:lang");

		}
		qs.append(" order by p.sortOrder asc");


    	String hql = qs.toString();
		Query q = this.em.createQuery(hql);


    	//q.setParameter("lang", language.getId());
    	//q.setParameter("mId", store.getId());
    	
    	if(!CollectionUtils.isEmpty(criteria.getCategoryIds())) {
    		q.setParameter("cid", criteria.getCategoryIds());
    	}
    	
		if(!CollectionUtils.isEmpty(criteria.getProductIds())) {
			q.setParameter("pId", criteria.getProductIds());
		}
		
		if(criteria.getAvailable()!=null) {
			q.setParameter("dt", new Date());
		}
		
		if(criteria.getManufacturerId()!=null) {
			q.setParameter("manufid", criteria.getManufacturerId());
		}
		
		if(!StringUtils.isBlank(criteria.getCode())) {
			q.setParameter("sku", new StringBuilder().append("%").append(criteria.getCode().toLowerCase()).append("%").toString());
		}
		
		if(!CollectionUtils.isEmpty(criteria.getAttributeCriteria())) {
			int cnt = 0;
			for(AttributeCriteria attributeCriteria : criteria.getAttributeCriteria()) {
				q.setParameter(attributeCriteria.getAttributeCode(),attributeCriteria.getAttributeCode());
				q.setParameter("val" + cnt  + attributeCriteria.getAttributeCode(),"%" + attributeCriteria.getAttributeValue() + "%");
				cnt++;
			}
		}
		
		if(!StringUtils.isBlank(criteria.getProductName())) {
			q.setParameter("nm", new StringBuilder().append("%").append(criteria.getProductName().toLowerCase()).append("%").toString());
		}
    	
    	if(criteria.getMaxCount()>0) {
    		
    		
	    	q.setFirstResult(criteria.getStartIndex());
	    	if(criteria.getMaxCount()<count.intValue()) {
	    		q.setMaxResults(criteria.getMaxCount());
	    	}
	    	else {
	    		q.setMaxResults(count.intValue());
	    	}
    	}
    	
    	@SuppressWarnings("unchecked")
		List<Product> products =  q.getResultList();
    	productList.setProducts(products);
    	
    	return productList;

		
		
		
	}


	@Override
	public boolean existsSKU(String sku) {
		// TODO Auto-generated method stub
		StringBuilder countBuilderSelect = new StringBuilder();
		countBuilderSelect.append("select count(distinct p) from Product as p");
		
		StringBuilder countBuilderWhere = new StringBuilder();
		countBuilderWhere.append(" where p.sku=:sku");
		Query countQ = this.em.createQuery(
				countBuilderSelect.toString() + countBuilderWhere.toString());
		countQ.setParameter("sku",sku);
		Number count = (Number) countQ.getSingleResult ();
		if(count.intValue()>0){
			return true;
		}
		return false;
	}

	
}
