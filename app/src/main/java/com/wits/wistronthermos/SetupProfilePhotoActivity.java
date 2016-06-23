package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import multipleimageselect.activities.AlbumSelectActivity;
import multipleimageselect.helpers.Constants;
import multipleimageselect.models.Image;


public class SetupProfilePhotoActivity extends Activity {
    private static final String TAG = "SetupProfilePhotoActivity";
    TextView skip,cancel,next,edit_photo;
    ImageButton avatar;
    final static int SELECT_PICTURE = 200;
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
        next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(SetupProfilePhotoActivity.this, SetupProfileNameActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
            }
        });
        edit_photo = (TextView)findViewById(R.id.edit_photo);
        edit_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupProfilePhotoActivity.this, AlbumSelectActivity.class);
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });
        avatar = (ImageButton)findViewById(R.id.avatar);
        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start AlbumSelectActivity activity
                Intent intent = new Intent(SetupProfilePhotoActivity.this, AlbumSelectActivity.class);
                //set limit on number of images that can be selected, default is 10
                intent.putExtra(Constants.INTENT_EXTRA_LIMIT, 1);
                startActivityForResult(intent, SELECT_PICTURE);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, Constants.WRITE_EXTERNAL_STORAGE);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the task you need to do.
                } else {
                    finish();
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
                    Uri fileUri = Uri.parse(new File(realPath).toString());
                    Glide.with(SetupProfilePhotoActivity.this)
                            .load(new File(realPath))
                            .centerCrop()
                            .bitmapTransform(new CropCircleTransformation(SetupProfilePhotoActivity.this))
                            .error(R.mipmap.unknow)
                            .into(avatar);



                    //not used
//                    BitmapFactory.Options options = new BitmapFactory.Options();
//                    options.inJustDecodeBounds = true;
//                    BitmapFactory.decodeFile(realPath, options);
//
//                    Matrix vMatrix = new Matrix();
//                    vMatrix.setRotate(readPictureDegree(realPath));
//
//                    if (options.outHeight > 4000 || options.outWidth > 4000) {
//                        options.inSampleSize = 2; // set inSampleSize =2
//                    }
//                    options.inJustDecodeBounds = false;
//                    Bitmap bm,bm2 ;
//                    int position ;
//                    bm = BitmapFactory.decodeFile(realPath, options);
//                    if(readPictureDegree(realPath) != 0) {
//                        bm2 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), vMatrix, true);
//                    }else{
//                    }

                } else if (resultCode == RESULT_CANCELED && data == null) {
                    //do nothing
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(0, 0);

    }
    public  static BitmapFactory.Options getOutFull(String photoPath) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(photoPath, options);
        return options;
    }

    public int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;

                default:

                    degree = 0;

                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }
}
