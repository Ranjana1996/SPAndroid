package com.example.ranjana.myapplication;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.CharBuffer;

import static android.content.ContentValues.TAG;

public class WifiService extends Service {

    public static String WifiData = "Wifi Data";
    private String ip = "192.168.43.153";
    private String TAG = "WifiService";
    private Thread t;

    public WifiService()
    {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        final String data = intent.getStringExtra(WifiData);
        Log.i(TAG, data);

        t = new Thread(){
            @Override
            public void run() {
                Log.i(TAG, "started thread");
                sendData(data);
                WifiService.this.stopSelf();
            }
        };
        t.start();

        return START_STICKY;
    }

    private void sendData(String data)
    {
        try {
            Socket client = new Socket(ip, 9000);
            PrintStream output = new PrintStream(client.getOutputStream());
            output.print(data);
            BufferedReader input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            String response = input.readLine();
            input.close();
            output.close();
            client.close();
            Log.i(TAG, "Response : " + response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
