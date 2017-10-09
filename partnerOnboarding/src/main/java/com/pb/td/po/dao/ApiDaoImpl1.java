package com.pb.td.po.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.orm.hibernate4.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.FileFormat;
import com.pb.td.po.domain.Partner;
import com.pb.td.po.domain.QueueServers;
import com.pb.td.po.domain.Services;
import com.pb.td.po.domain.StaticParameter;
import com.pb.td.po.domain.TrackingNumberValidation;
import com.pb.td.po.domain.User;

@EnableTransactionManagement
public class ApiDaoImpl1 extends HibernateDaoSupport implements ApiDao {

	@Override
	@Transactional(readOnly = false)
	public User saveUser(User pbUser) {
		getHibernateTemplate().saveOrUpdate(pbUser);
		return pbUser;
	}

	@Override
	public List<User> getUsers() {
		List<User> list = (List<User>) getHibernateTemplate().find("FROM  Users");
		return list;
	}

	@Override
	public User getUserByUserName(String userName) {
		List list = getHibernateTemplate().find("from Users where userName = ?", userName);

		if (list.size() > 0) {
			return (User) list.get(0);
		} else
			return null;
	}

	@Override
	public void deletePartnerInfo(Long partnerId) {
		try {Partner p=getPartnerById(partnerId);
		getHibernateTemplate().delete(p);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		getHibernateTemplate().delete(partnerId);
	}

	@Override
	public List<Partner> getPartnersInfo() {
		@SuppressWarnings("unchecked")
		List<Partner> list = (List<Partner>) getHibernateTemplate().find("from Partner");
		if (list.size() > 0) {
			return list;
		} else
			return null;
	}

	@Override
	public Partner getPartnerInfoUsingName(String name) {
		List list = getHibernateTemplate().find("from Partner where name = ?", name);
		if (list != null) {
			return (Partner) list.get(0);
		} else
			return null;
	}

	@Override
	public Partner getPartnerById(Long partnerId) {

		List list = getHibernateTemplate().find("from Partner where PartnerId = ?", partnerId);

		if (list.size() > 0) {
			return (Partner) list.get(0);
		} else
			return null;
	}

	@Override
	@Transactional(readOnly = false)
	public Partner savePartnerInfo(Partner partner) {
		getHibernateTemplate().saveOrUpdate(partner);
		return partner;
	}

	@Override
	@Transactional(readOnly = false)
	public QueueServers saveQueueServers(QueueServers queueServers) {
		getHibernateTemplate().saveOrUpdate(queueServers);
		return queueServers;
	}

	@Override
	public List<QueueServers> getQueueServers() {
		List<QueueServers> list = (List<QueueServers>) getHibernateTemplate().find("from QueueServers");
		if (list.size() > 0) {
			return list;
		} else
			return null;
	}

	/*	@Override
	public void deleteQueueServer(String serverName) {
		getHibernateTemplate().delete(serverName);
	}
	 */
	@Override
	public List<FileFormat> getFileFormat(Long partnerId) {
		@SuppressWarnings("unchecked")
		List<FileFormat> list = (List<FileFormat>) getHibernateTemplate().find("from FileFormat where partnerId=?",partnerId);
		if (list.size() > 0) {
			return list;
		} else
			return null;
	}

	@Override
	public List<Services> getAllServices() {
		@SuppressWarnings("unchecked")
		List<Services> list = (List<Services>) getHibernateTemplate().find("FROM  Services");
		return list;
	}

	@Override
	public List<String> getTrackingHeaderName() throws IOException, Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly = false)
	public StaticParameter saveStaticParameter(StaticParameter staticParameter) {
		getHibernateTemplate().saveOrUpdate(staticParameter);
		return staticParameter;
	}
	/*
	@Override
	@Transactional(readOnly = false)
	public void deleteStaticParameter(Long partnerId) {
		try {
			getHibernateTemplate().delete(partnerId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	 */
	@Override
	public List<StaticParameter> getStaticParameter() {
		@SuppressWarnings("unchecked")
		List<StaticParameter> list = (List<StaticParameter>) getHibernateTemplate().find("FROM  StaticParameter");
		return list;
	}

	@Override
	@Transactional(readOnly = false)	
	public TrackingNumberValidation saveTrackingNumberValidation(TrackingNumberValidation trackingNumberValidation) {
		getHibernateTemplate().save(trackingNumberValidation);
		return trackingNumberValidation;
	}
	
/*	
	@Override
	@Transactional(readOnly = false)
	public TrackingNumberValidation saveTrackingNumberValidationUsingCarrierCodeForEdit(TrackingNumberValidation trackingNumberValidation,TrackingNumberValidation oldRequest) {
		String carrierCode=oldRequest.getCarrierCode();
		String mask=oldRequest.getMask();
		TrackingNumberValidation list = (TrackingNumberValidation) getHibernateTemplate().find("from TrackingNumberValidation where CarrierCode = ? and Mask =?", carrierCode,mask);
		list.setMask(trackingNumberValidation.getMask());
		return list;
	}*/
	/*
	@Override
	public void deleteTrackingNumberValidation(String carrierCode) {
	
	}
	 */
	@Override
	public List<TrackingNumberValidation> getTrackingNumberValidation() {
		@SuppressWarnings("unchecked")
		List<TrackingNumberValidation> list = (List<TrackingNumberValidation>) getHibernateTemplate().find("FROM  TrackingNumberValidation");
		return list;
	}

	@Override
	public void saveUserFile(HttpServletRequest request) {
	}

	@Override
	@Transactional(readOnly = false)
	public CarrierConfig saveCarrierConfig(CarrierConfig carrierConfig) {
		getHibernateTemplate().saveOrUpdate(carrierConfig);
		return carrierConfig;
	}

	@Override
	public List<CarrierConfig> getCarrierConfig() {
		@SuppressWarnings("unchecked")
		List<CarrierConfig> list = (List<CarrierConfig>) getHibernateTemplate().find("FROM  CarrierConfig");
		return list;
	}

	@Override
	public QueueServers getQueueServersUsingServerName(String serverName) {
		List list = getHibernateTemplate().find("from QueueServers where ServerName = ?", serverName);

		if (list.size() > 0) {
			return (QueueServers) list.get(0);
		} else
			return null;
	}

	@Override
	public StaticParameter getStaticParameterUsingId(Long partnerId) {
		List list = getHibernateTemplate().find("from StaticParameter where partnerId = ?", partnerId);

		if (list.size() > 0) {
			return (StaticParameter) list.get(0);
		} else
			return null;
	}

	@Override
	public TrackingNumberValidation getTrackingNumberValidationUsingCarrierCode(TrackingNumberValidation trkVal) {
		String carrierCode=trkVal.getCarrierCode();
		String mask=trkVal.getMask();
		List list = getHibernateTemplate().find("from TrackingNumberValidation where CarrierCode = ? and Mask =?", carrierCode,mask);
		//TrackingNumberValidation updated=saveTrackingNumberValidation(list);
		if (list.size() > 0) {
			return (TrackingNumberValidation) list.get(0);
		} else
			return null;
		//return list;
	}

	@Override
	public CarrierConfig getCarrierConfigUsingCode(String code) {
		List list = getHibernateTemplate().find("from CarrierConfig where Code = ?", code);

		if (list.size() > 0) {
			return (CarrierConfig) list.get(0);
		} else
			return null;
	}

	@Override
	public List<User> getUserByPartnerId(Long partnerId) {
		@SuppressWarnings("unchecked")
		List<User> list = (List<User>) getHibernateTemplate().find("FROM Users c where c.partner.partnerId=?",partnerId);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<StaticParameter> getStaticParameters(Long partnerId,String userName,String carrierCode) {
		List<StaticParameter> list=null;
		if(!carrierCode.equalsIgnoreCase("null")&&!(userName.equalsIgnoreCase("null"))){
			list = (List<StaticParameter>)getHibernateTemplate().find("from StaticParameter  where partnerId = ? AND userName = ? AND carrierCode = ?",partnerId,userName,carrierCode);
		}
		else if(carrierCode.equalsIgnoreCase("null")&&(userName.equalsIgnoreCase("null")))
			{
				list = (List<StaticParameter>)getHibernateTemplate().find("from StaticParameter where partnerId = ?",partnerId);
			}
		else if(carrierCode.equalsIgnoreCase("null")){
			{
				list = (List<StaticParameter>)getHibernateTemplate().find("from StaticParameter  where partnerId = ? AND userName = ?",partnerId,userName);
			}
		}else{
			list = (List<StaticParameter>) getHibernateTemplate().find("FROM  CarrierConfig");
		}
		return list;
	}

	@Override
	public FileFormat saveFileFormat(FileFormat fileFormat) {
		getHibernateTemplate().saveOrUpdate(fileFormat);
		return fileFormat;
	}

	@Override
	public void deleteTrackingNumberValidation(TrackingNumberValidation trackingNumberValidation) {
		getHibernateTemplate().delete(trackingNumberValidation);
	}
	

}
