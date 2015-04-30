package com.abel.phonelocation;

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
				
				//iniciar timer
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					int contador = 0;
					@Override
					public void run() {
						Log.d("abel -- timer", ""+contador);
						
						runOnUiThread(new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								tvTimer.setText(""+contador);
							}
						});
						
						contador++;
					}
				};
				timer.schedule(task, 10, 1000);
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
		String imei = tm.getDeviceId();
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
			String latitud = Double.toString(loc.getLatitude());
			String longitud = Double.toString(loc.getLongitude());
			String altitud = Double.toString(loc.getAltitude());
			String time = Long.toString(loc.getTime());
			String vel = Float.toString(loc.getSpeed()*18/5);		// "*18/5" para pasar de m/s a km/h
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
