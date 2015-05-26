package com.example.serviceboot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ReceiverBoot extends BroadcastReceiver{

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		// LANZAR SERVICIO
		Intent serviceIntent = new Intent();
		serviceIntent.setAction("com.example.serviceboot.MyService");
		context.startService(serviceIntent);
		
		// LANZAR ACTIVIDAD
		Intent i = new Intent(context, MainActivity.class);
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		context.startActivity(i);

	}

}
