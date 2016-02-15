package edu.cmu.webapp.task8.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.databean.ValueFormatter;
import edu.cmu.webapp.task8.formbean.ViewCustomerFormBean2;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;

/**
 * After logging, customer sees his account, name, address, date of the last
 * trading day, cash balance, number of shares of each fund owned and the value
 * of that position (shares times price of fund as of the last trading day).
 * From this view there will be links to most other operations.
 */
public class ViewCustomerAccountAction2 extends Action {
	private FormBeanFactory<ViewCustomerFormBean2> formBeanFactory = FormBeanFactory
			.getInstance(ViewCustomerFormBean2.class);

	private PositionDAO positionDAO;
	private FundDAO fundDAO;
	private CustomerDAO customerDAO;
	private FundPriceHistoryDAO fundPriceHistoryDAO;

	public ViewCustomerAccountAction2(AbstractDAOFactory dao) {
		positionDAO = dao.getPositionDAO();
		fundDAO = dao.getFundDAO();
		fundPriceHistoryDAO = dao.getFundPriceHistoryDAO();
		customerDAO = dao.getCustomerDAO();
	}

	public String getName() {
		return "viewCustomerAccount2.do";
	}

	public String perform(HttpServletRequest request) {

		HttpSession session = request.getSession(false);

		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);
		request.setAttribute("customerUserNameList", "");
		request.setAttribute("hmCustomerUserName", "");

		try {
			ViewCustomerFormBean2 form = formBeanFactory.create(request);
			// If user is customer and logged in already,
			// process sell fund action and return success.
			if (session != null && session.getAttribute("user") != null
					&& session.getAttribute("user") instanceof EmployeeBean) {

				// Retireve all funds owned by logged in user
				EmployeeBean employee = (EmployeeBean) request.getSession(false).getAttribute("user");
				if (employee == null) {
					return "login.jsp";
				}
				List<String> customerUserNameList = customerDAO.getAllCustomersUserName();
				HashMap<String, String> hmCustomerUserName = new HashMap<String, String>();

				String selectedCustomerUserName = form.getUsername();
				for (String str : customerUserNameList) {
					if (str.equals(selectedCustomerUserName))
						hmCustomerUserName.put(str, "selected");
					else
						hmCustomerUserName.put(str, "");
				}
				request.setAttribute("customerUserNameList", customerUserNameList);
				request.setAttribute("hmCustomerUserName", hmCustomerUserName);
				request.setAttribute("selectedCustomerUserName",selectedCustomerUserName);
				if (selectedCustomerUserName == null) {
					return "viewCustomerAccount2.jsp";
				}

				//Check for button click errors
				errors.addAll(form.getValidationErrors());
				if (errors.size() > 0) {
					return "viewCustomerAccount2.jsp";
				}
				CustomerBean customer = customerDAO.getCustomerByUserName(selectedCustomerUserName);
				request.setAttribute("fund", positionDAO.getPositionsByCustomerId(customer.getCustomerId()));
				double availableBalance = customer.getCash() / 100.0;
				String balanceString = ValueFormatter.round(availableBalance, 2);
				request.setAttribute("cash", balanceString);
				request.setAttribute("customer",customer);
				request.setAttribute("message", "Account For: " + customer.getFirstName() +" " + customer.getLastName());

				List<PositionBean> positionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
				// System.out.println("inside VieMyAccountAction: " +
				// positionList.size());
				List<CustomerAccountItemBean> fundList = new ArrayList<>();

				if (positionList != null)
					for (int i = 0; i < positionList.size(); i++) {

						CustomerAccountItemBean item = new CustomerAccountItemBean();
						PositionBean position = positionList.get(i);
						item.setName(fundDAO.getFundById(position.getFundId()).getName());
						//item.setSymbol(fundDAO.getFundById(position.getFundId()).getSymbol());
//						NumberFormat shareFormat = new DecimalFormat("#.###");
						item.setShare(ValueFormatter.round(position.getShares() / 1000.0, 3));
						List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO.findFundPriceHistoryByFundId(position.getFundId());
						double price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100.0;
						double amount = price * position.getShares() / 1000.0;

						item.setPrice(ValueFormatter.round(amount, 2));
						fundList.add(item);
					}
				// request.setAttribute("fundlist", fundList);
				session.setAttribute("fundlist", fundList);
//				String cash = ValueFormatter.round(customer.getCash() / 100.0, 2);
				return "viewCustomerAccount2.jsp";

			}
		} catch (Exception e) {
			return "viewCustomerAccount2.jsp";
		}
		return "viewCustomerAccount2.jsp";
	}
}
