package communicator;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

public class PathReceiverForCommunicator implements Runnable {
	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	private Communicator receiver;

	public PathReceiverForCommunicator(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber,
			Communicator receiver) {

		m_stream = a_stream;
		m_threadNumber = a_threadNumber;
		this.receiver = receiver;

	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();

		while (it.hasNext()) {
			String msg = new String(it.next().message());

			try {
				if (msg.equals("go!")) {
					try {
						Client client2 = Client.create();
						WebResource webResource = client2.resource("http://localhost:8080/FAAserver/service/drone/go");
						// String input = "{";
						// for (int i = 0; i < p.getLength(); i++) {
						// input = input + "{x:" + p.getPathPoint(i).getX() + "
						// y:" + p.getPathPoint(i).getY() + "} ";
						// }
						// input = input + "}";
						ClientResponse response = webResource.type("application/json").post(ClientResponse.class,
								receiver.getDrone());
						if (response.getStatus() != 200) {
							throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Client client = Client.create();
					WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/path");
					// String input = "{";
					// for (int i = 0; i < p.getLength(); i++) {
					// input = input + "{x:" + p.getPathPoint(i).getX() + " y:"
					// +
					// p.getPathPoint(i).getY() + "} ";
					// }
					// input = input + "}";
					ClientResponse response = webResource.type("application/json").post(ClientResponse.class, msg);
					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
					}
					// System.out.println("Output from Server .... \n");
					String output = response.getEntity(String.class);
					// System.out.println(output);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}
