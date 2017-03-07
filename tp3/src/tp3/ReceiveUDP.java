package tp3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class ReceiveUDP {

	public static void main(String[] args) throws IOException {
		
		int port = 7654;
		DatagramSocket socket = new DatagramSocket(port);
		
		byte[] data = new byte[1024];
		DatagramPacket packet = new DatagramPacket(data, data.length);
		socket.receive(packet);
		
		String message = new String(data);
		System.out.println("Received from port " + port + " : " + message);
		socket.close();
	}
	
}