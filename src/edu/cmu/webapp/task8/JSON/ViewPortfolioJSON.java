package edu.cmu.webapp.task8.JSON;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
@XmlRootElement
public class ViewPortfolioJSON{
	
	private String message;
	private String cash;
	private List<CustomerAccountItemBean> Funds;
	//empty constructor to	 create json
	public ViewPortfolioJSON() {
	}
	
	public ViewPortfolioJSON(String message) {
		this.message = message;
	}
	public ViewPortfolioJSON (String message, String cash) {
		this.message = message;
		this.cash = cash;
		Funds = new ArrayList<CustomerAccountItemBean>();
	}
	public ViewPortfolioJSON (String message, String cash ,List<CustomerAccountItemBean> funds) {
		this.message = message;
		this.cash = cash;
		this.Funds = funds;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<CustomerAccountItemBean> getLstFund() {
		return Funds;
	}
	public void setFunds(List<CustomerAccountItemBean> funds) {
		this.Funds = funds;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}

	public List<CustomerAccountItemBean> getFunds() {
		return Funds;
	}

}
