package eventMediator;

/**
 * Created by mohannad on 02/01/16.
 */
/*
 * public class EventMediator extends UnicastRemoteObject implements MediatorIF
 * {
 * 
 * Hashtable<String,DroneRemoteIF> drones = new Hashtable<>(); Hashtable<String,
 * TracerIF> tracers = new Hashtable<>(); Hashtable<String,String> bindings =
 * new Hashtable<>(); Set<String> available = new HashSet<>();
 * 
 * 
 * protected EventMediator() throws RemoteException { }
 * 
 * @Override public void registerDrone(DroneRemoteIF drone) throws
 * RemoteException { this.drones.put(drone.getName(), drone);
 * this.available.add(drone.getName()); }
 * 
 * @Override public ArrayList<String> listDrones() throws RemoteException {
 * Enumeration<String> keys=this.drones.keys(); ArrayList<String> drones = new
 * ArrayList<>(); while(keys.hasMoreElements()){ String k = keys.nextElement();
 * drones.add(k); } return drones; }
 * 
 * @Override public synchronized void registerTracer(String drone, TracerIF
 * tracer) throws RemoteException { synchronized(this.tracers) {
 * this.tracers.put(tracer.getName(), tracer);
 * this.bindings.put(tracer.getName(), drone); this.bindings.put(drone,
 * tracer.getName()); } }
 * 
 * @Override public void notifyLocation(String drone, PathPoint pathPoint)
 * throws RemoteException { TracerIF tracer = tracers.get(bindings.get(drone));
 * //tracer.notify(pathPoint.getX(),pathPoint.getY(),pathPoint.getZ());
 * tracer.notify(pathPoint); }
 * 
 * @Override public void notifyArrivale(String drone) throws RemoteException {
 * TracerIF tracer = tracers.get(bindings.get(drone)); tracer.done(); }
 * 
 * @Override public void startDelivary(String dr) throws RemoteException {
 * DroneRemoteIF drone = this.drones.get(dr); drone.go(); }
 * 
 * @Override public void loadPath(String dr, ArrayList<PathPoint> path) throws
 * RemoteException { DroneRemoteIF drone = this.drones.get(dr);
 * drone.loadPath(path); }
 * 
 * @Override public void notifyStatus(String drone,Status droneState) throws
 * RemoteException { TracerIF tracer = tracers.get(bindings.get(drone)); }
 * 
 * @Override public String allocateDrone() throws RemoteException { synchronized
 * (this.available) { if (this.available.iterator().hasNext()) { String s =
 * this.available.iterator().next(); this.available.remove(s); return s; } }
 * return null; }
 * 
 * 
 * public static void main(String args[]) throws MalformedURLException,
 * RemoteException { String eventMediators [] = Naming.list("//localhost/"); int
 * len =0; if (eventMediators!=null) len = eventMediators.length; String name =
 * "eventmediator"+len; Naming.rebind(name,new EventMediator());
 * 
 * System.out.println(name+" is up and running"); }
 * 
 * }
 */
