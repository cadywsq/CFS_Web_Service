package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class ViewCustomerFormBean2 extends MyFormBean {
    private String username;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if ((getUsername() == null || getUsername().length() == 0)) {
            errors.add("Username is required");
        }

        if (errors.size() > 0) {
            return errors;
        }
        String errorUN = checkStringFormat(getUsername());
        if (errorUN != "") errors.add("Username " + errorUN);
        return errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
