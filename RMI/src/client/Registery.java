package client;

import java.lang.reflect.Proxy;

public class Registery {
	static Registery instance=null;
	private String remotehostname;
	private Integer remoteport;
	private Integer localport;
	public static Registery createRigstery(String remotehostname,Integer remoteport,Integer localport) throws Exception{
		if(instance==null){
			instance=new Registery();
			instance.remoteport=remoteport;
			instance.remotehostname=remotehostname;
			instance.localport=localport;
			SocketConnection.createSocket(localport);
		}
		return instance;
	}
	public static Registery getRigstery() throws Exception{
		if(instance==null){
			throw new Exception("Registery is null");
		}
		return instance;
	}
	public Remote lookUp(String name) throws Exception{
		Message m=new Message();
		m.setReference(name);
		Message newMessage=SocketConnection.communicate(m, remotehostname, remoteport);
		Class<?> interfaces[]=new Class<?>[newMessage.getInterfaceName().length];
		for(int i=0;i<newMessage.getInterfaceName().length;i++){
			interfaces[i]=Class.forName(newMessage.getInterfaceName()[i]);
		}
		return (Remote) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, newMessage.getProxy());
	}
}
