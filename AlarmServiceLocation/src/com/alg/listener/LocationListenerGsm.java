package com.alg.listener;

import java.text.SimpleDateFormat;
import java.util.Locale;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;

import com.alg.getset.GsmData;

public class LocationListenerGsm {
	GsmData gsmData;
	TelephonyManager tlManager;
	GsmCellLocation gsmCellLocation;
	SimpleDateFormat sdf;
	String cellLocation;

	private long date;

	public LocationListenerGsm(TelephonyManager tm, String ev){
		sdf = new SimpleDateFormat("yyMMdd HH:mm:ss",Locale.US);
		gsmData = new GsmData();
		gsmData.setEvent(ev);
		tlManager = tm;
		Log.d("LocationListenerGsm", "iniciando GsmCellLocation");
		gsmCellLocation = (GsmCellLocation)tm.getCellLocation();
		System.out.println("" + gsmCellLocation);
	}

	public GsmData getGsmData(){
		date = System.currentTimeMillis();
		gsmData.setTime(sdf.format(date).trim());
		gsmData.setImei(tlManager.getDeviceId());
		if(gsmCellLocation!=null){
			gsmData.setOpName(tlManager.getNetworkOperatorName().trim());
			gsmData.setMcc(tlManager.getNetworkOperator().substring(0, 3));
			gsmData.setMnc(tlManager.getNetworkOperator().substring(3));
			gsmData.setLac(Integer.toString(gsmCellLocation.getLac()).trim());
			gsmData.setCellId(Integer.toString(gsmCellLocation.getCid()).trim());
		}
		buildFrame();
		return gsmData;
	}

	public void buildFrame(){
		String frame = "**V," + gsmData.getImei() + "," +		//imei
				gsmData.getOpName()	+ "," +						//command
				"0001"	+ "," +									//identifier udp
				gsmData.getEvent() + "," +				//event code
				"" + "," +								//latitud
				" " + "," +								//longitud
				gsmData.getTime() + "," +				//date time
				""	+ "," +								//gps status
				""	+ "," +								//number satellites
				""	+ "," +								//gsm signal
				"9999" + "," +							//speed
				"" + "," +								//heading
				""	+ "," +								//HDOP
				gsmData.getMcc() + "," +				//altitude
				gsmData.getMnc()	+ "," +				//mileage
				gsmData.getLac()	+ "," +				//runtime
				gsmData.getCellId()	+ "," +				//Base ID
				""	+ "," +									//Sate IO
				""	+ "," +									//AD
				""	+ "," +									//RFID
				""	+ "," +									//Customize Data
				""	+ "," +									//Protocol Version
				""	+ "," +									//Fuel Percentage
				""	+ "," +									//Temperature
				"*";
		gsmData.setFrameGsm(frame);
		System.out.println(frame);
	}


}
