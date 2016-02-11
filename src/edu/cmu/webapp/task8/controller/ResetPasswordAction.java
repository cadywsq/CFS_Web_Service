package edu.cmu.webapp.task8.controller;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.ResetPwdFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

/**
 * Logged-in employees can reset the password for a customer's account.
 */
public class ResetPasswordAction extends Action {
    private FormBeanFactory<ResetPwdFormBean> formBeanFactory = FormBeanFactory.getInstance(ResetPwdFormBean.class);
    private CustomerDAO customerDAO;

    public ResetPasswordAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
    }

    @Override
    public String getName() {
        return "resetPassword.do";
    }

    @Override
    public String perform(HttpServletRequest request) {
		// get session info
		HttpSession session = request.getSession();

//		System.out.println("ResetPasswordAction: has arrived.");
		
		// set errors attributes
		List<String> errors = new ArrayList<String>();
		request.setAttribute("errors", errors);

        try {
            
			// Only logged in employee can deposit check
			if (session.getAttribute("user") != null &&
					session.getAttribute("user") instanceof EmployeeBean) {
	        	ResetPwdFormBean form = formBeanFactory.create(request);
	            request.setAttribute("form", form);
	            
//	            EmployeeBean employee = (EmployeeBean) session.getAttribute("user");
	            
//	            System.out.println("ResetPasswordAction: user is employee.");
	            
				// read all customers into list
				// request.setAttribute("customerList", customerDAO.match());
				List<CustomerBean> customerList = customerDAO.getAllCustomers(); 
						//customerDAO.getCustomerById();
				request.setAttribute("customerList", customerList);
	            
	            if (!form.isPresent()) {
	                return "resetPassword.jsp";
	            }
	            
	            errors.addAll(form.getValidationErrors());
	            if (errors.size() > 0) {
	                return "resetPassword.jsp";
	            }
	            
	            CustomerBean customer = customerDAO.getCustomerByUserName(form.getUsername());
                if (customer == null) {
                    errors.add("The customer doesn't exist.");
                    return "resetPassword.jsp";
                }
                
//                System.out.println("ResetPasswordAction: customer:" + customer.getUsername());
                
                if (!form.getNewPassword().equals(form.getConfirmPassword())) {
                	errors.add("The password doesn't match.");
                	return "resetPassword.jsp";
                }
                customer.setPassword(form.getNewPassword());
                customerDAO.updateCustomer(customer);
                
                session.setAttribute("msg", "Username: <font color=\"red\">" +
						customer.getUsername() + " </font>\n" +
						"Password has been reset successfully.");
				request.removeAttribute("form");
                
	            return "success.do";
			} else {
				return "login.do";
			}
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "resetPassword.jsp";
        }
        
    }
}
