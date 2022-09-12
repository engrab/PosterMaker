package ab.cd.ef.postermaker.main;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.common.Scopes;

import org.apache.http.HttpStatus;

import java.io.File;
import java.lang.reflect.Field;

import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.utility.ImageUtils;
import ab.cd.ef.postermaker.utility.SlowScroller;
import ab.cd.ef.postermaker.utility.ZoomOutSlideTransformer;

public class SelectImageTwoActivity extends FragmentActivity implements MaterialTabListener, OnClickListener, OnGetImageOnTouch {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    public static Bitmap bitmap;
    ViewPagerAdapter adapter;
    private Animation animSlideDown;
    private Animation animSlideUp;
    Editor editor;
    File f21f;
    FragmentBackgrounds fragmentbackgund = null;
    FragmentColor fragmentcolor = null;
    FragmentImage fragmentimage = null;
    FragmentTexture fragmenttexture = null;
    String hex12 = "";
    ImageView image1;
    ImageView image2;
    ImageView image3;
    ImageView image4;
    ImageView image5;
    RelativeLayout image_container;
    RelativeLayout lay_crop;
    ViewPager pager;
    String position;
    SharedPreferences preferences;
    SharedPreferences prefs;
    String profile;
    float screenHeight;
    float screenWidth;
    MaterialTabHost tabHost;
    private Typeface ttf;
    private Typeface ttfHeader;

    class dialog_click implements DialogInterface.OnClickListener {
        dialog_click() { }

        public void onClick(DialogInterface dialog, int which) {
            dialog.cancel();
        }
    }
    
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public Fragment getItem(int num) {
            if (num == 0) {
                SelectImageTwoActivity.this.fragmentbackgund = new FragmentBackgrounds();
                return SelectImageTwoActivity.this.fragmentbackgund;
            } else if (num == 1) {
                SelectImageTwoActivity.this.fragmenttexture = new FragmentTexture();
                return SelectImageTwoActivity.this.fragmenttexture;
            } else if (num == 2) {
                SelectImageTwoActivity.this.fragmentimage = new FragmentImage();
                return SelectImageTwoActivity.this.fragmentimage;
            } else if (num != 3) {
                return null;
            } else {
                SelectImageTwoActivity.this.fragmentcolor = new FragmentColor();
                return SelectImageTwoActivity.this.fragmentcolor;
            }
        }

        public int getCount() {
            return 4;
        }

        public CharSequence getPageTitle(int position) {
            CharSequence text = "";
            if (position == 0) {
                return ImageUtils.getSpannableString(SelectImageTwoActivity.this, SelectImageTwoActivity.this.ttf, R.string.txt_backgrounds);
            }
            if (position == 1) {
                return ImageUtils.getSpannableString(SelectImageTwoActivity.this, SelectImageTwoActivity.this.ttf, R.string.txt_texture);
            }
            if (position == 2) {
                return ImageUtils.getSpannableString(SelectImageTwoActivity.this, SelectImageTwoActivity.this.ttf, R.string.txt_image);
            }
            if (position == 3) {
                return ImageUtils.getSpannableString(SelectImageTwoActivity.this, SelectImageTwoActivity.this.ttf, R.string.txt_color);
            }
            return text;
        }
    }
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_image);
        this.ttf = Constants.getTextTypeface(this);
        this.ttfHeader = Constants.getHeaderTypeface(this);
        this.tabHost = (MaterialTabHost) findViewById(R.id.tabHost);
        this.pager = (ViewPager) findViewById(R.id.pager);
        MaterialTab t1 = new MaterialTab(this, false);
        MaterialTab t2 = new MaterialTab(this, false);
        MaterialTab t3 = new MaterialTab(this, false);
        MaterialTab t4 = new MaterialTab(this, false);
        this.tabHost.addTab(t1.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_backgrounds)).setTabListener(this));
        this.tabHost.addTab(t2.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_texture)).setTabListener(this));
        this.tabHost.addTab(t3.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_image)).setTabListener(this));
        this.tabHost.addTab(t4.setText(ImageUtils.getSpannableString(this, this.ttf, R.string.txt_color)).setTabListener(this));
        initUI();
        this.animSlideUp = Constants.getAnimUp(this);
        this.animSlideDown = Constants.getAnimDown(this);
        this.image_container = (RelativeLayout) findViewById(R.id.image_container);
        this.lay_crop = (RelativeLayout) findViewById(R.id.lay_crop);
        this.image1 = (ImageView) findViewById(R.id.image1);
        this.image2 = (ImageView) findViewById(R.id.image2);
        this.image3 = (ImageView) findViewById(R.id.image3);
        this.image4 = (ImageView) findViewById(R.id.image4);
        this.image5 = (ImageView) findViewById(R.id.image5);
        this.image1.setOnClickListener(this);
        this.image2.setOnClickListener(this);
        this.image3.setOnClickListener(this);
        this.image4.setOnClickListener(this);
        this.image5.setOnClickListener(this);
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.screenWidth = (float) dimension.widthPixels;
        this.screenHeight = (float) dimension.heightPixels;
        this.f21f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
        ((TextView) findViewById(R.id.txt_appname)).setTypeface(this.ttfHeader);
        ((Button) findViewById(R.id.btn_create)).setVisibility(View.GONE);
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
    }

    private void initUI() {
        this.adapter = new ViewPagerAdapter(getSupportFragmentManager());
        this.adapter.notifyDataSetChanged();
        this.pager.setAdapter(this.adapter);
        this.pager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                
            }

            @Override
            public void onPageSelected(int position) {
                SelectImageTwoActivity.this.tabHost.setSelectedNavigationItem(position);
                if (SelectImageTwoActivity.this.image_container.getVisibility() == View.VISIBLE) {
                    SelectImageTwoActivity.this.image_container.startAnimation(SelectImageTwoActivity.this.animSlideDown);
                    SelectImageTwoActivity.this.image_container.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        this.pager.setPageTransformer(true, new ZoomOutSlideTransformer());
        changePagerScroller();
    }

    private void changePagerScroller() {
        try {
            Field mScroller = ViewPager.class.getDeclaredField("mScroller");
            mScroller.setAccessible(true);
            mScroller.set(this.pager, new SlowScroller(this.pager.getContext()));
        } catch (Exception e) {
            Log.e("texting", "error of change scroller ", e);
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

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_bck:
                onBackPressed();
                return;
            case R.id.image1:
                callActivity("1:1");
                return;
            case R.id.image2:
                callActivity("16:9");
                return;
            case R.id.image3:
                callActivity("9:16");
                return;
            case R.id.image4:
                callActivity("4:3");
                return;
            case R.id.image5:
                callActivity("3:4");
                return;
            default:
                return;
        }
    }

    private void callActivity(String s) {
        Bundle bundle = new Bundle();
        bundle.putString("ratio", s);
        bundle.putString("position", this.position);
        bundle.putString(Scopes.PROFILE, this.profile);
        bundle.putString("color", this.hex12);
        Intent returnIntent = new Intent();
        returnIntent.putExtras(bundle);
        setResult(-1, returnIntent);
        finish();
    }

    public void ongetPosition(int i, String str, String hex) {
        this.position = "" + i;
        this.profile = str;
        this.hex12 = hex;
        if (this.image_container.getVisibility() != View.VISIBLE) {
            this.image_container.startAnimation(this.animSlideUp);
            this.image_container.setVisibility(View.VISIBLE);
        }
        Bitmap btm = null;
        if (str.equals("Background")) {
            Options options = new Options();
            options.inJustDecodeBounds = true;
            options.inSampleSize = 10;
            options.inJustDecodeBounds = false;
            btm = BitmapFactory.decodeResource(getResources(), Constants.Imageid0[i], options);
        } else if (str.equals("Texture")) {
            btm = BitmapFactory.decodeResource(getResources(), Constants.Imageid1[i]);
        } else if (str.equals("Color")) {
            String hex1 = hex;
            Bitmap image = Bitmap.createBitmap(HttpStatus.SC_MULTIPLE_CHOICES, HttpStatus.SC_MULTIPLE_CHOICES, Config.ARGB_8888);
            image.eraseColor(Color.parseColor("#" + hex1));
            btm = image;
        } else if (str.equals("Temp_Path") && FragmentBackImage.thumbnails.size() != 0) {
            btm = (Bitmap) FragmentBackImage.thumbnails.get(i);
        }
        for (int k = 0; k < 5; k++) {
            if (k == 0) {
                this.image1.setImageBitmap(cropInRatio(btm, 1, 1));
            } else if (k == 1) {
                this.image2.setImageBitmap(cropInRatio(btm, 16, 9));
            } else if (k == 2) {
                this.image3.setImageBitmap(cropInRatio(btm, 9, 16));
            } else if (k == 3) {
                this.image4.setImageBitmap(cropInRatio(btm, 4, 3));
            } else if (k == 4) {
                this.image5.setImageBitmap(cropInRatio(btm, 3, 4));
            }
        }
    }

    public Bitmap cropInRatio(Bitmap bitmap, int rx, int ry) {
        Bitmap bit = null;
        float Width = (float) bitmap.getWidth();
        float Height = (float) bitmap.getHeight();
        float newHeight = getnewHeight(rx, ry, Width, Height);
        float newWidth = getnewWidth(rx, ry, Width, Height);
        if (newWidth <= Width && newWidth < Width) {
            bit = Bitmap.createBitmap(bitmap, (int) ((Width - newWidth) / 2.0f), 0, (int) newWidth, (int) Height);
        }
        if (newHeight <= Height && newHeight < Height) {
            bit = Bitmap.createBitmap(bitmap, 0, (int) ((Height - newHeight) / 2.0f), (int) Width, (int) newHeight);
        }
        return (newWidth == Width && newHeight == Height) ? bitmap : bit;
    }

    private float getnewHeight(int i, int i1, float width, float height) {
        return (((float) i1) * width) / ((float) i);
    }

    private float getnewWidth(int i, int i1, float width, float height) {
        return (((float) i) * height) / ((float) i1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            this.fragmentbackgund.onActivityResult(requestCode, resultCode, data);
            this.fragmenttexture.onActivityResult(requestCode, resultCode, data);
        } catch (NullPointerException e) {
            e.printStackTrace();
        } catch (RuntimeException e2) {
            e2.printStackTrace();
        } catch (Exception e3) {
            e3.printStackTrace();
        }
        if (resultCode == -1) {
            if (data != null || requestCode == SELECT_PICTURE_FROM_CAMERA || requestCode == 4) {
                Intent customGalleryIntent;
                if (requestCode == SELECT_PICTURE_FROM_GALLERY) {
                    try {
                        bitmap = Constants.getBitmapFromUri(this, data.getData(), this.screenWidth, this.screenHeight);
                        bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                        customGalleryIntent = new Intent(this, CropActivityTwo.class);
                        customGalleryIntent.putExtra("value", "image");
                        startActivityForResult(customGalleryIntent, 4);
                    } catch (Exception e32) {
                        e32.printStackTrace();
                    }
                }
                if (requestCode == SELECT_PICTURE_FROM_CAMERA) {
                    try {
                        bitmap = Constants.getBitmapFromUri(this, Uri.fromFile(this.f21f), this.screenWidth, this.screenHeight);
                        bitmap = ImageUtils.resizeBitmap(bitmap, (int) this.screenWidth, (int) this.screenHeight);
                        customGalleryIntent = new Intent(this, CropActivityTwo.class);
                        customGalleryIntent.putExtra("value", "image");
                        startActivityForResult(customGalleryIntent, 4);
                    } catch (Exception e322) {
                        e322.printStackTrace();
                    }
                }
                if (requestCode == 4) {
                    Bundle bundle = new Bundle();
                    bundle.putString(Scopes.PROFILE, "no");
                    Intent returnIntent = new Intent();
                    returnIntent.putExtras(bundle);
                    setResult(-1, returnIntent);
                    finish();
                    return;
                }
                return;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(this, 16974126).setMessage(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.ok), new dialog_click()).create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
            alertDialog.show();
        }
    }
}
