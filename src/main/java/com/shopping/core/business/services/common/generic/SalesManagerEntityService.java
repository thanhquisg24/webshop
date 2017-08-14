package com.shopping.core.business.services.common.generic;

import java.io.Serializable;


/**
 * <p>Service racine pour la gestion des entités.</p>
 *
 * @param <T> type d'entité
 */
public interface SalesManagerEntityService<K extends Serializable & Comparable<K>, E extends com.shopping.core.model.generic.SalesManagerEntity<K, ?>> extends TransactionalAspectAwareService{

	
}
