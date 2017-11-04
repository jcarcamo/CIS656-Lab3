import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.json.JSONObject;

import edu.gvsu.cis.cis656.message.Message;
import edu.gvsu.cis.cis656.message.MessageTypes;

public class App {
	public static void main (String[] args){
		if (args.length != 1) {
            System.out.println("Usage: java ChatClient <username>");
            System.exit(-1);
        }
		int myPid = -1;
		String username = args[0];
		// get a datagram socket
        DatagramSocket socket;
		try {
			socket = new DatagramSocket();
			
			Message message = new Message(MessageTypes.REGISTER, username,0, null,"");
			// send request
	        byte[] buf = message.toString().getBytes();
	        InetAddress address = InetAddress.getByName("localhost");
	        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 8000);
	        socket.send(packet);
	    
	        byte[] responseBuf = new byte[65508]; //max datagram size 65508
	        // get response
	        packet = new DatagramPacket(responseBuf, responseBuf.length);
	        socket.receive(packet);
	        // display response
	        String received = new String(packet.getData());
	        JSONObject response = new JSONObject(received);
	        myPid = response.getInt("pid");
	        if(myPid == -1){
	        	System.err.println("Username already taken");
	        	System.exit(-1);
	        }
			
	        System.out.println("Response: " + received);
	    
	        
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Chat App for " + username);
//        while(true) {
//
//        
//        }
	}
}
