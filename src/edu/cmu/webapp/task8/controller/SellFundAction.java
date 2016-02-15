package edu.cmu.webapp.task8.controller;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.BuyFormBean;
import edu.cmu.webapp.task8.formbean.SellFundFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

public class SellFundAction extends Action {
	private FormBeanFactory<SellFundFormBean> formBeanFactory = FormBeanFactory.getInstance(SellFundFormBean.class);

	private CustomerDAO customerDAO;
	private FundDAO fundDAO;
	private PositionDAO positionDAO;
	private TransactionDAO transactionDAO;

	public SellFundAction(AbstractDAOFactory model) {
		customerDAO = model.getCustomerDAO();
		fundDAO = model.getFundDAO();
		positionDAO = model.getPositionDAO();
		transactionDAO = model.getTransactionDAO();
	}

	public String getName() {
		return "sellFund.do";
	}

	public String perform(HttpServletRequest request) {
		HttpSession session = request.getSession(false);

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("selectedfundName", "");
		request.setAttribute("hmFundBean", "");
		try {
			SellFundFormBean form = formBeanFactory.create(request);
			// If user is customer and logged in already,
			// process sell fund action and return success.
			if (session != null && session.getAttribute("user") != null
					&& session.getAttribute("user") instanceof CustomerBean) {

				// Retireve all funds owned by logged in user
				CustomerBean customer = (CustomerBean) request.getSession(false).getAttribute("user");
				customer = customerDAO.getCustomerByUserName(customer.getUsername());
				List<FundBean> fundList = fundDAO.getCustomerFundList(customer.getCustomerId());
				HashMap<String, String> hmFundBean = new HashMap<String,String>();
				String selectedfundName ="";
				if (fundList != null && fundList.size() > 0) 
					for(FundBean fb: fundList) {
						if(fb.getName().equals(selectedfundName))
							hmFundBean.put(fb.getName(), "selected");
						else
							hmFundBean.put(fb.getName(), "");
					}
				request.setAttribute("fundList", fundList);
				request.setAttribute("hmFundBean", hmFundBean);
				
				if(selectedfundName==null) {
					return "sellFund.jsp";
				}
				
				// Set decimal format
				DecimalFormat df3 = new DecimalFormat("#,###");

				String fundName ="";
				
				FundBean fundBean = fundDAO.getFundByName(fundName);
				int fundId = fundBean.getFundId();
				PositionBean posBean = positionDAO.getPosition(customer.getCustomerId(), fundId);
				long sharePosition = posBean.getShares();

				//Get current customer bean information
				//Get current fund bean information (****** not yet ******)
				customer = (CustomerBean) request.getSession(false).getAttribute("user");
				customer = customerDAO.getCustomerByUserName(customer.getUsername());
				
				request.setAttribute("selectedfundName", selectedfundName);
				request.setAttribute("fundSymbol", fundBean.getSymbol());
				
				DecimalFormat dfForShares = new DecimalFormat("#,##0.000");
				request.setAttribute("sharesown", dfForShares.format(posBean.getShares() / 1000.0));


				//Check for button click errors
				errors.addAll(form.getValidationErrors());
				if (errors.size() > 0) {
					return "sellFund.jsp";
				}
				
				if(form.getShares().isEmpty()) {
					return "sellFund.jsp";
				}

				double shareToSell = 0;
				try {
					shareToSell = Double.parseDouble(form.getShares());
				} catch (NumberFormatException nfe) {
					errors.add("Please input a valid share number");
					return "sellFund.jsp";
				}
				if (shareToSell + 0.0 > (sharePosition / 1000.0)) {
					errors.add("The shares to sell exceed the shares hold");
					return "sellFund.jsp";
				}

				// Otherwise, update available share balance and queue up a
				// pending transaction in Database
				long newShareBalance = (long) (sharePosition - shareToSell * 1000);

				synchronized (this) {
					posBean.setShares(newShareBalance);
					positionDAO.updatePosition(posBean);

					// Once transition day action occurs, verify these buying
					// fund action.
					TransactionBean transaction = new TransactionBean();
					transaction.setCustomerId(customer.getCustomerId());
					transaction.setFundId(fundId);
					transaction.setShares((long) (shareToSell * 1000));
					transaction.setTransactionType(TransactionBean.SELL_FUND);
					// how to set execute date without explicitly passing
					// parameter?
					// ****Need revise******
					transaction.setExecuteDate(null);
					transaction.setAmount(-1);
					transactionDAO.createTransaction(transaction);

					// set balance attributes
					DecimalFormat df2 = new DecimalFormat("#,##0.00");
					request.setAttribute("balance", "$ " + df2.format(customer.getCash()));
				}

				// show fund position list of the customer
				List<PositionBean> fundList1 = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
				request.setAttribute("fundList", fundList1);
				
				session.setAttribute("msg", "Fund: <font color=\"red\">" + fundBean.getSymbol() + "</font>"
						+ " Shares: <font color=\"red\">" +form.getShares() + "</font>"
						+ " has been sold successfully.\n"
						+ " Position will be updated after transition day.");
				request.removeAttribute("form");

				return "success.do";
			} else {
				// logout and re-login
				if (session.getAttribute("user") != null) {
					session.removeAttribute("user");
				}
				return "login.do";
			}
		} catch (Exception e) {
			errors.add(e.getMessage());
			return "sellFund.jsp";
		}
	}
}
