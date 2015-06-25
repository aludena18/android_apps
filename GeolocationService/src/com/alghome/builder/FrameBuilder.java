package com.alghome.builder;

import com.alghome.datagetset.GpsGetset;

public class FrameBuilder {
	GpsGetset dataGetSet;
	private String frame;

	public FrameBuilder(GpsGetset ggs){
		dataGetSet = ggs;
	}

	public String tramaGPS(){
		frame = "$$A123," + dataGetSet.getImei() + "," +		//imei
				"AAA"	+ "," +									//command
				"0001"	+ "," +									//identifier udp
				dataGetSet.getEvento()	+ "," +				//event code
				dataGetSet.getLatitud() + "," +				//latitud
				dataGetSet.getLongitud() + "," +			//longitud
				dataGetSet.getFechayhora() + "," +			//date time
				"A"	+ "," +									//gps status
				"10"	+ "," +									//number satellites
				"19"	+ "," +									//gsm signal
				dataGetSet.getVelocidad() + "," +			//speed
				dataGetSet.getGiro() + ","	+				//heading
				"1.0"	+ "," +									//HDOP
				dataGetSet.getAltitud() + "," +				//altitude
				"0"	+ "," +									//mileage
				"0"	+ "," +									//runtime
				"716|6|045E|0B1A"	+ "," +									//Base ID
				"0000"	+ "," +									//Sate IO
				"0000|0000|0000|0A2D|0BAA"	+ "," +									//AD
				""	+ "," +									//RFID
				//""	+ "," +									//Customize Data
				//""	+ "," +									//Protocol Version
				//""	+ "," +									//Fuel Percentage
				//""	+ "," +									//Temperature
				"*12";

		return frame;
	}

}
