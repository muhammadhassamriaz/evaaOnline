package com.ciesto.evaafashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.Functions;
import com.ciesto.evaafashion.R;

public class SplashActivity extends AppCompatActivity {

    Runnable runnable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initBasicTask();
    }
    private void initBasicTask() {

        if(new ExtraPreferences(SplashActivity.this).getDevicekey().equals(""))
        {
            new ExtraPreferences(SplashActivity.this).setDevicekey(Constant.FIREBASE_TOKEN);
        }

       Handler handler = new Handler();


        handler.postDelayed(runnable = new Runnable() {
            @Override
            public void run() {
               /* if(getIntent().hasExtra("Type"))
                {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    intent.putExtra("Type", getIntent().getStringExtra("Type"));
                    finish();
                }
                else {*/
                    startActivity(new Intent(SplashActivity.this, LanguageSelectionActivity.class));
                    Functions.animNext(SplashActivity.this);
                    finish();
                //}

            }
        }, 1000);
       /* if(getIntent().hasExtra("Type"))
        {
            handler.removeCallbacks(runnable);
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("Type", getIntent().getStringExtra("Type"));
            startActivity(intent);
            finish();
        }*/

    }
}
