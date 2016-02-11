package edu.cmu.webapp.task8.databean;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class TransactionRecordBean implements Comparable<TransactionRecordBean> {
    private String fundName;
    private String executeDate;
    private String shares;
    private String amount;
    private String sharePrice;
    private String transactionType;

    public TransactionRecordBean() {
        this.fundName = "N/A";
        this.executeDate = "N/A";
        this.shares = "N/A";
        this.amount = "N/A";
        this.sharePrice = "N/A";
        this.transactionType = "N/A";
    }
    
    @Override
    public int compareTo(TransactionRecordBean transaction) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        dateFormat.setLenient(false);
        try {
            return dateFormat.parse(this.getExecuteDate()).compareTo(dateFormat.parse(transaction.getExecuteDate()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }

	public  String getShares() {
		return shares;
	}
	public void setShares(String shares) {
		this.shares = shares;
	}
	public String getExecuteDate() {
		return executeDate;
	}
	public void setExecutedate(String executeDate) {
		this.executeDate = executeDate;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public String getFundName() {
		return fundName;
	}
	public void setFundName(String fundName) {
		this.fundName = fundName;
	}
	public String getSharePrice() {
		return sharePrice;
	}
	public void setSharePrice(String sharePrice) {
		this.sharePrice = sharePrice;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
}
