import endPoints.AdressEndPoints;
import maps.GoogleMap;
import maps.MapIF;
import path.Path;
import pathFinder.GooglePathFinder;
import pathFinder.PathPlannerStrategy;
import pathToNavCommands.CommandsProvider;
import tracer.Tracer;
import utils.MyConstants;
import java.net.MalformedURLException;
import java.util.Random;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import communicator.Communicator;

public class NTracerSimulation {
	public static void main(String args[]) {
		int n = MyConstants.NUMBER_OF_DRONES;
		for (int i = 0; i < n; i++) {
			Thread t = new Thread(new NTracerSimulation.TracerTask(i));
			t.start();
		}
	}

	static class TracerTask implements Runnable {

		int id;

		public TracerTask(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			// int nb_topics_per_zookeeper =
			// MyConstants.NUMBER_OF_DRONES/MyConstants.NUMBER_OF_ZOOKEEPER;
			// Random genetator = new Random();
			// String drone = EventMediatorLocator.mediator().allocateDrone();
			String dest[] = { "Dijon", "Rennes", "Tours", "Caen", "Nantes", "Angers" };
			Random random = new Random();
			int idx1 = random.nextInt(7);
			String destination = dest[idx1];
			// String destination = "Paris";
			/*
			 * int idx2 = random.nextInt(7); if (idx2 == idx1) { idx2 = (idx2 +
			 * 1) % dest.length; }
			 */

			// String start = dest[idx2];
			String start = "Lille";
			PathPlannerStrategy pathPlanner = new GooglePathFinder();
			System.out.println(start + ":" + destination);
			Path p = pathPlanner.findPath(new AdressEndPoints(start, destination));

			/*
			 * ArrayList<PathPoint> path = new ArrayList<>(); for(int i=0;
			 * i<30;i++){ path.add(new PathPoint(10+i,20,12)); }
			 */

			Communicator c = new Communicator("com" + id, "drone" + id, "localhost:" + MyConstants.KAFKA_ZK_PORT);
			c.run(1, false);

			CommandsProvider provider = new CommandsProvider("drone" + id);
			provider.setPath(p.getPathPoints());
			provider.sendCommands();

			Tracer tracer = new Tracer("tracer" + id, "drone" + id, "localhost:" + MyConstants.KAFKA_ZK_PORT); // +
																												// (id/nb_topics_per_zookeeper)));
			MapIF map = null;
			try {
				map = new GoogleMap(p);
				tracer.setMap(map);
				map.paintMap(p);
				tracer.run(1);
				try {
					Client client = Client.create();
					WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/path");
					String input = "{";
					for (int i = 0; i < p.getLength() / 10; i = i + 10) {
						input = input + "{x:" + p.getPathPoint(i).getX() + " y:" + p.getPathPoint(i).getY() + "} ";
					}
					input = input + "}";
					ClientResponse response = webResource.type("application/json").post(ClientResponse.class, input);
					if (response.getStatus() != 200) {
						throw new RuntimeException("Failed : HTTP error code : " + response.getStatus());
					}
					// System.out.println("Output from Server .... \n");
					// String output = response.getEntity(String.class);
					// System.out.println(output);
				} catch (Exception e) {
					e.printStackTrace();
				}
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}

		}

	}
}