package com.sharpflux.shetkarimaza.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.DetailFormActivity;
import com.sharpflux.shetkarimaza.activities.HomeActivity;
import com.sharpflux.shetkarimaza.activities.SelectLanguageActivity;
import com.sharpflux.shetkarimaza.activities.SelfieActivity;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SelfieFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelfieFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public String IsNewUser;

    TextView hideImageTvSelfie;
    Button btn_take_selfie;
    ImageView img_banner_profile_placeholder;
    Intent intent;
    public static final int RequestPermissionCode = 1;
    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;
    File imageFile = null;
    LinearLayout footer;
    AlertDialog.Builder builder;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    private String address = "", city = "", district = "", state = "", companyname = "",
            license = "", companyregnno = "", gstno = "", names = "", registrationTypeId = "",
            registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",
            accountname = "", bankname = "", branchcode = "", accno = "", ifsc = "", check = "", adhar = "", selfie = "",stateId = "", districtId = "", TalukaId = "",SubCategroyTypeId="";

    private Intent iin;
    private Bundle bundle;
    private Integer userid;
    private String UserId;
    public String ImageUrl;
    public String selfi;

    public SharedPreferences prefs;



    public SelfieFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SelfieFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SelfieFragment newInstance(String param1, String param2) {
        SelfieFragment fragment = new SelfieFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_selfie, container, false);
        ((DetailFormActivity) getActivity()).setActionBarTitle(getString(R.string.selfie));


        bundle = getArguments();
        footer = view.findViewById(R.id.footer);
        hideImageTvSelfie = view.findViewById(R.id.hideImageTvSelfie);
        btn_take_selfie = (Button) view.findViewById(R.id.btn_take_selfie);
        img_banner_profile_placeholder = (ImageView) view.findViewById(R.id.imageView);

        User user = SharedPrefManager.getInstance(getContext()).getUser();
        userid = (Integer) user.getId();
        UserId = userid.toString();

        builder = new AlertDialog.Builder(getContext());
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
        EnableRuntimePermission();

        footer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /* saveOrderDetails();*/
                AsyncTaskRunner runner = new AsyncTaskRunner();
                String sleepTime = "2";
                runner.execute(sleepTime);
            }
        });

        btn_take_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelecteImages();
                hideImageTvSelfie.setText("selfie");
            }
        });




        if (bundle != null) {
            IsNewUser = bundle.getString("IsNewUser");

        }


        return  view;
    }


    private void SelecteImages() {

        final CharSequence[] items = {"Camera", "Gallary", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                } else if (items[i].equals("Gallary")) {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                } else if (items[i].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm = null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                Bitmap newBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
                Canvas canvas = new Canvas(newBitmap);
                canvas.drawColor(Color.WHITE);
                canvas.drawBitmap(bm, 0, 0, null);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

                selfi = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
                img_banner_profile_placeholder.setImageBitmap(bm);

            } catch (Exception e) {

                builder.setMessage(e.getMessage())
                        .setCancelable(false)

                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                             dialog.dismiss();
                            }
                        });

                AlertDialog alert = builder.create();
                alert.setTitle("Image Error");
                alert.show();
                e.printStackTrace();
            }

        }

    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();


        Bitmap newBitmap = Bitmap.createBitmap(thumbnail.getWidth(), thumbnail.getHeight(), thumbnail.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(thumbnail, 0, 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);
        selfi = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);


        /*thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        ImageUrl= Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);*/
        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;

        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // img_banner_profile_placeholder.setImageBitmap(thumbnail);

        Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");
        img_banner_profile_placeholder.setImageBitmap(imageBitmap);

    }

    public void EnableRuntimePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CAMERA)) {

            Toast.makeText(getContext(), "CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(getActivity(), new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(getContext(), "Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(getContext(), "Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }

    public void saveOrderDetails() {

        String adhartv = hideImageTvSelfie.getText().toString();
        if (TextUtils.isEmpty(adhartv)) {
            adhartv="0";
        }
        final String ImageUrl=adhartv;

        bundle = getArguments();

        if (bundle != null) {
            final String image;
            names = bundle.getString("Name");
            registrationTypeId = bundle.getString("RegistrationTypeId");

            registrationCategoryId = bundle.getString("RegistrationCategoryId");
            gender = bundle.getString("Gender");

            mobile = bundle.getString("Mobile");
            alternateMobile = bundle.getString("AlternateMobile");

            email = bundle.getString("Email");
            address = bundle.getString("address");

            city = bundle.getString("city");
            district = bundle.getString("district");

            state = bundle.getString("state");
            companyname = bundle.getString("companyname");

            license = bundle.getString("license");
            companyregnno = bundle.getString("companyregnno");

            gstno = bundle.getString("gstno");
            accountname ="0"; //bundle.getString("accountname");


            stateId = bundle.getString("stateId");
            districtId = bundle.getString("districtId");
            TalukaId = bundle.getString("TalukaId");

            SubCategroyTypeId = bundle.getString("SubCategroyTypeId");

            bankname = "0";//bundle.getString("bankname");
            branchcode = "0";// bundle.getString("branchcode");

            accno = "0";// bundle.getString("accno");
            ifsc =  "0";//bundle.getString("ifsc");

            check = "0";// bundle.getString("check");
            adhar =  "0";//bundle.getString("adhar");

        }

        else {

            getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    builder.setMessage("Data not available please try again ")
                            .setCancelable(false)

                            .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                    fragmentTransaction.add(R.id.dynamic_fragment_frame_layout, new FirstFragment());
                                    fragmentTransaction.addToBackStack("FirstFragment");
                                    fragmentTransaction.commit();
                                }
                            });

                    AlertDialog alert = builder.create();
                    alert.setTitle("Data not available");
                    alert.show();

                }
            });



            return;
        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {
                                SharedPrefManager.getInstance(getContext()).IsRegistrationComplete(true);
                                Intent lin = new Intent(getContext(), HomeActivity.class);
                                lin.putExtra("UserId",obj.getInt("UserId"));
                                startActivity(lin);
                                getActivity().finish();

                            } else {
                                builder.setMessage(response)
                                        .setCancelable(false)

                                        .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                //  Action for 'NO' Button
                                                dialog.cancel();

                                            }
                                        });

                                AlertDialog alert = builder.create();
                                alert.setTitle(obj.getString("message"));
                                alert.show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {



                        builder.setMessage(error.getMessage())
                                .setCancelable(false)

                                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Action for 'NO' Button
                                        dialog.cancel();

                                    }
                                });

                        AlertDialog alert = builder.create();
                        alert.setTitle("Error");

                        alert.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {


                Map<String, String> params = new HashMap<>();
                params.put("UserId", UserId);
                params.put("RegistrationTypeId", registrationTypeId);
                params.put("RegistrationCategoryId", SubCategroyTypeId);
                params.put("FullName", names);
                params.put("MobileNo", mobile);
                params.put("AlternateMobile", alternateMobile);
                params.put("Address", address);
                params.put("EmailId", email);
                params.put("Gender", gender);
                params.put("StateId", stateId);
                params.put("CityId",districtId);
                params.put("TahasilId", TalukaId);
                params.put("CompanyFirmName", companyname);
                params.put("LandLineNo", "1");
                params.put("APMCLicence", license);
                params.put("CompanyRegNo", companyregnno);
                params.put("GSTNo", gstno);
                params.put("AccountHolderName", accountname);
                params.put("BankName", bankname);
                params.put("BranchCode", branchcode);
                params.put("AccountNo", accno);
                params.put("IFSCCode", ifsc);
                params.put("UserPassword", "1");
                params.put("AgentId", "0");
                params.put("UploadCancelledCheckUrl", "0");
                params.put("UploadAdharCardPancardUrl", "0");
                params.put("ImageUrl", "0");

                return params;
            }


        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);

    }


    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {


              /*  setDynamicFragmentToTabLayout();
                Thread.sleep(100);

                resp = "Slept for " + params[0] + " seconds";*/


                int time = Integer.parseInt(params[0]) * 1000;
                saveOrderDetails();
                Thread.sleep(time);
                resp = "Slept for " + params[0] + " seconds";


            } catch (Exception e) {
                e.printStackTrace();
                resp = e.getMessage();
            }
            return resp;
        }

        @Override
        protected void onPostExecute(String result) {

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
}