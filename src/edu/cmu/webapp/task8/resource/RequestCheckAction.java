package edu.cmu.webapp.task8.resource;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.TransactionBean;
import edu.cmu.webapp.task8.formbean.RequestCheckFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.TransactionDAO;

/**
 * 
 * @author Hunter Na
 *
 */
public class RequestCheckAction extends Action {
    private RequestCheckFormBean form;
    
    public RequestCheckAction(RequestCheckFormBean obj) {
        this.form = obj;
    }

    @Override
    public String getName() {
        return "requestCheck";
    }

    @Override
    public synchronized List<MessageJSON> perform(HttpServletRequest request) {
        List<MessageJSON> messagejson = new ArrayList<MessageJSON>();
        List<String> message = new ArrayList<String>();
        HttpSession session= request.getSession(true);
        CustomerBean customer = null;
        try {
            customer = (CustomerBean) session.getAttribute("user");
        } catch (Exception e) {
            messagejson.add(new MessageJSON("I'm sorry you are not authorized to perform that action"));
            return messagejson;
        }
        if(customer == null) {
            messagejson.add(new MessageJSON("You must log in prior to making this request"));
            return messagejson;
        }
        if (!(session.getAttribute("user") instanceof CustomerBean)) {
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
        double availableBalance = customer.getCash() / 100.0;
        double withdrawAmount = 0d;
        try {
            withdrawAmount = Double.parseDouble(this.form.getDollarAmount());
        } catch (Exception e) {
            messagejson.add(new MessageJSON("Amount should be a valid number"));
            return messagejson;
        }
        if (withdrawAmount > availableBalance) {
            messagejson.add(new MessageJSON("I'm sorry, the amount requested is greater than the balance of your account"));
            return messagejson;
        }
        SimpleDateFormat sdfDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
        availableBalance = (availableBalance - withdrawAmount) * 100;
        customer.setCash((long) availableBalance);
        customerDAO.updateCustomer(customer);
        //entering data into transaction history table
        TransactionBean transaction = new TransactionBean();
        transaction.setCustomerId(customer.getCustomerId());
        transaction.setAmount((long) (Double.parseDouble(this.form.getDollarAmount()) * 100));
        transaction.setTransactionType(TransactionBean.REQUEST_CHECK);
        transaction.setFundId(-1);
        transaction.setExecuteDate(sdfDate.format(new Date()));
        transaction.setShares(-1);
        try {
            transactionDAO.createTransaction(transaction);
            messagejson.add(new MessageJSON("The withdrawal was successfully completed"));
            return messagejson;
        } catch (HibernateException e) {
            messagejson.add(new MessageJSON("I am sorry, there was a problem depositing the money"));
            return messagejson;
        }
    }

}
