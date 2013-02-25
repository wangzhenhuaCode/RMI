package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import util.Message;

public class SocketConnection {

	static SocketConnection instance = null;
	private ServerSocket serversocket = null;
	private Integer port;

	public static SocketConnection createSocket(Integer port)
			throws IOException {
		if (instance == null) {
			instance = new SocketConnection();
			instance.port = port;
			try {
				instance.serversocket = new ServerSocket(port);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				instance = null;
				throw e;
			}

		}
		return instance;
	}

	@SuppressWarnings("resource")
	public synchronized static Message communicate(Message message,
			String hostname, Integer port) throws Exception {
		if (instance == null)
			throw new Exception("Null socketConnection");
		Message newMessage = null;
		Socket socket = new Socket();
		try {
			socket.connect(new InetSocketAddress(hostname, port));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			throw new Exception("Connection failure");
		}
		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		message.setPort(instance.port);
		out.writeObject(message);
		out.close();
		socket.close();
		Socket s = instance.serversocket.accept();
		ObjectInputStream in = new ObjectInputStream(s.getInputStream());
		newMessage = (Message) in.readObject();
		in.close();
		s.close();

		return newMessage;
	}
}
