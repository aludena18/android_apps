package com.alghome.loclistener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.alghome.datagetset.GpsGetset;

public class MiLocListener extends Thread implements LocationListener{
	String msj;
	GpsGetset gpsData;
	Handler uiHandler;
	Handler dataHandler;
	
	public MiLocListener(Handler hd){
		uiHandler = hd;
	}
	
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
		
		msj = gpsData.getFechayhora() + "," + gpsData.getLatitud() + "," + gpsData.getLongitud();
		gpsData.setMensaje(msj);
		Log.d("abel--loclistener", msj);
		this.run();
	}

	@Override
	public void run() {
		Bundle b = new Bundle();
		b.putString("key", "loclistener : " + msj);
		
		Message msg = uiHandler.obtainMessage();
		msg.setData(b);
		uiHandler.sendMessage(msg);
	}
	
	
	public Handler getHandlerToMsgQueue(){
		return dataHandler;
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
