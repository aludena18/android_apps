package com.alghome.loclistener;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;
import com.alghome.datasend.SendThread;

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
	protected Void doInBackground(Void... params) {
		mLocManager = (LocationManager)contexto.getSystemService(Context.LOCATION_SERVICE);
		mTManager = (TelephonyManager)contexto.getSystemService(Context.TELEPHONY_SERVICE);
		gsmCellLoc = (GsmCellLocation)mTManager.getCellLocation();
		
		mLocListener = new MiLocListener(mTManager.getDeviceId(), contexto);
		mLocListenerGsm = new MiLocListenerGsm( mTManager, gsmCellLoc);
		
		datosGetSet = mLocListener.getDataGPS();
		datosGsmGetSet = mLocListenerGsm.getDataGSM();
		
		new SendThread(datosGetSet, datosGsmGetSet, SERVER_IP, SERVER_PORT, TIME_REPORT).start();
		
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
	}

}
