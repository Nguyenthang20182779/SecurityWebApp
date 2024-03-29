package request;

import java.security.Principal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

//
public class UserRoleRequestWrapper extends HttpServletRequestWrapper{
	private String user;
	private List<String> roles = null;
	private HttpServletRequest realRequest;
	public UserRoleRequestWrapper(String user, List<String> roles, HttpServletRequest realRequest) {
		super(realRequest);
		// TODO Auto-generated constructor stub
		this.user = user;
		this.roles = roles;
		this.realRequest = realRequest;
	}
	
	@Override
	public boolean isUserInRole(String role) {
		if (roles == null) {
			return this.realRequest.isUserInRole(role);
		}
		return roles.contains(role);
	}
	@Override
	public Principal getUserPrincipal() {
		if (this.user == null) {
			return realRequest.getUserPrincipal();
		}
		return new Principal() {
			@Override
			public String getName() {
				return user;
			}
		};
	}
}
