package com.amt.postermaker.graphicdesign.flyer.bannermaker.create;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.GestureDetector;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBar;
import com.h6ah4i.android.widget.verticalseekbar.VerticalSeekBarWrapper;
import com.shiftcolorpicker.src.main.java.uz.shift.colorpicker.LineColorPicker;

import java.io.File;
import java.util.ArrayList;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter.StickerViewPagerAdapter;


public class CreateFrameActivity extends Activity {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    protected static final String TAG = "ShiftPicker";
    private static final int TEXT_ACTIVITY = 9082;
    private static final int TYPE_SHAPE = 9062;
    private static final int TYPE_STICKER = 9072;
    public static Bitmap bitmapOriginal;
    public static Activity f6c;
    public static boolean isUpdated = false;
    static String mDrawableName = "";
    private final int OPEN_CUSTOM_ACITIVITY = 4;
    private StickerViewPagerAdapter _adapter;
    private ViewPager _mViewPager;
    int asuncCount = 0;
    private int backcolor = -1;
    private int bgColor = 0;
    private int[] bgId = new int[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12, R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16, R.drawable.b17, R.drawable.b18, R.drawable.b19, R.drawable.b20, R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25, R.drawable.b26, R.drawable.b27, R.drawable.b28, R.drawable.b29, R.drawable.b30, R.drawable.b31, R.drawable.b32, R.drawable.b33, R.drawable.b34, R.drawable.b35, R.drawable.b36, R.drawable.b37, R.drawable.b38, R.drawable.b39, R.drawable.b40, R.drawable.b41, R.drawable.b42, R.drawable.b43, R.drawable.b44, R.drawable.b45, R.drawable.b46, R.drawable.b47, R.drawable.b48, R.drawable.b49, R.drawable.b50, R.drawable.b51, R.drawable.b52, R.drawable.b53, R.drawable.b54, R.drawable.b55, R.drawable.b56, R.drawable.b57, R.drawable.b58, R.drawable.b59, R.drawable.b60};
    private RelativeLayout bg_container;
    private RelativeLayout bg_list_container;
    private Bitmap bitmap;
    LinearLayout bottom;
    Button btn_copy;
    private ImageButton btn_up_down;
    private RelativeLayout camgal_container;
    private RelativeLayout color_list_container;
    private int curTileId = 0;
    private HorizontalScrollView decorative_shape;
    private boolean editMode = false;
    Editor editor;
    private File f7f = null;
    private HorizontalScrollView flower_shape;
    private View focusedView;
    String frame_Name;
    private ImageView frame_img;
    GestureDetector gd = null;
    private ImageView guideline;
    private int height;
    private LineColorPicker horizontalPicker;
    ImageSdAdapter imageListAdapter;
    private RelativeLayout image_list_container;
    HorizontalListView images_listview;
    RelativeLayout lay_sticker;
    private HorizontalScrollView leaves_shape;
    private RelativeLayout main_rel;
    private Bitmap mask;
    private Bitmap newBit;
    String[] pallete = new String[]{"#4182b6", "#4149b6", "#7641b6", "#b741a7", "#c54657", "#d1694a", "#24352a", "#b8c847", "#67bb43", "#41b691", "#293b2f", "#1c0101", "#420a09", "#b4794b", "#4b86b4", "#93b6d2", "#72aa52", "#67aa59", "#fa7916", "#16a1fa", "#165efa", "#1697fa"};
    String[] pallete1 = new String[]{"#b8c847", "#67bb43", "#41b691", "#4182b6", "#4149b6", "#7641b6", "#b741a7", "#c54657", "#d1694a", "#26ffd5", "#7b5b37", "#e7ae95", "#874848", "#987064", "#daa18f", "#deb289", "#e24d4e", "#768282", "#333c6f", "#c9b82b", "#1de592", "#156526", "#fedd31", "#f1ec12", "#806d88", "#055c5a", "#012153", "#e1e3ea", "#012153", "#00abff", "#b2c9ab", "#6fae93"};
    private ProgressDialog pdia;
    SharedPreferences prefs;
    private int processs;
    String profile_type;
    private ImageButton ratio;
    String ratioStr = "1:1";
    private RelativeLayout ratio_container;
    private RelativeLayout rel;
    private RelativeLayout res_container;
    private Animation scaleAnim;
    private VerticalSeekBarWrapper seekBarContainer;
    private int seekValue = 90;
    private RelativeLayout shape_container;
    private RelativeLayout shape_rel;
    int size_list;
    private Animation slideDown;
    private Animation slideUp;
    ArrayList<String> stkrNameList;
    private RelativeLayout tabbasic;
    private RelativeLayout tabbg;
    private RelativeLayout tabcamgal;
    private RelativeLayout tabcolor;
    private RelativeLayout tabdecorative;
    private RelativeLayout tabflower;
    private RelativeLayout tabimages;
    private RelativeLayout tableaves;
    PagerSlidingTabStrip tabs;
    private RelativeLayout tabtexture;
    String temp_path = "";
    private int[] textureId = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t9, R.drawable.t10, R.drawable.t11, R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20, R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30, R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40, R.drawable.t41, R.drawable.t42, R.drawable.t43, R.drawable.t44, R.drawable.t45, R.drawable.t46, R.drawable.t47, R.drawable.t48, R.drawable.t49, R.drawable.t50, R.drawable.t51, R.drawable.t52, R.drawable.t53, R.drawable.t54, R.drawable.t55, R.drawable.t56, R.drawable.t57, R.drawable.t58, R.drawable.t59, R.drawable.t60};
    private RelativeLayout texture_list_container;
    ArrayList<Bitmap> thumbnails = new ArrayList();
    private VerticalSeekBar tile_seekbar;
    private Typeface ttf;
    private RelativeLayout txt_stkr_rel;
    ArrayList<String> uri = new ArrayList();
    public SeekBar verticalSeekBar = null;
    private int width;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(1);
        getWindow().setFlags(1024, 1024);
        getWindow().setFlags(131072, 131072);
        setContentView(R.layout.activity_create_frame);
    }
}
