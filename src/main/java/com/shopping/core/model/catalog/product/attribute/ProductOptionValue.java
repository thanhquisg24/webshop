package com.shopping.core.model.catalog.product.attribute;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;

import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.springframework.web.multipart.MultipartFile;
import org.hibernate.annotations.Type;
import org.hibernate.validator.constraints.NotEmpty;

import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.generic.SalesManagerEntity;



@Entity
@Table(name="PRODUCT_OPTION_VALUE", schema=SchemaConstant.SALESMANAGER_SCHEMA, indexes = { @Index(name="PRD_OPTION_VAL_CODE_IDX", columnList = "PRODUCT_OPTION_VAL_CODE")}, uniqueConstraints=
	@UniqueConstraint(columnNames = { "PRODUCT_OPTION_VAL_CODE"}))
public class ProductOptionValue extends SalesManagerEntity<Long, ProductOptionValue> {
	private static final long serialVersionUID = 3736085877929910891L;

	@Id
	@Column(name="PRODUCT_OPTION_VALUE_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPT_VAL_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name="PRODUCT_OPT_VAL_SORT_ORD")
	private Integer productOptionValueSortOrder;
	
	@Column(name="PRODUCT_OPT_VAL_IMAGE")
	private String productOptionValueImage;
	
	@Column(name="PRODUCT_OPT_FOR_DISP")
	private boolean productOptionDisplayOnly=false;
	
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9_]*$")
	@Column(name="PRODUCT_OPTION_VAL_CODE")
	private String code;
	
	
	@NotEmpty
	@Column(name="NAME", nullable = false, length=120)
	private String name;
	
	@Column(name="TITLE", length=100)
	private String title;
	
	@Column(name="DESCRIPTION",columnDefinition = "TEXT")
	private String description;
	
	
	@Transient
	private MultipartFile image = null;
	
	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	

	public ProductOptionValue() {
	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public Integer getProductOptionValueSortOrder() {
		return productOptionValueSortOrder;
	}

	public void setProductOptionValueSortOrder(Integer productOptionValueSortOrder) {
		this.productOptionValueSortOrder = productOptionValueSortOrder;
	}

	public String getProductOptionValueImage() {
		return productOptionValueImage;
	}

	public void setProductOptionValueImage(String productOptionValueImage) {
		this.productOptionValueImage = productOptionValueImage;
	}




	public boolean isProductOptionDisplayOnly() {
		return productOptionDisplayOnly;
	}

	public void setProductOptionDisplayOnly(boolean productOptionDisplayOnly) {
		this.productOptionDisplayOnly = productOptionDisplayOnly;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
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




}
