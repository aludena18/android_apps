package com.alghome.loclistener;

import java.util.Timer;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;
import com.alghome.datasend.SendTask;

public class MiLocManager extends AsyncTask<Void, Void, Void>{
	MiLocListenerGsm mLocListenerGsm;
	MiLocListener mLocListener;
	LocationManager mLocManager;
	TelephonyManager mTManager;
	GsmCellLocation gsmCellLoc;
	Context contexto;
	
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	
	static final String SERVER_IP = "107.172.12.220";
	static final int SERVER_PORT = 21020;
	static final int TIME_REPORT = 30;
	
	
	public MiLocManager(Context ctx){
		contexto = ctx;
	}
	

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		//super.onPreExecute();
		mLocManager = (LocationManager)contexto.getSystemService(Context.LOCATION_SERVICE);
		mTManager = (TelephonyManager)contexto.getSystemService(Context.TELEPHONY_SERVICE);
		gsmCellLoc = (GsmCellLocation)mTManager.getCellLocation();
		
		mLocListener = new MiLocListener(mTManager.getDeviceId(), contexto);
		mLocListenerGsm = new MiLocListenerGsm( mTManager, gsmCellLoc);
		
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);

		datosGetSet = mLocListener.getDataGPS();
		datosGsmGetSet = mLocListenerGsm.getDataGSM();
	}

	@Override
	protected Void doInBackground(Void... params) {
		//new SendThread(datosGetSet, datosGsmGetSet, SERVER_IP, SERVER_PORT, TIME_REPORT).start();
		Timer timer = new Timer();
		SendTask task = new SendTask(contexto, datosGetSet, datosGsmGetSet, SERVER_IP, SERVER_PORT);
		timer.scheduleAtFixedRate(task, 5000, TIME_REPORT*1000);
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		//mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
		Log.d("onPostExecute()", "Ejecutado");
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
		Log.d("ASYNCTASK -- onCancelled()", "Cancelado");
	}

	@Override
	protected void onCancelled(Void result) {
		// TODO Auto-generated method stub
		super.onCancelled(result);
		Log.d("ASYNCTASK -- onCancelled(void result)", "Cancelado");
		
	}
	
	

}
