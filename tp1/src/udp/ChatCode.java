package udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.Scanner;

public class ChatCode {

	private final static int PORT = 7654;
	private final static String USERNAME = "Hugo";
	private final static String ADDRESS = "224.0.0.2";
	
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {

		InetAddress groupAddress = InetAddress.getByName(ADDRESS);
		MulticastSocket socket = new MulticastSocket(PORT);
		socket.joinGroup(groupAddress);

		Thread sender = new Thread() {
			public void run() {

				System.out.println("Server status : online");			
				String message = "";
				Scanner scan = new Scanner(System.in);
				
				while (true) {
					byte[] data = new byte[1024];
					message += scan.nextLine();

					// Encodage
					message = code(message, getKey());
					String finalMessage = getKey() + ":" + USERNAME + ":" + message;
					data = finalMessage.getBytes();

					DatagramPacket packet = new DatagramPacket(data, data.length, groupAddress, PORT);
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
					String tmp = new String(data, 0, packet.getLength());
					String message = getMessage(tmp);
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
		} catch (SocketException e1) {
			e1.printStackTrace();
		}
		return null;
	}
	
	public static int getKey() {
		String macAddress = getMacAddress();
		String[] tab = macAddress.split("-");
		return Integer.parseInt(tab[tab.length - 1], 16);
	}
	
	public static String getMessage(String s) {
		String[] tab = s.split(":");
		if(tab.length != 3) {
			return "Error : wrong message format";
		}
		int key = Integer.parseInt(tab[0]);
		String username = tab[1];
		String message = decode(tab[2], key);
		String display = username + " : " + message;
		return display;
	}

}
