package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.DepositCheckFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

/**
 * 
 * @author Hunter
 *
 */
public class DepositCheckAction extends Action {
    private DepositCheckFormBean form;

    public DepositCheckAction(DepositCheckFormBean obj) {
        this.form = obj;
    }
    @Override
    public String getName() {
        return "depositCheck";
    }

    @Override
    public synchronized List<MessageJSON> perform(HttpServletRequest request) {
        List<MessageJSON> messagejson = new ArrayList<MessageJSON>();
        List<String> message = new ArrayList<String>();
        HttpSession session= request.getSession(true);
        EmployeeBean employee = (EmployeeBean) session.getAttribute("user");
        if(employee == null) {
            messagejson.add(new MessageJSON("You must log in prior to making this request"));
            return messagejson;
        }
        if (!(session.getAttribute("user") instanceof EmployeeBean)) {
            messagejson.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
            return messagejson;
        }
        message.addAll(form.getValidationErrors());
        if(message.size() > 0) {
            for(String msg : message) {
                messagejson.add(new MessageJSON(msg));
            }
            return messagejson;
        }
        CustomerDAO customerDAO = new CustomerDAO();
        TransactionDAO transactionDAO = new TransactionDAO();
        CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
        TransactionBean transaction = new TransactionBean();
        transaction.setCustomerId(customer.getCustomerId());
        transaction.setAmount((long) (Double.parseDouble(form.getDollarAmount()) * 100));
        transaction.setTransactionType(TransactionBean.DEPOSIT_CHECK);
        transaction.setExecuteDate(null);
        transaction.setFundId(-1);
        transaction.setShares(-1);
        try {
            transactionDAO.createTransaction(transaction);
            messagejson.add(new MessageJSON("The account has been successfully updated."));
            return messagejson;
        } catch (HibernateException e) {
            messagejson.add(new MessageJSON("I am sorry, there was a problem depositing the money."));
            return messagejson;
        }
    }
    
}