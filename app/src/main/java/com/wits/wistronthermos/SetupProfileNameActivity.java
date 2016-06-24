package com.wits.wistronthermos;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import java.io.File;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;


public class SetupProfileNameActivity extends Activity {
    ImageButton avatar;
    TextView skip,cancel,next,mUserTextview;
    EditText edit_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_up_profile_name);
        avatar = (ImageButton)findViewById(R.id.avatar);
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
                Intent intent = new Intent(SetupProfileNameActivity.this, SetupProfilePhotoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });
        next = (TextView)findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //setup name
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.preference_user_name), edit_name.getText().toString());
                editor.commit();

                Intent intent = new Intent(SetupProfileNameActivity.this, SetupProfilePhotoActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        //setup sharedpreference
        sharedPref = SetupProfileNameActivity.this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        //Edittext
        edit_name = (EditText)findViewById(R.id.edit_name);

        //user_name
        mUserTextview = (TextView)findViewById(R.id.mUserTextview);

    }

    SharedPreferences sharedPref;
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(0, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //set user name
        String user_name = sharedPref.getString(getString(R.string.preference_user_name),"");
        mUserTextview.setText(user_name);

        //set user profile photo
        String realPath = sharedPref.getString(getString(R.string.preference_user_photo_real_path),"");
        if(realPath.contains("http")){

        }else{
            Glide.with(SetupProfileNameActivity.this)
                    .load(new File(realPath))
                    .centerCrop()
                    .bitmapTransform(new CenterCrop(SetupProfileNameActivity.this),new RoundedCornersTransformation(SetupProfileNameActivity.this,200,0))
                    .error(R.mipmap.unknow)
                    .into(avatar);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
