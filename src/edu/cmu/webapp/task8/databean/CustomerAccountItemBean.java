package edu.cmu.webapp.task8.databean;

/**
 *For the use of printing customer portfolio in "ViewCustomerAccount" & "ViewMyAccount" action.
 */
public class CustomerAccountItemBean {
    private String fundName;
    private String symbol;
    private String share;
    private String amount;


    public String getFundName() {
        return fundName;
    }

    public void setFundName(String fundName) {
        this.fundName = fundName;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getShare() {
        return share;
    }

    public void setShare(String share) {
        this.share = share;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
