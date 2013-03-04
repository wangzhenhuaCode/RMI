package rmi.server;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

import rmi.client.ProxyHandler;
import rmi.util.DataTable;
import rmi.util.Message;
import rmi.util.MessageProcessor;
import rmi.util.Remote;
import rmi.util.ServerSocketConection;

public class ServerMessageProcessor implements MessageProcessor {
	public static String localAddress = "";

	public Message process(Message message) {
		Message newMessage = new Message();
		newMessage.setResponseId(message.getMessageId());
		Object obj = DataTable.getInstance().get(message.getReference());
		Object val = null;
		// Multithread singleton method invocation;
		Class<?> type = obj.getClass();
		Class<?> paramaterType[] = new Class<?>[message.getArgs().length];
		for (int i = 0; i < message.getArgs().length; i++) {
			paramaterType[i] = message.getArgs()[i].getClass();
		}
		Method method = null;
		try {
			method = type.getMethod(message.getMethod(), paramaterType);
		} catch (SecurityException e) {
			newMessage.setErrorMessage("Remote error: " + e.toString());
			return newMessage;
		} catch (NoSuchMethodException e) {
			newMessage.setErrorMessage("Remote error: " + e.toString());
			return newMessage;
		}
		try {
			val = method.invoke(obj, message.getArgs());
		} catch (Exception e) {
			newMessage.setErrorMessage("Remote error: " + e.toString());
			return newMessage;
		}

		Class<?> interfaces[] = val.getClass().getInterfaces();
		boolean remotabe = false, proxyable = true;
		for (int j = 0; j < interfaces.length; j++) {
			if (interfaces[j].toString().equals(Remote.class.toString())) {
				remotabe = true;
			}
			if (interfaces[j].toString().equals(
					InvocationHandler.class.toString())) {
				proxyable = false;
			}
		}
		if (remotabe && proxyable) {
			String reference = message.getMessageId() + "_"
					+ (new Date()).getTime();
			if (localAddress == null || localAddress.equals("")) {
				InetAddress addr = null;
				try {
					addr = InetAddress.getLocalHost();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
				localAddress = addr.getHostAddress();
			}
			ProxyHandler px = new ProxyHandler(reference, localAddress,
					ServerSocketConection.getInstance().getPort());
			DataTable.getInstance().put(reference, val);
			val = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
					interfaces, px);
		}
		newMessage.setRemote(remotabe);
		try {
			newMessage.setValue((Serializable) val);
		} catch (Exception e) {
			newMessage.setErrorMessage("Remote error: " + e.toString());
			return newMessage;
		}
		return newMessage;

	}

}
