package com.shopping.core.model.catalog.product;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Cascade;
import org.hibernate.validator.constraints.NotEmpty;

import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.catalog.category.Category;
import com.shopping.core.model.common.audit.AuditListener;
import com.shopping.core.model.common.audit.AuditSection;
import com.shopping.core.model.common.audit.Auditable;
import com.shopping.core.model.generic.SalesManagerEntity;
import com.shopping.core.model.catalog.product.attribute.ProductAttribute;
import com.shopping.core.model.catalog.product.image.ProductImage;
import com.shopping.core.model.catalog.product.manufacturer.Manufacturer;
import com.shopping.core.model.catalog.product.relationship.ProductRelationship;



@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT", schema=SchemaConstant.SALESMANAGER_SCHEMA, uniqueConstraints=
@UniqueConstraint(columnNames = { "SKU"}))
public class Product extends SalesManagerEntity<Long, Product> implements Auditable {
	private static final long serialVersionUID = -6228066416290007047L;

	@Id
	@Column(name = "PRODUCT_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_SEQ_NEXT_VAL",initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Embedded
	private AuditSection auditSection = new AuditSection();



	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductAttribute> attributes = new HashSet<ProductAttribute>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "product")//cascade is set to remove because product save requires logic to create physical image first and then save the image id in the database, cannot be done in cascade
	private Set<ProductImage> images = new HashSet<ProductImage>();

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "product")
	private Set<ProductRelationship> relationships = new HashSet<ProductRelationship>();

	

	
	@ManyToMany(fetch=FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinTable(name = "PRODUCT_CATEGORY", schema=SchemaConstant.SALESMANAGER_SCHEMA, joinColumns = { 
			@JoinColumn(name = "PRODUCT_ID", nullable = false, updatable = false) }
			, 
			inverseJoinColumns = { @JoinColumn(name = "CATEGORY_ID", 
					nullable = false, updatable = false) }
	)
	@Cascade({
		org.hibernate.annotations.CascadeType.DETACH,
		org.hibernate.annotations.CascadeType.LOCK,
		org.hibernate.annotations.CascadeType.REFRESH,
		org.hibernate.annotations.CascadeType.REPLICATE
		
	})
	private Set<Category> categories = new HashSet<Category>();
	
	@Column(name="DATE_AVAILABLE")
	@Temporal(TemporalType.TIMESTAMP)
	private Date dateAvailable = new Date();
	
	
	@Column(name="IS_ACTIVE")
	private boolean active = true;
	


	@Column(name="IS_feature")
	private boolean feature = false;
	

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name="MANUFACTURER_ID", nullable=true)
	private Manufacturer manufacturer;


	@Column(name="PRODUCT_TYPE")//0 single 1 variant
	private boolean type =false;
	

	@Column(name = "IS_FREE_SHIPPING")
	private boolean product_is_free_shipping;

	@Column(name = "PRODUCT_LENGTH")
	private BigDecimal productLength;

	@Column(name = "PRODUCT_WIDTH")
	private BigDecimal productWidth;

	@Column(name = "PRODUCT_HEIGHT")
	private BigDecimal productHeight;

	@Column(name = "PRODUCT_WEIGHT")
	private BigDecimal productWeight;

	@Column(name = "REVIEW_AVG")
	private BigDecimal productReviewAvg;

	@Column(name = "REVIEW_COUNT")
	private Integer productReviewCount;
	
	/* stocck*/
	@Column(name = "STOCK_QUANTITY")
	private Integer stock_quantity;
	
	@Column(name = "DISPLAY_STOCK_AVAILABILITY")
	private boolean display_stock_availability;
	
	
	@Column(name = "DISPLAY_STOCK_QTY")
	private boolean display_stock_qty;
	

	@Column(name = "MINIMUM_STOCK_QTY")
	private Integer minimum_stock_qty;


	@Column(name = "MINIMUM_CART_QTY")
	private Integer minimum_cart_qty;


	@Column(name = "MAXIMUM_CART_QTY")
	private Integer maximum_cart_qty;
	
	/*end stock*/
	
	/*price*/
	@Column(name = "PRICE")
	private BigDecimal price;
	
	@Column(name = "OLD_PRICE")
	private BigDecimal old_price;
	
	@Column(name = "PRODUCT_COST")
	private BigDecimal product_cost;
	
	
	@Column(name = "IS_DISCOUNT")
	private boolean discount;
	
	@Column(name = "PERCENT_DISCOUNT")
	private float percent_discount =0;
	
	@Column(name = "PRICE_DISCOUNT")
	private BigDecimal price_discount;
	/*end price*/
	
	@Column(name = "SORT_ORDER")
	private Integer sortOrder = new Integer(0);
	
	
	@NotEmpty
	@Column(name="NAME", nullable = false, length=120)
	private String name;
	
	@Column(name="TITLE", length=100)
	private String title;
	
	@Column(name="SHORT_DESCRIPTION")
	private String short_description;
	
	@Column(name="FULL_DESCRIPTION",columnDefinition = "TEXT")
	private String full_description;
	
	@Column(name = "SEF_URL")
	private String seUrl;

	@Column(name = "META_TITLE")
	private String metatagTitle;

	@Column(name = "META_KEYWORDS")
	private String metatagKeywords;

	@Column(name = "META_DESCRIPTION")
	private String metatagDescription;

	@NotEmpty
	@Pattern(regexp="^[a-zA-Z0-9_]*$")
	@Column(name = "SKU")
	private String sku;
	
	/**
	 * External system reference SKU/ID
	 */
	@Column(name = "REF_SKU")
	private String refSku;

	public Product() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}



	public BigDecimal getProductLength() {
		return productLength;
	}

	public void setProductLength(BigDecimal productLength) {
		this.productLength = productLength;
	}

	public BigDecimal getProductWidth() {
		return productWidth;
	}

	public void setProductWidth(BigDecimal productWidth) {
		this.productWidth = productWidth;
	}

	public BigDecimal getProductHeight() {
		return productHeight;
	}

	public void setProductHeight(BigDecimal productHeight) {
		this.productHeight = productHeight;
	}

	public BigDecimal getProductWeight() {
		return productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public BigDecimal getProductReviewAvg() {
		return productReviewAvg;
	}

	public void setProductReviewAvg(BigDecimal productReviewAvg) {
		this.productReviewAvg = productReviewAvg;
	}

	public Integer getProductReviewCount() {
		return productReviewCount;
	}

	public void setProductReviewCount(Integer productReviewCount) {
		this.productReviewCount = productReviewCount;
	}





	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
	}





	public Set<ProductAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Set<ProductAttribute> attributes) {
		this.attributes = attributes;
	}



	public Manufacturer getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(Manufacturer manufacturer) {
		this.manufacturer = manufacturer;
	}




	public Set<ProductImage> getImages() {
		return images;
	}

	public void setImages(Set<ProductImage> images) {
		this.images = images;
	}

	public Set<ProductRelationship> getRelationships() {
		return relationships;
	}

	public void setRelationships(Set<ProductRelationship> relationships) {
		this.relationships = relationships;
	}


	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}




	public boolean isType() {
		return type;
	}

	public void setType(boolean type) {
		this.type = type;
	}

	public Date getDateAvailable() {
		return dateAvailable;
	}

	public void setDateAvailable(Date dateAvailable) {
		this.dateAvailable = dateAvailable;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}





	
	public ProductImage getProductImage() {
		ProductImage productImage = null;
		if(this.getImages()!=null && this.getImages().size()>0) {
			for(ProductImage image : this.getImages()) {
				productImage = image;
				if(productImage.isDefaultImage()) {
					break;
				}
			}
		}
		return productImage;
	}
	


	public String getRefSku() {
		return refSku;
	}

	public void setRefSku(String refSku) {
		this.refSku = refSku;
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


	public String getSeUrl() {
		return seUrl;
	}

	public void setSeUrl(String seUrl) {
		this.seUrl = seUrl;
	}

	public String getMetatagTitle() {
		return metatagTitle;
	}

	public void setMetatagTitle(String metatagTitle) {
		this.metatagTitle = metatagTitle;
	}

	public String getMetatagKeywords() {
		return metatagKeywords;
	}

	public void setMetatagKeywords(String metatagKeywords) {
		this.metatagKeywords = metatagKeywords;
	}

	public String getMetatagDescription() {
		return metatagDescription;
	}

	public void setMetatagDescription(String metatagDescription) {
		this.metatagDescription = metatagDescription;
	}

	public boolean isProduct_is_free_shipping() {
		return product_is_free_shipping;
	}

	public void setProduct_is_free_shipping(boolean product_is_free_shipping) {
		this.product_is_free_shipping = product_is_free_shipping;
	}

	public Integer getStock_quantity() {
		return stock_quantity;
	}

	public void setStock_quantity(Integer stock_quantity) {
		this.stock_quantity = stock_quantity;
	}

	public boolean isDisplay_stock_availability() {
		return display_stock_availability;
	}

	public void setDisplay_stock_availability(boolean display_stock_availability) {
		this.display_stock_availability = display_stock_availability;
	}

	public boolean isDisplay_stock_qty() {
		return display_stock_qty;
	}

	public void setDisplay_stock_qty(boolean display_stock_qty) {
		this.display_stock_qty = display_stock_qty;
	}

	public Integer getMinimum_stock_qty() {
		return minimum_stock_qty;
	}

	public void setMinimum_stock_qty(Integer minimum_stock_qty) {
		this.minimum_stock_qty = minimum_stock_qty;
	}

	public Integer getMinimum_cart_qty() {
		return minimum_cart_qty;
	}

	public void setMinimum_cart_qty(Integer minimum_cart_qty) {
		this.minimum_cart_qty = minimum_cart_qty;
	}

	public Integer getMaximum_cart_qty() {
		return maximum_cart_qty;
	}

	public void setMaximum_cart_qty(Integer maximum_cart_qty) {
		this.maximum_cart_qty = maximum_cart_qty;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}


	public BigDecimal getOld_price() {
		return old_price;
	}

	public void setOld_price(BigDecimal old_price) {
		this.old_price = old_price;
	}

	public BigDecimal getProduct_cost() {
		return product_cost;
	}

	public void setProduct_cost(BigDecimal product_cost) {
		this.product_cost = product_cost;
	}

	public boolean isDiscount() {
		return discount;
	}

	public void setDiscount(boolean discount) {
		this.discount = discount;
	}

	public float getPercent_discount() {
		return percent_discount;
	}

	public void setPercent_discount(float percent_discount) {
		this.percent_discount = percent_discount;
	}

	public BigDecimal getPrice_discount() {
		return price_discount;
	}

	public void setPrice_discount(BigDecimal price_discount) {
		this.price_discount = price_discount;
	}

	public String getShort_description() {
		return short_description;
	}

	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}

	public String getFull_description() {
		return full_description;
	}

	public void setFull_description(String full_description) {
		this.full_description = full_description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isFeature() {
		return feature;
	}

	public void setFeature(boolean feature) {
		this.feature = feature;
	}

	

}
