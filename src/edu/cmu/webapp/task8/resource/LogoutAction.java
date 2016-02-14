package edu.cmu.webapp.task8.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.JSON.MessageJSON;

public class LogoutAction extends Action {
    public LogoutAction() {
    }

    public String getName() {
        return "logout";
    }

    public MessageJSON perform(HttpServletRequest request) {
    	HttpSession session = request.getSession(false);
        session.setAttribute("user", null);
        session.invalidate();

        return new MessageJSON("You've been logged out");
    }
}
