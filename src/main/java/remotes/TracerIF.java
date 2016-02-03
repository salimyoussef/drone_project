package remotes;

import maps.MapIF;

import java.rmi.RemoteException;

/**
 * Created by mohannad on 02/01/16.
 */
public interface TracerIF extends Notifiable {
	public String getName() throws RemoteException;

	public void setMap(MapIF map) throws RemoteException;
}
