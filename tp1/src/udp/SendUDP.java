package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class SendUDP {

	public static void main(String[] args) throws IOException {
		
		String machine = args[0];
		int port = Integer.parseInt(args[1]);
		InetAddress address = InetAddress.getByName(machine);
		DatagramSocket socket = new DatagramSocket();
		
		byte[] data = new byte[1024];
		String message = args[2];
		data = message.getBytes();
		DatagramPacket packet = new DatagramPacket(data, data.length, address, port);
		socket.send(packet);
		
		socket.close();
		
	}
	
}
