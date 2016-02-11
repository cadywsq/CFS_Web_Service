package edu.cmu.webapp.task8.controller;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Convert;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.formbean.ResearchFundFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;

public class ResearchFundAction extends Action {

	// private static Logger logger =
	// Logger.getLogger(ResearchFundAction.class);
	private FormBeanFactory<ResearchFundFormBean> formBeanFactory = FormBeanFactory
			.getInstance(ResearchFundFormBean.class);

	private FundDAO fundDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public ResearchFundAction(AbstractDAOFactory model) {
		fundDAO = model.getFundDAO();
		fundPriceHistoryDAO = model.getFundPriceHistoryDAO();
	}

	public String getName() {
		return "researchFund.do";
	}

	public String perform(HttpServletRequest request) {
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("fundPriceHistory", "");
		request.setAttribute("msg", "");
		request.setAttribute("dataPoints", "");
		HttpSession session = request.getSession();

		try {
			// Only logged in customer can research funds
			if (session == null || session.getAttribute("user") == null
					|| request.getSession().getAttribute("user") instanceof EmployeeBean) {
				errors.add("Please log in as a customer");
				return "login.jsp";
			}

			ResearchFundFormBean form = formBeanFactory.create(request);
			request.setAttribute("form", form);

			List<FundBean> fundList = fundDAO.getFundList();
			request.setAttribute("fundList", fundList);

			if (!form.isPresent()) {
				return "researchFunds.jsp";
			}

			errors.addAll(form.getValidationErrors());
			if (errors.size() > 0) {
				return "researchFunds.jsp";
			}

			String fundName = form.getFundName();
			// String fundSymbol = form.getFundSymbol().toUpperCase();
			// if you want to see the description of some paragraphs or
			// tables(****).
			// request.setAttribute("description", "");

			// try to find the fund by fund name
			FundBean fundDetails = fundDAO.getFundByName(fundName);
			if (fundDetails == null) {
				errors.add("Fund does not exist");
				return "researchFunds.jsp";
			}

			// determine fund id
			int fId = fundDetails.getFundId();
			List<FundPriceHistoryBean> fundPriceHistory  = new ArrayList<FundPriceHistoryBean>();
			if (form.getAction() != null && form.getAction().equals("Fund History")) {
				fundPriceHistory = fundPriceHistoryDAO.findFundPriceHistoryByFundId(fId);
//				System.out.println("*** price here: " + fundPriceHistory.get(0).getPrice());
				if (fundPriceHistory.size() == 0) {
					request.setAttribute("msg", "There is no history for " + fundDetails.getSymbol() + ":"
							+ fundDetails.getName() + " " + " fund");
				} else {
					//done to display table
					request.setAttribute("msg", " ");
					
				}
			}
			request.setAttribute("fundPriceHistory", fundPriceHistory);
			request.setAttribute("fund", fundDetails);

			return "researchFunds.jsp";
		} catch (FormBeanException e) {
			errors.add(e.getMessage());
			return "researchFunds.jsp";
		} catch (Exception e) {
			errors.add("");
			return "researchFunds.jsp";
		}
	}
}
//
// private List<Map<String, String>> getFundPriceHistory(
// int fundId) throws RollbackException {
// List<Map<String,String>> fundPriceHistory = new
// ArrayList<Map<String,String>>();
// FundPriceHistoryBean[] fundPriceHistoryBean;
//
// fundPriceHistoryBean = fundPriceHistoryDAO. match(MatchArg.equals("fundId",
// fundId));
// if (fundPriceHistoryBean != null) {
// for (FundPriceHistoryBean hBean: fundPriceHistoryBean) {
//
// String id=Integer.toString(hBean.getFundId());
// Map<String,String> tmp = new HashMap<String,String>();
// tmp.put("fundId",id);
// NumberFormat formatter = new DecimalFormat("#,##0.00");
// String newPrice=formatter.format(hBean.getPrice()/100);
// tmp.put("price",newPrice);
// tmp.put("date",hBean.getPriceDate());
// tmp.put("fundName", fundDAO.read(hBean.getFundId()).getName());
//
// fundPriceHistory.add(tmp);
// }
// return fundPriceHistory;
// }
// else{
// return fundPriceHistory;
// }
//
// }

// private String chartData(int fundId) throws RollbackException {
// FundPriceHistoryBean[] fundPriceHistoryBean = fundPriceHistoryDAO.
// match(MatchArg.equals("fundId",fundId));
// StringBuilder data = new StringBuilder();
// if(fundPriceHistoryBean != null){
// for(FundPriceHistoryBean hBean: fundPriceHistoryBean){
// data.append(hBean.getPriceDate());
// data.append(",");
// data.append(((double)hBean.getPrice())/100);
// data.append(",");
// }
// }

/* System.out.println(data.toString()); */
// return data.toString();
//
// }
// }
