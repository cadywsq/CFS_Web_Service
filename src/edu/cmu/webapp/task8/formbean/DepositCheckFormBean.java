package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class DepositCheckFormBean extends MyFormBean {
    private String username;
    private String dollarAmount;
    private String confirmAmount;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if ((getUsername() == null || getUsername().trim().length() == 0)) {
            errors.add("Username is required");
            return errors;
        }
        if (getDollarAmount() == null || getDollarAmount().trim().length() == 0) {
            errors.add("Amount is required");
            return errors;
        }
        if (getConfirmAmount() == null || getConfirmAmount().trim().length() == 0) {
            errors.add("Confirmed Amount is required");
            return errors;
        }
        if (getAction() == null) {
            errors.add("Button is required");
            return errors;
        }

        if (!getAction().equals("Deposit Check")) {
            errors.add("Invalid button");
        }

        String errorUN = checkStringFormat(getUsername());
        if (errorUN != "") errors.add(errorUN);

        String errorD1 = checkDepositCheckFormat(getDollarAmount());
        if (errorD1 != "") {
            errors.add(errorD1);
            return errors;
        }
        String errorD2 = checkDepositCheckFormat(getConfirmAmount());
        if (errorD2 != "") {
            errors.add(errorD2);
            return errors;
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!(getDollarAmount().trim().equals(getConfirmAmount().trim()))) {
            errors.add("Dollar amount and the confirmed don't match");
        }
        return errors;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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