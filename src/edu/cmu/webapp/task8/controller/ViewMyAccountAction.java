package edu.cmu.webapp.task8.controller;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.databean.ValueFormatter;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

/**
 * After logging, customer sees his account, name, address, date of the last trading day, cash balance,
 * number of shares of each fund owned and the value of that position (shares times price of fund as of the last trading day).
 * From this view there will be links to most other operations.
 */
public class ViewMyAccountAction extends Action {
    private PositionDAO 				positionDAO;
    private FundDAO 					fundDAO;
    private CustomerDAO 				customerDAO;
    private FundPriceHistoryDAO 		fundPriceHistoryDAO;
    private TransactionDAO				transactionDAO;

    public ViewMyAccountAction(AbstractDAOFactory dao) {
        positionDAO = dao.getPositionDAO();
        fundDAO = dao.getFundDAO();
        fundPriceHistoryDAO = dao.getFundPriceHistoryDAO();
        customerDAO = dao.getCustomerDAO();
        transactionDAO = dao.getTransactionDAO();
    }

    public String getName() {
        return "viewMyAccount.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession customerSession = request.getSession();
        CustomerBean customer = (CustomerBean) customerSession.getAttribute("user");
        
        List<String> errors = new ArrayList<>();
        request.setAttribute("errors", errors);
        
        if (customer == null) {
            return "login.jsp";
        }
        
//        System.out.println("ViewMyAccount.do: customer = " + customer.getUsername());

        customerSession.setAttribute("customer", customer);
        request.setAttribute("fund", positionDAO.getPositionsByCustomerId(customer.getCustomerId()));
        
		customer = customerDAO.getCustomerByUserName(customer.getUsername());
		double availableBalance = customer.getCash() / 100.0;
		String balanceString = ValueFormatter.round(availableBalance, 2);
		request.setAttribute("cash", balanceString);
		
        List<PositionBean> positionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
//        System.out.println("inside VieMyAccountAction: " + positionList.size());
        List<CustomerAccountItemBean> fundList = new ArrayList<>();
        
        if  (positionList != null && positionList.size() > 0)
	        for (int i = 0; i < positionList.size(); i++) {
	        	
	            CustomerAccountItemBean item = new CustomerAccountItemBean();
	            PositionBean position = positionList.get(i);
	            item.setFundName(fundDAO.getFundById(position.getFundId()).getName());
//	            System.out.println("inside VieMyAccountAction: " + item.getFundName());
	            item.setSymbol(fundDAO.getFundById(position.getFundId()).getSymbol());
//	            System.out.println("inside VieMyAccountAction: " + fundDAO.getFundById(position.getFundId()).getSymbol());
//	            System.out.println("inside VieMyAccountAction: " + item.getSymbol());
	            NumberFormat shareFormat = new DecimalFormat("#,##0.000");
	            item.setShare(shareFormat.format(position.getShares() / 1000.0));
	            
	
	            List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO.findFundPriceHistoryByFundId(position
	                    .getFundId());
	            double price = 0.0;
	            if(fundPriceList != null && fundPriceList.size() > 0) {
	            	price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100.0;
	            }
	            double amount = price * (position.getShares() / 1000.0);
	            
	            item.setAmount(ValueFormatter.round(amount, 2));
//	            System.out.println("inside VieMyAccountAction: " + item.getAmount());
	            fundList.add(item);
	        }
//	    System.out.println("inside VieMyAccountAction, customer fund list size is: " + fundList.size());
//        request.setAttribute("fundlist", fundList);
        customerSession.setAttribute("fundlist", fundList);
//        String cash = ValueFormatter.round(customer.getCash() / 100.0, 2);
        
        String lastTradeDay = "N/A"; 
//        System.out.println("ViewMyAccountAction: " + customer.getCustomerId());
        TransactionBean tb = transactionDAO.getLatestTransactionByCustomerId(customer.getCustomerId());
        if (tb != null) lastTradeDay = tb.getExecuteDate().split(" ")[0];
		request.setAttribute("lastTradingDay", lastTradeDay);
//        request.setAttribute("cash", cash);
        return "viewMyAccount.jsp";
    }
}
