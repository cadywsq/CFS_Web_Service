package edu.cmu.webapp.task8.resource;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.JSON.MessageJSON;
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
    private SellFundFormBean sellFundForm;

    public SellFundAction(SellFundFormBean obj) {
        sellFundForm = obj;
    }

    @Override
    public String getName() {
        return "sellFund";
    }

    @Override
    public List<MessageJSON> perform(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<String> errors = new ArrayList<String>();
        List<MessageJSON> buyFundMessages = new ArrayList<>();

        CustomerDAO customerDAO = new CustomerDAO();
        FundDAO fundDAO = new FundDAO();
        PositionDAO positionDAO = new PositionDAO();
        TransactionDAO transactionDAO = new TransactionDAO();

        // Checking if the user has logged in.
        CustomerBean customer = (CustomerBean) session.getAttribute("user");
        if (customer == null) {
            buyFundMessages.add(new MessageJSON("You must log in prior to making this request"));
            return buyFundMessages;
        }

        // Check if the logged in user is an employee.
        if (!(session.getAttribute("user") instanceof CustomerBean)) {
            buyFundMessages.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
            return buyFundMessages;
        }

        // Retireve all funds owned by logged in user
        customer = customerDAO.getCustomerByUserName(customer.getUsername());

        String Symbol = sellFundForm.getSymbol();

        FundBean fundBean = fundDAO.getFundBySymbol(Symbol);
        int fundId = fundBean.getFundId();
        PositionBean posBean = positionDAO.getPosition(customer.getCustomerId(), fundId);
        long sharePosition = posBean.getShares();

        // Get current customer bean information
        // Get current fund bean information (****** not yet ******)
        customer = (CustomerBean) request.getSession(false).getAttribute("user");
        customer = customerDAO.getCustomerByUserName(customer.getUsername());

        // Check for button click errors
        errors.addAll(sellFundForm.getValidationErrors());

        // Check enough money
        double shareToSell = 0;
        try {
            shareToSell = Double.parseDouble(sellFundForm.getShares());
        } catch (NumberFormatException nfe) {
            // errors.add("Please input a valid share number");
            // return "sellFund.jsp";
        }
        if (shareToSell + 0.0 > (sharePosition / 1000.0)) {
            buyFundMessages.add(new MessageJSON("I'm sorry, you must first deposit sufficient funds "
                    + "in your account in order to make this purchase"));
            return buyFundMessages;
        }

        // Otherwise, update available share balance and queue up a
        // pending transaction in Database
        long newShareBalance = (long) (sharePosition - shareToSell * 1000);

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

        // Return success message.
        buyFundMessages.add(new MessageJSON("The purchase was successfully completed"));
        return buyFundMessages;
    }
}
