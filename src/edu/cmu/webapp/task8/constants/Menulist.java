package edu.cmu.webapp.task8.constants;

import java.util.ArrayList;
import java.util.List;

import edu.cmu.webapp.task8.JSON.Menu;

public class Menulist {
	private List<Menu> listEmployeeLinkFunctions = new ArrayList<Menu>();
	private List<Menu> listCustomerLinkFunctions = new ArrayList<Menu>();

	public List<Menu> getEmployeeLinkFunctions() {
		// Setting employee menu
		listEmployeeLinkFunctions.add(new Menu("/createCustomerAccount", "Create Account"));
		listEmployeeLinkFunctions.add(new Menu("/depositCheck", "Deposit Check"));
		listEmployeeLinkFunctions.add(new Menu("/createFund", "Create Fund"));
		listEmployeeLinkFunctions.add(new Menu("/transitionDay", "Transition Day"));
		listEmployeeLinkFunctions.add(new Menu("/logOut", "Logout"));

		return listEmployeeLinkFunctions;
	}

	public List<Menu> getCustomerLinkFunctions() {
		// Setting Customer Menu
		listCustomerLinkFunctions.add(new Menu("/viewPortfolio", "View Portfolio"));
		listCustomerLinkFunctions.add(new Menu("/buyFund", "Buy Fund"));
		listCustomerLinkFunctions.add(new Menu("/sellFund", "Sell Fund"));
		listCustomerLinkFunctions.add(new Menu("/requestCheck", "Request Check"));
		listCustomerLinkFunctions.add(new Menu("/logOut", "Logout"));

		return listCustomerLinkFunctions;
	}
}
