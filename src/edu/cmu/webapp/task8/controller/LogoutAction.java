package edu.cmu.webapp.task8.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class LogoutAction extends Action {
    public LogoutAction() {
    }

    public String getName() {
        return "logout.do";
    }

    public String perform(HttpServletRequest request) {
//        System.out.println("Logout: perform ");
    	
    	HttpSession session = request.getSession(false);
        session.setAttribute("user", null);
        session.invalidate();

        return "logout.jsp";
    }
}
