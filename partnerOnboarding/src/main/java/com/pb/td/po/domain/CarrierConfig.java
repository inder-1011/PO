package com.pb.td.po.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class CarrierConfig {

	private String code;
	private String name;
	private String serviceType;
	private Date startDownTime;
	private Date endDownTime;
	private Date updateDate;
	
	@Id
	@Column
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	@Column
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	
	@Column
	public Date getStartDownTime() {
		return startDownTime;
	}
	public void setStartDownTime(Date startDownTime) {
		this.startDownTime = startDownTime;
	}
	
	@Column
	public Date getEndDownTime() {
		return endDownTime;
	}
	public void setEndDownTime(Date endDownTime) {
		this.endDownTime = endDownTime;
	}
	
	@Column
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
	
}
