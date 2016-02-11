package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class ChangePwdFormBean extends MyFormBean {
    private String oldPassword;
    private String newPassword;
    private String confirmedPassword;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getOldPassword() == null || getOldPassword().length() == 0) {
            errors.add("Current password is required");
            return errors;
        }

        if (getNewPassword() == null || getNewPassword().length() == 0) {
            errors.add("New password is required");
            return errors;
        }

        if (getConfirmedPassword() == null || getConfirmedPassword().length() == 0) {
            errors.add("Confirmed password is required");
            return errors;
        }

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        String errorPwd1 = checkStringFormat(getNewPassword());
        if (errorPwd1 != "") {
            errors.add(errorPwd1);
            return errors;
        }

        if (!getNewPassword().equals(getConfirmedPassword())) {
            errors.add("Passwords do not match");
            return errors;
        }

        if (!getAction().equals("Change Password")) {
//            System.out.println("ChangePwdFormBean: validation errors: " + getAction());
            errors.add("Invalid button");
        }

        return errors;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedPassword() {
        return confirmedPassword;
    }

    public void setConfirmedPassword(String confirmedPassword) {
        this.confirmedPassword = confirmedPassword;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
