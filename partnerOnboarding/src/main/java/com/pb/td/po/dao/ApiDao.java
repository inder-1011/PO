package com.pb.td.po.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.FileFormat;
import com.pb.td.po.domain.Partner;
import com.pb.td.po.domain.QueueServers;
import com.pb.td.po.domain.Services;
import com.pb.td.po.domain.StaticParameter;
import com.pb.td.po.domain.TrackingNumberValidation;
import com.pb.td.po.domain.User;

public interface ApiDao {
	public void deleteTrackingNumberValidation(TrackingNumberValidation trackingNumberValidation);
	public User saveUser(User pbUser);
	void saveUserFile(HttpServletRequest request);
	//public void deleteUser(String userName);
	public List<User> getUsers();
	public User getUserByUserName(String userName);
	
	public void deletePartnerInfo(Long userId);
	public List<Partner> getPartnersInfo();
	public Partner getPartnerInfoUsingName(String name);
	public Partner getPartnerById(Long partnerId);
	public Partner savePartnerInfo(Partner partner);
	public QueueServers saveQueueServers(QueueServers queueServers);
	public List<QueueServers> getQueueServers();
	//public void deleteQueueServer(String serverName);
	public QueueServers getQueueServersUsingServerName(String serverName);
	
	public List<FileFormat> getFileFormat(Long partnerId);
	
	public StaticParameter saveStaticParameter(StaticParameter staticParameter);
	
	public List<Services> getAllServices();
	
	public List<User> getUserByPartnerId(Long partnerId);

	public List<String> getTrackingHeaderName() throws IOException, Exception;
	//public void deleteStaticParameter(Long partnerId);
	public List<StaticParameter> getStaticParameter();
	public StaticParameter getStaticParameterUsingId(Long partnerId);
	
	public TrackingNumberValidation saveTrackingNumberValidation(TrackingNumberValidation trackingNumberValidation);
	//public void deleteTrackingNumberValidation(String carrierCode);
	public List<TrackingNumberValidation> getTrackingNumberValidation();
	//public TrackingNumberValidation getTrackingNumberValidationUsingCarrierCode(String carrierCode);
	
	public CarrierConfig saveCarrierConfig(CarrierConfig carrierConfig);
	//public void deleteCrrierConfig(String code);
	public List<CarrierConfig> getCarrierConfig();
	public CarrierConfig getCarrierConfigUsingCode(String code);
	
	public List<StaticParameter>  getStaticParameters(Long partnerId,String userName,String CarrierCode);
	
	public FileFormat saveFileFormat(FileFormat fileFormat);
	public TrackingNumberValidation getTrackingNumberValidationUsingCarrierCode(TrackingNumberValidation trackingNumberValidation);
	//public TrackingNumberValidation saveTrackingNumberValidationUsingCarrierCodeForEdit(TrackingNumberValidation trackingNumberValidation,TrackingNumberValidation oldRequest);
	
	
}
