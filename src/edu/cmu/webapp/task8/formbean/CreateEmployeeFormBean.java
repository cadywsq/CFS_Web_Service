package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;

public class CreateEmployeeFormBean extends MyFormBean {
	private String firstName;
	private String lastName;
	private String username;
	private String password;
	private String confirmPassword;
	private String action;

	@Override
	public List<String> getValidationErrors() {
		List<String> errors = new ArrayList<>();

		if (getUsername() == null || getUsername().trim().length() == 0) {
			errors.add("User Name is required");
			return errors;
		}
		if (getFirstName() == null || getFirstName().trim().length() == 0) {
			errors.add("First Name is required");
            return errors;
        }
		if (getLastName() == null || getLastName().trim().length() == 0) {
			errors.add("Last Name is required");
            return errors;
        }
		if (getPassword() == null || getPassword().trim().length() == 0) {
			errors.add("Password is required");
            return errors;
        }
		if (getConfirmPassword() == null || getConfirmPassword().trim().length() == 0) {
			errors.add("Confirm password is required");
            return errors;
        }

		if (!getPassword().equals(getConfirmPassword())) {
			errors.add("Passwords are not the same");
            return errors;
        }
		if (getAction() == null) {
			errors.add("Button is required");
			return errors;
		}

        String errorUN = checkStringFormat(getUsername());
        String errorFN = checkLetterFormat(getFirstName());
        String errorLN = checkLetterFormat(getLastName());
        String errorPwd = checkStringFormat(getPassword());

        if (errorUN != "") errors.add("Username " + errorUN);
        if (errorFN != "") errors.add("First Name " + errorFN);
        if (errorLN != "") errors.add("Last Name " + errorLN);
        if (errorPwd != "") errors.add(errorPwd);
        if (errors.size() > 0) {
            return errors;
        }

		if (!getAction().equals("Create Employee")) {
			errors.add("Invalid button");
		}
		return errors;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
