package com.alghome.handler;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class BgTask implements Runnable {
  private final static String TAG = BgTask.class.getSimpleName();
  private Handler uiHandler;

  public BgTask(Handler h) {
    uiHandler = h;
  }

  @Override
  public void run() {
    Log.i(TAG, "starts background task running, will take 5 sec to process");
    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      Log.i(TAG, "interrupted");
      e.printStackTrace();
    }
    Message m = uiHandler.obtainMessage();
    Bundle b = m.getData();
    b.putString("key", "Background Task notify: " + getDateTimeNow());
    uiHandler.sendMessage(m);
    Log.i(TAG, "completed running background task");
  }

  private String getDateTimeNow() {
    return new SimpleDateFormat("HH:mm:ss MM/dd/yyyy").format(new Date());
  }
}
