package com.amt.postermaker.graphicdesign.flyer.bannermaker;

import android.app.Application;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.ads.AppOpenUtils;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                new AppOpenUtils(App.this);
            }
        });

    }
}
