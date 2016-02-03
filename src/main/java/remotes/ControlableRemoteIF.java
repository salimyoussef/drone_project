package remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ControlableRemoteIF extends Remote {
	public void go() throws RemoteException;
}
