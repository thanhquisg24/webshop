package com.shopping.core.model.catalog.category;


import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.shopping.core.model.common.audit.AuditSection;
import com.shopping.core.model.common.audit.Auditable;
import com.shopping.core.model.generic.SalesManagerEntity;


@Entity
@EntityListeners(value = com.shopping.core.model.common.audit.AuditListener.class)
@Table(name="Category")
@JsonIgnoreProperties({"categories"})
public class Category extends SalesManagerEntity<Integer, Category> implements Auditable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1288117969640564999L;

	static Log log = LogFactory.getLog(Category.class);

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CATEGORY_ID", unique=true, nullable=false)
	private Integer id = 0;
	
	@NotEmpty
	@Column(name="NAME", length=100, nullable=false)
	private String name = null;
	
	@Column(name = "CATEGORY_IMAGE", length=100)
	private String categoryImage;

	@Column(name = "SORT_ORDER")
	private Integer sortOrder = 0;

	@Column(name = "DESCRIPTION",columnDefinition = "TEXT")
	private String description = null;

	@Column(name="SEO_URL", length=120)
	private String seoUrl= null;
	
	@Column(name="META_TITLE", length=120)
	private String metatagTitle= null;
	
	@Column(name="META_KEYWORDS")
	private String metatagKeywords= null;
	
	@Column(name="META_DESCRIPTION")
	private String metatagDescription= null;

	@Column(name = "LINEAGE")
	private String lineage;
	
	
	@Column(name = "BREADCRUMB")
	private String breadcrumb;
	
	@Column(name = "ICON")
	private String icon;
	
	@Column(name = "CSSCLASS")
	private String cssclass;
	
	@Column(name = "DEPTH")
	private Integer depth;

	
	@ManyToOne
	@JoinColumn(name = "PARENT_ID")
	private Category parent;

	
	
	@OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE, orphanRemoval = true)
	private List<Category> categories = new ArrayList<Category>();

	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@Column(name = "IS_ACTIVE")
	private boolean Active = true;

	
	@Column(name = "SHOW_ON_HOME_PAGE")
	private boolean ShowOnHomePage = true;
	

	@Column(name = "INCLUDE_IN_TOP_MENU")
	private boolean IncludeInTopMenu = true;
	
	@Column(name = "IS_FEATURE")
	private boolean Feature = false;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategoryImage() {
		return categoryImage;
	}

	public void setCategoryImage(String categoryImage) {
		this.categoryImage = categoryImage;
	}

	public Integer getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(Integer sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	public String getSeoUrl() {
		return seoUrl;
	}

	public void setSeoUrl(String seoUrl) {
		this.seoUrl = seoUrl;
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

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

	public List<Category> getCategories() {
		return categories;
	}

	public void setCategories(List<Category> categories) {
		this.categories = categories;
	}
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}



	public String getLineage() {
		return lineage;
	}

	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
	
	

	public Integer getDepth() {
		return depth;
	}

	public void setDepth(Integer depth) {
		this.depth = depth;
	}

	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getCssclass() {
		return cssclass;
	}

	public void setCssclass(String cssclass) {
		this.cssclass = cssclass;
	}
	
	

	public boolean isFeature() {
		return Feature;
	}

	public void setFeature(boolean feature) {
		Feature = feature;
	}

	public boolean isActive() {
		return Active;
	}

	public void setActive(boolean active) {
		Active = active;
	}

	public boolean isShowOnHomePage() {
		return ShowOnHomePage;
	}

	public void setShowOnHomePage(boolean showOnHomePage) {
		ShowOnHomePage = showOnHomePage;
	}

	public boolean isIncludeInTopMenu() {
		return IncludeInTopMenu;
	}

	public void setIncludeInTopMenu(boolean includeInTopMenu) {
		IncludeInTopMenu = includeInTopMenu;
	}

	
	
	public String getBreadcrumb() {
		return breadcrumb;
	}

	public void setBreadcrumb(String breadcrumb) {
		this.breadcrumb = breadcrumb;
	}

	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(Integer id) {
		this.id = id;
		
	}
	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	
	
	
	@Override
	public String toString() {
		return "Category [id=" + id + ", name=" + name + ", categoryImage="
				+ categoryImage + ", sortOrder=" + sortOrder + ", description="
				+ description + ", seoUrl=" + seoUrl + ", metatagTitle="
				+ metatagTitle + ", metatagKeywords=" + metatagKeywords
				+ ", metatagDescription=" + metatagDescription + ", lineage="
				+ lineage + ", icon=" + icon + ", cssclass=" + cssclass
				+ ", depth=" + depth + ", parent=" + parent + ", categories="
				+ categories + ", auditSection=" + auditSection + ", Active="
				+ Active + ", ShowOnHomePage=" + ShowOnHomePage
				+ ", IncludeInTopMenu=" + IncludeInTopMenu + "]";
	}


}
