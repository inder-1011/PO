package com.pb.td.po.services;

import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.pb.td.po.dao.ApiDao;
import com.pb.td.po.dao.ApiDaoImpl1;
import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.EditTrackingnumberValidation;
import com.pb.td.po.domain.FileFormat;
import com.pb.td.po.domain.FileTransferDetail;
import com.pb.td.po.domain.Partner;
import com.pb.td.po.domain.QueueServers;
import com.pb.td.po.domain.StaticParameter;
import com.pb.td.po.domain.TrackingNumberValidation;
import com.pb.td.po.domain.User;
import com.pb.td.po.dto.CarrierConfigRequest;
import com.pb.td.po.dto.FileFormatRequest;
import com.pb.td.po.dto.PartnerRequest;
import com.pb.td.po.dto.QueueServerRequest;
import com.pb.td.po.dto.StaticParameterRequest;
import com.pb.td.po.dto.TrackingNumberValidationRequest;
import com.pb.td.po.dto.UserRequest;
import com.pb.td.po.util.Convertor;
/**
 * @author indrajeet.verma@pb.com
 * Mar 17, 2016
 */
@Component
@Transactional
public class UserManagementServiceImpl implements UserManagementService {

	@Autowired
	ApiDao apiDao;
	
	@Autowired
	Properties applicationProp;
	private final Logger logger = LoggerFactory.getLogger(UserManagementServiceImpl.class);

	@Override
	public User saveUser(UserRequest userRequest) {
		logger.info("########## inside save user API ##############");
		User user = apiDao.getUserByUserName(userRequest.getUserName());
		if (user == null) {
		logger.info("##### creating new User   ###########");
			user = new User();}
		
		Convertor.UserRequestToDomain(userRequest, user, apiDao.getAllServices(),
				apiDao.getPartnerById(userRequest.getPartnerId()));
		return apiDao.saveUser(user);
	}

	@Override
	public User loginUser(UserRequest userRequest) {
		User user =  apiDao.getUserByUserName(userRequest.getUserName());
		if(user==null){
			return null;
		}else if(!user.getPassword().equals(userRequest.getPassword())){
			return null;
		}
		return user;

	}
	
	@Override
	public QueueServers saveQueueServers(QueueServerRequest queueServerRequest){
		/*QueueServers queueServers = apiDao.getQueueServers(queueServerRequest.getServerName());*/
		QueueServers queueServers = new QueueServers();
		Convertor.queueServersToDomain(queueServerRequest, queueServers);
		return apiDao.saveQueueServers(queueServers);
	}
	@Override
	public TrackingNumberValidation saveTrackingNumberValidation(TrackingNumberValidationRequest trackingNumberValidationRequest) {
		TrackingNumberValidation trackingNumberValidation = new TrackingNumberValidation();
		Convertor.trackingNumberValidationToDomain(trackingNumberValidationRequest, trackingNumberValidation);
		return apiDao.saveTrackingNumberValidation(trackingNumberValidation);
	}
	
	@Override
	public StaticParameter saveStaticParameter(StaticParameterRequest staticParameterRequest) {
		StaticParameter staticParameter = new StaticParameter();
		Convertor.staticParameterToDomain(staticParameterRequest, staticParameter);
		return apiDao.saveStaticParameter(staticParameter);
	}
	
	

	@Override
	public Partner savePartnerInfo(PartnerRequest partnerRequest) {
		logger.info("################ inside save partner API #####################");
		/*Partner partner = apiDao.getPartnerById(partnerRequest.getPartnerId());
		if (partner == null) {
		Partner partner=null;
		}*/ 
			Partner	partner = new Partner();
			partner = Convertor.PartnerRequestToDomain(partnerRequest, partner, applicationProp,
					apiDao.getAllServices(), null);
			partner.setUpdateDate(Calendar.getInstance().getTime());
			if (partnerRequest.getFileTransferDetailRequest() != null) {
				FileTransferDetail fileTransferDetail = Convertor
						.fileTransferDetailRequestToDomain(partnerRequest.getFileTransferDetailRequest(), partner);
				partner.getFileTransferDetails().add(fileTransferDetail);
			}
			if (partnerRequest.getFileFormatRequests() != null && partnerRequest.getFileFormatRequests().size() > 0) {
				partner.setFileFormats(Convertor.fileFormatRequestToDomain(partnerRequest.getFileFormatRequests()));
			}
		
		return apiDao.savePartnerInfo(partner);
	}
	/* 
	 * @see com.pb.td.po.services.UserManagementService#saveUserFile(com.pb.td.po.dto.FileUploader)
	 */
	/*@Override
	public Response saveUserFile(InputStream in) {
		Partner partner =new Partner();
		FileTransferDetailRequest ftrequest = new FileTransferDetailRequest();
		try {
			InputStreamReader isr = new InputStreamReader(in);
			CSVReader reader = new CSVReader(isr);
			//

			String[] line=reader.readNext();
			
				while((line=reader.readNext())!=null){
					if(line.length==15){
						line=reader.readNext();
					partner.setCountryCode(Long.parseLong(line[0]));
					partner.setName(line[1]);
					partner.setFileExt(line[2]);
					partner.setFileNamePattern(line[3]);
					partner.setDelimiter(line[4]);
					partner.setHeader(Boolean.parseBoolean(line[5]));
					partner.setBlankFile(Boolean.parseBoolean(line[6])); 
					partner.setMaxSize(Integer.parseInt(line[7]));
					partner.setFrequency(Integer.parseInt(line[8]));
					partner.setFileFormats(Convertor.getDefaultFileFormat(applicationProp,partner));
				
					ftrequest.setHost(line[10]);
					ftrequest.setPort(line[11]);
					ftrequest.setProtocol(line[12]);
					ftrequest.setPassword(line[14]);
					partner.setFileTransferDetails(new ArrayList<FileTransferDetail>());
					partner.getFileTransferDetails().add(Convertor.fileTransferDetailRequestToDomain(ftrequest,partner));
					Partner dbPartner = apiDao.getPartnerInfoUsingName(partner.getName());
					if(dbPartner!=null){
						partner.setPartnerId(dbPartner.getPartnerId());
					}
					apiDao.savePartnerInfo(partner);
					}

				}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;	 
	}*/

/*	public List<User> deleteUser(String userName) {
		apiDao.deleteUser(userName);
		return apiDao.getUsers();

	}*/
	@Override
	public List<User> getUsers() {
		return apiDao.getUsers();

	}

	@Override
	public User getUser(String userName) {
		return apiDao.getUserByUserName(userName);
	}
/*
	public List<Partner> deletePartnerInfo(Long partnerId) {
		try{
			apiDao.deletePartnerInfo(partnerId);
		}
		catch(Exception e){
			System.out.println(e.getMessage());
		}
		return apiDao.getPartnersInfo();
	}
*/
	@Override
	public List<Partner> getPartnersInfo() {
		return apiDao.getPartnersInfo();
	}

	@Override
	public Partner getPartnerInfoUsingName(String name) {
		return apiDao.getPartnerInfoUsingName(name);
	}

	@Override
	public Partner getPartnerById(Long partnerId) {
		return apiDao.getPartnerById(partnerId);
	}
	@Override
	public List<FileFormat> getFileFormat(Long partnerId) {
		return apiDao.getFileFormat(partnerId);
	}

	@Override
	public List<QueueServers> getQueueServers() {
		return apiDao.getQueueServers();
	}


	@Override
	public List<StaticParameter> getStaticParameter() {
		return apiDao.getStaticParameter();
	}

	@Override
	public List<TrackingNumberValidation> getTrackingNumberValidation() {
		return apiDao.getTrackingNumberValidation();
	}

	/*public List<StaticParameter> deleteStaticParameter(Long partnerId) {
		apiDao.deleteStaticParameter(partnerId);
		return apiDao.getStaticParameter();
	}

	public List<TrackingNumberValidation> deleteTrackingNumberValidation(String carrierCode) {
		apiDao.deleteTrackingNumberValidation(carrierCode);
		return apiDao.getTrackingNumberValidation();
	}

	@Override
	public List<QueueServers> deleteQueueServer(String serverName) {
		apiDao.deleteQueueServer(serverName);
		return apiDao.getQueueServers();
	}

	@Override
	public List<CarrierConfig> deleteCarrierConfig(String code) {
		apiDao.deleteCrrierConfig(code);
		return apiDao.getCarrierConfig();
	}*/

	@Override
	public List<CarrierConfig> getCarrierConfig() {
		return apiDao.getCarrierConfig();
	}

	@Override
	public CarrierConfig saveCarrierConfig(CarrierConfigRequest carrierConfigRequest) {
		CarrierConfig carrierConfig= new CarrierConfig();
		Convertor.carrierConfigToDomain(carrierConfigRequest, carrierConfig);
		return apiDao.saveCarrierConfig(carrierConfig);
	}

	public TrackingNumberValidation getTrackingNumberValidationUsingCarrierCode(TrackingNumberValidation trkRequest) {
		return apiDao.getTrackingNumberValidationUsingCarrierCode(trkRequest);
	}

	@Override
	public StaticParameter getStaticParameterUsingId(Long partnerId) {
		return apiDao.getStaticParameterUsingId(partnerId);
	}

	@Override
	public QueueServers getQueueServersUsingServerName(String serverName) {
		return apiDao.getQueueServersUsingServerName(serverName);
	}

	@Override
	public CarrierConfig getCarrierConfigUsingCode(String code) {
		return apiDao.getCarrierConfigUsingCode(code);
	}

	@Override
	public List<User> getUserByPartnerId(Long partnerId) {
		return apiDao.getUserByPartnerId(partnerId);
	}

	@Override
	public List<StaticParameter> getStaticParameters(Long partnerId, String userName, String carrierCode) {
		return apiDao.getStaticParameters(partnerId, userName, carrierCode);
	}
	

	@Override
	public FileFormat saveFileFormats(FileFormatRequest fileFormatRequest) {
		FileFormat fileFormat= null;
		fileFormat= Convertor.fileFormatRequestToDomain(fileFormatRequest);
		return apiDao.saveFileFormat(fileFormat);
	}

	@Override
	public Response saveUserFile(InputStream fileData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackingNumberValidation saveEditTrackingNumberValidation(
			EditTrackingnumberValidation editTrackingNumberValidation) {
		TrackingNumberValidation tracking=new TrackingNumberValidation();
		tracking.setCarrierCode(editTrackingNumberValidation.getCarrierCode());
		tracking.setMask(editTrackingNumberValidation.getOldMask());
		TrackingNumberValidation tr= apiDao.getTrackingNumberValidationUsingCarrierCode(tracking);
		//tr.setMask(editTrackingNumberValidation.getNewMask());
		//tr.setCarrierCode(editTrackingNumberValidation.getCarrierCode());
		TrackingNumberValidation tr2=new TrackingNumberValidation();
		tr2.setCarrierCode(tr.getCarrierCode());
		tr2.setMask(editTrackingNumberValidation.getNewMask());
		tr2.setUpdateDate(new Date(System.currentTimeMillis()));
		apiDao.deleteTrackingNumberValidation(tr);
		apiDao.saveTrackingNumberValidation(tr2);
		
		return tr2;
	}
/*public static void main(String[] args) {
	ApplicationContext ctx= new ClassPathXmlApplicationContext("spring-config.xml");
	UserManagementService bean=ctx.getBean(UserManagementService.class);
	EditTrackingnumberValidation input=new EditTrackingnumberValidation();
	input.setCarrierCode("dhl");
	input.setOldMask("^(\\d)");
	input.setCarrierCode("^(\\\\d)******@");
	//bean.(input);
}*/

	
}
