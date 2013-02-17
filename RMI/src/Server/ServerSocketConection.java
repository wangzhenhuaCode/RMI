package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;



public class ServerSocketConection {
	public static ServerSocketConection instance=null;
	private Queue<Socket> messageQueue;
	private Thread listen;
	public static ServerSocketConection createServerSocketConnection(final Integer port){
		if(instance==null){
			instance=new ServerSocketConection();
			instance.messageQueue=new LinkedList<Socket>();
			instance.listen=new Thread(new Runnable(){

				@Override
				public void run() {
					// TODO Auto-generated method stub
					ServerSocket server=null;
					try {
						server= new ServerSocket(port);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						System.out.println("Port repeated!");
						return;
					}
					while(true){
						Socket s=null;
						try {
							s=server.accept();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						synchronized(instance.messageQueue){
							instance.messageQueue.add(s);
							instance.messageQueue.notify();
						}
						
					}
				}
				
			});
			instance.listen.start();
		}
		return instance;
	}
	public static ServerSocketConection getInstance() throws Exception{
		if(instance==null){
			throw new Exception("Null instance");
		}
			return instance;
		
	}
}
