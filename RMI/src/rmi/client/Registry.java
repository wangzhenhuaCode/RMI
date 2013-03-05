package rmi.client;

import java.io.IOException;
import java.lang.reflect.Proxy;

import rmi.util.Message;
import rmi.util.RemoteException;
import rmi.util.StubInfo;





/**
 * 
 *  Class for registery reference. Use getRegisery function before look up
 */
public class Registry {
	static Registry instance=null;
	private String remotehostname;
	private Integer remoteport;
	private Integer localport;
	/**
	 * Create registery reference and create a socket listening on given port.
	 * @param remotehostname Registery host name
	 * @param remoteport Registery port number
	 * @param localport local port number
	 * @return
	 * @throws IOException 
	 */
	public static Registry getRegistery(String remotehostname,Integer remoteport,Integer localport) throws IOException{
		
		if(instance==null){
			instance=new Registry();
			instance.remoteport=remoteport;
			instance.remotehostname=remotehostname;
			instance.localport=localport;
			SocketConnection.createSocket(localport);
		}
		return instance;
	}

	/**
	 * Look up the object on the registery
	 * @param name the name of the return object
	 * @return stub for remote object
	 * @throws RemoteException
	 * @throws ClassNotFoundException 
	 * @throws IOException 
	 */
	public static Object lookUp(String name) throws RemoteException, ClassNotFoundException, IOException{
		if(instance==null)throw new IOException("Null registery instance");
		Message m=new Message();
		m.setReference(name);
		m.setMessageType(Message.LOOK_UP);
		Message newMessage=SocketConnection.communicate(m, instance.remotehostname, instance.remoteport);
		String errorMessage=newMessage.getErrorMessage();
		if(errorMessage!=null&&!errorMessage.equals("")){
			throw new RemoteException(errorMessage);
		}
		StubInfo stubinfor=(StubInfo) newMessage.getValue();
		ProxyHandler px=new ProxyHandler(stubinfor.getReference(),stubinfor.getServerHost(),stubinfor.getPort());
		Class<?>[] interfaces=new Class<?>[stubinfor.getInterfaces().length];
		for(int i=0;i<stubinfor.getInterfaces().length;i++){
			interfaces[i]=Class.forName(stubinfor.getInterfaces()[i]);
		}
		return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),interfaces , px);
	}
}
