package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
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
        
    	
        if (session.getAttribute("user") instanceof CustomerBean) {
        	transitionDayMessages.add(new MessageJSON("“I’m sorry you are not authorized to preform that action"));
            return transitionDayMessages;
        }
        
        // Checking if the user has logged in.
        EmployeeBean customer = (EmployeeBean) session.getAttribute("user");
        if (customer == null) {
        	transitionDayMessages.add(new MessageJSON("You must log in prior to making this request"));
            return transitionDayMessages;
        }
        
        
        // Return success message.
        transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        return transitionDayMessages;

	}
}
