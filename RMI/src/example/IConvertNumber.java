package example;

import rmi.util.Remote;
import rmi.util.RemoteException;

public interface IConvertNumber extends Remote {
	public Integer getNum(String str) throws RemoteException;
}
