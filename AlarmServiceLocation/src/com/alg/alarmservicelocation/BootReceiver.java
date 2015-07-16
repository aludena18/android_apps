package com.alg.alarmservicelocation;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class BootReceiver extends BroadcastReceiver{
	private final long ALARM_INTERVAL = 260;

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		
		//LANZAR LA ALARMA
		if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")) {
			AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
			Intent alarmIntent = new Intent(context, AlarmBreceiver.class);
			PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);
			
			//manager.cancel(pendingIntent);
			manager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, 10000, 1000*ALARM_INTERVAL, pendingIntent);

			Toast.makeText(context, "Alarm Set", Toast.LENGTH_LONG).show();
			System.out.println("Alarm Set");
		}
		
		//LANZAR SERVICIO
		Intent serviceIntent = new Intent(context, ServiceAlarm.class);
		context.startService(serviceIntent);
	}

}
