package rmi.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;

import rmi.util.Message;
import rmi.util.RemoteException;

public class SocketConnection {

	static SocketConnection instance = null;
	private ServerSocket serversocket = null;
	private Integer port;
	private Hashtable<String, Message> messageTable;

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
			instance.messageTable = new Hashtable<String, Message>();
			Thread thread = new Thread(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					while (true) {
						Socket s;
						try {
							s = instance.serversocket.accept();
							ObjectInputStream in = new ObjectInputStream(s
									.getInputStream());
							Message newMessage = (Message) in.readObject();
							in.close();
							s.close();
							Message message = instance.messageTable
									.get(newMessage.getResponseId());
							instance.messageTable.remove(newMessage
									.getResponseId());
							synchronized (message) {
								message.setValue(newMessage.getValue());
								message.setRemote(newMessage.isRemote());
								message.setErrorMessage(newMessage
										.getErrorMessage());
								message.notify();
							}
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}

			});
			thread.start();

		}
		return instance;
	}

	
	public  static Message communicate(Message message,
			String hostname, Integer port) throws RemoteException, IOException,
			ClassNotFoundException {
		if (instance == null)
			throw new IOException("Null socketConnection");
		Socket socket = new Socket();

		socket.connect(new InetSocketAddress(hostname, port));

		ObjectOutputStream out = new ObjectOutputStream(
				socket.getOutputStream());
		message.setPort(instance.port);
		out.writeObject(message);
		out.close();
		socket.close();

		synchronized (message) {
			try {
				instance.messageTable.put(message.getMessageId(), message);
				message.wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String errorMessage = message.getErrorMessage();
		if (errorMessage != null && !errorMessage.equals("")) {
			throw new RemoteException(errorMessage);
		}
		return message;
	}
}
