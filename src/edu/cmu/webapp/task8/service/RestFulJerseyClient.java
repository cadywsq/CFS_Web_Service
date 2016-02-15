package edu.cmu.webapp.task8.service;
import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

import edu.cmu.webapp.task8.model.FundPriceHistoryDAO;

public class RestFulJerseyClient {

	private static final String webServiceURI = "http://localhost:8080/CFSTeam11";

	public static void main(String[] args) throws ParseException {
		
		/*SimpleDateFormat sdfCurrentDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		System.out.println(sdfCurrentDate.format(new Date()));
		*/
		
		
		System.out.println(createRandomPrice(100));
		System.out.println(createRandomPrice(100));
		
		FundPriceHistoryDAO	fundPriceHistoryDAO = new FundPriceHistoryDAO();
		SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		Date date = formatter.parse(fundPriceHistoryDAO.getMaxDate());
		System.out.println(fundPriceHistoryDAO.getMaxDate());
		
		
		
	/*	ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		URI serviceURI = UriBuilder.fromUri(webServiceURI).build();
		WebTarget webTarget = client.target(serviceURI);

		// response
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class).toString());

		// text
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_PLAIN).get(String.class));

		// xml
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_XML).get(String.class));

		// html
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_HTML).get(String.class));
*/	}
	private static long createRandomPrice(long currentPrice) {
		int lo = -10;
		int hi = 10;
		Random ran = new Random();
		int x = ran.nextInt(hi - lo) + lo;
		double dx = x / 100.0;
//		System.out.print([i] + " -> ");
		currentPrice = (long) (currentPrice * (1 + dx));
//		System.out.println(prices[i]);
		return currentPrice;
	}	

}
