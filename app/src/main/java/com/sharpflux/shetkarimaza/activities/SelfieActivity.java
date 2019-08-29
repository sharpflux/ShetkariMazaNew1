package com.sharpflux.shetkarimaza.activities;


import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
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


public class SelfieActivity extends AppCompatActivity {

    Button btn_take_selfie ;
    ImageView img_banner_profile_placeholder ;
    Intent intent ;
    public  static final int RequestPermissionCode  = 1 ;
    Integer REQUEST_CAMERA=1, SELECT_FILE=0;
    File imageFile = null;
    private Button btn_submit;

    private String address = "", city = "", district = "", state = "", companyname = "",
            license = "", companyregnno = "", gstno = "", names = "", registrationTypeId = "",
            registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",
            accountname = "", bankname = "", branchcode = "", accno = "", ifsc = "", check = "", adhar = "", selfie = "";

    private Intent iin;
    private Bundle bundle;
    private Integer userid;
    private String UserId;
    public String ImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selfie);

        btn_submit = findViewById(R.id.btnFormSubmit);

        User user = SharedPrefManager.getInstance(this).getUser();
        userid = (Integer) user.getId();
        UserId = userid.toString();

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent in = new Intent(SelfieActivity.this, HomeActivity.class);
                startActivity(in);

                saveOrderDetails();
            }
        });

        btn_take_selfie = (Button)findViewById(R.id.btn_take_selfie);
        img_banner_profile_placeholder = (ImageView)findViewById(R.id.imageView);


        EnableRuntimePermission();
        btn_take_selfie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SelecteImages();
               /* Intent photoPickerIntent= new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent,SELECT_PHOTO);*/
            }
        });


    }

    public void saveOrderDetails() {

        iin = getIntent();
        bundle = iin.getExtras();

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
            accountname = bundle.getString("accountname");

            bankname = bundle.getString("bankname");
            branchcode = bundle.getString("branchcode");

            accno = bundle.getString("accno");
            ifsc = bundle.getString("ifsc");

            check = bundle.getString("check");
            adhar = bundle.getString("adhar");



        }

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URLs.URL_REGISTER,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {

                            JSONObject obj = new JSONObject(response);

                            if (!obj.getBoolean("error")) {

                                Intent in = new Intent(SelfieActivity.this, HomeActivity.class);
                                startActivity(in);

                            } else {

                               /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelfieActivity.this);
                                alertDialogBuilder.setMessage(response);

                                AlertDialog alertDialog = alertDialogBuilder.create();
                                alertDialog.show();
                                Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();*/
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("ERROR", error.getMessage());

                       /* AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(SelfieActivity.this);
                        alertDialogBuilder.setMessage(error.getMessage());

                        AlertDialog alertDialog = alertDialogBuilder.create();
                        alertDialog.show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();*/
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("UserId",UserId);
                params.put("RegistrationTypeId", registrationTypeId);
                params.put("RegistrationCategoryId", registrationCategoryId);
                params.put("FullName",names);
                params.put("MobileNo", mobile);
                params.put("AlternateMobile",alternateMobile);
                params.put("Address", address);
                params.put("EmailId",email);
                params.put("Gender", "male");
                params.put("Address" , address);
                params.put("StateId", "1");
                params.put("CityId", "1");
                params.put("TahasilId", "1");
                params.put("CompanyFirmName", companyname);
                params.put("LandLineNo", "1");
                params.put("APMCLicence", license);
                params.put("CompanyRegNo",companyregnno );
                params.put("GSTNo", gstno);
                params.put("AccountHolderName", accountname);
                params.put("BankName", bankname);
                params.put("BranchCode",branchcode );
                params.put("AccountNo", accno);
                params.put("IFSCCode", ifsc);
                params.put("UploadCancelledCheckUrl",ImageUrl);
                params.put("UploadAdharCardPancardUrl", ImageUrl);
                params.put("UserPassword", "1");
                return params;
            }



        };

        VolleySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    private void SelecteImages(){

        final CharSequence[] items={"Camera","Gallary","Cancel"};
        AlertDialog.Builder builder=new AlertDialog.Builder(SelfieActivity.this);
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if(items[i].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }

                else if(items[i].equals("Gallary")){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);//
                    startActivityForResult(Intent.createChooser(intent, "Select File"),SELECT_FILE);
                }

                else if(items[i].equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, requestCode,data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }

    }

    private void onSelectFromGalleryResult(Intent data) {
        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        img_banner_profile_placeholder.setImageBitmap(bm);
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
        ImageUrl= Base64.encodeToString(bytes.toByteArray(), Base64.DEFAULT);
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

    public void EnableRuntimePermission(){

        if (ActivityCompat.shouldShowRequestPermissionRationale(SelfieActivity.this,
                Manifest.permission.CAMERA))
        {

            Toast.makeText(SelfieActivity.this,"CAMERA permission allows us to Access CAMERA app", Toast.LENGTH_LONG).show();

        } else {

            ActivityCompat.requestPermissions(SelfieActivity.this,new String[]{
                    Manifest.permission.CAMERA}, RequestPermissionCode);

        }
    }

    @Override
    public void onRequestPermissionsResult(int RC, String per[], int[] PResult) {

        switch (RC) {

            case RequestPermissionCode:

                if (PResult.length > 0 && PResult[0] == PackageManager.PERMISSION_GRANTED) {

                    Toast.makeText(SelfieActivity.this,"Permission Granted, Now your application can access CAMERA.", Toast.LENGTH_LONG).show();

                } else {

                    Toast.makeText(SelfieActivity.this,"Permission Canceled, Now your application cannot access CAMERA.", Toast.LENGTH_LONG).show();

                }
                break;
        }
    }


}
