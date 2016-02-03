package remotes;

import java.rmi.RemoteException;

/**
 * Created by mohannad on 02/01/16.
 */
public interface DroneRemoteIF extends ConfigurableRemoteIF, ControlableRemoteIF {
	public String getName() throws RemoteException;
}
