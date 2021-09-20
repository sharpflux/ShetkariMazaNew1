package com.sharpflux.shetkarimaza.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        //Utils.LogI("BootReceiver", "BootReceiver received!");
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Do my stuff
        }
    }
}