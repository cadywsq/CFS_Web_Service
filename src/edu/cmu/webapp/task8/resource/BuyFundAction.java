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
import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class BuyFundAction extends Action {
	private BuyFormBean buyFundForm;

	public BuyFundAction(BuyFormBean obj) {
		buyFundForm = obj;
	}

	@Override
	public String getName() {
		return "buyFund";
	}

	@Override
	public List<MessageJSON> perform(HttpServletRequest request) {
		// Set errors attributes
		List<String> errors = new ArrayList<String>();
		HttpSession session = request.getSession(false);
		List<MessageJSON> buyFundMessages = new ArrayList<>();

		CustomerDAO customerDAO = new CustomerDAO();
		FundDAO fundDAO = new FundDAO();
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
            buyFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
            return buyFundMessages;
        }
		if (customer == null) {
			buyFundMessages.add(new MessageJSON("You must log in prior to making this request"));
			return buyFundMessages;
		}

		// Check if the logged in user is an employee.
		if (!(session.getAttribute("user") instanceof CustomerBean)) {
			buyFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
			return buyFundMessages;
		}

		customer = customerDAO.getCustomerByUserName(customer.getUsername());

		// request.setAttribute("customer", customer);

		// ****Input parameter is fundSymbol, need to revise BuyFormBean****
		String fundSymbol = buyFundForm.getSymbol();

		double amountToBuy = Double.parseDouble(buyFundForm.getDollarAmount());

		// Get current customer information

		// Retrieve current available cash balance
		double availableBalance = customer.getCash() / 100.0;

		// Compare the buy fund amount with balance
		// If input is invalid - exceed the current balance,
		// return errors and ask to input a valid number
		if (availableBalance < amountToBuy) {
			buyFundMessages.add(new MessageJSON("I'm sorry, you must first "
					+ "deposit sufficient funds in your account in order to make this purchase"));
			return buyFundMessages;
		}

		// Form validation check
		errors.addAll(buyFundForm.getValidationErrors());
		if (errors.size() > 0) {
			for (String error : errors) {
				buyFundMessages.add(new MessageJSON(error));
			}
			return buyFundMessages;
		}
		// Otherwise, update available cash balance and queue up a pending
		// transaction to DB

		FundBean fundBean = fundDAO.getFundBySymbol(fundSymbol);
		
		if(fundBean==null) {
			buyFundMessages.add(new MessageJSON("No such Fund Exist"));
		}
		FundPriceHistoryDAO fundPriceHistoryDAO = new FundPriceHistoryDAO();

		String latestDate = fundPriceHistoryDAO.getMaxDate();
		SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		FundPriceHistoryBean fundPriceHistoryBean = fundPriceHistoryDAO
				.getFundPriceHistoryByIdAndDate(fundBean.getFundId(), latestDate);
		long latestFundPrice = fundPriceHistoryBean.getPrice();

		// updating customer balance
		amountToBuy = amountToBuy * 100;
		//multiplying with shares 1000 multiples
		long allocatedShares = (long) (amountToBuy / latestFundPrice);
		if (allocatedShares > 0) {
			availableBalance = availableBalance*100  - allocatedShares * latestFundPrice;
			customer.setCash((long) (availableBalance));
			customerDAO.updateCustomer(customer);

			// updating transaction table
			TransactionBean transaction = new TransactionBean();
			transaction.setCustomerId(customer.getCustomerId());
			transaction.setAmount((long) (latestFundPrice * allocatedShares));
			transaction.setTransactionType(TransactionBean.BUY_FUND);
			transaction.setFundId(fundBean.getFundId());
			// how to set execute date without explicitly passing parameter?
			// ****Need revise******
			transaction.setExecuteDate(sdfDate.format(new Date()));
			transaction.setShares(allocatedShares*1000);
			transactionDAO.createTransaction(transaction);

			// Updating position
			PositionDAO positionDAO = new PositionDAO();
			PositionBean position = positionDAO.getPosition(customer.getCustomerId(), fundBean.getFundId());
			if (position == null) {
				position = new PositionBean();
				position.setCustomerId(customer.getCustomerId());
				position.setFundId(fundBean.getFundId());
				position.setShares(allocatedShares*1000);
				positionDAO.createPosition(position);

			} else {
				// adding shares to in position table
				position.setShares(position.getShares() + allocatedShares*1000);
				positionDAO.updatePosition(position);
			}
			// Return success message.
			buyFundMessages.add(new MessageJSON("The purchase was successfully completed"));
		} else {
			buyFundMessages.add(new MessageJSON("No shares are allocated. "));
		}
		return buyFundMessages;
	}
}
