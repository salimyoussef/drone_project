package tracer;


import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import eventMediatorLocator.EventMediatorLocator;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import maps.MapIF;
import path.PathPoint;
import remotes.MediatorIF;
import remotes.Notifiable;
import remotes.TracableRemoteIF;
import remotes.TracerIF;

public class Tracer implements TracerIF {

	MediatorIF mediator;
	String drone ;
	String name;
    MapIF map;
	private final ConsumerConnector consumer;
	private ExecutorService executor;

	public Tracer(String a_groupId, String a_topic,String a_zookeeper) {
		this.drone = a_topic;
		this.name= a_groupId;
		/*this.mediator = EventMediatorLocator.mediator();
		this.mediator.registerTracer(drone,this);*/
		consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
				createConsumerConfig(a_zookeeper, a_groupId));

		System.out.println(this.name +" is ready to trace "+drone);
	}

	private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
		Properties props = new Properties();
		props.put("zookeeper.connect", a_zookeeper);
		props.put("group.id", a_groupId);
		props.put("zookeeper.session.timeout.ms", "400");
		props.put("zookeeper.sync.time.ms", "200");
		props.put("auto.commit.interval.ms", "1000");

		return new ConsumerConfig(props);
	}

	public void run(int a_numThreads) {
		Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
		topicCountMap.put(drone, a_numThreads);
		Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(drone);

		// now launch all the threads
		//
		executor = Executors.newFixedThreadPool(a_numThreads);

		// now create an object to consume the messages
		//
		int threadNumber = 0;
		for (final KafkaStream stream : streams) {
			executor.submit(new ConsumerTest(stream, threadNumber));
			threadNumber++;
		}
	}

	@Override
	public void notify(PathPoint p) {
		System.out.println(p);
		if(this.map!=null) this.map.setPossition(p);
	}

	
	@Override
	public void done() throws RemoteException {
		System.out.println("Drone has reached its destination .....");
	}


	@Override
	public String getName() {
		return this.name;
	}

    @Override
    public void setMap(MapIF map) {
        this.map = map;
    }

	public static void main(String args[]) {
		Tracer tracer = new Tracer("id3","drone34","localhost:2181");
		tracer.run(1);
	}
}
