

/**
 * 
 * 
 * @copyrights Copyright 1996-2015 Pitney Bowes Inc. All rights reserved.
 * @author indrajeet.verma@pb.com
 *
 * 
 */

package com.pb.td.po.services;

import java.io.InputStream;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.security.access.prepost.PreAuthorize;

import com.pb.td.po.domain.CarrierConfig;
import com.pb.td.po.domain.EditTrackingnumberValidation;
import com.pb.td.po.domain.FileFormat;
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

/**
 * @author indrajeet.verma@pb.com
 * Apr 5, 2016 </br>
 * <b>Support Soap and Rest</b></br> 
 * This Service Interface provides various API to manage a User/Partner</br>
 * UserManagementService.saveUserFile for file upload process to create a user.<br>
 * UserManagementService.saveUser to create user using UI. <br>
 * UserManagementService.deleteUser to make users inactive. <br>
 * UserManagementService.getUsers to retrieve active user list. <br>
 * UserManagementService.getuser to retrieve user through id. <br>
 * UserManagementService.
 */


@WebService
@Consumes(MediaType.APPLICATION_JSON)
@Produces({MediaType.APPLICATION_JSON})
@Transactional
public interface UserManagementService {


	/**
	 * This API takes CSV file as input stream and create a user based on Info provided inside CSV.
	 * @param <code>FileUploader</code>
	 * @return <code>Response<code>
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@POST
	@Path("/saveUserFile")
	@WebMethod
	@Consumes({MediaType.MULTIPART_FORM_DATA})
	public Response saveUserFile(InputStream fileData); 

	/**
	 * This API takes response from User and saves its details 
	 *@param <code> UserRequest </code>
	 *@return <code> User </code>
	 */
	@PreAuthorize("hasRole('ADMIN')")
	@POST
	@Path("/saveUser")
	@WebMethod
	public User saveUser(UserRequest userRequest);


	
	@POST
	@Path("/saveFileFormats")
	@WebMethod
	public FileFormat saveFileFormats(FileFormatRequest fileFormatRequest);

	/**
	 * This api makes active Users inactive
	 *@param <code>userId</code>
	 * 
	 */
/*	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deleteUser/{userName}")
	@WebMethod
	public List<User> deleteUser(@PathParam("userName") String userName);*/
	
	
	/**
	 * This api lists down the active Users
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getUsers")
	@WebMethod
	public List<User> getUsers();

	/**
	 * This api gives details of the particular User through userId
	 *@param <code>userId</code>
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getUser/{userName}")
	@WebMethod
	public User getUser(@PathParam("userName") String userName);
	
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getFileFormat/{partnerId}")
	@WebMethod
	public List<FileFormat> getFileFormat(@PathParam("partnerId") Long partnerId);
	
	@GET
	@Path("/getUserByPartnerId/{partnerId}")
	@WebMethod
	public List<User> getUserByPartnerId(@PathParam("partnerId")Long partnerId);

	@GET
	@Path("/getStaticParameters/partnerId/{partnerId}/userName/{userName}/carrierCode/{carrierCode}")
	@WebMethod
	public List<StaticParameter> getStaticParameters(@PathParam("partnerId") Long partnerId,@PathParam("userName") String userName,@PathParam("carrierCode") String carrierCode);
	
	
	

	/**
	 * This api saves details of the Partner through UI.
	 *@param <code>PartnerRequest</code>
	 *@return <code> Partner </code>
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@POST
	@Path("/savePartnerInfo")
	@WebMethod
	public Partner savePartnerInfo(PartnerRequest partnerRequest);

	/**
	 * This api makes active Partners inactive.
	 *@param <code>partnerId</code>
	 * 
	 */
	/*@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deletePartnerInfo/{partnerId}")
	@WebMethod
	public List<Partner> deletePartnerInfo(@PathParam("partnerId") Long partnerId);*/


	/**
	 * This api displays details of the active Partner.
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getPartnersInfo")
	@WebMethod
	public List<Partner> getPartnersInfo();
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@POST
	@Path("/saveQueueServers")
	@WebMethod
	public QueueServers saveQueueServers(QueueServerRequest queueServerRequest);
	
	@PreAuthorize("hasRole('ADMIN')")
	@GET
	@Path("/getQueueServers")
	@WebMethod
	public List<QueueServers> getQueueServers();
	
	@PreAuthorize("hasRole('ADMIN')") 
	@POST
	@Path("/saveStaticParameter")
	@WebMethod
	public StaticParameter saveStaticParameter(StaticParameterRequest staticParameterRequest);
	
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getStaticParameter")
	@WebMethod
	public List<StaticParameter> getStaticParameter();
	
	
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getTrackingNumberValidation")
	@WebMethod
	public List<TrackingNumberValidation> getTrackingNumberValidation();

	@PreAuthorize("hasRole('ADMIN')") 
	@POST
	@Path("/saveTrackingNumberValidation")
	@WebMethod
	public TrackingNumberValidation saveTrackingNumberValidation(TrackingNumberValidationRequest trackingNumberValidation);

	
	@PreAuthorize("hasRole('ADMIN')") 
	@POST
	@Path("/saveEditTrackingNumberValidation")
	@WebMethod
	public TrackingNumberValidation saveEditTrackingNumberValidation(EditTrackingnumberValidation editTrackingNumberValidation);
	/**
	 * This api displays details of the Partner using partnerId
	 *@param <code>partnerIds</code>
	 *@return <code> Partner </code>
	 * 
	 */
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getPartnerInfoUsingName/{name}")
	@WebMethod
	public Partner getPartnerInfoUsingName(String name);
	
	
	
	@GET
	@Path("/getPartnerById/{partnerId}")
	@WebMethod
	public Partner getPartnerById(@PathParam("partnerId") Long partnerId);
	
	/**
	 * @param userRequest
	 * @return
	 */
	/*@PreAuthorize("hasRole('ADMIN')")*/
	@POST
	@Path("/loginUser")
	@WebMethod
	public User loginUser(UserRequest userRequest);
	
/*	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deleteStaticParameter/{partnerId}")
	@WebMethod
	public List<StaticParameter> deleteStaticParameter(@PathParam("partnerId") Long partnerId);*/

	
/*	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deleteTrackingNumberValidation/{carrierCode}")
	@WebMethod
	public List<TrackingNumberValidation> deleteTrackingNumberValidation(@PathParam("carrierCode") String carrierCode);
	*/
	
/*	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deleteQueueServer/{serverName}")
	@WebMethod
	public List<QueueServers> deleteQueueServer(@PathParam("serverName") String serverName);

	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/deleteCarrierConfig/{code}")
	@WebMethod
	public List<CarrierConfig> deleteCarrierConfig(@PathParam("code") String code);
*/
	@PreAuthorize("hasRole('ADMIN')") 
	@GET
	@Path("/getCarrierConfig")
	@WebMethod
	public List<CarrierConfig> getCarrierConfig();
	
	@PreAuthorize("hasRole('ADMIN')")
	@POST
	@Path("/saveCarrierConfig")
	@WebMethod
	public CarrierConfig saveCarrierConfig(CarrierConfigRequest carrierConfigRequest);
	
	@PreAuthorize("hasRole('ADMIN')")
	@POST
	@Path("/getTrackingNumberValidationUsingCarrierCode")
	@WebMethod
	public TrackingNumberValidation getTrackingNumberValidationUsingCarrierCode(TrackingNumberValidation trackingRequest);
	
	@PreAuthorize("hasRole('ADMIN')")
	@GET
	@Path("/getStaticParameterUsingId/{partnerId}")
	@WebMethod
	public StaticParameter getStaticParameterUsingId(@PathParam("partnerId") Long partnerId);
	
	@PreAuthorize("hasRole('ADMIN')")
	@GET
	@Path("/getQueueServersUsingServerName/{serverName}")
	@WebMethod
	public QueueServers getQueueServersUsingServerName(@PathParam("serverName") String serverName);
	
	@PreAuthorize("hasRole('ADMIN')")
	@GET
	@Path("/getCarrierConfigUsingCode/{code}")
	@WebMethod
	public CarrierConfig getCarrierConfigUsingCode(@PathParam("code") String code);


}
