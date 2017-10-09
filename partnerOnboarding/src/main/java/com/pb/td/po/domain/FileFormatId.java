package com.pb.td.po.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonBackReference;

@Embeddable
public class FileFormatId implements Serializable{
	
	private static final long serialVersionUID = -9183472510547437711L;

	@ManyToOne(targetEntity=Partner.class)
	@JoinColumn(name="partnerId")
	@JsonBackReference
	private Partner partner;
	
	public Partner getPartner() {
		return partner;
	}
	
	public void setPartner(Partner partner) {
		this.partner = partner;
	} 
	
	private String columnName;
	
	@Column
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	

}
