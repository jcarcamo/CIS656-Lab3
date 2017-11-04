package edu.gvsu.cis.cis656.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class Listener implements Runnable{
	private DatagramSocket listener = null;
	
	public Listener(DatagramSocket listener){
		super();
		this.listener = listener;
	}
	@Override
	public void run() {
		DatagramPacket packet;
		byte[] buf = new byte[256];
		while(!Thread.currentThread().isInterrupted()) {
			packet = new DatagramPacket(buf, buf.length);
			try {
				listener.receive(packet);
				String received = new String(packet.getData(), 0, packet.getLength());
		        System.out.println("From Server: " + received);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
		listener.close();
	}
}
