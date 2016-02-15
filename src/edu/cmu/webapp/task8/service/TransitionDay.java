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
import edu.cmu.webapp.task8.beanparam.SellFundBeanParam;
import edu.cmu.webapp.task8.resource.SellFundAction;
import edu.cmu.webapp.task8.resource.TransitionDayAction;;
@Path("/transitionDay")
public class TransitionDay {
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public  List<MessageJSON> transitionDay(@Context HttpServletRequest request) {
        return new TransitionDayAction().perform(request);
    }	
}
