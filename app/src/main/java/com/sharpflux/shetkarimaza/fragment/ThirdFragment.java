package com.sharpflux.shetkarimaza.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.sharpflux.shetkarimaza.R;
import com.sharpflux.shetkarimaza.activities.ProcessorActivity;
import com.sharpflux.shetkarimaza.activities.ProductInfoForSaleActivity;
import com.sharpflux.shetkarimaza.activities.SelfieActivity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.sharpflux.shetkarimaza.activities.SelfieActivity.RequestPermissionCode;

public class ThirdFragment extends Fragment {

    TextInputEditText accountname, bankname, branchcode, accno, ifsc;

    // ImageView check,adhar;

    ImageView currentImageView = null;
    Bitmap imageBitmap;

    TextView tvAdhar, tvCheque;

    private static final String IMAGE_DIRECTORY = "/demonuts";

    private int GALLERY = 1, CAMERA = 2;

    Integer REQUEST_CAMERA = 1, SELECT_FILE = 0;

    ImageView adhar_image, cheque_image;

    public String ChequeImageBlob,AdharImageBlob,ImageType;

    Button submitButton, btn_chequeimage, btn_adharimage;

    Bundle bundle;

    private String [] permissions = {"android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.ACCESS_FINE_LOCATION",
            "android.permission.READ_PHONE_STATE", "android.permission.SYSTEM_ALERT_WINDOW","android.permission.CAMERA"};

    String address = "", city = "", district = "", state = "", companyname = "",
            license = "", companyregnno = "", gstno = "", name = "", registrationTypeId = "",
            registrationCategoryId = "", gender = "", mobile = "", alternateMobile = "", email = "",stateId="",districtId="",TalukaId="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_third, container, false);

        bundle = getArguments();

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
            stateId=bundle.getString("stateId");
            districtId=bundle.getString("districtId");
            TalukaId=bundle.getString("TalukaId");
        }

        int requestCode = 200;
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


               /* if (TextUtils.isEmpty(chequetv)) {
                    btn_chequeimage.setError("Please upload copy of cheque");
                    btn_chequeimage.requestFocus();
                    return;
                }

                if (TextUtils.isEmpty(adhartv)) {
                    btn_adharimage.setError("Please upload copy of adhar");
                    btn_adharimage.requestFocus();
                    return;
                }*/


                if (registrationTypeId.equals("1")) {



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
                    intent.putExtra("bankname", bankname.getText().toString());
                    intent.putExtra("branchcode", branchcode.getText().toString());
                    intent.putExtra("accno", accno.getText().toString());
                    intent.putExtra("ifsc", ifsc.getText().toString());
                    intent.putExtra("check", ChequeImageBlob);
                    intent.putExtra("adhar", AdharImageBlob);
                    intent.putExtra("adhar", AdharImageBlob);

                    startActivity(intent);

                } else {



                Intent intent = new Intent(getContext(), SelfieActivity.class);

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
                intent.putExtra("bankname", bankname.getText().toString());
                intent.putExtra("branchcode", branchcode.getText().toString());
                intent.putExtra("accno", accno.getText().toString());
                intent.putExtra("ifsc", ifsc.getText().toString());
                intent.putExtra("check", ChequeImageBlob);
                intent.putExtra("adhar", AdharImageBlob);

                startActivity(intent);
                }
            }
        });


        btn_chequeimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentImageView=(ImageView) view;
                ImageType="Cheque";
                showPictureDialog();
                tvCheque.setText("cheque");

            }
        });


        btn_adharimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //currentImageView=(ImageView) view;
                ImageType="Aadhar";
                showPictureDialog();
                tvAdhar.setText("adhar");

            }
        });
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

            if(ImageType=="Cheque"){
                ChequeImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            }
            if(ImageType=="Aadhar"){
                AdharImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
            }

            if(ImageType=="Cheque"){

                cheque_image.setImageBitmap(bm);
            }
            if(ImageType=="Aadhar"){

                adhar_image.setImageBitmap(bm);
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

        if(ImageType=="Cheque"){
            ChequeImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }
        if(ImageType=="Aadhar"){
            AdharImageBlob = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
        }

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


        if(ImageType=="Cheque"){
        imageBitmap = (Bitmap) data.getExtras().get("data");
        cheque_image.setImageBitmap(imageBitmap);
        }
        if(ImageType=="Aadhar"){
            imageBitmap = (Bitmap) data.getExtras().get("data");
            adhar_image.setImageBitmap(imageBitmap);
        }
    }


}




