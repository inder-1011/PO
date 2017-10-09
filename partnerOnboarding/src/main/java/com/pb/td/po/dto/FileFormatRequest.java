package com.pb.td.po.dto;

import java.util.Date;

import javax.xml.bind.annotation.XmlType;

@XmlType
public class FileFormatRequest {
	
	private Long partnerId;
	public Long getPartnerId() {
		return partnerId;
	}
	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	private String columnName;
	private Boolean required;
	private FileFormatIdRequest requestId;
	private String format;
	private String alias;
	private Integer columnOrder;
	private Date updateDate;
	

	
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public FileFormatIdRequest getRequestId() {
		return requestId;
	}
	public void setRequestId(FileFormatIdRequest requestId) {
		this.requestId = requestId;
	}
	public Boolean getRequired() {
		return required;
	}
	
	public void setRequired(Boolean required) {
		this.required = required;
	}
	
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public Integer getColumnOrder() {
		return columnOrder;
	}
	public void setColumnOrder(Integer columnOrder) {
		this.columnOrder = columnOrder;
	}

}
