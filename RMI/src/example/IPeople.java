package example;

import rmi.util.RemoteException;

public interface IPeople {
	public String work(IWork work)throws RemoteException;
	public IWork getFinishedWork() throws RemoteException;
}
