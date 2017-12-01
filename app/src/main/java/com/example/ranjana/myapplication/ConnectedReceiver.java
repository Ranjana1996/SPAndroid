package com.example.ranjana.myapplication;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.ranjana.myapplication.BluetoothService;
import com.example.ranjana.myapplication.LoginActivity;

public class ConnectedReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        BluetoothDevice device = (BluetoothDevice)intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        Log.v("BR-DEVICE", device.getName());
        if(device != null && device.getName().equals("PillBottle"))
        {
            Intent i = new Intent(context, BluetoothService.class);
            i.putExtra("device", device);
            Log.v("BR-DEVICE", device.getName());
            context.startService(i);
        }
    }
}
