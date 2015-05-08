package com.abel.phonelocation;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tvPosicion, tvHello, tvTimer;
	Button btOn, btOff;
	MiLocationListener lListener;
	LocationManager lm;
	
	String msj = "inicio";
	String imei, longitud, latitud, vel, time;
	
	InetAddress ipNumero;
	
	/*VARIABLES DONDE SE ALMACENARAN LOS MENSAJES DE ENTRADA Y SALIDA*/
	byte[] enviaData = new byte[1024];
	byte[] reciveData = new byte[1024];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvPosicion = (TextView)findViewById(R.id.txvPosicion);
		tvHello = (TextView)findViewById(R.id.txvHello);
		tvTimer = (TextView)findViewById(R.id.txvTimer);
		btOn = (Button)findViewById(R.id.btnOn);
		btOff = (Button)findViewById(R.id.btnOff);
		
		btOn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				btOn.setText("Encendido");
				btOff.setText("GPS Off");
				lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
				
				/*SE CREA EL HILO PARA ENVIAR DATOS*/
				new Thread(new Runnable() {
					public void run() {
						/*-------*/
						Timer timer = new Timer();
						TimerTask task = new TimerTask() {
							int contador = 0;
							@Override
							public void run() {
								
								/*PARA MOSTRAR EL CONTADOR EN LA GUI*/
								runOnUiThread(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										Log.d("abel--timer", ""+contador);
										tvTimer.setText(""+contador);
									}
								});
								/*--------------------------------------*/
								try {
									msj = imei + "," + time + "," + latitud + "," + longitud + "," + vel;
									Log.d("abel", msj);
									ipNumero = InetAddress.getByName("aludena.no-ip.biz");
									DatagramSocket clienteSocket = new DatagramSocket();
									enviaData = msj.getBytes();
									DatagramPacket enviaPaquete = new DatagramPacket(enviaData, enviaData.length, ipNumero, 21020);
									clienteSocket.send(enviaPaquete);
									clienteSocket.close();
								} catch (UnknownHostException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (SocketException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								contador++;
							}
						};
						//timer.schedule(task, 10, 10000);
						/*-------*/
						
					}
				}).start();
				
				
				/*INICIO DEL TIMER*/
				
				/*-------------------------------------*/
			}
		});
		
		
		
		
		btOff.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				btOff.setText("Apagado");
				btOn.setText("GPS On");
				lm.removeUpdates(lListener);
				
			}
		});

		/*CODIGO PARA OBTENER EL IMEI DEL CELU*/
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		tvHello.setText(imei);
		
		/*CODIGO PARA ACTIVAR EL GPS Y OBTENER LOS DATOS*/
		lListener = new MiLocationListener();
		lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		
		
	}
	
	/*LOCATION LISTENER*/
	public class MiLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			latitud = Double.toString(loc.getLatitude());
			longitud = Double.toString(loc.getLongitude());
			String altitud = Double.toString(loc.getAltitude());
			time = Long.toString(loc.getTime());
			vel = Float.toString(loc.getSpeed()*18/5);		// "*18/5" para pasar de m/s a km/h
			String direccion = Float.toString(loc.getBearing());
			
			tvPosicion.setText("La posicion actual es: \n" +
					"Latitud = " + latitud + "\n" +
					"Longitud = " + longitud + "\n" +
					"Altitud = " + altitud + "\n" +
					"Fecha y Hora = " + time + "\n" +
					"Velocidad = " + vel + "\n" +
					"Direccion = + " + direccion);
		}

		@Override
		public void onProviderDisabled(String arg0) {
			tvPosicion.setText("GPS Desactivado");
		}

		@Override
		public void onProviderEnabled(String arg0) {
			tvPosicion.setText("GPS Activado");
		}

		@Override
		public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
}
