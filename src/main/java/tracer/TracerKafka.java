package tracer;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import path.PathPoint;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Created by farouk khouly on 1/24/2016.
 */
public class TracerKafka {

    private final ConsumerConnector consumer;
    private final String topic ;
    private ExecutorService executor;

    public TracerKafka(String a_zookeeper, String a_groupId, String a_topic){
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createTracerConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;

    }
    public void run(int a_numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);



        // now launch all the threads
        //
        executor = Executors.newFixedThreadPool(a_numThreads);

        // now create an object to consume the messages
        Tracer tracer = new Tracer();

        //
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            ConsumerIterator<byte[], byte[]> iterator = stream.iterator();
            String position = new String(iterator.next().message(), StandardCharsets.UTF_8);
            //just this error cause i need to know how the message will be published in order to serialized it in order to put it the object point
            //also i need to test it to know if it works or not
            tracer.notify((PathPoint)position);

            executor.submit(new Tracer(stream, threadNumber));
            threadNumber++;
        }
    }

    private static ConsumerConfig createTracerConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");
        return new ConsumerConfig(props);
    }
    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }
    public static void main(String[] args) {
        String zooKeeper = args[0];
        String groupId = args[1];
        String topic = args[2];
        int threads = Integer.parseInt(args[3]);

        TracerKafka tracerKafka = new TracerKafka(zooKeeper, groupId, topic);
        tracerKafka.run(threads);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        tracerKafka.shutdown();
    }
}