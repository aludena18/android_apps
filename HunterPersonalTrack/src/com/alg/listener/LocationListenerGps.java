package com.alg.listener;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

import com.alg.builder.FrameGps;
import com.alg.getset.GpsData;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

public class LocationListenerGps implements LocationListener{
	
	GpsData gpsData = new GpsData();
	FrameGps frameGps;
	SimpleDateFormat sdf;
	
	public LocationListenerGps(String i, String ev){
		sdf = new SimpleDateFormat("yyMMddHHmmss",Locale.US);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		gpsData.setImei(i);
		gpsData.setEvent(ev);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		long dateCel = System.currentTimeMillis();
		
		gpsData.setTimeGps(location.getTime());
		gpsData.setTime(sdf.format(dateCel).trim());
		gpsData.setLatitude(Double.toString(location.getLatitude()));
		gpsData.setLongitude(Double.toString(location.getLongitude()));
		gpsData.setSpeed(Integer.toString((int)location.getSpeed()*18/5));
		gpsData.setAltitude(Integer.toString((int)location.getAltitude()));
		gpsData.setBearing(Integer.toString((int)location.getBearing()));
		
	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}
	
	public GpsData getGpsData(){
		return gpsData;
	}
	
}
