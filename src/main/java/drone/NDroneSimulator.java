package drone;

import utils.MyConstants;

import java.util.Random;

/**
 * Created by mohannad on 07/01/16.
 */
public class NDroneSimulator {

	public static void main(String args[]) {
		int n = MyConstants.NUMBER_OF_DRONES;
		for (int i = 0; i < n; i++) {
			Thread t = new Thread(new NDroneSimulator.DroneTask(i));
			t.start();
		}
	}

	static class DroneTask implements Runnable {
		int id;

		public DroneTask(int id) {
			this.id = id;
		}

		@Override
		public void run() {
			Random genetator = new Random();
			// try {
			// ArrayList<String> drones =
			// EventMediatorLocator.mediator().listDrones();
			// int len =EventMediatorLocator.mediator().size();
			// if(drones!=null) len=drones.size();
			// System.out.println("");
			int nb_topics_per_zookeeper = MyConstants.NUMBER_OF_DRONES / MyConstants.NUMBER_OF_ZOOKEEPER;
			Drone drone = new Drone("drone" + id,
					"localhost:" + (MyConstants.KAFKA_ZK_PORT + (id / nb_topics_per_zookeeper)));
			drone.run(1);
			System.out.println(drone.getName() + " is up and running");
			try {
				Thread.sleep(genetator.nextInt(1000));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			/*
			 * } catch (Exception e) { System.err.println("Client exception: " +
			 * e.toString()); e.printStackTrace(); }
			 */
		}

	}
}
