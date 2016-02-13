package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class CreateFundFormBean extends MyFormBean {
    private String fundName;
    private String symbol;
    private String action;
    private String initialPrice;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getFundName() == null || getFundName().length() == 0) {
            errors.add("Fund Name is required");
            return errors;
        }
        if (getSymbol() == null || getSymbol().length() == 0) {
            errors.add("Symbol is required");
            return errors;
        }
        if (getAction() == null) {
            errors.add("Button is required");
            return errors;         
        }
        if (getInitialPrice() == null) {
        	errors.add("Initial price is required");
            return errors;   
        }

        String errorFN = checkFundNameFormat(getFundName());
        Pattern SYMBOL_PATTERN = Pattern.compile("[A-Z]{1,5}");
        if (errorFN != "") errors.add(errorFN);
        if (!SYMBOL_PATTERN.matcher(getSymbol()).matches()) {
            errors.add("Symbol should be less than 5 capital letters");
            return errors;
        }
        
        // The initial price should be within the range of $0.01 to $1,000,000.
        String errorIP = checkDepositCheckFormat(getInitialPrice());
        if (errorIP != "") {
        	errors.add(errorIP);
        	return errors;
        }

        if (!getAction().equals("Create Fund")) {
            errors.add("Invalid button");
        }
        return errors;
    }

    public String getFundName() {
        return fundName;
    }

    public void setFundName(String name) {
        this.fundName = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

	public String getInitialPrice() {
		return initialPrice;
	}

	public void setInitialPrice(String initialPrice) {
		this.initialPrice = initialPrice;
	}
}
