package util;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import Server.MessageProcessor;


public class ThreadPool{
	private Thread[] workerList;
	public ThreadPool(int n, final Class<MessageProcessor> processClass){
			workerList=new Thread[n];
			for(int i=0;i<n;i++){
				workerList[i]=new Thread(new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						while(true){
							Socket s=null;
							try {
								s=ServerSocketConection.getNewSocket();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							ObjectInputStream in = null;
							try {
								in = new ObjectInputStream(s.getInputStream());
								Message message=(Message) in.readObject();
								in.close();
								MessageProcessor mp=processClass.newInstance();
								Message newMessage=mp.process(message);
								ObjectOutputStream out =new ObjectOutputStream(s.getOutputStream());
								out.writeObject(newMessage);
								out.close();
								s.close();
							}catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InstantiationException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					}
					
				});
			}
		
	}

	
	
}
