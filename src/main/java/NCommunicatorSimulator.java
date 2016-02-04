import java.net.MalformedURLException;
import java.util.Random;

import communicator.Communicator;

import utils.MyConstants;

public class NCommunicatorSimulator {
	public static void main(String args[]) {
		int n = MyConstants.NUMBER_OF_DRONES;
		CommunicatorTask[] t = new CommunicatorTask[n];
		for (int i = 0; i < n; i++) {
			t[i] = new NCommunicatorSimulator.CommunicatorTask(i);
			Thread tr = new Thread(t[i]);
			tr.start();
		}
	}

	static class CommunicatorTask implements Runnable {

		int id;

		public CommunicatorTask(int id) {
			this.id = id;
		}

        @Override
        public void run() {
        	Communicator c = new Communicator("com" + id, "drone" + id, "localhost:" + MyConstants.KAFKA_ZK_PORT);
        	c.run(2);
        	//c.run2(1);
		}
	}
}
