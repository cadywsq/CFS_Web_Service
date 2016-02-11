package edu.cmu.webapp.task8.controller;
/**
 * @author Zhijie Yang
 * @since 01-28-2016
 * @version 1.0
 */
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.DepositCheckFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class DepositCheckAction extends Action {
	private FormBeanFactory<DepositCheckFormBean> formBeanFactory = FormBeanFactory
			.getInstance(DepositCheckFormBean.class);

	private CustomerDAO customerDAO;
	private TransactionDAO transactionDAO;

	public DepositCheckAction(AbstractDAOFactory model) {
		customerDAO = model.getCustomerDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "depositCheck.do";
	}

	public synchronized String perform(HttpServletRequest request) {
		// get session info like user
		HttpSession session = request.getSession();

		// set errors attributes
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

		try {
			
//			System.out.println("DepositCheckAction: perform: ");
			
			// Only logged in employee can deposit check
			if (session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof EmployeeBean) {
				
				// retrieve deposit check form bean
				DepositCheckFormBean form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				// read all customers into list
				// request.setAttribute("customerList", customerDAO.match());
				List<CustomerBean> customerList = customerDAO.getAllCustomers(); 
						//customerDAO.getCustomerById();
				request.setAttribute("customerList", customerList);
				
				// If no parameters were passed in DepositCheckForm,
				// return "depositCheck.jsp"
				if (!form.isPresent()) {
					return "depositCheck.jsp";
				}
				
				// check form parameters errors
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "depositCheck.jsp";
				}
				
				// Read customer data from customerDAO
				// ******need refined code******
				
//				System.out.println("DepositCheckAction: get form username: " + form.getUsername());
				CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
				if (customer == null) {
					errors.add("Customer does not exist");
					return "depositCheck.jsp";
				}
				
				// insert the deposit transaction into database
				TransactionBean transaction = new TransactionBean();
				transaction.setCustomerId(customer.getCustomerId());
				transaction.setAmount((long) (Double.parseDouble(form.getDollarAmount()) * 100));
				transaction.setTransactionType(TransactionBean.DEPOSIT_CHECK);
				// how to set execute date without explicitly passing parameter?
				// ****Need revise******
				transaction.setExecuteDate(null);
				/******I NEED REVIEW HERE!******/
				transaction.setFundId(-1);
				transaction.setShares(-1);
				transactionDAO.createTransaction(transaction);
				
				// determine the decimal format
				NumberFormat formatter = new DecimalFormat("#,##0.00");
				session.setAttribute("msg", "Deposit Success!\n Deposit: $ <font color=\"red\">" +
						formatter.format(Double.parseDouble(form.getDollarAmount())) + " </font>\n" +
						"User: <font color=\"red\">" + form.getUsername() + "</font>\n" +
						"Updated will be shown after transition day.");
				request.removeAttribute("form");

				return "success.do";
			} else {
				// force logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "depositCheck.jsp";
		}

	}
}
