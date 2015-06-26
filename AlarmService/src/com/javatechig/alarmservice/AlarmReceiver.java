package com.javatechig.alarmservice;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.widget.Toast;

import com.javatechig.classes.GpsGetset;
import com.javatechig.classes.GsmGetSet;
import com.javatechig.classes.MiLocListenerGsm;
import com.javatechig.classes.MyLocListener;

public class AlarmReceiver extends BroadcastReceiver {
	String msj;
	
	static final int SINC_TIME = 15;
	static final int SERVER_PORT = 21022;
	static final String SERVER_IP = "107.172.12.220";	//190.223.20.12
	
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	
	LocationManager mLocManager;
	MyLocListener mLocListener;
	MiLocListenerGsm mLocListenerGsm;

    @Override
    public void onReceive(Context context, Intent intent) {
    	
    	/*start LocManager*/
        mLocManager = (LocationManager)context.getSystemService(Context.LOCATION_SERVICE);
		TelephonyManager mTManager = (TelephonyManager)context.getSystemService(Context.TELEPHONY_SERVICE);
		mLocListener = new MyLocListener(mTManager.getDeviceId(), context);
		mLocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, mLocListener);
		GsmCellLocation gsmCellLoc = (GsmCellLocation)mTManager.getCellLocation();
		mLocListenerGsm = new MiLocListenerGsm( mTManager, gsmCellLoc);
		datosGetSet = mLocListener.getDataGPS();
		datosGsmGetSet = mLocListenerGsm.getDataGSM();
		
    	/*Hora del sistema*/
    	long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/ HH:mm:ss",Locale.US);
		
        // For our recurring task, we'll just display a message
		//Toast.makeText(context, "I'm running", Toast.LENGTH_SHORT).show();
		msj = "Im Running -- " + sdf.format(date).trim();
        System.out.println(msj);
        
        
        //delay de inicio y envio de trama
        new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.e("SERVICIO GEOBOOT", "After " + SINC_TIME + " secs");
				mLocManager.removeUpdates(mLocListener);
				new Thread(new Runnable() {
					@Override
					public void run() {
						sendFrame();
						System.out.println("Enviado");
					}
				}).start();
			}
		}, SINC_TIME*1000);	
        
        /*new Thread(new Runnable() {
			@Override
			public void run() {
				byte[] enviaData = new byte[1024];
				enviaData = msj.getBytes();
				// TODO Auto-generated method stub
				try {
					InetAddress ip = InetAddress.getByName(SERVER_IP);
					DatagramSocket clSocket = new DatagramSocket();
					DatagramPacket dgPacket = new DatagramPacket(enviaData, enviaData.length, ip, SERVER_PORT);
					clSocket.send(dgPacket);
					clSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}).start();*/
        
        
        
    }
    
    //envio de trama
    private void sendFrame(){
		byte[] enviaData = new byte[1024];
		String tramaGPS = "" + datosGetSet.getTramaGps();
		String tramaGSM = "" + datosGsmGetSet.getTramaGsm();
		
		datosGetSet.setTramaGps("null");
		
		Log.d("abel--MiLocManager--GPS", tramaGPS);
		Log.d("abel- miLocManager--GSM", tramaGSM);
		
		if(tramaGPS.trim().startsWith("$$A")) enviaData = tramaGPS.getBytes();
		else if(tramaGSM.trim().startsWith("**V")) enviaData = tramaGSM.getBytes();
		
		try {
			InetAddress ip = InetAddress.getByName(SERVER_IP);
			DatagramSocket clSocket = new DatagramSocket();
			DatagramPacket dgPacket = new DatagramPacket(enviaData, enviaData.length, ip, SERVER_PORT);
			clSocket.send(dgPacket);
			clSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
