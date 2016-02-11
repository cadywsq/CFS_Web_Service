package edu.cmu.webapp.task8.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.beanparam.CreateFundBeanParam;
import edu.cmu.webapp.task8.resource.CreateFundAction;
import edu.cmu.webapp.task8.formbean.CreateFundFormBean;

@Path("/createFund")
public class CreateFund {
	CreateFundFormBean createFundForm = new CreateFundFormBean();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public  List<MessageJSON> createFund(@Context HttpServletRequest request, @BeanParam CreateFundBeanParam createFundBeanParam) {
		return new CreateFundAction(createFundBeanParam.getCreateFundForm()).perform(request);
	}
}