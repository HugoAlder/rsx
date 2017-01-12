package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;
import java.util.Scanner;

public class ChatCode {

	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		int port = 7654;
		String address = "224.0.0.2";
		InetAddress groupAddress = InetAddress.getByName(address);
		MulticastSocket socket = new MulticastSocket(port);
		socket.joinGroup(groupAddress);

		Thread sender = new Thread() {
			public void run() {

				System.out.println("Server started");
				
				String message = "";
				Scanner scan = new Scanner(System.in);
				while (true) {
					byte[] data = new byte[1024];
					message += scan.nextLine();

					// Encodage
					message = code(message, getKey());

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
					String tmp = decode(new String(data, 0, packet.getLength()), getKey());

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
	
	public static String getMacAddress() {
		try {
			InetAddress address = InetAddress.getLocalHost();
			Enumeration<NetworkInterface> network = NetworkInterface.getNetworkInterfaces();
			while (network.hasMoreElements()) {
				NetworkInterface ni = network.nextElement();
				byte[] mac = ni.getHardwareAddress();
				if (mac != null) {
					String res = "";
					for (int i = 0; i < mac.length; i++) {
						res += String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : "");
					}
					return res;
				}
			}
		} catch (UnknownHostException | SocketException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static int getKey() {
		String macAddress = getMacAddress();
		String[] tab = macAddress.split("-");
		return Integer.parseInt(tab[tab.length - 1], 16);
	}

}
