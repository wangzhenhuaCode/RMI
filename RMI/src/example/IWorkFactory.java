package example;

import rmi.util.RemoteException;

public interface IWorkFactory {
	public IWork getWork(String name) throws RemoteException;
}
