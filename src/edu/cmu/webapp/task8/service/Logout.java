package edu.cmu.webapp.task8.service;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.resource.Controller;
import edu.cmu.webapp.task8.resource.LogoutAction;

@Path("/logout")
public class Logout {	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public MessageJSON login(@Context HttpServletRequest request) {
		Controller.databasePrecheck();
		return new LogoutAction().perform(request);
	}
}
