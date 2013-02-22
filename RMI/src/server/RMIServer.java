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
import util.ThreadPool;

public class RMIServer {
	private String registeryHost;
	private Integer registeryPort;
	private Integer ServerPort;
	private Integer threadPoolSize;
	static RMIServer instance;
	private String localHost;
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
	public static Object exportStub(Object obj){
		Class<?> interfaces[]=obj.getClass().getInterfaces();
		String reference=obj.getClass().toString()+"_"+(new Date()).getTime();
		DataTable.getInstance().put(reference, obj);
		ProxyHandler px=new ProxyHandler(reference,instance.localHost,instance.ServerPort);
		return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, px);
	}
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
