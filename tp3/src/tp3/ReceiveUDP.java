package tp3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class ReceiveUDP {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		int port = 7654;
		InetAddress groupAddress = InetAddress.getByName("224.0.0.1");
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(groupAddress);

		byte[] data = new byte[1024];

		while (true) {
			DatagramPacket packet = new DatagramPacket(data, data.length);
			socket.receive(packet);
			String message = new String(data);
			System.out.println(message);
			data = new byte[1024];
		}

	}

}