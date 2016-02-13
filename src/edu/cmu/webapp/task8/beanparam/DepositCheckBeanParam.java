package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;

import edu.cmu.webapp.task8.formbean.DepositCheckFormBean;
/**
 * 
 * @author Hunter
 *
 */
public class DepositCheckBeanParam {
    @FormParam("username")
    private String username = null;
    
    @FormParam("dollarAmount")
    private String dollarAmount = null;
    
    @FormParam("confirmAmount")
    private String confirmAmount = null;
    
    private DepositCheckFormBean depositCheckFormBean = null;
    
    public DepositCheckBeanParam() {
        this.depositCheckFormBean = new DepositCheckFormBean();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public DepositCheckFormBean getDepositCheckFormBean() {
        this.depositCheckFormBean.setConfirmAmount(this.dollarAmount);
        this.depositCheckFormBean.setDollarAmount(this.dollarAmount);
        this.depositCheckFormBean.setUsername(this.username);
        return depositCheckFormBean;
    }
    
}
