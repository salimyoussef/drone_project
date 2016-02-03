package remotes;

import path.PathPoint;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Created by mohannad on 02/01/16.
 */
public interface MediatorIF extends Remote {
	public void registerDrone(DroneRemoteIF drone) throws RemoteException;

	public ArrayList<String> listDrones() throws RemoteException;

	public void registerTracer(String drone, TracerIF tracer) throws RemoteException;

	public void notifyLocation(String drone, PathPoint pathPoint) throws RemoteException;

	public void notifyArrivale(String drone) throws RemoteException;

	public void startDelivary(String drone) throws RemoteException;

	public void loadPath(String drone, ArrayList<PathPoint> path) throws RemoteException;

	public void notifyStatus(String drone, Status droneState) throws RemoteException;

	public String allocateDrone() throws RemoteException;
}
