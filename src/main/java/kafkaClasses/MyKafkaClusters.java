package kafkaClasses;

import kafka.admin.AdminUtils;
import kafka.utils.ZKStringSerializer$;
import org.I0Itec.zkclient.ZkClient;
import utils.MyConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by sy306571 on 28/01/16.
 */
public class MyKafkaClusters {
	public static void main(String[] args) {

		EmbeddedZookeeper embeddedZookeeper = new EmbeddedZookeeper(MyConstants.KAFKA_ZK_PORT);

		List<Integer> kafkaPorts = new ArrayList<Integer>();
		// -1 for any available port
		for (int i = 0; i < MyConstants.NUMBER_OF_BROKERS; i++) {
			kafkaPorts.add(MyConstants.INITIAL_BROKER_PORT + i);
		}

		EmbeddedKafkaCluster embeddedKafkaCluster = new EmbeddedKafkaCluster(embeddedZookeeper.getConnection(),
				new Properties(), kafkaPorts);
		try {
			embeddedZookeeper.startup();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("### Embedded Zookeeper connection: " + embeddedZookeeper.getConnection());
		embeddedKafkaCluster.startup();
		System.out.println("### Embedded Kafka cluster broker list: " + embeddedKafkaCluster.getBrokerList());

		// embeddedKafkaCluster.shutdown();
		// embeddedZookeeper.shutdown();

		// Create a ZooKeeper client
		int sessionTimeoutMs = 10000;
		int connectionTimeoutMs = 10000;
		ZkClient zkClient = new ZkClient("localhost:" + MyConstants.KAFKA_ZK_PORT, sessionTimeoutMs,
				connectionTimeoutMs, ZKStringSerializer$.MODULE$);

		// Create the topics for the communication between drones and tracers
		int numPartitions = 1;
		int replicationFactor = 1;
		Properties topicConfig = new Properties();
		for (int i = 0; i < MyConstants.NUMBER_OF_DRONES; i++) {

			AdminUtils.createTopic(zkClient, "drone" + i + "-in", numPartitions, replicationFactor, topicConfig);
			AdminUtils.createTopic(zkClient, "drone" + i + "-out", numPartitions, replicationFactor, topicConfig);
			System.out.println("drone" + i + "'s topic created");
		}

		/*
		 * int nb_brokers =
		 * MyConstants.NUMBER_OF_BROKERS/MyConstants.NUMBER_OF_ZOOKEEPER; int
		 * nb_topics =
		 * MyConstants.NUMBER_OF_DRONES/MyConstants.NUMBER_OF_ZOOKEEPER;
		 */

		/*
		 * for(int i=0; i<MyConstants.NUMBER_OF_BROKERS; i+=nb_brokers){ for(int
		 * j=0; j<MyConstants.NUMBER_OF_DRONES; j++) { if(j%nb_brokers==0){ int
		 * zookeeper = MyConstants.KAFKA_ZK_PORT+i; int begin_broker =
		 * MyConstants.INITIAL_BROKER_PORT + j; int begin_topic = 0; Thread t =
		 * new Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper,nb_topics,i,nb_brokers,j));
		 * t.start(); } } }
		 */
		/*
		 * int zookeeper = MyConstants.KAFKA_ZK_PORT; int broker_begin =
		 * MyConstants.INITIAL_BROKER_PORT;
		 * 
		 * Thread t1 = new Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper,nb_topics,0,nb_brokers,
		 * broker_begin)); t1.start();
		 */
		/*
		 * Thread t2 = new Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper+1,nb_topics,nb_topics,
		 * nb_brokers,broker_begin+nb_brokers)); t2.start(); Thread t3 = new
		 * Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper+2,nb_topics,2*nb_topics,
		 * nb_brokers,broker_begin+2*nb_brokers)); t3.start(); Thread t4 = new
		 * Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper+3,nb_topics,3*nb_topics,
		 * nb_brokers,broker_begin+3*nb_brokers)); t4.start(); Thread t5 = new
		 * Thread(new
		 * MyKafkaClusters.AKafkaCluster(zookeeper+4,nb_topics,4*nb_topics,
		 * nb_brokers,broker_begin+4*nb_brokers)); t5.start();
		 */

		/*
		*/
	}

	static class AKafkaCluster implements Runnable {

		public int getIndex_zookeeper() {
			return index_zookeeper;
		}

		public void setIndex_zookeeper(int index_zookeeper) {
			this.index_zookeeper = index_zookeeper;
		}

		public int getNumber_topics() {
			return number_topics;
		}

		public void setNumber_topics(int number_topics) {
			this.number_topics = number_topics;
		}

		public int getNumber_brokers() {
			return number_brokers;
		}

		public void setNumber_brokers(int number_brokers) {
			this.number_brokers = number_brokers;
		}

		public int getBegin_topic_index() {
			return begin_topic_index;
		}

		public void setBegin_topic_index(int begin_topic_index) {
			this.begin_topic_index = begin_topic_index;
		}

		public int getBegin_broker_index() {
			return begin_broker_index;
		}

		public void setBegin_broker_index(int begin_broker_index) {
			this.begin_broker_index = begin_broker_index;
		}

		private int index_zookeeper;
		private int number_topics;
		private int number_brokers;
		private int begin_topic_index;
		private int begin_broker_index;

		public AKafkaCluster(int index_zookeeper, int number_topics, int begin_topic_index, int number_brokers,
				int begin_broker_index) {
			this.index_zookeeper = index_zookeeper;
			this.number_topics = number_topics;
			this.number_brokers = number_brokers;
			this.begin_topic_index = begin_topic_index;
			this.begin_broker_index = begin_broker_index;
		}

		@Override
		public void run() {
		}
	}
}
