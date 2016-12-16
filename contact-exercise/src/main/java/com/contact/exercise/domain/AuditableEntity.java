package com.contact.exercise.domain;

import java.io.Serializable;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class AuditableEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4391508636203529290L;

	@Column(name="CREATED_ON", updatable=false)
	private Calendar createdOn;
	
	@Column(name="UPDATED_ON")
	private Calendar updatedOn;

	public Calendar getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Calendar createdOn) {
		this.createdOn = createdOn;
	}

	public Calendar getUpdatedOn() {
		return updatedOn;
	}

	public void setUpdatedOn(Calendar updatedOn) {
		this.updatedOn = updatedOn;
	}
	
}
