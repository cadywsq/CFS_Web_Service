package edu.cmu.webapp.task8.service;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import org.glassfish.jersey.client.ClientConfig;

public class RestFulJerseyClient {

	private static final String webServiceURI = "http://localhost:8080/CFSTeam11";

	public static void main(String[] args) {
		
		SimpleDateFormat sdfCurrentDate = new SimpleDateFormat("YYYY-MM-dd hh:mm:ss");
		System.out.println(sdfCurrentDate.format(new Date()));
		
		ClientConfig clientConfig = new ClientConfig();
		Client client = ClientBuilder.newClient(clientConfig);
		URI serviceURI = UriBuilder.fromUri(webServiceURI).build();
		WebTarget webTarget = client.target(serviceURI);

		// response
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.APPLICATION_JSON).get(Response.class).toString());

		/*// text
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_PLAIN).get(String.class));

		// xml
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_XML).get(String.class));

		// html
		System.out.println(webTarget.path("rest").path("login").request()
				.accept(MediaType.TEXT_HTML).get(String.class));*/
	}

}
