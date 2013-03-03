package rmi.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import rmi.util.Message;
import rmi.util.RemoteException;


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
				instance = null;
				throw e;
			}

		}
		return instance;
	}

	@SuppressWarnings("resource")
	public synchronized static Message communicate(Message message,
			String hostname, Integer port) throws RemoteException, IOException, ClassNotFoundException {
		if (instance == null)
			throw new IOException("Null socketConnection");
		Message newMessage = null;
		Socket socket = new Socket();
		
		socket.connect(new InetSocketAddress(hostname, port));
		
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
		
		String errorMessage=newMessage.getErrorMessage();
		if(errorMessage!=null&&!errorMessage.equals("")){
			throw new RemoteException(errorMessage);
		}
		return newMessage;
	}
}
