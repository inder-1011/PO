/**
 * 
 */
package com.pb.td.po.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;

/**
 * @author indrajeet.verma@pb.com
 *
 * Apr 6, 2016
 */
@Component
public class OnBoardingRequestMatcher implements RequestMatcher {

	/* (non-Javadoc)
	 * @see org.springframework.security.web.util.matcher.RequestMatcher#matches(javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public boolean matches(HttpServletRequest request) {
		request.getHeader("Authorization");
		return (request.getParameter("_wadl")!=null);
				
	}

}
