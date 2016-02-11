package edu.cmu.webapp.task8.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import edu.cmu.webapp.task8.JSON.Menu;
import edu.cmu.webapp.task8.controller.CreateFundAction;
import edu.cmu.webapp.task8.formbean.CreateFundFormBean;
import edu.cmu.webapp.task8.resource.Controller;
import edu.cmu.webapp.task8.resource.LoginAction;

@Path("/createFund")
public class CreateFund {
	CreateFundFormBean createFundForm = new CreateFundFormBean();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<Menu> createFund(@Context HttpServletRequest request,@QueryParam("fundName") String fundName, @QueryParam("symbol") String symbol) {
		
		List<String> errors = new ArrayList<String>();
		createFundForm.setFundName(fundName);
		createFundForm.setSymbol(symbol);
		errors = createFundForm.getValidationErrors();
		if(errors.size()>0){
			
		}
		Controller.databasePrecheck();
		return new CreateFundAction(createFundForm).perform(request);
	}
}