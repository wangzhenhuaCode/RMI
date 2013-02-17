package client;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyHandler implements InvocationHandler, Remote {
	private String reference;
	private String serverHost;
	private Integer serverPort;
	
	public ProxyHandler(String reference, String serverHost, Integer serverPort) {
		super();
		this.reference = reference;
		this.serverHost = serverHost;
		this.serverPort = serverPort;
	}

	@Override
	public Object invoke(Object arg0, Method arg1, Object[] arg2)
			throws Throwable {
		// TODO Auto-generated method stub
		Message message=new Message();
		message.setMethod(arg1.toString());
		try{
			message.setArgs((Serializable[]) arg2);
		}catch(Exception e){
			throw new Exception("Arguments should be serilizatble");
		}
		Message newMessage=SocketConnection.communicate(message, serverHost, serverPort);
		if(newMessage.isRemote()){
			Class<?> interfaces[]=new Class<?>[newMessage.getInterfaceName().length];
			for(int i=0;i<newMessage.getInterfaceName().length;i++){
				interfaces[i]=Class.forName(newMessage.getInterfaceName()[i]);
			}
			return (Remote) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, newMessage.getProxy());
		}else{
			return newMessage.getReturnVal();
		}
		
	}
	
	public ProxyHandler(String reference) {
		super();
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

}
