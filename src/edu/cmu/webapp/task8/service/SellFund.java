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
import edu.cmu.webapp.task8.beanparam.SellFundBeanParam;
import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.formbean.SellFundFormBean;
import edu.cmu.webapp.task8.resource.BuyFundAction;
import edu.cmu.webapp.task8.resource.Controller;
import edu.cmu.webapp.task8.resource.LoginAction;
import edu.cmu.webapp.task8.resource.SellFundAction;

@Path("/sellFund")
public class SellFund {
    SellFundFormBean sellFundForm = new SellFundFormBean();
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public  List<MessageJSON> sellFund(@Context HttpServletRequest request, @BeanParam SellFundBeanParam sellFundBeanParam) {
        return new SellFundAction(sellFundBeanParam.getSellFormBean()).perform(request);
    }
}

