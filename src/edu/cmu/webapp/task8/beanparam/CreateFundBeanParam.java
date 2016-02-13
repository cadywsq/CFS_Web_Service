package edu.cmu.webapp.task8.beanparam;

import javax.ws.rs.FormParam;

import edu.cmu.webapp.task8.formbean.CreateFundFormBean;

public class CreateFundBeanParam {
	private @FormParam("fundName") String fundName;
	private @FormParam("symbol") String symbol;
	private @FormParam("initialPrice") String initialPrice;
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

	public String getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(String initialPrice) {
		this.initialPrice = initialPrice;
	}

}
