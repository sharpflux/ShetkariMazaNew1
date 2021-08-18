package com.sharpflux.shetkarimaza.fragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;

import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.DetailFormActivity;
import com.sharpflux.shetkarimaza.activities.ProcessorActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.activities.SelfieActivity;
import com.sharpflux.shetkarimaza.customviews.CustomDialogLoadingProgressBar;
import com.sharpflux.shetkarimaza.customviews.CustomRecyclerViewDialog;
import com.sharpflux.shetkarimaza.model.Product;
import com.sharpflux.shetkarimaza.model.User;
import com.sharpflux.shetkarimaza.sqlite.dbLanguage;
import com.sharpflux.shetkarimaza.utils.DataFetcher;
import com.sharpflux.shetkarimaza.utils.PictureFacer;
import com.sharpflux.shetkarimaza.volley.SharedPrefManager;
import com.sharpflux.shetkarimaza.volley.URLs;
import com.sharpflux.shetkarimaza.volley.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.sharpflux.shetkarimaza.activities.SelfieActivity.RequestPermissionCode;

public class ThirdFragment extends Fragment {

    EditText accountname, bankname, branchcode, accno, ifsc;

    // ImageView check,adhar;

    ImageView currentImageView = null;
    Bitmap imageBitmap;
    String currentLanguage, language;
    TextView tvAdhar, tvCheque, hidBankId;

    private static final String IMAGE_DIRECTORY = "/demonuts";

    private int GALLERY = 1, CAMERA = 2;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    ImageView adhar_image, cheque_image;

    public String ChequeImageBlob, AdharImageBlob, ImageType;

    Button btn_chequeimage, btn_adharimage;
    LinearLayout submitButton;
    Bundle bundle;
    private CustomDialogLoadingProgressBar customDialogLoadingProgressBar;
    private String[] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW", "android.permission.CAMERA"};

    String address = "", city = "", district = "", state = "", companyname = "",
            license = "", companyregnno = "", gstno = "", name = "", registrationTypeId = "",
            registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "", stateId = "", districtId = "", TalukaId = "";
    User user;
    DataFetcher fetcher;
    Product sellOptions;
    private CustomRecyclerViewDialog customDialog;
    ArrayList<Product> list;
    dbLanguage mydatabase;
    int requestCode = 200;
    public String IsNewUser;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);
        IsNewUser="0";
        bundle = getArguments();
        customDialogLoadingProgressBar = new CustomDialogLoadingProgressBar(getContext());
        user = SharedPrefManager.getInstance(getContext()).getUser();
        list = new ArrayList<Product>();
        mydatabase = new dbLanguage(getContext());
        Cursor cursor = mydatabase.LanguageGet(language);

        ((DetailFormActivity) getActivity()).setActionBarTitle(getString(R.string.BankDetails));

        while (cursor.moveToNext()) {
            currentLanguage = cursor.getString(0);

        }


        hidBankId = view.findViewById(R.id.hidBankId);

        if (bundle != null) {
            name = bundle.getString("Name");
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
            stateId = bundle.getString("stateId");
            districtId = bundle.getString("districtId");
            TalukaId = bundle.getString("TalukaId");

            IsNewUser = bundle.getString("IsNewUser");

        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(permissions, requestCode);
        }

        submitButton = view.findViewById(R.id.thirdbtnnext);

        accountname = view.findViewById(R.id.edtaccholder);

        bankname = view.findViewById(R.id.edtbankname);
        branchcode = view.findViewById(R.id.edtbranchcode);

        btn_chequeimage = view.findViewById(R.id.btn_chequeimage);
        btn_adharimage = view.findViewById(R.id.btn_adharimage);


        accno = view.findViewById(R.id.edtaccno);
        ifsc = view.findViewById(R.id.edtifsc);

        cheque_image = view.findViewById(R.id.cheque_imageView);
        adhar_image = view.findViewById(R.id.adhar_imageView);

        tvCheque = view.findViewById(R.id.hideImageTvCheque);
        tvAdhar = view.findViewById(R.id.hideImageTvAdhar);

        fetcher = new DataFetcher(sellOptions, customDialog, list, getContext(), null);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String accountnamee = accountname.getText().toString();
                String banknamee = bankname.getText().toString();
                String branchcodee = branchcode.getText().toString();
                String accnoe = accno.getText().toString();
                String ifsce = ifsc.getText().toString();
                String chequetv = tvCheque.getText().toString();
                String adhartv = tvAdhar.getText().toString();


                if (TextUtils.isEmpty(accountnamee)) {
                    accountname.setError("Please enter your name");
                    accountname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(banknamee)) {
                    bankname.setError("Please enter your bank name");
                    bankname.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(branchcodee)) {
                    branchcode.setError("Please enter your branch code");
                    branchcode.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(accnoe)) {
                    accno.setError("Please enter your account number");
                    accno.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(ifsce)) {
                    ifsc.setError("Please enter your ifsc code");
                    ifsc.requestFocus();
                    return;
                }


              /*  if (TextUtils.isEmpty(chequetv)) {
                    btn_chequeimage.setError("Please upload copy of cheque");
                    btn_chequeimage.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(adhartv)) {
                    btn_adharimage.setError("Please upload copy of adhar");
                    btn_adharimage.requestFocus();
                    return;
                }*/


      /*          if (registrationTypeId.equals("1")) {


                    Intent intent = new Intent(getContext(), ProcessorActivity.class);
                    intent.putExtra("Name", name);
                    intent.putExtra("RegistrationTypeId", registrationTypeId);
                    intent.putExtra("RegistrationCategoryId", registrationCategoryId);
                    intent.putExtra("Gender", gender);
                    intent.putExtra("Mobile", mobile);
                    intent.putExtra("AlternateMobile", alternateMobile);
                    intent.putExtra("Email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("city", city);
                    intent.putExtra("district", district);
                    intent.putExtra("state", state);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("license", license);
                    intent.putExtra("companyregnno", companyregnno);
                    intent.putExtra("gstno", gstno);
                    intent.putExtra("accountname", accountname.getText().toString());
                    intent.putExtra("bankname", hidBankId.getText().toString());
                    intent.putExtra("branchcode", branchcode.getText().toString());
                    intent.putExtra("accno", accno.getText().toString());
                    intent.putExtra("ifsc", ifsc.getText().toString());
                    intent.putExtra("check", ChequeImageBlob);
                    intent.putExtra("adhar", AdharImageBlob);
                    intent.putExtra("adhar", AdharImageBlob);

                    startActivity(intent);

                } else {*/




                  /*  Intent intent = new Intent(getContext(), SelfieActivity.class);

                    intent.putExtra("Name", name);
                    intent.putExtra("RegistrationTypeId", registrationTypeId);
                    intent.putExtra("RegistrationCategoryId", registrationCategoryId);
                    intent.putExtra("Gender", gender);
                    intent.putExtra("Mobile", mobile);
                    intent.putExtra("AlternateMobile", alternateMobile);
                    intent.putExtra("Email", email);
                    intent.putExtra("address", address);
                    intent.putExtra("city", city);
                    intent.putExtra("district", district);
                    intent.putExtra("state", state);
                    intent.putExtra("companyname", companyname);
                    intent.putExtra("license", license);
                    intent.putExtra("companyregnno", companyregnno);
                    intent.putExtra("gstno", gstno);
                    intent.putExtra("accountname", accountname.getText().toString());
                    intent.putExtra("bankname", hidBankId.getText().toString());
                    intent.putExtra("branchcode", branchcode.getText().toString());
                    intent.putExtra("accno", accno.getText().toString());
                    intent.putExtra("ifsc", ifsc.getText().toString());
                    intent.putExtra("check", ChequeImageBlob);
                    intent.putExtra("adhar", AdharImageBlob);
                    startActivity(intent);*/


                    Bundle bundle = new Bundle();
                    bundle.putString("Name", name);
                    bundle.putString("RegistrationTypeId", registrationTypeId);
                    bundle.putString("RegistrationCategoryId", registrationCategoryId);
                    bundle.putString("Gender", gender);
                    bundle.putString("Mobile", mobile);
                    bundle.putString("AlternateMobile", alternateMobile);
                    bundle.putString("Email", email);
                    bundle.putString("address", address);
                    bundle.putString("city", TalukaId);
                    bundle.putString("district", districtId);
                    bundle.putString("state", stateId);
                    bundle.putString("companyname", companyname);
                    bundle.putString("license", license);
                    bundle.putString("companyregnno", companyregnno);
                    bundle.putString("gstno", gstno);
                    bundle.putString("accountname", accountname.getText().toString());
                    bundle.putString("bankname", hidBankId.getText().toString());
                    bundle.putString("branchcode", branchcode.getText().toString());
                    bundle.putString("accno", accno.getText().toString());
                    bundle.putString("ifsc", ifsc.getText().toString());
                    bundle.putString("check", ChequeImageBlob);
                    bundle.putString("adhar", AdharImageBlob);
                    bundle.putString("IsNewUser", IsNewUser);



                    FragmentTransaction transection =getActivity().getSupportFragmentManager().beginTransaction();
                    transection.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                    SelfieFragment mfragment = new SelfieFragment();
                    mfragment.setArguments(bundle);
                    transection.replace(R.id.dynamic_fragment_frame_layout, mfragment);
                    transection.addToBackStack("SelfieFragment");
                    transection.commit();

                //}
            }
        });


        btn_chequeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentImageView=(ImageView) view;
                ImageType = "Cheque";
                showPictureDialog();
                tvCheque.setText("cheque");

            }
        });


        bankname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ThirdFragment.AsyncTaskRunner runner = new ThirdFragment.AsyncTaskRunner();
                String sleepTime = "Bank";
                runner.execute(sleepTime);
            }

        });

        btn_adharimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentImageView=(ImageView) view;
                ImageType = "Aadhar";
                showPictureDialog();
                tvAdhar.setText("adhar");

            }
        });



        if (IsNewUser.equals("true")) {

        } else if (IsNewUser.equals("false")) {
            ThirdFragment.AsyncTaskRunner runner = new ThirdFragment.AsyncTaskRunner();
            String sleepTime = "userdetails";
            runner.execute(sleepTime);
        }

        return view;
    }

    private void showPictureDialog() {
        final CharSequence[] items = {"Camera", "Gallary", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Add Image");

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                if (items[i].equals("Camera")) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&  ContextCompat.checkSelfPermission( getContext(), android.Manifest.permission.CAMERA ) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(permissions, requestCode);
                        return;
                    }
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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

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
            Bitmap newBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(), bm.getConfig());
            Canvas canvas = new Canvas(newBitmap);
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(bm, 0, 0, null);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

            if (ImageType == "Cheque") {
                ChequeImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            }
            if (ImageType == "Aadhar") {
                AdharImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            }

            if (ImageType == "Cheque") {

                cheque_image.setImageBitmap(bm);
            }
            if (ImageType == "Aadhar") {
                adhar_image.setImageBitmap(bm);
            }

        }
    }


    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        ArrayList<PictureFacer> images = new ArrayList<>();

        Bitmap newBitmap = Bitmap.createBitmap(thumbnail.getWidth(), thumbnail.getHeight(), thumbnail.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(thumbnail, 0, 0, null);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        newBitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

        if (ImageType == "Cheque") {
            ChequeImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }
        if (ImageType == "Aadhar") {
            AdharImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

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


        if (ImageType == "Cheque") {
            imageBitmap = (Bitmap) data.getExtras().get("data");




            cheque_image.setImageBitmap(imageBitmap);
        }
        if (ImageType == "Aadhar") {
            imageBitmap = (Bitmap) data.getExtras().get("data");
            adhar_image.setImageBitmap(imageBitmap);
        }

        PictureFacer pic = new PictureFacer();
        pic.setPicturName(MediaStore.Images.Media.DISPLAY_NAME);
        pic.setPicturePath(MediaStore.Images.Media.DATA);
        pic.setPictureSize(MediaStore.Images.Media.SIZE);
        File fileObject = new File(MediaStore.Video.Media.DATA);
        Long fileModified = fileObject.lastModified();
        pic.setDateTime(new Date(fileModified));
        pic.setType(ImageType);
        images.add(pic);
    }

    private class AsyncTaskRunner extends AsyncTask<String, String, String> {

        private String resp;
        ProgressDialog progressDialog;

        @Override
        protected String doInBackground(String... params) {
            publishProgress("Sleeping..."); // Calls onProgressUpdate()
            try {
                if (params[0].toString() == "Bank")

                    fetcher.loadList("BankName", bankname, URLs.URL_GETBANKS + "?Language=" + currentLanguage,
                            "BankId", hidBankId, "", "", "Bank", "", null, null, customDialogLoadingProgressBar);

                else if (params[0].toString() == "userdetails") {
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


    private void GetUserDetails() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URLs.URL_REGISTRATIONGETUSERDETAILS + "&UserId=" + user.getId() + "&Language=" + currentLanguage,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONArray obj = new JSONArray(response);


                            for (int i = 0; i < obj.length(); i++) {
                                JSONObject userJson = obj.getJSONObject(i);

                                if (!userJson.getBoolean("error")) {

                                    if (!userJson.getString("AccountHolderName").equals("0"))
                                        accountname.setText(userJson.getString("AccountHolderName"));


                                    if (!userJson.getString("BankName").equals("0"))
                                        bankname.setText(userJson.getString("BankName"));

                                    if (!userJson.getString("BranchCode").equals("0"))
                                        branchcode.setText(userJson.getString("BranchCode"));

                                    if (!userJson.getString("AccountNo").equals("0"))
                                        accno.setText(userJson.getString("AccountNo"));

                                    if (!userJson.getString("IFSCCode").equals("0"))
                                        ifsc.setText(userJson.getString("IFSCCode"));

                                    if (!userJson.getString("BankName").equals("0"))
                                        bankname.setText(userJson.getString("BankName"));

                                    hidBankId.setText(userJson.getString("BankId"));

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

}




