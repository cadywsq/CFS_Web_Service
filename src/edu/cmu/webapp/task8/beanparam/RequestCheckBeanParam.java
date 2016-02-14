package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;

import edu.cmu.webapp.task8.formbean.RequestCheckFormBean;
/**
 * 
 * @author Hunter
 *
 */
public class RequestCheckBeanParam {
    
    private @FormParam("cashValue") String dollarAmount = null;
    
    
    private RequestCheckFormBean requestCheckFormBean = null;
    
    public RequestCheckBeanParam() {
        this.requestCheckFormBean = new RequestCheckFormBean();
    }

    public String getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(String dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public RequestCheckFormBean getRequestCheckFormBean() {
        this.requestCheckFormBean.setDollarAmount(this.dollarAmount);
        this.requestCheckFormBean.setConfirmAmount(this.dollarAmount);
        return requestCheckFormBean;
    }
    
}
