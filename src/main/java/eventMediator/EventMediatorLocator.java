package eventMediator;

import remotes.MediatorIF;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/**
 * Created by mohannad on 02/01/16.
 */
public class EventMediatorLocator {
	public static MediatorIF mediator() throws RemoteException, NotBoundException, MalformedURLException {
		return (MediatorIF) Naming.lookup("eventmediator0");
	}
}
