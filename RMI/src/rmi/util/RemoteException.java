package rmi.util;

/**
 *  Exception for in server method invocation
 *
 */
public class RemoteException extends Exception {

	private static final long serialVersionUID = 1L;

	public RemoteException(String arg0) {
		super(arg0);
		
	}

	public RemoteException(Throwable arg0) {
		super(arg0);
		
	}

}
