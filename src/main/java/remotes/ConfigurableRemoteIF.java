package remotes;

import java.rmi.Remote;
import java.rmi.RemoteException;
import com.google.gson.JsonArray;

public interface ConfigurableRemoteIF extends Remote {
	public void loadPath(JsonArray commands) throws RemoteException;
}
