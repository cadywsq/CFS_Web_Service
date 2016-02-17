package edu.cmu.webapp.task8.formbean;

import org.mybeans.form.FormBean;

import java.util.List;
import java.util.regex.Pattern;

public abstract class MyFormBean extends FormBean {
    public abstract List<String> getValidationErrors();

//    public String sanitize(String s) {
//        return s.replace("&", "&amp;").replace("<", "&lt;")
//                .replace(">", "&gt;").replace("\"", "&quot;");
//    }
//
//    public class MyException extends Exception {
//        private static final long serialVersionUID = 1L;
//
//        public MyException(Exception e) {
//            super(e);
//        }
//
//        public MyException(String s) {
//            super(s);
//        }
//    }

    public String checkNumberFormat(String number) {
        try {
            double num = Double.parseDouble(number);
            Pattern NUM_FORMAT = Pattern.compile("^[1-9][0-9]{0,6}\\.?[0-9]{0,2}$");
            Boolean rightFormat = NUM_FORMAT.matcher(number).matches();
            if (!rightFormat) {
                return "Amount should be within the range of $10.00 to $1,000,000.00 with two digits at most.";
            } else {
                return "";
            }
        } catch (NumberFormatException e) {
            return "Please input valid number";
        }
    }

    public String checkDepositCheckFormat(String number) {
        try{
            double checkAmount = Double.parseDouble(number);
            if (checkAmount < 0.01 || checkAmount > 1000000) {
                return "Check amount should be greater than $0.01 and less than $1,000,000";
            }
            else return "";
        } catch (NumberFormatException e) {
            return "Please input valid number";
        }
    }

    public String checkRequestCheckFormat(String number) {
        try {
            double checkAmount = Double.parseDouble(number);
            if (checkAmount < 0.01) {
                return "Check amount should be greater than $0.01";
            }
            else return "";
        } catch (NumberFormatException e) {
            return "Please input valid number";
        }
     }

    public String checkShareFormat(String number) {
    	try {
    		Double.parseDouble(number);
    	} catch (Exception e) {
    		return "Please inut valid number";
    	}
    	Pattern SHARE_FORMAT = Pattern.compile("[1-9][0-9]{0,6}");
    	Boolean rightFormat = SHARE_FORMAT.matcher(number).matches();
    	if (!rightFormat) {
    		return "Cannot sell partial share.";
    	} else return "";
//        try {
//            int shareAmount = Integer.parseInt(number);
//            if (shareAmount < 0.001) {
//                return "Share should be larger than 0.001";
//            }
//            else return "";
//        } catch (NumberFormatException e) {
//            return "Please input valid number";
//        }
    }

    public String checkLetterFormat(String formInput) {
        Pattern INPUT_FORMAT = Pattern.compile("[a-zA-Z]{1,25}");
        Boolean rightFormat = INPUT_FORMAT.matcher(formInput).matches();
        if (!rightFormat) {
            return "input should be all letters and less than 25 characters.";
        } else return "";
    }
    
    public String checkFundNameFormat(String fundName) {
    	Pattern FUNDNAME_FORMAT = Pattern.compile("[a-zA-Z0-9\\\\ ]*");
    	Boolean rightFormat = FUNDNAME_FORMAT.matcher(fundName).matches();
    	if (!rightFormat) {
    	       return "Fund name should be letters or numbers.";
        } else return "";
    }

    public String checkStringFormat(String username) {
    	Pattern USERNAME_FORMAT = Pattern.compile("[^<>;\":]*");
        Boolean rightFormat = USERNAME_FORMAT.matcher(username).matches();
        if (!rightFormat) {
            return "Username may not contain angle brackets, colon or quotes";
        } else {
            return "";
        }
    }

//    public String checkPassword(String pwd) {
//        Pattern PWD_FORMAT = Pattern.compile("[^<>;\":]*");
//        Boolean rightFormat = PWD_FORMAT.matcher(pwd).matches();
//        if (!rightFormat) {
//            return "Password may not contain angle brackets, colon or quotes";
//        } else {
//            return "";
//        }
//    }

//    public Date dateFormat(String date) throws MyException {
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
//        dateFormat.setLenient(false);
//        try {
//            return dateFormat.parse(date);
//        } catch (ParseException e) {
//            throw new MyException("Please input date" + date + "in the format of \"MM/dd/yyyy\"");
//        }
//    }

}
