package com.sharpflux.shetkarimaza.activities;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.adapter.GooglePlaceAdapter;
import com.sharpflux.shetkarimaza.model.GooglePlaceModel;
import com.sharpflux.shetkarimaza.utils.GeocodingLocation;
import com.sharpflux.shetkarimaza.utils.PlacesDetails;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;

//import com.google.gson.Gson;

public class LocationActivityPlaces extends AppCompatActivity {

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";
 //   private static final String API_KEY = "AIzaSyAIgg-7oWclBcQYqkg_fSAoSb1ZNCm1R0A&libraries=places";
     private static final String API_KEY = "AIzaSyABy4YAQGzriK4MmpeEcM9ORxf6L_XT_uI&libraries=places";
    EditText search;
    ImageView mic, clear, back;
    ArrayList<GooglePlaceModel> googlePlaceModels;
    ListView listView;
    String searchValue;
    String ProductId;
    RequestQueue queue;
    String longitute,latitude;

    PlacesDetails pl = new PlacesDetails();
    ArrayList < Double > list = new ArrayList < Double > ();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_place);
        search = (EditText) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listView);
        mic = (ImageView) findViewById(R.id.mic);
        clear = (ImageView) findViewById(R.id.clear);
        back = (ImageView) findViewById(R.id.back);
        googlePlaceModels = new ArrayList<>();
        // get search value from the MapsActivity
       /*searchValue = getIntent().getStringExtra("searchValue");
        if(!searchValue.isEmpty()) {;
            search.setText(searchValue);
            search.setSelection(search.getText().toString().length());
            mic.setVisibility(View.GONE);
            clear.setVisibility(View.VISIBLE);
        }
*/
        // speech to text


        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {

            ProductId = bundle.getString("ProductId");

        }
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

        // clear value
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search.setText(null);
                googlePlaceModels.clear();
                setAdapter();
            }
        });

        // go to another activity
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  Intent intent = new Intent(LocationActivityPlaces.this, HomeActivity.class);
                intent.putExtra("place", search.getText().toString());
                intent.putExtra("ProductId",ProductId);
                startActivityForResult(intent, 2);
                overridePendingTransition(0, 0);*/
                Intent intent=new Intent();
                intent.putExtra("place", search.getText().toString());
                intent.putExtra("lat", latitude);
                intent.putExtra("long",longitute);
                setResult(123, intent);
              //  setResult(Activity.RESULT_OK,intent);
                finish();//finishing activity
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(search.getText().toString().isEmpty()) {
                    mic.setVisibility(View.VISIBLE);
                    clear.setVisibility(View.GONE);
                } else {
                    mic.setVisibility(View.GONE);
                    clear.setVisibility(View.VISIBLE);
                }

                if(s.toString().isEmpty()) {
                    googlePlaceModels.clear();
                    setAdapter();
                } else {
                    new GooglePlaces().execute(String.valueOf(s));
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!googlePlaceModels.get(position).getPlaceName().equalsIgnoreCase("Not Found")) {
                   /* Intent intent = new Intent(LocationActivityPlaces.this, ProductInfoForSaleActivity.class);
                    intent.putExtra("place", googlePlaceModels.get(position).getPlaceName());
                    intent.putExtra("ProductId",ProductId);
                    startActivity(intent);*/


                    Intent intent=new Intent();
                    intent.putExtra("place", googlePlaceModels.get(position).getPlaceName());
                    intent.putExtra("lat", googlePlaceModels.get(position).getLatitude());
                    intent.putExtra("long",googlePlaceModels.get(position).getLongitude());
                    setResult(123, intent);
                    finish();//finishing activity

                    overridePendingTransition(0, 0);
                }
            }
        });

    }

    public void promptSpeechInput() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"speak here");
            startActivityForResult(intent, 1);
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
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
                        googlePlaceModel.setSecondary_text(jsonArray.getJSONObject(i).getJSONObject("structured_formatting").getString("secondary_text"));
                       // GeocodingLocation locationAddress = new GeocodingLocation();
                       // locationAddress.getAddressFromLocation(jsonArray.getJSONObject(i).getString("description"), getApplicationContext(), new GeocoderHandler());
                        googlePlaceModel.setLatitude(latitude);
                        googlePlaceModel.setLongitude(longitute);
                        googlePlaceModels.add(googlePlaceModel);
                        list = pl.placeDetail(jsonArray.getJSONObject(i).getString("place_id"));
                        
                    }
                }
                else if  (jsonObj.getString("status").equalsIgnoreCase("OVER_QUERY_LIMIT")) {
                    Toast.makeText(getApplicationContext(), "You have exceeded your daily request quota for this API.", Toast.LENGTH_LONG).show();
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



    private void geocodePlace(String placeID) {
        // Construct the request URL
        String apiKey = "AIzaSyABy4YAQGzriK4MmpeEcM9ORxf6L_XT_uI"; // Your GMP API key
        String url = "https://maps.googleapis.com/maps/api/geocode/json?place_id=%s&key=%s";
        String requestURL = String.format(url, placeID, apiKey);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, requestURL, null,
                response -> {
                    try {

                        JSONArray results = response.getJSONArray("results");
                        if (results.length() == 0) {
                            Log.w("TAG", "No results from geocoding request.");
                            return;
                        }

                        Log.d("TAG", "LatLng for geocoded place: " + results);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.e("TAG", "Request failed"));

        // Add the request to the Request queue.
        queue.add(request);
    }


    public class GeocoderHandler extends Handler {
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
        GooglePlaceAdapter adapter = new GooglePlaceAdapter(LocationActivityPlaces.this, googlePlaceModels);
        listView.setAdapter(adapter);
    }

}