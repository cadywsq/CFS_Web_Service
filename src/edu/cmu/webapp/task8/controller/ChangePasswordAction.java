package edu.cmu.webapp.task8.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.ChangePwdFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.EmployeeDAO;

public class ChangePasswordAction extends Action {
    private FormBeanFactory<ChangePwdFormBean> formBeanFactory = FormBeanFactory
            .getInstance(ChangePwdFormBean.class);
    private CustomerDAO customerDAO;
    private EmployeeDAO employeeDAO;

    public ChangePasswordAction(AbstractDAOFactory dao) {
        customerDAO = dao.getCustomerDAO();
        employeeDAO = dao.getEmployeeDAO();
    }

    public String getName() {
        return "changePwd.do";
    }

    public synchronized String perform(HttpServletRequest request) {
    	HttpSession session = request.getSession();
    	
        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);
        
        try {
            if (request.getSession().getAttribute("user") == null) {
                return "login.do";
            }
            
            // If user is customer and logged in already,
            // process and return success
            if (session.getAttribute("user") instanceof CustomerBean) {
                // get user attribute from session
                CustomerBean user = (CustomerBean) request.getSession().getAttribute("user");

                // extract change pwd form bean from request
                ChangePwdFormBean form = formBeanFactory.create(request);
                request.setAttribute("form", form);

                // if no parameters were passed in the form,
                // return customerchangepwd jsp (please confirm)
                if (!form.isPresent()) {
                    return "changePassword.jsp";
                }

                // If any validation errors,
                // return customerchangepwd jsp (please confirm)
                errors.addAll(form.getValidationErrors());
                if (errors.size() > 0) {
                    return "changePassword.jsp";
                }
                
//                System.out.println("ChangePasswordAction: Perform: old " + form.getOldPassword());
//                System.out.println("ChangePasswordAction: Perform: old " + form.getNewPassword());
//                System.out.println("ChangePasswordAction: Perform: old " + form.getConfirmedPassword());
                
                // the user need to input the origin pwd
                // if it doesn't match the one in the DB,
                // show errors and return customerchangepwd jsp (please confirm)
                if (!form.getOldPassword().equals(customerDAO.getCustomerByUserName(user.getUsername()).getPassword())) {
                    errors.add("Incorrect original password");
                }
                
                if (errors.size() > 0) {
                    return "changePassword.jsp";
                }
                
                
                // otherwise, the password is correct and revise to new pwd
                user.setPassword(form.getNewPassword());
                customerDAO.updateCustomer(user);
                
                request.removeAttribute("form");
                session.setAttribute("msg", "Password changed successfully for: " + user.getUsername());
                
                // which page should we return back to ?
                
                return "success.do";
                
            } else if (session.getAttribute("user") instanceof EmployeeBean) {
                // get user attribute from session
                EmployeeBean user = (EmployeeBean) request.getSession().getAttribute("user");
                
                // extract change pwd form bean from request
                ChangePwdFormBean form = formBeanFactory.create(request);
                request.setAttribute("form", form);

                // if no parameters were passed in the form,
                // return employeechangepwd jsp (please confirm)
                if (!form.isPresent()) {
                    return "changePasswordEmployee.jsp";
                }

                
                errors.addAll(form.getValidationErrors());
                if (errors.size() > 0) {
                    return "changePasswordEmployee.jsp";
                }
                
                if (!form.getOldPassword().equals(employeeDAO.getEmployeeByUserName(user.getUsername()).getPassword())) {
                    errors.add("Incorrect original password");
                }
                
                if (errors.size() > 0) {
                    return "changePasswordEmployee.jsp";
                }

                user.setPassword(form.getNewPassword());
                employeeDAO.updateEmployee(user);
                
                session.setAttribute("msg", "Password changed successfully for: " + user.getUsername());

                if (errors.size() > 0) 
                    return "changePasswordEmployee.jsp";   
                
                return "success.do";
            }
            else {
                if (session.getAttribute("user") != null)
                    session.removeAttribute("user");

                return "login.do";
            }

        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "error.jsp";
        }

    }
}
