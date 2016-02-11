package edu.cmu.webapp.task8.model;

public abstract class AbstractDAOFactory {
    public abstract CustomerDAO getCustomerDAO();
    public abstract EmployeeDAO getEmployeeDAO();
    public abstract PositionDAO getPositionDAO();
    public abstract TransactionDAO getTransactionDAO();
    public abstract FundDAO getFundDAO();
    public abstract FundPriceHistoryDAO getFundPriceHistoryDAO();
    public abstract void databasePrecheck();
    public static AbstractDAOFactory getDAOFactory() {
        return new DAOFactory();
    }
}
