package com.ciesto.evaafashion.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.ciesto.evaafashion.Other.Constant;
import com.ciesto.evaafashion.Other.ExtraPreferences;
import com.ciesto.evaafashion.Other.LoginPreferences;
import com.ciesto.evaafashion.R;
import com.ciesto.evaafashion.SharedPrefs.SharedPrefs;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.Locale;

public class LanguageSelectionActivity extends AppCompatActivity {

    protected TextView tvEnglish;
    protected TextView tvArabic;
    Activity activity = LanguageSelectionActivity.this;
    LoginPreferences loginPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        initView();
        initcomponent();
        SetListener();
    }

    private void initView() {
        tvEnglish = (TextView) findViewById(R.id.tv_english);
        tvArabic = (TextView) findViewById(R.id.tv_arabic);

        new ExtraPreferences(activity).setProductid("");
    }

    private void initcomponent() {
        loginPreferences = new LoginPreferences(activity);
    }

    private void SetListener() {
        tvEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExtraPreferences(activity).setLanguage("English");
                setLocale("en");
                ExtraPreferences extraPreferences = new ExtraPreferences(getApplicationContext());
                extraPreferences.setLanguage("English");
            }
        });
        tvArabic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ExtraPreferences(activity).setLanguage("Arabic");
                setLocale("ar");
                ExtraPreferences extraPreferences = new ExtraPreferences(getApplicationContext());
                extraPreferences.setLanguage("Arabic");
            }
        });
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            conf.setLocale(new Locale(lang)); // API 17+ only.
        } else {
            conf.locale = new Locale(lang);
        }

        res.updateConfiguration(conf, dm);

        if(new ExtraPreferences(activity).getDevicekey().equals(""))
        {
            new ExtraPreferences(activity).setDevicekey(Constant.FIREBASE_TOKEN);
        }

        if (loginPreferences.isLoggIn()) {
            startActivity(new Intent(activity, MainActivity.class));
            finish();
        } else {
            startActivity(new Intent(activity, LoginActivity.class));
            finish();
        }
    }
}
