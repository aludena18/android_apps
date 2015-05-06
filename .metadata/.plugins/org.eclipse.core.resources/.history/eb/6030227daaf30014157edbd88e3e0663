package com.home.learnhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
  private static final String TAG = MainActivity.class.getSimpleName();
  private WorkerThread wT;
  private Handler uiHandler;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    // bind the handler to current thread's(UI thread) default looper
    uiHandler = new Handler() {
      // this will handle the notification gets from worker thead
      @Override
      public void handleMessage(Message msg) {
        Bundle b = msg.getData();
        Log.i(TAG, "UI receved notification: " + b.getString("key"));
        ((TextView) findViewById(R.id.tv_info)).setText(b.getString("key"));
      }
    };
    // create a seperate thread
    wT = new WorkerThread(uiHandler);
    // starts the thead
    wT.start();
    // to wait for termination of the worker thead ==> wT.join();
  }

  // this method is bound to the layout.xml : onClick event of a button
  public void clickTestMsg(View v) {
    // now the worker thred is running, we take the handler from it
    Handler workerHandler = wT.getHandlerToMsgQueue();
    // obtain a msg object from global msg pool
    Message m = workerHandler.obtainMessage();
    Bundle b = m.getData();
    b.putString("key", getDateTimeNow());
    Log.i(TAG, "sending msg to worker thread from UI");
    // and pass the msg
    workerHandler.sendMessage(m);
  }

  // this method is bound to the layout.xml : onClick event of a button
  public void clickTestRunnable(View v) {
    Log.i(TAG, "sending background task to worker thread from UI");
    // now the worker thred is running, we take the handler from it
    Handler workerHandler = wT.getHandlerToMsgQueue();
    workerHandler.post(new Runnable() {
      @Override
      public void run() {
        Log.i("Runnable task", "running now.....");
      }
    });
    workerHandler.post(new BgTask(uiHandler));
  }

  private String getDateTimeNow() {
    return new SimpleDateFormat("HH:mm:ss MM/dd/yyyy").format(new Date());
  }
}
