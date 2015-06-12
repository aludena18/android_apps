package com.alghome.loclistener;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;

import com.alghome.builder.FrameBuilderGsm;
import com.alghome.datagetset.GsmGetSet;

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
	
	public MiLocListenerGsm(TelephonyManager tm, GsmCellLocation gsmCell){
		tlManager = tm;
		gsmCellLoc = gsmCell;
		listener();
	}
	
	public void listener(){
		TimerTask task = new TimerTask() {
			public void run() {
				gsmData.setImei(tlManager.getDeviceId());
				gsmData.setOpName(tlManager.getNetworkOperatorName().trim());
				gsmData.setMcc(tlManager.getNetworkOperator().substring(0, 3));
				gsmData.setMnc(tlManager.getNetworkOperator().substring(3));
				gsmData.setLac(Integer.toString(gsmCellLoc.getLac()).trim());
				gsmData.setCellId(Integer.toString(gsmCellLoc.getCid()).trim());

				long date = System.currentTimeMillis();
				SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd/ HH:mm:ss",Locale.US);
				gsmData.setTime(sdf.format(date).trim());
				
				FrameBuilderGsm fbGsm = new FrameBuilderGsm(gsmData);
				gsmData.setTramaGsm(fbGsm.tramaGSM());
				
			}
		};
		new Timer().schedule(task, 10, 1000);
	}
	
	public GsmGetSet getDataGSM(){
		return gsmData;
	}
	

}
