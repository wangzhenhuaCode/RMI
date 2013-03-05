package example;

import rmi.util.Remote;
import rmi.util.RemoteException;

public class People implements Remote, IPeople {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private IWork finishedWork;

	public People(String name) {
		super();
		this.name = name;
	}

	@Override
	public String work(IWork work) throws RemoteException{
	
		String result=name+" is working on "+work.getWorkName();
		finishedWork=work;
		return result;
	}

	public IWork getFinishedWork() throws RemoteException {
		return finishedWork;
	}
	
	
}
