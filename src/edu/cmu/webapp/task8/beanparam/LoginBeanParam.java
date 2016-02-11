package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.QueryParam;

import edu.cmu.webapp.task8.formbean.LoginFormBean;

public class LoginBeanParam {
	private @QueryParam("username") String username;
    private @QueryParam("password") String password;
	private LoginFormBean loginFormBean;
    public LoginBeanParam() {
    	loginFormBean = new LoginFormBean();
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginFormBean getLoginFormBean() {
		loginFormBean.setUsername(this.username);
		loginFormBean.setPassword(this.password);
		return loginFormBean;
	}
}
