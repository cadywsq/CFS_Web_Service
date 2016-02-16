package edu.cmu.webapp.task8.formbean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BuyFormBean extends MyFormBean {
    private String dollarAmount;
    private String Symbol;

    @Override
    public List<String> getValidationErrors() {
        List<String> errors = new ArrayList<>();
        if(getSymbol()==null || getSymbol().trim().length()==0){
        	errors.add("Please enter a fund symbol");
        	return errors;
        }
        if (getDollarAmount() == null || getDollarAmount().trim().length() == 0) {
            errors.add("Please enter a dollar amount");
            return errors;
        }
        //The dollar amount user entered should be within range of (1, 1,000,000).
        String amountError = checkNumberFormat(dollarAmount);
        if (!amountError.equals("")) {
            errors.add(amountError);
            return errors;
        }
        
        String symbolError = checkFundNameFormat(Symbol);
        if (!symbolError.equals("")) {
        	errors.add(symbolError);
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
    
    public String getSymbol() {
        return Symbol;
    }
    public void setSymbol(String symbol) {
        this.Symbol = symbol;
    }
}
