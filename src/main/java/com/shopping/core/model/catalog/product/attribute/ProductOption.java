package com.shopping.core.model.catalog.product.attribute;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;
import org.hibernate.validator.constraints.NotEmpty;
import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.generic.SalesManagerEntity;



@Entity
@Table(name="PRODUCT_OPTION", schema=SchemaConstant.SALESMANAGER_SCHEMA, indexes = { @Index(name="PRD_OPTION_CODE_IDX", columnList = "PRODUCT_OPTION_CODE")}, uniqueConstraints=
	@UniqueConstraint(columnNames = { "PRODUCT_OPTION_CODE"}))
public class ProductOption extends SalesManagerEntity<Long, ProductOption> {
	private static final long serialVersionUID = -2019269055342226086L;
	
	@Id
	@Column(name="PRODUCT_OPTION_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPTION_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name="PRODUCT_OPTION_SORT_ORD")
	private Integer productOptionSortOrder;
	
	@Column(name="PRODUCT_OPTION_TYPE", length=10)
	private String productOptionType;
	
	
	@Column(name="PRODUCT_OPTION_READ")
	private boolean readOnly;
	
	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9_]*$")
	@Column(name="PRODUCT_OPTION_CODE")
	//@Index(name="PRD_OPTION_CODE_IDX")
	private String code;
	
	@Column(name="PRODUCT_OPTION_COMMENT",columnDefinition = "TEXT")
	private String productOptionComment;
	
	@NotEmpty
	@Column(name="NAME", nullable = false, length=120)
	private String name;
	
	@Column(name="TITLE", length=100)
	private String title;
	
	@Column(name="DESCRIPTION",columnDefinition = "TEXT")
	private String description;
	
	public ProductOption() {
	}
	
	public Integer getProductOptionSortOrder() {
		return productOptionSortOrder;
	}
	
	public void setProductOptionSortOrder(Integer productOptionSortOrder) {
		this.productOptionSortOrder = productOptionSortOrder;
	}
	
	public String getProductOptionType() {
		return productOptionType;
	}

	public void setProductOptionType(String productOptionType) {
		this.productOptionType = productOptionType;
	}


	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}




	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setReadOnly(boolean readOnly) {
		this.readOnly = readOnly;
	}

	public boolean isReadOnly() {
		return readOnly;
	}

	public String getProductOptionComment() {
		return productOptionComment;
	}

	public void setProductOptionComment(String productOptionComment) {
		this.productOptionComment = productOptionComment;
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
