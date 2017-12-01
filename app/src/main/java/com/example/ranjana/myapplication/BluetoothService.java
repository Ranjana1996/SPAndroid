package com.example.ranjana.myapplication;

import android.app.Service;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class BluetoothService extends Service {
    private BluetoothDevice device;
    private Thread inputThread;
    private Thread outputThread;
    private boolean isRunning;

    public BluetoothService() {
        inputThread = new Thread(){
            @Override
            public void run() {
//                while(isRunning)
                {

                }
                Log.v("THREAD", "ENDED1");
            }
        };

        outputThread = new Thread(){
            @Override
            public void run() {
//                while(isRunning)
                {

                }
                Log.v("THREAD", "ENDED2");
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        BluetoothDevice d = (BluetoothDevice) intent.getParcelableExtra("device");
        isRunning = true;
        if(d != null)
            device = d;

        inputThread.start();
        outputThread.start();

        return START_REDELIVER_INTENT;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        isRunning = false;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
