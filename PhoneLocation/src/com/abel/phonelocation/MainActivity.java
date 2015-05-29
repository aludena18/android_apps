package com.abel.phonelocation;

import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
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
	
	SendThread hilo = new SendThread();
	
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
			public void onClick(View arg0) {
				btOn.setText("Encendido");
				btOff.setText("GPS Off");
				
				/*CODIGO PARA ACTIVAR EL GPS*/
				Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
				intent.putExtra("enabled", true);
				sendBroadcast(intent);
				
				/*SE CREA EL HILO PARA ENVIAR DATOS*/
				//hilo.start();
			}
		});
		
		btOff.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				btOff.setText("Apagado");
				btOn.setText("GPS On");

				/*CODIGO PARA APAGAR EL GPS*/
				//lm.removeUpdates(lListener);
				Intent intent = new Intent("android.location.GPS_ENABLED_CHANGE");
				intent.putExtra("enabled", false);
				sendBroadcast(intent);
			}
		});

		/*CODIGO PARA OBTENER EL IMEI DEL CELU*/
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		imei = tm.getDeviceId();
		tvHello.setText(imei);
		
		/*CODIGO PARA ACTIVAR EL GPS Y OBTENER LOS DATOS*/
		lListener = new MiLocationListener();
		lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
		
	}
	
	/*LOCATION LISTENER*/
	public class MiLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			latitud = Double.toString(loc.getLatitude());
			longitud = Double.toString(loc.getLongitude());
			String altitud = Integer.toString((int)loc.getAltitude());
			vel = Integer.toString((int)loc.getSpeed()*18/5);		// "*18/5" para pasar de m/s a km/h
			String direccion = Integer.toString((int)loc.getBearing());
			
			long t = loc.getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss", Locale.US);
			time = sdf.format(t);
			
			tvPosicion.setText("La posicion actual es: \n" +
					"Latitud = " + latitud + "\n" +
					"Longitud = " + longitud + "\n" +
					"Altitud = " + altitud + "\n" +
					"Fecha y Hora = " + time + "\n" +
					"Velocidad = " + vel + "\n" +
					"Direccion = + " + direccion);
			
			msj = imei + "," + time + "," + latitud + "," + longitud + "," + vel;
			hilo.setFrame(msj);
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
