package com.sharpflux.shetkarimaza.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.DetailFormActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.LocationActivityPlaces;
import com.sharpflux.shetkarimaza.adapter.GooglePlaceAdapter;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.GooglePlaceModel;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.utils.GeocodingLocation;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.app.Activity.RESULT_OK;


public class SecondFragment extends DialogFragment implements OnMapReadyCallback {
    EditText hideStateId, hideDistrictId,hideTalukaId;
    EditText address, city, edtdistrict,edttaluka, edtstate, companyname, license, companyregnno, gstno,name_botanical;
    ArrayList<Product> list;
    LinearLayout btn_next;
    Bundle bundle;
    String name = "", registrationTypeId = "", registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",SubCategroyTypeId=""
    ,Latitude="",Longitude="",GPSState="",GPSDistrict="",GPSTaluka="" ;
    DataFetcher fetcher;
    private CustomRecyclerViewDialog customDialog;
    Product sellOptions;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    Locale myLocale;
    dbLanguage mydatabase;
    String currentLanguage,language;
    TextInputLayout TICompany,textLayoutAddress;
    User user;
    EditText edtaddress;
    public String IsNewUser;
    private Location currentLocation;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private static final int MY_PERMISSIONS_REQUEST_CODE = 123;
    private static final int LOCATION_REQUEST_CODE = 101;
    Button btnCurrentAddress;
    boolean flagCurrentLocation=false;
    String longitute,latitude;

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
    private static final String API_KEY = "AIzaSyAIgg-7oWclBcQYqkg_fSAoSb1ZNCm1R0A&libraries=places";
    EditText search;
    ArrayList<GooglePlaceModel> googlePlaceModels;
    ListView listView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_second, container, false);

        myLocale = getResources().getConfiguration().locale;
        mydatabase = new dbLanguage(getContext());

        list = new ArrayList<Product>();

        btn_next =   (view.findViewById(R.id.secondbtnnext));

        address = view.findViewById(R.id.edtaddress);



        edtaddress = view.findViewById(R.id.edtaddress);


        textLayoutAddress=view.findViewById(R.id.textLayoutAddress);

        user = SharedPrefManager.getInstance(getContext()).getUser();

        ((DetailFormActivity) getActivity()).setActionBarTitle(getString(R.string.otherDetails));

        edtstate = view.findViewById(R.id.edtstate);
        edtdistrict = view.findViewById(R.id.edtdistrict);
        edttaluka=view.findViewById(R.id.edttaluka);
        city = view.findViewById(R.id.edtcity);
        name_botanical = view.findViewById(R.id.name_botanical);

        companyname = view.findViewById(R.id.edtcompanyname);
        license = view.findViewById(R.id.edtlicense);
        companyregnno = view.findViewById(R.id.edtcompanyregno);
        gstno = view.findViewById(R.id.edtgstno);

        TICompany = (TextInputLayout)view.findViewById(R.id.TICompany);
        IsNewUser="0";

        hideStateId = view.findViewById(R.id.hideStateId);
        hideDistrictId = view.findViewById(R.id.hideDistrictId);
        hideTalukaId = view.findViewById(R.id.hideTalukaId);

        btnCurrentAddress = view.findViewById(R.id.btnCurrentAddress);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        myLocale = getResources().getConfiguration().locale;
        language = user.getLanguage();



        googlePlaceModels = new ArrayList<>();
        search = (EditText) view.findViewById(R.id.search);
        listView = (ListView)view.findViewById(R.id.listView);

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

        bundle = getArguments();
        btnCurrentAddress.setVisibility(View.GONE);

        btnCurrentAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!flagCurrentLocation){
                    flagCurrentLocation=true;
                    btnCurrentAddress.setText("Use");
                    fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getContext());
                    if (ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_REQUEST_CODE);
                        return;
                    }
                    fetchLastLocation();
                }
                else {
                    btnCurrentAddress.setText("Clear");
                    flagCurrentLocation=false;
                }

            }
        });



        if (bundle != null) {
            name = bundle.getString("Name");
            registrationTypeId = bundle.getString("RegistrationTypeId");
            registrationCategoryId = bundle.getString("RegistrationCategoryId");
            gender = bundle.getString("Gender");
            mobile = bundle.getString("Mobile");
            alternateMobile = bundle.getString("AlternateMobile");
            email = bundle.getString("Email");
            SubCategroyTypeId= bundle.getString("SubCategroyTypeId");

            IsNewUser = bundle.getString("IsNewUser");

            if(registrationTypeId.equals("32")){
               // companyname.setHint("Nursery Name");
                TICompany.setHint("Nursery Name");
                textLayoutAddress.setHint("Nursery Address");
                companyregnno.setText("0");
                gstno.setText("0");
                license.setText("0");
                license.setVisibility(View.GONE);
                gstno.setVisibility(View.GONE);
                companyregnno.setVisibility(View.GONE);
            }
            else if(registrationTypeId.equals("36") || registrationTypeId.equals("37")){
                // companyname.setHint("Nursery Name");
                TICompany.setHint("Nursery Name");
                TICompany.setVisibility(View.GONE);
                textLayoutAddress.setHint("Address");
                companyregnno.setText("0");
                gstno.setText("0");
                license.setText("0");
                license.setVisibility(View.GONE);
                gstno.setVisibility(View.GONE);
                companyregnno.setVisibility(View.GONE);
                btnCurrentAddress.setVisibility(View.VISIBLE);
            }

        }


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String addresss = address.getText().toString();

                String state = edtstate.getText().toString();
                String district= edtdistrict.getText().toString();

                String taluka = edttaluka.getText().toString();
                String village= city.getText().toString();

                String firmname = companyname.getText().toString();
                String apmc = license.getText().toString();

                String companyreg = companyregnno.getText().toString();
                String gst= gstno.getText().toString();


                if (TextUtils.isEmpty(addresss)) {
                    address.setError("Please enter your address");
                    address.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(state)) {
                    edtstate.setError("Please select your state");
                    edtstate.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(district)) {
                    edtdistrict.setError("Please select your district");
                    edtdistrict.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(taluka)) {
                    edttaluka.setError("Please select your taluka");
                    edttaluka.requestFocus();
                    return;
                }

              /*  if (TextUtils.isEmpty(village)) {
                    city.setError("Please enter your village");
                    city.requestFocus();
                    return;
                }*/
                if (TextUtils.isEmpty(firmname)) {
                    firmname="0";
                }

                if (TextUtils.isEmpty(apmc)) {
                    apmc="0";
                }

                if (TextUtils.isEmpty(companyreg)) {
                    companyreg="0";
                }

                 if (TextUtils.isEmpty(gst)) {
                  //  gstno.setError("Please enter your gst no");
                    //gstno.requestFocus();
                   // return;

                     gst="0";
                }


                Bundle bundle = new Bundle();
                bundle.putString("RegistrationTypeId", registrationTypeId);
                bundle.putString("RegistrationCategoryId", registrationCategoryId);
                bundle.putString("Name", name);
                bundle.putString("Gender", gender);
                bundle.putString("Mobile", mobile);
                bundle.putString("AlternateMobile", alternateMobile);
                bundle.putString("Email", email);
                bundle.putString("address", address.getText().toString());
                bundle.putString("city","0" ); //city.getText().toString()
                bundle.putString("TalukaId", hideTalukaId.getText().toString());
                bundle.putString("district", edtdistrict.getText().toString());
                bundle.putString("districtId", hideDistrictId.getText().toString());
                bundle.putString("state", edtstate.getText().toString());
                bundle.putString("stateId", hideStateId.getText().toString());
                bundle.putString("companyname", firmname);
                bundle.putString("license", apmc);
                bundle.putString("companyregnno", companyreg);
                bundle.putString("gstno",gst);//gstno.getText().toString()
                bundle.putString("IsNewUser", IsNewUser);
                bundle.putString("SubCategroyTypeId", SubCategroyTypeId);
                bundle.putString("Latitude", Latitude);
                bundle.putString("Longitude", Longitude);
                bundle.putString("GPSState", GPSState);
                bundle.putString("GPSDistrict", GPSDistrict);
                bundle.putString("GPSTaluka", GPSTaluka);
                bundle.putBoolean("flagCurrentLocation", flagCurrentLocation);

                FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                transection.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                SelfieFragment mfragment = new SelfieFragment();
                mfragment.setArguments(bundle);
                transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                transection.addToBackStack("SelfieFragment");
                transection.commit();
            }
        });

        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(),name_botanical);
        edtstate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "state";
                runner.execute(sleepTime);

            }
        });

        edtdistrict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "district";
                runner.execute(sleepTime);
            }

        });
        edttaluka.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "taluka";
                runner.execute(sleepTime);
            }

        });

        if (IsNewUser.equals("true")) {

        } else if (IsNewUser.equals("false")) {
            SecondFragment.AsyncTaskRunner runner = new SecondFragment.AsyncTaskRunner();
            String sleepTime = "userdetails";
            runner.execute(sleepTime);
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openYourActivity();
            }
        });

       /* search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.toString().isEmpty()) {
                    googlePlaceModels.clear();
                    setAdapter();
                } else {
                    new SecondFragment.GooglePlaces().execute(String.valueOf(s));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });*/

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!googlePlaceModels.get(position).getPlaceName().equalsIgnoreCase("Not Found")) {
                   /* Intent intent = new Intent(LocationActivityPlaces.this, ProductInfoForSaleActivity.class);
                    intent.putExtra("place", googlePlaceModels.get(position).getPlaceName());
                    intent.putExtra("ProductId",ProductId);
                    startActivity(intent);*/


               /*     Intent intent=new Intent();
                    intent.putExtra("place", googlePlaceModels.get(position).getPlaceName());
                    intent.putExtra("lat", latitude);
                    intent.putExtra("long",longitute);
                    getContext().setResult(123, intent);
                    //  setResult(Activity.RESULT_OK,intent);
                    finish();//finishing activity

                    overridePendingTransition(0, 0);*/
                }
            }
        });

        return view;

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
    }

    public List<Address> getAddress(Context ctx, double lat, double lng) {
        String fullAdd = null;
        List<Address> addresses = null;
        try {
            Geocoder geocoder = new Geocoder(ctx, Locale.getDefault());
            addresses = geocoder.getFromLocation(lat, lng, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                fullAdd = address.getAddressLine(0);

                // if you want only city or pin code use following code //
                   /* String Location = address.getLocality();
                    String zip = address.getPostalCode();
                    String Country = address.getCountryName(); */
            }


        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return addresses;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CODE: {
                // When request is cancelled, the results array are empty
                if (
                        (grantResults.length > 0) &&
                                (grantResults[0]
                                        + grantResults[1]
                                        + grantResults[2]
                                        + grantResults[3]
                                        + grantResults[4]
                                        + grantResults[5]
                                        == PackageManager.PERMISSION_GRANTED
                                )
                ) {
                    // Permissions are granted
                    Toast.makeText(getContext(), "Permissions granted.", Toast.LENGTH_SHORT).show();
                } else {
                    // Permissions are denied
                    Toast.makeText(getContext(), "Permissions denied.", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

    private void fetchLastLocation() {
        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    currentLocation = location;
                    List<Address> addresses = getAddress(getContext(), currentLocation.getLatitude(), currentLocation.getLongitude());


                    if (addresses != null) {

                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            edtaddress.setText(addresses.get(0).getAddressLine(0));
                            edtstate.setText(address.getAdminArea());
                            edtdistrict.setText(address.getSubAdminArea());
                            edttaluka.setText(address.getLocality()+ " " + address.getSubLocality());


                            Latitude=String.valueOf(address.getLatitude());
                            Longitude=String.valueOf(address.getLongitude());
                            GPSState=address.getAdminArea();
                            GPSDistrict=address.getSubAdminArea();
                            GPSTaluka=address.getSubAdminArea();

                           // edt_address2.setText(addresses.get(0).getAddressLine(1));
                          //  edt_pinCode.setText(address.getPostalCode());
                          //  edt_city.setText(address.getLocality() + " " + address.getSubLocality());
                         //   edt_state.setText(address.getAdminArea() +" "+ address.getSubAdminArea() );
                           // DeliveryLocation=new LatLng(address.getLatitude(),address.getLongitude());
                        }



                    }




                } else {
                    Toast.makeText(getContext(), "No Location recorded", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                if (params[0].toString() == "state")
                    fetcher.loadList("StatesName", edtstate, URLs.URL_STATE + "?StatesID=15&Language="+currentLanguage, "StatesID", hideStateId, "", "","State","",null,null,customDialogLoadingProgressBar);
                   // fetcher.loadList("StatesName", edtstate, URLs.URL_STATE, "StatesID", hideStateId, "", "");

                else if (params[0].toString() == "district")
                    fetcher.loadList("DistrictName", edtdistrict, URLs.URL_DISTRICT + hideStateId.getText()+"," + "&Language="+currentLanguage, "DistrictId", hideDistrictId, "", "","District","",null,null,customDialogLoadingProgressBar);

                else if (params[0].toString() == "taluka")
                    fetcher.loadList("TalukaName", edttaluka, URLs.URL_TALUKA + hideDistrictId.getText()+"," + "&Language="+currentLanguage, "TalukasId", hideTalukaId, "", "","Taluka","",null,null,customDialogLoadingProgressBar);
                else if (params[0].toString() == "userdetails")
                {
                    GetUserDetails();
                }

                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";
            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }


        @Override
        protected void onPostExecute(String result) {
            // execution of result of Long time consuming operation
           // progressDialog.dismiss();
            // finalResult.setText(result);
        }


        @Override
        protected void onPreExecute() {
            customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
            customDialogLoadingProgressBar.show();
        }


        @Override
        protected void onProgressUpdate(String... text) {
            // finalResult.setText(text[0]);

        }

    }

    public void onBackPressed() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext(), R.style.AlertDialogTheme);
        builder.setCancelable(false);
        builder.setMessage("Do you want to leave this page ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //if user pressed "yes", then he is allowed to exit from application
                //finish();
                Intent intent = new Intent(getContext(), HomeActivity.class);
                startActivity(intent);


            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }
    private void GetUserDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_REGISTRATIONGETUSERDETAILS + "UserId="+user.getId() +"&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);


                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    if(!userJson.getString("CompanyFirmName").equals("0"))
                                        companyname.setText(userJson.getString("CompanyFirmName"));


                                    if(!userJson.getString("Address").equals("0"))
                                        address.setText(userJson.getString("Address"));

                                    if(!userJson.getString("StatesName").equals("0"))
                                        edtstate.setText(userJson.getString("StatesName"));


                                    if(!userJson.getString("DistrictName").equals("0"))
                                        edtdistrict.setText(userJson.getString("DistrictName"));

                                    if(!userJson.getString("TalukaName").equals("0"))
                                        edttaluka.setText(userJson.getString("TalukaName"));

                                    if(!userJson.getString("TalukaName").equals("0"))
                                        city.setText(userJson.getString("TalukaName"));

                                    if(!userJson.getString("APMCLicence").equals("0"))
                                        license.setText(userJson.getString("APMCLicence"));


                                    if(!userJson.getString("CompanyRegNo").equals("0"))
                                        companyregnno.setText(userJson.getString("CompanyRegNo"));

                                    if(!userJson.getString("GSTNo").equals("0") && !userJson.getString("GSTNo").equals("null") )
                                        gstno.setText(userJson.getString("GSTNo"));




                                    hideDistrictId.setText(String.valueOf(userJson.getInt("CityId")));
                                    hideStateId.setText(String.valueOf(userJson.getInt("StateId")));
                                    hideTalukaId.setText(String.valueOf(userJson.getInt("TalukaId")));

                                    Latitude=userJson.getString("Latitude");
                                    Longitude=userJson.getString("Longitude");
                                    
                                    GPSState=userJson.getString("StatesName");
                                    GPSDistrict=userJson.getString("DistrictName");
                                    GPSTaluka=userJson.getString("TalukaName");

                                } else {
                                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                                }

                            }
                            customDialogLoadingProgressBar.dismiss();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            customDialogLoadingProgressBar.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        customDialogLoadingProgressBar.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1 && resultCode == RESULT_OK && null != data) {
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            search.setText(result.get(0));
            search.setSelection(search.getText().toString().length());
        }

    }

    public class GooglePlaces extends AsyncTask<String, String, String> {

        @Override
        public void onPreExecute() {
            super .onPreExecute();

        }

        @Override
        protected String doInBackground(String... params) {
            String result = null;
            try {
                // Your API key
                String key="?key=" + API_KEY;
                // components type
                String components="&components=country:in";
                // set input type
                String input="&input=" + URLEncoder.encode(params[0], "utf8");
                // Building the url to the web service
                String strURL = PLACES_API_BASE+TYPE_AUTOCOMPLETE+OUT_JSON+key+components+input;


                URL url = new URL(strURL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.connect();

                if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
                    InputStreamReader inputStreamReader = new InputStreamReader(conn.getInputStream());
                    BufferedReader reader = new BufferedReader(inputStreamReader);
                    StringBuilder stringBuilder = new StringBuilder();
                    String temp;

                    while ((temp = reader.readLine()) != null) {
                        stringBuilder.append(temp);
                    }
                    result = stringBuilder.toString();
                }else  {
                    result = "error";
                }

            } catch (Exception  e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        public void onPostExecute(String s) {
            super .onPostExecute(s);
            googlePlaceModels.clear();
            try {
                JSONObject jsonObj = new JSONObject(s);
                JSONArray jsonArray = jsonObj.getJSONArray("predictions");




                if (jsonObj.getString("status").equalsIgnoreCase("OK")) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GooglePlaceModel googlePlaceModel = new GooglePlaceModel();
                        googlePlaceModel.setPlaceName(jsonArray.getJSONObject(i).getString("description"));


                        GeocodingLocation locationAddress = new GeocodingLocation();
                        locationAddress.getAddressFromLocation(jsonArray.getJSONObject(i).getString("description"), getContext(), new SecondFragment.GeocoderHandler());
                        googlePlaceModel.setLatitude(latitude);
                        googlePlaceModel.setLongitude(longitute);
                        googlePlaceModels.add(googlePlaceModel);

                    }
                } else if   (jsonObj.getString("status").equalsIgnoreCase("OVER_QUERY_LIMIT")) {
                    Toast.makeText(getContext(), "You have exceeded your daily request quota for this API.", Toast.LENGTH_LONG).show();
                    GooglePlaceModel googlePlaceModel = new GooglePlaceModel();
                    googlePlaceModel.setPlaceName("Not Found");
                    googlePlaceModels.add(googlePlaceModel);
                } else {
                    GooglePlaceModel googlePlaceModel = new GooglePlaceModel();
                    googlePlaceModel.setPlaceName("Not Found");
                    googlePlaceModels.add(googlePlaceModel);
                }
                // set adapter
                setAdapter();
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {
            String locationAddress;
            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    latitude= bundle.getString("lat");
                    longitute= bundle.getString("longi");
                    break;
                default:
                    locationAddress = null;
            }

        }
    }

    public void setAdapter() {
        GooglePlaceAdapter adapter = new GooglePlaceAdapter(getContext(), googlePlaceModels);
        listView.setAdapter(adapter);
    }

    ActivityResultLauncher<Intent> launchSomeActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == 123) {
                        Intent data = result.getData();

                        Bundle bundle =data.getExtras();

                        search.setText(bundle.getString("place"));
                        longitute=bundle.getString("lat");
                        longitute=bundle.getString("long");
                        // your operation....
                    }
                }
            });

    public void openYourActivity() {
        Intent intent = new Intent(getContext(), LocationActivityPlaces.class);
        launchSomeActivity.launch(intent);
        getActivity().overridePendingTransition(R.anim.left_to_right, R.anim.right_to_left);
    }

}
