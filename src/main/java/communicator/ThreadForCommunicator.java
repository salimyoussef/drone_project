package communicator;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.json.JSONObject;

/**
 * Created by farouk khouly on 1/29/2016.
 */
public class ThreadForCommunicator implements Runnable {

    private KafkaStream<byte[],byte[]> m_stream;
    private int m_threadNumber;
    private CommunicatorReceiver receiver;



    public ThreadForCommunicator(KafkaStream<byte[],byte[]> a_stream, int a_threadNumber, CommunicatorReceiver receiver){

        m_stream = a_stream;
        m_threadNumber = a_threadNumber;
        this.receiver = receiver;

    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();


        while (it.hasNext()){
            String msg = new String(it.next().message());
            JSONObject json = new JSONObject(msg);
            //method for sending the json(postion) with interval (not every position)
            receiver.sendForCommunicatorSender(json);
            System.out.println("*** Drone moved to these coordinates "+json.getDouble("x"));
            // "("+json.getString("x")+","+json.getString("y")+","+json.getString("z")+") ***");
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}
