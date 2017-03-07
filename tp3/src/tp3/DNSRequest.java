package tp3;

public class DNSRequest {
	
	public byte[] createLabel(String address) {
		byte[] request = new byte[address.length() + 1];
		int i = 0, j = 0, cpt = 1;
		char c;
		for(i = 0; i<address.length(); i++) {
			c = address.charAt(i);
			if(c == '.') {
				request[j] = (byte) (cpt - 1);
				j = j + cpt;
				cpt = 1;
			}
			else {
				request[j + cpt] = (byte) c;
				cpt++;
			}
		}
		
		// On s'occupe du dernier compteur.
		
		request[j] = (byte) (cpt - 1);
		return request;
		
	}
	
	public static void main(String[] args) {
		int port = 53;
		
	}
	
}
