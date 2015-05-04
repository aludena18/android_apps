package abel.java.sockets;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

class UDPClient {
	public static void main(String args[]) throws Exception {
		
		System.out.println("Cliente UDP iniciado");
		
		BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
		String sentence = inFromUser.readLine();

		InetAddress IPAddress = InetAddress.getByName("192.168.1.34");
		System.out.println(IPAddress);
		
		/*CREA LAS VARIABLES*/
		byte[] sendData = new byte[1024];
		byte[] receiveData = new byte[1024];
		
		/*CREA EL SOCKET DEL CLIENTE*/
		DatagramSocket clientSocket = new DatagramSocket();

		/*PARA ENVIAR MENSAJES A UN SERVIDOR*/
		sendData = sentence.getBytes();
		DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 2015);
		clientSocket.send(sendPacket);
		
		/*PARA RECIBIR LAS RESPUESTAS DEL SERVIDOR*/
		DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
		clientSocket.receive(receivePacket);
		String modifiedSentence = new String(receivePacket.getData());
		clientSocket.close();

		System.out.println("FROM SERVER:" + modifiedSentence);
	}
}
