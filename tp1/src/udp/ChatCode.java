package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.Scanner;

public class ChatCode {

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
				while (true) {
					byte[] data = new byte[1024];
					message += scan.nextLine();

					// Encodage
					message = code(message, 5);

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
					
					// Decodage
					String tmp = decode(new String(data, 0, packet.getLength()), 5);
					
					String message = packet.getAddress().getHostName() + " " + tmp;
					System.out.println(message);
					data = new byte[1024];
				}
			}
		};

		receiver.start();
		sender.start();

	}

	public static String code(String s, int value) {
		String res = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			c = (char) ((int) c + value);
			res += c;
		}
		return res;
	}

	public static String decode(String s, int value) {
		String res = "";
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			c = (char) ((int) c - value);
			res += c;
		}
		return res;
	}

}
