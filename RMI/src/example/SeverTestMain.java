package example;

import java.io.IOException;

import rmi.server.RMIServer;
import rmi.util.Remote;




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
		ConvertNumerberImp imp=new ConvertNumerberImp();
		Remote stub=(Remote) RMIServer.exportStub(imp);
		try {
			RMIServer.bindToRegistery("num", stub);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
	}

}
