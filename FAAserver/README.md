## RESTful web services with Jersey
Test Server：Tomcat 6.0

### POST Service URI

- http://localhost:8080/FAAserver/service/drone/position
- http://localhost:8080/FAAserver/service/drone/position

### GET Service URI

- http://localhost:8080/FAAserver/service/drone/welcome

### Sample Code for POST Service

```sh
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class TestPost {

	public static void main(String[] args) {

		try {

			Client client = Client.create();

			WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/position");

			String input = "{\"log\":\"7.1167\",\"lat\":\"43.5833\"}";

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
```