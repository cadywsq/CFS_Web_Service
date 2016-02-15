package edu.cmu.webapp.task8.databean;

/**
 *For the use of printing customer portfolio in "ViewCustomerAccount" & "ViewMyAccount" action.
 */
public class CustomerAccountItemBean {
    private String name;
    private String shares;
    private String price;


    public String getName() {
        return name;
    }

    public void setName(String fundName) {
        this.name = fundName;
    }

    public String getShares() {
        return shares;
    }

    public void setShare(String shares) {
        this.shares = shares;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String amount) {
        this.price = amount;
    }
}
