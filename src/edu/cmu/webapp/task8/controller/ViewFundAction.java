package edu.cmu.webapp.task8.controller;

import edu.cmu.webapp.task8.controller.Action;
import edu.cmu.webapp.task8.model.AbstractDAOFactory;
import edu.cmu.webapp.task8.model.FundDAO;

import javax.servlet.http.HttpServletRequest;

public class ViewFundAction extends Action {
    private FundDAO fundDAO;

    public ViewFundAction(AbstractDAOFactory dao) {
        fundDAO = dao.getFundDAO();
    }

    public String getName() {
        return "viewFund.do";
    }

    public String perform(HttpServletRequest request) {
        return null;
    }
}
