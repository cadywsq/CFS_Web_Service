package edu.cmu.webapp.task8.databean;

public class TransactionDetails {
	private TransactionBean transaction;
	private FundBean fund;
	private FundPriceHistoryBean priceHistory;
	private String transactionDate;
	private String operation;
	private String fundName;
	private String fundSymbol;
	private String shares;
	private String price;
	private String amount;

	public TransactionBean getTransaction() {
		return transaction;
	}

	public void setTransaction(TransactionBean transaction) {
		this.transaction = transaction;
	}

	public FundBean getFund() {
		return fund;
	}

	public void setFund(FundBean fund) {
		this.fund = fund;
	}

	public FundPriceHistoryBean getPriceHistory() {
		return priceHistory;
	}

	public void setPriceHistory(FundPriceHistoryBean priceHistory) {
		this.priceHistory = priceHistory;
	}

	public String getTransactionDate() {
		if(this.transaction.getExecuteDate() == null) {
			this.transactionDate = "Pending";
		} else {
			this.transactionDate = this.transaction.getExecuteDate();
		}
		
		return this.transactionDate;
	}

	public String getFundName() {
		if (this.transaction.getTransactionType()== 1 || this.transaction.getTransactionType()== 2) {
			this.fundName = this.fund.getName();
		} else {
			this.fundName = "-";
		}
		return this.fundName;
	}

	public String getFundSymbol() {
		if (this.transaction.getTransactionType()== 1 || this.transaction.getTransactionType()== 2) {
			this.fundSymbol = this.fund.getSymbol();
			
		} else {
			this.fundSymbol = "-";
		}
		return this.fundSymbol;
	}

	public String getShares() {
		
		if(this.transaction.getTransactionType()== 1) {
			if (this.transaction.getShares() == -1) {
				this.shares = "Pending";
			} else {
				this.shares = String.valueOf((double) this.transaction.getShares() / 1000);
			}
		} 
		
		else if(this.transaction.getTransactionType()== 2) {
			if (this.transaction.getAmount() == -1) {
				this.shares = "Pending";
			} else {
				this.shares = String.valueOf((double) this.transaction.getShares() / 1000);
			}
		} 
		else {
			this.shares = "-";
		}
		return this.shares;
	}
	//latest share price

	public String getPrice() {
		if(this.transaction.getTransactionType()== 1) {
			if (this.transaction.getShares() == -1 ) {
				this.price = "-";
			} else {
				this.price = String.valueOf((double) this.priceHistory.getPrice() / 100);
			}
		} else if(this.transaction.getTransactionType()== 2) {
			if (this.transaction.getAmount() == -1 ) {
				this.price = "Pending";
			} else {
				this.price = String.valueOf((double) this.priceHistory.getPrice() / 100);
			}
		} else {
			this.price = "-";
		}
		return this.price;
	}

	public String getAmount() {
		if(this.transaction.getTransactionType()== 1) {
				this.amount = String.valueOf((double) this.transaction.getAmount() / 100000);
		} else if(this.transaction.getTransactionType()== 2) {
			if(this.transaction.getAmount()==-1) {
				this.amount = "Pending";
			} else {
				this.amount = String.valueOf((double) this.transaction.getAmount() / 100000);
			}
		} else {
			this.amount = String.valueOf((double) this.transaction.getAmount() / 100000);
		}
		
		return this.amount;
	}

	public String getOperation() {
		switch (this.transaction.getTransactionType()) {
		case 1:
			this.operation = "Buy";
			break;
		case 2:
			this.operation = "Sell";
			break;
		case 3:
			this.operation = "Request Check";
			break;
		case 4:
			this.operation = "Deposit Check";
			break;
		default:
			this.operation = "";
		}
		return this.operation;
	}
}
