package com.alghome.loclistener;

import com.alghome.datagetset.GpsGetset;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class MiLocListener implements LocationListener{
	
	GpsGetset gpsData;
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		gpsData = new GpsGetset();
		gpsData.setFechayhora(Long.toString(location.getTime()));
		gpsData.setLatitud(Double.toString(location.getLatitude()));
		gpsData.setLongitud(Double.toString(location.getLongitude()));
		gpsData.setVelocidad(Float.toString(location.getSpeed()));
		gpsData.setAltitud(Double.toString(location.getAltitude()));
		gpsData.setGiro(Float.toString(location.getBearing()));
		
		String msj = gpsData.getFechayhora() + "," + gpsData.getLatitud() + "," + gpsData.getLongitud();
		Log.d("abel--loclistener", msj);
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		gpsData = new GpsGetset();
		gpsData.setMensaje("GPS Activado");
		Log.d("abel--loclistener", gpsData.getMensaje());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		gpsData = new GpsGetset();
		gpsData.setMensaje("GPS Desactivado");
		Log.d("abel--loclistener", gpsData.getMensaje());
	}

}
