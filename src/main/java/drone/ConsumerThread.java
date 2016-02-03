package drone;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.utils.Json;
import org.json.JSONArray;
import remotes.DroneRemoteIF;

import java.util.ArrayList;

/**
 * Created by sy306571 on 29/01/16.
 */
public class ConsumerThread implements Runnable {
    private KafkaStream<byte[],byte[]> m_stream;
    private int m_threadNumber;
    private Drone drone;

    public ConsumerThread(KafkaStream<byte[],byte[]> a_stream, int a_threadNumber, Drone drone) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
        this.drone = drone;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();

        while (it.hasNext()){
            String msg = new String(it.next().message());
            if(msg.equals("go!")){
                drone.go();
            }
            else{
                JsonParser j = new JsonParser();
                JsonArray arrayCommands = (JsonArray) j.parse(msg);
                //String[] arrayCommands = msg.substring(1,msg.length()-1).split(", ");
                drone.loadPath(arrayCommands);
            }

            //System.out.println("*** Drone moved to these coordinates "+json.getDouble("x"));
            // "("+json.getString("x")+","+json.getString("y")+","+json.getString("z")+") ***");
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}
