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
import edu.cmu.webapp.task8.beanparam.RequestCheckBeanParam;
import edu.cmu.webapp.task8.formbean.RequestCheckFormBean;
import edu.cmu.webapp.task8.resource.RequestCheckAction;

/**
 * 
 * @author Hunter Na
 *
 */
@Path("/requestCheck")
public class RequestCheckService {
    RequestCheckFormBean requestCheckForm = new RequestCheckFormBean();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public  List<MessageJSON> requestCheck(@Context HttpServletRequest request, @BeanParam RequestCheckBeanParam requestCheckBeanParam) {
        return new RequestCheckAction(requestCheckBeanParam.getRequestCheckFormBean()).perform(request);
    }
}
