package example;

import rmi.util.RemoteException;

public interface IPeopleFactory {
	public IPeople getPeople(String name) throws RemoteException;
}
