package edu.cmu.webapp.task8.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.genericdao.RollbackException;
import org.mybeans.form.FormBeanException;
import org.mybeans.form.FormBeanFactory;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.formbean.ChangePwdFormBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.CustomerDAO;
import edu.cmu.webapp.task8.model.EmployeeDAO;

public class SuccessAction extends Action {

    public String getName() {
        return "success.do";
    }

    public String perform(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session.getAttribute("user") != null) {
            if (session.getAttribute("user") instanceof CustomerBean) {
                request.setAttribute("msg", session.getAttribute("msg"));
                session.removeAttribute("msg");
                return "successCus.jsp";
            }
            else {
                request.setAttribute("msg", session.getAttribute("msg"));
                session.removeAttribute("msg");
                return "successEmp.jsp";
            }
        }
        else {
            return "login.do";
        }
    }
}