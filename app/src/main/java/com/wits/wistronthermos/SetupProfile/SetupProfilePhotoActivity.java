package com.wits.wistronthermos.SetupProfile;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.cocosw.bottomsheet.BottomSheet;
import com.wits.wistronthermos.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import multipleimageselect.activities.AlbumSelectActivity;
import multipleimageselect.helpers.Constants;
import multipleimageselect.models.Image;


public class SetupProfilePhotoActivity extends Activity {
    private static final String TAG = "SetupProfilePhotoActivity";
    TextView skip,cancel,next,edit_photo,mUserTextview;
    ImageButton avatar;
    final static int SELECT_PICTURE = 200;
    final static int IMAGE_SHOOT = 201;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_photo);
        //set cancel
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        skip = (TextView)findViewById(R.id.skip);
        skip.setPaintFlags(skip.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);//add underline.= =
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupProfilePhotoActivity.this, SetupProfileEmailActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupProfilePhotoActivity.this, SetupProfileEmailActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        edit_photo = (TextView)findViewById(R.id.edit_photo);
        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(SetupProfilePhotoActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int cameraPermissionCheck = ContextCompat.checkSelfPermission(SetupProfilePhotoActivity.this, Manifest.permission.CAMERA);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED || cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            SetupProfilePhotoActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.CAMERA_WRITE_EXTERNAL_STORAGE);
                }else {
                    chooseImage();
                }
            }
        });
        avatar = (ImageButton)findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int permissionCheck = ContextCompat.checkSelfPermission(SetupProfilePhotoActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
                int cameraPermissionCheck = ContextCompat.checkSelfPermission(SetupProfilePhotoActivity.this, Manifest.permission.CAMERA);
                if (permissionCheck != PackageManager.PERMISSION_GRANTED || cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(
                            SetupProfilePhotoActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, Constants.CAMERA_WRITE_EXTERNAL_STORAGE);
                } else {
                    chooseImage();
                }
            }
        });

        //setup sharedpreference
        sharedPref = SetupProfilePhotoActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);


        //user_name
        mUserTextview = (TextView)findViewById(R.id.mUserTextview);
    }

    SharedPreferences sharedPref;
    @Override
    protected void onStart() {
        super.onStart();
        //set user name
        String user_name = sharedPref.getString(getString(R.string.preference_user_name),"");
        mUserTextview.setText(user_name);

        //set user profile photo
        String realPath = sharedPref.getString(getString(R.string.preference_user_photo_real_path),"");
        if(realPath.contains("http")){
            Glide.with(SetupProfilePhotoActivity.this)
                    .load(realPath)
                    .centerCrop()
                    .bitmapTransform(new CenterCrop(SetupProfilePhotoActivity.this),new RoundedCornersTransformation(SetupProfilePhotoActivity.this,200,0))
                    .error(R.mipmap.unknow)
                    .into(avatar);
        }else{
            Glide.with(SetupProfilePhotoActivity.this)
                    .load(new File(realPath))
                    .bitmapTransform(new CenterCrop(SetupProfilePhotoActivity.this),new RoundedCornersTransformation(SetupProfilePhotoActivity.this,200,0))
                    .error(R.mipmap.unknow)
                    .into(avatar);
        }

    }

    private void chooseImage() {

        new BottomSheet.Builder(SetupProfilePhotoActivity.this).sheet(R.menu.no_icon_bottomlist).listener(new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case R.id.choose_from_library:
                        // choose exist image start AlbumSelectActivity activity
                        Intent intent = new Intent(SetupProfilePhotoActivity.this, AlbumSelectActivity.class);
                        //set limit on number of images that can be selected, default is 10
                        intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                        startActivityForResult(intent, SELECT_PICTURE);
                        break;
                    case R.id.take_a_photo:
                        try {
                            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            // Ensure that there's a camera activity to handle the intent
                            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                                // Create the File where the photo should go
                                File photoFile = null;
                                photoFile = getOutputMediaFile();
                                // Continue only if the File was successfully created
                                if (photoFile != null) {
                                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                                    startActivityForResult(takePictureIntent, IMAGE_SHOOT);
                                }
                            }
                        } catch (Exception e) {
                            Toast.makeText(SetupProfilePhotoActivity.this, "image error", Toast.LENGTH_LONG).show();
                            Log.e(e.getClass().getName(), e.getMessage(), e);
                        }
                        break;
                    case R.id.cancel_select:
                        break;
                }
            }
        }).show();

    }

    String mCurrentPhotoPath="";
    /** Create a File for saving an image or video */
    private File getOutputMediaFile(){
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "ThermosBottle");
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
        File mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                "IMG_"+ timeStamp + ".jpg");
        mCurrentPhotoPath = mediaFile.getAbsolutePath();

        return mediaFile;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.CAMERA_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                    chooseImage();
                } else {
                    Toast.makeText(SetupProfilePhotoActivity.this,"please give me permissions...",Toast.LENGTH_LONG);
                }
                return;
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.e("mainactivity", "requestCode = " + requestCode);
        Log.e("mainactivity" , "resultCode = "+resultCode);
        switch (requestCode) {
            case SELECT_PICTURE:
                Log.d(TAG, "devon check onActivityResult requestCode" + requestCode);
                Log.d(TAG, "devon check onActivityResult resultCode" + resultCode);
                Log.d(TAG, "devon check onActivityResult data" + data);
                if (resultCode == RESULT_OK && data != null) {
                    ArrayList<Image> images = data.getParcelableArrayListExtra(Constants.INTENT_EXTRA_IMAGES);
                    //it will always return 1 pictures, due to limit the INTENT_EXTRA_LIMIT =1 in AlbumSelectActivity
                    //我們這裡只會拿一張照片 due to limit the NTENT_EXTRA_LIMIT =1 in AlbumSelectActivity
                    String realPath = images.get(0).path;
                    Log.d(TAG, "devon check realPath is = " + realPath);
//                    Uri fileUri = Uri.parse(new File(realPath).toString());//we don't need the Uri, maybe will.

                    //setup
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_user_photo_real_path), realPath);
                    editor.commit();

                    Glide.with(SetupProfilePhotoActivity.this)
                            .load(new File(realPath))
                            .bitmapTransform(new CenterCrop(SetupProfilePhotoActivity.this),new RoundedCornersTransformation(SetupProfilePhotoActivity.this,200,0))
                            .error(R.mipmap.unknow)
                            .into(avatar);

                } else if (resultCode == RESULT_CANCELED && data == null) {
                    //do nothing
                }
                break;
            case IMAGE_SHOOT:
                if (resultCode == RESULT_OK){
                    File f = new File(mCurrentPhotoPath);
                    Log.e(TAG,"file mCurrentPhotoPath is " + mCurrentPhotoPath);
                    //setup
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString(getString(R.string.preference_user_photo_real_path), mCurrentPhotoPath);
                    editor.commit();

                    Glide.with(SetupProfilePhotoActivity.this)
                            .load(new File(mCurrentPhotoPath))
                            .bitmapTransform(new CenterCrop(SetupProfilePhotoActivity.this),new RoundedCornersTransformation(SetupProfilePhotoActivity.this,200,0))
                            .error(R.mipmap.unknow)
                            .into(avatar);
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }

}
