package example;

import java.io.IOException;

import rmi.client.Registry;
import rmi.util.RemoteException;

public class ClientTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			Registry.getRegistery("127.0.0.1", 1928, 1930);
			IConvertNumber stub=(IConvertNumber)Registry.lookUp("num");
			System.out.println(stub.getNum("1234"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
