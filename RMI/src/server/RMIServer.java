package server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import util.DataTable;
import util.Message;
import util.ServerSocketConection;
import util.StubInfo;
import util.ThreadPool;

/**
 * 
 *  Class for RMI server.
 */
public class RMIServer {
	private String registeryHost;
	private Integer registeryPort;
	private Integer ServerPort;
	private Integer threadPoolSize;
	static RMIServer instance;
	private String localHost;
	/**
	 * @param registeryHost the host name for registry
	 * @param registeryPort the port number for registry
	 * @param serverPort the local server port
	 * @param poolsize the size of thread pool in handling client request
	 * @return
	 * @throws IOException
	 */
	public static RMIServer createServer(String registeryHost,Integer registeryPort,Integer serverPort,Integer poolsize) throws IOException{
		if(instance==null){
			instance=new RMIServer();
			instance.registeryHost=registeryHost;
			instance.registeryPort=registeryPort;
			instance.ServerPort=serverPort;
			instance.threadPoolSize=poolsize;
			instance.initialize();
		}
		return instance;
	}
	/**
	 * Initialize RMI server with thread pool size 5
	 * @param registeryHost the host name for registry
	 * @param registeryPort the port number for registry 
	 * @param serverPort the local server port
	 * @return
	 * @throws IOException
	 */
	public static RMIServer createServer(String registeryHost,Integer registeryPort,Integer serverPort) throws IOException{
		if(instance==null){
			instance=new RMIServer();
			instance.registeryHost=registeryHost;
			instance.registeryPort=registeryPort;
			instance.ServerPort=serverPort;
			instance.threadPoolSize=5;
			instance.initialize();
		}
		return instance;
	}
	private void initialize() throws IOException{
		DataTable.getInstance();
		ServerSocketConection.createServerSocketConnection(ServerPort);
		ThreadPool.getInstance(threadPoolSize, ServerMessageProcessor.class);
		InetAddress addr = InetAddress.getLocalHost();
		localHost = addr.getHostAddress();	
	}
	/**
	 * export stub information object which will be bind to registry.
	 * @param obj the implementation object
	 * @return stub information object
	 */
	public static Object exportStub(Object obj){
		Class<?> interfaces[]=obj.getClass().getInterfaces();
		String reference=obj.getClass().toString()+"_"+(new Date()).getTime();
		DataTable.getInstance().put(reference, obj);
		String[] interfaceName=new String[interfaces.length];
		for(int i=0;i<interfaces.length;i++){
			interfaceName[i]=interfaces[i].getName();
		}
		StubInfo stub=new StubInfo(instance.localHost,instance.ServerPort,reference,interfaceName);
		return stub;
	}
	/**
	 * bind the stub information object to the remote registry.
	 * @param name the name of the stub on the registry
	 * @param obj the stub informaton object
	 * @throws IOException
	 */
	public static void bindToRegistery(String name,Serializable obj) throws IOException{
		Socket socket=new Socket();
		socket.connect(new InetSocketAddress(instance.registeryHost,instance.registeryPort));
		Message message=new Message();
		message.setMessageType(Message.BIND_TO_REGISTERY);
		message.setValue(obj);
		message.setReference(name);
		ObjectOutputStream out =new ObjectOutputStream(socket.getOutputStream());
		out.writeObject(message);
		out.close();
		socket.close();
	}
	
}
