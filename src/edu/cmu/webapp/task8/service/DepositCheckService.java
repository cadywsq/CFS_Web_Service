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
import edu.cmu.webapp.task8.beanparam.DepositCheckBeanParam;
import edu.cmu.webapp.task8.formbean.DepositCheckFormBean;
import edu.cmu.webapp.task8.resource.DepositCheckAction;
/**
 * 
 * @author Hunter Na
 *
 */
@Path("/depositCheck")
public class DepositCheckService {
    DepositCheckFormBean depositCheckForm = new DepositCheckFormBean();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public  List<MessageJSON> depositCheck(@Context HttpServletRequest request, @BeanParam DepositCheckBeanParam depositCheckBeanParam) {
        return new DepositCheckAction(depositCheckBeanParam.getDepositCheckFormBean()).perform(request);
    }
}
