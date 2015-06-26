package com.javatechig.classes;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

public class MiLocListenerGsm {
	GsmGetSet gsmData = new GsmGetSet();
	String imei;
	String opName;
	String mcc;
	String mnc;
	String lac;
	String cellId;
	
	TelephonyManager tlManager;
	GsmCellLocation gsmCellLoc;
	SimpleDateFormat sdf;
	
	private long date;
	private String frame;
	
	public MiLocListenerGsm(TelephonyManager tm, GsmCellLocation gsmCell){
		sdf = new SimpleDateFormat("yyMMddHHmmss",Locale.US);
		tlManager = tm;
		gsmCellLoc = gsmCell;
		listener();
	}
	
	public void listener(){
		TimerTask task = new TimerTask() {
			public void run() {

				date = System.currentTimeMillis();
				
				gsmData.setTime(sdf.format(date).trim());
				gsmData.setImei(tlManager.getDeviceId());
				gsmData.setOpName(tlManager.getNetworkOperatorName().trim());
				gsmData.setMcc(tlManager.getNetworkOperator().substring(0, 3));
				gsmData.setMnc(tlManager.getNetworkOperator().substring(3));
				gsmData.setLac(Integer.toString(gsmCellLoc.getLac()).trim());
				gsmData.setCellId(Integer.toString(gsmCellLoc.getCid()).trim());

				//FrameBuilderGsm fbGsm = new FrameBuilderGsm(gsmData);
				//gsmData.setTramaGsm(fbGsm.tramaGSM());
				buildFrame();
				gsmData.setTramaGsm(frame);
				
			}
		};
		new Timer().schedule(task, 10, 1000);
	}
	
	public GsmGetSet getDataGSM(){
		return gsmData;
	}
	
	private void buildFrame(){
		frame = "**V," + gsmData.getImei() + "," +		//imei
				gsmData.getOpName()	+ "," +				//command
				//""	+ "," +								//identifier udp
				gsmData.getEvento() + "," +				//event code
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
	}

}
