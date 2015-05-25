package com.alghome.builder;

import com.alghome.datagetset.GsmGetSet;

public class FrameBuilderGsm {
	GsmGetSet dataGetSet;
	String frame;
	
	public FrameBuilderGsm(GsmGetSet gsmGS){
		dataGetSet = gsmGS;
	}
	
	public String tramaGSM(){
		frame = "**V," + dataGetSet.getImei() + "," +		//imei
				" "	+ "," +									//command
				dataGetSet.getOpName() + "," +				//event codde
				" " + "," +									//latitud
				" " + "," +									//longitud
				dataGetSet.getTime() + "," +				//date time
				" "	+ "," +									//gps status
				" "	+ "," +									//number satellites
				" "	+ "," +									//gsm signal
				"9999" + "," +								//speed
				" " + "," +									//heading
				" "	+ "," +									//HDOP
				dataGetSet.getMcc() + "," +					//altitude
				dataGetSet.getMnc()	+ "," +					//mileage
				dataGetSet.getLac()	+ "," +					//runtime
				dataGetSet.getCellId()	+ "," +				//Base ID
				" "	+ "," +									//Sate IO
				"*";

		return frame;
	}

}
