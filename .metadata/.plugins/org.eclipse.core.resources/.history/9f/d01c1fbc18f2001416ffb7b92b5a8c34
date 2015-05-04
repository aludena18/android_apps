package abel.android.cliente;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {
	EditText etMensaje;
	Button btEnviar;
	String msj;
	
	InetAddress ipNumero;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etMensaje = (EditText)findViewById(R.id.etxMensaje);
		btEnviar = (Button)findViewById(R.id.btnEnviar);
		
		btEnviar.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				msj = etMensaje.getText().toString();
				
				new Thread(new Runnable() {
					public void run() {
						try {
							ipNumero = InetAddress.getByName("107.172.12.220");
							
							/*VARIABLES DONDE SE ALMACENARAN LOS MENSAJES DE ENTRADA Y SALIDA*/
							byte[] enviaData = new byte[1024];
							byte[] reciveData = new byte[1024];
							
							/*SE CREA EL SOCKET DEL CLIENTE*/
							DatagramSocket clienteSocket = new DatagramSocket();
							
							/*PARA ENVIAR MENSAJES AL SERVIDOR*/
							enviaData = msj.getBytes();
							DatagramPacket enviaPaquete = new DatagramPacket(enviaData, enviaData.length, ipNumero, 21020);
							clienteSocket.send(enviaPaquete);
							
							/*PARA RECIBIR LAS RESPUESTAS DEL SERVIDOR*/
							DatagramPacket recibePaquete = new DatagramPacket(reciveData, reciveData.length);
							clienteSocket.receive(recibePaquete);
							String msjRsp = new String(recibePaquete.getData());
							
							Log.d("MainActivity -- ", msjRsp.trim());
							
						} catch (SocketException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (UnknownHostException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
	}
}
