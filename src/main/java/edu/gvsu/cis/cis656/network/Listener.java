package edu.gvsu.cis.cis656.network;

import java.net.DatagramSocket;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.message.Message;
import edu.gvsu.cis.cis656.message.MessageTypes;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class Listener implements Runnable{
	private static boolean DEBUG = true;
	private VectorClock sharedClock;
	private PriorityQueue<Message> sharedMessageQueue;
	private DatagramSocket listener;
	
	public Listener(DatagramSocket listener, VectorClock sharedClock, PriorityQueue<Message> sharedMessageQueue){
		super();
		this.listener = listener;
		this.sharedClock = sharedClock;
		this.sharedMessageQueue = sharedMessageQueue;
	}
	@Override
	public void run() {
		int c_p,c_q;
		Message inMessage;
		Message topMessage;
		while(!Thread.currentThread().isInterrupted()) {
			inMessage = Message.receiveMessage(listener);
			if(inMessage.type == MessageTypes.CHAT_MSG) {
				if(DEBUG)
					System.out.println("\nDEBUG: Sender " + inMessage.sender + " message # " + inMessage.tag);
//				synchronized (this.sharedMessageQueue) {
					sharedMessageQueue.add(inMessage);
					topMessage = sharedMessageQueue.peek();
//				}
				while(topMessage != null) {
					c_q = topMessage.ts.getTime(topMessage.pid);
					c_p = sharedClock.getTime(topMessage.pid);
					if(DEBUG)
						System.out.println("c_q: " + c_q + " c_p+1:" + (c_p+1));
					if((c_q == c_p + 1) &&  sharedClock.happenedBefore(topMessage.ts)) {
						if(DEBUG)
							System.out.println("\nDEBUG: Print Order Sender " + topMessage.sender + " message #" + topMessage.tag);
						System.out.println(topMessage.sender + " says: " + topMessage.message);
//						synchronized (this.sharedClock) {
							this.sharedClock.update(topMessage.ts);
//						}
//						synchronized (this.sharedMessageQueue) {
							sharedMessageQueue.remove(topMessage);
							topMessage = sharedMessageQueue.peek();
//						}
						
					}else{
						topMessage = null;
					}
				}
			}else {
				System.out.println("Other Type of Message" + inMessage.toString());
			}
			
        }
		listener.close();
	}
}
