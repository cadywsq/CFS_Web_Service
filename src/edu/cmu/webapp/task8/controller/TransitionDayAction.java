package edu.cmu.webapp.task8.controller;
/**
 * @author Zhijie Yang
 * @since 01-29-2016 01:46 AM
 */
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.PositionDetails;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.databean.TransitionDayItemBean;
import edu.cmu.webapp.task8.formbean.TransitionDayFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class TransitionDayAction extends Action {
	private FormBeanFactory<TransitionDayFormBean> formBeanFactory = FormBeanFactory
			.getInstance(TransitionDayFormBean.class);
	
	private CustomerDAO 		customerDAO;
	private FundDAO				fundDAO;
	private	TransactionDAO		transactionDAO;
	private FundPriceHistoryDAO	fundPriceHistoryDAO;
	private PositionDAO			positionDAO;
	
//	private Formatter formatter = new Formatter("#0.00");
	
	public TransitionDayAction(AbstractDAOFactory model) {
		customerDAO 		= model.getCustomerDAO();
		transactionDAO 		= model.getTransactionDAO();
		fundDAO				= model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
		positionDAO 		= model.getPositionDAO();
	}

	public String getName() {
		return "transitionDay.do";
	}
	
	public synchronized String perform(HttpServletRequest request) {
		HttpSession session = request.getSession();
		
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		
//		System.out.println("TransitionDayAction!");
		
		try {
			// If user is already logged in, redirect to todolist.do
			if (session != null && session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof EmployeeBean) {
				TransitionDayFormBean form = formBeanFactory.create(request);
				request.setAttribute("form", form);
				
				// get the last closing price from database into lastPrice list
				List<FundBean> fundList2 = fundDAO.getFundList();
				
				if (fundList2 == null || fundList2.size() == 0) {
					errors.add("Please create at least one fund before going to set transition day.");
					return "transitionDay.jsp";
				}
				
				List<String> lastPrice = new ArrayList<>();
				if (fundList2 != null && fundList2.size() > 0)
				for (int i = 0; i < fundList2.size(); i++) {
					FundPriceHistoryBean fundLastPrice = fundPriceHistoryDAO.getLatestFundPriceByFundId(fundList2.get(i).getFundId());
					if (fundLastPrice == null) lastPrice.add("N/A");
					else lastPrice.add("" + fundLastPrice.getPrice() / 100.0);
					
//					System.out.println("TransitionDay: fund: " + fundList2.get(i).getSymbol() + " lastPrice = " + fundLastPrice);
					
				}
				
				// put (1) fundname, (2) fundsymbol, (3) fund-last price as one bean into fundList
				List<TransitionDayItemBean> fundList = new ArrayList<>();
				if (fundList2 != null && fundList2.size() > 0)
				for (int i = 0; i < fundList2.size(); i++) {
					TransitionDayItemBean tditem = new TransitionDayItemBean();
					tditem.setFundName(fundList2.get(i).getName());
					tditem.setSymbol(fundList2.get(i).getSymbol());
					tditem.setPrice(lastPrice.get(i));
					fundList.add(tditem);
					
//					System.out.println("TransitionDay: " + tditem.getFundName() + " " 
//							+ tditem.getSymbol() + " " + tditem.getPrice());
				}
				request.setAttribute("fundList", fundList);
				System.out.println("last = ");
				FundPriceHistoryBean fundphBean = fundPriceHistoryDAO.getLatestFundPriceByFundId(1);
				
				String lastTradingDay = "";
				
				if (fundphBean != null) lastTradingDay = fundphBean.getPriceDate();
				if (lastTradingDay.length() >= 0) lastTradingDay = lastTradingDay.split(" ")[0];
				
				request.setAttribute("lastTradingDay", lastTradingDay);
				
				if (!form.isPresent()) {
					return "transitionDay.jsp";
				}
				errors.addAll(form.getValidationErrors(lastTradingDay));
		        if (errors.size() > 0) {
		        	return "transitionDay.jsp";
		        }
		        
		        List<Long> closingPrice = new ArrayList<>();
		        for (int i = 0; i < fundList.size(); i++) {
		        	long newPrice = 0L;
		        	try {
		        		
		        		double rawData =  Double.parseDouble(
		        				request.getParameter("fund_" + fundList.get(i).getSymbol()));
		        		
		        		newPrice = (long) (rawData * 1000) / 10;
		        		
//		        		System.out.println("TransitionDayAction: newPrice=" + newPrice + 
//		        				" origin=" + rawData);
		        		
		        		if (newPrice > 100000000) { // 100000000 stands for one million
		        			errors.add("Please input a price less than one million.");
		        			break;
		        		} else if (newPrice <= 0) {
		        			errors.add("Please input a positive closing price.");
		        			break;
		        		}
		        		closingPrice.add(newPrice);
//		        		System.out.println("TransitionDayAction: " + fundList.get(i).getSymbol() + 
//		        				"  with new price: " + newPrice);
		        		
		        	} catch (Exception e) {
		        		errors.add("New Closing Price should be a valid number.");
		        		return "transitionDay.jsp";
		        	}
		        }
				
		        if (errors.size() > 0) {
		        	return "transitionDay.jsp";
		        }
		        
				String executeDate = form.getDate();
				
				if (lastTradingDay.length() > 0 && executeDate.compareTo(lastTradingDay) <= 0) {
					errors.add("The date of closing price should be later than last trading day.");
					return "transitionDay.jsp";
				}
				
				// 1. get all the lastest up-to-date closing price
				// Key-Value : FundId-Closing Price
				HashMap<Integer, Long> map = new HashMap<>();
//				synchronized (this) {
					for (int i = 0; i < fundList.size(); i++) {
						// update fund_price_history table
	//					TransitionDayItemBean tdib = form.getTransitionDayItemList().get(i);
						FundPriceHistoryBean fphb = new FundPriceHistoryBean();
						int fundId = fundDAO.getFundBySymbol(fundList.get(i).getSymbol()).getFundId();
						fphb.setFundId(fundId);
						fphb.setPrice(closingPrice.get(i));
						fphb.setPriceDate(executeDate);
						fundPriceHistoryDAO.createFundPriceHistory(fphb);
						
//						System.out.println("TransitionDayAction: fund " + fundList.get(i).getSymbol() 
//								+ " closing price is: " + fphb.getPrice());
						
						map.put(fundId, fphb.getPrice());
					}
//					for (Integer k : map.keySet()) {
//						System.out.println("fundID = " + k + " closing price = " + map.get(k));
//					}
//				}// sychronized
				

				
				// 2. Get all the pending transactions
//				synchronized (this) {
					List<TransactionBean> tbs = transactionDAO.findTransactionsByNullExecuteDate();
					
					if (tbs != null && tbs.size() > 0) {
						for (TransactionBean tb : tbs) {
							tb.setExecuteDate(executeDate);
							transactionDAO.updateTransaction(tb);
							
//							System.out.println("Transit: Type = " + tb.getTransactionType());
							
		//				    public final static int BUY_FUND = 1;
		//				    public final static int SELL_FUND = 2;
		//				    public final static int REQUEST_CHECK = 3;
		//				    public final static int DEPOSIT_CHECK = 4;
							switch (tb.getTransactionType()) {
								case 1:
									// Buy Fund:
									// using amount, then calc the shares by fund price
									// then add to position table
									double price = map.get(tb.getFundId());
									
//									System.out.println("fundid="+tb.getFundId() +" price="+price + ", customer id: " + tb.getCustomerId());
									
//									PositionBean positionBean = positionDAO.getPosition(tb.getCustomerId(), tb.getFundId());
									PositionDetails details = positionDAO.getPostionDetails(tb.getCustomerId(), tb.getFundId());
									
									PositionBean positionBean = new PositionBean();
									
									if (details == null) {
										details = new PositionDetails();
										positionBean.setCustomerId(tb.getCustomerId());
										positionBean.setFundId(tb.getFundId());
										positionBean.setShares(0L);
										positionDAO.createPosition(positionBean);
										details.setPosition(positionBean);
									}
									
//									System.out.println("Transition day action: here we got the position details");
									positionBean = details.getPosition();
//									System.out.println("Transition day action: here we got the position bean");
									long origin = positionBean.getShares();
									
//									System.out.println("customerid=" + positionBean.getCustomerId() +" ori shares="+origin/1000.0);
									
									long sharesToBuy = (long) (tb.getAmount() * 1000.0 / price);
									
									positionBean.setShares(origin + sharesToBuy);
									
//									System.out.println("Buy Fund: origin shares is " + origin / 1000.0);
//									System.out.println("Buy Fund: update shares is " + positionBean.getShares() / 1000.0);
									
									positionDAO.updatePosition(positionBean);
									
									tb.setShares(sharesToBuy);
									
									transactionDAO.updateTransaction(tb);
									break;
								case 2:
									// Sell Fund:
									// Starting from shares number, calculate the dollar amount
									// Add to cash in customer table (in long type)
									double dollarAmount = map.get(tb.getFundId()) * tb.getShares() / 1000.0;
									
//									System.out.println("sell fund amount="+dollarAmount);
									
									CustomerBean customerBean = customerDAO.getCustomerById(tb.getCustomerId());
									long cash = customerBean.getCash();
									
//									System.out.println("customer="+customerBean.getUsername() +" ori shares="+cash/100.0);
									
									customerBean.setCash(cash + (long) dollarAmount);
									customerDAO.updateCustomer(customerBean);
									
									tb.setAmount((long)dollarAmount);
									transactionDAO.updateTransaction(tb);
									
//									System.out.println("Sell Fund: origin cash is " + cash / 100);
//									System.out.println("Sell Fund: update cash is " + customerBean.getCash() / 100);
									
									break;
								case 3:
									// REQUEST_CHECK = 3;
									// when customer makes request for check,
									// we reduce the available cash balance immediately.
									// here the only thing we need to do is to set the execute date.
									break;
								case 4:
									// DEPOSIT_CHECK, add deposit to cash balance
									long depositeAmount = tb.getAmount();
									CustomerBean cb = customerDAO.getCustomerById(tb.getCustomerId());
									long cashBalance = cb.getCash();
									cb.setCash(depositeAmount + cashBalance);
									customerDAO.updateCustomer(cb);
									
//									System.out.println("Deposit check: origin cash is " + cashBalance / 100);
//									System.out.println("Deposit check: update cash is " + cb.getCash() / 100);
									
									break;
								default:
									break;
							} // switch case
						} // for loop
					} // if transaction is not null
//				} // synchronized
				
				
				// read all customers into list
//				FundBean[] fundList =  fundDAO.match();
//				request.setAttribute("fundList", fundList);
				session.setAttribute("msg", "Transit Day for <font color=\"red\">" + executeDate + "</font> has been completed");
			}
			return "success.do";
		} catch (Exception e) {
			// logout and re-login
			if (session.getAttribute("user") != null) {
				session.removeAttribute("user");
			}
			return "login.do";
		}
	}
}