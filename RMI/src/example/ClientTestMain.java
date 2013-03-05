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
			IPeopleFactory peopleFactory=(IPeopleFactory)Registry.lookUp("people");
			IWorkFactory workFactory=(IWorkFactory)Registry.lookUp("work");
			IPeople people=peopleFactory.getPeople("Jack");
			IWork work=workFactory.getWork("Distributed System homework");
			System.out.println(people.work(work));
			System.out.println("Finished work:");
			System.out.println(people.getFinishedWork().getWorkName());
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
		System.exit(0);
		
	}

}
