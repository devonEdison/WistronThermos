package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class SetupProfileEmailActivity extends Activity {
    private static final String TAG = "SetupProfileEmailActivity";
    TextView skip,cancel,next,edit_photo,mUserTextview,emailRegisterInfo;
    ImageButton avatar;
    EditText edit_email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_profile_email);
        //avatar
        avatar = (ImageButton)findViewById(R.id.avatar);
        //user textview
        mUserTextview = (TextView)findViewById(R.id.mUserTextview);
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
//                Intent intent = new Intent(SetupProfileEmailActivity.this, SetupProfilePhotoActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
            }
        });
        next = (TextView)findViewById(R.id.next);
        next.setEnabled(false);
        next.setAlpha((float) 0.5);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setup name
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.preference_user_email), edit_email.getText().toString());
                editor.commit();

//                Intent intent = new Intent(SetupProfileNameActivity.this, SetupProfilePhotoActivity.class);
//                startActivity(intent);
//                overridePendingTransition(0, 0);
            }
        });


        //email info
        emailRegisterInfo = (TextView)findViewById(R.id.emailRegisterInfo);
        String emailInfo = getString(R.string.emailRegisterInfo);
        emailRegisterInfo.setMovementMethod(LinkMovementMethod.getInstance());

        //email edittext
        edit_email = (EditText) findViewById(R.id.edit_email);


        //setup sharedpreference
        sharedPref = SetupProfileEmailActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
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
        if(realPath.contains("https://")){
            Glide.with(SetupProfileEmailActivity.this)
                    .load(realPath)
                    .centerCrop()
                    .bitmapTransform(new CenterCrop(SetupProfileEmailActivity.this),new RoundedCornersTransformation(SetupProfileEmailActivity.this,200,0))
                    .error(R.mipmap.unknow)
                    .into(avatar);
        }else{
            Glide.with(SetupProfileEmailActivity.this)
                    .load(new File(realPath))
                    .centerCrop()
                    .bitmapTransform(new CenterCrop(SetupProfileEmailActivity.this),new RoundedCornersTransformation(SetupProfileEmailActivity.this,200,0))
                    .error(R.mipmap.unknow)
                    .into(avatar);
        }
    }
    Handler mHandler = null ;
    Runnable checkInputText =null;
    private boolean isInFront =false;
    @Override
    protected void onResume() {
        super.onResume();
        //start button listener
        isInFront = true;
        if (mHandler== null){
            mHandler = new Handler();
            mHandler.postDelayed(checkInputText = new Runnable() {
                @Override
                public void run() {
                    if(isInFront){
                        if (edit_email != null) {
                            //set button color
                            if (!edit_email.getText().toString().equals("")) {
                                next.setAlpha((float) 1.0);
                                next.setEnabled(true);
                            } else {
                                next.setAlpha((float) 0.5);
                                next.setEnabled(false);
                            }
                        }

                        //run again
                        if (mHandler != null){
                            mHandler.postDelayed(this,1000);
                        }
                    }
                }
            },1000);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        isInFront = false;
        mHandler = null;
        checkInputText = null;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, 0);
    }
}
