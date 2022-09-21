package com.amt.postermaker.graphicdesign.flyer.bannermaker.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.crop.CropImageView;

public class CropActivity extends Activity {
    public static Bitmap bitmapImage;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    CropImageView cropimage;
    Button custom;
    ImageView done;
    RelativeLayout footer;
    RelativeLayout header;
    TextView headertext;
    Button ratio1;
    Button ratio10;
    Button ratio11;
    Button ratio12;
    Button ratio13;
    Button ratio14;
    Button ratio15;
    Button ratio2;
    Button ratio3;
    Button ratio4;
    Button ratio5;
    Button ratio6;
    Button ratio7;
    Button ratio8;
    Button ratio9;
    RelativeLayout rel;
    Button square;
    Typeface ttf;
    Typeface ttfHeader;
    String value;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crop);

        Toast.makeText(this, "Crop ", Toast.LENGTH_SHORT).show();

        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.cropimage = (CropImageView) findViewById(R.id.cropimage);
        this.done =  findViewById(R.id.done);
        this.custom = (Button) findViewById(R.id.cutom);
        this.square = (Button) findViewById(R.id.square);
        this.ratio1 = (Button) findViewById(R.id.ratio1);
        this.ratio2 = (Button) findViewById(R.id.ratio2);
        this.ratio3 = (Button) findViewById(R.id.ratio3);
        this.ratio4 = (Button) findViewById(R.id.ratio4);
        this.ratio5 = (Button) findViewById(R.id.ratio5);
        this.ratio6 = (Button) findViewById(R.id.ratio6);
        this.ratio7 = (Button) findViewById(R.id.ratio7);
        this.ratio8 = (Button) findViewById(R.id.ratio8);
        this.ratio9 = (Button) findViewById(R.id.ratio9);
        this.ratio10 = (Button) findViewById(R.id.ratio10);
        this.ratio11 = (Button) findViewById(R.id.ratio11);
        this.ratio12 = (Button) findViewById(R.id.ratio12);
        this.ratio13 = (Button) findViewById(R.id.ratio13);
        this.ratio14 = (Button) findViewById(R.id.ratio14);
        this.ratio15 = (Button) findViewById(R.id.ratio15);
        this.headertext = (TextView) findViewById(R.id.headertext);
        this.bottomUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_up);
        this.bottomDown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.bottom_down);
        this.footer.setVisibility(View.VISIBLE);
        this.footer.startAnimation(this.bottomUp);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.headertext.setTypeface(this.ttfHeader);
        this.custom.setTypeface(this.ttf, 1);
        this.square.setTypeface(this.ttf, 1);
        if (getIntent().getExtras().getString("value").equals("image")) {
            this.value = "image";
            this.bitmap = SelectImageActivity.bitmap;
        } else if (getIntent().getExtras().getString("value").equals("sticker")) {
            this.value = "sticker";
            this.bitmap = PosterActivity.btmSticker;
        }
        this.bitmap = resizeBitmap(this.bitmap, getIntent().getIntExtra("forcal", 102));
        this.cropimage.setImageBitmap(this.bitmap);



        findViewById(R.id.btn_bck).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.finish();
            }
        });



        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.bitmap = CropActivity.this.cropimage.getCroppedImage();
                CropActivity.bitmapImage = CropActivity.this.bitmap;
                CropActivity.this.setResult(-1);
                CropActivity.this.finish();
            }
        });



        this.custom.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(false);
            }
        });


        this.square.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(1, 1);
            }
        });


        this.ratio1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(1, 2);
            }
        });



        this.ratio2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(2, 1);
            }
        });


        this.ratio3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(2, 3);
            }
        });


        this.ratio4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(3, 2);
            }
        });


        this.ratio5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(3, 4);
            }
        });


        this.ratio6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(3, 5);
            }
        });

        this.ratio7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 3);
            }
        });


        this.ratio8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 5);
            }
        });


        this.ratio9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(4, 7);
            }
        });


        this.ratio10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 3);
            }
        });


        this.ratio11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 6);
            }
        });


        this.ratio12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 4);
            }
        });


        this.ratio13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(5, 7);
            }
        });


        this.ratio14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(9, 16);
            }
        });
        this.ratio15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivity.this.cropimage.setFixedAspectRatio(true);
                CropActivity.this.cropimage.setAspectRatio(16, 9);
            }
        });
    }

    Bitmap resizeBitmap(Bitmap bit, int forcal) {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float wr = (float) dm.widthPixels;
        float hr = ((float) dm.heightPixels) - ((float) forcal);
        float wd = (float) bit.getWidth();
        float he = (float) bit.getHeight();
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
        } else {
            wd = wr;
            he = wd * rat2;
        }
        return Bitmap.createScaledBitmap(bit, (int) wd, (int) he, false);
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
