package edu.cmu.webapp.task8.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.CreateCustomerFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;

public class CreateCustomerAccountAction extends Action {
    private FormBeanFactory<CreateCustomerFormBean> formBeanFactory = FormBeanFactory
            .getInstance(CreateCustomerFormBean.class);
    
    private CustomerDAO customerDAO;
    
    public CreateCustomerAccountAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
    }
    
    public String getName() {
        return "createCustomer.do";
    }

    public synchronized String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        // set errors attribute
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            // If user is employee and logged in already,
            // process and return success
            if (session.getAttribute("user") != null &&
                    session.getAttribute("user") instanceof EmployeeBean) {
                // extract form from http request
                CreateCustomerFormBean form = formBeanFactory.create(request);
                request.setAttribute("form", form);

                // If no parameters were passed in the form,
                // return createCustomer.jsp
                if (!form.isPresent()) {
                    return "createCustomer.jsp";
                }

                // If any validation errors, return createCustomer.jsp
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "createCustomer.jsp";
                }

                // If the username has been registered before,
                // errors.add("This username already exists")
                // and return createCustomer.jsp
                if (customerDAO.getCustomerByUserName(form.getUsername()) != null) {
                    errors.add("This username already exists");
                    return "createCustomer.jsp";
                }
                
	            // If everything is correct, create new customer account
                // using customerDAO to create new customerBean
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
	            customerDAO.createCustomer(newCustomer);
                
                // remove form attribute in order to prevent further errors
                request.removeAttribute("form");
                session.setAttribute("msg", "Customer account created successfully");

                return "success.do";
            } else {
             // if the user is customer,
             // he/she is not allowed to create new customer
             // Remove session and go back to log in page
                if (session.getAttribute("user") != null)
                    session.removeAttribute("user");

                return "login.do";                                          
            }
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createCustomer.jsp";
        }

    }
}
