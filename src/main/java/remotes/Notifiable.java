package remotes;

import path.PathPoint;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Notifiable extends Remote {
	public void notify(PathPoint pos) throws RemoteException;

	public void done() throws RemoteException;
}
