/*package com.pb.td.po.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.FileFormat;
import com.pb.td.po.domain.Partner;
import com.pb.td.po.domain.QueueServers;
import com.pb.td.po.domain.Services;
import com.pb.td.po.domain.StaticParameter;
import com.pb.td.po.domain.TrackingNumberValidation;
import com.pb.td.po.domain.User;
import com.pb.td.po.dto.FileFormatRequest;

@Repository
public class ApiDaoImpl  {


	@Autowired
	private SessionFactory sessionFactory;

	 
	public User saveUser(User pbUser) {
		Session sessoion = sessionFactory.openSession();
		org.hibernate.Transaction Transaction = sessoion.beginTransaction();
		sessoion.saveOrUpdate(pbUser);
		Transaction.commit();
		sessoion.close();
		return pbUser;
	}


	public void saveUserFile(@Context HttpServletRequest request) {
	}

	 
	public List<User> getUsers() {
		Session session = sessionFactory.openSession();
		List<User> list = session.createQuery("from Users where active=1")
				.list();
		for (User user : list) {
			Hibernate.initialize(user.getServices());
			if(user.getPartner()!=null){
				Hibernate.initialize(user.getPartner());
				Hibernate.initialize(user.getPartner().getFileFormats());
				Hibernate.initialize(user.getPartner().getFileTransferDetails());
			}
		}
		session.close();
		return list;
	}
	 
	public User getUserByUserName(String userName) {
		User user = null;
		try{	
			
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from Users where userName ='"+userName+"'");
		List<User> userList = q.list();
		if(userList!=null && userList.size()>0){
			user = (User) q.list().get(0);
			Hibernate.initialize(user.getServices());
		}
		//System.out.println(user);
		session.close();
		}catch(Exception e){
			System.out.println(" haha "+e.getMessage());
		}
		return user;

	}

	public void deleteUser(String userName) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction tx = session.beginTransaction();
		User user = (User) session.load(User.class, userName);
		user.setActive(false);
		tx.commit();
		session.close();

	}

	public Partner savePartnerInfo(Partner partner) {
		Session sessoion = sessionFactory.openSession();
		org.hibernate.Transaction Transaction = sessoion.beginTransaction();
		sessoion.saveOrUpdate(partner);
		Transaction.commit();
		sessoion.close();
		return partner;

	}

	public void deletePartnerInfo(Long partnerId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		Partner partner = (Partner) session.load(Partner.class, partnerId);
		session.delete(partner);
		tx.commit();
		session.close();

	}

	 
	public List<Partner> getPartnersInfo() {
		Session session = sessionFactory.openSession();
		@SuppressWarnings("unchecked")
		List<Partner> list = session.createQuery("from Partner")
		.list();
		
		for (Partner partner : list) {
			Hibernate.initialize(partner.getFileTransferDetails());
			Hibernate.initialize(partner.getFileFormats());
		}
		session.close();
		return list;

	}

	 
	public Partner getPartnerInfoUsingName(String name){
		Partner partner = null;
		Session session = sessionFactory.openSession();
		Query q = session.createQuery("from Partner where name ='"+ name+"'");
		List<User> partnerList = q.list();
		if(partnerList!=null && partnerList.size()>0){
			partner = (Partner) q.list().get(0);
			Hibernate.initialize(partner.getFileTransferDetails());
			Hibernate.initialize(partner.getFileFormats());
		}
		System.out.println(partner);
		session.close();
		return partner;
	}	
	
	 
	public Partner getPartnerById(Long partnerId){
		if(partnerId==null)return null;
		Session sessoion = sessionFactory.openSession();
		org.hibernate.Transaction Transaction = sessoion.beginTransaction();
		Partner partner  = (Partner)sessoion.get(Partner.class,partnerId);
		if(partner!=null){
		Hibernate.initialize(partner.getFileTransferDetails());
		Hibernate.initialize(partner.getFileFormats());
		Transaction.commit();
		}
		sessoion.close();
		return partner;
	}	
	
	public Partner getPartnerById(Long partnerId){
		Session session = sessionFactory.openSession();
		Partner partner = (Partner) session.load(Partner.class, new Long (partnerId));
		session.close();
		return partner;
	}
	
	 
	public List<String> getTrackingHeaderName() throws Exception {
		BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\nex7kjt\\Desktop\\inderr.txt"));
		String line = null;
		while ((line = br.readLine()) != null) {
			String[] values = line.split("//t");
			FileFormatRequest fileFormatRequest= new FileFormatRequest();

			{
			
				String[] newVal = line.split(",");
				for(int i=0;i<newVal.length;i++){
					fileFormatRequest.setAlias(newVal[0]);
					int x= Integer.parseInt(newVal[1]);
					fileFormatRequest.setColumnOrder(x);
					//fileFormatRequest.setColumnName(newVal[2]);
					fileFormatRequest.setFormat(newVal[3]);
					fileFormatRequest.setRequired(true);
				}
				
			}


		}
		return null;

	}


	 
	public List<Services> getAllServices() {
		Session session = sessionFactory.openSession();
		List<Services> list = session.createQuery("from Services").list();
		session.close();
		return list;
	}


	 
	public List<FileFormat> getFileFormat(Long partnerId) {
		Session session = sessionFactory.openSession();
		List<FileFormat> list = session.createQuery("from FileFormat where partnerId="+partnerId).list();
		session.close();
		return list;
	}


	 
	public List<QueueServers> getQueueServers() {
		Session session= sessionFactory.openSession();
		List<QueueServers> list = session.createQuery("from QueueServers").list();
		session.close();
		return list;
	}


	 
	public QueueServers saveQueueServers(QueueServers queueServers) {
		Session session = sessionFactory.openSession();
		org.hibernate.Transaction transaction= session.beginTransaction();
		session.saveOrUpdate(queueServers);
		transaction.commit();
		session.close();
		return queueServers;
	}


	 
	public StaticParameter saveStaticParameter(StaticParameter staticParameter) {
		Session session= sessionFactory.openSession();
		org.hibernate.Transaction Transaction= session.beginTransaction();
		session.saveOrUpdate(staticParameter);
		Transaction.commit();
		session.close();
		return staticParameter;
	}


	 
	public List<StaticParameter> getStaticParameter() {
		Session session=sessionFactory.openSession();
		List<StaticParameter> list= session.createQuery("from StaticParameter").list();
		session.close();
		return list;
	}


	 
	public TrackingNumberValidation saveTrackingNumberValidation(TrackingNumberValidation trackingNumberValidation) {
		Session session= sessionFactory.openSession();
		org.hibernate.Transaction transaction = session.beginTransaction();
		session.saveOrUpdate(trackingNumberValidation);
		transaction.commit();
		session.close();
		return trackingNumberValidation;
	}


	 
	public List<TrackingNumberValidation> getTrackingNumberValidation() {
		Session session = sessionFactory.openSession();
		List<TrackingNumberValidation> list = session.createQuery("from TrackingNumberValidation").list();
		session.close();
		return list;
	}
	


	public void deleteQueueServer(String serverName) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		QueueServers queueServers= (QueueServers) session.load(QueueServers.class, serverName);
		session.delete(queueServers);
		tx.commit();
		session.close();
	}


	public void deleteStaticParameter(Long partnerId) {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		StaticParameter staticParameter=(StaticParameter) session.load(StaticParameter.class, partnerId);
		session.delete(staticParameter);
		tx.commit();
		session.close();
	}


	public void deleteTrackingNumberValidation(String carrierCode) {

		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		TrackingNumberValidation trackingNumberValidation= (TrackingNumberValidation) session.load(TrackingNumberValidation.class, carrierCode);
		session.delete(trackingNumberValidation);
		tx.commit();
		session.close();
	}


	 
	public CarrierConfig saveCarrierConfig(CarrierConfig carrierConfig) {
		Session session=sessionFactory.openSession();
		org.hibernate.Transaction transaction=session.beginTransaction();
		session.saveOrUpdate(carrierConfig);
		transaction.commit();
		session.close();
		return carrierConfig;
	}


	 
	public List<CarrierConfig> getCarrierConfig() {
		Session session= sessionFactory.openSession();
		List<CarrierConfig> list= session.createQuery("from CarrierConfig").list();
		session.close();
		return list;
	}
	
	public void deleteCrrierConfig(String code) {
		Session session = sessionFactory.openSession();
		Transaction transaction= session.beginTransaction();
		CarrierConfig carrierConfig= (CarrierConfig) session.load(CarrierConfig.class, code);
		session.delete(carrierConfig);
		transaction.commit();
		session.close();
		
	}


	


	
}
*/