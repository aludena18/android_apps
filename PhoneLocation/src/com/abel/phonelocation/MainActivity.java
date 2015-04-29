package com.abel.phonelocation;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.widget.TextView;

public class MainActivity extends Activity {
	TextView tvPosicion, tvHello;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvPosicion = (TextView)findViewById(R.id.txvPosicion);
		tvHello = (TextView)findViewById(R.id.txvHello);
		
		/*CODIGO PARA ACTIVAR EL GPS Y OBTENER LOS DATOS*/
		MiLocationListener lListener = new MiLocationListener();
		LocationManager lm = (LocationManager)getSystemService(LOCATION_SERVICE);
		lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, lListener);
		
		/*CODIGO PARA OBTENER EL IMEI DEL CELU*/
		TelephonyManager tm = (TelephonyManager)getSystemService(TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		tvHello.setText(imei);
	}
	
	public class MiLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location loc) {
			// TODO Auto-generated method stub
			String latitud = Double.toString(loc.getLatitude());
			String longitud = Double.toString(loc.getLongitude());
			String altitud = Double.toString(loc.getAltitude());
			String time = Long.toString(loc.getTime());
			String vel = Float.toString(loc.getSpeed());
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
