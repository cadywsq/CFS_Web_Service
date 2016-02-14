package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
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
        	transitionDayMessages.add(new MessageJSON("“I’m sorry you are not authorized to preform that action"));
            return transitionDayMessages;
        }
        
        EmployeeBean employee = (EmployeeBean) session.getAttribute("user");
        
        List<FundBean> fundList2 = fundDAO.getFundList();
        
        
        // Return success message.
        transitionDayMessages.add(new MessageJSON("The fund prices have been recalculated"));
        return transitionDayMessages;

	}
}
