package com.pb.td.po.dto;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class TrackingNumberValidationRequest {
	
	private String carrierCode;
	private String mask;
	
	public String getCarrierCode() {
		return carrierCode;
	}
	public void setCarrierCode(String carrierCode) {
		this.carrierCode = carrierCode;
	}
	public String getMask() {
		return mask;
	}
	public void setMask(String mask) {
		this.mask = mask;
	}

}
