package edu.cmu.webapp.task8.databean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "POSITION")
@IdClass(PositionPrimaryKeys.class)
public class PositionBean implements Serializable {
    @Id
    @Column(name = "CUSTOMER_ID")
    private int customerId;
    @Id
    @Column(name = "FUND_ID")
    private int fundId;
    @Column(name = "SHARES")
    private long shares;


    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getFundId() {
        return fundId;
    }

    public void setFundId(int fundId) {
        this.fundId = fundId;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }
}
