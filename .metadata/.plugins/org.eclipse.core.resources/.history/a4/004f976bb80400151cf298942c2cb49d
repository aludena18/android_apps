package com.alghome.geolocalizador;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.alghome.loclistener.MiLocManager;

public class ServicioGeoBoot extends Service{

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//super.onCreate();
		Log.d("SERVICIO GEO BOOT", "Servicio creado");
		new MiLocManager(this).execute();
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//super.onDestroy();
		Log.d("SERVICIO GEO BOOT", "Servicio destruido");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//return super.onStartCommand(intent, flags, startId);
		Log.d("SERVICIO GEO BOOT", "Servicio iniciado");
		return START_STICKY;
	}

}
