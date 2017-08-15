package com.shopping.core.spec;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.data.jpa.domain.Specification;

import com.shopping.core.utils.SpecSearchCriteria;



public class CustomSpecification<T> implements Specification<T>{
	
	private SpecSearchCriteria criteria;
	
	public SpecSearchCriteria getCriteria() {
		return criteria;
	}
	
	public CustomSpecification(final SpecSearchCriteria criteria) {

		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(final Root<T> root, final CriteriaQuery<?> query, final CriteriaBuilder builder) {
		query.distinct(true);
		switch (criteria.getOperation()) {
		case EQUALITY:
			return builder.equal(root.get(criteria.getKey()), criteria.getValue());
		case NEGATION:
			return builder.notEqual(root.get(criteria.getKey()), criteria.getValue());
		case GREATER_THAN:
			return builder.greaterThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		case LESS_THAN:
			return builder.lessThan(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		case LIKE:
			return builder.like(root.<String>get(criteria.getKey()), criteria.getValue().toString());
		case STARTS_WITH:
			return builder.like(root.<String>get(criteria.getKey()), criteria.getValue() + "%");
		case ENDS_WITH:
			return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue());
		case CONTAINS:
			return builder.like(root.<String>get(criteria.getKey()), "%" + criteria.getValue() + "%");
		default:
			return null;
		}
	}
	

}
