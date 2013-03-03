package client;

import java.lang.reflect.Proxy;


import util.Message;
import util.Remote;
import util.StubInfo;



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
	 * @throws Exception
	 */
	public static Registry getRegistery(String remotehostname,Integer remoteport,Integer localport) throws Exception{
		
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
	 * @return
	 * @throws Exception
	 */
	public static Remote lookUp(String name) throws Exception{
		if(instance==null)throw new Exception("Null registery instance");
		Message m=new Message();
		m.setReference(name);
		m.setMessageType(Message.LOOK_UP);
		Message newMessage=SocketConnection.communicate(m, instance.remotehostname, instance.remoteport);
		String errorMessage=newMessage.getErrorMessage();
		if(errorMessage!=null&&!errorMessage.equals("")){
			throw new Exception(errorMessage);
		}
		StubInfo stubinfor=(StubInfo) newMessage.getValue();
		ProxyHandler px=new ProxyHandler(stubinfor.getReference(),stubinfor.getServerHost(),stubinfor.getPort());
		Class<?>[] interfaces=new Class<?>[stubinfor.getInterfaces().length];
		for(int i=0;i<stubinfor.getInterfaces().length;i++){
			interfaces[i]=Class.forName(stubinfor.getInterfaces()[i]);
		}
		return (Remote) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),interfaces , px);
	}
}
