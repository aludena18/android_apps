package com.alghome.datasend;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.TimerTask;

import com.alghome.datagetset.GpsGetset;
import com.alghome.datagetset.GsmGetSet;

import android.util.Log;

public class SendTask extends TimerTask{
	GpsGetset datosGetSet;
	GsmGetSet datosGsmGetSet;
	byte[] enviaData = new byte[1024];
	String ipNumber;
	int port;
	
	public SendTask(GpsGetset gpsG, GsmGetSet gsmG, String ipN, int p){
		datosGetSet = gpsG;
		datosGsmGetSet = gsmG;
		ipNumber = ipN;
		port = p;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
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

}
