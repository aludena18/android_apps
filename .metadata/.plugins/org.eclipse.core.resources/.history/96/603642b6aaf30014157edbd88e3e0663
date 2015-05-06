package com.home.learnhandler;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

/*
 * instead of creating Thread and Looper, android provides HandlerThread which already contains a msg queue
 * to bind the existing looper to a handler:
 * 
 * Handler h = new Handler(myThreadHandler.getLooper());
 * 
 */
//one of the two ways to create a thread, extend Thread / implement Runnable
public class WorkerThread extends Thread {
  private final static String TAG = WorkerThread.class.getSimpleName();
  // to send msgs to the msg queue
  private Handler workerHandler;
  // to update the UI thread
  private Handler uiHandler;

  public WorkerThread(Handler h) {
    uiHandler = h;
  }

  // what needs to be executed when thread starts
  @Override
  public void run() {
    // Thread by default doesnt have a msg queue, to attach a msg queue to this thread
    Looper.prepare();
    // this will bind the Handler to the msg queue
    // notice that msg queue is FIFO, if u send 2 runable objects 1 after the other, second 1 will wait till first one finish
    workerHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
        Bundle u = msg.getData();
        Log.i(TAG, "received a msg to worker thread: " + u.getString("key"));
        Message m = uiHandler.obtainMessage();
        Bundle uB = m.getData();
        uB.putString("key", "changed from worker thead message handler: " + getDateTimeNow());
        m.setData(uB);
        // updating UI thread
        uiHandler.sendMessage(m);
      }
    };
    // handles msgs/runnables receive to msgqueue, this will start a loop that listens msg receiving
    Looper.loop();
  }

  public Handler getHandlerToMsgQueue() {
    return workerHandler;
  }

  private String getDateTimeNow() {
    return new SimpleDateFormat("HH:mm:ss MM/dd/yyyy").format(new Date());
  }
}