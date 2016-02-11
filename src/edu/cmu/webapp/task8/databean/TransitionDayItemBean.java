package edu.cmu.webapp.task8.databean;

/**
 * @author Siqi Wang siqiw1 on 1/24/16.
 */
public class TransitionDayItemBean {
    private String fundName;
    private String symbol;
    private String price;
    private String closePrice;


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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(String closePrice) {
        this.closePrice = closePrice;
    }
}
