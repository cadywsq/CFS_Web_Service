package edu.cmu.webapp.task8.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import edu.cmu.webapp.task8.databean.CustomerBean;
import edu.cmu.webapp.task8.databean.EmployeeBean;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;

@SuppressWarnings("serial")
public class Controller{
    public void init() throws ServletException {
        AbstractDAOFactory dao = AbstractDAOFactory.getDAOFactory();
        dao.databasePrecheck();
        Action.add(new BuyFundAction(dao));
        Action.add(new ChangePasswordAction(dao));
        Action.add(new CreateEmployeeAccountAction(dao));
        Action.add(new CreateCustomerAccountAction(dao));
        Action.add(new CreateFundAction(dao));
        Action.add(new DepositCheckAction(dao));
        Action.add(new LoginAction(dao));
        Action.add(new LogoutAction());
        Action.add(new RequestCheckAction(dao));
        Action.add(new ResearchFundAction(dao));
        Action.add(new SellFundAction(dao));
        Action.add(new ViewCustomerAction(dao));
        Action.add(new ViewCustomerAccountAction2(dao));
        Action.add(new ViewFundAction(dao));
        Action.add(new ViewMyAccountAction(dao));
        Action.add(new SuccessAction());
        Action.add(new EmployeeMainAction());
        Action.add(new ResetPasswordAction(dao));
        Action.add(new ViewMyTransactionHistoryAction(dao));
        Action.add(new ViewCustomerTransactionHistoryAction2(dao));
        Action.add(new TransitionDayAction(dao));
        
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nextPage = this.performTheAction(request);
        
//        System.out.println("Controller: doGet: nextPage = " + nextPage);
        
        this.sendToNextPage(nextPage, request, response);
    }
    private String performTheAction(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        String servletPath = request.getServletPath();
        String action = getActionName(servletPath);
        if (session.getAttribute("user") == null) {
//            System.out.println("controller: user is null");
            return Action.perform("login.do", request);
        }
//        System.out.println("Controller: performTheAction: " + action);
        
        return Action.perform(action, request);
    }
    private void sendToNextPage(String nextPage, HttpServletRequest request,
            HttpServletResponse response) throws IOException, ServletException {
        if (nextPage == null) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, request.getServletPath());
            return;
        }
        if (nextPage.endsWith(".do")) {
        	System.out.println("Controller: " + nextPage);
            response.sendRedirect(nextPage);
            return;
        }
        if (nextPage.endsWith(".jsp")) {
            RequestDispatcher d = request.getRequestDispatcher("WEB-INF/" + nextPage);
            d.forward(request, response);
            return;
        }
        response.sendRedirect(nextPage);
    }
    private String getActionName(String path) {
        // We're guaranteed that the path will start with a slash
        int slash = path.lastIndexOf('/');
        return path.substring(slash + 1);
    }
}
