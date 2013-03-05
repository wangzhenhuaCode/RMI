package example;

import java.io.IOException;
import java.io.Serializable;

import rmi.server.RMIServer;


public class ServerTestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			RMIServer.createServer("127.0.0.1", 1928, 1929);
		} catch (IOException e) {

			e.printStackTrace();
		}
		PeopleFactory people = new PeopleFactory();
		Serializable stub1 = (Serializable) RMIServer.exportStub(people);
		WorkFactory work = new WorkFactory();
		Serializable stub2 = (Serializable) RMIServer.exportStub(work);
		try {
			RMIServer.bindToRegistery("people", stub1);
			RMIServer.bindToRegistery("work", stub2);
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

}
