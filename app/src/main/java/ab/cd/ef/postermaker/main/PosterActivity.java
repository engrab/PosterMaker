package ab.cd.ef.postermaker.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.preference.PreferenceManager;

import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.google.android.gms.common.Scopes;
import com.msl.demo.view.ComponentInfo;
import com.msl.demo.view.ResizableStickerView;
import com.msl.demo.view.ResizableStickerView.TouchEventListener;
import com.msl.textmodule.AutofitTextRel;
import com.msl.textmodule.HorizontalListView;
import com.msl.textmodule.ImageViewAdapter;
import com.msl.textmodule.TextActivity;
import com.msl.textmodule.TextInfo;
import com.shiftcolorpicker.src.main.java.uz.shift.colorpicker.LineColorPicker;
import com.shiftcolorpicker.src.main.java.uz.shift.colorpicker.OnColorChangedListener;

import org.apache.http.HttpStatus;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.adapter.AssetsGridMain;
import ab.cd.ef.postermaker.adapter.StickerViewPagerAdapter;
import ab.cd.ef.postermaker.create.BitmapDataObject;
import ab.cd.ef.postermaker.create.BlurOperationAsync;
import ab.cd.ef.postermaker.create.DatabaseHandler;
import ab.cd.ef.postermaker.create.RepeatListener;
import ab.cd.ef.postermaker.create.TemplateInfo;
import ab.cd.ef.postermaker.sticker_fragment.GetSnapListener;
import ab.cd.ef.postermaker.utility.GPUImageFilterTools.FilterAdjuster;
import ab.cd.ef.postermaker.utility.ImageUtils;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class PosterActivity extends FragmentActivity implements OnClickListener, OnSeekBarChangeListener, TouchEventListener, AutofitTextRel.TouchEventListener, GetSnapListener, OnSetImageSticker, GetColorListener {
    private static final int OPEN_CUSTOM_ACITIVITY = 4;
    private static final int SELECT_PICTURE_FROM_CAMERA = 905;
    private static final int SELECT_PICTURE_FROM_GALLERY = 907;
    private static final int TEXT_ACTIVITY = 908;
    private static final int TYPE_SHAPE = 9062;
    private static final int TYPE_STICKER = 9072;
    public static PosterActivity activity;
    public static Bitmap btmSticker;
    public static Activity f24c;
    public static Bitmap imgBtmap;
    public static boolean isUpadted = false;
    public static boolean isUpdated = false;
    public static Bitmap withoutWatermark;
    boolean OneShow = true;
    private ViewPager _mViewPager;
    AssetsGridMain adapter;
    private RelativeLayout add_sticker;
    private RelativeLayout add_text;
    int alpha = 80;
    private SeekBar alphaSeekbar;
    private Animation animSlideDown;
    private Animation animSlideUp;
    ImageView background_blur;
    ImageView background_img;
    int bgAlpha = 0;
    int bgColor = 0;
    String bgDrawable = "0";
    LinearLayout bgShow;
    private Bitmap bitmap;
    ImageButton btn_bck1;
    private RelativeLayout center_rel;
    boolean checkMemory;
    LinearLayout colorShow;
    String color_Type;
    LinearLayout controlsShow;
    LinearLayout controlsShowStkr;
    private int curTileId = 0;
    String dev_name = "Photo+Cool+Apps";
    ProgressDialog dialogIs;
    boolean dialogShow = true;
    float distance;
    int distanceScroll;
    int dsfc;
    private boolean editMode = false;
    Editor editor;
    private File f25f = null;
    private String filename;
    private View focusedView;
    String fontName = "";
    LinearLayout fontsShow;
    String frame_Name = "";
    ImageView guideline;
    String hex;
    private LineColorPicker horizontalPicker;
    private LineColorPicker horizontalPickerColor;
    private float hr = 1.0f;
    private SeekBar hueSeekbar;
    int[] imageId = new int[]{R.drawable.btxt0, R.drawable.btxt1, R.drawable.btxt2, R.drawable.btxt3, R.drawable.btxt4, R.drawable.btxt5, R.drawable.btxt6, R.drawable.btxt7, R.drawable.btxt8, R.drawable.btxt9, R.drawable.btxt10, R.drawable.btxt11, R.drawable.btxt12, R.drawable.btxt13, R.drawable.btxt14, R.drawable.btxt15, R.drawable.btxt16, R.drawable.btxt17, R.drawable.btxt18, R.drawable.btxt19, R.drawable.btxt20, R.drawable.btxt21, R.drawable.btxt22, R.drawable.btxt23, R.drawable.btxt24};
    Button img_oK;
    private View[] layArr = new View[5];
    RelativeLayout lay_color;
    LinearLayout lay_colorOacity;
    RelativeLayout lay_colorOpacity;
    RelativeLayout lay_controlStkr;
    LinearLayout lay_dupliStkr;
    LinearLayout lay_dupliText;
    LinearLayout lay_edit;
    private LinearLayout lay_effects;
    RelativeLayout lay_filter;
    RelativeLayout lay_handletails;
    RelativeLayout lay_hue;
    ScrollView lay_scroll;
    RelativeLayout lay_sticker;
    private LinearLayout lay_textEdit;
    private RelativeLayout lay_touchremove;
    private LinearLayout logo_ll;
    private RelativeLayout main_rel;
    private int min = 0;
    Options options = new Options();
    String overlay_Name = "";
    int overlay_blur;
    int overlay_opacty;
    String[] pallete = new String[]{"#ffffff", "#cccccc", "#999999", "#666666", "#333333", "#000000", "#ffee90", "#ffd700", "#daa520", "#b8860b", "#ccff66", "#adff2f", "#00fa9a", "#00ff7f", "#00ff00", "#32cd32", "#3cb371", "#99cccc", "#66cccc", "#339999", "#669999", "#006666", "#336666", "#ffcccc", "#ff9999", "#ff6666", "#ff3333", "#ff0033", "#cc0033"};
    float parentY;
    private LineColorPicker pickerBg;
    String position;
    SharedPreferences preferences;
    SharedPreferences prefs;
    private int processs;
    String profile;
    String ratio;
    RelativeLayout rellative;
    float rotation = 0.0f;
    LinearLayout sadowShow;
    float screenHeight;
    float screenWidth;
    private SeekBar seek;
    private SeekBar seekBar3;
    private SeekBar seekBar_shadow;
    private int seekValue = 90;
    private SeekBar seek_blur;
    private SeekBar seek_tailys;
    private LinearLayout seekbar_container;
    private RelativeLayout select_backgnd;
    private RelativeLayout select_effect;
    int shadowColor = ViewCompat.MEASURED_STATE_MASK;
    private LineColorPicker shadowPickerColor;
    int shadowProg = 0;
    RelativeLayout shape_rel;
    boolean showtailsSeek = false;
    int sizeFull = 0;
    int stkrColorSet = Color.parseColor("#ffffff");
    int tAlpha = 100;
    int tColor = -1;
    PagerSlidingTabStrip tabs;
    String temp_Type = "";
    String temp_path = "";
    private List<TemplateInfo> templateList = new ArrayList();
    int template_id;
    int textColorSet = Color.parseColor("#ffffff");
    boolean touchChange = false;
    ImageView trans_img;
    private Typeface ttf;
    private Typeface ttfHeader;
    HashMap<Integer, Object> txtShapeList;
    private RelativeLayout txt_stkr_rel;
    ArrayList<String> uriArry = new ArrayList();
    private RelativeLayout user_image;
    SeekBar verticalSeekBar = null;
    private float wr = 1.0f;
    public ListFragment listFragment;

    class main_layout_runnable implements Runnable {

        class layout_ implements Runnable {
            layout_() {
            }
            public void run() {
                int[] los1 = new int[2];
                PosterActivity.this.lay_scroll.getLocationOnScreen(los1);
                PosterActivity.this.parentY = (float) los1[1];
            }
        }
        main_layout_runnable() { }
        public void run() {
            PosterActivity.this.guideline.setImageBitmap(Constants.guidelines_bitmap(PosterActivity.this, PosterActivity.this.main_rel.getWidth(), PosterActivity.this.main_rel.getHeight()));
            PosterActivity.this.lay_scroll.post(new layout_());
        }
    }
    
    public class BlurOperationTwoAsync extends AsyncTask<String, Void, String> {
        ImageView background_blur;
        Bitmap btmp;
        Activity context;

        public BlurOperationTwoAsync(PosterActivity posterActivity, Bitmap bit, ImageView background_blur) {
            this.context = posterActivity;
            this.btmp = bit;
            this.background_blur = background_blur;
        }

        protected void onPreExecute() {
        }

        protected String doInBackground(String... params) {
            this.btmp = PosterActivity.this.gaussinBlur(this.context, this.btmp);
            return "yes";
        }

        protected void onPostExecute(String result) {
            this.background_blur.setImageBitmap(this.btmp);
            PosterActivity.this.txt_stkr_rel.removeAllViews();
            if (PosterActivity.this.temp_path.equals("")) {
                new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
                return;
            }
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1");
            if (file.exists()) {
                if (file.listFiles().length >= 7) {
                    new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
                } else if (new File(PosterActivity.this.temp_path).exists()) {
                    new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
                } else {
                    new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
                }
            } else if (new File(PosterActivity.this.temp_path).exists()) {
                new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
            } else {
                new LordStickersAsync().execute(new String[]{"" + PosterActivity.this.template_id});
            }
        }
    }

    class change_color_listener implements OnColorChangedListener {
        change_color_listener() {
        }

        public void onColorChanged(int c) {
            PosterActivity.this.updateBgColor(c);
        }
    }


    private class LordStickersAsync extends AsyncTask<String, String, Boolean> {
        private LordStickersAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            DatabaseHandler dh = DatabaseHandler.getDbHandler(PosterActivity.this.getApplicationContext());
            ArrayList<ComponentInfo> shapeInfoList = dh.getComponentInfoList(PosterActivity.this.template_id, "SHAPE");
            ArrayList<TextInfo> textInfoList = dh.getTextInfoList(PosterActivity.this.template_id);
            ArrayList<ComponentInfo> stickerInfoList = dh.getComponentInfoList(PosterActivity.this.template_id, "STICKER");
            dh.close();
            PosterActivity.this.txtShapeList = new HashMap();
            Iterator it = textInfoList.iterator();
            while (it.hasNext()) {
                TextInfo ti = (TextInfo) it.next();
                PosterActivity.this.txtShapeList.put(Integer.valueOf(ti.getORDER()), ti);
            }
            it = stickerInfoList.iterator();
            while (it.hasNext()) {
                ComponentInfo ci = (ComponentInfo) it.next();
                PosterActivity.this.txtShapeList.put(Integer.valueOf(ci.getORDER()), ci);
            }
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (PosterActivity.this.txtShapeList.size() == 0) {
                PosterActivity.this.dialogIs.dismiss();
            }
            List sortedKeys = new ArrayList(PosterActivity.this.txtShapeList.keySet());
            Collections.sort(sortedKeys);
            int len = sortedKeys.size();
            for (int j = 0; j < len; j++) {
                Object obj = PosterActivity.this.txtShapeList.get(sortedKeys.get(j));
                PosterActivity posterActivity;
                if (obj instanceof ComponentInfo) {
                    String stkr_path = ((ComponentInfo) obj).getSTKR_PATH();
                    ResizableStickerView riv;
                    if (stkr_path.equals("")) {
                        riv = new ResizableStickerView(PosterActivity.this);
                        PosterActivity.this.txt_stkr_rel.addView(riv);
                        riv.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                        riv.setComponentInfo((ComponentInfo) obj);
                        riv.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                        riv.setOnTouchCallbackListener(PosterActivity.this);
                        riv.setBorderVisibility(false);
                        posterActivity = PosterActivity.this;
                        posterActivity.sizeFull++;
                    } else {
                        File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1");
                        if (!pictureFileDir.exists() && !pictureFileDir.mkdirs()) {
                            Log.d("", "Can't create directory to save image.");
                            Toast.makeText(PosterActivity.this, PosterActivity.this.getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
                            return;
                        } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1").exists()) {
                           File file1 = new File(stkr_path);
                            if (file1.exists()) {
                                riv = new ResizableStickerView(PosterActivity.this);
                                PosterActivity.this.txt_stkr_rel.addView(riv);
                                riv.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                                riv.setComponentInfo((ComponentInfo) obj);
                                riv.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                                riv.setOnTouchCallbackListener(PosterActivity.this);
                                riv.setBorderVisibility(false);
                                posterActivity = PosterActivity.this;
                                posterActivity.sizeFull++;
                            } else if (file1.getName().replace(".png", "").length() < 7) {
                                PosterActivity.this.dialogShow = false;
                            } else {
                                if (PosterActivity.this.OneShow) {
                                    PosterActivity.this.dialogShow = true;
                                    PosterActivity.this.errorDialogTempInfo();
                                    PosterActivity.this.OneShow = false;
                                }
                                posterActivity = PosterActivity.this;
                                posterActivity.sizeFull++;
                            }
                        } else {
                          File  file1 = new File(stkr_path);
                            if (file1.exists()) {
                                riv = new ResizableStickerView(PosterActivity.this);
                                PosterActivity.this.txt_stkr_rel.addView(riv);
                                riv.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                                riv.setComponentInfo((ComponentInfo) obj);
                                riv.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                                riv.setOnTouchCallbackListener(PosterActivity.this);
                                riv.setBorderVisibility(false);
                                posterActivity = PosterActivity.this;
                                posterActivity.sizeFull++;
                            } else if (file1.getName().replace(".png", "").length() < 7) {
                                PosterActivity.this.dialogShow = false;
                            } else {
                                if (PosterActivity.this.OneShow) {
                                    PosterActivity.this.dialogShow = true;
                                    PosterActivity.this.errorDialogTempInfo();
                                    PosterActivity.this.OneShow = false;
                                }
                                posterActivity = PosterActivity.this;
                                posterActivity.sizeFull++;
                            }
                        }
                    }
                } else {
                    AutofitTextRel rl = new AutofitTextRel(PosterActivity.this);
                    PosterActivity.this.txt_stkr_rel.addView(rl);
                    rl.setTextInfo((TextInfo) obj, false);
                    rl.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                    rl.setOnTouchCallbackListener(PosterActivity.this);
                    rl.setBorderVisibility(false);
                    PosterActivity.this.fontName = ((TextInfo) obj).getFONT_NAME();
                    PosterActivity.this.tColor = ((TextInfo) obj).getTEXT_COLOR();
                    PosterActivity.this.shadowColor = ((TextInfo) obj).getSHADOW_COLOR();
                    PosterActivity.this.shadowProg = ((TextInfo) obj).getSHADOW_PROG();
                    PosterActivity.this.tAlpha = ((TextInfo) obj).getTEXT_ALPHA();
                    PosterActivity.this.bgDrawable = ((TextInfo) obj).getBG_DRAWABLE();
                    PosterActivity.this.bgAlpha = ((TextInfo) obj).getBG_ALPHA();
                    PosterActivity.this.rotation = ((TextInfo) obj).getROTATION();
                    PosterActivity.this.bgColor = ((TextInfo) obj).getBG_COLOR();
                    posterActivity = PosterActivity.this;
                    posterActivity.sizeFull++;
                    Log.e("details is", "" + ((TextInfo) obj).getWIDTH() + " ," + ((TextInfo) obj).getHEIGHT() + " ," + ((TextInfo) obj).getTEXT());
                }
            }
            if (PosterActivity.this.txtShapeList.size() == PosterActivity.this.sizeFull && PosterActivity.this.dialogShow) {
                PosterActivity.this.dialogIs.dismiss();
            }
        }
    }

    private class LordTemplateAsync extends AsyncTask<String, String, Boolean> {
        int indx;
        String postion;

        private LordTemplateAsync() {
            this.indx = 0;
            this.postion = "1";
        }

        protected void onPreExecute() {
            super.onPreExecute();
            PosterActivity.this.dialogIs = new ProgressDialog(PosterActivity.this);
            PosterActivity.this.dialogIs.setMessage(PosterActivity.this.getResources().getString(R.string.plzwait));
            PosterActivity.this.dialogIs.setCancelable(false);
            PosterActivity.this.dialogIs.show();
        }

        protected Boolean doInBackground(String... params) {
            this.indx = Integer.parseInt(params[0]);
            TemplateInfo templateInfo = (TemplateInfo) PosterActivity.this.templateList.get(this.indx);
            PosterActivity.this.template_id = templateInfo.getTEMPLATE_ID();
            PosterActivity.this.frame_Name = templateInfo.getFRAME_NAME();
            PosterActivity.this.temp_path = templateInfo.getTEMP_PATH();
            PosterActivity.this.ratio = templateInfo.getRATIO();
            PosterActivity.this.profile = templateInfo.getPROFILE_TYPE();
            String seekValue1 = templateInfo.getSEEK_VALUE();
            PosterActivity.this.hex = templateInfo.getTEMPCOLOR();
            PosterActivity.this.overlay_Name = templateInfo.getOVERLAY_NAME();
            PosterActivity.this.overlay_opacty = templateInfo.getOVERLAY_OPACITY();
            PosterActivity.this.overlay_blur = templateInfo.getOVERLAY_BLUR();
            PosterActivity.this.seekValue = Integer.parseInt(seekValue1);
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (PosterActivity.this.profile.equals("Background")) {
                this.postion = PosterActivity.this.frame_Name.replace("b", "");
            } else if (!PosterActivity.this.profile.equals("Color")) {
                if (PosterActivity.this.profile.equals("Texture")) {
                    this.postion = PosterActivity.this.frame_Name.replace("t", "");
                    PosterActivity.this.seek_tailys.setProgress(PosterActivity.this.seekValue);
                } else if (!(!PosterActivity.this.profile.equals("Temp_Path") || PosterActivity.this.frame_Name.equals("") || PosterActivity.this.ratio.equals(""))) {
                    this.postion = PosterActivity.this.frame_Name.replace("b", "");
                }
            }
            if (!PosterActivity.this.overlay_Name.equals("")) {
                PosterActivity.this.setBitmapOverlay(BitmapFactory.decodeResource(PosterActivity.this.getResources(), PosterActivity.this.getResources().getIdentifier(PosterActivity.this.overlay_Name, "drawable", PosterActivity.this.getPackageName())));
            }
            PosterActivity.this.seek.setProgress(PosterActivity.this.overlay_opacty);
            PosterActivity.this.seek_blur.setProgress(PosterActivity.this.overlay_blur);
            String vv = String.valueOf(Integer.parseInt(this.postion));
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("USER")) {
                PosterActivity.this.drawBackgroundImage(PosterActivity.this.ratio, vv, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("FREESTYLE")) {
                PosterActivity.this.drawBackgroundImage(PosterActivity.this.ratio, vv, PosterActivity.this.profile, "created");
            }
            if (((TemplateInfo) PosterActivity.this.templateList.get(this.indx)).getTYPE().equals("FRIDAY")) {
                PosterActivity.this.drawBackgroundImage(PosterActivity.this.ratio, vv, PosterActivity.this.profile, "created");
            }
        }
    }

    private class SaveStickersAsync extends AsyncTask<String, String, Boolean> {
        Object objk;
        String stkr_path;

        public SaveStickersAsync(Object obj1) {
            this.objk = obj1;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            String stkrName = params[0];
            this.stkr_path = ((ComponentInfo) this.objk).getSTKR_PATH();
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(PosterActivity.this.getResources(), PosterActivity.this.getResources().getIdentifier(stkrName, "drawable", PosterActivity.this.getPackageName()));
                if (bitmap != null) {
                    return Boolean.valueOf(Constants.saveBitmapObject(PosterActivity.this, bitmap, this.stkr_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            PosterActivity posterActivity = PosterActivity.this;
            posterActivity.sizeFull++;
            if (PosterActivity.this.txtShapeList.size() == PosterActivity.this.sizeFull) {
                PosterActivity.this.dialogShow = true;
            }
            if (isDownloaded.booleanValue()) {
                ResizableStickerView riv = new ResizableStickerView(PosterActivity.this);
                PosterActivity.this.txt_stkr_rel.addView(riv);
                riv.optimizeScreen(PosterActivity.this.screenWidth, PosterActivity.this.screenHeight);
                riv.setComponentInfo((ComponentInfo) this.objk);
                riv.optimize(PosterActivity.this.wr, PosterActivity.this.hr);
                riv.setOnTouchCallbackListener(PosterActivity.this);
                riv.setBorderVisibility(false);
            }
            if (PosterActivity.this.dialogShow) {
                PosterActivity.this.dialogIs.dismiss();
            }
        }
    }

    private class SavebackgrundAsync extends AsyncTask<String, String, Boolean> {
        private String crted;
        private String profile;
        private String ratio;

        private SavebackgrundAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            String stkrName = params[0];
            this.ratio = params[1];
            this.profile = params[2];
            this.crted = params[3];
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(PosterActivity.this.getResources(), PosterActivity.this.getResources().getIdentifier(stkrName, "drawable", PosterActivity.this.getPackageName()));
                if (bitmap != null) {
                    return Boolean.valueOf(Constants.saveBitmapObject(PosterActivity.this, bitmap, PosterActivity.this.temp_path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (isDownloaded.booleanValue()) {
                try {
                    PosterActivity.this.bitmapRatio(this.ratio, this.profile, ImageUtils.getResampleImageBitmap(Uri.parse(PosterActivity.this.temp_path), PosterActivity.this, (int) (PosterActivity.this.screenWidth > PosterActivity.this.screenHeight ? PosterActivity.this.screenWidth : PosterActivity.this.screenHeight)), this.crted);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                PosterActivity.this.txt_stkr_rel.removeAllViews();
            }
        }
    }

    Button btn_layControls;
    FrameLayout lay_container;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        setContentView(R.layout.activity_poster);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        intilization();
        f24c = this;
        this.options.inScaled = false;
        activity = this;
        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        DisplayMetrics dimension = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.screenWidth = (float) dimension.widthPixels;
        this.screenHeight = (float) (dimension.heightPixels - ImageUtils.dpToPx(this, 105));
        this.prefs = getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getSharedPreferences("MY_PREFS_NAME", 0).edit();
        initViewPager();
        this.ttfHeader = Constants.getHeaderTypeface(this);
        ((TextView) findViewById(R.id.txtheader)).setTypeface(this.ttfHeader);
        ((TextView) findViewById(R.id.txtheader1)).setTypeface(this.ttfHeader);
        if (getIntent().getBooleanExtra("loadUserFrame", false)) {
            Bundle extra = getIntent().getExtras();
            if (!extra.getString("ratio").equals("cropImg")) {
                this.ratio = extra.getString("ratio");
                this.position = extra.getString("position");
                this.profile = extra.getString(Scopes.PROFILE);
                this.hex = extra.getString("hex");
                drawBackgroundImage(this.ratio, this.position, this.profile, "nonCreated");
            } else if (extra.getString("ratio").equals("cropImg")) {
                this.ratio = "";
                this.position = "1";
                this.profile = "Temp_Path";
                this.hex = "";
                setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(CropActivity.bitmapImage, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
            }
        } else {
            this.temp_Type = getIntent().getExtras().getString("Temp_Type");
            DatabaseHandler dh = DatabaseHandler.getDbHandler(getApplicationContext());
            if (this.temp_Type.equals("MY_TEMP")) {
                this.templateList = dh.getTemplateListDes("USER");
            } else if (this.temp_Type.equals("FREE_TEMP")) {
                this.templateList = dh.getTemplateList("FREESTYLE");
            } else if (this.temp_Type.equals("FRIDAY_TEMP")) {
                this.templateList = dh.getTemplateList("FRIDAY");
            }
            dh.close();
            final int intExtra = getIntent().getIntExtra("position", 0);
            this.center_rel.post(new Runnable() {
                public void run() {
                    new LordTemplateAsync().execute(new String[]{"" + intExtra});
                }
            });
        }
        int[] colors = new int[this.pallete.length];
        for (int i = 0; i < colors.length; i++) {
            colors[i] = Color.parseColor(this.pallete[i]);
        }
        this.horizontalPicker.setColors(colors);
        this.horizontalPickerColor.setColors(colors);
        this.shadowPickerColor.setColors(colors);
        this.pickerBg.setColors(colors);
        this.horizontalPicker.setSelectedColor(this.textColorSet);
        this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
        this.shadowPickerColor.setSelectedColor(colors[0]);
        this.pickerBg.setSelectedColor(colors[0]);
        int color = this.horizontalPicker.getColor();
        int color1 = this.horizontalPickerColor.getColor();
        int color2 = this.shadowPickerColor.getColor();
        int color3 = this.pickerBg.getColor();
        updateColor(color);
        updateColor(color1);
        updateShadow(color2);
        updateBgColor(color3);
        OnColorChangedListener color_change_listener = new change_color_listener();
        this.horizontalPicker.setOnColorChangedListener(color_change_listener);
        this.horizontalPickerColor.setOnColorChangedListener(color_change_listener);
        this.shadowPickerColor.setOnColorChangedListener(color_change_listener);
        this.pickerBg.setOnColorChangedListener(color_change_listener);
        this.guideline = (ImageView) findViewById(R.id.guidelines);
        this.select_backgnd.setBackgroundResource(R.drawable.overlay);
        this.select_effect.setBackgroundResource(R.drawable.overlay);
        this.user_image.setBackgroundResource(R.drawable.overlay);
        this.add_text.setBackgroundResource(R.drawable.overlay);
        this.add_sticker.setBackgroundResource(R.drawable.overlay);
        this.rellative = (RelativeLayout) findViewById(R.id.rellative);
        this.btn_bck1 = (ImageButton) findViewById(R.id.btn_bck1);
        this.btn_bck1.setOnClickListener(this);
        this.lay_scroll = (ScrollView) findViewById(R.id.lay_scroll);
        this.lay_scroll.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return false;
            }
        });
        this.lay_scroll.setLayoutParams(new LayoutParams(-1, -2));
        this.lay_scroll.postInvalidate();
        this.lay_scroll.requestLayout();
        ((TextView) findViewById(R.id.txt_add_text)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_add_sticker)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_select_dframe)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_image)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_select_edit)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_fonts)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_colors)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_shadow)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_bg)).setTypeface(this.ttf);
        ((TextView) findViewById(R.id.txt_controls)).setTypeface(this.ttf);
        ImageButton btnRight = (ImageButton) findViewById(R.id.btnRight);
        ImageButton btnUp = (ImageButton) findViewById(R.id.btnUp);
        ImageButton btnDown = (ImageButton) findViewById(R.id.btnDown);
        ImageButton btnLeftS = (ImageButton) findViewById(R.id.btnLeftS);
        ImageButton btnRightS = (ImageButton) findViewById(R.id.btnRightS);
        ImageButton btnUpS = (ImageButton) findViewById(R.id.btnUpS);
        ImageButton btnDownS = (ImageButton) findViewById(R.id.btnDownS);

        ((ImageButton) findViewById(R.id.btnLeft)).setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decX");

            }
        }));

        btnRight.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrX");
            }
        }));

        btnUp.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decY");

            }
        }));

        btnDown.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrY");
            }
        }));



        btnLeftS.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decX");
            }
        }));



        btnRightS.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrX");
            }
        }));


        btnUpS.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("decY");

            }
        }));




        btnDownS.setOnTouchListener(new RepeatListener(200, 100, new OnClickListener() {
            @Override
            public void onClick(View view) {
                PosterActivity.this.updatePositionSticker("incrY");
                
            }
        }));

    }

    private void drawBackgroundImage(String ratio, String position, String profile, String crted) {
        this.lay_sticker.setVisibility(View.GONE);
        int i = Integer.parseInt(position);
        if (!profile.equals("no")) {
            if (profile.equals("Background")) {
                this.lay_handletails.setVisibility(View.GONE);
                this.frame_Name = "b" + String.valueOf(i);
                this.temp_path = "";
                Options bfo = new Options();
                bfo.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), Constants.Imageid0[i], bfo);
                Options optsDownSample = new Options();
                optsDownSample.inSampleSize = ImageUtils.getClosestResampleSize(bfo.outWidth, bfo.outHeight, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight));
                bfo.inJustDecodeBounds = false;
                bitmapRatio(ratio, profile, BitmapFactory.decodeResource(getResources(), Constants.Imageid0[i], optsDownSample), crted);
                return;
            }
            if (profile.equals("Texture")) {
                this.frame_Name = "t" + String.valueOf(i);
                this.temp_path = "";
                this.curTileId = Constants.Imageid1[i];
                this.showtailsSeek = true;
                this.lay_handletails.setVisibility(View.VISIBLE);
                bitmapRatio(ratio, profile, ImageUtils.getTiledBitmap(this, this.curTileId, (int) this.screenWidth, (int) this.screenHeight), crted);
                return;
            }
            if (profile.equals("Color")) {
                this.lay_handletails.setVisibility(View.GONE);
                this.temp_path = "";
                String hex1 = this.hex;
                Bitmap image = Bitmap.createBitmap(HttpStatus.SC_INTERNAL_SERVER_ERROR, 650, Config.ARGB_8888);
                image.eraseColor(Color.parseColor("#" + hex1));
                bitmapRatio(ratio, profile, image, crted);
                return;
            }
            if (profile.equals("Temp_Path")) {
                this.profile = "Temp_Path";
                if (ratio.equals("")) {
                    this.frame_Name = "";
                } else {
                    this.frame_Name = "b" + String.valueOf(i);
                }
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1");
                if (file.exists() || file.mkdirs()) {
                    File file2 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1");
                    if (file2.exists()) {
                    }
                    if (crted.equals("nonCreated")) {
                        this.uriArry.clear();
                        for (File absolutePath22 : file2.listFiles()) {
                            this.uriArry.add(absolutePath22.getAbsolutePath());
                        }
                        this.temp_path = (String) this.uriArry.get(i);
                    }
                    File file1 = new File(this.temp_path);
                    if (file1.exists()) {
                        try {
                            bitmapRatio(ratio, profile, ImageUtils.getResampleImageBitmap(Uri.parse(this.temp_path), this, (int) (this.screenWidth > this.screenHeight ? this.screenWidth : this.screenHeight)), crted);
                            return;
                        } catch (IOException e22) {
                            e22.printStackTrace();
                            return;
                        }
                    }
                    if (!ratio.equals("")) {
                        String draName = file1.getName().replace(".png", "");
                        new SavebackgrundAsync().execute(new String[]{draName, ratio, profile, crted});
                        return;
                    } else if (this.OneShow) {
                        errorDialogTempInfo();
                        this.OneShow = false;
                        return;
                    } else {
                        return;
                    }
                }
                Log.d("", "Can't create directory to save image.");
                Toast.makeText(this, getResources().getString(R.string.create_dir_err), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void bitmapRatio(String ratio, String profile, Bitmap btm, String crted) {
        Bitmap bit = null;
        if (ratio.equals("")) {
            bit = btm;
        } else if (ratio.equals("1:1")) {
            bit = cropInRatio(btm, 1, 1);
        } else if (ratio.equals("16:9")) {
            bit = cropInRatio(btm, 16, 9);
        } else if (ratio.equals("9:16")) {
            bit = cropInRatio(btm, 9, 16);
        } else if (ratio.equals("4:3")) {
            bit = cropInRatio(btm, 4, 3);
        } else if (ratio.equals("3:4")) {
            bit = cropInRatio(btm, 3, 4);
        }
        bit = ImageUtils.resizeBitmap(bit, (int) this.screenWidth, (int) this.screenHeight);
        if (!crted.equals("created")) {
            setImageBitmapAndResizeLayout(bit, "nonCreated");
        } else if (profile.equals("Texture")) {
            setImageBitmapAndResizeLayout(Constants.getTiledBitmap((Activity) this, this.curTileId, bit, this.seek_tailys), "created");
        } else {
            setImageBitmapAndResizeLayout(bit, "created");
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

    private void setImageBitmapAndResizeLayout(Bitmap bit, String creted) {
        this.main_rel.getLayoutParams().width = bit.getWidth();
        this.main_rel.getLayoutParams().height = bit.getHeight();
        this.main_rel.postInvalidate();
        this.main_rel.requestLayout();
        this.background_img.setImageBitmap(bit);
        imgBtmap = bit;
        this.main_rel.post(new main_layout_runnable());
        
        try {
            float ow = (float) bit.getWidth();
            float oh = (float) bit.getHeight();
            bit = ImageUtils.resizeBitmap(bit, this.center_rel.getWidth(), this.center_rel.getHeight());
            float nh = (float) bit.getHeight();
            this.wr = ((float) bit.getWidth()) / ow;
            this.hr = nh / oh;
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (this.min != 0) {
            this.background_blur.setVisibility(View.VISIBLE);
        } else {
            this.background_blur.setVisibility(View.GONE);
        }
        if (creted.equals("created")) {
            new BlurOperationTwoAsync(this, bit, this.background_blur).execute(new String[]{""});
            return;
        }
        new BlurOperationAsync(this, bit, this.background_blur).execute(new String[]{""});
    }

    private void intilization() {
        this.ttf = Constants.getTextTypeface(this);
        this.center_rel = (RelativeLayout) findViewById(R.id.center_rel);
        this.lay_touchremove = (RelativeLayout) findViewById(R.id.lay_touchremove);
        this.main_rel = (RelativeLayout) findViewById(R.id.main_rel);
        this.background_img = (ImageView) findViewById(R.id.background_img);
        this.background_blur = (ImageView) findViewById(R.id.background_blur);
        this.txt_stkr_rel = (RelativeLayout) findViewById(R.id.txt_stkr_rel);
        this.user_image = (RelativeLayout) findViewById(R.id.user_image);
        this.select_backgnd = (RelativeLayout) findViewById(R.id.select_backgnd);
        this.select_effect = (RelativeLayout) findViewById(R.id.select_effect);
        this.add_sticker = (RelativeLayout) findViewById(R.id.add_sticker);
        this.add_text = (RelativeLayout) findViewById(R.id.add_text);
        this.lay_effects = (LinearLayout) findViewById(R.id.lay_effects);
        this.lay_sticker = (RelativeLayout) findViewById(R.id.lay_sticker);
        this.lay_handletails = (RelativeLayout) findViewById(R.id.lay_handletails);
        this.seekbar_container = (LinearLayout) findViewById(R.id.seekbar_container);
        this.shape_rel = (RelativeLayout) findViewById(R.id.shape_rel);
        this.seek_tailys = (SeekBar) findViewById(R.id.seek_tailys);
        this.alphaSeekbar = (SeekBar) findViewById(R.id.alpha_seekBar);
        this.seekBar3 = (SeekBar) findViewById(R.id.seekBar3);
        this.seekBar_shadow = (SeekBar) findViewById(R.id.seekBar_shadow);
        this.hueSeekbar = (SeekBar) findViewById(R.id.hue_seekBar);
        this.trans_img = (ImageView) findViewById(R.id.trans_img);
        this.alphaSeekbar.setOnSeekBarChangeListener(this);
        this.seekBar3.setOnSeekBarChangeListener(this);
        this.seekBar_shadow.setOnSeekBarChangeListener(this);
        this.hueSeekbar.setOnSeekBarChangeListener(this);
        this.seek_tailys.setOnSeekBarChangeListener(this);
        this.seek = (SeekBar) findViewById(R.id.seek);
        this.lay_filter = (RelativeLayout) findViewById(R.id.lay_filter);
        this.lay_dupliText = (LinearLayout) findViewById(R.id.lay_dupliText);
        this.lay_dupliStkr = (LinearLayout) findViewById(R.id.lay_dupliStkr);
        this.lay_edit = (LinearLayout) findViewById(R.id.lay_edit);
        this.lay_dupliText.setOnClickListener(this);
        this.lay_dupliStkr.setOnClickListener(this);
        this.lay_edit.setOnClickListener(this);
        this.seek_blur = (SeekBar) findViewById(R.id.seek_blur);
        this.trans_img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.o1));
        this.hueSeekbar.setProgress(1);
        this.seek.setMax(255);
        this.seek.setProgress(80);
        this.seek_blur.setMax(255);
        this.seekBar_shadow.setProgress(0);
        this.seekBar3.setProgress(255);
        this.seek_blur.setProgress(this.min);
        this.trans_img.setImageAlpha(this.alpha);
        this.seek.setOnSeekBarChangeListener(this);
        this.seek_blur.setOnSeekBarChangeListener(this);
        this.seek_tailys.setMax(290);
        this.seek_tailys.setProgress(90);
        this.logo_ll = (LinearLayout) findViewById(R.id.logo_ll);
        this.img_oK = (Button) findViewById(R.id.btn_done);
        this.img_oK.setOnClickListener(this);
        this.user_image.setOnClickListener(this);
        this.select_backgnd.setOnClickListener(this);
        this.select_effect.setOnClickListener(this);
        this.add_sticker.setOnClickListener(this);
        this.add_text.setOnClickListener(this);
        this.lay_touchremove.setOnClickListener(this);
        this.center_rel.setOnClickListener(this);
        this.lay_textEdit = (LinearLayout) findViewById(R.id.lay_textEdit);
        this.animSlideUp = Constants.getAnimUp(this);
        this.animSlideDown = Constants.getAnimDown(this);
        this.verticalSeekBar = (SeekBar) findViewById(R.id.seekBar2);
        this.verticalSeekBar.setOnSeekBarChangeListener(this);
        this.horizontalPicker = (LineColorPicker) findViewById(R.id.picker);
        this.horizontalPickerColor = (LineColorPicker) findViewById(R.id.picker1);
        this.shadowPickerColor = (LineColorPicker) findViewById(R.id.pickerShadow);
        this.pickerBg = (LineColorPicker) findViewById(R.id.pickerBg);
        this.lay_color = (RelativeLayout) findViewById(R.id.lay_color);
        this.lay_hue = (RelativeLayout) findViewById(R.id.lay_hue);
        this.lay_effects.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        this.lay_textEdit.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });
        this.seekbar_container.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {
            }
        });

        btn_layControls=(Button)findViewById(R.id.btn_layControls);
        lay_container=(FrameLayout)findViewById(R.id.lay_container);
        btn_layControls.setOnClickListener(new OnClickListener() {
            public void onClick(View view) {

                if (lay_container.getVisibility() == 8) {
                    btn_layControls.setVisibility(View.GONE);
                    listFragment.getLayoutChild(true);
                    lay_container.setVisibility(View.VISIBLE);
                    lay_container.animate().translationX((float) lay_container.getLeft()).setDuration(200).setInterpolator(new DecelerateInterpolator()).start();
                    return;
                }
                lay_container.setVisibility(View.VISIBLE);
                lay_container.animate().translationX((float) (-lay_container.getRight())).setDuration(200).setInterpolator(new AccelerateInterpolator()).start();
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        PosterActivity.this.lay_container.setVisibility(View.GONE);
                        PosterActivity.this.btn_layControls.setVisibility(View.VISIBLE);
                    }
                }, 200);
                return;

            }
        });

        this.fontsShow = (LinearLayout) findViewById(R.id.fontsShow);
        this.colorShow = (LinearLayout) findViewById(R.id.colorShow);
        this.sadowShow = (LinearLayout) findViewById(R.id.sadowShow);
        this.bgShow = (LinearLayout) findViewById(R.id.bgShow);
        this.controlsShow = (LinearLayout) findViewById(R.id.controlsShow);
        this.layArr[0] = (RelativeLayout) findViewById(R.id.lay_fonts);
        this.layArr[1] = (RelativeLayout) findViewById(R.id.lay_colors);
        this.layArr[2] = (RelativeLayout) findViewById(R.id.lay_shadow);
        this.layArr[3] = (RelativeLayout) findViewById(R.id.lay_backgnd);
        this.layArr[4] = (RelativeLayout) findViewById(R.id.lay_controls);
        setSelected(R.id.lay_fonts);
        this.adapter = new AssetsGridMain(this, getResources().getStringArray(R.array.fonts_array));
        GridView font_gridview = (GridView) findViewById(R.id.font_gridview);
        font_gridview.setAdapter(this.adapter);
        font_gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PosterActivity.this.setTextFonts((String) PosterActivity.this.adapter.getItem(position));
                PosterActivity.this.adapter.setSelected(position);
            }
        });
        HorizontalListView listview = (HorizontalListView) findViewById(R.id.listview);
        listview.setAdapter(new ImageViewAdapter(this, this.imageId));
        listview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                PosterActivity.this.setTextBgTexture("btxt" + String.valueOf(position));
            }
        });
        this.lay_colorOpacity = (RelativeLayout) findViewById(R.id.lay_colorOpacity);
        this.lay_controlStkr = (RelativeLayout) findViewById(R.id.lay_controlStkr);
        this.lay_colorOacity = (LinearLayout) findViewById(R.id.lay_colorOacity);
        this.controlsShowStkr = (LinearLayout) findViewById(R.id.controlsShowStkr);
        this.lay_colorOpacity.setOnClickListener(this);
        this.lay_controlStkr.setOnClickListener(this);

        this.listFragment = new ListFragment();
        this.listFragment.setLayouts(this.txt_stkr_rel, this.lay_container, this.btn_layControls);
        showFragment(this.listFragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.lay_container, fragment, "fragment").commit();
    }

    public void onClick(View view) {
        int i;
        switch (view.getId()) {
            case R.id.add_sticker:
                removeScroll();
                removeImageViewControll();
                this.lay_handletails.setVisibility(View.GONE);
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                this.img_oK.setVisibility(View.GONE);
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    this.lay_textEdit.startAnimation(this.animSlideDown);
                    this.lay_textEdit.setVisibility(View.GONE);
                }
                this.lay_sticker.setVisibility(View.VISIBLE);
                return;
            case R.id.add_text:
                removeScroll();
                removeImageViewControll();
                this.lay_handletails.setVisibility(View.GONE);
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.lay_textEdit.setVisibility(View.GONE);
                openTextActivity();
                return;
            case R.id.btnColor:
                new AmbilWarnaDialog(this, 0, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        PosterActivity.this.updateColor(color);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.btn_bck1:
                this.lay_scroll.smoothScrollTo(0, this.distanceScroll);
                return;
            case R.id.btn_bckprass:
                removeScroll();
                onBackPressed();
                return;
            case R.id.btn_done:
                removeScroll();
                this.lay_handletails.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.lay_effects.setVisibility(View.GONE);
                removeImageViewControll();
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    this.lay_textEdit.startAnimation(this.animSlideDown);
                    this.lay_textEdit.setVisibility(View.GONE);
                }
                this.guideline.setVisibility(View.GONE);
                showDialogSave();
                return;
            case R.id.btn_piclColor:
                Intent ii1 = new Intent(this, PickColorImageActivity.class);
                ii1.putExtra("way", "txtColor");
                startActivity(ii1);
                return;
            case R.id.btn_piclColor2:
                Intent ii2 = new Intent(this, PickColorImageActivity.class);
                ii2.putExtra("way", "txtShadow");
                startActivity(ii2);
                return;
            case R.id.btn_piclColor3:
                Intent intent = new Intent(this, PickColorImageActivity.class);
                intent.putExtra("way", "txtBg");
                startActivity(intent);
                return;
            case R.id.btn_piclColorS:
                Intent ii = new Intent(this, PickColorImageActivity.class);
                ii.putExtra("way", "stkr");
                startActivity(ii);
                return;
            case R.id.btn_txtColor:
                new AmbilWarnaDialog(this, this.textColorSet, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        PosterActivity.this.updateColor(color);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.btn_txtColor1:
                new AmbilWarnaDialog(this, this.stkrColorSet, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        PosterActivity.this.updateColor(color);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.btn_txtColor2:
                new AmbilWarnaDialog(this, 0, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        PosterActivity.this.updateShadow(color);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.btn_txtColor3:
                new AmbilWarnaDialog(this, 0, new OnAmbilWarnaListener() {
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        PosterActivity.this.updateBgColor(color);
                    }

                    public void onCancel(AmbilWarnaDialog dialog) {
                    }
                }).show();
                return;
            case R.id.center_rel:
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                onTouchApply();
                return;
            case R.id.lay_backgnd:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.VISIBLE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_backgnd);
                return;
            case R.id.lay_colorOpacity:
                this.lay_colorOacity.setVisibility(View.VISIBLE);
                this.controlsShowStkr.setVisibility(View.GONE);
                this.lay_colorOpacity.setBackgroundResource(R.drawable.trans);
                this.lay_controlStkr.setBackgroundResource(R.drawable.overlay);
                return;
            case R.id.lay_colors:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.VISIBLE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_colors);
                return;
            case R.id.lay_controlStkr:
                this.lay_colorOacity.setVisibility(View.GONE);
                this.controlsShowStkr.setVisibility(View.VISIBLE);
                this.lay_controlStkr.setBackgroundResource(R.drawable.trans);
                this.lay_colorOpacity.setBackgroundResource(R.drawable.overlay);
                return;
            case R.id.lay_controls:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.VISIBLE);
                setSelected(R.id.lay_controls);
                return;
            case R.id.lay_dupliStkr:
                int childCount = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount; i++) {
                    View view1 = this.txt_stkr_rel.getChildAt(i);
                    if ((view1 instanceof ResizableStickerView) && ((ResizableStickerView) view1).getBorderVisbilty()) {
                        ResizableStickerView resizableStickerView1 = new ResizableStickerView(this);
                        resizableStickerView1.setComponentInfo(((ResizableStickerView) view1).getComponentInfo());
                        this.txt_stkr_rel.addView(resizableStickerView1);
                        removeImageViewControll();
                        resizableStickerView1.setOnTouchCallbackListener(this);
                        resizableStickerView1.setBorderVisibility(true);
                    }
                }
                return;
            case R.id.lay_dupliText:
                int childCount1 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount1; i++) {
                    View view2 = this.txt_stkr_rel.getChildAt(i);
                    if ((view2 instanceof AutofitTextRel) && ((AutofitTextRel) view2).getBorderVisibility()) {
                        AutofitTextRel resizableStickerView = new AutofitTextRel(this);
                        this.txt_stkr_rel.addView(resizableStickerView);
                        removeImageViewControll();
                        resizableStickerView.setTextInfo(((AutofitTextRel) view2).getTextInfo(), false);
                        resizableStickerView.setOnTouchCallbackListener(this);
                        resizableStickerView.setBorderVisibility(true);
                    }
                }
                return;
            case R.id.lay_edit:
                removeScroll();
                this.lay_textEdit.setVisibility(View.GONE);
                doubleTabPrass();
                return;
            case R.id.lay_fonts:
                this.fontsShow.setVisibility(View.VISIBLE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.GONE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_fonts);
                return;
            case R.id.lay_shadow:
                this.fontsShow.setVisibility(View.GONE);
                this.colorShow.setVisibility(View.GONE);
                this.sadowShow.setVisibility(View.VISIBLE);
                this.bgShow.setVisibility(View.GONE);
                this.controlsShow.setVisibility(View.GONE);
                setSelected(R.id.lay_shadow);
                return;
            case R.id.lay_touchremove:
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.guideline.setVisibility(View.GONE);
                onTouchApply();
                return;
            case R.id.o0:
                this.overlay_Name = "";
                this.lay_filter.setVisibility(View.GONE);
                this.trans_img.setVisibility(View.GONE);
                return;
            case R.id.o1:
                this.overlay_Name = "o1";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o1));
                return;
            case R.id.o10:
                this.overlay_Name = "o10";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o10));
                return;
            case R.id.o11:
                this.overlay_Name = "o11";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o11));
                return;
            case R.id.o12:
                this.overlay_Name = "o12";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o12));
                return;
            case R.id.o13:
                this.overlay_Name = "o13";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o13));
                return;
            case R.id.o14:
                this.overlay_Name = "o14";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o14));
                return;
            case R.id.o15:
                this.overlay_Name = "o15";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o15));
                return;
            case R.id.o16:
                this.overlay_Name = "o16";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o16));
                return;
            case R.id.o17:
                this.overlay_Name = "o17";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o17));
                return;
            case R.id.o18:
                this.overlay_Name = "o18";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o18));
                return;
            case R.id.o19:
                this.overlay_Name = "o19";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o19));
                return;
            case R.id.o2:
                this.overlay_Name = "o2";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o2));
                return;
            case R.id.o20:
                this.overlay_Name = "o20";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o20));
                return;
            case R.id.o21:
                this.overlay_Name = "o21";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o21));
                return;
            case R.id.o22:
                this.overlay_Name = "o22";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o22));
                return;
            case R.id.o23:
                this.overlay_Name = "o23";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o23));
                return;
            case R.id.o24:
                this.overlay_Name = "o24";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o24));
                return;
            case R.id.o25:
                this.overlay_Name = "o25";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o25));
                return;
            case R.id.o26:
                this.overlay_Name = "o26";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o26));
                return;
            case R.id.o27:
                this.overlay_Name = "o27";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o27));
                return;
            case R.id.o28:
                this.overlay_Name = "o28";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o28));
                return;
            case R.id.o29:
                this.overlay_Name = "o29";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o29));
                return;
            case R.id.o3:
                this.overlay_Name = "o3";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o3));
                return;
            case R.id.o30:
                this.overlay_Name = "o30";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o30));
                return;
            case R.id.o31:
                this.overlay_Name = "o31";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o31));
                return;
            case R.id.o4:
                this.overlay_Name = "o4";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o4));
                return;
            case R.id.o5:
                this.overlay_Name = "o5";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o5));
                return;
            case R.id.o6:
                this.overlay_Name = "o6";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o6));
                return;
            case R.id.o7:
                this.overlay_Name = "o7";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o7));
                return;
            case R.id.o8:
                this.overlay_Name = "o8";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o8));
                return;
            case R.id.o9:
                this.overlay_Name = "o9";
                this.lay_filter.setVisibility(View.VISIBLE);
                setBitmapOverlay(BitmapFactory.decodeResource(getResources(), R.drawable.o9));
                return;
            case R.id.select_backgnd:
                removeScroll();
                removeImageViewControll();
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.lay_handletails.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.trans);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    this.lay_textEdit.startAnimation(this.animSlideDown);
                    this.lay_textEdit.setVisibility(View.GONE);
                }
                startActivityForResult(new Intent(this, SelectImageTwoActivity.class), 4);
                return;
            case R.id.select_effect:
                removeScroll();
                removeImageViewControll();
                this.lay_handletails.setVisibility(View.GONE);
                if (this.lay_effects.getVisibility() != View.VISIBLE) {
                    this.lay_effects.setVisibility(View.VISIBLE);
                    this.lay_effects.startAnimation(this.animSlideUp);
                }
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    this.lay_textEdit.startAnimation(this.animSlideDown);
                    this.lay_textEdit.setVisibility(View.GONE);
                }
                this.seekbar_container.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.trans);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                return;
            case R.id.txt_bg_none:
                int childCount6 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount6; i++) {
                    View view6 = this.txt_stkr_rel.getChildAt(i);
                    if ((view6 instanceof AutofitTextRel) && ((AutofitTextRel) view6).getBorderVisibility()) {
                        ((AutofitTextRel) view6).setBgAlpha(0);
                        this.bgDrawable = "0";
                        this.bgColor = 0;
                    }
                }
                return;
            case R.id.user_image:
                removeScroll();
                removeImageViewControll();
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.lay_handletails.setVisibility(View.GONE);
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.trans);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
                    this.lay_textEdit.startAnimation(this.animSlideDown);
                    this.lay_textEdit.setVisibility(View.GONE);
                }
                showDialogPicker();
                return;
            default:
                return;
        }
    }

    private void setTextBgTexture(String mDrawableName) {
        int resID = getResources().getIdentifier(mDrawableName, "drawable", getPackageName());
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setBgDrawable(mDrawableName);
                ((AutofitTextRel) view).setBgAlpha(this.seekBar3.getProgress());
                this.bgColor = 0;
                ((AutofitTextRel) this.txt_stkr_rel.getChildAt(i)).getTextInfo().setBG_DRAWABLE(mDrawableName);
                this.bgDrawable = ((AutofitTextRel) view).getBgDrawable();
                this.bgAlpha = this.seekBar3.getProgress();
            }
        }
    }

    private void setTextFonts(String fontName1) {
        this.fontName = fontName1;
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setTextFont(fontName1);
            }
        }
    }

    private void setBitmapOverlay(Bitmap oi) {
        this.trans_img.setVisibility(View.VISIBLE);
        this.trans_img.setImageBitmap(oi);
    }

    private void initViewPager() {
        this.tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        this._mViewPager = (ViewPager) findViewById(R.id.imageviewPager);
        this._mViewPager.setAdapter(new StickerViewPagerAdapter(this, getSupportFragmentManager()));
        this.tabs.setViewPager(this._mViewPager);
        this.tabs.setTypeface(this.ttf, 0);
        this._mViewPager.setCurrentItem(0);
        this._mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int position) {
            }

            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            public void onPageSelected(int position) {
            }
        });
    }

    private void updateColor(int color) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setTextColor(color);
                this.tColor = color;
                this.textColorSet = color;
                this.horizontalPicker.setSelectedColor(color);
            }
            if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                ((ResizableStickerView) view).setColor(color);
                this.stkrColorSet = color;
                this.horizontalPickerColor.setSelectedColor(color);
            }
        }
    }

    private void updateShadow(int color) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setTextShadowColor(color);
                this.shadowColor = color;
            }
        }
    }

    private void updateBgColor(int color) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                ((AutofitTextRel) view).setBgAlpha(this.seekBar3.getProgress());
                ((AutofitTextRel) view).setBgColor(color);
                this.bgColor = color;
                this.bgDrawable = "0";
            }
        }
    }

    private void updatePositionSticker(String incr) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                if (incr.equals("incrX")) {
                    ((AutofitTextRel) view).incrX();
                }
                if (incr.equals("decX")) {
                    ((AutofitTextRel) view).decX();
                }
                if (incr.equals("incrY")) {
                    ((AutofitTextRel) view).incrY();
                }
                if (incr.equals("decY")) {
                    ((AutofitTextRel) view).decY();
                }
            }
            if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                if (incr.equals("incrX")) {
                    ((ResizableStickerView) view).incrX();
                }
                if (incr.equals("decX")) {
                    ((ResizableStickerView) view).decX();
                }
                if (incr.equals("incrY")) {
                    ((ResizableStickerView) view).incrY();
                }
                if (incr.equals("decY")) {
                    ((ResizableStickerView) view).decY();
                }
            }
        }
    }

    private void createFrame() {
        this.main_rel.setDrawingCacheEnabled(true);
        final Bitmap thumbBit = Bitmap.createBitmap(this.main_rel.getDrawingCache());
        this.main_rel.setDrawingCacheEnabled(false);
        final ProgressDialog ringProgressDialog = ProgressDialog.show(this, "", ImageUtils.getSpannableString(this, this.ttf, R.string.plzwait), true);
        ringProgressDialog.setCancelable(false);
        new Thread(new Runnable() {
            public void run() {
                DatabaseHandler dh = null;
                try {
                    if (PosterActivity.this.ratio.equals("")) {
                        PosterActivity.this.temp_path = Constants.saveBitmapObject1(PosterActivity.imgBtmap);
                    }
                    String thumbPath = Constants.saveBitmapObject(PosterActivity.this, ImageUtils.resizeBitmap(thumbBit, ((int) PosterActivity.this.screenWidth) / 3, ((int) PosterActivity.this.screenHeight) / 3));
                    if (thumbPath != null) {
                        Log.e("ssssssss", "" + PosterActivity.this.frame_Name + " ," + PosterActivity.this.ratio + " ," + PosterActivity.this.profile + " ," + PosterActivity.this.temp_path);
                        TemplateInfo ti = new TemplateInfo();
                        ti.setTHUMB_URI(thumbPath);
                        ti.setFRAME_NAME(PosterActivity.this.frame_Name);
                        ti.setRATIO(PosterActivity.this.ratio);
                        ti.setPROFILE_TYPE(PosterActivity.this.profile);
                        ti.setSEEK_VALUE(String.valueOf(PosterActivity.this.seekValue));
                        ti.setTYPE("USER");
                        ti.setTEMP_PATH(PosterActivity.this.temp_path);
                        ti.setTEMPCOLOR(PosterActivity.this.hex);
                        ti.setOVERLAY_NAME(PosterActivity.this.overlay_Name);
                        ti.setOVERLAY_OPACITY(PosterActivity.this.seek.getProgress());
                        ti.setOVERLAY_BLUR(PosterActivity.this.seek_blur.getProgress());
                        dh = DatabaseHandler.getDbHandler(PosterActivity.this.getApplicationContext());
                        PosterActivity.this.saveComponent1(dh.insertTemplateRow(ti), dh);
                        PosterActivity.isUpdated = true;
                    }
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
        ringProgressDialog.setOnDismissListener(new OnDismissListener() {
            public void onDismiss(DialogInterface dialog) {
                final Dialog dialog1 = new Dialog(PosterActivity.this, 16974126);
                dialog1.requestWindowFeature(1);
                dialog1.setCancelable(true);
                dialog1.setContentView(R.layout.save_success_dialog);
                ((TextView) dialog1.findViewById(R.id.heater)).setTypeface(PosterActivity.this.ttfHeader);
                ((TextView) dialog1.findViewById(R.id.txt_free)).setTypeface(PosterActivity.this.ttf);
                Button btn_ok = (Button) dialog1.findViewById(R.id.btn_ok);
                btn_ok.setTypeface(PosterActivity.this.ttf, 1);
                btn_ok.setOnClickListener(new OnClickListener() {
                    public void onClick(View v) {
                        if (!PosterActivity.this.temp_Type.equals("")) {
                            PosterActivity.this.editor.putBoolean("isChanged", true);
                            PosterActivity.this.editor.commit();
                        }
                        dialog1.dismiss();
                    }
                });
                dialog1.show();
            }
        });
    }

    private void saveComponent1(long templateId, DatabaseHandler dh) {
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = this.txt_stkr_rel.getChildAt(i);
            if (child instanceof AutofitTextRel) {
                TextInfo textInfo = ((AutofitTextRel) child).getTextInfo();
                textInfo.setTEMPLATE_ID((int) templateId);
                textInfo.setORDER(i);
                textInfo.setTYPE("TEXT");
                dh.insertTextRow(textInfo);
            } else {
                saveShapeAndSticker(templateId, i, TYPE_STICKER, dh);
            }
        }
    }

    private void saveComponent(long templateId, DatabaseHandler dh) {
        int childCount = this.shape_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            saveShapeAndSticker(templateId, i, TYPE_SHAPE, dh);
        }
    }

    public void saveShapeAndSticker(long templateId, int i, int type, DatabaseHandler dh) {
        ComponentInfo ci = ((ResizableStickerView) this.txt_stkr_rel.getChildAt(i)).getComponentInfo();
        ci.setTEMPLATE_ID((int) templateId);
        ci.setTYPE("STICKER");
        ci.setORDER(i);
        dh.insertComponentInfoRow(ci);
    }

    private void openTextActivity() {
        Intent i = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (this.txt_stkr_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 100));
        bundle.putInt("Y", (this.txt_stkr_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 100));
        bundle.putInt("wi", ImageUtils.dpToPx(this, 200));
        bundle.putInt("he", ImageUtils.dpToPx(this, 200));
        bundle.putString("text", "");
        bundle.putString("fontName", this.fontName);
        bundle.putInt("tColor", this.tColor);
        bundle.putInt("tAlpha", this.tAlpha);
        bundle.putInt("shadowColor", this.shadowColor);
        bundle.putInt("shadowProg", this.shadowProg);
        bundle.putString("bgDrawable", this.bgDrawable);
        bundle.putInt("bgColor", this.bgColor);
        bundle.putInt("bgAlpha", this.bgAlpha);
        bundle.putFloat("rotation", this.rotation);
        bundle.putString("view", "mosaic");
        i.putExtras(bundle);
        startActivityForResult(i, TEXT_ACTIVITY);
    }

    private void onTouchApply() {
        removeScroll();
        this.select_backgnd.setBackgroundResource(R.drawable.overlay);
        this.select_effect.setBackgroundResource(R.drawable.overlay);
        this.user_image.setBackgroundResource(R.drawable.overlay);
        this.add_text.setBackgroundResource(R.drawable.overlay);
        this.add_sticker.setBackgroundResource(R.drawable.overlay);
        if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
            this.lay_textEdit.startAnimation(this.animSlideDown);
            this.lay_textEdit.setVisibility(View.GONE);
        }
        if (this.showtailsSeek) {
            this.lay_handletails.setVisibility(View.VISIBLE);
        }
        removeImageViewControll();
    }

    public void setSelected(int id) {
        for (int i = 0; i < this.layArr.length; i++) {
            if (this.layArr[i].getId() == id) {
                this.layArr[i].setBackgroundResource(R.drawable.trans);
            } else {
                this.layArr[i].setBackgroundResource(R.drawable.overlay);
            }
        }
    }

    public void onSnapFilter(int position, int call_Value, String stkr_path) {
        this.lay_sticker.setVisibility(View.GONE);
        this.add_sticker.setBackgroundResource(R.drawable.trans);
        this.img_oK.setVisibility(View.VISIBLE);
        if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
            this.lay_textEdit.startAnimation(this.animSlideDown);
            this.lay_textEdit.setVisibility(View.GONE);
        }
        if (!stkr_path.equals("")) {
            this.color_Type = "colored";
            addSticker("", stkr_path, null);
        } else if (call_Value == 0) {
            setDrawable("colored", "a_" + String.valueOf(position + 1));
        } else if (call_Value == 1) {
            setDrawable("colored", "b_" + String.valueOf(position + 1));
        } else if (call_Value == 2) {
            setDrawable("colored", "c_" + String.valueOf(position + 1));
        } else if (call_Value == 3) {
            setDrawable("white", "d_" + String.valueOf(position + 1));
        } else if (call_Value == 4) {
            setDrawable("colored", "e_" + String.valueOf(position + 1));
        } else if (call_Value == 5) {
            setDrawable("colored", "f_" + String.valueOf(position + 1));
        } else if (call_Value == 6) {
            setDrawable("colored", "g_" + String.valueOf(position + 1));
        } else if (call_Value == 7) {
            setDrawable("colored", "h_" + String.valueOf(position + 1));
        } else if (call_Value == 8) {
            setDrawable("colored", "i_" + String.valueOf(position + 1));
        } else if (call_Value == 9) {
            setDrawable("colored", "j_" + String.valueOf(position + 1));
        } else if (call_Value == 10) {
            setDrawable("colored", "k_" + String.valueOf(position + 1));
        } else if (call_Value == 11) {
            setDrawable("colored", "l_" + String.valueOf(position + 1));
        } else if (call_Value == 12) {
            setDrawable("colored", "m_" + String.valueOf(position + 1));
        } else if (call_Value == 13) {
            setDrawable("colored", "n_" + String.valueOf(position + 1));
        } else if (call_Value == 14) {
            setDrawable("colored", "o_" + String.valueOf(position + 1));
        } else if (call_Value == 15) {
            setDrawable("colored", "p_" + String.valueOf(position + 1));
        } else if (call_Value == 16) {
            setDrawable("colored", "q_" + String.valueOf(position + 1));
        } else if (call_Value == 17) {
            setDrawable("colored", "r_" + String.valueOf(position + 1));
        } else if (call_Value == 18) {
            setDrawable("colored", "s_" + String.valueOf(position + 1));
        } else if (call_Value == 19) {
            setDrawable("colored", "t_" + String.valueOf(position + 1));
        } else if (call_Value == 20) {
            setDrawable("white", "sh" + String.valueOf(position + 1));
        } else if (call_Value == 21) {
            setDrawable("white", "u_" + String.valueOf(position + 1));
        } else {
            this.color_Type = "colored";
        }
    }

    private void setDrawable(String sticker_color, String mDrawableName) {
        this.color_Type = sticker_color;
        if (sticker_color.equals("white")) {
            this.lay_color.setVisibility(View.VISIBLE);
            this.lay_hue.setVisibility(View.GONE);
        } else {
            this.lay_color.setVisibility(View.GONE);
            this.lay_hue.setVisibility(View.VISIBLE);
        }
        this.lay_effects.setVisibility(View.GONE);
        this.lay_textEdit.setVisibility(View.GONE);
        if (this.seekbar_container.getVisibility() == View.GONE) {
            this.seekbar_container.setVisibility(View.VISIBLE);
            this.seekbar_container.startAnimation(this.animSlideUp);
        }
        addSticker(mDrawableName, "", null);
    }

    private Bitmap viewToBitmap(View frameLayout) {
        Bitmap b = null;
        try {
            b = Bitmap.createBitmap(frameLayout.getWidth(), frameLayout.getHeight(), Config.ARGB_8888);
            frameLayout.draw(new Canvas(b));
            return b;
        } finally {
            frameLayout.destroyDrawingCache();
        }
    }

    private void saveBitmap(final boolean inPNG) {
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.plzwait));
        pd.setCancelable(false);
        pd.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/"+getString(R.string.app_name));
                    if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                        String photoFile = "Photo_" + System.currentTimeMillis();
                        if (inPNG) {
                            photoFile = photoFile + ".png";
                        } else {
                            photoFile = photoFile + ".jpg";
                        }
                        PosterActivity.this.filename = pictureFileDir.getPath() + File.separator + photoFile;
                        File pictureFile = new File(PosterActivity.this.filename);
                        try {
                            if (!pictureFile.exists()) {
                                pictureFile.createNewFile();
                            }
                            FileOutputStream ostream = new FileOutputStream(pictureFile);
                            if (inPNG) {
                                PosterActivity.this.checkMemory = PosterActivity.this.bitmap.compress(CompressFormat.PNG, 100, ostream);
                            } else {
                                Bitmap newBitmap = Bitmap.createBitmap(PosterActivity.this.bitmap.getWidth(), PosterActivity.this.bitmap.getHeight(), PosterActivity.this.bitmap.getConfig());
                                Canvas canvas = new Canvas(newBitmap);
                                canvas.drawColor(-1);
                                canvas.drawBitmap(PosterActivity.this.bitmap, 0.0f, 0.0f, null);
                                PosterActivity.this.checkMemory = newBitmap.compress(CompressFormat.JPEG, 100, ostream);
                                newBitmap.recycle();
                            }
                            ostream.flush();
                            ostream.close();
                            PosterActivity.isUpadted = true;
                            PosterActivity.this.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Thread.sleep(1000);
                        pd.dismiss();
                        return;
                    }
                    Log.d("", "Can't create directory to save image.");
                    Toast.makeText(PosterActivity.this.getApplicationContext(), PosterActivity.this.getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
                } catch (Exception e2) {
                }
            }
        }).start();
        pd.setOnDismissListener(new OnDismissListener() {

            class C03041 implements DialogInterface.OnClickListener {
                C03041() {
                }

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }

            public void onDismiss(DialogInterface dialog) {
                if (PosterActivity.this.checkMemory) {
                    Toast.makeText(PosterActivity.this.getApplicationContext(), PosterActivity.this.getString(R.string.saved).toString() + " " + PosterActivity.this.filename, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PosterActivity.this, ShareActivity.class);
                    intent.putExtra("uri", PosterActivity.this.filename);
                    intent.putExtra("way", "Poster");
                    PosterActivity.this.startActivity(intent);
                    return;
                }
                AlertDialog alertDialog = new AlertDialog.Builder(PosterActivity.this, 16974126).setMessage(Constants.getSpannableString(PosterActivity.this, Typeface.DEFAULT, R.string.memoryerror)).setPositiveButton(Constants.getSpannableString(PosterActivity.this, Typeface.DEFAULT, R.string.ok), new C03041()).create();
                alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
                alertDialog.show();
            }
        });
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        int i;
        switch (seekBar.getId()) {
            case R.id.alpha_seekBar:
                Log.e("seekbar", " : is_seekbar_4");
                int childCount = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount; i++) {
                    View view = this.txt_stkr_rel.getChildAt(i);
                    if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                        ((ResizableStickerView) view).setAlphaProg(progress);
                    }
                }
                return;
            case R.id.hue_seekBar:
                int childCount1 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount1; i++) {
                    View view1 = this.txt_stkr_rel.getChildAt(i);
                    if ((view1 instanceof ResizableStickerView) && ((ResizableStickerView) view1).getBorderVisbilty()) {
                        Log.e("seekbar", " : is_seekbar_5" + progress);
                        ((ResizableStickerView) view1).setHueProg_1(progress);
                    }
                }
                return;
            case R.id.seek:
                Log.e("seekbar", " : is_seekbar_3");
                this.alpha = progress;
                this.trans_img.setImageAlpha(this.alpha);
                return;
            case R.id.seekBar2:
                this.processs = progress;
                int childCount3 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount3; i++) {
                    View view3 = this.txt_stkr_rel.getChildAt(i);
                    if ((view3 instanceof AutofitTextRel) && ((AutofitTextRel) view3).getBorderVisibility()) {
                        ((AutofitTextRel) view3).setTextAlpha(progress);
                    }
                }
                return;
            case R.id.seekBar3:
                int childCount5 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount5; i++) {
                    View view5 = this.txt_stkr_rel.getChildAt(i);
                    if ((view5 instanceof AutofitTextRel) && ((AutofitTextRel) view5).getBorderVisibility()) {
                        ((AutofitTextRel) view5).setBgAlpha(progress);
                        this.bgAlpha = progress;
                    }
                }
                return;
            case R.id.seekBar_shadow:
                int childCount4 = this.txt_stkr_rel.getChildCount();
                for (i = 0; i < childCount4; i++) {
                    View view4 = this.txt_stkr_rel.getChildAt(i);
                    if ((view4 instanceof AutofitTextRel) && ((AutofitTextRel) view4).getBorderVisibility()) {
                        ((AutofitTextRel) view4).setTextShadowProg(progress);
                        this.shadowProg = progress;
                    }
                }
                return;
            case R.id.seek_blur:
                Log.e("seekbar", " : is_seekbar_2");
                if (progress != 0) {
                    this.background_blur.setVisibility(View.VISIBLE);
                    this.min = progress;
                    this.background_blur.setImageAlpha(progress);
                    return;
                }
                this.background_blur.setVisibility(View.GONE);
                return;
            case R.id.seek_tailys:
                Log.e("seekbar", " : is_seekbar_1");
                this.background_blur.setVisibility(View.GONE);
                this.seekValue = progress;
                addTilesBG(this.curTileId);
                return;
            default:
                return;
        }
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.seek_tailys:
                if (this.min != 0) {
                    this.background_blur.setVisibility(View.VISIBLE);
                } else {
                    this.background_blur.setVisibility(View.GONE);
                }
                new BlurOperationAsync(this, imgBtmap, this.background_blur).execute(new String[]{""});
                return;
            default:
                return;
        }
    }

    private void addTilesBG(int resId) {
        if (resId != 0) {
            setImageBitmapAndResizeLayout1(Constants.getTiledBitmap((Activity) this, resId, imgBtmap, this.seek_tailys));
        }
    }

    private void setImageBitmapAndResizeLayout1(Bitmap bit) {
        this.main_rel.getLayoutParams().width = bit.getWidth();
        this.main_rel.getLayoutParams().height = bit.getHeight();
        this.main_rel.postInvalidate();
        this.main_rel.requestLayout();
        this.background_img.setImageBitmap(bit);
        imgBtmap = bit;
    }

    public void onTouchDown(View v) {
        this.touchChange = false;
        touchDown(v);
    }

    public void onTouchUp(View v) {
        this.touchChange = false;
        touchUp(v);
    }

    public void onTouchMove(View v) {
        this.touchChange = false;
        touchMove(v);
    }

    private void touchDown(View v) {
        String[] font_Arr;
        int i;
        if (v == this.focusedView || this.touchChange) {
            this.focusedView = v;
            if (this.focusedView instanceof ResizableStickerView) {
                this.lay_effects.setVisibility(View.GONE);
                this.lay_textEdit.setVisibility(View.GONE);
                this.stkrColorSet = ((ResizableStickerView) this.focusedView).getColor();
                this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
            }
            if (this.focusedView instanceof AutofitTextRel) {
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.textColorSet = ((AutofitTextRel) this.focusedView).getTextColor();
                this.horizontalPicker.setSelectedColor(this.textColorSet);
                this.fontName = ((AutofitTextRel) this.focusedView).getFontName();
                this.tColor = ((AutofitTextRel) this.focusedView).getTextColor();
                this.shadowColor = ((AutofitTextRel) this.focusedView).getTextShadowColor();
                this.shadowProg = ((AutofitTextRel) this.focusedView).getTextShadowProg();
                this.tAlpha = ((AutofitTextRel) this.focusedView).getTextAlpha();
                this.bgDrawable = ((AutofitTextRel) this.focusedView).getBgDrawable();
                this.bgAlpha = ((AutofitTextRel) this.focusedView).getBgAlpha();
                this.rotation = this.focusedView.getRotation();
                this.bgColor = ((AutofitTextRel) this.focusedView).getBgColor();
                font_Arr = getResources().getStringArray(R.array.fonts_array);
                for (i = 0; i < font_Arr.length; i++) {
                    if (font_Arr[i].equals(this.fontName)) {
                        this.adapter.setSelected(i);
                    }
                }
            }
        } else {
            removeImageViewControll();
            this.focusedView = v;
            if (this.focusedView instanceof ResizableStickerView) {
                this.lay_effects.setVisibility(View.GONE);
                this.lay_textEdit.setVisibility(View.GONE);
                this.stkrColorSet = ((ResizableStickerView) this.focusedView).getColor();
                this.horizontalPickerColor.setSelectedColor(this.stkrColorSet);
            }
            if (this.focusedView instanceof AutofitTextRel) {
                this.lay_effects.setVisibility(View.GONE);
                this.seekbar_container.setVisibility(View.GONE);
                this.textColorSet = ((AutofitTextRel) this.focusedView).getTextColor();
                this.horizontalPicker.setSelectedColor(this.textColorSet);
                this.fontName = ((AutofitTextRel) this.focusedView).getFontName();
                this.tColor = ((AutofitTextRel) this.focusedView).getTextColor();
                this.shadowColor = ((AutofitTextRel) this.focusedView).getTextShadowColor();
                this.shadowProg = ((AutofitTextRel) this.focusedView).getTextShadowProg();
                this.tAlpha = ((AutofitTextRel) this.focusedView).getTextAlpha();
                this.bgDrawable = ((AutofitTextRel) this.focusedView).getBgDrawable();
                this.bgAlpha = ((AutofitTextRel) this.focusedView).getBgAlpha();
                this.rotation = this.focusedView.getRotation();
                this.bgColor = ((AutofitTextRel) this.focusedView).getBgColor();
                font_Arr = getResources().getStringArray(R.array.fonts_array);
                for (i = 0; i < font_Arr.length; i++) {
                    if (font_Arr[i].equals(this.fontName)) {
                        this.adapter.setSelected(i);
                    }
                }
            }
        }
        if (this.guideline.getVisibility() == View.GONE) {
            this.guideline.setVisibility(View.VISIBLE);
        }
    }

    private void touchMove(View v) {
        if (v != this.focusedView) {
            if (this.focusedView instanceof ResizableStickerView) {
                this.alphaSeekbar.setProgress(((ResizableStickerView) this.focusedView).getAlphaProg());
                this.hueSeekbar.setProgress(((ResizableStickerView) this.focusedView).getHueProg());
            } else {
                this.seekbar_container.setVisibility(View.GONE);
            }
        }
        if (this.focusedView instanceof ResizableStickerView) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_textEdit.setVisibility(View.GONE);
            this.seekbar_container.setVisibility(View.GONE);
            removeScroll();
        }
        if (this.focusedView instanceof AutofitTextRel) {
            this.lay_effects.setVisibility(View.GONE);
            this.lay_textEdit.setVisibility(View.GONE);
            this.seekbar_container.setVisibility(View.GONE);
            removeScroll();
        }
    }

    private void touchUp(final View v) {
        if (this.focusedView instanceof AutofitTextRel) {
            if (this.lay_textEdit.getVisibility() == View.GONE) {
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.trans);
                this.add_sticker.setBackgroundResource(R.drawable.overlay);
                this.lay_textEdit.setVisibility(View.VISIBLE);
                this.lay_textEdit.startAnimation(this.animSlideUp);
                this.lay_textEdit.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.textScrollView(v);
                    }
                });
            }
            if (this.processs != 0) {
                this.verticalSeekBar.setProgress(this.processs);
            }
        }
        if (this.focusedView instanceof ResizableStickerView) {
            if (("" + ((ResizableStickerView) this.focusedView).getColorType()).equals("white")) {
                this.lay_color.setVisibility(View.VISIBLE);
                this.lay_hue.setVisibility(View.GONE);
            } else {
                this.lay_color.setVisibility(View.GONE);
                this.lay_hue.setVisibility(View.VISIBLE);
            }
            if (this.seekbar_container.getVisibility() == View.GONE) {
                this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                this.select_effect.setBackgroundResource(R.drawable.overlay);
                this.user_image.setBackgroundResource(R.drawable.overlay);
                this.add_text.setBackgroundResource(R.drawable.overlay);
                this.add_sticker.setBackgroundResource(R.drawable.trans);
                this.seekbar_container.setVisibility(View.VISIBLE);
                this.seekbar_container.startAnimation(this.animSlideUp);
                this.seekbar_container.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.stickerScrollView(v);
                    }
                });
            }
        }
        if (this.guideline.getVisibility() == View.VISIBLE) {
            this.guideline.setVisibility(View.GONE);
        }
    }

    public void onDelete() {
        this.touchChange = false;
        removeScroll();
        if (this.seekbar_container.getVisibility() == View.VISIBLE) {
            this.seekbar_container.startAnimation(this.animSlideDown);
            this.seekbar_container.setVisibility(View.GONE);
        }
        if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
            this.lay_textEdit.startAnimation(this.animSlideDown);
            this.lay_textEdit.setVisibility(View.GONE);
        }
        this.select_backgnd.setBackgroundResource(R.drawable.overlay);
        this.select_effect.setBackgroundResource(R.drawable.overlay);
        this.user_image.setBackgroundResource(R.drawable.overlay);
        this.add_text.setBackgroundResource(R.drawable.overlay);
        this.add_sticker.setBackgroundResource(R.drawable.overlay);
        this.guideline.setVisibility(View.GONE);
    }

    public void onRotateDown(View v) {
        if (this.touchChange) {
            touchDown(v);
        } else {
            touchDown(this.focusedView);
        }
    }

    public void onRotateMove(View v) {
        if (this.touchChange) {
            touchMove(v);
        } else {
            touchMove(this.focusedView);
        }
    }

    public void onRotateUp(View v) {
        if (this.touchChange) {
            touchUp(v);
        } else {
            touchUp(this.focusedView);
        }
    }

    public void onScaleDown(View v) {
        if (this.touchChange) {
            touchDown(v);
        } else {
            touchDown(this.focusedView);
        }
    }

    public void onScaleMove(View v) {
        if (this.touchChange) {
            touchMove(v);
        } else {
            touchMove(this.focusedView);
        }
    }

    public void onScaleUp(View v) {
        if (this.touchChange) {
            touchUp(v);
        } else {
            touchUp(this.focusedView);
        }
    }

    public void onEdit(View v, Uri uri) {
    }

    public void onDoubleTap() {
        doubleTabPrass();
    }

    private void doubleTabPrass() {
        removeImageViewControll();
        this.editMode = true;
        TextInfo t = ((AutofitTextRel) this.txt_stkr_rel.getChildAt(this.txt_stkr_rel.getChildCount() - 1)).getTextInfo();
        Intent i = new Intent(this, TextActivity.class);
        Bundle bundle = new Bundle();
        bundle.putInt("X", (int) t.getPOS_X());
        bundle.putInt("Y", (int) t.getPOS_Y());
        bundle.putInt("wi", t.getWIDTH());
        bundle.putInt("he", t.getHEIGHT());
        bundle.putString("text", t.getTEXT());
        bundle.putString("fontName", t.getFONT_NAME());
        bundle.putInt("tColor", t.getTEXT_COLOR());
        bundle.putInt("tAlpha", t.getTEXT_ALPHA());
        bundle.putInt("shadowColor", t.getSHADOW_COLOR());
        bundle.putInt("shadowProg", t.getSHADOW_PROG());
        bundle.putString("bgDrawable", t.getBG_DRAWABLE());
        bundle.putInt("bgColor", t.getBG_COLOR());
        bundle.putInt("bgAlpha", t.getBG_ALPHA());
        bundle.putFloat("rotation", t.getROTATION());
        i.putExtras(bundle);
        startActivityForResult(i, TEXT_ACTIVITY);
    }

    public void stickerScrollView(View v) {
        if (v != null) {
            int vW = v.getWidth();
            int vH = v.getHeight();
            this.distance = this.parentY - ((float) ImageUtils.dpToPx(this, 50));
            int[] los = new int[2];
            v.getLocationOnScreen(los);
            float bY = (float) (los[1] + vH);
            int[] los_edittext_lay = new int[2];
            this.seekbar_container.getLocationOnScreen(los_edittext_lay);
            float pY = (float) los_edittext_lay[1];
            if (this.parentY + ((float) this.lay_scroll.getHeight()) < bY) {
                bY = this.parentY + ((float) this.lay_scroll.getHeight());
            }
            if (bY > pY) {
                this.distanceScroll = (int) (bY - pY);
                this.dsfc = this.distanceScroll;
                if (((float) this.distanceScroll) < this.distance) {
                    this.lay_scroll.setY((this.parentY - ((float) ImageUtils.dpToPx(this, 50))) - ((float) this.distanceScroll));
                } else {
                    this.lay_scroll.setLayoutParams(new LayoutParams(-1, -2));
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                    this.distanceScroll = (int) ((bY - this.distance) - pY);
                    this.lay_scroll.getLayoutParams().height = this.lay_scroll.getHeight() - this.distanceScroll;
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                }
                this.lay_scroll.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.btn_bck1.performClick();
                    }
                });
            }
        }
    }

    public void textScrollView(View v) {
        if (v != null) {
            int vW = v.getWidth();
            int vH = v.getHeight();
            this.distance = this.parentY - ((float) ImageUtils.dpToPx(this, 50));
            int[] los = new int[2];
            v.getLocationOnScreen(los);
            float bY = (float) (los[1] + vH);
            int[] los_edittext_lay = new int[2];
            this.lay_textEdit.getLocationOnScreen(los_edittext_lay);
            float pY = (float) los_edittext_lay[1];
            if (this.parentY + ((float) this.lay_scroll.getHeight()) < bY) {
                bY = this.parentY + ((float) this.lay_scroll.getHeight());
            }
            if (bY > pY) {
                this.distanceScroll = (int) (bY - pY);
                this.dsfc = this.distanceScroll;
                if (((float) this.distanceScroll) < this.distance) {
                    this.lay_scroll.setY((this.parentY - ((float) ImageUtils.dpToPx(this, 50))) - ((float) this.distanceScroll));
                } else {
                    this.lay_scroll.setLayoutParams(new LayoutParams(-1, -2));
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                    this.distanceScroll = (int) ((bY - this.distance) - pY);
                    this.lay_scroll.getLayoutParams().height = this.lay_scroll.getHeight() - this.distanceScroll;
                    this.lay_scroll.postInvalidate();
                    this.lay_scroll.requestLayout();
                }
                this.lay_scroll.post(new Runnable() {
                    public void run() {
                        PosterActivity.this.btn_bck1.performClick();
                    }
                });
            }
        }
    }

    private void removeScroll() {
        if (((float) this.dsfc) < this.distance) {
            this.lay_scroll.setY(this.parentY - ((float) ImageUtils.dpToPx(this, 50)));
        }
        LayoutParams params = new LayoutParams(-1, -2);
        params.addRule(13);
        this.lay_scroll.setLayoutParams(params);
        this.lay_scroll.postInvalidate();
        this.lay_scroll.requestLayout();
    }

    private void addSticker(String resId, String str_path, Bitmap btm) {
        ComponentInfo ci = new ComponentInfo();
        ci.setPOS_X((float) ((this.main_rel.getWidth() / 2) - ImageUtils.dpToPx(this, 70)));
        ci.setPOS_Y((float) ((this.main_rel.getHeight() / 2) - ImageUtils.dpToPx(this, 70)));
        ci.setWIDTH(ImageUtils.dpToPx(this, 140));
        ci.setHEIGHT(ImageUtils.dpToPx(this, 140));
        ci.setROTATION(0.0f);
        ci.setRES_ID(resId);
        ci.setBITMAP(btm);
        ci.setCOLORTYPE(this.color_Type);
        ci.setTYPE("STICKER");
        ci.setSTC_OPACITY(100);
        ci.setSTC_COLOR(0);
        ci.setSTKR_PATH(str_path);
        ci.setFIELD_TWO("0,0");
        ResizableStickerView riv = new ResizableStickerView(this);
        riv.optimizeScreen(this.screenWidth, this.screenHeight);
        riv.setComponentInfo(ci);
        this.txt_stkr_rel.addView(riv);
        riv.setOnTouchCallbackListener(this);
        riv.setBorderVisibility(true);
    }

    public void removeImageViewControll() {
        this.guideline.setVisibility(View.GONE);
        this.lay_effects.setVisibility(View.GONE);
        int childCount = this.txt_stkr_rel.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View view = this.txt_stkr_rel.getChildAt(i);
            if (view instanceof AutofitTextRel) {
                ((AutofitTextRel) view).setBorderVisibility(false);
            }
            if (view instanceof ResizableStickerView) {
                ((ResizableStickerView) view).setBorderVisibility(false);
            }
        }
    }

    private BitmapDataObject getBitmapDataObject(String path) {
        try {
            return (BitmapDataObject) new ObjectInputStream(new FileInputStream(new File(path))).readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
        }
        return null;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1) {
            this.seekbar_container.setVisibility(View.GONE);
            if (data != null || requestCode == SELECT_PICTURE_FROM_CAMERA || requestCode == 4 || requestCode == TEXT_ACTIVITY) {
                Bundle bundle;
                Intent _main;
                if (requestCode == TEXT_ACTIVITY) {
                    bundle = data.getExtras();
                    TextInfo textInfo = new TextInfo();
                    textInfo.setPOS_X((float) bundle.getInt("X", 0));
                    textInfo.setPOS_Y((float) bundle.getInt("Y", 0));
                    textInfo.setWIDTH(bundle.getInt("wi", ImageUtils.dpToPx(this, 200)));
                    textInfo.setHEIGHT(bundle.getInt("he", ImageUtils.dpToPx(this, 200)));
                    textInfo.setTEXT(bundle.getString("text", ""));
                    textInfo.setFONT_NAME(bundle.getString("fontName", ""));
                    textInfo.setTEXT_COLOR(bundle.getInt("tColor", Color.parseColor("#4149b6")));
                    textInfo.setTEXT_ALPHA(bundle.getInt("tAlpha", 100));
                    textInfo.setSHADOW_COLOR(bundle.getInt("shadowColor", Color.parseColor("#7641b6")));
                    textInfo.setSHADOW_PROG(bundle.getInt("shadowProg", 5));
                    textInfo.setBG_COLOR(bundle.getInt("bgColor", 0));
                    textInfo.setBG_DRAWABLE(bundle.getString("bgDrawable", "0"));
                    textInfo.setBG_ALPHA(bundle.getInt("bgAlpha", 255));
                    textInfo.setROTATION(bundle.getFloat("rotation", 0.0f));
                    textInfo.setFIELD_TWO(bundle.getString("field_two", ""));
                    this.fontName = bundle.getString("fontName", "");
                    this.tColor = bundle.getInt("tColor", Color.parseColor("#4149b6"));
                    this.shadowColor = bundle.getInt("shadowColor", Color.parseColor("#7641b6"));
                    this.shadowProg = bundle.getInt("shadowProg", 0);
                    this.tAlpha = bundle.getInt("tAlpha", 100);
                    this.bgDrawable = bundle.getString("bgDrawable", "0");
                    this.bgAlpha = bundle.getInt("bgAlpha", 255);
                    this.rotation = bundle.getFloat("rotation", 0.0f);
                    this.bgColor = bundle.getInt("bgColor", 0);
                    if (this.editMode) {
                        this.touchChange = false;
                        ((AutofitTextRel) this.txt_stkr_rel.getChildAt(this.txt_stkr_rel.getChildCount() - 1)).setTextInfo(textInfo, false);
                        ((AutofitTextRel) this.txt_stkr_rel.getChildAt(this.txt_stkr_rel.getChildCount() - 1)).setBorderVisibility(true);
                        this.editMode = false;
                    } else {
                        this.touchChange = true;
                        AutofitTextRel rl = new AutofitTextRel(this);
                        this.txt_stkr_rel.addView(rl);
                        rl.setTextInfo(textInfo, false);
                        rl.setOnTouchCallbackListener(this);
                        rl.setBorderVisibility(true);
                    }
                    if (this.lay_textEdit.getVisibility() == View.GONE) {
                        this.select_backgnd.setBackgroundResource(R.drawable.overlay);
                        this.select_effect.setBackgroundResource(R.drawable.overlay);
                        this.add_sticker.setBackgroundResource(R.drawable.overlay);
                        this.user_image.setBackgroundResource(R.drawable.overlay);
                        this.add_text.setBackgroundResource(R.drawable.trans);
                        this.lay_textEdit.setVisibility(View.VISIBLE);
                        this.lay_textEdit.startAnimation(this.animSlideUp);
                    }
                }
                if (requestCode == SELECT_PICTURE_FROM_GALLERY) {
                    try {
                        btmSticker = ImageUtils.resizeBitmap(Constants.getBitmapFromUri(this, data.getData(), this.screenWidth, this.screenHeight), (int) this.screenWidth, (int) this.screenWidth);
                        _main = new Intent(this, CropActivityTwo.class);
                        _main.putExtra("value", "sticker");
                        startActivity(_main);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (requestCode == SELECT_PICTURE_FROM_CAMERA) {
                    try {
                        btmSticker = ImageUtils.resizeBitmap(Constants.getBitmapFromUri(this, Uri.fromFile(this.f25f), this.screenWidth, this.screenHeight), (int) this.screenWidth, (int) this.screenWidth);
                        _main = new Intent(this, CropActivityTwo.class);
                        _main.putExtra("value", "sticker");
                        startActivity(_main);
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
                if (requestCode == 4) {
                    bundle = data.getExtras();
                    this.profile = bundle.getString(Scopes.PROFILE);
                    if (this.profile.equals("no")) {
                        this.showtailsSeek = false;
                        this.lay_handletails.setVisibility(View.GONE);
                        this.ratio = "";
                        this.position = "1";
                        this.profile = "Temp_Path";
                        this.hex = "";
                        setImageBitmapAndResizeLayout(ImageUtils.resizeBitmap(CropActivityTwo.bitmapImage, (int) this.screenWidth, (int) this.screenHeight), "nonCreated");
                        return;
                    }
                    if (this.profile.equals("Texture")) {
                        this.showtailsSeek = true;
                        this.lay_handletails.setVisibility(View.VISIBLE);
                    } else {
                        this.showtailsSeek = false;
                        this.lay_handletails.setVisibility(View.GONE);
                    }
                    this.ratio = bundle.getString("ratio");
                    String position = bundle.getString("position");
                    this.hex = bundle.getString("color");
                    drawBackgroundImage(this.ratio, position, this.profile, "nonCreated");
                    return;
                }
                return;
            }
            AlertDialog alertDialog = new AlertDialog.Builder(this, 16974126).setMessage(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.picUpImg)).setPositiveButton(Constants.getSpannableString(this, Typeface.DEFAULT, R.string.ok), new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            }).create();
            alertDialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
            alertDialog.show();
        } else if (requestCode == TEXT_ACTIVITY) {
            this.editMode = false;
        }
    }

    private void showDialogPicker() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(1);
        dialog.setContentView(R.layout.dialog);
        ((TextView) dialog.findViewById(R.id.txt_title)).setTypeface(Constants.getHeaderTypeface(this));
        ((ImageButton) dialog.findViewById(R.id.img_camera)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PosterActivity.this.onCameraButtonClick();
                dialog.dismiss();
            }
        });
        ((ImageButton) dialog.findViewById(R.id.img_gallery)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PosterActivity.this.onGalleryButtonClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void onCameraButtonClick() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        this.f25f = new File(Environment.getExternalStorageDirectory(), ".temp.jpg");
        intent.putExtra("output", Uri.fromFile(this.f25f));
        startActivityForResult(intent, SELECT_PICTURE_FROM_CAMERA);
    }

    public void onGalleryButtonClick() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction("android.intent.action.PICK");
        startActivityForResult(Intent.createChooser(intent, getResources().getString(R.string.select_picture)), SELECT_PICTURE_FROM_GALLERY);
    }

    public void onBackPressed() {
        if (this.lay_textEdit.getVisibility() == View.VISIBLE) {
            this.lay_textEdit.startAnimation(this.animSlideDown);
            this.lay_textEdit.setVisibility(View.GONE);
            removeScroll();
        } else if (this.lay_sticker.getVisibility() == View.VISIBLE) {
            this.lay_sticker.setVisibility(View.GONE);
            this.add_sticker.setBackgroundResource(R.drawable.trans);
            this.img_oK.setVisibility(View.VISIBLE);
        } else if (this.seekbar_container.getVisibility() == View.VISIBLE) {
            this.seekbar_container.startAnimation(this.animSlideDown);
            this.seekbar_container.setVisibility(View.GONE);
            removeScroll();
        } else if (this.lay_effects.getVisibility() == View.VISIBLE) {
            this.lay_effects.startAnimation(this.animSlideDown);
            this.lay_effects.setVisibility(View.GONE);
        } else {
            final Dialog dialog = new Dialog(this, 16974126);
            dialog.requestWindowFeature(1);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.leave_dialog);
            ((TextView) dialog.findViewById(R.id.txtapp)).setTypeface(this.ttfHeader);
            ((TextView) dialog.findViewById(R.id.txt_free)).setTypeface(this.ttf);
            ((Button) dialog.findViewById(R.id.btn_yes)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    PosterActivity.this.finish();
                    dialog.dismiss();
                }
            });
            ((Button) dialog.findViewById(R.id.btn_no)).setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
        }
        super.onBackPressed();
    }

    public void ongetSticker() {
        this.color_Type = "colored";
        addSticker("", "", CropActivityTwo.bitmapImage);
    }


    public void onColor(int position, String way) {
        if (position != 0) {
            int childCount = this.txt_stkr_rel.getChildCount();
            int i;
            View view;
            if (way.equals("txtShadow")) {
                for (i = 0; i < childCount; i++) {
                    view = this.txt_stkr_rel.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextShadowColor(position);
                    }
                }
            } else if (way.equals("txtBg")) {
                for (i = 0; i < childCount; i++) {
                    view = this.txt_stkr_rel.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setBgColor(position);
                        ((AutofitTextRel) view).setBgAlpha(this.seekBar3.getProgress());
                    }
                }
            } else {
                for (i = 0; i < childCount; i++) {
                    view = this.txt_stkr_rel.getChildAt(i);
                    if ((view instanceof AutofitTextRel) && ((AutofitTextRel) view).getBorderVisibility()) {
                        ((AutofitTextRel) view).setTextColor(position);
                    }
                    if ((view instanceof ResizableStickerView) && ((ResizableStickerView) view).getBorderVisbilty()) {
                        ((ResizableStickerView) view).setColor(position);
                    }
                }
            }
        }
    }

    private void showDialogSave() {
        final Dialog dialog = new Dialog(this, 16974126);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.save_dialog);
        ((TextView) dialog.findViewById(R.id.txtapp)).setTypeface(this.ttfHeader);
        ((TextView) dialog.findViewById(R.id.txt)).setTypeface(this.ttf);
        ((TextView) dialog.findViewById(R.id.txt1)).setTypeface(this.ttf);
        Button dialogButton = (Button) dialog.findViewById(R.id.btn_template);
        dialogButton.setTypeface(this.ttf, Typeface.BOLD);
        dialogButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PosterActivity.this.createFrame();
                dialog.dismiss();
            }
        });
        Button btn_yes = (Button) dialog.findViewById(R.id.btn_image);
        btn_yes.setTypeface(this.ttf, Typeface.BOLD);
        btn_yes.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PosterActivity.this.bitmap = PosterActivity.this.viewToBitmap(PosterActivity.this.main_rel);
                PosterActivity.this.logo_ll.setVisibility(View.VISIBLE);
                PosterActivity.this.logo_ll.setDrawingCacheEnabled(true);
                Bitmap logo = Bitmap.createBitmap(PosterActivity.this.logo_ll.getDrawingCache());
                PosterActivity.this.logo_ll.setDrawingCacheEnabled(false);
                PosterActivity.this.logo_ll.setVisibility(View.INVISIBLE);
                PosterActivity.withoutWatermark = PosterActivity.this.bitmap;
//                if (!(PosterActivity.this.preferences.getBoolean("isAdsDisabled", false) || PosterActivity.this.preferences.getBoolean("removeWatermark", false))) {
//                    PosterActivity.this.bitmap = ImageUtils.mergelogo(PosterActivity.this.bitmap, logo);
//                }
                PosterActivity.this.saveBitmap(true);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void errorDialogTempInfo() {
        final Dialog dialog = new Dialog(this, 16974126);
        dialog.requestWindowFeature(1);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.error_dialog);
        ((TextView) dialog.findViewById(R.id.txtapp)).setTypeface(this.ttfHeader);
        ((TextView) dialog.findViewById(R.id.txt)).setTypeface(this.ttf);
        Button btn_ok = (Button) dialog.findViewById(R.id.btn_ok);
        btn_ok.setTypeface(this.ttf);
        btn_ok.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                PosterActivity.this.finish();
            }
        });
        Button btn_conti = (Button) dialog.findViewById(R.id.btn_conti);
        btn_conti.setTypeface(this.ttf);
        btn_conti.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private Bitmap gaussinBlur(Activity activity, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(activity);
        GPUImageGaussianBlurFilter sepiaFilter5 = new GPUImageGaussianBlurFilter();
        gpuImage.setFilter(sepiaFilter5);
        new FilterAdjuster(sepiaFilter5).adjust(100);
        gpuImage.requestRender();
        return gpuImage.getBitmapWithFilterApplied(bitmap);
    }
}
