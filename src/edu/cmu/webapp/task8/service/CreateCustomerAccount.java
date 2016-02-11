package edu.cmu.webapp.task8.service;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.BeanParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.beanparam.CreateCustomerBeanParam;
import edu.cmu.webapp.task8.resource.CreateCustomerAccountAction;
import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;

@Path("/createCustomerAccount")
public class CreateCustomerAccount {
	CreateCustomerFormBean createCustomerFormBean = new CreateCustomerFormBean();
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public List<MessageJSON> createAccount(@Context HttpServletRequest request, 
								@BeanParam CreateCustomerBeanParam createCustomerBeanParam){
        return new CreateCustomerAccountAction(createCustomerBeanParam.getCustomerFormbean()).perform(request);
	
	}
	

}
