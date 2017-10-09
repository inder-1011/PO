package com.pb.td.po.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.transaction.annotation.Transactional;

@Entity
public class Partner implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8537659919945957867L;
	private Long partnerId;
	private Long countryCode;
	private String name;
	private String partnerCode;
	private String fileNamePattern;
	private String fileExt;
	private String delimiter;
	private Boolean header;
	private Boolean blankFile;
	private Integer maxSize;
	private Integer frequency;
	private String externalEvents;
	private String reportScan;

	private Date updateDate;
	private Partner parentPartner;
	private List<FileTransferDetail> fileTransferDetails;
	private List<FileFormat> fileFormats;
	
	
	
	@Id
	@Column
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}
	
	@Column
	public String getReportScan() {
		return reportScan;
	}
	
	public void setReportScan(String reportScan) {
		this.reportScan = reportScan;
	}
	
	@Column
	public Long getCountryCode() {
		return countryCode;
	}

		public void setCountryCode(Long code) {
		this.countryCode = code;
	}

	
	@Column
	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	@Column
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}
	
	@Column
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	@Column
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	@Column
	public Boolean getHeader() {
		return header;
	}
	public void setHeader(Boolean header) {
		this.header = header;
	}
	
	@Column
	public Boolean getBlankFile() {
		return blankFile;
	}
	public void setBlankFile(Boolean blankFile) {
		this.blankFile = blankFile;
	}
	
	@Column
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	@Column
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	
	@Column
	public Partner getParentPartner() {
		return parentPartner;
	}
	public void setParentPartner(Partner parentPartner) {
		this.parentPartner = parentPartner;
	}
	
	@Column
	public String getExternalEvents() {
		return externalEvents;
	}
	public void setExternalEvents(String externalEvents) {
		this.externalEvents = externalEvents;
	}
	@OneToMany(mappedBy = "id.partner",cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	public List<FileTransferDetail> getFileTransferDetails() {
		return fileTransferDetails;
	}
	@Transactional(readOnly=false)
	public void setFileTransferDetails(List<FileTransferDetail> fileTransferDetails) {
		this.fileTransferDetails = fileTransferDetails;
	}


	@OneToMany(mappedBy = "partnerId",cascade={CascadeType.ALL}, fetch=FetchType.EAGER)
	public List<FileFormat> getFileFormats() {
		return fileFormats;
	}
	@Transactional(readOnly=false)
	public void setFileFormats(List<FileFormat> fileFormats) {
		this.fileFormats = fileFormats;
	}
	
	@Column
	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	@Column
	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	
}


