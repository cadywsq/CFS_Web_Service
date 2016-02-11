package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.formbean.CreateFundFormBean;
import edu.cmu.webapp.task8.model.FundDAO;

public class CreateFundAction extends Action {
	private CreateFundFormBean createFundForm;

	public CreateFundAction(CreateFundFormBean obj) {
		createFundForm = obj;
	}

	@Override
	public String getName() {
		return "createFund";
	}

	@Override
	public List<MessageJSON> perform(HttpServletRequest request) {
		
		HttpSession session = request.getSession();
		List<String> errors = new ArrayList<String>();
		List<MessageJSON> createFundMessages = new ArrayList<>();

		// Checking if the user has logged in.
		EmployeeBean employee = (EmployeeBean) session.getAttribute("user");
		if (employee == null ) {
			createFundMessages.add(new MessageJSON("You must log in prior to making this request"));
			return createFundMessages;
		}
		
		// Check if the logged in user is an employee.
		if (!(session.getAttribute("user") instanceof EmployeeBean)) {
			createFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
        	return createFundMessages;
        }


		// Form validation check and check if fund already exists.
		errors.addAll(createFundForm.getValidationErrors());
		
		FundDAO fundDAO = new FundDAO();
		FundBean fund = fundDAO.getFundByName(createFundForm.getFundName());
		
		if (errors.size() > 0 || fund != null) {
			createFundMessages.add(new MessageJSON("“I’m sorry, there was a problem creating the fund."));
			return createFundMessages;
		}
		
		//Create the fund.
		FundBean newFund = new FundBean();
		newFund.setName(createFundForm.getFundName());
		newFund.setSymbol(createFundForm.getSymbol());
		fundDAO.createFund(newFund);
		
		//Return success message.
		createFundMessages.add(new MessageJSON("Fund " + createFundForm.getFundName() + " is created."));
		return createFundMessages;

	}
}
