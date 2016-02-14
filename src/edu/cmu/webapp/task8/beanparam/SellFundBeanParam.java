package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;
import edu.cmu.webapp.task8.formbean.LoginFormBean;
import edu.cmu.webapp.task8.formbean.SellFundFormBean;

public class SellFundBeanParam {
	
    private @FormParam("fundSymbol") String fundSymbol;
    private @FormParam("numShares") String numShares;
    private SellFundFormBean sellFundFormBean;
    
    public SellFundBeanParam(){
    	sellFundFormBean = new SellFundFormBean();
    }
    
    public String getFundSymbol() {
        return fundSymbol;
    }

    public void setFundSymbol(String fundSymbol) {
        this.fundSymbol = fundSymbol;
    }

    public String getNumShares() {
        return numShares;
    }

    public void setNumShares(String numShares) {
        this.numShares = numShares;
    }

    public SellFundFormBean getSellFormBean() {
        sellFundFormBean.setSymbol(this.fundSymbol);
        sellFundFormBean.setShares(this.numShares);
        return sellFundFormBean;
    }

}
