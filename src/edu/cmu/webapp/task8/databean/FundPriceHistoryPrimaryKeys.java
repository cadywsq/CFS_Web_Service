package edu.cmu.webapp.task8.databean;

import java.io.Serializable;

public class FundPriceHistoryPrimaryKeys implements Serializable {
    protected int fundId;
    protected String priceDate;

    public FundPriceHistoryPrimaryKeys() {
    }

    public FundPriceHistoryPrimaryKeys(int fundId, String priceDate) {
    }

    public boolean equals(FundPriceHistoryPrimaryKeys primaryKeys) {
        return this.fundId == primaryKeys.getFundId() && this.priceDate.equals(primaryKeys.getString());
    }

    public int hashCode() {
        int hash = 1;
        hash = hash * 17 + this.fundId;
        hash = hash * 31 + this.priceDate.hashCode();
        return hash;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public String getString() {
        return priceDate;
    }

    public void setString(String priceDate) {
        this.priceDate = priceDate;
    }
}
