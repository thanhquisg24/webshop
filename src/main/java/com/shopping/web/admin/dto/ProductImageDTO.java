package com.shopping.web.admin.dto;

import org.springframework.web.multipart.MultipartFile;

public class ProductImageDTO {

	private Long id;
	private String name;
	private String title;
	private String productImage;
	private boolean defaultImage = false;
	private String altTag;
	private int displayOrder;
	
	private MultipartFile file;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
