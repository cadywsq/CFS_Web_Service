package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class TransitionDayAction2 {

	public TransitionDayAction2() {
		
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
        
//        EmployeeBean employee = (EmployeeBean) session.getAttribute("user");
        
        List<FundBean> fundList = fundDAO.getFundList();
        if (fundList == null || fundList.size() == 0) {
        	// The message is not required by the task description, but it just uses for the debug purpose
        	// transitionDayMessages.add(new MessageJSON("I'm sorry you need to put some funds before you can update"));
        	transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        	return transitionDayMessages;
        }
        
        // update prices for each every fund
        for (int i = 0; i < fundList.size(); i++) {
        	FundBean fb = fundList.get(i);
        	FundPriceHistoryBean currentPrice = fundPriceHistoryDAO.getLatestFundPriceByFundId(fb.getFundId());
        	// generate -10% ~ 10% fluctuation and set the new price
        	currentPrice.setPrice(createRandomPrice(currentPrice.getPrice()));
        	fundPriceHistoryDAO.updateFundPriceHistory(currentPrice);
        }
        
        // Return success message.
        transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        return transitionDayMessages;
	}
	
	private static long createRandomPrice(long currentPrice) {
		int lo = -10;
		int hi = 10;
		Random ran = new Random();
		int x = ran.nextInt(hi - lo) + lo;
		double dx = x / 100.0;
//		System.out.print([i] + " -> ");
		currentPrice = (long) (currentPrice * (1 + dx));
//		System.out.println(prices[i]);
		return currentPrice;
	}	
}
