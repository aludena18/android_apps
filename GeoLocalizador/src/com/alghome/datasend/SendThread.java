package com.alghome.datasend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Timer;
import java.util.TimerTask;

import android.util.Log;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;

public class SendThread extends Thread{
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	byte[] enviaData = new byte[1024];
	String ipNumber;
	int port;
	long timeReport;
	
	public SendThread(GpsGetset gpsG, GsmGetSet gsmG, String ipN, int p, long n){
		datosGetSet = gpsG;
		datosGsmGetSet = gsmG;
		ipNumber = ipN;
		port = p;
		timeReport = n;
	}

	@Override
	public void run() {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				String tramaGPS = "" + datosGetSet.getTramaGps();
				String tramaGSM = "" + datosGsmGetSet.getTramaGsm();
				
				datosGetSet.setTramaGps("null");
				//datosGsmGetSet.setTramaGsm("null");
				
				Log.d("abel--MiLocManager--GPS", tramaGPS);
				Log.d("abel- miLocManager--GSM", tramaGSM);
				
				if(tramaGPS.trim().startsWith("$$A")) enviaData = tramaGPS.getBytes();
				else if(tramaGSM.trim().startsWith("**V")) enviaData = tramaGSM.getBytes();
				
				try {
					InetAddress ip = InetAddress.getByName(ipNumber);
					DatagramSocket clSocket = new DatagramSocket();
					DatagramPacket dgPacket = new DatagramPacket(enviaData, enviaData.length, ip, port);
					clSocket.send(dgPacket);
					clSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		};
		timer.scheduleAtFixedRate(task, 5000, timeReport*1000);
	}

}
