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
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.pb.td.po.util.OnBoardingResponseBuilder;
import com.pb.td.po.util.POConstants;
import com.pb.td.po.util.OnBoardingErrorResponse;

/**
 * @author at002ya
 *
 */
@Component
public class OnBoardingAuthenticationEntryPoint implements AuthenticationEntryPoint {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.AuthenticationEntryPoint#commence(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.AuthenticationException)
	 */
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
			throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON);
        PrintWriter writer = response.getWriter();
        
        OnBoardingErrorResponse error = new OnBoardingErrorResponse();
		error.setError(POConstants.ERROR_UNAUTHORIZED_CODE);
		error.setMessage(POConstants.ERROR_UNAUTHORIZED_MESSAGE);
		Response resp = OnBoardingResponseBuilder.buildResponse(error, Response.Status.UNAUTHORIZED);
		
        ObjectMapper mapper = new ObjectMapper();
        String result = mapper.writeValueAsString(resp.getEntity());
        writer.println(result);
	}

}
