import edu.gvsu.cis.cis656.ui.ChatClient;

public class App {
	public static void main (String[] args){
		if (args.length != 1) {
            System.out.println("Usage: java -jar lab3-0.0.1-SNAPSHOT.jar <username>");
            System.exit(-1);
        }
		String username = args[0];
		ChatClient client = new ChatClient(username);
		client.startClient("localhost", 8000);
	}
}
