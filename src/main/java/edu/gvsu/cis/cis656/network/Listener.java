package edu.gvsu.cis.cis656.network;

import java.net.DatagramSocket;

import edu.gvsu.cis.cis656.clock.VectorClock;
import edu.gvsu.cis.cis656.message.Message;
import edu.gvsu.cis.cis656.message.MessageTypes;
import edu.gvsu.cis.cis656.queue.PriorityQueue;

public class Listener implements Runnable{
	private static boolean DEBUG = false;
	private VectorClock sharedClock;
	private PriorityQueue<Message> sharedMessageQueue;
	private DatagramSocket listener;
	
	public Listener(DatagramSocket listener, VectorClock sharedClock, PriorityQueue<Message> sharedMessageQueue){
		super();
		this.listener = listener;
		this.sharedClock = sharedClock;
		this.sharedMessageQueue = sharedMessageQueue;
		if(DEBUG) {
			System.out.println("\n\n\n\tInitial clock:");
			System.out.println("\tMy Clock:" + this.sharedClock.toString());
		}
	}
	@Override
	public void run() {
		int c_p,c_q;
		Message inMessage;
		Message topMessage;
		while(!Thread.currentThread().isInterrupted()) {
			inMessage = Message.receiveMessage(this.listener);
			if(inMessage.type == MessageTypes.CHAT_MSG) {
				this.sharedMessageQueue.add(inMessage);
				topMessage = this.sharedMessageQueue.peek();
				while(topMessage != null) {
					c_q = topMessage.ts.getTime(topMessage.pid);
					c_p = this.sharedClock.getTime(topMessage.pid);
					VectorClock comparissonClock = new VectorClock();
					comparissonClock.setClock(this.sharedClock);
					comparissonClock.addProcess(topMessage.pid, c_p +1);
					
					if(DEBUG) {
						System.out.println("\n\n\n\tMy Details:");
						System.out.println("\tMy Clock:" + this.sharedClock.toString());
						System.out.println("\tTop Message Details:");
						System.out.println("\tSender: " + topMessage.sender + " Clock:" + topMessage.ts.toString());
						System.out.println("\tCondition 1:");
						System.out.println("\tc_q: " + c_q + " c_p+1:" + (c_p+1));
						System.out.println("\tCondition 2:");
						System.out.println("\tHappenedBefore? " + topMessage.ts.happenedBefore(comparissonClock)+"\n\n\n");
					}
					if((c_q == c_p + 1) &&  topMessage.ts.happenedBefore(comparissonClock)) {
						System.out.println(topMessage.sender + " says: " + topMessage.message);
						this.sharedClock.update(topMessage.ts);
						this.sharedMessageQueue.remove(topMessage);
						topMessage = this.sharedMessageQueue.peek();
						
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
