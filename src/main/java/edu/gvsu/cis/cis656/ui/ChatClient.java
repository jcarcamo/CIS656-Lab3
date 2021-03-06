package edu.gvsu.cis.cis656.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.message.Message;
import edu.gvsu.cis.cis656.message.MessageComparator;
import edu.gvsu.cis.cis656.message.MessageTypes;
import edu.gvsu.cis.cis656.network.Listener;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class ChatClient {
	private int messageOrder;
	private int myPid;
	private String username;
	private VectorClock myClock;
	private PriorityQueue<Message> messageQueue;
	
	private DatagramSocket socket;

	private InetAddress address;
	private int port;
	
	public ChatClient(String username) {
		this.username = username;
		this.messageOrder = 0;
	}
	
	private void initializeNetwork(String address, int port) {
		try {
			this.socket = new DatagramSocket();
			this.address = InetAddress.getByName(address);
			this.port = port;
	        
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean registerClient() {
		boolean registerResult = false;
		
		Message registerMessage = new Message(MessageTypes.REGISTER, this.username, 0, null,"");
		Message.sendMessage(registerMessage, this.socket, this.address, this.port);
		
		Message registerResponseMessage = Message.receiveMessage(socket);
        
        if(registerResponseMessage.type != MessageTypes.ERROR){
        	this.myPid = registerResponseMessage.pid;
            this.myClock = new VectorClock();
            this.myClock.addProcess(myPid, 0);
            
            this.messageQueue = new PriorityQueue<>(new MessageComparator());
            
            registerResult =  true;
        }

        
        return registerResult;
	}
	
	public void startClient(String address, int port) {
		initializeNetwork(address, port);
		if(!registerClient()){
        	System.out.println("Username " + this.username +" already in use");
			System.exit(-1);
        }
		System.out.println("Chat App for " + username);
		
		Thread listener = new Thread(new Listener(this.socket, this.myClock, this.messageQueue));
		listener.start();
		
		BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
		String readLine = "";
		Message outMessage = new Message(MessageTypes.CHAT_MSG, this.username, this.myPid, this.myClock, readLine);
		while(true) {
    		System.out.println("Type your message or type 'exit' to quit: ");	
            try {
            	readLine = is.readLine();
            	switch(readLine){
            		case "":
            			break;
            		case "exit":
                		listener.interrupt();
                		break;
                	default:
                		this.messageOrder++;
                		outMessage.message = readLine;
                		outMessage.tag = this.messageOrder;
            			this.myClock.tick(myPid);
        				Message.sendMessage(outMessage, this.socket, this.address, this.port);
        				break;
            	}
                					
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
