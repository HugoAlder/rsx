package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class Chat {
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		int port = 7654;
		InetAddress groupAddress = InetAddress.getByName("224.0.0.2");
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(groupAddress);
		
		Thread sender = new Thread() {
			public void run() {
			
				String message = "";
				Scanner scan = new Scanner(System.in);
				while(!message.equals("/exit")) {
					byte[] data = new byte[1024];
					message += scan.nextLine();
					data = message.getBytes();
					DatagramPacket packet = new DatagramPacket(data, data.length, groupAddress, port);
					message = "";
					try {
						socket.send(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				
			}
		};
		
		Thread receiver = new Thread() {
			public void run() {
				
				byte[] data = new byte[1024];

				while (true) {
					DatagramPacket packet = new DatagramPacket(data, data.length);
					try {
						socket.receive(packet);
					} catch (IOException e) {
						e.printStackTrace();
					}
					String message = packet.getAddress().getHostName() + " : " + new String(data);
					System.out.println(message);
					data = new byte[1024];
				}
				
			}
		};

		receiver.start();
		sender.start();
		
	}

}
