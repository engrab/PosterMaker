package com.amt.postermaker.graphicdesign.flyer.bannermaker.main;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;


import com.google.android.gms.ads.AdView;

import androidx.appcompat.app.AppCompatActivity;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.ads.AdsUtils;


public class QuitActivity extends AppCompatActivity {


    AdView adView;
    @Override
    protected void onDestroy() {
        if (adView != null){
            adView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quit);

        adView = AdsUtils.showMediumRectangle(this, findViewById(R.id.llAdds));


        findViewById(R.id.chip_give_us_rating).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });

        findViewById(R.id.btn_no).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(QuitActivity.this, MainActivity.class));

                finish();
            }
        });

        findViewById(R.id.btn_yes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(QuitActivity.this, MainActivity.class));
        finish();
    }


}