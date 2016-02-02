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

public class CommunicatorReceiver{


        String drone ;
        String name;
        MapIF map;
        Path p;
        private final ConsumerConnector consumer;

        public CommunicatorReceiver(String a_groupId, String a_topic,String a_zookeeper) {
            this.drone = a_topic;
            this.name= a_groupId;
		/*this.mediator = EventMediatorLocator.mediator();
		this.mediator.registerTracer(drone,this);*/
            consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                    createConsumerConfig(a_zookeeper, a_groupId));

            System.out.println(this.name +" is ready to trace "+drone);
        }

        public Path getPath(){
            return p;

        }
        public void setPath(Path p){
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

}