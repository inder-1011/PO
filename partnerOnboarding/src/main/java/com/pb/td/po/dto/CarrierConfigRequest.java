package com.pb.td.po.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class CarrierConfigRequest {
	private String code;
	private String name;
	private String serviceType;
	private String startDownTime;
	private String endDownTime;
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
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getStartDownTime() {
		return startDownTime;
	}
	public void setStartDownTime(String startDownTime) {
		this.startDownTime = startDownTime;
	}
	public String getEndDownTime() {
		return endDownTime;
	}
	public void setEndDownTime(String endDownTime) {
		this.endDownTime = endDownTime;
	}

}
