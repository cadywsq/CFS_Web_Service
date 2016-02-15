package edu.cmu.webapp.task8.JSON;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
@XmlRootElement
public class ViewPortfolioJSON extends MessageJSON{
	private String cash;
	private List<CustomerAccountItemBean> Funds;
	//empty constructor to create json
	public ViewPortfolioJSON() {
		super();
		Funds = new ArrayList<CustomerAccountItemBean>();
	}
	public ViewPortfolioJSON(String message) {
		super(message);
	}
	public ViewPortfolioJSON (String message, String cash) {
		super(message);
		this.cash = cash;
		Funds = new ArrayList<CustomerAccountItemBean>();
	}
	public ViewPortfolioJSON (String message, String cash ,List<CustomerAccountItemBean> lstFund) {
		super(message);
		this.cash = cash;
		this.Funds = new ArrayList<CustomerAccountItemBean>();
		this.Funds = lstFund;
	}
	
	
	public List<CustomerAccountItemBean> getLstFund() {
		return Funds;
	}
	public void setLstFund(List<CustomerAccountItemBean> lstFund) {
		this.Funds = lstFund;
	}
	public String getCash() {
		return cash;
	}
	public void setCash(String cash) {
		this.cash = cash;
	}

}
