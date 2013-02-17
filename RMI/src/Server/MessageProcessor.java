package Server;

import util.Message;

public interface MessageProcessor {
	public Message process(Message message);
}
