package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class ResetPwdFormBean extends MyFormBean {

    private String username;
    private String newPassword;
    private String confirmPassword;
    private String action;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();

        if (getNewPassword() == null || getNewPassword().trim().length() == 0) {
            errors.add("New password is required");
        }

        if (getConfirmPassword() == null || getConfirmPassword().trim().length() == 0) {
            errors.add("Confirm Password is required");
        }
        if (getAction() == null || getAction().length() == 0) {
            errors.add("button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        String errorPwd = checkStringFormat(getNewPassword());
        if (!errorPwd.equals("")) {
            errors.add(errorPwd);
            return errors;
        }

        if (!getNewPassword().equals(getConfirmPassword())) {
            errors.add("New password and confirm password don't match");
        }

        if (!getAction().equals("Reset Password")) {
            errors.add("Invalid button");
        }
        return errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
