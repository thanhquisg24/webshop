package com.shopping.web.admin.dto;

import java.math.BigDecimal;
import java.util.Set;

/**
 * @author qui-r90270
 *
 */
public class ProductDTO {
	private Long id;
	
	/*Mapping*/
	private Set<Integer> categories;
	private Long manufacture_id;
	/*end mapping*/
	
	/*product info*/
	private String sku;
	private String name;
	private String short_description;
	private String full_description;
	private Integer sortOrder = new Integer(0);
	private boolean active =false;
	/*end product info*/
	
	/*price*/
	private BigDecimal price =new BigDecimal(0);
	private BigDecimal old_price=new BigDecimal(0);
	private BigDecimal product_cost=new BigDecimal(0);
	private BigDecimal price_discount=new BigDecimal(0);
	private float percent_discount = 0;
	private boolean discount =false;
	
	/*end price*/
	
	/*Inventory*/
	private Integer stock_quantity= new Integer(0);
	private boolean display_stock_availability=true;
	private boolean display_stock_qty=false;
	private Integer minimum_stock_qty= new Integer(0);
	private Integer minimum_cart_qty= new Integer(0);
	private Integer maximum_cart_qty= new Integer(0);
	/*end Inventory*/
	
	
	/*shipping*/
	private boolean product_is_free_shipping;
	private BigDecimal productLength=new BigDecimal(0);
	private BigDecimal productWidth=new BigDecimal(0);
	private BigDecimal productHeight=new BigDecimal(0);
	private BigDecimal productWeight=new BigDecimal(0);
	/*end shipping*/
	
	/*seo*/
	private String seUrl;
	private String metatagTitle;
	private String metatagKeywords;
	private String metatagDescription;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Set<Integer> getCategories() {
		return categories;
	}
	public void setCategories(Set<Integer> categories) {
		this.categories = categories;
	}
	public Long getManufacture_id() {
		return manufacture_id;
	}
	public void setManufacture_id(Long manufacture_id) {
		this.manufacture_id = manufacture_id;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public Integer getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
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
	public BigDecimal getPrice_discount() {
		return price_discount;
	}
	public void setPrice_discount(BigDecimal price_discount) {
		this.price_discount = price_discount;
	}
	public float getPercent_discount() {
		return percent_discount;
	}
	public void setPercent_discount(float percent_discount) {
		this.percent_discount = percent_discount;
	}
	public boolean isDiscount() {
		return discount;
	}
	public void setDiscount(boolean discount) {
		this.discount = discount;
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
	public boolean isProduct_is_free_shipping() {
		return product_is_free_shipping;
	}
	public void setProduct_is_free_shipping(boolean product_is_free_shipping) {
		this.product_is_free_shipping = product_is_free_shipping;
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
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	
	
}
