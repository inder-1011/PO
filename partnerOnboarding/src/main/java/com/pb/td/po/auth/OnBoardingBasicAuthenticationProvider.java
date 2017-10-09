/**
 * 
 */
package com.pb.td.po.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.pb.td.po.dao.ApiDao;
import com.pb.td.po.domain.User;

/**
 * @author indrajeet.verma@pb.com
 * 
 *
 */
@Component
public class OnBoardingBasicAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	ApiDao apiDao; 
	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#authenticate(org.springframework.security.core.Authentication)
	 */
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		 String name = authentication.getName();
	     String password = authentication.getCredentials().toString();
	 
	        // use the credentials to try to authenticate against the third party system
	     User user = null;
	     try{
	    	 user = apiDao.getUserByUserName(name);
	     }catch(Exception e){
	    	 throw new AuthenticationCredentialsNotFoundException("No User Found with provided credentials");
	     }
	     if (user!=null && user.getPassword().equals(password)) {
	    	final String userRole = user.getRole();
	    	GrantedAuthority ga = new GrantedAuthority() {
	    		 private static final long serialVersionUID = 1L;
	    		 @Override
	    		 public String getAuthority() {
	    			 return userRole;
	    		 }
	    	 };
	    	 List<GrantedAuthority> grantedAuths = new ArrayList<GrantedAuthority>();
	    	 grantedAuths.add(ga);
	    	 user.setUserName(name);
	    	 return new UsernamePasswordAuthenticationToken(user, password, grantedAuths);
	     } else {
	    	 throw new AuthenticationCredentialsNotFoundException("No User Found with provided credentials");
	     }
	}


	/* (non-Javadoc)
	 * @see org.springframework.security.authentication.AuthenticationProvider#supports(java.lang.Class)
	 */
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}
