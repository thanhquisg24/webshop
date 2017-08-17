package com.shopping.core.model.catalog.product.manufacturer;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.validator.constraints.NotEmpty;

import com.shopping.core.constants.SchemaConstant;
import com.shopping.core.model.common.audit.AuditListener;
import com.shopping.core.model.common.audit.AuditSection;
import com.shopping.core.model.common.audit.Auditable;
import com.shopping.core.model.generic.SalesManagerEntity;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "MANUFACTURER", schema=SchemaConstant.SALESMANAGER_SCHEMA )
public class Manufacturer extends SalesManagerEntity<Long, Manufacturer> implements Auditable {
	private static final long serialVersionUID = 80693964563570099L;
	
	/**
	 * 
	 */
	@Id
	@Column(name = "MANUFACTURER_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MANUFACT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	/**
	 * 
	 * 
	 * 
	 */
	@Embedded
	private AuditSection auditSection = new AuditSection();
	

	/**
	 * 
	 */
	@Column(name = "MANUFACTURER_IMAGE")
	private String image;
	
	/**
	 * 
	 */
	@Column(name="SORT_ORDER")
	private Integer order = new Integer(0);

	/**
	 * 
	 */
	@NotEmpty
	@Column(name="CODE", length=100, nullable=false)
	private String code;

	/**
	 * 
	 */
	@NotEmpty
	@Column(name="NAME", nullable = false, length=120)
	private String name;
	
	/**
	 * 
	 */
	@Column(name="TITLE", length=100)
	private String title;
	
	/**
	 * 
	 */
	@Column(name="DESCRIPTION",columnDefinition = "TEXT")
	private String description;
	
	
	/**
	 * 
	 */
	@Column(name = "MANUFACTURERS_URL")
	private String url;
	
	/**
	 * 
	 */
	@Column(name = "URL_CLICKED")
	private Integer urlClicked;
	
	/**
	 * 
	 */
	@Column(name = "DATE_LAST_CLICK")
	private Date dateLastClick;
	
	public Manufacturer() {
	}

	@Override
	public Long getId() {
		return id;
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

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}




	public void setOrder(Integer order) {
		this.order = order;
	}

	public Integer getOrder() {
		return order;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getUrlClicked() {
		return urlClicked;
	}

	public void setUrlClicked(Integer urlClicked) {
		this.urlClicked = urlClicked;
	}

	public Date getDateLastClick() {
		return dateLastClick;
	}

	public void setDateLastClick(Date dateLastClick) {
		this.dateLastClick = dateLastClick;
	}


}
