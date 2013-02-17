package naming;

import java.io.Serializable;

import util.DataTable;
import util.Message;
import util.MessageProcessor;

public class RegistryMessageProcessor implements MessageProcessor {

	@Override
	public Message process(Message message) {
		// TODO Auto-generated method stub
		if(message.getMessageType()==Message.BIND_TO_REGISTERY){
			DataTable.getInstance().put(message.getReference(), message.getValue());
			return null;
		}else if(message.getMessageType()==Message.LOOK_UP){
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
