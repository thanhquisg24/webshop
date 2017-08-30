package com.shopping.core.model.catalog.product.image;

import java.io.InputStream;
import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotEmpty;

import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.catalog.product.Product;
import com.shopping.core.model.generic.SalesManagerEntity;

@Entity
@Table(name = "PRODUCT_IMAGE", schema=SchemaConstant.SALESMANAGER_SCHEMA)
public class ProductImage extends SalesManagerEntity<Long, ProductImage> {
	private static final long serialVersionUID = 247514890386076337L;
	
	@Id
	@Column(name = "PRODUCT_IMAGE_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_IMG_SEQ_NEXT_VAL",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name="NAME", nullable = false, length=120)
	private String name;
	
	@Column(name="TITLE", length=100)
	private String title;
	
	@Column(name="DESCRIPTION",columnDefinition = "TEXT")
	private String description;
	
	
	@Column(name = "PRODUCT_IMAGE")
	private String productImage;
	
	@Column(name = "DEFAULT_IMAGE")
	private boolean defaultImage = true;
	
	
	@Column(name="ALT_TAG", length=100)
	private String altTag;
	
	
	
	/**
	 * default to 0 for images managed by the system
	 */

	/**
	 * Refers to images not accessible through the system. It may also be a video.
	 */
	@Column(name = "PRODUCT_IMAGE_URL")
	private String productImageUrl;
	
	@Column(name = "DISPLAY_ORDER")
	private int displayOrder ;
	

	
	@ManyToOne(targetEntity = Product.class)
	@JoinColumn(name = "PRODUCT_ID", nullable = false)
	private Product product;
	
	@Transient
	private InputStream image = null;
	
	//private MultiPartFile image

	public ProductImage(){
	}

	public String getProductImage() {
		return productImage;
	}

	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}

	public boolean isDefaultImage() {
		return defaultImage;
	}

	public void setDefaultImage(boolean defaultImage) {
		this.defaultImage = defaultImage;
	}



	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	

	public InputStream getImage() {
		return image;
	}

	public void setImage(InputStream image) {
		this.image = image;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAltTag() {
		return altTag;
	}

	public void setAltTag(String altTag) {
		this.altTag = altTag;
	}

	public int getDisplayOrder() {
		return displayOrder;
	}

	public void setDisplayOrder(int displayOrder) {
		this.displayOrder = displayOrder;
	}


}
