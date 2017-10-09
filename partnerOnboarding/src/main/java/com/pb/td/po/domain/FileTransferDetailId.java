package com.pb.td.po.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.codehaus.jackson.annotate.JsonBackReference;

@Embeddable
public class FileTransferDetailId implements Serializable{
	private static final long serialVersionUID = -5365617403228488136L;

	private String userName;
	
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
	
	@Column
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	

}
