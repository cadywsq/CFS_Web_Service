package edu.cmu.webapp.task8.databean;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@Entity
@Table(name = "TRANSACTION")
public class TransactionBean implements Comparable<TransactionBean> {

    public final static int BUY_FUND = 1;
    public final static int SELL_FUND = 2;
    public final static int REQUEST_CHECK = 3;
    public final static int DEPOSIT_CHECK = 4;
    @Id
    @Column(name = "TRANSACTION_ID")
    private int transactionId;
    @Column(name = "CUSTOMER_ID")
    private int customerId;
    
    @Column(name = "FUND_ID")
    private int fundId;
        
    @Column(name = "EXECUTE_DATE")
    private String executeDate;
    @Column(name = "SHARES")
    private long shares;
    @Column(name = "TRANSACTION_TYPE")
    private int transactionType;
    @Column(name = "AMOUNT")
    private long amount;

    @Override
    public int compareTo(TransactionBean transaction) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(this.getExecuteDate()).compareTo(dateFormat.parse(transaction.getExecuteDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

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

    public String getExecuteDate() {
        return executeDate;
    }

    public void setExecuteDate(String executeDate) {
        this.executeDate = executeDate;
    }

    public long getShares() {
        return shares;
    }

    public void setShares(long shares) {
        this.shares = shares;
    }

    public int getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(int transactionType) {
        this.transactionType = transactionType;
    }

    public long getAmount() {
        return amount;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }
}

