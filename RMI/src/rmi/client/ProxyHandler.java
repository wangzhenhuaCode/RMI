package rmi.client;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import rmi.util.Message;
import rmi.util.Remote;



/**
 * 
 * proxy class for method invocation.
 */
public class ProxyHandler implements InvocationHandler, Serializable {
	
	private static final long serialVersionUID = 1L;
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
		Message message=new Message();
		String fullName=arg1.toString();
		String method=arg1.getName()+fullName.substring(fullName.indexOf("("),fullName.indexOf(")")+1);
		message.setMethod(method);
		try{
			message.setArgs(arg2);
		}catch(Exception e){
			e.printStackTrace();
			throw new IOException("Arguments should be serilizatble");
		}
		message.setReference(reference);
		Message newMessage=SocketConnection.communicate(message, serverHost, serverPort);
		
		return newMessage.getValue();
		
		
	}
	
	public ProxyHandler(String reference) {
		super();
		this.reference = reference;
	}

	public String getReference() {
		return reference;
	}

}
