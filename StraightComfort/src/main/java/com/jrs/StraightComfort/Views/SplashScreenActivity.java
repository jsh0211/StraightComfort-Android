package com.jrs.StraightComfort.Views;

/**
 * Created by JMtorii on 2014-03-19.
 */


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.jrs.StraightComfort.R;
import com.jrs.StraightComfort.Utilities.FilterActivity;
import com.jrs.StraightComfort.Utilities.FilterSCData;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SplashScreenActivity extends FilterActivity {
    private static final int TIME = 4 * 1000;// 4 seconds
    private static final String preference = "AppNameSettings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.splash_screen);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent =null;
                SharedPreferences sharedPreferences = getSharedPreferences(preference,0);
                boolean firstUser = sharedPreferences.getBoolean("firstUser",true);
                if (firstUser)
                {
                    intent = new Intent(SplashScreenActivity.this, WelcomePagerAdapter.class);
                    firstUser = false;
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("firstUser",firstUser);
                    editor.commit();
                }
                else {
                    intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                }
                startActivity(intent);
                SplashScreenActivity.this.finish();
                overridePendingTransition(R.anim.splash_fade_in, R.anim.splash_fade_out);
            }
        }, TIME);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    Context context = getApplicationContext();

                        filterscData().init(context);
                        filterscData().getPageInfo(context);
                        filterscData().getDiscomfortInfo();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }, TIME);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        super.onBackPressed();
    }
}