package com.pb.td.po.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class FileTransferDetail implements Serializable{
	
	private static final long serialVersionUID = -4717994042072565507L;
	
	private Boolean active;
	private String protocol;
	private String host;
	private String port;
	private String password;
	private Date updateDate;
	

	@EmbeddedId
	private FileTransferDetailId id;
	
	public FileTransferDetailId getId() {
		return id;
	}

	public void setId(FileTransferDetailId id) {
		this.id = id;
	}
	
	@Column
	public Boolean getActive() {
		return active;
	}
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	@Column
	public String getProtocol() {
		return protocol;
	}
	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}
	
	@Column
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	
	@Column
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	
	
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	@Column
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

}
