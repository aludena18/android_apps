package com.alg.geolocalizadorgsm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	TextView tvPosicion, tvDatosPhone;
	Button btLocation;
	String lat,lon,alt,vel,time,dir;
	
	TelephonyManager tlfManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvPosicion = (TextView)findViewById(R.id.txvMensaje);
		tvDatosPhone = (TextView)findViewById(R.id.txvDatosFono);
		btLocation = (Button)findViewById(R.id.btnLocation);
		
		btLocation.setOnClickListener(this);
		
		tlfManager = (TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
		
		String operatorName = tlfManager.getNetworkOperatorName();
		String operatorCode = tlfManager.getNetworkOperator();
		String imei = tlfManager.getDeviceId();
		String cellLoc = tlfManager.getCellLocation().toString();
		
		String simOperator = tlfManager.getSimOperator();
		
		GsmCellLocation gsmCelLoc = (GsmCellLocation)tlfManager.getCellLocation();
		String lac = Integer.toString(gsmCelLoc.getLac());
		String cid = Integer.toString(gsmCelLoc.getCid());
		
		long date = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm yyyyMMdd",Locale.US);
		String dateString = sdf.format(date);
		
		tvDatosPhone.setText(	"OP NAME = " + operatorName + "\n" +
								"OP CODE = " + operatorCode + "\n" +
								"SIM OP = " + simOperator + "\n" +
								"IMEI = " + imei + "\n" +
								"CELL LOC = " + cellLoc + "\n" +
								"CELL ID = " + cid + "\n" +
								"LAC = " + lac + "\n" +
								"Fecha Hora = " + dateString + "\n"
				);
		
		
		LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
		
		
		/*OPEN CELL ID*/
		
		//--------------------------

		// Define a listener that responds to location updates
		LocationListener locationListener = new LocationListener() {
		    public void onLocationChanged(Location location) {
		      // Called when a new location is found by the network location provider.
		      makeUseOfNewLocation(location);
		    }
		    
			public void onStatusChanged(String provider, int status, Bundle extras) {}

		    public void onProviderEnabled(String provider) {
		    	tvPosicion.setText("Proveedor Activado");
		    }

		    public void onProviderDisabled(String provider) {
		    	tvPosicion.setText("Proveedor Desactivado");
		    }
		  };

		// Register the listener with the Location Manager to receive location updates
		locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
	}

	
	
	
	private void makeUseOfNewLocation(Location location) {
		tvPosicion.setText("Latitud = " + location.getLatitude() + "\n" +
							"Latitud = " + location.getLatitude() + "\n" +
							"Hora = " + location.getTime() + "\n"
							);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
		switch(v.getId()){
		case R.id.btnLocation:
			Log.d("ALG -- MAINACTIVITY", "Boton presionado");
			new Thread(new Runnable() {
				@Override
				public void run() {
					urlMensaje();
				}
			}).start();
			break;
			
		default:
			break;
		}
	}




	private void urlMensaje() {
		// TODO Auto-generated method stub
		URL urlConsulta = null;
		InputStreamReader isrConsulta = null;
		BufferedReader br = null;
		StringBuffer strBuffer = new StringBuffer();
		String linea;
		String urlPagina = "http://opencellid.org/cell/get?key=bb170ece-37f4-4f48-9b80-17812af4fb1d&mcc=716&mnc=6&lac=2200&cellid=406062&format=json";
		
		try {
			urlConsulta = new URL(urlPagina);
			isrConsulta = new InputStreamReader(urlConsulta.openStream());
			br = new BufferedReader(isrConsulta);
			
			while((linea = br.readLine())!=null){
				strBuffer.append(linea);
			}
			
			br.close();
			isrConsulta.close();
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Log.d("URL -- MAIN ACTIVITY", strBuffer.toString());
	}
	
	
}
