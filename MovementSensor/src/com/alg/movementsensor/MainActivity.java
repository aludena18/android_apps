package com.alg.movementsensor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

public class MainActivity extends Activity  implements SensorEventListener{
	private long last_update = 0, last_movement = 0;
	private float prevX = 0, prevY = 0, prevZ = 0;
	private float curX = 0, curY = 0, curZ = 0;
	
	private SensorManager sensorManager;
	private Sensor senAccelerometer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        senAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, senAccelerometer , SensorManager.SENSOR_DELAY_NORMAL);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		sensorManager.unregisterListener(this);
		super.onPause();
		
	}


	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		sensorManager.registerListener(this, senAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
	}


	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		long current_time = event.timestamp;
        
		curX = event.values[0];
		curY = event.values[1];
		curZ = event.values[2];
		
		if (prevX == 0 && prevY == 0 && prevZ == 0) {
		    last_update = current_time;
		    last_movement = current_time;
		    prevX = curX;
		    prevY = curY;
		    prevZ = curZ;
		}
		
		
	}
}
