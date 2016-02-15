package edu.cmu.webapp.task8.databean;

/**
 * For the use of printing customer portfolio in "ViewCustomerAccount" &
 * "ViewMyAccount" action.
 */
public class CustomerAccountItemBean {
	private String shares;
	private String name;
	private String price;
	
	public CustomerAccountItemBean() {

	}

	public String getName() {
		return name;
	}

	public void setName(String fundName) {
		this.name = fundName;
	}
	public String getPrice() {
		return price;
	}

	public void setPrice(String amount) {
		this.price = amount;
	}
	public String getShares() {
		return shares;
	}

	public void setShares(String shares) {
		this.shares = shares;
	}
}
