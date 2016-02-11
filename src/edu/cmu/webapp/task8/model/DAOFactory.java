package edu.cmu.webapp.task8.model;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class DAOFactory extends AbstractDAOFactory {
    public static SessionFactory CreateSessionFactory() throws Exception {
        SessionFactory sessionFactory = null;
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
            System.out.println("Session Factory Created");
            return sessionFactory;
        } catch (HibernateException e) {
            System.out.println("Cannot get session factory.");
            e.printStackTrace();
            throw new Exception(e);
        }
    }
    @Override
    public CustomerDAO getCustomerDAO() {
        return new CustomerDAO();
    }
    @Override
    public EmployeeDAO getEmployeeDAO() {
        return new EmployeeDAO();
    }
    @Override
    public PositionDAO getPositionDAO() {
        return new PositionDAO();
    }
    @Override
    public TransactionDAO getTransactionDAO() {
        return new TransactionDAO();
    }
    @Override
    public FundDAO getFundDAO() {
        return new FundDAO();
    }
    @Override
    public FundPriceHistoryDAO getFundPriceHistoryDAO() {
        return new FundPriceHistoryDAO();
    }
    
    public void databasePrecheck() {
        String dbName = "task7";
        try {
            ArrayList<String> list = new ArrayList<String>();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306");
            DatabaseMetaData meta = (DatabaseMetaData) con.getMetaData();
            ResultSet rs = meta.getCatalogs();
            while (rs.next()) {
                String listofDatabases = rs.getString("TABLE_CAT");
                list.add(listofDatabases);
            }
            if (list.contains(dbName)) {
               System.out.println("Database Exists!");
            } else {
//                System.out.println("Database Does not Exist. Starting to Create One.");
                this.createDabase(dbName);
                String createCustomer = String.format("CREATE TABLE `%s`.`customer` ("
                       + "`customer_id` INT NOT NULL AUTO_INCREMENT,"
                        + "`username` VARCHAR(225) NULL,"
                        + "`password` VARCHAR(45) NULL,"
                        + "`firstname` VARCHAR(225) NULL,"
                        + "`lastname` VARCHAR(225) NULL,"
                        + "`addr_line1` VARCHAR(225) NULL,"
                        + "`addr_line2` VARCHAR(225) NULL,"
                        + "`city` VARCHAR(225) NULL,"
                        + "`state` VARCHAR(45) NULL,"
                        + "`zip` INT NULL,"
                        + "`cash` BIGINT(20) NULL,"
                        + "PRIMARY KEY (`customer_id`))", dbName);
                String createEmployee = String.format("CREATE TABLE `%s`.`employee` ("
                        + "`username` VARCHAR(225) NOT NULL,"
                        + "`firstname` VARCHAR(225) NULL,"
                        + "`lastname` VARCHAR(225) NULL,"
                        + "`password` VARCHAR(225) NULL,"
                        + "PRIMARY KEY (`username`))", dbName);
                String createFund = String.format("CREATE TABLE `%s`.`fund` ("
                        + "`fund_id` INT NOT NULL AUTO_INCREMENT,"
                        + "`name` VARCHAR(45) NULL,"
                        + "`symbol` VARCHAR(5) NULL,"
                        + "PRIMARY KEY (`fund_id`))", dbName);
                String createPosition = String.format("CREATE TABLE `%s`.`position` ("
                        + "`customer_id` INT NOT NULL,"
                        + "`fund_id` INT NOT NULL,"
                        + "`shares` BIGINT(20) NOT NULL,"
                        + " PRIMARY KEY (`customer_id`, `fund_id`),"
                        + " INDEX `fundid_idx` (`fund_id` ASC),"
                        + " CONSTRAINT `customerid`"
                        + " FOREIGN KEY (`customer_id`)"
                        + " REFERENCES `%s`.`customer` (`customer_id`)"
                        + " ON DELETE NO ACTION"
                        + " ON UPDATE NO ACTION,"
                        + " CONSTRAINT `fundid`"
                        + " FOREIGN KEY (`fund_id`)"
                        + " REFERENCES `%s`.`fund` (`fund_id`)"
                        + " ON DELETE NO ACTION"
                        + " ON UPDATE NO ACTION)", dbName, dbName, dbName);
                String createFundPriceHistory = String.format("CREATE TABLE `%s`.`fund_price_history` ("
                        + "`fund_id` INT NOT NULL,"
                        + "`price_date` VARCHAR(225) NOT NULL,"
                        + "`price` BIGINT(20) NOT NULL,"
                        + " PRIMARY KEY (`fund_id`, `price_date`),"
                        + " CONSTRAINT `fundidpricehistory`"
                        + " FOREIGN KEY (`fund_id`)"
                        + " REFERENCES `%s`.`fund` (`fund_id`)"
                        + " ON DELETE NO ACTION"
                        + " ON UPDATE NO ACTION)", dbName, dbName);
                String createTransaction = String.format("CREATE TABLE `%s`.`transaction` ("
                        + "`transaction_id` INT NOT NULL AUTO_INCREMENT,"
                        + "`customer_id` INT NOT NULL,"
                        + "`fund_id` INT NULL,"
                        + "`execute_date` VARCHAR(225) NULL,"
                        + "`shares` BIGINT(20) NULL,"
                        + "`transaction_type` INT NOT NULL,"
                        + "`amount` BIGINT(20) NULL,"
                        + " PRIMARY KEY (`transaction_id`),"
                        + " INDEX `customerid_idx` (`customer_id` ASC),"
                        + " CONSTRAINT `customeridtransaction`"
                        + " FOREIGN KEY (`customer_id`)"
                        + " REFERENCES `%s`.`customer` (`customer_id`)"
                        + " ON DELETE NO ACTION"
                        + " ON UPDATE NO ACTION)", dbName, dbName);
                this.createTable(dbName, createCustomer);
                this.createTable(dbName, createEmployee);
                this.createTable(dbName, createFund);
                this.createTable(dbName, createPosition);
                this.createTable(dbName, createFundPriceHistory);
                this.createTable(dbName, createTransaction);
                
                String[] insertSql = {
                "INSERT INTO employee VALUES('jadmin','Jane','Admin','admin')",
                /*"INSERT INTO employee VALUES('moosewen','Moose','wen','123')",
                "INSERT INTO employee VALUES('je0ke','Jeff','Eppinger','123')",
                "INSERT INTO employee VALUES('jmussitsche','Jason','Mussitsch','123')",
                "INSERT INTO employee VALUES('sujatae','Sujata','Telang','123')",
                "INSERT INTO employee VALUES('djriele','David','Riel','123');",
                "INSERT INTO employee VALUES('terrysime','Terry','Lee','123');",
                "INSERT INTO employee VALUES('tooth2e','Sophie','Jeong','123');",
                "INSERT INTO customer VALUES('1','je0kc','123','Jeff','Eppinger','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",
                "INSERT INTO customer VALUES('2','jmussitschc','123','Jason','Mussitsch','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",
                "INSERT INTO customer VALUES('3','sujatac','123','Sujata','Telang','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",
                "INSERT INTO customer VALUES('4','djrielc','123','David','Riel','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",
                "INSERT INTO customer VALUES('5','terrysimc','123','Terry','Lee','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",
                "INSERT INTO customer VALUES('6','tooth2c','123','Sophie','Jeong','5000 Forbes Avenue','Morewood','Pittsburgh','PA','15213','1000000');",*/
//                "INSERT INTO fund VALUES(1,'Mutual Fund A','FUNDA')", 
//                "INSERT INTO fund VALUES(2,'Mutual Fund B','FUNDB')", 
//                "INSERT INTO fund VALUES(3,'Mutual Fund C','FUNDC')", 
//                "INSERT INTO fund VALUES(4,'Mutual Fund D','FUNDD')", 
//                "INSERT INTO position VALUES('1','1','200000')", 
//                "INSERT INTO position VALUES('2','2','790000')", 
//                "INSERT INTO position VALUES('3','3','450000')", 
//                "INSERT INTO position VALUES('4','4','560000')", 
//                "INSERT INTO position VALUES('2','1','540000')", 
//                "INSERT INTO position VALUES('3','2','670000')", 
//                "INSERT INTO position VALUES('4','3','340000')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-15 14:11:30','10000')",
//                "INSERT INTO fund_price_history VALUES('2','2016-01-15 14:11:30','2500')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-15 14:11:30','20000')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-15 14:11:30','7500')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-16 14:11:30','10000')", 
//                "INSERT INTO fund_price_history VALUES('2','2016-01-16 14:11:30','2800')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-16 14:11:30','21000')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-16 14:11:30','8000')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-17 14:11:30','10300')", 
//                "INSERT INTO fund_price_history VALUES('2','2016-01-17 14:11:30','2300')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-17 14:11:30','20300')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-17 14:11:30','7700')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-18 14:11:30','10800')", 
//                "INSERT INTO fund_price_history VALUES('2','2016-01-18 14:11:30','2700')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-18 14:11:30','20800')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-18 14:11:30','7900')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-19 14:11:30','10700')", 
//                "INSERT INTO fund_price_history VALUES('2','2016-01-19 14:11:30','2600')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-19 14:11:30','20600')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-19 14:11:30','7800')", 
//                "INSERT INTO fund_price_history VALUES('1','2016-01-20 14:11:30','10800')", 
//                "INSERT INTO fund_price_history VALUES('2','2016-01-20 14:11:30','2700')", 
//                "INSERT INTO fund_price_history VALUES('3','2016-01-20 14:11:30','20800')", 
//                "INSERT INTO fund_price_history VALUES('4','2016-01-20 14:11:30','7900')",
//                "INSERT INTO transaction VALUES('1','1','1','2016-01-15 14:11:30','10000','1','100000')", 
//                "INSERT INTO transaction VALUES('2','2','2','2016-01-15 14:11:30','23000','1','57500')", 
//                "INSERT INTO transaction VALUES('3','3','3','2016-01-16 14:11:30','45000','1','945000')", 
//                "INSERT INTO transaction VALUES('4','4','4','2016-01-16 14:11:30','56000','1','448000')", 
//                "INSERT INTO transaction VALUES('5','2','1','2016-01-16 14:11:30','54000','1','540000')", 
//                "INSERT INTO transaction VALUES('6','3','2','2016-01-16 14:11:30','67000','1','187600')", 
//                "INSERT INTO transaction VALUES('7','4','3','2016-01-17 14:11:30','34000','1','690200')", 
//                "INSERT INTO transaction VALUES('8','2','2','2016-01-18 14:11:30','56000','1','151200')", 
//                "INSERT INTO transaction VALUES('9','1','1','2016-01-18 14:11:30','8000','2','86400')", 
//                "INSERT INTO transaction VALUES('10','1','-1','2016-01-19 14:11:30','-1','3','1000000')",
//                "INSERT INTO transaction VALUES('10','2','-1','2016-01-19 14:11:30','-1','4','6000000')",
//                "INSERT INTO transaction VALUES('10','3','-1','2016-01-19 14:11:30','-1','4','6000000')", 
//                "INSERT INTO transaction VALUES('11','4','-1','2016-01-19 14:11:30','-1','3','6000000')", 
//                "INSERT INTO transaction VALUES('12','4','1','2016-01-20 14:11:30','10000','1','100000')"
                };
                for (int i = 0; i < insertSql.length; i++) {
//                    System.out.println("this is: " + i + ", " + insertSql[i]);
                    this.createData(dbName, insertSql[i]);
                }
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createDabase(String databaseName) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/");
            String sql = String.format("CREATE DATABASE %s", databaseName);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createTable(String databaseName, String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void createData(String databaseName, String sql) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/" + databaseName);
            PreparedStatement pstmt = con.prepareStatement(sql);
            pstmt.executeUpdate();
            pstmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
