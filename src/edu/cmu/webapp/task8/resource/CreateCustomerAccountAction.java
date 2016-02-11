package edu.cmu.webapp.task8.resource;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;
import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;
import edu.cmu.webapp.task8.model.CustomerDAO;

public class CreateCustomerAccountAction extends Action{
	private CreateCustomerFormBean form;
	public CreateCustomerAccountAction(CreateCustomerFormBean obj) {
		form = obj;
	}
	@Override
	public String getName() {
		return "createCustomer";
	}
	@Override
	public List<MessageJSON> perform(HttpServletRequest request) {
		CustomerDAO customer;
		List<MessageJSON> messagejson = new ArrayList<MessageJSON>();
		List<String> message = new ArrayList<String>();
		HttpSession session= request.getSession(true);
    	EmployeeBean employee = (EmployeeBean)session.getAttribute("user");
		//
    	if(employee==null) {
    		//return error  message invalid session id;
    		messagejson.add(new MessageJSON("You must log in prior to making this request"));
    		return messagejson;
    	}
        if (!(session.getAttribute("user") instanceof EmployeeBean)) {
        	messagejson.add(new MessageJSON("I/'m sorry you are not authorized to perform that action"));
        	return messagejson;
        }
        message.addAll(form.getValidationErrors());
        if(message.size()>0) {
        	for(String msg : message) {
        		messagejson.add(new MessageJSON(msg));
        	}
        	return messagejson;
        }
        //return over here to avoid session connection in case error
        customer = new CustomerDAO();
        if(!isUserNameAvailable(customer)) {
        	messagejson.add(new MessageJSON("I/'m sorry there was problem creating the account"));
        	return messagejson;
        }
        // If everything is correct, create new customer account
        CustomerBean newCustomer = new CustomerBean();
        newCustomer.setUsername(form.getUsername());
        newCustomer.setFirstName(form.getFirstName());
        newCustomer.setLastName(form.getLastName());
        newCustomer.setPassword(form.getPassword());
        newCustomer.setAddressLine1(form.getAddrLine1());
        newCustomer.setAddressLine2(form.getAddrLine2());
        newCustomer.setCity(form.getCity());
        newCustomer.setState(form.getState());
        newCustomer.setZip(form.getZip());
        newCustomer.setCash(0);
        customer.createCustomer(newCustomer);
        
        if(!isUserNameAvailable(customer)) {
        	messagejson.add(new MessageJSON("The account has been successfully created"));
        	return messagejson;
        }
        messagejson.add(new MessageJSON("I/'m sorry there was problem creating the account"));
        return messagejson;
	}
	private boolean isUserNameAvailable(CustomerDAO customer) {
		if (customer.getCustomerByUserName(form.getUsername()) == null) {
            return true;
        } else {
        	return false;
        }
	}
	
	

}
