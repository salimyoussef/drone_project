package tracer;

import com.google.gson.Gson;

import communicator.InformationSender;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.json.JSONObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class ConsumerTest implements Runnable {
	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	private String idDrone;

	public ConsumerTest(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber, String idDrone) {
		m_threadNumber = a_threadNumber;
		m_stream = a_stream;
		this.idDrone = idDrone;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while (it.hasNext()) {
			String msg = new String(it.next().message());
			JSONObject json = new JSONObject(msg);
//			try {
//				Client client = Client.create();
//				WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/position");
//				String input = "{\"x\":\"" + json.getDouble("x") + "\",\"y\":\"" + json.getDouble("y") + "\"}";
//				ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
//				if (response.getStatus() != 200) {
//					throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
//				}
//				System.out.println("Output from Server .... \n");
//				String output = response.getEntity(String.class);
//				System.out.println(output);
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			System.out.println("*** " + idDrone + " moved to these coordinates: (" + json.getDouble("x") + ","
//					+ json.getDouble("y") + "," + json.getDouble("z") + ") ***");
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}