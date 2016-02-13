package edu.cmu.webapp.task8.service;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import edu.cmu.webapp.task8.JSON.LoginJSON;
import edu.cmu.webapp.task8.beanparam.LoginBeanParam;
import edu.cmu.webapp.task8.resource.Controller;
import edu.cmu.webapp.task8.resource.LoginAction;

@Path("/login")
public class Login {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public LoginJSON login(@Context HttpServletRequest request,@BeanParam LoginBeanParam loginBeanParam) {
		System.out.println("Check");
		Controller.databasePrecheck();
		return new LoginAction(loginBeanParam.getLoginFormBean()).perform(request);
	}
}
