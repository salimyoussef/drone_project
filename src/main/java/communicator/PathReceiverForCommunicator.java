package communicator;

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

			// method for sending the json(postion) with interval (not every
			// position)
			receiver.sendPath(msg);

			// "("+json.getString("x")+","+json.getString("y")+","+json.getString("z")+")
			// ***");
		}
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}
}
