package edu.cmu.webapp.task8.resource;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.JSON.ViewPortfolioJSON;
import edu.cmu.webapp.task8.resource.Action;
import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.ValueFormatter;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;

/**
 * After logging, customer sees his account, name, address, date of the last trading day, cash balance,
 * number of shares of each fund owned and the value of that position (shares times price of fund as of the last trading day).
 * From this view there will be links to most other operations.
 */
public class ViewPortfolioAction extends Action {

    public String getName() {
        return "viewMyAccount";
    }

    public ViewPortfolioJSON perform(HttpServletRequest request) {
        HttpSession customerSession = request.getSession();
        
//        CustomerBean customer = (CustomerBean) customerSession.getAttribute("user");
        /**
         * Check if the logged in user is a customer.
         * Modified by Hunter
         */
        CustomerBean customer = null;
        try {
            customer = (CustomerBean) customerSession.getAttribute("user");
        } catch (Exception e) {
            return new ViewPortfolioJSON("I'm sorry you are not authorized to perform that action");
        }
        
        List<String> errors = new ArrayList<>();
        request.setAttribute("errors", errors);
        
        if (customer == null) {
            return new ViewPortfolioJSON("You must log in prior to making this request");
        }
        customerSession.setAttribute("customer", customer);
        
        //Getting available balance
        CustomerDAO customerDAO = new CustomerDAO();
		customer = customerDAO.getCustomerByUserName(customer.getUsername());
		double availableBalance = customer.getCash() / 100.0;
		String strAvailableBalance = ValueFormatter.round(availableBalance, 2);
		
		PositionDAO positionDAO = new PositionDAO();
        List<PositionBean> positionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
        if(positionList == null || positionList.size()==0) {
        	return new ViewPortfolioJSON("You don't have any fund at this time",strAvailableBalance);
        }
        List<CustomerAccountItemBean> fundList = new ArrayList<>();
        FundDAO fundDAO = new FundDAO();
        FundPriceHistoryDAO fundPriceHistoryDAO = new FundPriceHistoryDAO(); 
        for (int i = 0; i < positionList.size(); i++) {
	            CustomerAccountItemBean item = new CustomerAccountItemBean();
	            PositionBean position = positionList.get(i);
	            item.setName(fundDAO.getFundById(position.getFundId()).getSymbol());
	            NumberFormat shareFormat = new DecimalFormat("#,##0.000");
	            item.setShares(shareFormat.format(position.getShares() / 1000.0));
	            
	            List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO.findFundPriceHistoryByFundId(position.getFundId());
	            double price = 0.0;
	            if(fundPriceList != null && fundPriceList.size() > 0) {
	            	price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100.0;
	            }
	            
	            item.setPrice(ValueFormatter.round(price, 2));
	            fundList.add(item);
	        }

        return new ViewPortfolioJSON("Funds owned by you and available cash",strAvailableBalance,fundList);
    
    }
}
