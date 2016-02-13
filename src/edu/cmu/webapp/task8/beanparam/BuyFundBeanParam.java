package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;

import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;
import edu.cmu.webapp.task8.formbean.LoginFormBean;

public class BuyFundBeanParam {
	
    private @FormParam("fundSymbol") String fundSymbol;
    private @FormParam("cashValue") String cashValue;
    private BuyFormBean buyFundFormBean;
    
    public BuyFundBeanParam(){
    	buyFundFormBean = new BuyFormBean();
    }

    public String getFundSymbol() {
        return fundSymbol;
    }

    public void setFundSymbol(String fundSymbol) {
        this.fundSymbol = fundSymbol;
    }

    public String getCashValue() {
        return cashValue;
    }

    public void setCashValue(String cashValue) {
        this.cashValue = cashValue;
    }
    
    public BuyFormBean getBuyFormBean() {
        //****Need setSymbol() in form bean****
        buyFundFormBean.setSymbol(this.fundSymbol);
        buyFundFormBean.setDollarAmount(this.cashValue);
        return buyFundFormBean;
    }

}
