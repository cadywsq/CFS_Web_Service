package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;

import edu.cmu.webapp.task8.formbean.RequestCheckFormBean;
/**
 * 
 * @author Hunter
 *
 */
public class RequestCheckBeanParam {
    @FormParam("dollarAmount")
    private String dollarAmount = null;
    
    @FormParam("confirmAmount")
    private String confirmAmount = null;
    
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

    public String getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(String confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public RequestCheckFormBean getRequestCheckFormBean() {
        this.requestCheckFormBean.setDollarAmount(this.dollarAmount);
        this.requestCheckFormBean.setConfirmAmount(this.confirmAmount);
        return requestCheckFormBean;
    }
    
}
