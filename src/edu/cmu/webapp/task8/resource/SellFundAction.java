package edu.cmu.webapp.task8.resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.SellFundFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class SellFundAction extends Action {
	private SellFundFormBean sellFundForm;

	public SellFundAction(SellFundFormBean obj) {
		sellFundForm = obj;
	}

	@Override
	public String getName() {
		return "sellFund";
	}

	@Override
	public List<MessageJSON> perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		List<String> errors = new ArrayList<String>();
		List<MessageJSON> sellFundMessages = new ArrayList<>();

		CustomerDAO customerDAO = new CustomerDAO();
		FundDAO fundDAO = new FundDAO();
		PositionDAO positionDAO = new PositionDAO();
		TransactionDAO transactionDAO = new TransactionDAO();

		// Checking if the user has logged in.
//		CustomerBean customer = (CustomerBean) session.getAttribute("user");
		/**
         * Check if the logged in user is a customer.
         * Modified by Hunter
         */
		CustomerBean customer = null;
		try {
            customer = (CustomerBean) session.getAttribute("user");
        } catch (Exception e) {
            sellFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
            return sellFundMessages;
        }
		if (customer == null) {
			sellFundMessages.add(new MessageJSON("You must log in prior to making this request"));
			return sellFundMessages;
		}

		// Check if the logged in user is an employee.
		if (!(session.getAttribute("user") instanceof CustomerBean)) {
			sellFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
			return sellFundMessages;
		}

		// Retrieve all funds owned by logged in user
		customer = customerDAO.getCustomerByUserName(customer.getUsername());

		// Get current customer bean information
		// Get current fund bean information (****** not yet ******)
/*		customer = (CustomerBean) request.getSession(false).getAttribute("user");
		customer = customerDAO.getCustomerByUserName(customer.getUsername());*/

		// Form validation check
		errors.addAll(sellFundForm.getValidationErrors());
		if (errors.size() > 0) {
			for (String error : errors) {
				sellFundMessages.add(new MessageJSON(error));
			}
			return sellFundMessages;
		}
		String Symbol = sellFundForm.getSymbol();

		FundBean fundBean = fundDAO.getFundBySymbol(Symbol);
		if(fundBean==null) {
			sellFundMessages.add(new MessageJSON("No such fund symbol exist"));
			return sellFundMessages;
		}
		int fundId = fundBean.getFundId();
		PositionBean posBean = positionDAO.getPosition(customer.getCustomerId(), fundId);
		if(posBean ==null) {
			sellFundMessages.add(new MessageJSON("I'm sorry, you don't have enough shares of that fund in your portfolio"));
			return sellFundMessages;
		}
		long sharePosition = posBean.getShares();
		
		// Check enough money
		double shareToSell = 0;
		try {
			shareToSell = Double.parseDouble(sellFundForm.getShares());
		} catch (NumberFormatException nfe) {
			sellFundMessages.add(new MessageJSON("Please input a valid number of shares to sell"));
			return sellFundMessages;
		}
		if (shareToSell + 0.0 > (sharePosition / 1000.0)) {
			sellFundMessages.add(
					new MessageJSON("I'm sorry, you don't have enough shares of that fund in your portfolio"));
			return sellFundMessages;
		}

		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		FundPriceHistoryDAO fundPriceHistoryDAO = new FundPriceHistoryDAO();
		String latestDate = fundPriceHistoryDAO.getMaxDate();
		FundPriceHistoryBean fundPriceHistoryBean = fundPriceHistoryDAO
				.getFundPriceHistoryByIdAndDate(fundBean.getFundId(), latestDate);
		long latestFundPrice = fundPriceHistoryBean.getPrice();

		// update share balance
		long newShareBalance = (long) (sharePosition - shareToSell * 1000);
		posBean.setShares(newShareBalance);
		positionDAO.updatePosition(posBean);

		// updating customer balance
		long allocateFund = (long) (shareToSell * latestFundPrice);
		long availableBalance = customer.getCash() + allocateFund;
		customer.setCash((long) (availableBalance));
		customerDAO.updateCustomer(customer);
		// Once transition day action occurs, verify these buying
		// fund action.
		TransactionBean transaction = new TransactionBean();
		transaction.setCustomerId(customer.getCustomerId());
		transaction.setFundId(fundId);
		transaction.setShares((long) (shareToSell * 1000));
		transaction.setTransactionType(TransactionBean.SELL_FUND);
		// how to set execute date without explicitly passing
		// parameter?
		// ****Need revise******
		transaction.setExecuteDate(sdfDate.format(new Date()));
		transaction.setAmount(allocateFund);
		transactionDAO.createTransaction(transaction);

		// Return success message.
		sellFundMessages.add(new MessageJSON("The sale was successfully completed"));
		return sellFundMessages;
	}
}
