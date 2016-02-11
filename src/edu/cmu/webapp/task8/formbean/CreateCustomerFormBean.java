package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;


public class CreateCustomerFormBean extends MyFormBean {
    private String username;
    private String firstName;
    private String password;
    private String lastName;
    private String addrLine1;
    private String addrLine2;
    private String city;
    private String state;
    private String zip;
    private long cash;

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
        if (errors.size() > 0) {
            return errors;
        }
        if (getAddrLine1() == null || getAddrLine1().trim().length() == 0) {
            errors.add("Address Line 1 is required");
            return errors;
        }

        if (getCity() == null || getCity().trim().length() == 0) {
            errors.add("City is required");
            return errors;
        }
        if (getState() == null || getState().trim().length() == 0) {
            errors.add("State is required");
            return errors;
        }
        if (getZip() == null || getZip().trim().length() == 0) {
            errors.add("Zip is required");
            return errors;
        }
        String errorUN = checkStringFormat(getUsername());
        String errorFN = checkLetterFormat(getFirstName());
        String errorLN = checkLetterFormat(getLastName());
        String errorPwd1 = checkStringFormat(getPassword());
        String errorAddr1 = checkStringFormat(getAddrLine1());
        String errorAddr2 = checkStringFormat(getAddrLine2());
        String errorCity = checkLetterFormat(getCity());
        Pattern STATE_FORMAT = Pattern.compile("[A-Z]{2}");

        if (errorUN != "") errors.add("Username " + errorUN);
        if (errorFN != "") errors.add("First Name " + errorFN);
        if (errorLN != "") errors.add("Last Name " + errorLN);
        if (errorPwd1 != "") errors.add(errorPwd1);
        if (errorAddr1 != "") errors.add(errorAddr1);
        if (errorAddr2 != "") errors.add(errorAddr2);
        if (errorCity != "") errors.add("City" + errorCity);
        if (!STATE_FORMAT.matcher(getState()).matches())
            errors.add("State is required to be two capital letters");
        Pattern ZIP_FORMAT = Pattern.compile("^[0-9]{5}");
        if (!ZIP_FORMAT.matcher(getZip()).matches())
            errors.add("Zip code is required to be 5 digits");
        if (errors.size() > 0) {
            return errors;
        }
        return errors;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddrLine1() {
        return addrLine1;
    }

    public void setAddrLine1(String addrLine1) {
        this.addrLine1 = addrLine1;
    }

    public String getAddrLine2() {
    	if(addrLine2 ==null) {
    		addrLine2="";
    	}
        return addrLine2;
    }

    public void setAddrLine2(String addrLine2) {
        this.addrLine2 = addrLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public long getCash() {
        return cash;
    }

    public void setCash(long cash) {
        this.cash = cash;
    }
}
