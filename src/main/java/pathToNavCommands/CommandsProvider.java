package pathToNavCommands;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import kafka.producer.ProducerConfig;
import path.PathPoint;
import utils.MyConstants;
import java.util.List;
import java.util.Properties;

/**
 * Created by sy306571 on 28/01/16.
 */
public class CommandsProvider {
	private String drone;
	private Producer<String, String> producer;
	private List<PathPoint> path;

	public CommandsProvider(String drone) {
		this.drone = drone;

		Properties props = new Properties();
		String listBrokers = "localhost:" + MyConstants.INITIAL_BROKER_PORT;
		for (int i = 1; i < MyConstants.NUMBER_OF_BROKERS; i++) {
			listBrokers = listBrokers + ",localhost:" + (MyConstants.INITIAL_BROKER_PORT + i);
		}
		props.put("metadata.broker.list", listBrokers);

		props.put("serializer.class", "kafka.serializer.StringEncoder");
		// Partitionnement pas important pour l'instant
		// props.put("partitioner.class", "SimplePartitioner");
		props.put("request.required.acks", "1");
		ProducerConfig config = new ProducerConfig(props);

		producer = new Producer<String, String>(config);
	}

	public void setPath(List<PathPoint> path) {
		this.path = path;
	}

	public void sendCommands() {
		JsonArray jsonCommands = PathToJsonCommandConverter.convertPath(path);

		String msg = jsonCommands.toString();
		KeyedMessage<String, String> data = new KeyedMessage<>(drone + "-in", msg);

		producer.send(data);
	}

	public static class PathToJsonCommandConverter {

		public static JsonArray convertPath(List<PathPoint> path) {
			JsonArray jsonCommands = new JsonArray();

			for (PathPoint point : path) {
				JsonObject command = new JsonObject();
				command.addProperty("idCommand", "goAhead");
				command.addProperty("x", point.getX());
				command.addProperty("y", point.getY());
				command.addProperty("z", point.getZ());
				jsonCommands.add(command);
			}

			return jsonCommands;
		}

	}
}
