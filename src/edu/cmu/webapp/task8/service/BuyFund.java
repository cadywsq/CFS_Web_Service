package edu.cmu.webapp.task8.service;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import edu.cmu.webapp.task8.JSON.Menu;
import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.beanparam.BuyFundBeanParam;
import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.resource.BuyFundAction;
import edu.cmu.webapp.task8.resource.Controller;
import edu.cmu.webapp.task8.resource.LoginAction;

@Path("/buyFund")
public class BuyFund {
    BuyFormBean buyFundForm = new BuyFormBean();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public  List<MessageJSON> buyFund(@Context HttpServletRequest request, @BeanParam BuyFundBeanParam buyFundBeanParam) {
        return new BuyFundAction(buyFundBeanParam.getBuyFormBean()).perform(request);
    }
}

