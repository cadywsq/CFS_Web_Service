package edu.cmu.webapp.task8.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.formbean.CreateFundFormBean;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;

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
		if (employee == null) {
			createFundMessages.add(new MessageJSON("You must log in prior to making this request"));
			return createFundMessages;
		}

		// Check if the logged in user is an employee.
		if (!(session.getAttribute("user") instanceof EmployeeBean)) {
			createFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
			return createFundMessages;
		}

		errors.addAll(createFundForm.getValidationErrors());

		if (errors.size() > 0) {
			for (String error : errors) {
				createFundMessages.add(new MessageJSON(error));
			}
			return createFundMessages;
		}
		// Form validation check and check if fund already exists.
		FundDAO fundDAO = new FundDAO();
		FundBean fund = fundDAO.getFundByName(createFundForm.getFundName());

		if (fund != null) {
			if (fund.getName().equals(createFundForm.getFundName())) {
				createFundMessages.add(new MessageJSON("Fund with " + createFundForm.getFundName() +"name already exists"));
				return createFundMessages;
			}
			if (fund.getSymbol().equals(createFundForm.getSymbol())) {
				createFundMessages.add(new MessageJSON("Fund with "+ createFundForm.getSymbol() + " symbol already exists"));
				return createFundMessages;
			}
		}
		// Create the new fund.
		FundBean newFund = new FundBean();
		newFund.setName(createFundForm.getFundName());
		newFund.setSymbol(createFundForm.getSymbol());
		fundDAO.createFund(newFund);

		FundPriceHistoryDAO fundPriceHistoryDAO = new FundPriceHistoryDAO();
		String maxDate = fundPriceHistoryDAO.getMaxDate();
		SimpleDateFormat sdfCurrentDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		//in case if fund price history table is receiving the value for first time
		if (maxDate == null) {
			maxDate = sdfCurrentDate.format(new Date());
		}
		
		
		FundPriceHistoryBean newFundPriceHistory = new FundPriceHistoryBean();
		newFund = fundDAO.getFundByName(createFundForm.getFundName());
		
		// Add the new fund initial price to Fund price history table.
		newFundPriceHistory.setFundId(newFund.getFundId());
		Long initialValue = Long.parseLong(createFundForm.getInitialValue());
		newFundPriceHistory.setPrice(initialValue*100);
		
		newFundPriceHistory.setPriceDate(maxDate);
		fundPriceHistoryDAO.createFundPriceHistory(newFundPriceHistory);

		// Return success message.
		createFundMessages.add(new MessageJSON("The fund has been successfully created"));
		return createFundMessages;

	}
}
