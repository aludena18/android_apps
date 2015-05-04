package abel.java.sockets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

class UDPServer{
	static DatagramSocket socketServer = null;
	static DatagramPacket dataPacket = null;
	static byte[] receive = new byte[1024];
	byte[] send = new byte[1024];
	
	public static void main(String args[]) throws IOException {
		
		/*INGRESA EL PUERTO ESCUCHA*/
		System.out.print("INGRESE EL PUERTO : ");
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String msj = bfr.readLine().trim();
		int serverPort = Integer.parseInt(msj);
		
		try {
			/*CREA EL SOCKET UDP*/
			socketServer = new DatagramSocket(serverPort);
			System.out.println("Servidor UDP iniciado");
		} catch (SocketException e1) {
			e1.printStackTrace();
		}

		Thread hiloUdp = new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						/*PARA RECIBIR MENSAJES DESDE UN CLIENTE*/
						dataPacket = new DatagramPacket(receive, receive.length);
						socketServer.receive(dataPacket);
						String sentence = new String(dataPacket.getData()).trim();

						System.out.println("RECIBIDO: " + sentence);

						/*PARA RESPONDER AL CLIENTE CON UN MENSAJE*/
						InetAddress IPAddress = dataPacket.getAddress();
						int port = dataPacket.getPort();
						//send = sentence.getBytes();
						//DatagramPacket sendPacket = new DatagramPacket(send, send.length, IPAddress, port);
						//serverSocket.send(sendPacket);

						System.out.println(IPAddress + " : " + port);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
		hiloUdp.start();
	}
}
