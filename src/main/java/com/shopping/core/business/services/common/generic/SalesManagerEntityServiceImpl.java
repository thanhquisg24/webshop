package com.shopping.core.business.services.common.generic;

import java.io.Serializable;

import com.shopping.core.model.generic.SalesManagerEntity;

/**
 * @param <T> entity type
 */
public abstract class SalesManagerEntityServiceImpl<K extends Serializable & Comparable<K>, E extends SalesManagerEntity<K, ?>>
	implements SalesManagerEntityService<K, E> {
	
}