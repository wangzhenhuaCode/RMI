package example;

import rmi.util.Remote;
import rmi.util.RemoteException;

public class Work implements Remote, IWork {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String workName;
	
	public Work(String workName) {
		super();
		this.workName = workName;
	}

	@Override
	public String getWorkName() throws RemoteException{
		
		return workName;
	}

}
