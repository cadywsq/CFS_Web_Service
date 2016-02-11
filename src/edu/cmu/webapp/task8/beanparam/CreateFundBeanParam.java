package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.QueryParam;

import edu.cmu.webapp.task8.formbean.CreateFundFormBean;

public class CreateFundBeanParam {
	private @QueryParam("fundName") String fundName;
	private @QueryParam("symbol") String symbol;
    private CreateFundFormBean createFundForm;
    
    public CreateFundBeanParam() {
    	createFundForm = new CreateFundFormBean();
    }
    
    public CreateFundFormBean getCreateFundForm() {
    	createFundForm.setFundName(this.fundName);
    	createFundForm.setSymbol(this.symbol);
    	return createFundForm;
    }
    
    public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

}
