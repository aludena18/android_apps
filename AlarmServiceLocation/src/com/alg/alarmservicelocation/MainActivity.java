package com.alg.alarmservicelocation;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.alg.getset.GpsData;
import com.alg.listener.LocationListenerGps;

public class MainActivity extends Activity {

	private AlarmManager alarmMgr;
	private PendingIntent pendingIntent;

	private LocationManager locationManagerMain;
	private LocationListenerGps locListenerGpsMain;
	GpsData gpsDataMain;
	SimpleDateFormat sdf;

	Button btPanic;
	TextView tvDireccion;

	private static long TIME_INTERVAL = 260;
	final static String EVENT_NUMBER = "1";
	
	static final int SERVER_PORT = 13000;
	static final String SERVER_IP = "190.223.20.12";			//VPS=107.172.12.220/21020 ; Servidor Hunter=190.223.20.12/13000

	static final int VPS_SERVER_PORT = 21023;
	static final String VPS_SERVER_IP = "107.172.12.220";
	
	private Handler handler = new Handler();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		System.out.println("onCreate()");

		alarmMgr = (AlarmManager)getSystemService(ALARM_SERVICE);
		Intent alarmIntent = new Intent(MainActivity.this, AlarmBreceiver.class);
		pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, alarmIntent, 0);

		cancel();
		start();

		tvDireccion = (TextView)findViewById(R.id.txvDireccion);
		btPanic = (Button)findViewById(R.id.btnPanic);
		btPanic.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				processButton();
			}
		});

		//FORMATO DE FECHA Y HORA
		sdf = new SimpleDateFormat("yy/MM/dd HH:mm:ss",Locale.US);

		//HANDLER DE ESCUCHA
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				/* do what you need to do */
				foobar(gpsDataMain);
				/* and here comes the "trick" */
				handler.postDelayed(this, 1000);
			}
		};
		handler.postDelayed(runnable, 100);

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.out.println("onDestroy()");
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		System.out.println("onPause()");
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		System.out.println("onRestart()");
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		System.out.println("onResume()");
		btPanic.setEnabled(false);
		locationManagerMain.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locListenerGpsMain);
		tvDireccion.setText("Esperando direccion");
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		System.out.println("onStart()");
		//INICIO DEL MANAGER GPS
		TelephonyManager telManager = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
		locationManagerMain = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
		locListenerGpsMain = new LocationListenerGps(telManager.getDeviceId(), EVENT_NUMBER);
		gpsDataMain = locListenerGpsMain.getGpsData();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		System.out.println("onStop()");
		locationManagerMain.removeUpdates(locListenerGpsMain);
		gpsDataMain.setLatitude(null);
		gpsDataMain.setLongitude(null);
		System.out.println("locationManagerMain removido");
	}

	public void start(){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());

		alarmMgr.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, 1000*TIME_INTERVAL, pendingIntent);
		System.out.println("Alarm Set");
	}

	public void cancel(){
		alarmMgr.cancel(pendingIntent);
		System.out.println("Alarm Canceled");
	}

	public void processButton(){
		//cancel();
		//locationManagerMain.removeUpdates(locListenerGpsMain);
		//System.out.println("locationManagerMain removido");
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				sendFrame();
				//releaseListeners();
			}
		}).start();
	}

	public void foobar(GpsData gpsD){
		if (gpsD.getLatitude() != null && gpsD.getLongitude() != null) {
			try {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				List<Address> list = geocoder.getFromLocation(
						Double.parseDouble(gpsD.getLatitude()), 
						Double.parseDouble(gpsD.getLongitude()), 
						1);
				if (!list.isEmpty()) {
					Address address = list.get(0);
					tvDireccion.setText("Mi direcci—n es: \n"
							+ address.getAddressLine(0)
							+ "\nFecha y hora GPS: " + sdf.format(gpsD.getTimeGps()));

				}

			} catch (IOException e) {
				e.printStackTrace();
			}
			btPanic.setTextColor(Color.YELLOW);
			btPanic.setEnabled(true);
		}
	}
	
	//ENVIO DE DATOS A SERVIDORES
	public void sendFrame(){
		byte[] dataToSend = new byte[1024];
		String frameGPS = "" + gpsDataMain.getFrameGps();

		Log.d("MainActivity--GPS", frameGPS);

		if(frameGPS.trim().startsWith("$$A")){
			dataToSend = frameGPS.getBytes();
			sendToHunter(dataToSend);
			sendToVPS(dataToSend);
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					Toast.makeText(MainActivity.this, "BOTON DE PANICO ENVIADO", Toast.LENGTH_LONG).show();
				}
			});
		}
	}
	
	//ENVIO DE DATOS AL SERVIDOR DE HUNTER
	public void sendToHunter(byte[] d2s){
		try {
			InetAddress ip = InetAddress.getByName(SERVER_IP);
			DatagramSocket clSocket = new DatagramSocket();
			DatagramPacket dgPacket = new DatagramPacket(d2s, d2s.length, ip, SERVER_PORT);
			clSocket.send(dgPacket);
			clSocket.close();
			System.out.println("Enviado : " + new String(d2s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//ENVIO DE DATOS AL SERVIDOR DE LA VPS
	public void sendToVPS(byte[] d2s){
		try {
			DatagramSocket clSocket2 = new DatagramSocket();
			InetAddress ip2 = InetAddress.getByName(VPS_SERVER_IP);
			DatagramPacket dgPacket2 = new DatagramPacket(d2s, d2s.length, ip2, VPS_SERVER_PORT);
			clSocket2.send(dgPacket2);
			clSocket2.close();
			System.out.println("Enviado a VPS: " + new String(d2s));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
