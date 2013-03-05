package example;

import rmi.util.RemoteException;

public class PeopleFactory implements IPeopleFactory {

	@Override
	public IPeople getPeople(String name) throws RemoteException {
		// TODO Auto-generated method stub
		return new People(name);
	}

}
