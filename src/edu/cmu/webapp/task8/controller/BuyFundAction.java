package edu.cmu.webapp.task8.controller;

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
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.databean.ValueFormatter;
import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class BuyFundAction extends Action {
    private FormBeanFactory<BuyFormBean> formBeanFactory = 
    		FormBeanFactory.getInstance(BuyFormBean.class);
    
    private CustomerDAO customerDAO;
    private FundDAO fundDAO;
	private TransactionDAO transactionDAO;
	private PositionDAO positionDAO;
	NumberFormat formatter = new DecimalFormat("#,##0.00");
	
    public BuyFundAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        fundDAO = dao.getFundDAO();
        transactionDAO = dao.getTransactionDAO();
        positionDAO = dao.getPositionDAO();
    }
	
	public String getName() {
		return "buyFund.do";
	}
	
	public String perform(HttpServletRequest request) {
		// Set errors attributes
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("msg","");
		HttpSession session = request.getSession(false);
		
		try {
			// Only customer can buy fund
			if (session.getAttribute("user") == null ||
					session.getAttribute("user") instanceof EmployeeBean) {
				errors.add("Please Use Customer Login");
				return "login.jsp";
			}
			
			String availableBalanceString = "";
			CustomerBean customer = (CustomerBean) session.getAttribute("user");
			customer = customerDAO.getCustomerByUserName(customer.getUsername());
//			customer.setCash(customer.getCash() / 100);
			request.setAttribute("customer", customer);
			availableBalanceString = "$ " + ValueFormatter.round(customer.getCash() / 100.0, 2);
			request.setAttribute("balance", availableBalanceString);
			
			List<FundBean> fundList = fundDAO.getFundList();
			//positionDAO.getPositionsByCustomerId(customer.getCustomerId());
//			System.out.println("BuyFundAction: The below list is the funds: ");
//			System.out.println(fundList);
			request.setAttribute("fundList", fundList);
			
			// Get BuyFund FormBean from request
			BuyFormBean form = formBeanFactory.create(request);
			if (!form.isPresent()) {
				return "buyFund.jsp";
			}
			errors.addAll(form.getValidationErrors());
			request.setAttribute("errors", errors);
			if (errors.size() > 0) {
				return "buyFund.jsp";
			}
			
			// *****Next version would be fund name. (Now it's fund id in fact)*******
			//String fundName = form.getFund();
			String fundName = "FUNDA";
			//int fundName = Integer.parseInt(form.getFund());
			double amountToBuy = Double.parseDouble(form.getDollarAmount());
			
			
			// Get current customer information
			synchronized (this) {
				//customer = customerDAO.getCustomerByUserName(customer.getUsername());

				// Retrieve current available cash balance
				double availableBalance = customer.getCash() / 100.0;
				//transactionDAO.getValidBalance(user.getUsername(), user.getCash() / 100.0);
				
//				System.out.println("BuyFundAction: " + "availableBalance: " + availableBalance + 
//						"; amountToBuy: " + amountToBuy);
				
				// Compare the buy fund amount with balance
				// If input is invalid - exceed the current balance,
				// return errors and ask to input a valid number
				if (availableBalance < amountToBuy) {
					errors.add("The balance is not enough.");
					return "buyFund.jsp";
				}
				
				// if the fund is not found, return errors
				FundBean fundBean = fundDAO.getFundByName(fundName);
				//FundBean fundBean = fundDAO.getFundById(fundName);
				if (fundBean == null) {
					errors.add("Fund name does not exist.");
					return "buyFund.jsp";
				}
				
				// Otherwise, update available cash balance and queue up a pending transaction to DB
				availableBalance -= amountToBuy;
				customer.setCash((long) (availableBalance * 100));
				customerDAO.updateCustomer(customer);
				
				// Once transition day action occurs, verify these buying fund action.
				TransactionBean transaction = new TransactionBean();
				//transaction.setTransactionId(transactionDAO.getLastTransactionId()+1);
				transaction.setCustomerId(customer.getCustomerId());
				transaction.setAmount((long) (amountToBuy * 100));
				transaction.setTransactionType(TransactionBean.BUY_FUND);
				transaction.setFundId(fundBean.getFundId());
				// how to set execute date without explicitly passing parameter?
				// ****Need revise******
				transaction.setExecuteDate(null);
				transaction.setShares(-1);
				transactionDAO.createTransaction(transaction);
				
				availableBalanceString = "$ " + formatter.format(availableBalance);
			}
			
//			user = customerDAO.read(user.getUserName());
//			if (! transactionDAO.buyFund(user.getUserName(), user.getCash()/100, amount, fb[0].getFundId()) ){
//				errors.add("You do not have enough cash available.");
//				return "buyFund.jsp";	
//			}

//			availableBalance = transactionDAO.getValidBalance(user.getUserName(), user.getCash() / 100.0);
			
			session.setAttribute("msg",
					"Buy fund successfully!\n" +
					"Amount: <font color=\"red\">$" + form.getDollarAmount() + "</font>\n" +
					"Fund: <font color=\"red\">" + fundName + "</font>\n");
			
//			if (errors.size() > 0) { 
//				return "buyFund.jsp";
//			}
			
			return "success.do";
		}
//		catch (RollbackException e) {
//			errors.add(e.getMessage());
//			return "buyFund.jsp";
//		}
		catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "buyFund.jsp";
		}
	}

}
