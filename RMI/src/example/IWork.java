package example;

import rmi.util.RemoteException;

public interface IWork {
	public String getWorkName() throws RemoteException;
}
