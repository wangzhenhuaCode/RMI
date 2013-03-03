package rmi.naming;

import java.io.Serializable;

import rmi.util.DataTable;
import rmi.util.Message;
import rmi.util.MessageProcessor;


public class RegistryMessageProcessor implements MessageProcessor {

	@Override
	public Message process(Message message) {
		
		if(message.getMessageType().equals(Message.BIND_TO_REGISTERY)){
			DataTable.getInstance().put(message.getReference(), message.getValue());
			return null;
		}else if(message.getMessageType().equals(Message.LOOK_UP)){
			System.out.println("look up");
			Serializable obj=(Serializable) DataTable.getInstance().get(message.getReference());
			Message newMessage=new Message();
			newMessage.setValue(obj);
			newMessage.setRemote(true);
			newMessage.setMessageType(Message.RETURN_LOOK_UP);
			return newMessage;
		}
		return null;
	}


}
