package ab.cd.ef.postermaker.main;

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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.crop.CropImageView;
import ab.cd.ef.postermaker.utility.ImageUtils;

public class CropActivityTwo extends Activity {
    public static Bitmap bitmapImage;
    Bitmap bitmap;
    Animation bottomDown;
    Animation bottomUp;
    CropImageView cropimage;
    Button custom;
    Button done;
    RelativeLayout footer;
    OnSetImageSticker getSticker;
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
    float screenHeight;
    float screenWidth;
    Button square;
    Typeface ttf;
    Typeface ttfHeader;
    String value;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_crop);

        Toast.makeText(this, "Crop 1", Toast.LENGTH_SHORT).show();

        this.getSticker = PosterActivity.activity;
        this.header = (RelativeLayout) findViewById(R.id.header);
        this.rel = (RelativeLayout) findViewById(R.id.rel);
        this.footer = (RelativeLayout) findViewById(R.id.footer);
        this.footer.setVisibility(View.INVISIBLE);
        this.cropimage = (CropImageView) findViewById(R.id.cropimage);
        this.done = (Button) findViewById(R.id.done);
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
            this.bitmap = SelectImageTwoActivity.bitmap;
        } else if (getIntent().getExtras().getString("value").equals("sticker")) {
            this.value = "sticker";
            this.bitmap = PosterActivity.btmSticker;
        }
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        this.screenWidth = (float) dm.widthPixels;
        this.screenHeight = (float) (dm.heightPixels - ImageUtils.dpToPx(this, 105));
        int htd = getIntent().getIntExtra("forcal", 102);
        this.cropimage.setImageBitmap(this.bitmap);


        findViewById(R.id.btn_bck).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.finish();
            }
        });



        this.done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.bitmap = CropActivityTwo.this.cropimage.getCroppedImage();
                if (CropActivityTwo.this.value.equals("image")) {
                    CropActivityTwo.bitmapImage = CropActivityTwo.this.bitmap;
                    CropActivityTwo.this.setResult(-1);
                    CropActivityTwo.this.finish();
                    return;
                }
                CropActivityTwo.this.bitmap = ImageUtils.resizeBitmap(CropActivityTwo.this.bitmap, (int) CropActivityTwo.this.screenWidth, (int) CropActivityTwo.this.screenHeight);
                CropActivityTwo.bitmapImage = CropActivityTwo.this.bitmap;
                CropActivityTwo.this.getSticker.ongetSticker();
                CropActivityTwo.this.finish();

            }
        });



        this.custom.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(false);
            }
        });


        this.square.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(1, 1);
            }
        });


        this.ratio1.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(1, 2);
            }
        });



        this.ratio2.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(2, 1);
            }
        });


        this.ratio3.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(2, 3);
            }
        });


        this.ratio4.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(3, 2);
            }
        });


        this.ratio5.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(3, 4);
            }
        });


        this.ratio6.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(3, 5);
            }
        });

        this.ratio7.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 3);
            }
        });


        this.ratio8.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 5);
            }
        });


        this.ratio9.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(4, 7);
            }
        });


        this.ratio10.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 3);
            }
        });


        this.ratio11.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 6);
            }
        });


        this.ratio12.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 4);
            }
        });


        this.ratio13.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(5, 7);
            }
        });

        this.ratio14.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(9, 16);
            }
        });
        this.ratio15.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                CropActivityTwo.this.cropimage.setFixedAspectRatio(true);
                CropActivityTwo.this.cropimage.setAspectRatio(16, 9);
            }
        });
    }

    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) getSystemService("connectivity")).getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
