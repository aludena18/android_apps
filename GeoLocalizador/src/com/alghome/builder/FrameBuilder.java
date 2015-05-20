package com.alghome.builder;

import com.alghome.datagetset.GpsGetset;

public class FrameBuilder {
	GpsGetset dataGetSet;
	private String frame;

	public FrameBuilder(GpsGetset ggs){
		dataGetSet = ggs;
	}

	public String tramaGPS(){
		frame = "$$A," + dataGetSet.getImei() + "," +		//imei
				" "	+ "," +									//command
				" "	+ "," +									//event codde
				dataGetSet.getLatitud() + "," +				//latitud
				dataGetSet.getLongitud() + "," +			//longitud
				dataGetSet.getFechayhora() + "," +			//date time
				" "	+ "," +									//gps status
				" "	+ "," +									//number satellites
				" "	+ "," +									//gsm signal
				dataGetSet.getVelocidad() + "," +			//speed
				dataGetSet.getGiro() + ","	+				//heading
				" "	+ "," +									//HDOP
				dataGetSet.getAltitud() + "," +				//altitude
				" "	+ "," +									//mileage
				" "	+ "," +									//runtime
				" "	+ "," +									//Base ID
				" "	+ "," +									//Sate IO
				"*";

		return frame;
	}

}
