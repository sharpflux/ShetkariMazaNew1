package com.sharpflux.shetkarimaza.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckDeviceIsOnline {

    public static boolean isNetworkConnected(Context c) {



        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();

        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();



        return isConnected;
    }

    public static boolean isWifiConnected(Context c) {



        ConnectivityManager cm =
                (ConnectivityManager)c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();


        boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;



        return isWiFi;
    }
}
