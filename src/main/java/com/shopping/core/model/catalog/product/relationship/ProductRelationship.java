package com.shopping.core.model.catalog.product.relationship;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.generic.SalesManagerEntity;


@Entity
@Table(name = "PRODUCT_RELATIONSHIP", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class ProductRelationship extends SalesManagerEntity<Long, ProductRelationship> implements Serializable {
	private static final long serialVersionUID = -9045331138054246299L;
	
	@Id
	@Column(name = "PRODUCT_RELATIONSHIP_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_RELATION_SEQ_NEXT_VAL",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	

	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name="PRODUCT_ID",updatable=false,nullable=true) 
	private Product product = null;
	
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name="RELATED_PRODUCT_ID",updatable=false,nullable=true) 
	private Product relatedProduct = null;
	
	@Column(name="CODE")
	private String code;
	
	@Column(name="ACTIVE")
	private boolean active = true;
	
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getRelatedProduct() {
		return relatedProduct;
	}

	public void setRelatedProduct(Product relatedProduct) {
		this.relatedProduct = relatedProduct;
	}
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}

	public ProductRelationship() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}




}
