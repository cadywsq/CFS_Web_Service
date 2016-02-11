package edu.cmu.webapp.task8.formbean;

import org.mybeans.form.FormBean;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TransitionDayFormBean extends FormBean {
    private String date;
    private String action;

    public List<String> getValidationErrors(String lastTradingDay) {
        List<String> errors = new ArrayList<>();

        if (getAction() == null) {
            errors.add("Button is required");
        }

        if (errors.size() > 0) {
            return errors;
        }

        if (!getAction().equals("Transit Day")) {
            errors.add("Button is invalid");
        }

        String transitionDate = getDate();
        System.out.println("TransitionDayFormBean: " + transitionDate);
        
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        df.setLenient(false);
        
        if (lastTradingDay.length() == 0) {
        	try {
                Date inputTransitionDate = df.parse(transitionDate);
        	} catch (ParseException e) {
                errors.add("Invalid transit date");
            }
        } else {
//        DateFormat dfInput = new SimpleDateFormat("MM/dd/yyyy");
	        try {
	            Date inputTransitionDate = df.parse(transitionDate);
	            Date lastTradingDate = df.parse(lastTradingDay);
	            
	            long dif = inputTransitionDate.getTime() - lastTradingDate.getTime();
	            
//	            if (inputTransitionDate.getTime() - lastTradingDate.getTime() > 432000000)
	
	            // We're not allowed to put a day gaps longer than 15 days
	            if (dif > 1296000000) {
	            	errors.add("You are not allowed to put a day gaps longer than 15 days.");
	            } else if (dif <= 0) {
	                errors.add("The previous trading day cannot be changed.");
	            }
	        } catch (ParseException e) {
	            errors.add("Invalid transit date");
	        }
        }
        return errors;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
