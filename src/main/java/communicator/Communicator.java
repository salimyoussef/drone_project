package communicator;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import maps.MapIF;
import path.*;
import remotes.TracerIF;
import tracer.ConsumerTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

public class Communicator{


        String drone ;
        String name;
        MapIF map;
        PathPoint p;
        private final ConsumerConnector consumer;

        public Communicator(String a_groupId, String a_topic,String a_zookeeper) {
            this.drone = a_topic;
            this.name= a_groupId;
            consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                    createConsumerConfig(a_zookeeper, a_groupId));
        }

        public PathPoint getPath(){
            return p;

        }
        public void setPath(PathPoint p){
            this.p = p;
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
            ExecutorService executor = Executors.newFixedThreadPool(a_numThreads);

            // now create an object to consume the messages
            //
            int threadNumber = 0;
            for (final KafkaStream stream : streams) {
                executor.submit(new ThreadForCommunicator(stream, threadNumber,this));
                threadNumber++;
            }
        }
        
        public void sendPosition(PathPoint p){
        	try {
				Client client = Client.create();
				WebResource webResource = client.resource("http://localhost:8080/FAAserver/service/drone/position");
				String input = "{\"x\":\"" + p.getX() + "\",\"y\":\"" + p.getY() + "\"}";
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