package com.alghome.geolocalizador;

import java.util.Timer;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.IBinder;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;
import com.alghome.datasend.SendTask;
import com.alghome.loclistener.MiLocListener;
import com.alghome.loclistener.MiLocListenerGsm;

public class ServicioGeoBoot extends Service{
	//MiLocManager mLocManager = new MiLocManager(this);
	LocationManager mLocManager;
	MiLocListener mLocListener;
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	Timer timer;

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		//super.onCreate();
		//mLocManager.execute();
		locManager();
		startTimer();
		Log.d("SERVICIO GEO BOOT", "Servicio creado");
	}

	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		//super.onDestroy();
		//mLocManager.cancel(true);
		mLocManager.removeUpdates(mLocListener);
		timer.cancel();
		Log.d("SERVICIO GEO BOOT", "Servicio destruido");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		//return super.onStartCommand(intent, flags, startId);
		Log.d("SERVICIO GEO BOOT", "Servicio iniciado");
		return START_STICKY;
	}
	
	
	private void locManager() {
		// TODO Auto-generated method stub
		mLocManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		TelephonyManager mTManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		GsmCellLocation gsmCellLoc = (GsmCellLocation)mTManager.getCellLocation();
		
		mLocListener = new MiLocListener(mTManager.getDeviceId(), this);
		MiLocListenerGsm mLocListenerGsm = new MiLocListenerGsm( mTManager, gsmCellLoc);
		
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

		datosGetSet = mLocListener.getDataGPS();
		datosGsmGetSet = mLocListenerGsm.getDataGSM();
	}
	
	private void startTimer() {
		// TODO Auto-generated method stub
		timer = new Timer();
		SendTask task = new SendTask(this, datosGetSet, datosGsmGetSet, "107.172.12.220", 21020);
		timer.scheduleAtFixedRate(task, 6000, 30*1000);
	}

}
