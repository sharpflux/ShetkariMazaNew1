package com.sharpflux.shetkarimaza.uploadimage;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;

import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import com.sharpflux.shetkarimaza.constants.Constatns;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by ptblr-1167 on 19/4/17.
 */

public class UploadImageHelper  {

    private static final int CAMERA_REQUEST = 1;
    private static final int RESULT_LOAD_IMAGE = 2;
    private Activity activity;
    private Fragment fragment;
    private UploadImageHelper.onImgaeUploadListener onImgaeUploadListener;
    String  camerapath;
    File myFile;
    Context context;
    Bitmap rotatedBitmap=null;
    private Uri mPhotoURI;

    private Intent cameraIntent,externalLibraryIntent;
    private String reqPermissions[]  = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,};
    public interface onImgaeUploadListener {
        void OnUploadSuccess(String imagePath, File imageFile, Bitmap imageBitmap);

    }

    public UploadImageHelper(Context context,Activity activity, UploadImageHelper.onImgaeUploadListener onImgaeUploadListener) {
        this.activity = activity;
        this.onImgaeUploadListener = onImgaeUploadListener;
        this.context = activity;
    }

    public UploadImageHelper(Context context,Fragment fragment, UploadImageHelper.onImgaeUploadListener onImgaeUploadListener) {
        this.fragment = fragment;
        this.onImgaeUploadListener = onImgaeUploadListener;
        this.context = context;
    }


    public void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library", "Cancel" };
        AlertDialog.Builder builder = new AlertDialog.Builder((Activity)context);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraIntent  = intent;
                    camerapath = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".jpg";
                    mPhotoURI = FileProvider.getUriForFile(context,
                            "com.supergo.customer.provider",
                            new File(camerapath) );
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, /*Uri.fromFile(new File(camerapath))*/mPhotoURI);
                    operateCamera(intent);

                } else if (items[item].equals("Choose from Library")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    operateLibrary(context, Manifest.permission.READ_EXTERNAL_STORAGE, (Activity) context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, Constatns.READ_EXTERNAL_STORAGE, Intent.createChooser(intent, "Select File"), RESULT_LOAD_IMAGE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void operateLibrary(Context context, String readExternalStorage, Activity context2, String[] permissions, int readExternalStorage2, Intent chooser, int resultLoadImage) {
        if (ContextCompat.checkSelfPermission(context, readExternalStorage) != PackageManager.PERMISSION_GRANTED) {
           /* if (ActivityCompat.shouldShowRequestPermissionRationale(context2,
                    readExternalStorage)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {*/
            // No explanation needed, we can request the permission.
            if(activity != null) {
                ActivityCompat.requestPermissions(context2,
                        permissions,
                        readExternalStorage2);
            } else if(fragment != null){
                fragment.requestPermissions(permissions,Constatns.READ_EXTERNAL_STORAGE);
            }
            //}
        } else {
            if (activity != null)
                activity.startActivityForResult(chooser, resultLoadImage);
            else
                fragment.startActivityForResult(chooser, resultLoadImage);
        }
    }

    private void operateCamera(final Intent intent) {

        if(!hasCameraCaptureAndAccessPermissions(context,reqPermissions)){
            if (activity != null) {
                ActivityCompat.requestPermissions((Activity) context,
                        reqPermissions,
                        Constatns.TAKE_PHOTO_FROM_CAMERA);
            } else if (fragment != null) {
                fragment.requestPermissions(reqPermissions, Constatns.TAKE_PHOTO_FROM_CAMERA);

            }
        }else {
            if (activity != null)
                activity.startActivityForResult(intent, CAMERA_REQUEST);
            else
                fragment.startActivityForResult(intent, CAMERA_REQUEST);
        }

    }

    public static boolean hasCameraCaptureAndAccessPermissions(Context context, String... permissions) {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }


    public void handleRequestPermissionResult(int requestCode,String permission,int isPermissionGranted){
        if(requestCode == Constatns.TAKE_PHOTO_FROM_CAMERA && isPermissionGranted == PackageManager.PERMISSION_GRANTED) {
            if (cameraIntent != null) {
                if (activity != null) {
                    activity.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
                else {
                    fragment.startActivityForResult(cameraIntent, CAMERA_REQUEST);
                }
            }
        }

        if(requestCode == Constatns.READ_EXTERNAL_STORAGE && isPermissionGranted ==  PackageManager.PERMISSION_GRANTED ){
            if(externalLibraryIntent != null){
                if(activity != null){
                    activity.startActivityForResult(externalLibraryIntent,RESULT_LOAD_IMAGE);
                } else if(fragment != null){
                    fragment.startActivityForResult(externalLibraryIntent,RESULT_LOAD_IMAGE);
                }
            }
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RESULT_LOAD_IMAGE && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };
            Cursor cursor =context.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath1 = cursor.getString(columnIndex);
                cursor.close();

                rotatedBitmap = rotateImage(picturePath1);
                new compressImage(rotatedBitmap, 1).execute();
                Log.d("image path", "" + myFile);
            }else {
                File myFile = new File(selectedImage.getPath());
                String filePath = myFile.getAbsolutePath();
                if (myFile.exists()) {
                    rotatedBitmap = rotateImage(filePath);
                    new compressImage(rotatedBitmap, 1).execute();
                    Log.d("image path", "" + myFile);
                }
            }

        }
        else if(requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK ) {
            if(camerapath!=null) {
                rotatedBitmap = rotateImage(camerapath);
                new compressImage(rotatedBitmap, 1).execute();
                Log.d("image path", "" + myFile);
                /*if(rotatedBitmap!=null) {
                    onImgaeUploadListener.OnUploadSuccess("", myFile, rotatedBitmap);
                }*/
            }


        }

    }

    class compressImage extends AsyncTask<Integer, Void, File> {
        private final Bitmap bitmap;
        private int image_flag;

        // Constructor
        public compressImage(Bitmap bitmap,int image_flag) {
            this.bitmap = bitmap;
            this.image_flag=image_flag;
        }

        // Compress and Decode image in background.
        @Override
        protected File doInBackground(Integer... params) {
            File image_str=reduceFileSize(bitmap);
            return image_str;
        }

        // This method is run on the UI thread
        @Override
        protected void onPostExecute(File file) {
            myFile = file;
            if(rotatedBitmap!=null) {
                onImgaeUploadListener.OnUploadSuccess("", myFile, rotatedBitmap);
            }
        }
    }

    public File reduceFileSize(Bitmap bitmap){
        // String  path = Environment.getExternalStorageDirectory() + File.separator + System.currentTimeMillis() + ".jpg";
        File filesDir = context.getFilesDir();
        File imageFile = new File(filesDir, System.currentTimeMillis() + ".jpg");

        OutputStream os;
        try {
            os = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, os);
            os.flush();
            os.close();
        } catch (Exception e) {
            Log.e(getClass().getSimpleName(), "Error writing bitmap", e);
        }



        return imageFile;
    }

    public Bitmap rotateImage(String path){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Bitmap photo = BitmapFactory.decodeFile(path);


        Bitmap newBitmapImage = null;
        ExifInterface ei = null;
        try {
            ei = new ExifInterface(path);

            int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_UNDEFINED);

            switch (orientation) {

                case ExifInterface.ORIENTATION_ROTATE_90:
                    newBitmapImage = getrotateImage(photo, 90);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_180:
                    newBitmapImage = getrotateImage(photo, 180);
                    break;

                case ExifInterface.ORIENTATION_ROTATE_270:
                    newBitmapImage = getrotateImage(photo, 270);
                    break;

                case ExifInterface.ORIENTATION_NORMAL:

                default:
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(newBitmapImage==null){
            return photo;
        }
        return newBitmapImage;
    }

    public Bitmap getrotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }


}
