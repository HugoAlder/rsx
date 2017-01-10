package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class SendMultiUDP {

	public static final String PROMPT = "Hugo : ";
	
	public static void main(String[] args) throws IOException {
		
		int port = 7654;
		InetAddress groupAddress = InetAddress.getByName("224.0.0.1");
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(groupAddress);
		
		String message = PROMPT;
		Scanner scan = new Scanner(System.in);
		while(!message.equals(PROMPT + "/exit")) {
			byte[] data = new byte[1024];
			message += scan.nextLine();
			data = message.getBytes();
			DatagramPacket packet = new DatagramPacket(data, data.length, groupAddress, port);
			message = PROMPT;
			socket.send(packet);
		}
		socket.leaveGroup(groupAddress);
		scan.close();
		socket.close();
		
	}
	
}
