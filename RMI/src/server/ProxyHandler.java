package server;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import util.Message;
import util.Remote;

import client.SocketConnection;

public class ProxyHandler implements InvocationHandler, Remote {
	/**
	 * 
	 */
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
		// TODO Auto-generated method stub
		Message message=new Message();
		message.setMethod(arg1.toString());
		try{
			message.setArgs((Serializable[]) arg2);
		}catch(Exception e){
			throw new Exception("Arguments should be serilizatble");
		}
		message.setReference(reference);
		Message newMessage=SocketConnection.communicate(message, serverHost, serverPort);
		if(newMessage.isRemote()){
			return (Remote)newMessage.getValue();
		}else{
			return newMessage.getValue();
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
