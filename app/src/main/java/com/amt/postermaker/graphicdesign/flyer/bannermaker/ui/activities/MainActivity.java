package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.Intent.ShortcutIconResource;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.internal.view.SupportMenu;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.ads.AdsUtils;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.db.DatabaseHandler;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.TemplateInfo;
import com.google.android.gms.ads.AdView;
import com.google.android.material.navigation.NavigationView;
import com.msl.demo.view.ComponentInfo;
import com.msl.textmodule.TextInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity implements OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    private static final int PERMISSIONS_REQUEST = 100;
    private static final int REQUEST_PERMISSION = 101;
    public static int height1;
    public static float ratio;
    public static int width;
    private final Handler handler = new Handler();
    public SharedPreferences prefs;
    SharedPreferences appPreferences;
    int count = 0;
    boolean isAppInstalled = false;
    //    LinearLayout layView;
    AdView mAdView;
    SharedPreferences preferences;
    ArrayList<String> stkrNameList;
    Typeface ttHADER;
    Typeface ttText;
    //    TextView txt_load;
    DrawerLayout drawer;
    ProgressDialog progressDialog;
    private Editor editor;
    private boolean isDataStored = false;
    private boolean isOpenFisrtTime = false;
    private Runnable runnable;
    private NavigationView navigationView;

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawer.closeDrawers();
        switch (item.getItemId()) {

            case R.id.action_privacy:

                startActivity(new Intent(MainActivity.this, PrivacyActivity.class));
                return true;

            case R.id.action_more:

                String url = "https://play.google.com/store/apps/developer?id=Lala+Apps+Studio";
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
                return true;

            case R.id.action_rate:

                String url1 = "https://play.google.com/store/apps/details?id=" + getPackageName();
                Uri uri1 = Uri.parse(url1);
                Intent intent1 = new Intent(Intent.ACTION_VIEW, uri1);
                if (intent1.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent1);
                }
                return true;
            case R.id.action_share:

                shareApp();

                return true;


        }

        return false;
    }

    public void shareApp(){
        try {
            String text = "Download Poster Maker App share with friends and family member with built in beautiful design\n https://play.google.com/store/apps/details?id=" + getPackageName();
            Intent txtIntent = new Intent(android.content.Intent.ACTION_SEND);
            txtIntent.setType("text/plain");
            txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Poster Maker");
            txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, text);
            startActivity(Intent.createChooser(txtIntent, "Share Poster Maker App"));
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "can not share text", Toast.LENGTH_SHORT).show();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AdsUtils.showInlineBanner(this, findViewById(R.id.llAds));

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawer_layout);
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(MainActivity.this);
        progressDialog = new ProgressDialog(this);

        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor.putString("rating123", "yes");
        this.editor.commit();

        this.ttHADER = Constants.getHeaderTypeface(this);
        this.ttText = Constants.getTextTypeface(this);
        this.appPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        this.isAppInstalled = this.appPreferences.getBoolean("isAppInstalled", false);
        this.isDataStored = this.appPreferences.getBoolean("isDataStored", false);
        if (!this.isAppInstalled) {
            createShortCut();
            Editor editor = this.appPreferences.edit();
            editor.putBoolean("isAppInstalled", true);
            editor.commit();
        }
        findViewById(R.id.ivCreatePoster).setOnClickListener(this);
        findViewById(R.id.ivFreeTemplate).setOnClickListener(this);
        findViewById(R.id.ivShareApp).setOnClickListener(this);
        findViewById(R.id.ivMyWork).setOnClickListener(this);
        Typeface ttf = Constants.getHeaderTypeface(this);
//        this.layView = findViewById(R.id.layView);
//        this.txt_load = findViewById(R.id.txt_load);
        this.stkrNameList = new ArrayList();
        this.stkrNameList.clear();
        for (int k = 1; k <= 7; k++) {
            this.stkrNameList.add("bh" + k);
        }
        if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1");
            File[] files = file.listFiles();
            if (!file.exists()) {
                downloadStickers();
                ViewTextAnimation();
                return;
            } else if (files.length >= this.stkrNameList.size()) {
//                this.layView.setVisibility(View.GONE);
//                this.txt_load.setVisibility(View.VISIBLE);
                ViewTextAnimation();
                init("yes");
                return;
            } else {
                downloadStickers();
                ViewTextAnimation();
                return;
            }
        } else {

            permissionDialog();
        }


    }

    private void ViewTextAnimation() {

        progressDialog.setMessage("Loading Data");
        progressDialog.setCancelable(false);
        progressDialog.show();
        Handler handler = this.handler;
        Runnable running_thread = new running_thread();
        this.runnable = running_thread;
        handler.postDelayed(running_thread, 400);
    }

    private void HideTextAnimation() {

        this.handler.removeCallbacks(this.runnable);
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
//        this.layView.setVisibility(View.VISIBLE);
//        this.txt_load.setVisibility(View.GONE);
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivShareApp:
                shareApp();
                return;
            case R.id.ivMyWork:
                startActivity(new Intent(this, MyCreationActivity.class));
                return;
            case R.id.ivCreatePoster:
                startActivity(new Intent(this, SelectImageActivity.class));
                return;
            case R.id.ivFreeTemplate:
                startActivity(new Intent(this, TemplatesActivity.class));
                return;
            default:
                return;
        }
    }

    public void createShortCut() {
        Intent shortcutintent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        shortcutintent.putExtra("duplicate", false);
        shortcutintent.putExtra("android.intent.extra.shortcut.NAME", getString(R.string.app_name));
        shortcutintent.putExtra("android.intent.extra.shortcut.ICON_RESOURCE", ShortcutIconResource.fromContext(getApplicationContext(), R.mipmap.ic_launcher));
        shortcutintent.putExtra("android.intent.extra.shortcut.INTENT", new Intent(getApplicationContext(), MainActivity.class));
        sendBroadcast(shortcutintent);
    }

    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.close();
        } else {
            startActivity(new Intent(MainActivity.this, QuitActivity.class));
            finish();

        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && VERSION.SDK_INT >= 23) {
            if (checkSelfPermission("android.permission.CAMERA") != 0 || checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED || checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") != PackageManager.PERMISSION_GRANTED) {
                permissionDialog();
            }
        }
    }

    public void permissionDialog() {
        final Dialog dialog = new Dialog(this, 16974128);
        dialog.setContentView(R.layout.permissionsdialog);
        dialog.setTitle(getResources().getString(R.string.permission));
        dialog.setCancelable(false);
        dialog.findViewById(R.id.ok).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if (VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{"android.permission.CAMERA", "android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"}, 100);
                }
                dialog.dismiss();
            }
        });
        if (this.isOpenFisrtTime) {
            Button setting = dialog.findViewById(R.id.settings);
            setting.setVisibility(View.VISIBLE);
            setting.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.fromParts("package", MainActivity.this.getPackageName(), null));
                    intent.addFlags(268435456);
                    MainActivity.this.startActivityForResult(intent, 101);
                    dialog.dismiss();
                }
            });
        }
        dialog.show();
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            File file;
            File[] files;
            if (grantResults[0] == 0) {
                if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
                    file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1");
                    files = file.listFiles();
                    if (!file.exists()) {
                        downloadStickers();
                        ViewTextAnimation();
                        return;
                    } else if (files.length >= this.stkrNameList.size()) {
                        ViewTextAnimation();
//                        this.layView.setVisibility(View.GONE);
//                        this.txt_load.setVisibility(View.VISIBLE);
                        new DummyAsync().execute("");
                        return;
                    } else {
                        downloadStickers();
                        ViewTextAnimation();
                        return;
                    }
                }
                this.isOpenFisrtTime = true;
                permissionDialog();
            } else if (VERSION.SDK_INT < 23 || (checkSelfPermission("android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED && checkSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED)) {
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1");
                files = file.listFiles();
                if (!file.exists()) {
                    downloadStickers();
                    ViewTextAnimation();
                } else if (files.length >= this.stkrNameList.size()) {
                    ViewTextAnimation();
//                    this.layView.setVisibility(View.GONE);
//                    this.txt_load.setVisibility(View.VISIBLE);
                    new DummyAsync().execute("");
                } else {
                    downloadStickers();
                    ViewTextAnimation();
                }
            } else {
                this.isOpenFisrtTime = true;
                permissionDialog();
            }
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void downloadStickers() {
        try {
            File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1");
            if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                File rootFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file = new File(rootFolder, getString(R.string.app_name) + "/.Poster Maker Stickers/category1");
                File[] files = pictureFileDir.listFiles();
                int i;
                if (file.exists()) {
                    for (i = 0; i < this.stkrNameList.size(); i++) {
                        if (!new File(rootFolder, getString(R.string.app_name) + "/.Poster Maker Stickers/category1/" + this.stkrNameList.get(i) + ".png").exists()) {
                            new SaveStickersAsync().execute(this.stkrNameList.get(i), "" + i);
                        } else if (files.length >= this.stkrNameList.size()) {
                            HideTextAnimation();
                        }
                    }
                    return;
                }
                for (i = 0; i < this.stkrNameList.size(); i++) {
                    if (!new File(rootFolder, getString(R.string.app_name) + "/.Poster Maker Stickers/category1/" + this.stkrNameList.get(i) + ".png").exists()) {
                        new SaveStickersAsync().execute(this.stkrNameList.get(i), "" + i);
                    } else if (files.length >= this.stkrNameList.size()) {
                        HideTextAnimation();
                    }
                }
                return;
            }
            Log.d("", "Can't create directory to save image.");
            Toast.makeText(this, getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }

    private void init(String st) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        width = size.x;
        height1 = size.y;
        ratio = ((float) width) / ((float) height1);
        if (this.isDataStored) {
            if (st.equals("yes")) {
                HideTextAnimation();
                return;
            }
            return;
        }
        DatabaseHandler dh = DatabaseHandler.getDbHandler(getApplicationContext());
        int templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_4", "b44", "1:1", "Background", "90", "FREESTYLE", "", "", "o1", 114, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(17.178528f), Constants.getNewY(607.0f), Constants.getNewWidth(1037.0f), Constants.getNewHeight(823.0f), 0.0f, 0.0f, "g_28", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(20.678528f), Constants.getNewY(-352.0f), Constants.getNewWidth(1037.0f), Constants.getNewHeight(823.0f), 0.0f, 0.0f, "g_28", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(801.919f), Constants.getNewY(270.0f), Constants.getNewWidth(277.0f), Constants.getNewHeight(305.0f), 0.0f, 0.0f, "a_5", "STICKER", 8, 0, 0, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(740.5f), Constants.getNewY(213.5f), Constants.getNewWidth(309.0f), Constants.getNewHeight(337.0f), 0.0f, 0.0f, "a_5", "STICKER", 9, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "VISIT US AT 002, 2ND STREET EAST, CITY (123) 456 789", "font6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(172.0f), Constants.getNewY(804.0f), Constants.getNewWidth(731.0f), Constants.getNewHeight(99.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "F R E S H   F L O W E R S   F O R   E V E R Y   O C C A S S I O N", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(218.01721f), Constants.getNewY(656.0f), Constants.getNewWidth(647.0f), Constants.getNewHeight(119.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.BLOOMFLOWERS.COM", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(303.0f), Constants.getNewY(903.0f), Constants.getNewWidth(473.0f), Constants.getNewHeight(105.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FLOWERS", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(393.0f), Constants.getNewY(190.0f), Constants.getNewWidth(295.0f), Constants.getNewHeight(141.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "THE BLOOM", "ffont7.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(125.0f), Constants.getNewY(65.0f), Constants.getNewWidth(819.0f), Constants.getNewHeight(149.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "F  O  R Y  O  U", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 2, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(377.0f), Constants.getNewY(326.76074f), Constants.getNewWidth(327.0f), Constants.getNewHeight(301.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "10 % OFF", "ffont3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(816.9211f), Constants.getNewY(270.93372f), Constants.getNewWidth(189.0f), Constants.getNewHeight(173.0f), 23.50966f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1 DAY ONLY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(767.00006f), Constants.getNewY(412.86566f), Constants.getNewWidth(193.0f), Constants.getNewHeight(79.0f), 22.982195f, "TEXT", 11, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_5", "b15", "1:1", "Background", "90", "FREESTYLE", "", "", "o6", 125, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(146.00006f), Constants.getNewY(79.0f), Constants.getNewWidth(781.0f), Constants.getNewHeight(763.0f), -90.0f, 0.0f, "sh14", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(468.5f), Constants.getNewY(499.0f), Constants.getNewWidth(701.0f), Constants.getNewHeight(607.0f), 0.0f, 0.0f, "g_24", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-91.5f), Constants.getNewY(495.0f), Constants.getNewWidth(701.0f), Constants.getNewHeight(607.0f), 0.0f, 0.0f, "g_24", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(767.00006f), Constants.getNewY(231.50015f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), -15.349043f, 0.0f, "a_4", "STICKER", 8, 0, 100, 0, 0, 0, 0, "", "colored", 23, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(102.0f), Constants.getNewY(33.0f), Constants.getNewWidth(873.0f), Constants.getNewHeight(145.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "MAKE A LIST", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(232.23907f), Constants.getNewY(239.0f), Constants.getNewWidth(611.0f), Constants.getNewHeight(163.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(304.4713f), Constants.getNewY(1002.5f), Constants.getNewWidth(471.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Get Ready For This Year's Grand Sale !", "ffont18.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 62, Constants.getNewX(177.0f), Constants.getNewY(495.0f), Constants.getNewWidth(719.0f), Constants.getNewHeight(151.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DECEMBER 20, 2020 SOUTHTOWN MALL, CITY", "ffont14.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(240.0f), Constants.getNewY(849.0f), Constants.getNewWidth(597.0f), Constants.getNewHeight(157.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CHECK IT TWICE", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(162.0f), Constants.getNewY(370.0f), Constants.getNewWidth(759.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_7", "", "1:1", "Color", "90", "FREESTYLE", "", "ffffe4b4", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(77.0f), Constants.getNewY(68.0f), Constants.getNewWidth(925.0f), Constants.getNewHeight(557.0f), 0.0f, 0.0f, "", "STICKER", 2, 0, 100, 0, 0, 0, 0, "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh5.png", "colored", 4, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(151.99493f), Constants.getNewY(484.005f), Constants.getNewWidth(763.0f), Constants.getNewHeight(629.0f), 0.0f, 0.0f, "e_3", "STICKER", 3, 0, 100, 0, 0, 0, 0, "", "colored", 78, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(72.646515f), Constants.getNewY(-173.99994f), Constants.getNewWidth(933.0f), Constants.getNewHeight(1039.0f), -90.0f, 0.0f, "sh14", "STICKER", 5, -7773943, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(83.99982f), Constants.getNewY(-155.88539f), Constants.getNewWidth(911.0f), Constants.getNewHeight(1003.0f), -90.0f, 0.0f, "sh14", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "M I S S I N G", "ffont18.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(386.0f), Constants.getNewY(669.0f), Constants.getNewWidth(303.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Last seen at 12th cor. 20th sts. City", "ffont18.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(210.0f), Constants.getNewY(876.0f), Constants.getNewWidth(655.0f), Constants.getNewHeight(115.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "REWARD $200", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(233.5f), Constants.getNewY(771.922f), Constants.getNewWidth(605.0f), Constants.getNewHeight(89.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Please Call Alex At (123) 456 789 For Any Leads.", "ffont18.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 77, Constants.getNewX(0.5f), Constants.getNewY(993.8727f), Constants.getNewWidth(1079.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_9", "t59", "1:1", "Color", "202", "FREESTYLE", "", "ffffffff", "o6", 0, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-98.0f), Constants.getNewY(-120.0f), Constants.getNewWidth(495.0f), Constants.getNewHeight(311.0f), 0.0f, 0.0f, "g_16", "STICKER", 4, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(686.0f), Constants.getNewY(-118.5f), Constants.getNewWidth(495.0f), Constants.getNewHeight(311.0f), 0.0f, 0.0f, "g_16", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(266.0f), Constants.getNewY(-121.37366f), Constants.getNewWidth(551.0f), Constants.getNewHeight(283.0f), 0.0f, 0.0f, "g_16", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(357.6632f), Constants.getNewY(337.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, 0.0f, "g_22", "STICKER", 9, 0, 100, 0, 0, 0, 0, "", "colored", 6, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "D O  Y O U  K N O W  H O W  L O V E D  Y O U  A R E ?", "ffont14.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(85.0f), Constants.getNewY(405.0f), Constants.getNewWidth(911.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Please join us for a baby shower honoring", "ffont4.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(176.80322f), Constants.getNewY(558.5f), Constants.getNewWidth(729.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Angela", "font3.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(326.0f), Constants.getNewY(633.0f), Constants.getNewWidth(465.0f), Constants.getNewHeight(269.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Saturday, 28th of March 2:30 in the afternoon The Paradise, Town, City", "ffont14.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(285.0f), Constants.getNewY(897.0f), Constants.getNewWidth(511.0f), Constants.getNewHeight(175.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "TWINKLE  TWINKLE", "ffont14.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(315.03717f), Constants.getNewY(131.0f), Constants.getNewWidth(459.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "little star", "font3.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(143.5f), Constants.getNewY(185.0f), Constants.getNewWidth(781.0f), Constants.getNewHeight(349.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_10", "b13", "1:1", "Background", "90", "FREESTYLE", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(61.0f), Constants.getNewY(-21.0f), Constants.getNewWidth(955.0f), Constants.getNewHeight(1117.0f), 0.0f, 0.0f, "sh22", "STICKER", 0, ViewCompat.MEASURED_STATE_MASK, 72, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(55.0f), Constants.getNewY(-15.550537f), Constants.getNewWidth(967.0f), Constants.getNewHeight(1271.0f), 0.0f, 0.0f, "sh5", "STICKER", 10, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "Masquerade", "ffont20.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(155.0f), Constants.getNewY(242.0f), Constants.getNewWidth(771.0f), Constants.getNewHeight(275.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "================================", "", -8294304, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(134.92383f), Constants.getNewY(202.92383f), Constants.getNewWidth(811.0f), Constants.getNewHeight(109.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "=============================", "", -8689062, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(140.00153f), Constants.getNewY(593.99695f), Constants.getNewWidth(799.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "December 20th", "font38.TTF", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(317.0f), Constants.getNewY(667.0f), Constants.getNewWidth(439.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "=============================", "", -8425892, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(131.0f), Constants.getNewY(735.0f), Constants.getNewWidth(815.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Ball", "ffont20.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(349.0f), Constants.getNewY(432.0f), Constants.getNewWidth(335.0f), Constants.getNewHeight(207.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "=============================", "", -8162718, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(126.5f), Constants.getNewY(850.0f), Constants.getNewWidth(821.0f), Constants.getNewHeight(95.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NEW STREET HOTEL    ||    8 IN THE EVENING", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(128.0f), Constants.getNewY(798.0f), Constants.getNewWidth(827.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "T H E  R O C K H O U S E  C L U B  25TH  A N N U A L", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(212.0f), Constants.getNewY(152.0f), Constants.getNewWidth(643.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_11", "b16", "1:1", "Background", "90", "FREESTYLE", "", "", "", 80, 223));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-123.0f), Constants.getNewY(569.5f), Constants.getNewWidth(937.0f), Constants.getNewHeight(887.0f), 0.0f, 0.0f, "i_15", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(132.5f), Constants.getNewY(70.6991f), Constants.getNewWidth(807.0f), Constants.getNewHeight(881.0f), 0.0f, 0.0f, "sh30", "STICKER", 1, ViewCompat.MEASURED_STATE_MASK, 47, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(290.4878f), Constants.getNewY(557.5f), Constants.getNewWidth(937.0f), Constants.getNewHeight(887.0f), 0.0f, -180.0f, "i_15", "STICKER", 9, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(119.1333f), Constants.getNewY(16.0f), Constants.getNewWidth(837.0f), Constants.getNewHeight(991.0f), 0.0f, 0.0f, "sh11", "STICKER", 12, 0, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "LIVE", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 12, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(254.24512f), Constants.getNewY(158.24512f), Constants.getNewWidth(565.0f), Constants.getNewHeight(355.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CONCERT", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 12, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(292.5f), Constants.getNewY(431.0f), Constants.getNewWidth(491.0f), Constants.getNewHeight(221.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "__________________", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(442.83313f), Constants.getNewY(616.34186f), Constants.getNewWidth(389.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SEPTEMBER | 2020", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 12, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(430.69452f), Constants.getNewY(664.755f), Constants.getNewWidth(411.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "__________________", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(441.67615f), Constants.getNewY(676.3419f), Constants.getNewWidth(389.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "2", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 12, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(236.0f), Constants.getNewY(612.0f), Constants.getNewWidth(199.0f), Constants.getNewHeight(185.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Your Entertainment Presents", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(150.92786f), Constants.getNewY(-15.428894f), Constants.getNewWidth(783.0f), Constants.getNewHeight(127.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1100 WESTERN STREET, COUNTRY", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(228.0f), Constants.getNewY(955.0f), Constants.getNewWidth(599.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(334.0f), Constants.getNewY(1005.0f), Constants.getNewWidth(399.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ELVIS | CHRIS SHINN", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 14, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(347.5f), Constants.getNewY(784.0f), Constants.getNewWidth(379.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 13, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_16", "", "1:1", "Color", "90", "FREESTYLE", "", "ffffffff", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(159.0f), Constants.getNewY(446.5f), Constants.getNewWidth(755.0f), Constants.getNewHeight(687.0f), 0.0f, 0.0f, "e_3", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 92, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(225.5f), Constants.getNewY(20.0f), Constants.getNewWidth(641.0f), Constants.getNewHeight(639.0f), 0.0f, 0.0f, "", "STICKER", 5, 0, 100, 0, 0, 0, 0, "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh4.png", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(206.91235f), Constants.getNewY(-7.0f), Constants.getNewWidth(679.0f), Constants.getNewHeight(695.0f), 0.0f, 0.0f, "sh11", "STICKER", 6, -12103933, 100, 0, 0, 0, 0, "", "white", 95, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "M I S S I N G", "ffont18.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(386.0f), Constants.getNewY(669.0f), Constants.getNewWidth(303.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "REWARD 200 $", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(235.5f), Constants.getNewY(765.0f), Constants.getNewWidth(605.0f), Constants.getNewHeight(89.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Last seen at 12th cor. 20th sts. City", "ffont18.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(210.0f), Constants.getNewY(876.0f), Constants.getNewWidth(655.0f), Constants.getNewHeight(115.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Please Call Alex At (123) 456 789 For Any Leads.", "ffont18.otf", -11841534, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(48.0f), Constants.getNewY(991.0f), Constants.getNewWidth(981.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_17", "b45", "1:1", "Background", "90", "FREESTYLE", "", "", "", 80, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(49.27881f), Constants.getNewY(2.0f), Constants.getNewWidth(983.0f), Constants.getNewHeight(635.0f), 0.0f, 0.0f, "", "STICKER", 4, 0, 100, 0, 0, 0, 0, "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh1.png", "colored", 1, 0, "-137,35", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(31.5f), Constants.getNewY(-230.73828f), Constants.getNewWidth(1019.0f), Constants.getNewHeight(1101.0f), -90.0f, 0.0f, "sh14", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-326,-367", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BABY MARIELLA", "font3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 2, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(265.0f), Constants.getNewY(689.0f), Constants.getNewWidth(553.0f), Constants.getNewHeight(195.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-141,65", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "RSVP TO JENNIFER AT (123) 456 789", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 2, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(235.0f), Constants.getNewY(1005.0f), Constants.getNewWidth(617.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "-462,23", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1ST BIRTHDAY", "ffont4.ttf", -13421773, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(499.28082f), Constants.getNewY(803.7013f), Constants.getNewWidth(339.0f), Constants.getNewHeight(89.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "932,18", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "April 20, 2020 • 3 pm Your Place 1234 Street, Town, Country", "ffont10.ttf", -13421773, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(260.5f), Constants.getNewY(913.0f), Constants.getNewWidth(557.0f), Constants.getNewHeight(99.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-162,13", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "You are cordially invited to", "ffont4.ttf", -13421773, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(324.45563f), Constants.getNewY(626.03394f), Constants.getNewWidth(443.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "41,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_20", "b7", "1:1", "Temp_Path", "90", "FREESTYLE", "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh6.png", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-121.46063f), Constants.getNewY(0.62127686f), Constants.getNewWidth(675.0f), Constants.getNewHeight(481.0f), 0.0f, 0.0f, "b_19", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NOW", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 20, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(301.0f), Constants.getNewY(796.0f), Constants.getNewWidth(493.0f), Constants.getNewHeight(115.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Address, Street, Town/City, Country", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 176, Constants.getNewX(0.0f), Constants.getNewY(917.5f), Constants.getNewWidth(1085.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "www.yourcompany.com  | (123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 176, Constants.getNewX(0.0f), Constants.getNewY(999.0f), Constants.getNewWidth(1151.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_19", "b15", "4:3", "Background", "90", "FREESTYLE", "", "", "o6", 47, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(338.7677f), Constants.getNewY(-140.2677f), Constants.getNewWidth(405.0f), Constants.getNewHeight(415.0f), -90.0f, 0.0f, "sh14", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(511.46112f), Constants.getNewY(222.5f), Constants.getNewWidth(371.0f), Constants.getNewHeight(327.0f), 0.0f, 0.0f, "e_4", "STICKER", 3, 0, 100, 0, 0, 0, 0, "", "colored", 7, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(199.0f), Constants.getNewY(222.5f), Constants.getNewWidth(371.0f), Constants.getNewHeight(327.0f), 0.0f, -180.0f, "e_4", "STICKER", 4, 0, 100, 0, 0, 0, 0, "", "colored", 7, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "You Are Invited To A", "ffont20.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(347.17267f), Constants.getNewY(0.32733154f), Constants.getNewWidth(385.0f), Constants.getNewHeight(183.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "GRAND OPENING", "ffont24.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(156.5f), Constants.getNewY(189.0f), Constants.getNewWidth(765.0f), Constants.getNewHeight(145.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "RIBBON CUTTING", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(308.0f), Constants.getNewY(356.0f), Constants.getNewWidth(465.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CEREMONY", "ffont24.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(274.51556f), Constants.getNewY(247.5f), Constants.getNewWidth(525.0f), Constants.getNewHeight(525.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "THE NEW PLACE", "ffont24.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(382.0f), Constants.getNewY(670.0f), Constants.getNewWidth(323.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Call Us At : (123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(313.0f), Constants.getNewY(726.0f), Constants.getNewWidth(455.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY 4TH OF JULY AT 8 PM", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 139, Constants.getNewX(0.0f), Constants.getNewY(583.0f), Constants.getNewWidth(1185.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_1", "b42", "3:4", "Background", "90", "FREESTYLE", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(2.488037f), Constants.getNewY(37.5f), Constants.getNewWidth(1145.0f), Constants.getNewHeight(1093.0f), 0.0f, 0.0f, "sh14", "STICKER", 8, 0, 91, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "SUM MER", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 2, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(277.0f), Constants.getNewY(182.5f), Constants.getNewWidth(525.0f), Constants.getNewHeight(525.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BEACH PARTY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(319.0f), Constants.getNewY(608.0f), Constants.getNewWidth(437.0f), Constants.getNewHeight(409.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "D J   V I C E  |  D J   M A X  |  D I Z Z Y", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(91.0f), Constants.getNewY(1213.0f), Constants.getNewWidth(895.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "02 | 02 | 2020", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(70.82153f), Constants.getNewY(965.1543f), Constants.getNewWidth(925.0f), Constants.getNewHeight(105.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CLUB HOUSE PRESENTS", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(268.13586f), Constants.getNewY(98.864136f), Constants.getNewWidth(543.0f), Constants.getNewHeight(91.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FREE ENTRY AND PARKING  |  PARTY STARTS AT 7 PM SILOCO BEACH PARTY, SINGAPORE", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(68.5f), Constants.getNewY(1289.4852f), Constants.getNewWidth(935.0f), Constants.getNewHeight(93.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "F E A T U R I N G", "ffont48.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(379.5f), Constants.getNewY(1135.0f), Constants.getNewWidth(317.0f), Constants.getNewHeight(101.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.SILOEVENTS.COM", "ffont14.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(339.75427f), Constants.getNewY(1367.0f), Constants.getNewWidth(403.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_2", "b9", "3:4", "Temp_Path", "90", "FREESTYLE", "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh3.png", "", "o1", 118, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(51.49997f), Constants.getNewY(-185.0f), Constants.getNewWidth(977.0f), Constants.getNewHeight(1079.0f), -90.0f, 0.0f, "sh14", "STICKER", 4, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(244.0f), Constants.getNewY(3.0f), Constants.getNewWidth(297.0f), Constants.getNewHeight(245.0f), 0.0f, 0.0f, "g_15", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(537.0f), Constants.getNewY(6.0f), Constants.getNewWidth(297.0f), Constants.getNewHeight(245.0f), 0.0f, -180.0f, "g_15", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "HIGH QUALITY TATTOO", "ffont3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 14, "0", ViewCompat.MEASURED_STATE_MASK, 115, Constants.getNewX(97.0f), Constants.getNewY(451.0f), Constants.getNewWidth(887.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SINCE 1991", "ffont16.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 15, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(236.62128f), Constants.getNewY(530.44147f), Constants.getNewWidth(609.0f), Constants.getNewHeight(99.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Thunder House Tattoo Memory Lane, Switzerland (123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 12, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(251.0f), Constants.getNewY(1201.0f), Constants.getNewWidth(571.0f), Constants.getNewHeight(167.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.THUNDERTATTOOS.COM", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 9, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(332.0f), Constants.getNewY(1354.0f), Constants.getNewWidth(413.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "THUNDER HOUSE TATTOO", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 20, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(98.01251f), Constants.getNewY(74.5f), Constants.getNewWidth(885.0f), Constants.getNewHeight(457.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "VISIT US", "ffont6.ttf", -2379684, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(453.54364f), Constants.getNewY(1130.0f), Constants.getNewWidth(165.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "High Quality Tattooing & Body Modification", "ffont32.ttf", -1, 100, -5741056, 18, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(129.01416f), Constants.getNewY(704.2322f), Constants.getNewWidth(813.0f), Constants.getNewHeight(357.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_3", "b37", "3:4", "Background", "90", "FREESTYLE", "", "", "o24", 144, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(526.50006f), Constants.getNewY(-25.00005f), Constants.getNewWidth(609.0f), Constants.getNewHeight(537.0f), 13.373082f, -180.0f, "p_11", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-110.50015f), Constants.getNewY(-106.26331f), Constants.getNewWidth(889.0f), Constants.getNewHeight(723.0f), -17.406427f, 0.0f, "p_11", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 4, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "PLEASE JOIN US FOR AN", "ffont38.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(274.0f), Constants.getNewY(531.0f), Constants.getNewWidth(529.0f), Constants.getNewHeight(83.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ENGAGEMENT PARTY", "ffont46.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(82.0f), Constants.getNewY(590.0f), Constants.getNewWidth(915.0f), Constants.getNewHeight(177.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "HONORING", "ffont38.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(393.0f), Constants.getNewY(659.0f), Constants.getNewWidth(299.0f), Constants.getNewHeight(249.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Megan Farrell & Stephan Athinson", "font3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 3, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(30.0f), Constants.getNewY(766.5f), Constants.getNewWidth(1019.0f), Constants.getNewHeight(271.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NOVEMBER 20TH, 2020", "ffont38.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(177.0f), Constants.getNewY(978.0f), Constants.getNewWidth(717.0f), Constants.getNewHeight(111.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "REGRETS ONLY : CALL AT (123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(241.06079f), Constants.getNewY(1323.0f), Constants.getNewWidth(587.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "AT 4 PM IN SPIRITUAL LOUNGE 002 DIRECT STREET, CITY", "ffont38.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(245.0f), Constants.getNewY(1067.0f), Constants.getNewWidth(595.0f), Constants.getNewHeight(153.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "HOSTED BY CARLA AND MEGAN", "ffont38.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 1, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(188.0f), Constants.getNewY(1220.5425f), Constants.getNewWidth(695.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_6", "b52", "3:4", "Background", "90", "FREESTYLE", "", "", "", 80, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(295.7704f), Constants.getNewY(262.49988f), Constants.getNewWidth(485.0f), Constants.getNewHeight(585.0f), -44.664978f, 0.0f, "sh1", "STICKER", 0, -1, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(291.7704f), Constants.getNewY(424.49988f), Constants.getNewWidth(485.0f), Constants.getNewHeight(585.0f), -44.664978f, 0.0f, "sh1", "STICKER", 1, -1, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(256.21927f), Constants.getNewY(365.70004f), Constants.getNewWidth(563.0f), Constants.getNewHeight(541.0f), -44.382946f, 0.0f, "sh17", "STICKER", 2, -12024378, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(278.25528f), Constants.getNewY(320.49994f), Constants.getNewWidth(517.0f), Constants.getNewHeight(625.0f), -44.664978f, 0.0f, "sh1", "STICKER", 3, -1, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "PARTY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(421.0f), Constants.getNewY(786.0f), Constants.getNewWidth(235.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "RETRO CLUB", "ffont10.ttf", -5972999, 100, ViewCompat.MEASURED_STATE_MASK, 3, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(753.0f), Constants.getNewY(26.169983f), Constants.getNewWidth(305.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CITY", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 3, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(877.9247f), Constants.getNewY(89.30823f), Constants.getNewWidth(231.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ELECTRO", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(434.0f), Constants.getNewY(403.72498f), Constants.getNewWidth(207.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "POP", "ffont1.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(318.0f), Constants.getNewY(464.0f), Constants.getNewWidth(443.0f), Constants.getNewHeight(367.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "7:00 PM", "ffont10.ttf", -6825738, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(878.0f), Constants.getNewY(1093.0f), Constants.getNewWidth(211.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DOORS OPEN", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(881.5f), Constants.getNewY(1163.4215f), Constants.getNewWidth(207.0f), Constants.getNewHeight(105.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "(123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(750.0f), Constants.getNewY(1282.0f), Constants.getNewWidth(307.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DIZZY | ROSES", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(25.44983f), Constants.getNewY(1315.6907f), Constants.getNewWidth(403.0f), Constants.getNewHeight(131.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DJ VICE | DJ MAX", "", -6104072, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(26.0f), Constants.getNewY(1262.5f), Constants.getNewWidth(385.0f), Constants.getNewHeight(83.0f), 0.0f, "TEXT", 13, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "02 | 02 | 2020", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 4, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(24.471893f), Constants.getNewY(103.0f), Constants.getNewWidth(353.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 14, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.RETROCLUB.COM", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(582.0f), Constants.getNewY(1168.0f), Constants.getNewWidth(473.0f), Constants.getNewHeight(437.0f), 0.0f, "TEXT", 15, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DATE", "ffont10.ttf", -6759945, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-64.0f), Constants.getNewY(34.0f), Constants.getNewWidth(323.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 16, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_8", "b0", "3:4", "Color", "90", "FREESTYLE", "", "ff000000", "", 80, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(497.5f), Constants.getNewY(278.5f), Constants.getNewWidth(449.0f), Constants.getNewHeight(451.0f), 0.0f, -180.0f, "h_1", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(133.5f), Constants.getNewY(276.5f), Constants.getNewWidth(449.0f), Constants.getNewHeight(451.0f), 0.0f, 0.0f, "h_1", "STICKER", 2, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BEERFEST 2020", "ffont5.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(78.65546f), Constants.getNewY(33.890747f), Constants.getNewWidth(919.0f), Constants.getNewHeight(175.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Saturday October 21st 7:00 pm", "ffont18.otf", -2522365, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(350.0f), Constants.getNewY(739.46533f), Constants.getNewWidth(381.0f), Constants.getNewHeight(223.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BEER | FOOD | MUSIC | DRINK", "ffont2.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(60.295288f), Constants.getNewY(985.0f), Constants.getNewWidth(961.0f), Constants.getNewHeight(163.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "0022 New Street Town, Country", "ffont18.otf", -1586, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(332.0f), Constants.getNewY(1285.5979f), Constants.getNewWidth(413.0f), Constants.getNewHeight(129.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Beer Pub", "ffont18.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(320.4536f), Constants.getNewY(1126.1456f), Constants.getNewWidth(445.0f), Constants.getNewHeight(157.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_12", "b45", "3:4", "Background", "90", "FREESTYLE", "", "", "", 80, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(127.0f), Constants.getNewY(88.0f), Constants.getNewWidth(825.0f), Constants.getNewHeight(607.0f), 0.0f, 0.0f, "e_3", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(138.0f), Constants.getNewY(92.0f), Constants.getNewWidth(801.0f), Constants.getNewHeight(763.0f), 0.0f, 0.0f, "sh1", "STICKER", 2, -1, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "FASHION", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(337.0f), Constants.getNewY(361.0f), Constants.getNewWidth(407.0f), Constants.getNewHeight(91.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EXCLUSIVE", "", -2339893, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(131.38965f), Constants.getNewY(109.30502f), Constants.getNewWidth(811.0f), Constants.getNewHeight(243.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "60%", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(508.5f), Constants.getNewY(834.0f), Constants.getNewWidth(375.0f), Constants.getNewHeight(317.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UPTO", "ffont10.ttf", -1612844, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(198.0f), Constants.getNewY(950.0f), Constants.getNewWidth(287.0f), Constants.getNewHeight(167.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont10.ttf", -2139968, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(662.0f), Constants.getNewY(1085.0f), Constants.getNewWidth(249.0f), Constants.getNewHeight(103.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFFER", "", -2140735, 100, -2005047, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(201.0f), Constants.getNewY(855.14136f), Constants.getNewWidth(267.0f), Constants.getNewHeight(173.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "2020 ANY STREET, TOWN, CITY", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(243.0f), Constants.getNewY(1233.0f), Constants.getNewWidth(595.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SALE", "ffont10.ttf", -2272059, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(128.90735f), Constants.getNewY(394.97943f), Constants.getNewWidth(821.0f), Constants.getNewHeight(467.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "For More Info Call Us At :", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(299.0f), Constants.getNewY(1299.7472f), Constants.getNewWidth(481.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "(123) 456 789", "", -2009409, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(394.0f), Constants.getNewY(1358.5f), Constants.getNewWidth(297.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "* Condition Apply", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(11.0f), Constants.getNewY(-10.0f), Constants.getNewWidth(281.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "==========================", "ffont1.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(110.4288f), Constants.getNewY(1163.5712f), Constants.getNewWidth(871.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 13, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_13", "b42", "3:4", "Background", "90", "FREESTYLE", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-44.0f), Constants.getNewY(1034.0001f), Constants.getNewWidth(679.0f), Constants.getNewHeight(765.0f), -90.0f, 0.0f, "sh33", "STICKER", 3, ViewCompat.MEASURED_STATE_MASK, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(526.0f), Constants.getNewY(1033.0001f), Constants.getNewWidth(679.0f), Constants.getNewHeight(765.0f), -90.0f, 0.0f, "sh33", "STICKER", 4, ViewCompat.MEASURED_STATE_MASK, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(558.0f), Constants.getNewY(990.42456f), Constants.getNewWidth(517.0f), Constants.getNewHeight(443.0f), 0.0f, -180.0f, "p_3", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-96.5f), Constants.getNewY(945.42456f), Constants.getNewWidth(607.0f), Constants.getNewHeight(525.0f), 0.0f, 0.0f, "p_3", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(827.0812f), Constants.getNewY(981.00555f), Constants.getNewWidth(517.0f), Constants.getNewHeight(443.0f), -1.89556f, -180.0f, "p_3", "STICKER", 8, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(206.44415f), Constants.getNewY(557.0f), Constants.getNewWidth(645.0f), Constants.getNewHeight(819.0f), 0.0f, 0.0f, "d_13", "STICKER", 9, ViewCompat.MEASURED_STATE_MASK, 100, 0, 0, 0, 0, "", "white", 100, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "MOUNTAIN BIKE", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(163.50989f), Constants.getNewY(43.0f), Constants.getNewWidth(755.0f), Constants.getNewHeight(115.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "MARATHON", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(214.52026f), Constants.getNewY(71.69708f), Constants.getNewWidth(651.0f), Constants.getNewHeight(245.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CHAMPIONSHIPS", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(220.0f), Constants.getNewY(188.34546f), Constants.getNewWidth(637.0f), Constants.getNewHeight(195.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FULL DETAILS : WWW.YOURWEBSITE.COM", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(172.0f), Constants.getNewY(1354.0f), Constants.getNewWidth(733.0f), Constants.getNewHeight(91.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "LOCATION : XYZ TRAIL CENTRE DATE : SUNDAY 26TH SEPTEMBER", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(210.5f), Constants.getNewY(1278.0f), Constants.getNewWidth(647.0f), Constants.getNewHeight(101.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "A GOOD BIKE", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(372.0f), Constants.getNewY(340.0f), Constants.getNewWidth(327.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "RIDE", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(367.44812f), Constants.getNewY(365.0f), Constants.getNewWidth(335.0f), Constants.getNewHeight(193.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_14", "b20", "3:4", "Background", "90", "FREESTYLE", "", "", "o30", 0, 81));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(138.1344f), Constants.getNewY(348.0f), Constants.getNewWidth(885.0f), Constants.getNewHeight(859.0f), 0.0f, 0.0f, "h_12", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-36.0f), Constants.getNewY(797.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, 0.0f, "f_23", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "colored", 4, 0, "", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(748.5172f), Constants.getNewY(797.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, -180.0f, "f_23", "STICKER", 8, 0, 100, 0, 0, 0, 0, "", "colored", 5, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "9 PM ONWARDS", "ffont10.ttf", SupportMenu.CATEGORY_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(341.0f), Constants.getNewY(267.70892f), Constants.getNewWidth(385.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "===========================", "", SupportMenu.CATEGORY_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(109.0f), Constants.getNewY(-20.0f), Constants.getNewWidth(873.0f), Constants.getNewHeight(95.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Live Music", "ffont9.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(298.0f), Constants.getNewY(178.0f), Constants.getNewWidth(471.0f), Constants.getNewHeight(101.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "===========================", "", SupportMenu.CATEGORY_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(108.0f), Constants.getNewY(107.0f), Constants.getNewWidth(873.0f), Constants.getNewHeight(95.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CARNIVAL FESTIVAL", "ffont2.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(128.0f), Constants.getNewY(11.0f), Constants.getNewWidth(835.0f), Constants.getNewHeight(179.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Saturday, 02, 2020", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(346.5f), Constants.getNewY(1192.0f), Constants.getNewWidth(401.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "(123) 456 789", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 127, Constants.getNewX(0.30566406f), Constants.getNewY(1356.0f), Constants.getNewWidth(1087.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1002, YOUR ADDRESS, TOWN, CITY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 127, Constants.getNewX(0.0f), Constants.getNewY(1277.0f), Constants.getNewWidth(1097.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("raw_18", "b59", "9:16", "Background", "90", "FREESTYLE", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-99.0f), Constants.getNewY(149.0f), Constants.getNewWidth(1234.0f), Constants.getNewHeight(1155.0f), 0.0f, 0.0f, "i_11", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-190,145", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "WORLD'S NO.1 DJ AT HANGOUT STATION", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 98, Constants.getNewX(-2.0f), Constants.getNewY(1188.0f), Constants.getNewWidth(857.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NEW STREET, TOWN, COUNTRY", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(181.0f), Constants.getNewY(1402.0f), Constants.getNewWidth(485.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "www.yourwebsite.com", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(215.0f), Constants.getNewY(1446.0f), Constants.getNewWidth(419.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "20-Sep-20", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(241.0f), Constants.getNewY(1267.0f), Constants.getNewWidth(371.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "HANGOUT STATION", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(179.0f), Constants.getNewY(1315.0f), Constants.getNewWidth(491.0f), Constants.getNewHeight(131.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "_________________________", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-47.0f), Constants.getNewY(130.0f), Constants.getNewWidth(1018.0f), Constants.getNewHeight(147.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-86,73", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "MUSIC EVOLUTION", "ffont1.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 7, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(25.451538f), Constants.getNewY(51.0f), Constants.getNewWidth(803.0f), Constants.getNewHeight(141.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "MUSIC", "ffont10.ttf", -295845, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(218.52399f), Constants.getNewY(522.0f), Constants.getNewWidth(421.0f), Constants.getNewHeight(263.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FESTIVAL", "ffont10.ttf", -832942, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(216.5f), Constants.getNewY(679.7322f), Constants.getNewWidth(419.0f), Constants.getNewHeight(197.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "", "", ""));

        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_1", "", "1:1", "Color", "90", "FRIDAY", "", "ffffffff", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(174.0f), Constants.getNewY(53.0f), Constants.getNewWidth(761.0f), Constants.getNewHeight(701.0f), 0.0f, 0.0f, "c_7", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "-197,-167", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont5.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(185.0f), Constants.getNewY(20.0f), Constants.getNewWidth(691.0f), Constants.getNewHeight(163.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-83,181", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Your Shop Name", "ffont1.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(230.34314f), Constants.getNewY(272.07434f), Constants.getNewWidth(599.0f), Constants.getNewHeight(223.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-37,151", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Friday", "ffont5.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(105.0365f), Constants.getNewY(604.927f), Constants.getNewWidth(861.0f), Constants.getNewHeight(197.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-168,164", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Sale", "ffont5.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(275.0f), Constants.getNewY(670.0f), Constants.getNewWidth(525.0f), Constants.getNewHeight(525.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_3", "b0", "1:1", "Color", "90", "FRIDAY", "", "ff000000", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(328.40906f), Constants.getNewY(503.18628f), Constants.getNewWidth(201.0f), Constants.getNewHeight(69.0f), 45.8396f, 0.0f, "sh17", "STICKER", 7, -3407821, 100, 0, 0, 0, 0, "", "white", 1, 0, "83,149", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(606.409f), Constants.getNewY(512.6863f), Constants.getNewWidth(201.0f), Constants.getNewHeight(69.0f), 45.8396f, 0.0f, "sh17", "STICKER", 9, -3407821, 100, 0, 0, 0, 0, "", "white", 1, 0, "83,149", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(473.40897f), Constants.getNewY(507.6861f), Constants.getNewWidth(201.0f), Constants.getNewHeight(69.0f), 45.8396f, 0.0f, "sh17", "STICKER", 10, -3407821, 100, 0, 0, 0, 0, "", "white", 1, 0, "83,149", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "Black Friday", "font4.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(90.992676f), Constants.getNewY(138.44035f), Constants.getNewWidth(959.0f), Constants.getNewHeight(293.0f), -12.24211f, "TEXT", 0, 0, 0, 0, 0, 0, "-217,116", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "IS ON!", "ffont51.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(576.0f), Constants.getNewY(358.0f), Constants.getNewWidth(449.0f), Constants.getNewHeight(111.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "38,207", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EXTRA", "ffont3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(133.57014f), Constants.getNewY(533.86414f), Constants.getNewWidth(203.0f), Constants.getNewHeight(349.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "161,88", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(781.4624f), Constants.getNewY(540.5376f), Constants.getNewWidth(205.0f), Constants.getNewHeight(301.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "160,112", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ALL REDUCED FOOTWEAR", "ffont3.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(225.0f), Constants.getNewY(798.0f), Constants.getNewWidth(693.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-84,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "START SHOPPING", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(32.5f), Constants.getNewY(904.0f), Constants.getNewWidth(1021.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-248,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "COMPANY NAME", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(177.0f), Constants.getNewY(-37.0f), Constants.getNewWidth(741.0f), Constants.getNewHeight(199.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-108,163", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "30%", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(364.0642f), Constants.getNewY(545.5f), Constants.getNewWidth(415.0f), Constants.getNewHeight(263.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "55,131", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_8", "b53", "1:1", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(174.0f), Constants.getNewY(189.0f), Constants.getNewWidth(749.0f), Constants.getNewHeight(179.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-112,173", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Address lines goes here, for more info please call +00 000 000 0000", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 137, Constants.getNewX(-22.463501f), Constants.getNewY(894.5f), Constants.getNewWidth(1129.0f), Constants.getNewHeight(163.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "-302,181", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "YOUR COMPANY NAME", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 20, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(86.0f), Constants.getNewY(59.0f), Constants.getNewWidth(915.0f), Constants.getNewHeight(161.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-195,182", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Discount upto 50%", "font6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(42.0f), Constants.getNewY(688.25f), Constants.getNewWidth(1007.0f), Constants.getNewHeight(131.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-241,197", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Sale", "ffont33.ttf", SupportMenu.CATEGORY_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(257.43695f), Constants.getNewY(273.95966f), Constants.getNewWidth(589.0f), Constants.getNewHeight(497.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-32,14", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_14", "b33", "1:1", "Background", "90", "FRIDAY", "", "", "", 80, 223));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(722.0f), Constants.getNewY(-12.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, 0.0f, "a_2", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "0,0", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(329.0f), Constants.getNewY(15.5f), Constants.getNewWidth(419.0f), Constants.getNewHeight(415.0f), 0.0f, 0.0f, "a_5", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "-26,-24", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "DON'T MISS OUT!", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(203.5f), Constants.getNewY(439.0f), Constants.getNewWidth(679.0f), Constants.getNewHeight(109.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-77,208", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY SALE", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(205.98541f), Constants.getNewY(531.765f), Constants.getNewWidth(669.0f), Constants.getNewHeight(105.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-72,210", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SAVE UP TO", "ffont45.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(415.0f), Constants.getNewY(620.0f), Constants.getNewWidth(251.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "137,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "75%OFF", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(204.5f), Constants.getNewY(632.5f), Constants.getNewWidth(663.0f), Constants.getNewHeight(275.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-69,125", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(381.44922f), Constants.getNewY(891.0f), Constants.getNewWidth(335.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "95,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1100 STREET, TOWN (123) 456 789", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(211.0f), Constants.getNewY(957.0f), Constants.getNewWidth(665.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-70,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(340.0f), Constants.getNewY(1012.0f), Constants.getNewWidth(401.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "62,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_17", "b20", "1:1", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(815.0308f), Constants.getNewY(133.99988f), Constants.getNewWidth(237.0f), Constants.getNewHeight(241.0f), 19.516615f, 0.0f, "b_24", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "65,63", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-29.5f), Constants.getNewY(678.5f), Constants.getNewWidth(403.0f), Constants.getNewHeight(455.0f), 0.0f, 0.0f, "f_23", "STICKER", 2, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "-18,-44", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(177.0f), Constants.getNewY(93.66443f), Constants.getNewWidth(729.0f), Constants.getNewHeight(843.0f), 0.0f, 0.0f, "b_10", "STICKER", 3, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "-181,-238", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont10.ttf", -1, 100, -10066330, 8, "0", -7262832, 0, Constants.getNewX(333.0f), Constants.getNewY(1008.0f), Constants.getNewWidth(423.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "51,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY WEEKEND", "ffont6.ttf", -1, 100, -10066330, 20, "0", -7262832, 255, Constants.getNewX(284.5f), Constants.getNewY(0.0f), Constants.getNewWidth(517.0f), Constants.getNewHeight(165.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "48,-20", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont10.ttf", -1, 100, -4038736, 0, "0", -7262832, 255, Constants.getNewX(387.0f), Constants.getNewY(864.0f), Constants.getNewWidth(313.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "106,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ANY STREET, TOWN, COUNTRY", "ffont10.ttf", -1, 100, -10066330, 8, "0", -7262832, 0, Constants.getNewX(196.0f), Constants.getNewY(952.0f), Constants.getNewWidth(689.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-82,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_20", "b48", "1:1", "Background", "290", "FRIDAY", "", "", "o6", 129, 11));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(33.0f), Constants.getNewY(-134.0f), Constants.getNewWidth(1015.0f), Constants.getNewHeight(1215.0f), 0.0f, 0.0f, "sh14", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-324,-424", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(711.00006f), Constants.getNewY(94.00007f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), -17.257963f, 0.0f, "a_3", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 32, 0, "0,0", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(257.0f), Constants.getNewY(213.0f), Constants.getNewWidth(567.0f), Constants.getNewHeight(617.0f), 0.0f, 0.0f, "a_7", "STICKER", 2, 0, 100, 0, 0, 0, 0, "", "colored", 5, 0, "-100,-125", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont10.ttf", -26880, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(260.5f), Constants.getNewY(-36.0f), Constants.getNewWidth(561.0f), Constants.getNewHeight(219.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-18,153", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "ffont10.ttf", -26880, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(302.0f), Constants.getNewY(782.0f), Constants.getNewWidth(477.0f), Constants.getNewHeight(201.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "24,162", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NOVEMBER 20TH", "ffont10.ttf", -14633479, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(311.0f), Constants.getNewY(142.52332f), Constants.getNewWidth(455.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "35,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME, NEW STREET, TOWN", "ffont10.ttf", -14633479, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(147.0f), Constants.getNewY(986.0f), Constants.getNewWidth(787.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-131,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_21", "", "1:1", "Color", "90", "FRIDAY", "", "ff000000", "o24", 255, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(3.498413f), Constants.getNewY(-53.0f), Constants.getNewWidth(1073.0f), Constants.getNewHeight(1099.0f), 0.0f, 0.0f, "m_16", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-353,-366", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "ALL SALE ITEMS", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(315.0f), Constants.getNewY(574.5f), Constants.getNewWidth(471.0f), Constants.getNewHeight(95.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "27,215", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(435.0f), Constants.getNewY(671.0f), Constants.getNewWidth(223.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "151,220", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -1, 255, Constants.getNewX(436.0f), Constants.getNewY(756.0f), Constants.getNewWidth(221.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "152,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "0002 New Street, Town", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -1, 0, Constants.getNewX(296.72266f), Constants.getNewY(989.71643f), Constants.getNewWidth(493.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "16,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "TAKE AN EXTRA", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(239.0f), Constants.getNewY(190.5f), Constants.getNewWidth(611.0f), Constants.getNewHeight(127.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-43,199", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "50", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(230.14264f), Constants.getNewY(173.85736f), Constants.getNewWidth(525.0f), Constants.getNewHeight(525.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(667.0f), Constants.getNewY(455.0f), Constants.getNewWidth(123.0f), Constants.getNewHeight(127.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "201,199", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "%", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(651.0f), Constants.getNewY(280.0f), Constants.getNewWidth(153.0f), Constants.getNewHeight(249.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "186,138", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -1, 0, Constants.getNewX(397.0f), Constants.getNewY(129.0f), Constants.getNewWidth(293.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "116,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_22", "b6", "1:1", "Temp_Path", "90", "FRIDAY", "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh7.png", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(33.812683f), Constants.getNewY(375.5f), Constants.getNewWidth(417.0f), Constants.getNewHeight(247.0f), 0.0f, 0.0f, "sh1", "STICKER", 0, -4464, 100, 0, 0, 0, 0, "", "white", 1, 0, "-25,60", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "Get up to 50% off", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(147.0f), Constants.getNewY(380.5f), Constants.getNewWidth(191.0f), Constants.getNewHeight(237.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "167,144", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "My Beauty Salon", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(66.0f), Constants.getNewY(670.0f), Constants.getNewWidth(351.0f), Constants.getNewHeight(183.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "87,171", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EARLY BLACK FRIDAY DEALS", "ffont7.otf", -4464, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(38.25982f), Constants.getNewY(68.4816f), Constants.getNewWidth(411.0f), Constants.getNewHeight(251.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "57,137", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "YOUR PLACE, NEW STREET, COUNTRY", "ffont7.otf", -4464, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(31.5f), Constants.getNewY(894.50073f), Constants.getNewWidth(413.0f), Constants.getNewHeight(109.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "56,208", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_23", "", "1:1", "Color", "90", "FRIDAY", "", "ff000000", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(385.32532f), Constants.getNewY(378.67468f), Constants.getNewWidth(641.0f), Constants.getNewHeight(569.0f), 0.0f, 0.0f, "g_29", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-137,-101", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(383.32532f), Constants.getNewY(705.6747f), Constants.getNewWidth(641.0f), Constants.getNewHeight(569.0f), 0.0f, 0.0f, "g_29", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-137,-101", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(18.0f), Constants.getNewY(789.0f), Constants.getNewWidth(289.0f), Constants.getNewHeight(289.0f), 0.0f, 0.0f, "n_4", "STICKER", 8, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "39,39", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(0.0f), Constants.getNewY(-10.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, 0.0f, "g_11", "STICKER", 9, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "0,0", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "GET $25 OFF", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(27.85669f), Constants.getNewY(103.14331f), Constants.getNewWidth(1115.0f), Constants.getNewHeight(179.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-295,173", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ORDERS OVER $ 60", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(443.7658f), Constants.getNewY(302.2342f), Constants.getNewWidth(589.0f), Constants.getNewHeight(183.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "-32,171", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Plus free GiFT", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(588.0f), Constants.getNewY(490.80115f), Constants.getNewWidth(439.0f), Constants.getNewHeight(131.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "43,197", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "THE COFFEE SHOP", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(376.0f), Constants.getNewY(677.49f), Constants.getNewWidth(663.0f), Constants.getNewHeight(175.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-69,175", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY WEEKEND DEAL", "", -2448096, 100, -1, 5, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(32.0f), Constants.getNewY(345.0f), Constants.getNewWidth(323.0f), Constants.getNewHeight(343.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "123 Main Street , City", "ffont8.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(578.0f), Constants.getNewY(846.0f), Constants.getNewWidth(455.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "35,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_24", "b29", "1:1", "Background", "90", "FRIDAY", "", "", "o4", 46, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(218.0f), Constants.getNewY(212.5f), Constants.getNewWidth(647.0f), Constants.getNewHeight(611.0f), 0.0f, 0.0f, "b_23", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-140,-122", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(33.0f), Constants.getNewY(-134.0f), Constants.getNewWidth(1015.0f), Constants.getNewHeight(1215.0f), 0.0f, 0.0f, "sh14", "STICKER", 3, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-324,-424", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(258.0f), Constants.getNewY(-34.0f), Constants.getNewWidth(561.0f), Constants.getNewHeight(219.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-18,153", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(235.5f), Constants.getNewY(792.5f), Constants.getNewWidth(601.0f), Constants.getNewHeight(181.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-38,172", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME, NEW STREET, TOWN", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(157.0f), Constants.getNewY(966.0f), Constants.getNewWidth(773.0f), Constants.getNewHeight(121.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-124,202", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "MARCH 20TH", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(311.0f), Constants.getNewY(146.0f), Constants.getNewWidth(455.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_7", "", "4:3", "Color", "90", "FRIDAY", "", "ff000000", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-82.2475f), Constants.getNewY(-91.927864f), Constants.getNewWidth(555.0f), Constants.getNewHeight(713.0f), 15.919445f, 0.0f, "a_4", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-94,-173", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(90.47528f), Constants.getNewY(143.52478f), Constants.getNewWidth(905.0f), Constants.getNewHeight(923.0f), 0.0f, 0.0f, "g_27", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-269,-278", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "ffont11.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(91.66834f), Constants.getNewY(32.33139f), Constants.getNewWidth(231.0f), Constants.getNewHeight(447.0f), 15.742978f, "TEXT", 1, 0, 0, 0, 0, 0, "147,39", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DEALS", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(39.889145f), Constants.getNewY(202.41737f), Constants.getNewWidth(241.0f), Constants.getNewHeight(411.0f), 15.914184f, "TEXT", 2, 0, 0, 0, 0, 0, "142,57", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SAVE", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(539.9586f), Constants.getNewY(3.5551758f), Constants.getNewWidth(407.0f), Constants.getNewHeight(225.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "59,150", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(613.4417f), Constants.getNewY(217.20258f), Constants.getNewWidth(297.0f), Constants.getNewHeight(107.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "114,209", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "£250", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(476.0f), Constants.getNewY(287.0f), Constants.getNewWidth(555.0f), Constants.getNewHeight(241.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-15,142", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "My shop.com", "ffont7.otf", -4684277, 100, -1, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(227.0f), Constants.getNewY(622.0f), Constants.getNewWidth(653.0f), Constants.getNewHeight(87.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-64,219", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Best Brands Local Shop", "ffont7.otf", -4684277, 100, -1, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(228.0f), Constants.getNewY(695.0f), Constants.getNewWidth(653.0f), Constants.getNewHeight(87.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_12", "b9", "4:3", "Background", "290", "FRIDAY", "", "", "o6", 0, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(802.18146f), Constants.getNewY(514.4536f), Constants.getNewWidth(295.0f), Constants.getNewHeight(299.0f), 0.0f, -180.0f, "t_23", "STICKER", 15, 0, 100, 0, 0, 0, 0, "", "colored", 7, 0, "36,34", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(10.460033f), Constants.getNewY(-13.9999695f), Constants.getNewWidth(271.0f), Constants.getNewHeight(251.0f), -24.112087f, 0.0f, "b_20", "STICKER", 16, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "48,58", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "ffont12.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(353.59857f), Constants.getNewY(-9.401428f), Constants.getNewWidth(377.0f), Constants.getNewHeight(231.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "74,147", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "A", "ffont12.ttf", -2096897, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(165.0f), Constants.getNewY(182.0f), Constants.getNewWidth(173.0f), Constants.getNewHeight(177.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "176,174", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "W", "ffont12.ttf", -16739842, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(270.0f), Constants.getNewY(193.0f), Constants.getNewWidth(151.0f), Constants.getNewHeight(177.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "187,174", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "W", "ffont12.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(341.0f), Constants.getNewY(182.0f), Constants.getNewWidth(177.0f), Constants.getNewHeight(189.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "174,168", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "W", "ffont12.ttf", -10496, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(418.0f), Constants.getNewY(200.0f), Constants.getNewWidth(167.0f), Constants.getNewHeight(161.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "179,182", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "E", "ffont12.ttf", -26880, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(486.54156f), Constants.getNewY(188.49991f), Constants.getNewWidth(167.0f), Constants.getNewHeight(181.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "179,172", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "S", "ffont12.ttf", -65281, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(547.0f), Constants.getNewY(182.00005f), Constants.getNewWidth(175.0f), Constants.getNewHeight(187.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "175,169", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "O", "ffont12.ttf", -2044672, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(631.7735f), Constants.getNewY(185.7735f), Constants.getNewWidth(161.0f), Constants.getNewHeight(173.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "182,176", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "M", "ffont12.ttf", -52429, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(685.0f), Constants.getNewY(200.0f), Constants.getNewWidth(139.0f), Constants.getNewHeight(135.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "193,195", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "E", "ffont12.ttf", -16732932, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(726.0f), Constants.getNewY(165.5f), Constants.getNewWidth(193.0f), Constants.getNewHeight(191.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "166,167", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Get down to your TOYS STORE for the CRAZIEST DEALS", "ffont18.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(252.39087f), Constants.getNewY(648.5094f), Constants.getNewWidth(577.0f), Constants.getNewHeight(153.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "-26,186", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "|", "ffont10.ttf", -11229473, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(483.0f), Constants.getNewY(416.0f), Constants.getNewWidth(81.0f), Constants.getNewHeight(231.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "222,147", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "70% OFF", "ffont10.ttf", -11229473, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(555.5f), Constants.getNewY(422.0f), Constants.getNewWidth(219.0f), Constants.getNewHeight(237.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "153,144", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -11229473, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(351.98047f), Constants.getNewY(404.5f), Constants.getNewWidth(129.0f), Constants.getNewHeight(265.0f), 0.0f, "TEXT", 13, 0, 0, 0, 0, 0, "198,130", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DEALS VALID ON NOVEMBER 02", "ffont10.ttf", -11229473, 100, -10066330, 0, "0", 0, 0, Constants.getNewX(235.0f), Constants.getNewY(353.22937f), Constants.getNewWidth(613.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 14, 0, 0, 0, 0, 0, "-44,223", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_15", "b16", "4:3", "Background", "90", "FRIDAY", "", "", "o6", 128, 223));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(13.5f), Constants.getNewY(-23.0f), Constants.getNewWidth(249.0f), Constants.getNewHeight(283.0f), 0.0f, 0.0f, "c_11", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "59,42", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(822.3184f), Constants.getNewY(-23.0f), Constants.getNewWidth(249.0f), Constants.getNewHeight(283.0f), 0.0f, 0.0f, "c_11", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "59,42", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(267.0f), Constants.getNewY(132.0f), Constants.getNewWidth(547.0f), Constants.getNewHeight(573.0f), 0.0f, 0.0f, "sh14", "STICKER", 3, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-90,-103", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(437.5841f), Constants.getNewY(281.0f), Constants.getNewWidth(203.0f), Constants.getNewHeight(151.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "161,187", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SAVE", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(369.24695f), Constants.getNewY(124.0f), Constants.getNewWidth(345.0f), Constants.getNewHeight(225.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "90,150", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(508.5f), Constants.getNewY(515.0f), Constants.getNewWidth(173.0f), Constants.getNewHeight(181.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "176,172", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "80%", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(389.0f), Constants.getNewY(331.0f), Constants.getNewWidth(297.0f), Constants.getNewHeight(255.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "114,135", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "ffont44.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(248.5f), Constants.getNewY(9.0f), Constants.getNewWidth(585.0f), Constants.getNewHeight(121.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-30,202", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1110 ANY STREET, TOWN (123) 456 789", "ffont14.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(154.0f), Constants.getNewY(712.0f), Constants.getNewWidth(763.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_16", "b9", "4:3", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-116.03375f), Constants.getNewY(-130.0f), Constants.getNewWidth(1312.0f), Constants.getNewHeight(1036.0f), 90.0f, 0.0f, "sh14", "STICKER", 5, -3407821, 100, 0, 0, 0, 0, "", "white", 1, 0, "-293,-290", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(658.5f), Constants.getNewY(73.0f), Constants.getNewWidth(363.0f), Constants.getNewHeight(363.0f), 0.0f, 0.0f, "c_11", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 1, 0, "", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "SALE", "ffont10.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(74.5f), Constants.getNewY(92.5f), Constants.getNewWidth(427.0f), Constants.getNewHeight(257.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "-------- UP TO --------", "", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(69.0f), Constants.getNewY(338.0f), Constants.getNewWidth(433.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "50%", "ffont10.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(66.0f), Constants.getNewY(288.0f), Constants.getNewWidth(441.0f), Constants.getNewHeight(509.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "O F F", "ffont14.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(517.0f), Constants.getNewY(416.0f), Constants.getNewWidth(79.0f), Constants.getNewHeight(249.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont10.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(325.0f), Constants.getNewY(719.0f), Constants.getNewWidth(439.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NO., ANY STREET, TOWN, CITY", "ffont10.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(644.0f), Constants.getNewY(551.0f), Constants.getNewWidth(387.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "(123) 456 789", "ffont10.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(733.0f), Constants.getNewY(611.0f), Constants.getNewWidth(207.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_19", "b13", "4:3", "Color", "90", "FRIDAY", "", "ff000000", "o28", 183, 255));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(61.0f), Constants.getNewY(304.0f), Constants.getNewWidth(943.0f), Constants.getNewHeight(735.0f), 0.0f, 0.0f, "g_27", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-288,-184", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(65.0f), Constants.getNewY(177.0f), Constants.getNewWidth(943.0f), Constants.getNewHeight(735.0f), 0.0f, 0.0f, "g_27", "STICKER", 6, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-288,-184", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(393.72656f), Constants.getNewY(-44.0f), Constants.getNewWidth(529.0f), Constants.getNewHeight(187.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-2,169", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EARLY", "", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(104.5f), Constants.getNewY(-8.5f), Constants.getNewWidth(329.0f), Constants.getNewHeight(119.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "98,203", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SALE", "", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(304.0f), Constants.getNewY(67.0f), Constants.getNewWidth(471.0f), Constants.getNewHeight(239.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "27,143", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "20% Off", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(255.31525f), Constants.getNewY(224.5f), Constants.getNewWidth(573.0f), Constants.getNewHeight(285.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-24,120", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ALMOST EVERYTHING IN STOCK", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(268.0f), Constants.getNewY(455.0f), Constants.getNewWidth(543.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-9,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Sale Begins Now", "ffont38.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(225.0f), Constants.getNewY(541.225f), Constants.getNewWidth(635.0f), Constants.getNewHeight(133.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-55,196", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ANY STREET, TOWN, COUNTRY", "ffont38.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(187.0f), Constants.getNewY(681.0f), Constants.getNewWidth(689.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "-82,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont38.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(296.0f), Constants.getNewY(712.0f), Constants.getNewWidth(483.0f), Constants.getNewHeight(125.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "21,200", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_4", "", "16:9", "Color", "90", "FRIDAY", "", "fff8f8f8", "", 80, 0));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -1, 255, Constants.getNewX(388.0f), Constants.getNewY(-19.0f), Constants.getNewWidth(809.0f), Constants.getNewHeight(161.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-142,182", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(-90.5f), Constants.getNewY(-13.0f), Constants.getNewWidth(635.0f), Constants.getNewHeight(153.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "-55,186", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Upto", "", -13421773, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(32.0f), Constants.getNewY(188.29254f), Constants.getNewWidth(137.0f), Constants.getNewHeight(393.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "194,66", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "70% Off", "", -13421773, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(188.0f), Constants.getNewY(131.0f), Constants.getNewWidth(249.0f), Constants.getNewHeight(505.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "138,10", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Company Name", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(537.5f), Constants.getNewY(189.0f), Constants.getNewWidth(565.0f), Constants.getNewHeight(249.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-20,138", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "IN-STORE & ONLINE", "ffont9.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(169.60913f), Constants.getNewY(120.304565f), Constants.getNewWidth(739.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-107,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Street, Address, City", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(538.0f), Constants.getNewY(434.0f), Constants.getNewWidth(595.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "www.website.com", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(538.0f), Constants.getNewY(519.0f), Constants.getNewWidth(595.0f), Constants.getNewHeight(85.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-35,220", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_5", "b45", "16:9", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(670.0f), Constants.getNewY(-11.0f), Constants.getNewWidth(431.0f), Constants.getNewHeight(431.0f), 0.0f, 0.0f, "u_17", "STICKER", 1, -16751002, 100, 0, 0, 0, 0, "", "white", 1, 0, "-32,-32", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "OUR LOWEST EVER PRICES", "", -16751002, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-54.632874f), Constants.getNewY(-11.234253f), Constants.getNewWidth(775.0f), Constants.getNewHeight(279.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-125,123", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "HURRY, ENDS MIDNIGHT MONDAY 27th NOVEMBER", "", -16751002, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(12.307007f), Constants.getNewY(301.60236f), Constants.getNewWidth(643.0f), Constants.getNewHeight(113.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "YOUR BRAND", "", -7323741, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(10.0f), Constants.getNewY(406.74988f), Constants.getNewWidth(647.0f), Constants.getNewHeight(217.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-61,154", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(702.76483f), Constants.getNewY(416.0f), Constants.getNewWidth(347.0f), Constants.getNewHeight(165.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "89,180", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_2", "b5", "3:4", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(557.0f), Constants.getNewY(117.0f), Constants.getNewWidth(411.0f), Constants.getNewHeight(123.0f), 0.0f, 0.0f, "sh30", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-22,122", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "YOUR SHOP NAME", "ffont31.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(28.34845f), Constants.getNewY(99.0f), Constants.getNewWidth(1027.0f), Constants.getNewHeight(151.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "-251,187", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Sale", "font22.otf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-81.0f), Constants.getNewY(720.0f), Constants.getNewWidth(581.0f), Constants.getNewHeight(323.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-28,101", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "CODE: BLACK", "font12.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(156.5f), Constants.getNewY(959.5f), Constants.getNewWidth(739.0f), Constants.getNewHeight(281.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Address: Shop 1, Town Mall, City Zip 00000", "font12.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(20.0f), Constants.getNewY(1222.0f), Constants.getNewWidth(1007.0f), Constants.getNewHeight(141.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-241,192", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "40% OFF EVERYTHING", "ffont9.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(493.28784f), Constants.getNewY(733.0f), Constants.getNewWidth(541.0f), Constants.getNewHeight(263.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-8,131", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY", "ffont48.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(-35.71521f), Constants.getNewY(282.0f), Constants.getNewWidth(1127.0f), Constants.getNewHeight(453.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-301,36", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_6", "", "3:4", "Color", "90", "FRIDAY", "", "ffff0027", "o22", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(358.0f), Constants.getNewY(210.0f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), 0.0f, 0.0f, "g_27", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "0,0", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "Best Brands & Joggers Included", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(286.0f), Constants.getNewY(125.0f), Constants.getNewWidth(527.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-1,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ENTIRE STORE", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(131.0f), Constants.getNewY(145.0f), Constants.getNewWidth(799.0f), Constants.getNewHeight(257.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-137,134", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "%", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(550.1998f), Constants.getNewY(514.1998f), Constants.getNewWidth(365.0f), Constants.getNewHeight(287.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "80,119", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BUY ONE, GET ONE", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(199.0f), Constants.getNewY(449.0f), Constants.getNewWidth(673.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-74,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(601.0f), Constants.getNewY(748.0f), Constants.getNewWidth(259.0f), Constants.getNewHeight(93.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "133,216", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "50", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(261.5f), Constants.getNewY(475.0f), Constants.getNewWidth(435.0f), Constants.getNewHeight(419.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "45,53", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "123 Main St, City.", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-1.0f), Constants.getNewY(1205.0f), Constants.getNewWidth(1083.0f), Constants.getNewHeight(91.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-90,34", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Your  Brand", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(80.548096f), Constants.getNewY(1056.0f), Constants.getNewWidth(903.0f), Constants.getNewHeight(159.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY WEEKEND", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(-3.3693848f), Constants.getNewY(907.7081f), Constants.getNewWidth(1123.0f), Constants.getNewHeight(89.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "-299,218", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "www.website.com", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(9.0f), Constants.getNewY(1306.0f), Constants.getNewWidth(1083.0f), Constants.getNewHeight(91.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_9", "", "3:4", "Color", "90", "FRIDAY", "", "ff000000", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(630.5f), Constants.getNewY(117.0f), Constants.getNewWidth(459.0f), Constants.getNewHeight(421.0f), 0.0f, 0.0f, "sh17", "STICKER", 0, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-46,-27", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(744.6611f), Constants.getNewY(27.5f), Constants.getNewWidth(233.0f), Constants.getNewHeight(321.0f), 0.0f, 0.0f, "sh11", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "67,23", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(28.024399f), Constants.getNewY(-31.920166f), Constants.getNewWidth(615.0f), Constants.getNewHeight(719.0f), -90.0f, 0.0f, "sh34", "STICKER", 4, -3407821, 100, 0, 0, 0, 0, "", "white", 1, 0, "-124,-176", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "DEALS", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(692.5f), Constants.getNewY(313.0f), Constants.getNewWidth(333.0f), Constants.getNewHeight(155.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "96,185", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "GET BEST", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(686.0f), Constants.getNewY(189.0f), Constants.getNewWidth(345.0f), Constants.getNewHeight(181.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "90,172", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "Company Name", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(71.0f), Constants.getNewY(174.5f), Constants.getNewWidth(527.0f), Constants.getNewHeight(305.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "-1,110", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "IN STORE & COMPANY.COM THURSDAY - 5PM FRIDAY", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(13.5f), Constants.getNewY(548.0f), Constants.getNewWidth(1053.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-264,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY DEALS", "", -5374161, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(20.0f), Constants.getNewY(629.0f), Constants.getNewWidth(1043.0f), Constants.getNewHeight(135.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-259,195", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "DOORS OPEN AT", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(213.5763f), Constants.getNewY(753.0f), Constants.getNewWidth(651.0f), Constants.getNewHeight(127.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "-63,199", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "GET IN STORE EARLY THURSDAY FOR A COUPON GIVEAWAY!", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(58.0f), Constants.getNewY(1245.0f), Constants.getNewWidth(965.0f), Constants.getNewHeight(143.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "-220,191", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "THURSDAY", "", -65485, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(277.2207f), Constants.getNewY(1069.0f), Constants.getNewWidth(525.0f), Constants.getNewHeight(137.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "0,194", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "3PM", "", -65485, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(299.0f), Constants.getNewY(865.0f), Constants.getNewWidth(483.0f), Constants.getNewHeight(257.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "21,134", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_11", "b11", "3:4", "Temp_Path", "90", "FRIDAY", "/storage/emulated/0/DCIM/" + getString(R.string.app_name) + "/.Poster Maker Stickers/category1/bh2.png", "", "", 80, 172));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(56.00006f), Constants.getNewY(194.00006f), Constants.getNewWidth(961.0f), Constants.getNewHeight(935.0f), -90.0f, 0.0f, "sh34", "STICKER", 0, ViewCompat.MEASURED_STATE_MASK, 67, 0, 0, 0, 0, "", "white", 1, 0, "-297,-284", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-8.499939f), Constants.getNewY(183.0f), Constants.getNewWidth(1097.0f), Constants.getNewHeight(959.0f), -90.0f, 0.0f, "sh14", "STICKER", 1, 0, 100, 0, 0, 0, 0, "", "white", 1, 0, "-365,-296", "", "", null, null));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(9.00001f), Constants.getNewY(25.000006f), Constants.getNewWidth(367.0f), Constants.getNewHeight(367.0f), -25.268269f, 0.0f, "b_21", "STICKER", 7, 0, 100, 0, 0, 0, 0, "", "colored", 17, 0, "0,0", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "SEMI - ANNUAL CLEARANCE SALE", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(146.0f), Constants.getNewY(407.0f), Constants.getNewWidth(787.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "-131,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(754.0f), Constants.getNewY(799.0f), Constants.getNewWidth(237.0f), Constants.getNewHeight(123.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "144,201", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "60%", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(281.0f), Constants.getNewY(405.0f), Constants.getNewWidth(701.0f), Constants.getNewHeight(505.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-88,10", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(84.0f), Constants.getNewY(506.0f), Constants.getNewWidth(219.0f), Constants.getNewHeight(315.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "153,105", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(347.0f), Constants.getNewY(950.0f), Constants.getNewWidth(387.0f), Constants.getNewHeight(135.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "69,195", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "23-NOV-17", "ffont6.ttf", -1460162, 100, ViewCompat.MEASURED_STATE_MASK, 20, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(279.0f), Constants.getNewY(1276.5f), Constants.getNewWidth(525.0f), Constants.getNewHeight(83.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "0,221", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME, STREET, COUNTRY   (123) 456 789", "ffont6.ttf", -1131719, 100, ViewCompat.MEASURED_STATE_MASK, 20, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-8.5f), Constants.getNewY(1359.6708f), Constants.getNewWidth(1095.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "-285,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "ffont6.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -1, 255, Constants.getNewX(346.7965f), Constants.getNewY(1084.9592f), Constants.getNewWidth(385.0f), Constants.getNewHeight(103.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "70,211", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_13", "b6", "3:4", "Background", "90", "FRIDAY", "", "", "o6", 128, 124));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-28.0f), Constants.getNewY(111.0f), Constants.getNewWidth(1135.0f), Constants.getNewHeight(813.0f), 0.0f, 0.0f, "g_27", "STICKER", 2, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-384,-223", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "20%", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(265.5f), Constants.getNewY(12.804138f), Constants.getNewWidth(549.0f), Constants.getNewHeight(335.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-12,95", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "OFF", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(355.0f), Constants.getNewY(270.0f), Constants.getNewWidth(371.0f), Constants.getNewHeight(215.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "77,155", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont10.ttf", -1, 100, -10496, 0, "0", 0, 0, Constants.getNewX(89.0f), Constants.getNewY(554.5f), Constants.getNewWidth(893.0f), Constants.getNewHeight(179.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-184,173", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK", "ffont13.ttf", -1, 100, -10496, 0, "0", ViewCompat.MEASURED_STATE_MASK, 255, Constants.getNewX(325.5f), Constants.getNewY(854.5f), Constants.getNewWidth(427.0f), Constants.getNewHeight(167.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "49,179", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FRIDAY", "ffont13.ttf", ViewCompat.MEASURED_STATE_MASK, 100, -10496, 0, "0", -1, 255, Constants.getNewX(401.0f), Constants.getNewY(1008.2932f), Constants.getNewWidth(279.0f), Constants.getNewHeight(103.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "123,211", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FURNITURE BRANDS", "ffont14.ttf", -10496, 100, -10496, 0, "0", 0, 0, Constants.getNewX(58.14215f), Constants.getNewY(698.0f), Constants.getNewWidth(959.0f), Constants.getNewHeight(159.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-217,183", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "1100 STREET, TOWN (123) 456 789", "ffont10.ttf", -10496, 100, -10496, 0, "0", -1, 0, Constants.getNewX(262.0f), Constants.getNewY(1281.0f), Constants.getNewWidth(553.0f), Constants.getNewHeight(133.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-14,196", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "HURRY UP!", "ffont10.ttf", -1, 100, -10496, 0, "0", -1, 0, Constants.getNewX(308.0f), Constants.getNewY(1152.5f), Constants.getNewWidth(467.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_25", "b15", "3:4", "Background", "90", "FRIDAY", "", "", "", 88, 255));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME STREET, COUNTRY (123) 456 789", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 3, "0", 0, 0, Constants.getNewX(357.0f), Constants.getNewY(1244.0f), Constants.getNewWidth(359.0f), Constants.getNewHeight(185.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -9224030, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(712.27313f), Constants.getNewY(283.00003f), Constants.getNewWidth(253.0f), Constants.getNewHeight(99.0f), -10.28814f, "TEXT", 1, 0, 0, 0, 0, 0, "136,213", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -4490762, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(717.5911f), Constants.getNewY(277.00003f), Constants.getNewWidth(253.0f), Constants.getNewHeight(99.0f), -10.683027f, "TEXT", 2, 0, 0, 0, 0, 0, "136,213", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "70% OFF", "ffont10.ttf", -9224030, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(187.0f), Constants.getNewY(382.99976f), Constants.getNewWidth(725.0f), Constants.getNewHeight(763.0f), -9.880583f, "TEXT", 3, 0, 0, 0, 0, 0, "-100,-119", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "70% OFF", "ffont10.ttf", -5017868, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(198.99997f), Constants.getNewY(368.9997f), Constants.getNewWidth(725.0f), Constants.getNewHeight(763.0f), -9.169726f, "TEXT", 4, 0, 0, 0, 0, 0, "-100,-119", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NEW STYLES ADDED!", "ffont10.ttf", -5017868, 84, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(135.0f), Constants.getNewY(1123.1919f), Constants.getNewWidth(819.0f), Constants.getNewHeight(105.0f), -10.755405f, "TEXT", 5, 0, 0, 0, 0, 0, "-147,210", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "NEW STYLES ADDED!", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(142.9325f), Constants.getNewY(1119.192f), Constants.getNewWidth(819.0f), Constants.getNewHeight(105.0f), -10.111114f, "TEXT", 6, 0, 0, 0, 0, 0, "-147,210", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ITS ABOUT TO GET REAL (LOW)!", "ffont10.ttf", -1354753, 100, -8305933, 0, "0", 0, 0, Constants.getNewX(65.00009f), Constants.getNewY(85.99997f), Constants.getNewWidth(941.0f), Constants.getNewHeight(81.0f), -9.374797f, "TEXT", 7, 0, 0, 0, 0, 0, "-208,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY DEALS", "ffont10.ttf", -9945711, 50, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(89.21417f), Constants.getNewY(194.26277f), Constants.getNewWidth(899.0f), Constants.getNewHeight(119.0f), -9.592758f, "TEXT", 8, 0, 0, 0, 0, 0, "-187,203", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "BLACK FRIDAY DEALS", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(96.71039f), Constants.getNewY(188.26273f), Constants.getNewWidth(899.0f), Constants.getNewHeight(119.0f), -9.535547f, "TEXT", 9, 0, 0, 0, 0, 0, "-187,203", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_10", "b11", "9:16", "Background", "90", "FRIDAY", "", "", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-13.0f), Constants.getNewY(828.0f), Constants.getNewWidth(885.0f), Constants.getNewHeight(715.0f), 0.0f, 0.0f, "g_27", "STICKER", 8, 0, 100, 0, 0, 0, 0, "", "colored", 100, 0, "-259,-174", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "FREE SHIPPING ON EVERY ORDER", "", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(-11.223389f), Constants.getNewY(15.0f), Constants.getNewWidth(875.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-175,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FOR Menhhdjdhdjdjdh hshshsjshshfs xbhdjdudhdh day bxnxudbdkdjd", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(900.00006f), Constants.getNewY(989.0f), Constants.getNewWidth(83.0f), Constants.getNewHeight(85.0f), 1600.4055f, "TEXT", 1, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SAVE BIG", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(179.0f), Constants.getNewY(113.0f), Constants.getNewWidth(495.0f), Constants.getNewHeight(187.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "15,169", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ON BLACK FRIDAY", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(15.5f), Constants.getNewY(245.0f), Constants.getNewWidth(823.0f), Constants.getNewHeight(185.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "-149,170", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FOR WOMEN", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(121.0f), Constants.getNewY(1376.0f), Constants.getNewWidth(245.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "140,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FOR MEN", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", -3407821, 255, Constants.getNewX(486.0f), Constants.getNewY(1376.0f), Constants.getNewWidth(245.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 5, 0, 0, 0, 0, 0, "", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "|", "", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(387.0f), Constants.getNewY(1284.0f), Constants.getNewWidth(79.0f), Constants.getNewHeight(233.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "223,146", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NEW ARRIVALS", "ffont6.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(109.0f), Constants.getNewY(1218.5f), Constants.getNewWidth(629.0f), Constants.getNewHeight(103.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-52,211", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EXCLUDES GIFT CARDS", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(216.5f), Constants.getNewY(1063.0f), Constants.getNewWidth(423.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "51,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "EVERYTHING", "ffont25.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(106.0f), Constants.getNewY(737.9966f), Constants.getNewWidth(641.0f), Constants.getNewHeight(255.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "-58,135", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UNTIL MIDNIGHT", "ffont10.ttf", ViewCompat.MEASURED_STATE_MASK, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(164.39563f), Constants.getNewY(963.3956f), Constants.getNewWidth(523.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "1,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "50", "ffont25.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(-8.589172f), Constants.getNewY(325.05396f), Constants.getNewWidth(633.0f), Constants.getNewHeight(541.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "-54,-8", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "% OFF", "ffont25.ttf", -3407821, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", ViewCompat.MEASURED_STATE_MASK, 0, Constants.getNewX(534.75745f), Constants.getNewY(403.0f), Constants.getNewWidth(191.0f), Constants.getNewHeight(387.0f), 0.0f, "TEXT", 13, 0, 0, 0, 0, 0, "167,69", "", ""));
        templateId = (int) dh.insertTemplateRow(new TemplateInfo("fri_18", "", "9:16", "Color", "90", "FRIDAY", "", "ffcbcbcb", "", 80, 0));
        dh.insertComponentInfoRow(new ComponentInfo(templateId, Constants.getNewX(-105.0f), Constants.getNewY(637.0f), Constants.getNewWidth(1059.0f), Constants.getNewHeight(749.0f), 0.0f, 0.0f, "g_27", "STICKER", 5, 0, 100, 0, 0, 0, 0, "", "colored", 0, 0, "-346,-191", "", "", null, null));
        dh.insertTextRow(new TextInfo(templateId, "------------SHOP NOW------------", "ffont7.otf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(51.0f), Constants.getNewY(239.0f), Constants.getNewWidth(751.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 0, 0, 0, 0, 0, 0, "-113,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "40", "ffont25.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(202.0f), Constants.getNewY(213.0f), Constants.getNewWidth(451.0f), Constants.getNewHeight(601.0f), 0.0f, "TEXT", 1, 0, 0, 0, 0, 0, "37,-38", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "UP TO", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(95.0f), Constants.getNewY(341.0f), Constants.getNewWidth(121.0f), Constants.getNewHeight(205.0f), 0.0f, "TEXT", 2, 0, 0, 0, 0, 0, "202,160", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "%OFF", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(218.0f), Constants.getNewY(643.0f), Constants.getNewWidth(411.0f), Constants.getNewHeight(235.0f), 0.0f, "TEXT", 3, 0, 0, 0, 0, 0, "57,145", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SELECT STYLES", "ffont10.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(105.0f), Constants.getNewY(819.0f), Constants.getNewWidth(641.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 4, 0, 0, 0, 0, 0, "-58,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "TEES, SHORTS, DRESSES, MORE!", "ffont10.ttf", -11960662, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(109.5f), Constants.getNewY(904.0f), Constants.getNewWidth(621.0f), Constants.getNewHeight(83.0f), 0.0f, "TEXT", 6, 0, 0, 0, 0, 0, "-48,221", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SHOP NAME", "ffont10.ttf", -11960662, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(132.0f), Constants.getNewY(35.0f), Constants.getNewWidth(585.0f), Constants.getNewHeight(139.0f), 0.0f, "TEXT", 7, 0, 0, 0, 0, 0, "-30,193", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "ANY STREET, TOWN, COUNTRY", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(120.0f), Constants.getNewY(1328.0f), Constants.getNewWidth(615.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 8, 0, 0, 0, 0, 0, "-45,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "WWW.YOURWEBSITE.COM", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(210.0f), Constants.getNewY(1446.0f), Constants.getNewWidth(445.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 9, 0, 0, 0, 0, 0, "40,223", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "(123) 456 789", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(292.18982f), Constants.getNewY(1387.5f), Constants.getNewWidth(269.0f), Constants.getNewHeight(81.0f), 0.0f, "TEXT", 10, 0, 0, 0, 0, 0, "128,222", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "SALE", "ffont44.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(180.23431f), Constants.getNewY(986.7656f), Constants.getNewWidth(495.0f), Constants.getNewHeight(291.0f), 0.0f, "TEXT", 11, 0, 0, 0, 0, 0, "15,117", "", ""));
        dh.insertTextRow(new TextInfo(templateId, "FOR WOMEN | FOR MEN", "ffont6.ttf", -1, 100, ViewCompat.MEASURED_STATE_MASK, 0, "0", 0, 0, Constants.getNewX(199.8534f), Constants.getNewY(1200.0f), Constants.getNewWidth(453.0f), Constants.getNewHeight(79.0f), 0.0f, "TEXT", 12, 0, 0, 0, 0, 0, "36,223", "", ""));


        Log.e("not rrrrr", "eeeee");
        this.isDataStored = true;
        this.editor = this.appPreferences.edit();
        this.editor.putBoolean("isDataStored", true);
        this.editor.commit();
        if (st.equals("yes")) {
            HideTextAnimation();
        }
    }

    class running_thread implements Runnable {
        running_thread() {
        }

        public void run() {
            if (MainActivity.this.count == 0) {
                MainActivity.this.count = 1;
//                MainActivity.this.txt_load.setText(MainActivity.this.getResources().getString(R.string.loading));
            } else if (MainActivity.this.count == 1) {
                MainActivity.this.count = 2;
//                MainActivity.this.txt_load.setText(MainActivity.this.getResources().getString(R.string.loading) + ".");
            } else if (MainActivity.this.count == 2) {
                MainActivity.this.count = 3;
//                MainActivity.this.txt_load.setText(MainActivity.this.getResources().getString(R.string.loading) + "..");
            } else if (MainActivity.this.count == 3) {
                MainActivity.this.count = 0;
//                MainActivity.this.txt_load.setText(MainActivity.this.getResources().getString(R.string.loading) + "...");
            }
            MainActivity.this.handler.postDelayed(MainActivity.this.runnable, 400);
        }
    }

    private class DummyAsync extends AsyncTask<String, String, Boolean> {
        private DummyAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            MainActivity.this.init("no");
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            HideTextAnimation();
        }
    }

    private class SaveStickersAsync extends AsyncTask<String, String, Boolean> {
        int size;

        private SaveStickersAsync() {
            this.size = 0;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            String stkrName = params[0];
            this.size = Integer.parseInt(params[1]) + 1;
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(MainActivity.this.getResources(), MainActivity.this.getResources().getIdentifier(stkrName, "drawable", MainActivity.this.getPackageName()));
                if (bitmap != null) {
                    return Boolean.valueOf(saveBitmapObject(bitmap, stkrName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (isDownloaded.booleanValue()) {
                if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1").listFiles().length >= MainActivity.this.stkrNameList.size()) {
                    MainActivity.this.init("yes");
                }
            } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name) + "/.Poster Maker Stickers/category1").listFiles().length >= MainActivity.this.stkrNameList.size()) {
                MainActivity.this.init("yes");
            }
        }

        private boolean saveBitmapObject(Bitmap bitmap, String fname) {
            File myDir = Constants.getSaveFileLocation("category1");
            myDir.mkdirs();
            File file = new File(myDir, fname + ".png");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream ostream = new FileOutputStream(file);
                boolean saved = bitmap.compress(CompressFormat.PNG, 100, ostream);
                ostream.close();
                return saved;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("testing", "Exception" + e.getMessage());
                return false;
            }
        }
    }

}