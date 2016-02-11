package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class RequestCheckFormBean extends MyFormBean {
    private String dollarAmount;
    private String confirmAmount;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getDollarAmount() == null || getDollarAmount().trim().length() == 0) {
            errors.add("Amount is required");
        }
        if (getConfirmAmount() == null || getConfirmAmount().trim().length() == 0) {
            errors.add("Confirm Amount is required");
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!getAction().equals("Request Check")) {
            errors.add("Invalid button");
        }
        if (errors.size() > 0) {
            return errors;
        }

        String errorCheck = checkRequestCheckFormat(getDollarAmount());
        if (!errorCheck.equals("")) {
            errors.add(errorCheck);
            return errors;
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (Double.parseDouble(getDollarAmount()) != Double.parseDouble(getConfirmAmount())) {
            errors.add("Dollar amount and the confirmed don't match");
        }

        if (errors.size() > 0) {
            return errors;
        }
        return errors;
    }

    public String getDollarAmount() {
        return dollarAmount;
    }

    public void setDollarAmount(String dollarAmount) {
        this.dollarAmount = dollarAmount;
    }

    public String getConfirmAmount() {
        return confirmAmount;
    }

    public void setConfirmAmount(String confirmAmount) {
        this.confirmAmount = confirmAmount;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}