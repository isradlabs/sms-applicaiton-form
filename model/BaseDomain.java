package com.isradlabs.sms.applicationform.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class BaseDomain implements Serializable {

	public static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@CreatedDate
	@Column(name = "created_date", updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdDate;
	
	@LastModifiedDate
	@Column(name = "updated_date", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedDate;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createdBy;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date updatedBy;
	
	private Boolean softDelete;
	
	@Column(name = "effective_date", updatable = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date effectiveDate;

}
