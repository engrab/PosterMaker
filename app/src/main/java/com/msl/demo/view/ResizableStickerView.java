package com.msl.demo.view;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Color;
import android.graphics.PorterDuff.Mode;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;

import com.msl.demo.DatabaseHandler;
import com.msl.demo.listener.MultiTouchListener;
import com.msl.demo.listener.MultiTouchListener.TouchCallbackListener;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import ab.cd.ef.postermaker.R;

public class ResizableStickerView extends RelativeLayout implements TouchCallbackListener {
    private static final int SELF_SIZE_DP = 30;
    public static final String TAG = "ResizableStickerView";
    private int alphaProg = 0;
    double angle = 0.0d;
    int baseh;
    int basew;
    int basex;
    int basey;
    private ImageView border_iv;
    private Bitmap btmp = null;
    float cX = 0.0f;
    float cY = 0.0f;
    private double centerX;
    private double centerY;
    private String colorType = "colored";
    private Context context;
    double dAngle = 0.0d;
    private ImageView delete_iv;
    private String drawableId;
    private int f22s;
    private String field_four = "";
    private int field_one = 0;
    private String field_three = "";
    private String field_two = "0,0";
    private ImageView flip_iv;
    private int he;
    private int hueProg = 1;
    private int imgAlpha = 100;
    private int imgColor = 0;
    private boolean isBorderVisible = false;
    private boolean isColorFilterEnable = false;
    private boolean isSticker = true;
    private boolean isStrickerEditEnable = false;
    private int leftMargin = 0;
    private TouchEventListener listener = null;
    private OnTouchListener mTouchListener = new C06005();
    private OnTouchListener mTouchListener1 = new C06027();
    public ImageView main_iv;
    int margl;
    int margt;
    private OnTouchListener rTouchListener = new C06016();
    private Uri resUri = null;
    private ImageView rotate_iv;
    private float rotation;
    Animation scale;
    private int scaleRotateProg = 0;
    private ImageView scale_iv;
    private double scale_orgHeight = -1.0d;
    private double scale_orgWidth = -1.0d;
    private float scale_orgX = -1.0f;
    private float scale_orgY = -1.0f;
    int screenHeight = HttpStatus.SC_MULTIPLE_CHOICES;
    int screenWidth = HttpStatus.SC_MULTIPLE_CHOICES;
    private String stkr_path = "";
    double tAngle = 0.0d;
    private float this_orgX = -1.0f;
    private float this_orgY = -1.0f;
    private int topMargin = 0;
    double vAngle = 0.0d;
    private int wi;
    private int xRotateProg = 0;
    private int yRotateProg = 0;
    private float yRotation;
    private int zRotateProg = 0;
    Animation zoomInScale;
    Animation zoomOutScale;
    public boolean isMultiTouchEnabled = true;

    class C05951 implements OnClickListener {
        C05951() {
        }

        public void onClick(View v) {
            float f = -180.0f;
            ImageView imageView = ResizableStickerView.this.main_iv;
            if (ResizableStickerView.this.main_iv.getRotationY() == -180.0f) {
                f = 0.0f;
            }
            imageView.setRotationY(f);
            ResizableStickerView.this.main_iv.invalidate();
            ResizableStickerView.this.requestLayout();
        }
    }

    class C05972 implements OnClickListener {
        C05972() {
        }

        public void onClick(View v) {
            final ViewGroup parent = (ViewGroup) ResizableStickerView.this.getParent();
            ResizableStickerView.this.zoomInScale.setAnimationListener(new AnimationListener() {
                public void onAnimationStart(Animation animation) {
                }

                public void onAnimationEnd(Animation animation) {
                    parent.removeView(ResizableStickerView.this);
                }

                public void onAnimationRepeat(Animation animation) {
                }
            });
            ResizableStickerView.this.main_iv.startAnimation(ResizableStickerView.this.zoomInScale);
            ResizableStickerView.this.setBorderVisibility(false);
            if (ResizableStickerView.this.listener != null) {
                ResizableStickerView.this.listener.onDelete();
            }
        }
    }

    class C05994 implements OnDismissListener {
        C05994() {
        }

        public void onDismiss(DialogInterface dialog) {
        }
    }

    class C06005 implements OnTouchListener {
        C06005() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    ResizableStickerView.this.this_orgX = ResizableStickerView.this.getX();
                    ResizableStickerView.this.this_orgY = ResizableStickerView.this.getY();
                    ResizableStickerView.this.scale_orgX = event.getRawX();
                    ResizableStickerView.this.scale_orgY = event.getRawY();
                    ResizableStickerView.this.scale_orgWidth = (double) ResizableStickerView.this.getLayoutParams().width;
                    ResizableStickerView.this.scale_orgHeight = (double) ResizableStickerView.this.getLayoutParams().height;
                    ResizableStickerView.this.centerX = (double) ((((View) ResizableStickerView.this.getParent()).getX() + ResizableStickerView.this.getX()) + (((float) ResizableStickerView.this.getWidth()) / 2.0f));
                    int result = 0;
                    int resourceId = ResizableStickerView.this.getResources().getIdentifier("status_bar_height", "dimen", "android");
                    if (resourceId > 0) {
                        result = ResizableStickerView.this.getResources().getDimensionPixelSize(resourceId);
                    }
                    ResizableStickerView.this.centerY = (((double) (((View) ResizableStickerView.this.getParent()).getY() + ResizableStickerView.this.getY())) + ((double) result)) + ((double) (((float) ResizableStickerView.this.getHeight()) / 2.0f));
                    break;
                case 1:
                    ResizableStickerView.this.wi = ResizableStickerView.this.getLayoutParams().width;
                    ResizableStickerView.this.he = ResizableStickerView.this.getLayoutParams().height;
                    break;
                case 2:
                    double angle_diff = (Math.abs(Math.atan2((double) (event.getRawY() - ResizableStickerView.this.scale_orgY), (double) (event.getRawX() - ResizableStickerView.this.scale_orgX)) - Math.atan2(((double) ResizableStickerView.this.scale_orgY) - ResizableStickerView.this.centerY, ((double) ResizableStickerView.this.scale_orgX) - ResizableStickerView.this.centerX)) * 180.0d) / 3.141592653589793d;
                    Log.v(ResizableStickerView.TAG, "angle_diff: " + angle_diff);
                    double length1 = ResizableStickerView.this.getLength(ResizableStickerView.this.centerX, ResizableStickerView.this.centerY, (double) ResizableStickerView.this.scale_orgX, (double) ResizableStickerView.this.scale_orgY);
                    double length2 = ResizableStickerView.this.getLength(ResizableStickerView.this.centerX, ResizableStickerView.this.centerY, (double) event.getRawX(), (double) event.getRawY());
                    int size = ResizableStickerView.this.dpToPx(ResizableStickerView.this.getContext(), 30);
                    double offset;
                    LayoutParams layoutParams;
                    if (length2 > length1 && (angle_diff < 25.0d || Math.abs(angle_diff - 180.0d) < 25.0d)) {
                        offset = (double) Math.round(Math.max((double) Math.abs(event.getRawX() - ResizableStickerView.this.scale_orgX), (double) Math.abs(event.getRawY() - ResizableStickerView.this.scale_orgY)));
                        layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        layoutParams.width = (int) (((double) layoutParams.width) + offset);
                        layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        layoutParams.height = (int) (((double) layoutParams.height) + offset);
                    } else if (length2 < length1 && ((angle_diff < 25.0d || Math.abs(angle_diff - 180.0d) < 25.0d) && ResizableStickerView.this.getLayoutParams().width > size / 2 && ResizableStickerView.this.getLayoutParams().height > size / 2)) {
                        offset = (double) Math.round(Math.max((double) Math.abs(event.getRawX() - ResizableStickerView.this.scale_orgX), (double) Math.abs(event.getRawY() - ResizableStickerView.this.scale_orgY)));
                        layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        layoutParams.width = (int) (((double) layoutParams.width) - offset);
                        layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
                        layoutParams.height = (int) (((double) layoutParams.height) - offset);
                    }
                    ResizableStickerView.this.scale_orgX = event.getRawX();
                    ResizableStickerView.this.scale_orgY = event.getRawY();
                    ResizableStickerView.this.postInvalidate();
                    ResizableStickerView.this.requestLayout();
                    break;
            }
            return true;
        }
    }

    class C06016 implements OnTouchListener {
        C06016() {
        }

        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case 0:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateDown(view);
                    }
                    Rect rect = new Rect();
                    ((View) view.getParent()).getGlobalVisibleRect(rect);
                    ResizableStickerView.this.cX = rect.exactCenterX();
                    ResizableStickerView.this.cY = rect.exactCenterY();
                    ResizableStickerView.this.vAngle = (double) ((View) view.getParent()).getRotation();
                    ResizableStickerView.this.tAngle = (Math.atan2((double) (ResizableStickerView.this.cY - event.getRawY()), (double) (ResizableStickerView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ResizableStickerView.this.dAngle = ResizableStickerView.this.vAngle - ResizableStickerView.this.tAngle;
                    break;
                case 1:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateUp(view);
                        break;
                    }
                    break;
                case 2:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onRotateMove(view);
                    }
                    ResizableStickerView.this.angle = (Math.atan2((double) (ResizableStickerView.this.cY - event.getRawY()), (double) (ResizableStickerView.this.cX - event.getRawX())) * 180.0d) / 3.141592653589793d;
                    ((View) view.getParent()).setRotation((float) (ResizableStickerView.this.angle + ResizableStickerView.this.dAngle));
                    ((View) view.getParent()).invalidate();
                    ((View) view.getParent()).requestLayout();
                    break;
            }
            return true;
        }
    }

    class C06027 implements OnTouchListener {
        C06027() {
        }

        @SuppressLint({"NewApi"})
        public boolean onTouch(View view, MotionEvent event) {
            int j = (int) event.getRawX();
            int i = (int) event.getRawY();
            LayoutParams layoutParams = (LayoutParams) ResizableStickerView.this.getLayoutParams();
            switch (event.getAction()) {
                case 0:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleDown(view);
                    }
                    ResizableStickerView.this.invalidate();
                    ResizableStickerView.this.basex = j;
                    ResizableStickerView.this.basey = i;
                    ResizableStickerView.this.basew = ResizableStickerView.this.getWidth();
                    ResizableStickerView.this.baseh = ResizableStickerView.this.getHeight();
                    ResizableStickerView.this.getLocationOnScreen(new int[2]);
                    ResizableStickerView.this.margl = layoutParams.leftMargin;
                    ResizableStickerView.this.margt = layoutParams.topMargin;
                    break;
                case 1:
                    ResizableStickerView.this.wi = ResizableStickerView.this.getLayoutParams().width;
                    ResizableStickerView.this.he = ResizableStickerView.this.getLayoutParams().height;
                    ResizableStickerView.this.leftMargin = ((LayoutParams) ResizableStickerView.this.getLayoutParams()).leftMargin;
                    ResizableStickerView.this.topMargin = ((LayoutParams) ResizableStickerView.this.getLayoutParams()).topMargin;
                    ResizableStickerView.this.field_two = String.valueOf(ResizableStickerView.this.leftMargin) + "," + String.valueOf(ResizableStickerView.this.topMargin);
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleUp(view);
                        break;
                    }
                    break;
                case 2:
                    if (ResizableStickerView.this.listener != null) {
                        ResizableStickerView.this.listener.onScaleMove(view);
                    }
                    float f2 = (float) Math.toDegrees(Math.atan2((double) (i - ResizableStickerView.this.basey), (double) (j - ResizableStickerView.this.basex)));
                    float f1 = f2;
                    if (f2 < 0.0f) {
                        f1 = f2 + 360.0f;
                    }
                    j -= ResizableStickerView.this.basex;
                    int k = i - ResizableStickerView.this.basey;
                    i = (int) (Math.sqrt((double) ((j * j) + (k * k))) * Math.cos(Math.toRadians((double) (f1 - ResizableStickerView.this.getRotation()))));
                    j = (int) (Math.sqrt((double) ((i * i) + (k * k))) * Math.sin(Math.toRadians((double) (f1 - ResizableStickerView.this.getRotation()))));
                    k = (i * 2) + ResizableStickerView.this.basew;
                    int m = (j * 2) + ResizableStickerView.this.baseh;
                    if (k > ResizableStickerView.this.f22s) {
                        layoutParams.width = k;
                        layoutParams.leftMargin = ResizableStickerView.this.margl - i;
                    }
                    if (m > ResizableStickerView.this.f22s) {
                        layoutParams.height = m;
                        layoutParams.topMargin = ResizableStickerView.this.margt - j;
                    }
                    ResizableStickerView.this.setLayoutParams(layoutParams);
                    ResizableStickerView.this.performLongClick();
                    break;
            }
            return true;
        }
    }

    public interface TouchEventListener {
        void onDelete();

        void onEdit(View view, Uri uri);

        void onRotateDown(View view);

        void onRotateMove(View view);

        void onRotateUp(View view);

        void onScaleDown(View view);

        void onScaleMove(View view);

        void onScaleUp(View view);

        void onTouchDown(View view);

        void onTouchMove(View view);

        void onTouchUp(View view);
    }

    public ResizableStickerView setOnTouchCallbackListener(TouchEventListener l) {
        this.listener = l;
        return this;
    }

    public ResizableStickerView(Context context) {
        super(context);
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ResizableStickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context ctx) {
        this.context = ctx;
        this.main_iv = new ImageView(this.context);
        this.scale_iv = new ImageView(this.context);
        this.border_iv = new ImageView(this.context);
        this.flip_iv = new ImageView(this.context);
        this.rotate_iv = new ImageView(this.context);
        this.delete_iv = new ImageView(this.context);
        this.f22s = dpToPx(this.context, 25);
        this.wi = dpToPx(this.context, 200);
        this.he = dpToPx(this.context, 200);
        this.scale_iv.setImageResource(R.drawable.sticker_scale);
        this.border_iv.setImageResource(R.drawable.sticker_border_gray);
        this.flip_iv.setImageResource(R.drawable.sticker_flip);
        this.rotate_iv.setImageResource(R.drawable.rotate);
        this.delete_iv.setImageResource(R.drawable.sticker_delete1);
        LayoutParams lp = new LayoutParams(this.wi, this.he);
        LayoutParams mlp = new LayoutParams(-1, -1);
        mlp.setMargins(5, 5, 5, 5);
        mlp.addRule(17);
        LayoutParams slp = new LayoutParams(this.f22s, this.f22s);
        slp.addRule(12);
        slp.addRule(11);
        slp.setMargins(5, 5, 5, 5);
        LayoutParams flp = new LayoutParams(this.f22s, this.f22s);
        flp.addRule(10);
        flp.addRule(11);
        flp.setMargins(5, 5, 5, 5);
        LayoutParams elp = new LayoutParams(this.f22s, this.f22s);
        elp.addRule(12);
        elp.addRule(9);
        elp.setMargins(5, 5, 5, 5);
        LayoutParams dlp = new LayoutParams(this.f22s, this.f22s);
        dlp.addRule(10);
        dlp.addRule(9);
        dlp.setMargins(5, 5, 5, 5);
        LayoutParams blp = new LayoutParams(-1, -1);
        setLayoutParams(lp);
        addView(this.border_iv);
        this.border_iv.setLayoutParams(blp);
        this.border_iv.setScaleType(ScaleType.FIT_XY);
        this.border_iv.setTag("border_iv");
        addView(this.main_iv);
        this.main_iv.setLayoutParams(mlp);
        this.main_iv.setTag("main_iv");
        addView(this.flip_iv);
        this.flip_iv.setLayoutParams(flp);
        this.flip_iv.setOnClickListener(new C05951());
        addView(this.rotate_iv);
        this.rotate_iv.setLayoutParams(elp);
        this.rotate_iv.setOnTouchListener(this.rTouchListener);
        addView(this.delete_iv);
        this.delete_iv.setLayoutParams(dlp);
        this.delete_iv.setOnClickListener(new C05972());
        addView(this.scale_iv);
        this.scale_iv.setLayoutParams(slp);
        this.scale_iv.setOnTouchListener(this.mTouchListener1);
        this.scale_iv.setTag("scale_iv");
        this.rotation = getRotation();
        this.scale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_anim);
        this.zoomOutScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_out);
        this.zoomInScale = AnimationUtils.loadAnimation(getContext(), R.anim.sticker_scale_zoom_in);
        this.isMultiTouchEnabled = setDefaultTouchListener(true);
    }

    public boolean setDefaultTouchListener(boolean z) {
        if (z) {
            this.field_three = "UNLOCKED";
            setOnTouchListener(new MultiTouchListener().enableRotation(true).setOnTouchCallbackListener(this));
            return true;
        }
        this.field_three = "LOCKED";
        setOnTouchListener(null);
        return false;
    }

    public void setBorderVisibility(boolean ch) {
        this.isBorderVisible = ch;
        if (!ch) {
            this.border_iv.setVisibility(View.GONE);
            this.scale_iv.setVisibility(View.GONE);
            this.flip_iv.setVisibility(View.GONE);
            this.rotate_iv.setVisibility(View.GONE);
            this.delete_iv.setVisibility(View.GONE);
            setBackgroundResource(0);
            if (this.isColorFilterEnable) {
                this.main_iv.setColorFilter(Color.parseColor("#303828"));
            }
        } else if (this.border_iv.getVisibility() != 0) {
            this.border_iv.setVisibility(View.VISIBLE);
            this.scale_iv.setVisibility(View.VISIBLE);
            this.flip_iv.setVisibility(View.VISIBLE);
            this.rotate_iv.setVisibility(View.VISIBLE);
            this.delete_iv.setVisibility(View.VISIBLE);
            setBackgroundResource(R.drawable.textlib_border_gray);
            this.main_iv.startAnimation(this.scale);
        }
    }

    public boolean getBorderVisbilty() {
        return this.isBorderVisible;
    }

    public void opecitySticker(int process) {
        try {
            this.main_iv.setAlpha(((float) process) / 100.0f);
            this.imgAlpha = process;
        } catch (Exception e) {
        }
    }

    public int getHueProg() {
        return this.hueProg;
    }

    public void setHueProg(int hueProg) {
    }

    public void setHueProg_1(int hueProg) {
        this.main_iv.setColorFilter(hueProg, Mode.SRC_ATOP);
    }

    public String getColorType() {
        return this.colorType;
    }

    public void setColorType(String colorType) {
        this.colorType = colorType;
    }

    public int getAlphaProg() {
        return this.imgAlpha;
    }

    public void setAlphaProg(int alphaProg) {
        this.alphaProg = alphaProg;
        opecitySticker(alphaProg);
    }

    public int getColor() {
        return this.imgColor;
    }

    public void setColor(int color) {
        try {
            this.main_iv.setColorFilter(color);
            this.imgColor = color;
        } catch (Exception e) {
        }
    }

    public void setBgDrawable(String redId) {
        this.main_iv.setImageResource(getResources().getIdentifier(redId, "drawable", this.context.getPackageName()));
        this.drawableId = redId;
        this.main_iv.startAnimation(this.zoomOutScale);
    }

    public void setStrPath(String stkr_path1) {
        try {
            this.main_iv.setImageBitmap(ImageUtils.getResampleImageBitmap(Uri.parse(stkr_path1), this.context, this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight));
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.stkr_path = stkr_path1;
        this.main_iv.startAnimation(this.zoomOutScale);
    }

    public void setMainImageUri(Uri uri) {
        this.resUri = uri;
        this.main_iv.setImageURI(this.resUri);
    }

    public Uri getMainImageUri() {
        return this.resUri;
    }

    public void setMainImageBitmap(Bitmap bit) {
        this.main_iv.setImageBitmap(bit);
    }

    public Bitmap getMainImageBitmap() {
        return this.btmp;
    }

    public void setComponentInfo(ComponentInfo ci) {
        this.wi = ci.getWIDTH();
        this.he = ci.getHEIGHT();
        this.drawableId = ci.getRES_ID();
        this.resUri = ci.getRES_URI();
        this.btmp = ci.getBITMAP();
        this.rotation = ci.getROTATION();
        this.imgColor = ci.getSTC_COLOR();
        this.yRotation = ci.getY_ROTATION();
        this.imgAlpha = ci.getSTC_OPACITY();
        this.stkr_path = ci.getSTKR_PATH();
        this.colorType = ci.getCOLORTYPE();
        this.hueProg = ci.getSTC_HUE();
        this.field_two = ci.getFIELD_TWO();
        if (!this.stkr_path.equals("")) {
            setStrPath(this.stkr_path);
        } else if (this.drawableId.equals("")) {
            this.main_iv.setImageBitmap(this.btmp);
        } else {
            setBgDrawable(this.drawableId);
        }
        if (this.colorType.equals("white")) {
            setColor(this.imgColor);
        } else {
            setHueProg(this.hueProg);
        }
        setRotation(this.rotation);
        opecitySticker(this.imgAlpha);
        if (this.field_two.equals("")) {
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(ci.getPOS_X());
            setY(ci.getPOS_Y());
        } else {
            String[] parts = this.field_two.split(",");
            int leftMergin = Integer.parseInt(parts[0]);
            int topMergin = Integer.parseInt(parts[1]);
            ((LayoutParams) getLayoutParams()).leftMargin = leftMergin;
            ((LayoutParams) getLayoutParams()).topMargin = topMergin;
            getLayoutParams().width = this.wi;
            getLayoutParams().height = this.he;
            setX(ci.getPOS_X() + ((float) (leftMergin * -1)));
            setY(ci.getPOS_Y() + ((float) (topMergin * -1)));
        }
        if (ci.getTYPE() == "SHAPE") {
            this.flip_iv.setVisibility(View.GONE);
            this.isSticker = false;
        }
        if (ci.getTYPE() == "STICKER") {
            this.flip_iv.setVisibility(View.VISIBLE);
            this.isSticker = true;
        }

        this.isMultiTouchEnabled = setDefaultTouchListener(true);

/*
        if ("LOCKED".equals(this.field_three)) {
            this.isMultiTouchEnabled = setDefaultTouchListener(false);
        } else {
            this.isMultiTouchEnabled = setDefaultTouchListener(true);
        }
*/
        this.main_iv.setRotationY(this.yRotation);
    }

    public void optimize(float wr, float hr) {
        setX(getX() * wr);
        setY(getY() * hr);
        getLayoutParams().width = (int) (((float) this.wi) * wr);
        getLayoutParams().height = (int) (((float) this.he) * hr);
    }

    public void optimizeScreen(float wr, float hr) {
        this.screenHeight = (int) hr;
        this.screenWidth = (int) wr;
    }

    public void incrX() {
        setX(getX() + 1.0f);
    }

    public void decX() {
        setX(getX() - 1.0f);
    }

    public void incrY() {
        setY(getY() + 1.0f);
    }

    public void decY() {
        setY(getY() - 1.0f);
    }

    public ComponentInfo getComponentInfo() {
        if (this.btmp != null) {
            this.stkr_path = saveBitmapObject1(this.btmp);
        }
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X(getX());
        ci.setPOS_Y(getY());
        ci.setWIDTH(this.wi);
        ci.setHEIGHT(this.he);
        ci.setRES_ID(this.drawableId);
        ci.setSTC_COLOR(this.imgColor);
        ci.setRES_URI(this.resUri);
        ci.setSTC_OPACITY(this.imgAlpha);
        ci.setCOLORTYPE(this.colorType);
        ci.setBITMAP(this.btmp);
        ci.setROTATION(getRotation());
        ci.setY_ROTATION(this.main_iv.getRotationY());
        ci.setXRotateProg(this.xRotateProg);
        ci.setYRotateProg(this.yRotateProg);
        ci.setZRotateProg(this.zRotateProg);
        ci.setScaleProg(this.scaleRotateProg);
        ci.setSTKR_PATH(this.stkr_path);
        ci.setSTC_HUE(this.hueProg);
        ci.setFIELD_ONE(this.field_one);
        ci.setFIELD_TWO(this.field_two);
        ci.setFIELD_THREE(this.field_three);
        ci.setFIELD_FOUR(this.field_four);
        return ci;
    }

    private void saveStkrBitmap(final Bitmap btmp) {
        final ProgressDialog ringProgressDialog = ProgressDialog.show(this.context, "", "", true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                DatabaseHandler dh = null;
                try {
                    ResizableStickerView.this.stkr_path = ResizableStickerView.this.saveBitmapObject1(btmp);
                    if (dh != null) {
                        dh.close();
                    }
                } catch (Exception e) {
                    Log.i("testing", "Exception " + e.getMessage());
                    e.printStackTrace();
                    if (dh != null) {
                        dh.close();
                    }
                } catch (Throwable th) {
                    if (dh != null) {
                        dh.close();
                    }
                }
                ringProgressDialog.dismiss();
            }
        }).start();
        ringProgressDialog.setOnDismissListener(new C05994());
    }

    private String saveBitmapObject1(Bitmap bitmap) {
        String temp_path = "";
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), ".Poster Maker Stickers/category1");
        myDir.mkdirs();
        File file1 = new File(myDir, "raw1-" + System.currentTimeMillis() + ".png");
        temp_path = file1.getAbsolutePath();
        if (file1.exists()) {
            file1.delete();
        }
        try {
            FileOutputStream ostream = new FileOutputStream(file1);
            bitmap.compress(CompressFormat.PNG, 100, ostream);
            ostream.close();
            return temp_path;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return "";
        }
    }

    public int dpToPx(Context c, int dp) {
        float f = (float) dp;
        c.getResources();
        return (int) (Resources.getSystem().getDisplayMetrics().density * f);
    }

    private double getLength(double x1, double y1, double x2, double y2) {
        return Math.sqrt(Math.pow(y2 - y1, 2.0d) + Math.pow(x2 - x1, 2.0d));
    }

    public void enableColorFilter(boolean b) {
        this.isColorFilterEnable = b;
    }

    public void onTouchCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchDown(v);
        }
    }

    public void onTouchUpCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchUp(v);
        }
    }

    public void onTouchMoveCallback(View v) {
        if (this.listener != null) {
            this.listener.onTouchMove(v);
        }
    }
}
