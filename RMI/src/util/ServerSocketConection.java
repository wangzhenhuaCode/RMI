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
