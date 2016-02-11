package edu.cmu.webapp.task8.controller;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.RequestCheckFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;


public class RequestCheckAction extends Action {
	private FormBeanFactory<RequestCheckFormBean> formBeanFactory = FormBeanFactory
			.getInstance(RequestCheckFormBean.class);

	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	NumberFormat formatter = new DecimalFormat("#,##0.00"); 

	public RequestCheckAction(AbstractDAOFactory model) {
		transactionDAO = model.getTransactionDAO();
		customerDAO = model.getCustomerDAO();
	}

	public String getName() {
		return "requestCheck.do";
	}

	public String perform(HttpServletRequest request) {
		// Get seesion
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("msg","");
		try {
			// If user is already logged in, redirect to todolist.do
			if (session != null && session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof CustomerBean) {
				RequestCheckFormBean form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				CustomerBean customer = (CustomerBean) session.getAttribute("user");
				customer = customerDAO.getCustomerByUserName(customer.getUsername());
				double availableBalance = customer.getCash() / 100.0;
				String balanceString = formatter.format(availableBalance);
				
				
				request.setAttribute("balance", balanceString);
				
				// If no params were passed, return with no errors so that the
				// form will be presented (we assume for the first time).
				if (!form.isPresent()) {
					return "requestCheck.jsp";
				}
				
				// Check form validation errors
				errors.addAll(form.getValidationErrors());
				if (errors.size() != 0) {
					return "requestCheck.jsp";
				}
				
				synchronized (this) {
					
					//transactionDAO.getValidBalance(customer.getUsername(), customer.getCash() / 100.0 );
					customer = customerDAO.getCustomerByUserName(customer.getUsername());
					availableBalance = customer.getCash() / 100.0;
					
					// Get Request Withdraw Amount from form
					double withdrawAmount = 0;
					try {
						withdrawAmount = Double.parseDouble(form.getDollarAmount());
					} catch (NumberFormatException nfe) {
						errors.add("Amount should be a number.");
						return "requestCheck.jsp";
					}
					
					// if withdrawAmount > availableBalance
					if (withdrawAmount > availableBalance) {
						errors.add("Amount requested cannot be higher than available cash balance");
						return "requestCheck.jsp";
					}
					
					availableBalance = (availableBalance - withdrawAmount) * 100;
					customer.setCash((long) availableBalance);
					customerDAO.updateCustomer(customer);
					
					// Once transition day action occurs, verify these buying fund action.
					TransactionBean transaction = new TransactionBean();
					//transaction.setTransactionId(transactionDAO.getLastTransactionId()+1);
					transaction.setCustomerId(customer.getCustomerId());
					transaction.setAmount((long) (Double.parseDouble(form.getDollarAmount()) * 100));
					transaction.setTransactionType(TransactionBean.REQUEST_CHECK);
					transaction.setFundId(-1);
					// how to set execute date without explicitly passing parameter?
					// ****Need revise******
					transaction.setExecuteDate(null);
					transaction.setShares(-1);
					transactionDAO.createTransaction(transaction);

					
					session.setAttribute("msg",
							"Withdraw Sucessfully!\n"
							+ "Amount: <font color=\"red\">$"+ formatter.format(withdrawAmount)+ "</font>\n"
							+ "Please check your bank account after our transition day.\n");
				}
				
				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null)
					session.removeAttribute("user");

				return "login.do";
			}
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "requestCheck.jsp";
		}
	}
}
