package edu.cmu.webapp.task8.databean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "FUND")
public class FundBean {
    @Id
    @Column(name = "FUND_ID")
    private int fundId;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SYMBOL")
    private String symbol;
    
    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
}
