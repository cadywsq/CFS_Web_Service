package edu.cmu.webapp.task8.controller;

import edu.cmu.webapp.task8.databean.CustomerAccountItemBean;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.FundPriceHistoryBean;
import edu.cmu.webapp.task8.databean.PositionBean;
import edu.cmu.webapp.task8.formbean.ViewCustomerFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.FundDAO;
import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;
import edu.cmu.webapp.task8.model.PositionDAO;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Logged-in employees view a customer's account information
 * including a customer's name, address, date of the last trading day, cash balance,
 * number of shares of each fund owned and the value of that position.
 */
public class ViewCustomerAction extends Action {
    private FormBeanFactory<ViewCustomerFormBean> formBeanFactory = FormBeanFactory.getInstance(ViewCustomerFormBean.class);
    private CustomerDAO customerDAO;
    private PositionDAO positionDAO;
    private FundDAO fundDAO;
    private FundPriceHistoryDAO fundPriceHistoryDAO;

    public ViewCustomerAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        positionDAO = dao.getPositionDAO();
        fundDAO = dao.getFundDAO();
        fundPriceHistoryDAO = dao.getFundPriceHistoryDAO();
    }

    @Override
    public String getName() {
        return "viewCustomer.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        HttpSession employeeSession = request.getSession();
        try {
            ViewCustomerFormBean form = formBeanFactory.create(request);
            request.setAttribute("form", form);
            EmployeeBean employee = (EmployeeBean) employeeSession.getAttribute("user");
            //Check employee login.
            if (employee == null) {
                return "login.jsp";
            }

            if (!form.isPresent()) {
                return "selectCustomerAccount.jsp";
            }

            if (form.getAction().equals("View Customer Account")) {
                errors.addAll(form.getValidationErrors());
                //check form input.
                if (errors.size() > 0) {
                    return "selectCustomerAccount.jsp";
                }
                CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
                //check customer existence.
                if (customer == null) {
                    errors.add("The customer doesn't exist");
                    return "selectCustomerAccount.jsp";
                }
                //print out viewMyAccount.
                request.setAttribute("customer", customer);
                List<PositionBean> positionList = positionDAO.getPositionsByCustomerId(customer.getCustomerId());
                List<CustomerAccountItemBean> fundList = new ArrayList<>();
                for (int i = 0; i < positionList.size(); i++) {
                    CustomerAccountItemBean item = new CustomerAccountItemBean();
                    PositionBean position = positionList.get(i);
                    item.setName(fundDAO.getFundById(position.getFundId()).getName());
                    //item.setSymbol(fundDAO.getFundById(position.getFundId()).getSymbol());
                    NumberFormat shareFormat = new DecimalFormat("#.###");
                    item.setShare(shareFormat.format(position.getShares() / 1000.0));

                    List<FundPriceHistoryBean> fundPriceList = fundPriceHistoryDAO.findFundPriceHistoryByFundId(position
                            .getFundId());
                    double price = fundPriceList.get(fundPriceList.size() - 1).getPrice() / 100.0;
                    double amount = price * position.getShares();
                    NumberFormat amountFormat = new DecimalFormat("#.##");
                    item.setPrice(amountFormat.format(amount));

                    fundList.add(item);
                }
                request.setAttribute("fundlist", fundList);

                return "viewCustomerAccount.jsp";
            }
        } catch (FormBeanException e) {
            return "selectCustomerAccount.jsp";
        }
        return "selectCustomerAccount.jsp";
    }
}
