package com.alghome.builder;

import com.alghome.datagetset.GpsGetset;

public class FrameBuilder {
	GpsGetset dataGetSet;
	
	private String imei;
	private String latitude;
	private String longitude;
	private String datetime;
	private String speed;
	private String heading;
	private String altitude;
	
	private String frame;
	
	
	public FrameBuilder(GpsGetset ggs){
		dataGetSet = ggs;
	}
	

}
