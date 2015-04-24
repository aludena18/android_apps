package com.expocode.android.mygps;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {

	TextView messageTextView;
	TextView messageTextView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		messageTextView = (TextView) findViewById(R.id.message_id);
		messageTextView2 = (TextView) findViewById(R.id.message_id2);

		/* Use the LocationManager class to obtain GPS locations */
		LocationManager mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		MyLocationListener mlocListener = new MyLocationListener();
		mlocListener.setMainActivity(this);
		mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				(LocationListener) mlocListener);

		messageTextView.setText("LocationListener agregado");
		messageTextView2.setText("");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	public void setLocation(Location loc) {
		//Obtener la direcci—n de la calle a partir de la latitud y la longitud 
		if (loc.getLatitude() != 0.0 && loc.getLongitude() != 0.0) {
			try {
				Geocoder geocoder = new Geocoder(this, Locale.getDefault());
				List<Address> list = geocoder.getFromLocation(
						loc.getLatitude(), loc.getLongitude(), 1);
				if (!list.isEmpty()) {
					Address address = list.get(0);
					messageTextView2.setText("Mi direcci—n es: \n"
							+ address.getAddressLine(0));
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/* Class My Location Listener */
	public class MyLocationListener implements LocationListener {
		MainActivity mainActivity;

		public MainActivity getMainActivity() {
			return mainActivity;
		}

		public void setMainActivity(MainActivity mainActivity) {
			this.mainActivity = mainActivity;
		}

		@Override
		public void onLocationChanged(Location loc) {
			// Este mŽtodo se ejecuta cada vez que el GPS recibe nuevas coordenadas
			// debido a la detecci—n de un cambio de ubicacion
			loc.getLatitude();
			loc.getLongitude();
			String Text = "Mi ubicaci—n actual es: " + "\n Lat = "
					+ loc.getLatitude() + "\n Long = " + loc.getLongitude();
			messageTextView.setText(Text);
			this.mainActivity.setLocation(loc);
		}

		@Override
		public void onProviderDisabled(String provider) {
			// Este mŽtodo se ejecuta cuando el GPS es desactivado
			messageTextView.setText("GPS Desactivado");
		}

		@Override
		public void onProviderEnabled(String provider) {
			// Este mŽtodo se ejecuta cuando el GPS es activado
			messageTextView.setText("GPS Activado");
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// Este mŽtodo se ejecuta cada vez que se detecta un cambio en el
			// status del proveedor de localizaci—n (GPS)
			// Los diferentes Status son:
			// OUT_OF_SERVICE -> Si el proveedor esta fuera de servicio
			// TEMPORARILY_UNAVAILABLE -> Temp˜ralmente no disponible pero se
			// espera que este disponible en breve
			// AVAILABLE -> Disponible
		}

	}/* End of Class MyLocationListener */

}
