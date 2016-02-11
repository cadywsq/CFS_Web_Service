package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class ResearchFundFormBean extends MyFormBean {
	private String fundName;
	private String fundSymbol;
	private String action;
	private String startDate;
	private String endDate;

	@Override
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<>();
		if (getFundName() == null || getFundName().length() == 0) {
			errors.add("Fund name is required");
		}

		if (getAction() == null) {
			errors.add("Button is required");
		}
		return errors;
	}

	public String getFundName() {
		return fundName;
	}

	public void setFundName(String fundName) {
		this.fundName = fundName;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFundSymbol() {
		return fundSymbol;
	}

	public void setFundSymbol(String fundSymbol) {
		this.fundSymbol = fundSymbol;
	}
}
