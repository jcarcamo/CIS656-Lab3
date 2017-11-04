package edu.gvsu.cis.cis656.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import edu.gvsu.cis.cis656.message.Message;

public class Sender implements Runnable {
	private DatagramSocket sender = null;
	
	private InetAddress host;
	private int port;
	private Message message;
	
	public Sender(DatagramSocket sender,InetAddress host, int port,  Message message){
		super();
		this.sender = sender;
		this.host = host;
		this.port = port;
		this.message = message;
		
	}
	@Override
	public void run() {
		byte[] buf = message.toString().getBytes();
		DatagramPacket packet;
		
		packet = new DatagramPacket(buf, buf.length, host, port);
		try {
			sender.send(packet);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    
	}
}
