package com.pb.td.po.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;


@Entity(name="Users")
public class User implements Serializable{
	
	private static final long serialVersionUID = -1232251304611423910L;
	private String userName;
	private String password;
	private String role;
	private Partner partner;
	private boolean active;

	private Date updateDate;
	private List<Services> services;
	
	public User() {
		super();
	}
	
	public User(String userName, String password, String role, Partner partner, boolean active, Date created,
			Date updated, List<Services> services) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
		this.partner = partner;
		this.active = active;
		this.updateDate = updated;
		this.services = services;
	}

	@Id
	@Column
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Column
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	@Column
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@OneToOne(targetEntity=Partner.class)
	@JoinTable(name = "userpartner", joinColumns = { @JoinColumn(name = "userName") }, inverseJoinColumns = { @JoinColumn(name = "partnerId") })
	public Partner getPartner() {
		return partner;
	}
	public void setPartner(Partner partner) {
		this.partner = partner;
	}
	
	@Column
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	
	@Column
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Date updated) {
		this.updateDate = updated;
	}
	
	@OneToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "userServices", joinColumns = { @JoinColumn(name = "userName") }, inverseJoinColumns = { @JoinColumn(name = "serviceName") })
	public List<Services> getServices() {
		return services;
	}

	public void setServices(List<Services> services) {
		this.services = services;
	}
}	

