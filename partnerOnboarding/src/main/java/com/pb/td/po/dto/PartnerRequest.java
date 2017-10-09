package com.pb.td.po.dto;

import java.util.List;

import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

@XmlType
public class PartnerRequest {
	
	private Long partnerId;
	private String partnerCode;
	private String name;
	private Long countryCode;
	private String fileExt;
	private String delimiter;
	private Boolean header;
	private Boolean blankFile;
	private Integer maxSize;
	private Integer frequency;
	private String reportScan;
	

	FileTransferDetailIdRequest fileTransferDetailIdRequest;
	FileTransferDetailRequest fileTransferDetailRequest;
	private String fileNamePattern;
	
	List<FileFormatRequest> fileFormatRequests;
	/**
	 * @return the fileFormatRequests
	 */
	public List<FileFormatRequest> getFileFormatRequests() {
		return fileFormatRequests;
	}

	/**
	 * @param fileFormatRequests the fileFormatRequests to set
	 */
	public void setFileFormatRequests(List<FileFormatRequest> fileFormatRequests) {
		this.fileFormatRequests = fileFormatRequests;
	}


	
	
	public String getReportScan() {
		return reportScan;
	}
	
	public void setReportScan(String reportScan) {
		this.reportScan = reportScan;
	}
	
	
	public Long getCountryCode() {
		return countryCode;
	}

	public void setCountryCode(Long code) {
		this.countryCode = code;
	}

	public String getName() {
		return name;
	}
	

	public void setName(String name) {
		this.name = name;
	}
	
	public String getFileNamePattern() {
		return fileNamePattern;
	}
	public void setFileNamePattern(String fileNamePattern) {
		this.fileNamePattern = fileNamePattern;
	}
	
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	
	public String getDelimiter() {
		return delimiter;
	}
	public void setDelimiter(String delimiter) {
		this.delimiter = delimiter;
	}
	
	public Boolean getHeader() {
		return header;
	}
	public void setHeader(Boolean header) {
		this.header = header;
	}
	
	public Boolean getBlankFile() {
		return blankFile;
	}
	public void setBlankFile(Boolean blankFile) {
		this.blankFile = blankFile;
	}
	
	public Integer getMaxSize() {
		return maxSize;
	}
	public void setMaxSize(Integer maxSize) {
		this.maxSize = maxSize;
	}
	
	public Integer getFrequency() {
		return frequency;
	}
	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}
	
	public String getPartnerCode() {
		return partnerCode;
	}
	
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}
	
	public Long getPartnerId() {
		return partnerId;
	}

	public void setPartnerId(Long partnerId) {
		this.partnerId = partnerId;
	}

	/**
	 * @return the fileTransferDetailRequest
	 */
	public FileTransferDetailRequest getFileTransferDetailRequest() {
		return fileTransferDetailRequest;
	}

	/**
	 * @param fileTransferDetailRequest the fileTransferDetailRequest to set
	 */
	public void setFileTransferDetailRequest(FileTransferDetailRequest fileTransferDetailRequest) {
		this.fileTransferDetailRequest = fileTransferDetailRequest;
	}

	

}

