package example;

import rmi.util.RemoteException;

public class WorkFactory implements IWorkFactory {

	@Override
	public IWork getWork(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return new Work(name);
	}

}
