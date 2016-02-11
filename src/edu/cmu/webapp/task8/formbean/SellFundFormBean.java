package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class SellFundFormBean extends MyFormBean {
    private String fundName;
    private String action;
    private String shares;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getShares() == null || getShares().trim().length() == 0) {
            errors.add("Number of shares to sell is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }
        if (!getAction().equals("Sell Fund")) {
            errors.add("Invalid button");
        }

        String errorShare = checkShareFormat(getShares());
        if (!errorShare.equals("")) {
            errors.add(errorShare);
            return errors;
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

    public String getShares() {
        return shares;
    }

    public void setShares(String shares) {
        this.shares = shares;
    }
}
