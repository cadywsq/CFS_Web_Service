package edu.cmu.webapp.task8.controller;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.databean.TransactionRecordBean;
import edu.cmu.webapp.task8.databean.ValueFormatter;
import edu.cmu.webapp.task8.databean.TransactionDetails;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;


public class ViewCustomerTransactionHistoryAction2 extends Action {
	private TransactionDAO transactionDAO;
	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	
	NumberFormat sharePriceDF = new DecimalFormat("#,###.00");
	NumberFormat amountDF = new DecimalFormat("#,##0.00");
	NumberFormat sharesDF = new DecimalFormat("#,##0.000");
	
	public ViewCustomerTransactionHistoryAction2(AbstractDAOFactory dao) {
		customerDAO = dao.getCustomerDAO();
		transactionDAO = dao.getTransactionDAO();
		fundDAO = dao.getFundDAO();
		
	}

	public String getName() {
		return "viewCustomerTransactionHistory2.do";
	}

	public String perform(HttpServletRequest request) {
        HttpSession employeeSession = request.getSession();
        EmployeeBean employee = (EmployeeBean) employeeSession.getAttribute("user");

        List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

        if (employee == null) {
            return "login.jsp";
        }
        try {
        	employeeSession.setAttribute("employee", employee);        

        	//List<TransactionBean> history = (List<TransactionBean>) transactionDAO.findTransactionsByCustomerId(customer.getCustomerId());
        	String user = request.getParameter("username");
        	if(user == null || customerDAO.userNameAvailable(user)) {
        		errors.add("No such user exists");
				return "viewCustomerAccount2.do";
        	}
        	CustomerBean customer = customerDAO.getCustomerByUserName(user);
        	
        	
        	//List<TransactionDetails> history =  transactionDAO.getTransactionDetailsByCustomerId(customer.getCustomerId());
        	//List<Tran>
        	List<TransactionBean> history = (List<TransactionBean>) transactionDAO
					.findTransactionsByCustomerId(customer.getCustomerId());
        	
        	request.setAttribute("customer", customer);
        	request.setAttribute("historyRecord", history);
        	if (history.isEmpty()) {
				errors.add("No Transaction History exists for this user!");
				return "viewCustomerTransactionHistory.jsp";
			}

        	List<TransactionRecordBean> lstTrb = new ArrayList<TransactionRecordBean>();
        	for (int i = 0; i < history.size(); i++) {
				Double d = 0.00;
				TransactionRecordBean trb = new TransactionRecordBean();

				if (history.get(i).getExecuteDate()==null) {
					trb.setExecutedate("Pending");
				} else {
					trb.setExecutedate(history.get(i).getExecuteDate().substring(0, 10));
				}
				switch (history.get(i).getTransactionType()) {
				case 1:
					trb.setTransactionType("Buy Fund");
					trb.setFundName(fundDAO.getFundById(history.get(i).getFundId()).getName());
					double dollar1 = history.get(i).getAmount() / 100.0;
					trb.setAmount(ValueFormatter.round(dollar1, 2));
					if (history.get(i).getShares() < 0) {
						trb.setShares("Pending");
						trb.setSharePrice("Pending");
					} else {
						double share1 = history.get(i).getShares() / 1000.0;
						trb.setShares(ValueFormatter.round(share1, 3));
						double price1 = dollar1 / share1;
						trb.setSharePrice(ValueFormatter.round(price1, 2));
					}
					break;
				case 2:
					trb.setTransactionType("Sell Fund");
					trb.setFundName(fundDAO.getFundById(history.get(i).getFundId()).getName());
					double share2 = history.get(i).getShares() / 1000.0;
					trb.setShares(ValueFormatter.round(share2, 3));
					
					if (history.get(i).getAmount() < 0) {
						trb.setSharePrice("Pending");
						trb.setAmount("Pending");
						
					} else {
						double dollar2 = history.get(i).getAmount() / 100.0;
						trb.setAmount(ValueFormatter.round(dollar2, 2));
						double price = dollar2 / share2;
						trb.setSharePrice(ValueFormatter.round(price, 2));
					}
					break;
				case 3:
					trb.setTransactionType("Request Check");
					trb.setFundName("-");
					trb.setShares("-");
					trb.setSharePrice("-");
					double dollar3 = history.get(i).getAmount() / 100.0;
					trb.setAmount(ValueFormatter.round(dollar3, 2));
					break;
				case 4:
					trb.setTransactionType("Deposit Check");
					trb.setFundName("-");
					trb.setShares("-");
					trb.setSharePrice("-");
					double dollar4 = history.get(i).getAmount() / 100.0;
					trb.setAmount(ValueFormatter.round(dollar4, 2));
					break;
				default:
					break;
				}
				lstTrb.add(trb);
				
			}
			
			request.setAttribute("historyRecord", lstTrb);        	
        	return "viewCustomerTransactionHistory.jsp";
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "viewCustomerTransactionHistory.jsp";
		}
	}
}
