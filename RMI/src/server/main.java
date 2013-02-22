package server;

import java.io.IOException;


import client.ITest;

public class main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RMIServer server = null;
		try {
			server = RMIServer.createServer("127.0.0.1", 1928, 1929);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TestImpl impl=new TestImpl();
		ITest stub =(ITest) server.exportStub(impl);
		try {
			server.bindToRegistery("test", stub);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
