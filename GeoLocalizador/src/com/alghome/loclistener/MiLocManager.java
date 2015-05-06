package com.alghome.loclistener;

import com.alghome.datagetset.GpsGetset;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.text.InputFilter.LengthFilter;
import android.widget.Toast;

public class MiLocManager extends AsyncTask<Void, Void, Void>{
	MiLocListener mLocListener;
	LocationManager mLocManager;
	Context contexto;
	GpsGetset dataGps;
	
	public MiLocManager(Context ctx){
		contexto = ctx;
	}

	@Override
	protected Void doInBackground(Void... params) {
		mLocListener = new MiLocListener();
		mLocManager = (LocationManager)contexto.getSystemService(Context.LOCATION_SERVICE);
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		dataGps = new GpsGetset();
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
		Toast.makeText(contexto, dataGps.getMensaje(), Toast.LENGTH_SHORT).show();
	}
	

}
