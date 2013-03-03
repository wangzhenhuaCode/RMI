package util;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Queue;



public class ServerSocketConection {
	public static ServerSocketConection instance=null;
	private Queue<Socket> messageQueue;
	private Thread listen;
	private ServerSocket server;
	public static ServerSocketConection createServerSocketConnection(final Integer port) throws IOException{
		if(instance==null){
			instance=new ServerSocketConection();
			instance.messageQueue=new LinkedList<Socket>();
			instance.server= new ServerSocket(port);
			instance.listen=new Thread(new Runnable(){	
				@Override
				public void run() {
					
					
					
					while(true){
						Socket s=null;
						try {
							s=instance.server.accept();
						} catch (IOException e) {
							
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
	public static ServerSocketConection getInstance(){
		
			return instance;
		
	}
	public static Socket getNewSocket() throws InterruptedException{
		Socket s=null;
		synchronized(instance.messageQueue){
			while(instance.messageQueue.isEmpty()){
				instance.messageQueue.wait();
			}
			s=instance.messageQueue.poll();
		}
		return s;
	}
}
