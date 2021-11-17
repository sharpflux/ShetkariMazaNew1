package com.sharpflux.shetkarimaza.service;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.PersistableBundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.sharpflux.shetkarimaza.BuildConfig;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.AllSimilarDataActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.adapter.SimilarListAdapter;
import com.sharpflux.shetkarimaza.model.SimilarList;
import com.sharpflux.shetkarimaza.sqlite.dbBuyerFilter;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

public class MyJobService extends JobService {

    private static final String TAG = MyJobService.class.getSimpleName();
    String TalukaId = "", VarityId = "", AvailableMonth = "", QualityId = "",Age = "", ItemTypeId = "", StatesID = "", DistrictId = "",priceids="",ItemName,categoryId="";
    PersistableBundle bundle;
    dbBuyerFilter myDatabaseBuyer;
    dbLanguage mydatabase;
    String currentLanguage,language;

    int id = 1;


    private int messageCount = 0;
    private static Uri alarmSound;
    // Vibration pattern long array
    private final long[] pattern = { 100, 300, 300, 300 };
    private NotificationManager mNotificationManager;

    private NotificationManager notificationManager;
    private NotificationCompat.Builder mBuilder;



    public void showNotification(Context context, String title, String body, Intent intent) {
        notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        int notificationId = 1;
        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

          mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(notificationId, mBuilder.build());
    }



    @Override
    public boolean onStartJob(final JobParameters params) {

        HandlerThread handlerThread = new HandlerThread("SomeOtherThread");
        handlerThread.start();

        myDatabaseBuyer = new dbBuyerFilter(getApplicationContext());
        mydatabase = new dbLanguage(getApplicationContext());

        Cursor cursor = mydatabase.LanguageGet(language);

        if(cursor.getCount()==0) {
            currentLanguage="en";
        }
        else{
            while (cursor.moveToNext()) {
                currentLanguage = cursor.getString(0);
                if( currentLanguage==null)
                {
                    currentLanguage="en";
                }

            }
        }



        Handler handler = new Handler(handlerThread.getLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {


                // DEFAULT ALARM SOUND
                alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

                // INITIALIZE NOTIFICATION MANAGER
                mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Intent resultIntent = new Intent(getApplicationContext(), HomeActivity.class);
                showNotification(getApplicationContext(),"Downloading...", "",resultIntent);


                new Thread(
                        new Runnable() {
                            @Override
                            public void run() {
                                bundle = params.getExtras();
                                if (bundle != null) {
                                    ItemTypeId = bundle.getString("ItemTypeId");
                                    ItemName = bundle.getString("ItemName");
                                    categoryId = bundle.getString("categoryId");
                                }
                                Log.e(TAG, "Running!!!!!!!!!!!!!");
                                SetDynamicDATA(params);
                            }
                        }
                ).start();



            }
        });

        return true;
    }

    private void AssignVariables()
    {
        ItemTypeId = "";
        TalukaId = "";
        VarityId = "";
        QualityId ="";
        StatesID ="";
        DistrictId ="";
        priceids="";
    }



    public void SetDynamicDATA(final JobParameters parameters) {

                    Cursor AVAILABLEMONTHCursor = myDatabaseBuyer.FilterGetByFilterName("AVAILABLEMONTH");
                    Cursor VARIETYCursor = myDatabaseBuyer.FilterGetByFilterName("VARIETY");
                    Cursor QUALITYCursor = myDatabaseBuyer.FilterGetByFilterName("QUALITY");
                    Cursor STATECursor = myDatabaseBuyer.FilterGetByFilterName("STATE");
                    Cursor DISTRICTCursor = myDatabaseBuyer.FilterGetByFilterName("DISTRICT");
                    Cursor TALUKACursor = myDatabaseBuyer.FilterGetByFilterName("TALUKA");
                    Cursor AGECursor = myDatabaseBuyer.FilterGetByFilterName("AGE");

                    priceids=bundle.getString("SortBy");

                    while (AGECursor.moveToNext()) {
                        if (Age == null) {
                            Age = "";
                        }
                        Age = Age + AGECursor.getString(0) + ",";
                    }



                    while (AVAILABLEMONTHCursor.moveToNext()) {
                        if(AvailableMonth==null)
                        {
                            AvailableMonth="";
                        }
                        AvailableMonth = AvailableMonth + AVAILABLEMONTHCursor.getString(0) + ",";
                    }



                    while (VARIETYCursor.moveToNext()) {
                        if(VarityId==null)
                        {
                            VarityId="";
                        }
                        VarityId = VarityId + VARIETYCursor.getString(0) + ",";
                    }
                    while (QUALITYCursor.moveToNext()) {
                        if(QualityId==null)
                        {
                            QualityId="";
                        }
                        QualityId = QualityId + QUALITYCursor.getString(0) + ",";
                    }
                    while (STATECursor.moveToNext()) {
                        StatesID = StatesID + STATECursor.getString(0) + ",";
                    }
                    while (DISTRICTCursor.moveToNext()) {
                        if(DistrictId==null)
                        {
                            DistrictId="";
                        }
                        DistrictId = DistrictId + DISTRICTCursor.getString(0) + ",";
                    }
                    while (TALUKACursor.moveToNext()) {
                        if(TalukaId==null)
                        {
                            TalukaId="";
                        }
                        TalukaId = TalukaId + TALUKACursor.getString(0) + ",";
                    }




            if (TalukaId != null) {
                if (TalukaId.equals(""))
                    TalukaId = "0";
            } else {
                TalukaId = "0";
            }

            if (VarityId != null) {
                if (VarityId.equals(""))
                    VarityId = "0";
            } else {
                VarityId = "0";
            }
            if (QualityId != null) {
                if (QualityId.equals(""))
                    QualityId = "0";
            } else {
                QualityId = "0";
            }
            if (StatesID != null) {
                if (StatesID.equals(""))
                    StatesID = "0";
            } else {
                StatesID = "0";
            }
            if (DistrictId != null) {
                if (DistrictId.equals(""))
                    DistrictId = "0";
            } else {
                DistrictId = "0";
            }
            if (TalukaId != null) {
                if (TalukaId.equals(""))
                    TalukaId = "0";
            } else {
                TalukaId = "0";
            }
            if (priceids != null) {
                if (priceids.equals(""))
                    priceids = "0";
            } else {
                priceids = "0";
            }

            if (AvailableMonth != null) {
                if (AvailableMonth.equals(""))
                    AvailableMonth = "0";
            } else {
                AvailableMonth = "0";
            }

            if (Age != null) {
                if (Age.equals(""))
                    Age = "0";
            } else {
                Age = "0";
            }



        if(ItemTypeId==null)
            {

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(), "ITEM TYPE IS NULL", Toast.LENGTH_SHORT).show();
                    }
                });


                return;
            }



            StringRequest stringRequest = new StringRequest(Request.Method.GET,
                    URLs.URL_REQESTS + "?StartIndex=" + 1 + "&PageSize=" + 100000 +
                            "&ItemTypeId=" + ItemTypeId + "&VarityId=" + VarityId + "&StateId=" + StatesID +
                            "&DistrictId=" + DistrictId + "&QualityId=" + QualityId + "&TalukaId="
                            + TalukaId+"&Language="+currentLanguage+"&SortByRate="+priceids+"&AvailableMonths="+AvailableMonth+ "&Age=" + Age,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {
                                JSONArray obj = new JSONArray(response);


                                int min = 65;
                                int max = 80;

                                Random r = new Random();
                                int randomNumber = r.nextInt(max - min + 1) + min;

                                final String fileName = ItemName+"_"+String.valueOf(randomNumber)+"_"+".xls";


                                //  File sdCard = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
                                File directory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "KisanMaza");

                                //create directory if not exist
                                if (!directory.isDirectory()) {
                                    directory.mkdirs();
                                }

                                //file path
                                File file = new File(directory, fileName);





                              //  Uri uri = FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",file);


                                WorkbookSettings wbSettings = new WorkbookSettings();
                                wbSettings.setLocale(new Locale("en", "EN"));
                                WritableWorkbook workbook;

                                try {

                                    WritableFont titleFont = new WritableFont(WritableFont.TIMES, 10, WritableFont.BOLD, false);
                                    WritableCellFormat titleformat = new WritableCellFormat(titleFont);
                                    workbook = Workbook.createWorkbook(file, wbSettings);
                                    //Excel sheet name. 0 represents first sheet
                                    WritableSheet sheet = workbook.createSheet("FarmerList_", 0);
//
                                    try {

                                        sheet.addCell(new Label(0, 0, "Category Name", titleformat));
                                        sheet.addCell(new Label(1, 0, "Item Name", titleformat)); // column and row
                                        sheet.addCell(new Label(2, 0, "Variety Name", titleformat));
                                        sheet.addCell(new Label(3, 0, "Available Quality", titleformat));
                                        sheet.addCell(new Label(4, 0, "Organic/Non-organic", titleformat));
                                        sheet.addCell(new Label(5, 0, "Organic Certifying Agency and Certificate No.", titleformat));
                                        sheet.addCell(new Label(6, 0, "Available Quantity", titleformat));
                                        sheet.addCell(new Label(7, 0, "Unit", titleformat));
                                        sheet.addCell(new Label(8, 0, "Price per unit", titleformat));
                                        sheet.addCell(new Label(9, 0, "Available(month)", titleformat));
                                        sheet.addCell(new Label(10, 0, "Farm Address", titleformat));
                                        sheet.addCell(new Label(11, 0, "Survey No.", titleformat));
                                        sheet.addCell(new Label(12, 0, "State", titleformat));
                                        sheet.addCell(new Label(13, 0, "District", titleformat));
                                        sheet.addCell(new Label(14, 0, "Taluka", titleformat));
                                        sheet.addCell(new Label(15, 0, "Village", titleformat));
                                        sheet.addCell(new Label(16, 0, "Area in hector", titleformat));
                                        sheet.addCell(new Label(17, 0, "Full Name", titleformat));
                                        sheet.addCell(new Label(18, 0, "Mobile No.", titleformat));
                                        sheet.addCell(new Label(19, 0, "Email.", titleformat));
                                        int j = 1;
                                        for (int i = 0; i < obj.length(); i++) {
                                            JSONObject userJson = obj.getJSONObject(i);
                                            if (!userJson.getBoolean("error")) {

                                               // mBuilder.setContentText(String.valueOf(i)).setProgress(obj.length(),i,false);



                                                sheet.addCell(new Label(0, j,  userJson.getString("CategoryName_EN")));
                                                sheet.addCell(new Label(1, j,  userJson.getString("ItemName")));
                                                sheet.addCell(new Label(2, j,  userJson.getString("VarietyName")));
                                                sheet.addCell(new Label(3, j,  userJson.getString("QualityType")));
                                                sheet.addCell(new Label(4, j,  userJson.getString("Organic")));
                                                sheet.addCell(new Label(5, j,  userJson.getString("OrganicCertiicateNo")));
                                                sheet.addCell(new Label(6, j,  String.valueOf(userJson.getDouble("AvailableQuantity"))));
                                                sheet.addCell(new Label(7, j,  String.valueOf(userJson.getDouble("AvailableQuantity"))));
                                                sheet.addCell(new Label(8, j,  String.valueOf(  userJson.getDouble("PerUnitPrice"))));
                                                sheet.addCell(new Label(9, j,  userJson.getString("AvailableMonths")));
                                                sheet.addCell(new Label(10, j, userJson.getString("FarmAddress")));
                                                sheet.addCell(new Label(11, j, userJson.getString("SurveyNo")));
                                                sheet.addCell(new Label(12, j, userJson.getString("StatesName")));
                                                sheet.addCell(new Label(13, j, userJson.getString("DistrictName")));
                                                sheet.addCell(new Label(14, j, userJson.getString("TalukaName")));
                                                sheet.addCell(new Label(15, j, userJson.getString("VillageName")));
                                                sheet.addCell(new Label(16, j, userJson.getString("Hector")));
                                                sheet.addCell(new Label(17, j, userJson.getString("FullName")));
                                                sheet.addCell(new Label(18, j, userJson.getString("MobileNo")));
                                                sheet.addCell(new Label(19, j,userJson.getString("EmailId")));
                                                j++;

                                                mBuilder.setContentText(String.valueOf(i)).setProgress(obj.length(),i,false);

                                            } else {
                                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();

                                            }


                                        }


                                    } catch (RowsExceededException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    } catch (WriteException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }
                                    workbook.write();
                                    try {
                                        workbook.close();
                                    } catch (WriteException e) {
                                        Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(getApplicationContext(),"Download complete", Toast.LENGTH_SHORT).show();
                                    mBuilder.setContentText("Download complete").setProgress(0,0,false);
                                    notificationManager.notify(0, mBuilder.build());
                                    jobFinished(parameters, false);


                                    Intent intent = new Intent(Intent.ACTION_VIEW);
                                    intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(), BuildConfig.APPLICATION_ID + ".provider",file),"application/vnd.ms-excel");
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                    startActivity(intent);


                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();

                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            // Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            VolleySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
        }


    @Override
    public boolean onStopJob(final JobParameters params) {
        Log.d(TAG, "onStopJob() was called");
        return true;
    }
}