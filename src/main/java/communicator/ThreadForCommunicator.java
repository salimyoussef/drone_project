package communicator;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import path.PathPoint;

import org.json.JSONObject;

/**
 * Created by farouk khouly on 1/29/2016.
 */
public class ThreadForCommunicator implements Runnable {

	private KafkaStream<byte[], byte[]> m_stream;
	private int m_threadNumber;
	private Communicator receiver;

	public ThreadForCommunicator(KafkaStream<byte[], byte[]> a_stream, int a_threadNumber, Communicator receiver) {

		m_stream = a_stream;
		m_threadNumber = a_threadNumber;
		this.receiver = receiver;

	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		int compt = 0;

		while (it.hasNext()) {
			compt++;
			String msg = new String(it.next().message());

			// method for sending the json(postion) with interval (not every
			// position)
			if (compt % 10 == 0) {
				JSONObject json = new JSONObject(msg);
				PathPoint p = new PathPoint(json.getDouble("x"), json.getDouble("y"), json.getDouble("z"));
				receiver.sendPosition(p);
			}

			// "("+json.getString("x")+","+json.getString("y")+","+json.getString("z")+")
			// ***");
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}
