package abel.java.sockets;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClaseInetAddress {
	static InetAddress direcci�n;
	static InetAddress direcci�n2;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			direcci�n = InetAddress.getByName("time-a.nist.gov");
			direcci�n2 = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(direcci�n.getHostAddress());//129.6.15.28
		System.out.println(direcci�n2);
		System.out.println(InetAddress.getLoopbackAddress());
	}

}
