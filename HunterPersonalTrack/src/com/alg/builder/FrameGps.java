package com.alg.builder;

import com.alg.getset.GpsData;

public class FrameGps {
	GpsData gpsData;
	
	public FrameGps(GpsData gpd){
		gpsData = gpd;
	}
	
	public String frameGps(){
		String frame = "$$A123," + gpsData.getImei() + "," +		//imei
				"AAA"	+ "," +										//command
				"0001"	+ "," +									//identifier udp
				gpsData.getEvent()	+ "," +							//event code
				gpsData.getLatitude() + "," +			//latitud
				gpsData.getLongitude() + "," +			//longitud
				gpsData.getTime() + "," +				//date time
				"A"	+ "," +								//gps status
				"8"	+ "," +								//number satellites
				"19"	+ "," +							//gsm signal
				gpsData.getSpeed() + "," +				//speed
				gpsData.getBearing() + ","	+			//heading
				"1.0"	+ "," +							//HDOP
				gpsData.getAltitude() + "," +			//altitude
				"0"	+ "," +								//mileage
				"0"	+ "," +								//runtime
				"000|0|0000|0000"	+ "," +					//Base ID
				"0000"	+ "," +								//Sate IO
				"0000|0000|0000|0000|0000"	+ "," +			//AD
				""	+ "," +									//RFID
				//""	+ "," +								//Customize Data
				//""	+ "," +								//Protocol Version
				//""	+ "," +								//Fuel Percentage
				//""	+ "," +								//Temperature
				"*12";
		return frame;
	}

}
