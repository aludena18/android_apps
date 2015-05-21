package com.alghome.builder;

import com.alghome.datagetset.GsmGetSet;

public class FrameBuilderGsm {
	GsmGetSet dataGetSet;
	String frame;
	
	public FrameBuilderGsm(GsmGetSet gsmGS){
		dataGetSet = gsmGS;
	}
	
	public String tramaGSM(){
		frame = "$$V," + dataGetSet.getImei() + "," +		//imei
				" "	+ "," +									//command
				dataGetSet.getOpName() + "," +				//event codde
				dataGetSet.getMcc() + "," +					//latitud
				dataGetSet.getMnc() + "," +					//longitud
				dataGetSet.getTime() + "," +				//date time
				" "	+ "," +									//gps status
				" "	+ "," +									//number satellites
				" "	+ "," +									//gsm signal
				dataGetSet.getLac() + "," +					//speed
				dataGetSet.getCellId() + "," +				//heading
				" "	+ "," +									//HDOP
				" " + "," +									//altitude
				" "	+ "," +									//mileage
				" "	+ "," +									//runtime
				" "	+ "," +									//Base ID
				" "	+ "," +									//Sate IO
				"*";

		return frame;
	}

}
