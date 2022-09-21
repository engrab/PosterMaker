package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter.AdapterPager;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;

public class TemplatesActivity extends FragmentActivity implements MaterialTabListener {
    public static final int OPEN_UPDATE_ACITIVITY_TEMP = 1124;
    AdapterPager adapter;
    private Editor editor;
    boolean isChanged = false;
    ViewPager pager;
    SharedPreferences prefs;
    MaterialTabHost tabHost;
    private Typeface ttf;
    private Typeface ttfHeader;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_templates);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.isChanged = this.prefs.getBoolean("isChanged", false);
        ((TextView) findViewById(R.id.txt_appname)).setTypeface(this.ttfHeader);
        initUI();
        findViewById(R.id.btn_bck).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                TemplatesActivity.this.onBackPressed();

            }
        });
    }

    private void initUI() {
        this.tabHost = findViewById(R.id.tabHost);
        this.pager = findViewById(R.id.pager);
        this.adapter = new AdapterPager(this, getSupportFragmentManager());
        this.adapter.notifyDataSetChanged();
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                TemplatesActivity.this.tabHost.setSelectedNavigationItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        for (int i = 0; i < this.adapter.getCount(); i++) {
            this.tabHost.addTab(this.tabHost.newTab().setText(this.adapter.getPageTitle(i)).setTabListener(this));
        }
    }

    public void onTabSelected(MaterialTab tab) {
        if (this.pager != null) {
            this.pager.setCurrentItem(tab.getPosition(), true);
        }
    }

    public void onTabReselected(MaterialTab tab) {
    }

    public void onTabUnselected(MaterialTab tab) {
    }

    protected void onResume() {
        super.onResume();
        this.isChanged = this.prefs.getBoolean("isChanged", false);
        if (this.isChanged) {
            this.tabHost.setSelectedNavigationItem(0);
            this.adapter.notifyDataSetChanged();
            this.pager.setCurrentItem(0, true);
            getSupportFragmentManager().beginTransaction().detach(this.adapter.currentFragment(this.pager.getCurrentItem())).attach(this.adapter.currentFragment(this.pager.getCurrentItem())).commit();
            this.pager.postInvalidate();
            this.editor.putBoolean("isChanged", false);
            this.editor.commit();
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.pager.getChildCount() != 0) {
            this.adapter.currentFragment(this.pager.getCurrentItem()).onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }
}
