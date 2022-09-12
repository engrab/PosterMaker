package ab.cd.ef.postermaker.main;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import ab.cd.ef.postermaker.R;

public class PickColorImageActivity extends Activity {
    float f10r;
    int height;
    Bitmap imgBtmap;
    float initialX;
    float initialY;
    GetColorListener onGetColor;
    int pixel = -1;
    float rat1;
    float rat2;
    float screenHeight;
    float screenWidth;
    String way;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.dialog_color);
        this.onGetColor = (GetColorListener) PosterActivity.f24c;
        this.way = getIntent().getStringExtra("way");
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.screenWidth = (float) dimension.widthPixels;
        this.height = 55;
        this.screenHeight = (float) (dimension.heightPixels - this.height);
        ImageView img_base = (ImageView) findViewById(R.id.img_base);
        TextView txt_head = (TextView) findViewById(R.id.txt_head);
        TextView tt = (TextView) findViewById(R.id.tt);
        this.imgBtmap = PosterActivity.imgBtmap;
        float wd = (float) this.imgBtmap.getWidth();
        float he = (float) this.imgBtmap.getHeight();
        this.rat1 = wd / he;
        this.rat2 = he / wd;
        if (wd > this.screenWidth) {
            wd = this.screenWidth;
            he = wd * this.rat2;
        } else if (wd < this.screenWidth) {
            wd = this.screenWidth;
            he = wd * this.rat2;
        }
        this.f10r = wd / he;
        Bitmap bitmap = Bitmap.createScaledBitmap(this.imgBtmap, (int) wd, (int) he, false);
        img_base.getLayoutParams().width = (int) wd;
        img_base.getLayoutParams().height = (int) he;
        final ImageView img_putcolor = (ImageView) findViewById(R.id.img_putcolor);
        img_base.setImageBitmap(bitmap);
        ImageView img_done = (ImageView) findViewById(R.id.img_done);
        ImageView img_back = (ImageView) findViewById(R.id.img_back);
        img_base.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case 0:
                        PickColorImageActivity.this.initialX = event.getX();
                        PickColorImageActivity.this.initialY = event.getY();
                        try {
                            PickColorImageActivity.this.pixel = PickColorImageActivity.this.imgBtmap.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                            img_putcolor.setBackgroundColor(PickColorImageActivity.this.pixel);
                            break;
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            PickColorImageActivity.this.pixel = 0;
                            break;
                        }
                    case 2:
                        PickColorImageActivity.this.initialX = event.getX();
                        PickColorImageActivity.this.initialY = event.getY();
                        try {
                            PickColorImageActivity.this.pixel = PickColorImageActivity.this.imgBtmap.getPixel((int) PickColorImageActivity.this.initialX, (int) PickColorImageActivity.this.initialY);
                            img_putcolor.setBackgroundColor(PickColorImageActivity.this.pixel);
                            break;
                        } catch (IllegalArgumentException e2) {
                            e2.printStackTrace();
                            PickColorImageActivity.this.pixel = 0;
                            break;
                        }
                }
                return true;
            }
        });

        img_done.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PickColorImageActivity.this.onGetColor.onColor(PickColorImageActivity.this.pixel, PickColorImageActivity.this.way);
                PickColorImageActivity.this.finish();
            }
        });

        img_back.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PickColorImageActivity.this.onBackPressed();
            }
        });

    }

    public void onBackPressed() {
        super.onBackPressed();
        this.onGetColor.onColor(0, this.way);
        finish();
    }
}
