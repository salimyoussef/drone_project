package tracer;

import com.google.gson.Gson;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.json.JSONObject;
import path.PathPoint;

public class ConsumerTest implements Runnable {
    private KafkaStream<byte[],byte[]> m_stream;
    private int m_threadNumber;
    private String idDrone;
    private Tracer tracer;

    public ConsumerTest(KafkaStream<byte[],byte[]> a_stream, int a_threadNumber, String idDrone, Tracer tracer) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
        this.idDrone = idDrone;
        this.tracer = tracer;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        while (it.hasNext()){
            String msg = new String(it.next().message());
            JSONObject json = new JSONObject(msg);
           // System.out.println("*** "+idDrone+" moved to these coordinates: ("+json.getDouble("x")+
           //         ","+ json.getDouble("y")+","+json.getDouble("z")+") ***");
            PathPoint p = new PathPoint(json.getDouble("x"),json.getDouble("y"),json.getDouble("z"));
            tracer.notify(p);
        }
        System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}