package abel.java.sockets;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
	static ServerSocket servidor;
	static Socket cliente = null;

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		
		/*INGRESA EL PUERTO ESCUCHA*/
		System.out.print("INGRESE EL PUERTO : ");
		BufferedReader bfr = new BufferedReader(new InputStreamReader(System.in));
		String msj = bfr.readLine().trim();
		int serverPort = Integer.parseInt(msj);
		
		try {
			/*CREAR EL SOCKET DEL SERVIDOR*/
			servidor = new ServerSocket(serverPort);
			System.out.println("SERVIDOR TPC INICIADO");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Thread hilo = new Thread(new Runnable() {
			public void run() {
				while(true){
					try {
						/*PARA RECIBIR MENSAJES DESDE UN CLIENTE*/
						cliente = servidor.accept();				
						BufferedReader entrada = new BufferedReader(new InputStreamReader(cliente.getInputStream()));
						String msje = entrada.readLine().trim();
						
						/*PARA RESPONDER AL CLIENTE CON UN MENSAJE*/
						DataOutputStream salida = new DataOutputStream(cliente.getOutputStream());
						salida.writeUTF(msje);
						
						System.out.println("RECIBIDO : " + msje);
						
						/*SE CIERRAN LA ENTRAD Y EL SOCKET*/
						entrada.close();
						cliente.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					} 
				}
			}
		});
		hilo.start();
	}
}
