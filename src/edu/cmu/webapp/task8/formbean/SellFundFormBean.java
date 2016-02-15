package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class SellFundFormBean extends MyFormBean {
    private String symbol;
    private String shares;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        if(getSymbol() ==null || getSymbol().trim().length()==0) {
        	errors.add("Fund Sybmol is required");
        	return errors;
        }

        if (getShares() == null || getShares().trim().length() == 0) {
            errors.add("Number of shares to sell is required");
            return errors;
        }
        
        String errorShare = checkShareFormat(getShares());
        if (!errorShare.equals("")) {
            errors.add(errorShare);
            return errors;
        }
        return errors;
    }

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
    
    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
