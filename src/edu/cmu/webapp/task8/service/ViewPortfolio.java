package edu.cmu.webapp.task8.service;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import edu.cmu.webapp.task8.JSON.ViewPortfolioJSON;
import edu.cmu.webapp.task8.resource.ViewPortfolioAction;
/**
 * @author Rahul Somani
 *
 */
@Path("/viewPortfolio")
public class ViewPortfolio {
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public  ViewPortfolioJSON depositCheck(@Context HttpServletRequest request) {
        return new ViewPortfolioAction().perform(request);
    }
}
