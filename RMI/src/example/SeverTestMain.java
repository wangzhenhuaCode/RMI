package example;

import java.io.IOException;
import java.io.Serializable;

import rmi.server.RMIServer;


public class SeverTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			RMIServer.createServer("127.0.0.1", 1928, 1929);
		} catch (IOException e) {

			e.printStackTrace();
		}
		ConvertNumerberImp imp = new ConvertNumerberImp();
		Serializable stub = (Serializable) RMIServer.exportStub(imp);
		try {
			RMIServer.bindToRegistery("num", stub);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
