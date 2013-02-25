package server;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import util.DataTable;
import util.Message;
import util.MessageProcessor;
import util.Remote;

public class ServerMessageProcessor implements MessageProcessor {

	public Message process(Message message) {
		Message newMessage = new Message();
		Object obj = DataTable.getInstance().get(message.getReference());
		Object val = null;

		synchronized (obj) {
			Class<?> type = obj.getClass();
			Class<?> paramaterType[] = new Class<?>[message.getArgs().length];
			for (int i = 0; i < message.getArgs().length; i++) {
				paramaterType[i] = message.getArgs()[i].getClass();
			}
			Method method = null;
			try {
				method = type.getMethod(message.getMethod(), paramaterType);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				newMessage.setErrorMessage("Remote error: " + e.toString());
				return newMessage;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				newMessage.setErrorMessage("Remote error: " + e.toString());
				return newMessage;
			}
			try {
				val = method.invoke(obj, message.getArgs());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				newMessage.setErrorMessage("Remote error: " + e.toString());
				return newMessage;
			}
			DataTable.getInstance().put(message.getReference(), obj);
		}
		Class<?> interfaces[] = val.getClass().getInterfaces();
		boolean remotabe = false;
		for (int j = 0; j < interfaces.length; j++) {
			if (interfaces[j].toString().equals(Remote.class.toString())) {
				remotabe = true;
			}
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
