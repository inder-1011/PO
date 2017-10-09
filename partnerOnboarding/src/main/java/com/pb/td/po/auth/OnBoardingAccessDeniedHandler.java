/**
 * 
 */
package com.pb.td.po.auth;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.pb.td.po.util.OnBoardingErrorResponse;
import com.pb.td.po.util.OnBoardingResponseBuilder;
import com.pb.td.po.util.POConstants;

/**
 * @author at002ya
 *
 */
@Component
public class OnBoardingAccessDeniedHandler implements AccessDeniedHandler {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.access.AccessDeniedHandler#handle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.access.AccessDeniedException)
	 */
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
			
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType(MediaType.APPLICATION_JSON);
            PrintWriter writer = response.getWriter();
            
            OnBoardingErrorResponse error = new OnBoardingErrorResponse();
    		error.setError(POConstants.ERROR_UNAUTHORIZED_CODE);
    		if(request.getMethod().equals(HttpMethod.DELETE.name())){
    			error.setMessage(POConstants.ERROR_UNAUTHORIZED_DELETE_MESSAGE);
    		}else if(request.getMethod().equals(HttpMethod.GET.name())){
    			error.setMessage(POConstants.ERROR_UNAUTHORIZED_MESSAGE);
    		}else if(request.getMethod().equals(HttpMethod.POST.name())){
    			error.setMessage(POConstants.ERROR_UNAUTHORIZED_POST_MESSAGE);
    		}else{
	    		error.setMessage(POConstants.ERROR_UNAUTHORIZED_MESSAGE);
    		}
    		Response resp = OnBoardingResponseBuilder.buildResponse(error, Response.Status.UNAUTHORIZED);
    		
            ObjectMapper mapper = new ObjectMapper();
            String result = mapper.writeValueAsString(resp.getEntity());
            writer.println(result);
    }

}
