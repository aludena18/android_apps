package com.alghome.loclistener;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.alghome.datagetset.GpsGetset;

public class MiLocListener implements LocationListener{
	String msj;
	GpsGetset gpsData;
	
	public MiLocListener(String i){
		gpsData = new GpsGetset();
		gpsData.setImei(i);
	}
	
	@Override
	public void onLocationChanged(Location location) {
		// TODO Auto-generated method stub
		
		gpsData.setFechayhora(Long.toString(location.getTime()));
		gpsData.setLatitud(Double.toString(location.getLatitude()));
		gpsData.setLongitud(Double.toString(location.getLongitude()));
		gpsData.setVelocidad(Float.toString(location.getSpeed()));
		gpsData.setAltitud(Double.toString(location.getAltitude()));
		gpsData.setGiro(Float.toString(location.getBearing()));
		
		//msj = gpsData.getFechayhora() + "," + gpsData.getLatitud() + "," + gpsData.getLongitud();
		//gpsData.setMensaje(msj);
		//Log.d("abel--loclistener", msj);
		
		FrameBuilder();
		
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method .stub
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		gpsData.setMensaje("GPS Activado");
		Log.d("abel--loclistener", gpsData.getMensaje());
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		gpsData.setMensaje("GPS Desactivado");
		Log.d("abel--loclistener", gpsData.getMensaje());
	}
	
	public GpsGetset datosGPS() {
		return gpsData;
	}
	
	public void FrameBuilder(){
		String frame;
		
		frame = "$$," + gpsData.getImei() + "," +		//imei
						" "	+ "," +						//command
						" "	+ "," +						//event codde
						gpsData.getLatitud() + "," +	//latitud
						gpsData.getLongitud() + "," +	//longitud
						gpsData.getFechayhora() + "," +	//date time
						" "	+ "," +						//gps status
						" "	+ "," +						//number satellites
						" "	+ "," +						//gsm signal
						gpsData.getVelocidad() + "," +	//speed
						gpsData.getGiro() + ","	+		//heading
						" "	+ "," +						//HDOP
						gpsData.getAltitud() + "," +	//altitude
						" "	+ "," +						//mileage
						" "	+ "," +						//runtime
						" "	+ "," +						//Base ID
						" "	+ "," +						//Sate IO
						"*";
			
		gpsData.setMensaje(frame);
		
	}

}
