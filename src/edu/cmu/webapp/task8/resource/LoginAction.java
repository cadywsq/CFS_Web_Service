package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.LoginJSON;
import edu.cmu.webapp.task8.JSON.Menu;
import edu.cmu.webapp.task8.constants.Menulist;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.LoginFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.EmployeeDAO;

public class LoginAction extends Action {
	private LoginFormBean loginBean;

	public LoginAction(LoginFormBean obj) {
		loginBean = obj;
	}

	public String getName() {
		return "login";
	}

	public LoginJSON perform(HttpServletRequest request) {
		String message = "";
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		List<Menu> menuLinks = new ArrayList<Menu>();
		LoginJSON loginJSON = null;

		// normal validation check
		errors = loginBean.getValidationErrors();
		if (errors.size() > 0) {
			// list all errors and return.
		}
		// Checking if customer has logged in
		CustomerDAO customerDAO = new CustomerDAO();
		CustomerBean customer = customerDAO.getCustomerByUserName(loginBean.getUsername());
		if (customer != null) {
			if (customer.getPassword().equals(loginBean.getPassword())) {
				session.setAttribute("user", customer);
				message = "Welcome " + customer.getFirstName() + " " + customer.getLastName();
				menuLinks = new Menulist().getCustomerLinkFunctions();
				loginJSON = new LoginJSON(message, menuLinks);
				return loginJSON;
			} else {
				message = "The username/password combination that you entered is not correct";
				loginJSON = new LoginJSON(message, menuLinks);
			}
		}
		// Checking if employee has logged in
		EmployeeDAO employeeDAO = new EmployeeDAO();
		EmployeeBean employee = employeeDAO.getEmployeeByUserName(loginBean.getUsername());
		if (employee != null) {
			if (employee.getPassword().equals(loginBean.getPassword())) {
				session.setAttribute("user", employee);
				message = "Welcome " + employee.getFirstName() + " " + employee.getLastName();
				menuLinks = new Menulist().getEmployeeLinkFunctions();
				loginJSON = new LoginJSON(message, menuLinks);
				return loginJSON;
			} else {
				message = "The username/password combination that you entered is not correct";
				loginJSON = new LoginJSON(message, menuLinks);
			}
		}
		// Checking if user name and password doesn't match for both employee
		// and customer
		if (employee == null && customer == null) {
			message = "The username/password combination that you entered is not correct";
			loginJSON = new LoginJSON(message, menuLinks);
			return loginJSON;
		}
		return loginJSON;
	}
}
