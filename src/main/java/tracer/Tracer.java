package tracer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import maps.MapIF;
import path.PathPoint;
import remotes.TracerIF;

public class Tracer  implements TracerIF, Runnable {

	//MediatorIF mediator;
	String drone ;
	String name;
    MapIF map;
	private KafkaStream m_stream;
	private int m_threadNumber;

	//NTracerSimulator nts = new NTracerSimulator();
	public Tracer(){}

	public Tracer(String drone,String name) throws Exception{
		super();
		this.drone = drone;
		this.name=name;

		//this.mediator = EventMediatorLocator.mediator();
		//this.mediator.registerTracer(drone,this);
		System.out.println(this.name +" is ready to trace "+drone);
	}

	public Tracer(KafkaStream a_stream, int a_threadNumber) {
		m_threadNumber = a_threadNumber;
		m_stream = a_stream;
	}

	public void run() {
		ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
		while (it.hasNext())
			System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
		System.out.println("Shutting down Thread: " + m_threadNumber);
	}

	@Override
	public void notify(PathPoint p)  {
		System.out.println(p);
		if(this.map!=null) this.map.setPossition(p);
	}
	
	@Override
	public void done() {
		System.out.println("Drone has reached its destination .....");
		
	}


	@Override
	public String getName()  {
		return this.name;
	}

    @Override
    public void setMap(MapIF map)  {
        this.map = map;
    }

}
