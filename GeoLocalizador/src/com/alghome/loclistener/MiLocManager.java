package com.alghome.loclistener;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;

public class MiLocManager extends AsyncTask<Void, Void, Void>{
	MiLocListenerGsm mLocListenerGsm;
	MiLocListener mLocListener;
	LocationManager mLocManager;
	TelephonyManager mTManager;
	GsmCellLocation gsmCellLoc;
	Context contexto;
	
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	
	Handler workerHandler;
	Handler uiHandler;

	InetAddress ipNumber;
	byte[] enviaData = new byte[1024];
	byte[] reciveData = new byte[1024];

	public MiLocManager(Context ctx){
		contexto = ctx;
		
	}



	@Override
	protected Void doInBackground(Void... params) {
		mLocManager = (LocationManager)contexto.getSystemService(Context.LOCATION_SERVICE);
		mTManager = (TelephonyManager)contexto.getSystemService(Context.TELEPHONY_SERVICE);
		gsmCellLoc = (GsmCellLocation)mTManager.getCellLocation();
		
		mLocListener = new MiLocListener(mTManager.getDeviceId());
		mLocListenerGsm = new MiLocListenerGsm( mTManager, gsmCellLoc);
		
		datosGetSet = mLocListener.getDataGPS();
		datosGsmGetSet = mLocListenerGsm.getDataGSM();
		
		envioDatos();
		return null;
	}

	@Override
	protected void onPostExecute(Void result) {
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
	}

	
	
	public void envioDatos(){
		new Thread(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Timer timer = new Timer();
				TimerTask task = new TimerTask() {
					@Override
					public void run() {
						String tramaGPS = "" + datosGetSet.getTramaGps();
						String tramaGSM = "" + datosGsmGetSet.getTramaGsm();
						
						Log.d("abel--MiLocManager--GPS", tramaGPS);
						Log.d("abel- miLocManager--GSM", tramaGSM);
						
						if(tramaGPS.trim().startsWith("$$A")) enviaData = tramaGPS.getBytes();
						else if(tramaGPS.trim().startsWith("$$V")) enviaData = tramaGSM.getBytes();
						/*
						try {
							ipNumber = InetAddress.getByName("107.172.12.220");
							DatagramSocket clSocket = new DatagramSocket();
							DatagramPacket dgPacket = new DatagramPacket(enviaData, enviaData.length, ipNumber, 21020);
							clSocket.send(dgPacket);
							clSocket.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						*/
					}
				};
				timer.scheduleAtFixedRate(task, 5000, 30000);
			}
		}).start();

	}

}
