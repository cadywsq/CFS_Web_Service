package edu.cmu.webapp.task8.resource;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.PositionDetails;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class TransitionDayAction {

	public TransitionDayAction() {
		
	}
	
	public String getName() {
		return "transitionDay";
	}
	
	public List<MessageJSON> perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<String> errors = new ArrayList<String>();
        List<MessageJSON> transitionDayMessages = new ArrayList<>();

        CustomerDAO 		customerDAO = new CustomerDAO();
        FundDAO 			fundDAO = new FundDAO();
        PositionDAO 		positionDAO = new PositionDAO();
        TransactionDAO 		transactionDAO = new TransactionDAO();
    	FundPriceHistoryDAO	fundPriceHistoryDAO = new FundPriceHistoryDAO();
        
    	// Checking if the user has logged in.
    	if (session.getAttribute("user") == null) {
    		transitionDayMessages.add(new MessageJSON("You must log in prior to making this request"));
            return transitionDayMessages;
        }
    	
    	// The user needs to be an employee and be actively logged into the system.
        if (session.getAttribute("user") instanceof CustomerBean) {
        	transitionDayMessages.add(new MessageJSON("I'm sorry you are not authorized to preform that action"));
            return transitionDayMessages;
        }
        
        List<FundBean> fundList = fundDAO.getFundList();
        if (fundList == null || fundList.size() == 0) {
        	// The message is not required by the task description, but it just uses for the debug purpose
        	// transitionDayMessages.add(new MessageJSON("I'm sorry you need to put some funds before you can update"));
        	transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        	return transitionDayMessages;
        }
        
        // set last trading day
        Calendar calendarObj = Calendar.getInstance();
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        String executeDate = df.format(calendarObj.getTime());
        
        // new price map
        HashMap<Integer, Long> newPriceMap = new HashMap<>();
        
        // update prices for each every fund
        for (int i = 0; i < fundList.size(); i++) {
        	FundBean fb = fundList.get(i);
        	FundPriceHistoryBean currentPrice = fundPriceHistoryDAO.getLatestFundPriceByFundId(fb.getFundId());
        	
        	// update FundPriceHistoryBean
        	FundPriceHistoryBean newPriceBean = new FundPriceHistoryBean();
        	newPriceBean.setFundId(currentPrice.getFundId());
        	newPriceBean.setPriceDate(executeDate);
        	// generate -10% ~ 10% fluctuation and set the new price
        	newPriceBean.setPrice(createRandomPrice(currentPrice.getPrice()));
        	
        	// update database
        	fundPriceHistoryDAO.createFundPriceHistory(newPriceBean);
        	
        	newPriceMap.put(newPriceBean.getFundId(), newPriceBean.getPrice());
        }
        
        // process pending transactions
        List<TransactionBean> tbs = transactionDAO.findTransactionsByNullExecuteDate();
        
        // if there is no pending transactions,
        // return success message.
        if (tbs == null || tbs.size() == 0) {
            transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
            return transitionDayMessages;
        }
        
        for (TransactionBean tb : tbs) {
			tb.setExecuteDate(executeDate);
			transactionDAO.updateTransaction(tb);
			// update fund_price_history table
			
			switch (tb.getTransactionType()) {
				case 1:
					// Buy Fund:
					// using amount, then calc the shares by fund price
					// then add to position table
					double price = newPriceMap.get(tb.getFundId());
					PositionDetails details = positionDAO.getPostionDetails(tb.getCustomerId(), tb.getFundId());
					
					PositionBean positionBean = new PositionBean();
					
					// create a new positionBean if it doesn't exist before
					if (details == null) {
						details = new PositionDetails();
						positionBean.setCustomerId(tb.getCustomerId());
						positionBean.setFundId(tb.getFundId());
						positionBean.setShares(0L);
						positionDAO.createPosition(positionBean);
						details.setPosition(positionBean);
					}
					
					positionBean = details.getPosition();
					long origin = positionBean.getShares();
					long sharesToBuy = (long) (tb.getAmount() * 1000.0 / price);
					positionBean.setShares(origin + sharesToBuy);
					positionDAO.updatePosition(positionBean);
	
					tb.setShares(sharesToBuy);
					transactionDAO.updateTransaction(tb);
					break;
				case 2:
					// Sell Fund:
					// Starting from shares number, calculate the dollar amount
					// Add to cash in customer table (in long type)
					double dollarAmount = newPriceMap.get(tb.getFundId()) * tb.getShares() / 1000.0;
					
					CustomerBean customerBean = customerDAO.getCustomerById(tb.getCustomerId());
					long cash = customerBean.getCash();
					
					// update customer table - change the cash balance of the customer
					customerBean.setCash(cash + (long) dollarAmount);
					customerDAO.updateCustomer(customerBean);
					
					// update transaction table - change the cash amount field of the row
					tb.setAmount((long)dollarAmount);
					transactionDAO.updateTransaction(tb);
					
					break;
				case 3:
					// REQUEST_CHECK = 3;
					// when customer makes request for check,
					// we reduce the available cash balance immediately.
					// here the only thing we need to do is to set the execute date.
					// ****NO NOTHING HERE****
					break;
				case 4:
					// DEPOSIT_CHECK, add deposit to cash balance
					long depositeAmount = tb.getAmount();
					CustomerBean cb = customerDAO.getCustomerById(tb.getCustomerId());

					// get the current cash balance
					long cashBalance = cb.getCash();
					
					// set the new balance equal up by depositeAmount
					cb.setCash(depositeAmount + cashBalance);
					
					// update customer table to show the deposit
					customerDAO.updateCustomer(cb);
					break;
				default:
					break;
			}
        }
        
        // Return success message.
        transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        return transitionDayMessages;
	}
	
	// cannot return non-positive price
	private static long createRandomPrice(long currentPrice) {
		int lo = -10;
		int hi = 10;
		Random ran = new Random();

		// if the new random final price > 0, quit
		// else regenerate a new price
		while (true) {
			int x = ran.nextInt(hi - lo) + lo;
			double dx = x / 100.0;
			long newPrice = (long) (currentPrice * (1 + dx));
			if (currentPrice > 0) return newPrice; 
		}
	}	
}
