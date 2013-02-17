package Naming;

import util.Message;
import util.MessageProcessor;

public class RegistryMessageProcessor implements MessageProcessor {

	@Override
	public Message process(Message message) {
		// TODO Auto-generated method stub
		if(message.getMessageType()==Message.BIND_TO_REGISTERY){
		
			return null;
		}else if(message.getMessageType()==Message.LOOK_UP){
			
		}
	}


}
