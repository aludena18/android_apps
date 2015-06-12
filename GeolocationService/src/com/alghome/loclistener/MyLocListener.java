package com.alghome.loclistener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alghome.builder.FrameBuilder;
import com.alghome.datagetset.GpsGetset;

public class MyLocListener implements LocationListener{
	GpsGetset gpsData = new GpsGetset();
	Context context;
	
	public MyLocListener(String i, Context ctx){
		gpsData.setImei(i);
		context = ctx;
	}
	
	@Override
	public void onLocationChanged(Location location) {
		
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss",Locale.US);
		long dateGps = location.getTime();
		long dateCel = System.currentTimeMillis();
		
		gpsData.setFechayhora(sdf.format(dateCel).trim());
		gpsData.setLatitud(Double.toString(location.getLatitude()));
		gpsData.setLongitud(Double.toString(location.getLongitude()));
		gpsData.setVelocidad(Integer.toString((int)location.getSpeed()*18/5));
		gpsData.setAltitud(Integer.toString((int)location.getAltitude()));
		gpsData.setGiro(Integer.toString((int)location.getBearing()));
		
		FrameBuilder fb = new FrameBuilder(gpsData);
		gpsData.setTramaGps(fb.tramaGPS());
		
		Log.d("MYLOCMANAGER", "Trama : " + fb.tramaGPS());
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.d("MI LOC LISTENER -- " + provider, "Status = " + status);
	}

	@Override
	public void onProviderEnabled(String provider) {
		String msje = "GPS Activado";
		Log.d("abel--loclistener", msje);
		Toast.makeText(context, msje, Toast.LENGTH_LONG).show();
	}

	@Override
	public void onProviderDisabled(String provider) {
		//gpsData.setTramaGps("$$V");
		String msje = "GPS Desactivado";
		Log.d("abel--loclistener", msje);
		Toast.makeText(context, msje, Toast.LENGTH_LONG).show();
	}
	
	
	public GpsGetset getDataGPS() {
		return gpsData;
	}

	
	
}