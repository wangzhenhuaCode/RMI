package rmi.naming;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import rmi.util.DataTable;
import rmi.util.ServerSocketConection;
import rmi.util.ThreadPool;


/**
 *
 *  Class for initialize registry
 *  The first argument is the port number for registry
 */
public class RMIRegistry {

	public static void main(String[] args) {
		if(args.length!=1){
			System.out.println("Please provide RMI registery port number");
			return;
		}
		Integer port=null;
		try{
			port=Integer.valueOf(args[0]);
		}catch(Exception e){
			System.out.println("Invalid arguments");
			return;
		}
		DataTable.getInstance();
		try {
			ServerSocketConection.createServerSocketConnection(port);
		} catch (IOException e1) {
			System.out.println("Port number repeated!");
			return;
		}
		ThreadPool.getInstance(5, RegistryMessageProcessor.class);
		InetAddress addr = null;
		try {
			addr = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		String local = addr.getHostAddress();
		System.out.println("Registery create sucessfully!");
		System.out.println("Address:"+local+":"+port);
	}

}
