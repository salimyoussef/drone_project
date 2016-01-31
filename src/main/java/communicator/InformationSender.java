package communicator;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class InformationSender {
	public void sendPath(double x, double y, double z) {
		try {
			Client client = Client.create();
			WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/position");
			String input = "{\"x\":\"+x+\",\"y\":\"+y+\",\"z\":\"+z+\"}";
			ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
			if (response.getStatus() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
			}
			System.out.println("Output from Server .... \n");
			String output = response.getEntity(String.class);
			System.out.println(output);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
