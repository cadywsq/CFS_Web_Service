package edu.cmu.webapp.task8.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.CreateEmployeeFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.EmployeeDAO;

public class CreateEmployeeAccountAction extends Action {
    private FormBeanFactory<CreateEmployeeFormBean> formBeanFactory = FormBeanFactory
            .getInstance(CreateEmployeeFormBean.class);

    private EmployeeDAO employeeDAO;

    public CreateEmployeeAccountAction(AbstractDAOFactory dao) {
        employeeDAO = dao.getEmployeeDAO();
    }

    public String getName() {
        return "createEmployee.do";
    }

    public synchronized String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();

        List<String> errors = new ArrayList<String>();
        request.setAttribute("errors", errors);

        try {
            // If user is employee and logged in already,
            // process and return success
            if (session.getAttribute("user") != null &&
                    session.getAttribute("user") instanceof EmployeeBean) {
                CreateEmployeeFormBean form = formBeanFactory.create(request);
                request.setAttribute("form", form);

                // If no parameters were passed in the form,
                // return createCustomer.jsp
                if (!form.isPresent()) {
                    return "createEmployee.jsp";
                }

                // If any validation errors, return createCustomer.jsp
                errors.addAll(form.getValidationErrors());
                if (errors.size() != 0) {
                    return "createEmployee.jsp";
                }

                // If the employee username has been registed before,
                // errors.add("This employee username has already exists")
                // and return createEmployee.jsp
                if (employeeDAO.getEmployeeByUserName(form.getUsername()) != null) {
                    errors.add("This username already exists");
                    return "createEmployee.jsp";
                }
                
                // If everything is correct, create new employee account
                // using employeeDAO to create new employeeBean
                EmployeeBean newEmployee = new EmployeeBean();
                newEmployee.setUsername(form.getUsername());
                newEmployee.setFirstName(form.getFirstName());
                newEmployee.setLastName(form.getLastName());
                newEmployee.setPassword(form.getPassword());
                
                synchronized (this) {
	                employeeDAO.createEmployee(newEmployee);
                }
                
                // remove form attribute in order to prevent further errors
                request.removeAttribute("form");
                session.setAttribute("msg", "Employee account with <font color=\"red\">" + form.getUsername()+ "</font> was created successfully.");
                
                
                return "success.do";
            } else {
                
                // if the user is customer,
                // he/she is not allowed to create new customer
                // I'm not very sure about the next page?? please correct
                
                return "login.do";
            }
        } catch (FormBeanException e) {
            errors.add(e.getMessage());
            return "createEmployee.jsp";
        }

    }
}
