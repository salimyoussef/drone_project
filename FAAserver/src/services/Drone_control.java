package services;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/drone")
public class Drone_control {

	// This method is called if TEXT_PLAIN is request
	@GET
	@Path("/welcome")
	@Produces(MediaType.TEXT_PLAIN)
	public String welcome() {
		return "Welcome to FAA Web Service";
	}

	// This method is called if XML is request
	@GET
	@Path("/welcome.xml")
	@Produces(MediaType.TEXT_XML)
	public String welcomeXML() {
		return "<?xml version=\"1.0\"?>" + "<title> Welcome to FAA Web Service" + "</title>";
	}

	// This method is called if HTML is request
	@GET
	@Path("/welcome.html")
	@Produces(MediaType.TEXT_HTML)
	public String welcomeHTML() {
		return "<html> " + "<title>" + "Welcome" + "</title>" + "<body><h1>" + "Welcome to FAA Web Service"
				+ "</body></h1>" + "</html> ";
	}

	@POST
	@Path("/path")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response pathREST(InputStream incomingData) {
		StringBuilder pathBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				pathBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Path Received: " + pathBuilder.toString());

		// return HTTP response 200 in case of success
		return Response.status(200).entity(pathBuilder.toString()).build();
	}

	@POST
	@Path("/position")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response positionREST(InputStream incomingData) {
		StringBuilder positionBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				positionBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println("Position Received: " + positionBuilder.toString());

		// return HTTP response 200 in case of success
		return Response.status(200).entity("Position Received").build();
	}

	@POST
	@Path("/go")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response goREST(InputStream incomingData) {
		StringBuilder pathBuilder = new StringBuilder();
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(incomingData));
			String line = null;
			while ((line = in.readLine()) != null) {
				pathBuilder.append(line);
			}
		} catch (Exception e) {
			System.out.println("Error Parsing: - ");
		}
		System.out.println(pathBuilder.toString() + " is running");

		// return HTTP response 200 in case of success
		return Response.status(200).entity(pathBuilder.toString()).build();
	}
}
