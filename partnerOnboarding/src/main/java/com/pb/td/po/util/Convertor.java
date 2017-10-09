/**
 * 
 */
package com.pb.td.po.util;

import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.hibernate.TransactionException;

import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.FileFormat;
import com.pb.td.po.domain.FileFormatId;
import com.pb.td.po.domain.FileTransferDetail;
import com.pb.td.po.domain.FileTransferDetailId;
import com.pb.td.po.domain.Partner;
import com.pb.td.po.domain.QueueServers;
import com.pb.td.po.domain.Services;
import com.pb.td.po.domain.StaticParameter;
import com.pb.td.po.domain.TrackingNumberValidation;
import com.pb.td.po.domain.User;
import com.pb.td.po.dto.CarrierConfigRequest;
import com.pb.td.po.dto.FileFormatRequest;
import com.pb.td.po.dto.FileTransferDetailIdRequest;
import com.pb.td.po.dto.FileTransferDetailRequest;
import com.pb.td.po.dto.PartnerRequest;
import com.pb.td.po.dto.QueueServerRequest;
import com.pb.td.po.dto.StaticParameterRequest;
import com.pb.td.po.dto.TrackingNumberValidationRequest;
import com.pb.td.po.dto.UserRequest;

/**
 * @author indrajeet.verma@pb.com Mar 18, 2016
 */
public class Convertor {

	/**
	 * This API Converts User request object to user Domain which is mapped with
	 * TD user table
	 * 
	 * @param userRequest
	 * @return Domain User
	 */
	public static User UserRequestToDomain(UserRequest userRequest, User user, List<Services> services,
			Partner partner) {
		user.setActive(true);
		user.setPassword(Base64.getEncoder().encodeToString(userRequest.getPassword().getBytes(StandardCharsets.UTF_8)));
		user.setRole(userRequest.getRole());
		user.setUpdateDate(Calendar.getInstance().getTime());
		user.setUserName(userRequest.getUserName());
		user.setServices(servicesRequestToDomain(userRequest.getServices(), services));
		user.setPartner(partner);
		return user;
	}

	public static Partner PartnerRequestToDomain(PartnerRequest partnerRequest, Partner partner, Properties properties,
			List<Services> services, FileTransferDetailIdRequest fileTransferDetailIdRequest) {
		partner.setPartnerId(partnerRequest.getPartnerId());
		partner.setBlankFile(partnerRequest.getBlankFile());
		partner.setCountryCode(partnerRequest.getCountryCode());
		partner.setUpdateDate(Calendar.getInstance().getTime());
		partner.setDelimiter(partnerRequest.getDelimiter());
		partner.setFileExt(partnerRequest.getFileExt());
		partner.setFileNamePattern(partnerRequest.getFileNamePattern());
		partner.setFrequency(partnerRequest.getFrequency());
		partner.setHeader(partnerRequest.getHeader());
		partner.setMaxSize(partnerRequest.getMaxSize());
		partner.setName(partnerRequest.getName());
		partner.setReportScan(partnerRequest.getReportScan());
		partner.setPartnerCode(partnerRequest.getPartnerCode());
		if (partnerRequest.getFileTransferDetailRequest() != null) {
			List<FileTransferDetail> details = new ArrayList<FileTransferDetail>();
			details.add(fileTransferDetailRequestToDomain(partnerRequest.getFileTransferDetailRequest(), partner));
			partner.setFileTransferDetails(details);
		}
		//partner.setFileFormats(getDefaultFileFormat(properties, partner));
		return partner;
	}

	public static List<Services> servicesRequestToDomain(List<String> servicesRequest, List<Services> services) {
		List<String> list = Arrays.asList(servicesRequest.get(0).split("%2C"));
		List<Services> servicesSelected = new ArrayList<Services>();
		for (Services service : services) {
			if (list.contains(service.getName()))
				servicesSelected.add(service);

		}
		return servicesSelected;
	}

	public static FileTransferDetail fileTransferDetailRequestToDomain(
			FileTransferDetailRequest fileTransferDetailRequest, Partner partner) {
		if (fileTransferDetailRequest == null)
			return null;
		FileTransferDetailId detailId = new FileTransferDetailId();
		FileTransferDetail fileTransferDetail = new FileTransferDetail();
		fileTransferDetail.setActive(true);
		fileTransferDetail.setUpdateDate(Calendar.getInstance().getTime());
		fileTransferDetail.setHost(fileTransferDetailRequest.getHost());
		fileTransferDetail.setPort(fileTransferDetailRequest.getPort());
		fileTransferDetail.setProtocol(fileTransferDetailRequest.getProtocol());
		detailId.setUserName(fileTransferDetailRequest.getUserName());
		detailId.setPartner(partner);
		fileTransferDetail.setId(detailId);
		return fileTransferDetail;

	}

	public static QueueServers queueServersToDomain(QueueServerRequest queueServerRequest, QueueServers queueServers) {
		queueServers.setServerName(queueServerRequest.getServerName());
		queueServers.setQueueServer(queueServerRequest.getQueueServer());
		queueServers.setUpdateDate(Calendar.getInstance().getTime());
		return queueServers;
	}

	public static StaticParameter staticParameterToDomain(StaticParameterRequest staticParameterRequest,
			StaticParameter staticParameter) {
		staticParameter.setPartnerId(staticParameterRequest.getPartnerId());
		staticParameter.setUserName(staticParameterRequest.getUserName());
		staticParameter.setCarrierCode(staticParameterRequest.getCarrierCode());
		staticParameter.setName(staticParameterRequest.getName());
		staticParameter.setValue(staticParameterRequest.getValue());
		staticParameter.setUpdateDate(Calendar.getInstance().getTime());
		return staticParameter;
	}

	public static TrackingNumberValidation trackingNumberValidationToDomain(
			TrackingNumberValidationRequest trackingNumberValidationRequest,
			TrackingNumberValidation trackingNumberValidation) {
		trackingNumberValidation.setCarrierCode(trackingNumberValidationRequest.getCarrierCode());
		trackingNumberValidation.setMask(trackingNumberValidationRequest.getMask());
		trackingNumberValidation.setUpdateDate(Calendar.getInstance().getTime());
		return trackingNumberValidation;
	}

	public static CarrierConfig carrierConfigToDomain(CarrierConfigRequest carrierConfigRequest, CarrierConfig carrierConfig ){
		 carrierConfig.setCode(carrierConfigRequest.getCode());
		 carrierConfig.setName(carrierConfigRequest.getName());
		 SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss",Locale.ENGLISH);
		 carrierConfig.setServiceType(carrierConfigRequest.getServiceType());
		 try {
			if(df.parse(carrierConfigRequest.getEndDownTime()).after(df.parse(carrierConfigRequest.getStartDownTime()))){
				  carrierConfig.setEndDownTime(df.parse(carrierConfigRequest.getEndDownTime()));
				 carrierConfig.setStartDownTime(df.parse(carrierConfigRequest.getStartDownTime()));
			}
			else if((carrierConfigRequest.getEndDownTime()==null)||(carrierConfigRequest.getEndDownTime()==null)){
				 carrierConfig.setEndDownTime(df.parse(carrierConfigRequest.getEndDownTime()));
				 carrierConfig.setStartDownTime(df.parse(carrierConfigRequest.getStartDownTime()));
			}else {
				throw new TransactionException("canot save start date greater than end date");
			}
		} catch (ParseException e) {
			 System.out.println("StartDownTime and EndDownTime not saved : The Date is not in the correct format ");
		}
		 carrierConfig.setUpdateDate(Calendar.getInstance().getTime());
		 return carrierConfig;
	 }
	
	public static List<FileFormat> getDefaultFileFormat(Properties properties, Partner partner) {
		List<FileFormat> formatList = new ArrayList<FileFormat>(properties.size());
		for (Object key : properties.keySet()) {
			String values = properties.get(key).toString();
			String[] tokens = values.split(",");
			FileFormat format = new FileFormat();

			FileFormatId fileFormatId = new FileFormatId();
			fileFormatId.setPartner(partner);
			fileFormatId.setColumnName(key.toString());
			format.setPartnerId(123L);
			format.setUpdateDate(Calendar.getInstance().getTime());
			format.setColumnOrder(Integer.parseInt(tokens[0]));
			format.setAlias(tokens[1]);
			format.setRequired(true);
			format.setFormat(tokens[2]);

			System.out.println(format);
			formatList.add(format);
		}
		return formatList;

	}

	public static List<FileFormat> fileFormatRequestToDomain(List<FileFormatRequest> fileFormatRequests) {
		List<FileFormat> fileFormats = new ArrayList<FileFormat>();
		for (FileFormatRequest fileFormatRequest : fileFormatRequests) {
			FileFormat fileFormat = new FileFormat();
			fileFormat.setAlias(fileFormatRequest.getAlias());

			// fileFormat.setId(fileFormatRequest.getRequestId().getColumnName().toString());
			fileFormat.setUpdateDate(Calendar.getInstance().getTime());
			fileFormats.add(fileFormat);
		}
		return fileFormats;
	}
	
	public static FileFormat fileFormatRequestToDomain(FileFormatRequest fileformatRequest){
		FileFormat fileFormat=new FileFormat();
		fileFormat.setPartnerId(fileformatRequest.getPartnerId());
		fileFormat.setColumnName(fileformatRequest.getColumnName());
		fileFormat.setAlias(fileformatRequest.getAlias());
		fileFormat.setRequired(fileformatRequest.getRequired());
		fileFormat.setColumnOrder(fileformatRequest.getColumnOrder());
		fileFormat.setFormat(fileformatRequest.getFormat());
		fileFormat.setUpdateDate(Calendar.getInstance().getTime());
		return fileFormat;
	}
}
