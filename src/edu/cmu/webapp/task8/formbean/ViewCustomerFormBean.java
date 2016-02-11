package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerFormBean extends MyFormBean{
    private String username;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if ((getUsername() == null || getUsername().length() == 0)) {
            errors.add("Username is required");
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!getAction().equals("View Customer Account") && !getAction().equals("View Customer Transaction History") ) {
            errors.add("Invalid button");
        }
        if (getUsername().matches(".*[<>\"].*")) {
            errors.add("User Name may not contain angle brackets or quotes");
        }

        return errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
