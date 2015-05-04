package abel.java.sockets;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ClaseInetAddress {
	static InetAddress dirección;
	static InetAddress dirección2;
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			dirección = InetAddress.getByName("time-a.nist.gov");
			dirección2 = InetAddress.getLocalHost();
			
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(dirección.getHostAddress());//129.6.15.28
		System.out.println(dirección2);
		System.out.println(InetAddress.getLoopbackAddress());
	}

}
