package abel.java.sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class TCPClient {
	static final String IP_NUMBER = "192.168.1.34";
	static final int PORT_NUMBER = 2015;

	/**
	 * @param args
	 * @throws Exception 
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception{

		System.out.print("Ingrese mensaje: ");
		BufferedReader entradaMsj = new BufferedReader(new InputStreamReader(System.in));
		String msj = entradaMsj.readLine().trim();

		try {
			/*SE CREA EL SOCKET PARA CONECTARSE AL SERVIDOR*/
			Socket servidor = new Socket(IP_NUMBER, PORT_NUMBER);
			System.out.println("Se creo el socket de comunicacion");

			/*PARA ENVIAR MENSAJES AL SERVIDOR*/
			DataOutputStream salida = new DataOutputStream(servidor.getOutputStream());
			salida.writeUTF(msj);
			salida.write('\n');
			System.out.println("Se envio el mensaje");

			/*PARA RECIBIR LA RESPUESTA DEL SERVIDOR*/
			BufferedReader entrada = new BufferedReader(new InputStreamReader(servidor.getInputStream()));
			String msjInput = entrada.readLine().trim();

			/*SE CIERRA EL SOCKET*/
			servidor.close();
			
			System.out.println("DESDE EL SERVIDOR : " + msjInput);
			System.out.println("Se cerro el socket");

		} catch (UnknownHostException e) {
			System.out.println("host desconocido");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("error de entrada y salida");
			e.printStackTrace();
		}
	}

}
