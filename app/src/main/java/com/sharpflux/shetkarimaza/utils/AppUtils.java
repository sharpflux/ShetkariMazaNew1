package com.sharpflux.shetkarimaza.utils;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.text.TextUtils;


public class AppUtils {


    public class LocationConstants {
        public static final int SUCCESS_RESULT = 0;

        public static final int FAILURE_RESULT = 1;

        public static final String RECEIVER = "RECEIVER";

        public static final String RESULT_DATA_KEY = "RESULT_DATA_KEY";

        public static final String LOCATION_DATA_EXTRA = "LOCATION_DATA_EXTRA";
        public static final String LOCATION_DATA_AREA = "LOCATION_DATA_AREA";
        public static final String LOCATION_DATA_CITY = "LOCATION_DATA_CITY";
        public static final String LOCATION_DATA_STREET = "LOCATION_DATA_STREET";
        public static final String LOCATION_LATITUDE ="LOCATION_LATI";
        public static final String LOCATION_LONGI="LOCATION_LONGI";
        public static final String LOCATION_STATE="LOCATION_STATE";
        public static final String LOCATION_POSTAL_CODE="LOCATION_CODE";

    }


    public static boolean hasLollipop() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean isLocationEnabled(Context context) {
        int locationMode = 0;
        String locationProviders;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);

            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            return locationMode != Settings.Secure.LOCATION_MODE_OFF;
        } else {
            locationProviders = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);
            return !TextUtils.isEmpty(locationProviders);
        }
    }

}
