package ab.cd.ef.postermaker.main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.Shader.TileMode;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Environment;
import android.os.ParcelFileDescriptor;

import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.core.view.ViewCompat;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.R_D;
import ab.cd.ef.postermaker.create.BitmapDataObject;
import ab.cd.ef.postermaker.utility.ExifUtils;
import ab.cd.ef.postermaker.utility.ImageUtils;

public class Constants {
    public static int[] Imageid0 = new int[]{R.drawable.b1, R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5, R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9, R.drawable.b10, R.drawable.b11, R.drawable.b12, R.drawable.b13, R.drawable.b14, R.drawable.b15, R.drawable.b16, R.drawable.b17, R.drawable.b18, R.drawable.b19, R.drawable.b20, R.drawable.b21, R.drawable.b22, R.drawable.b23, R.drawable.b24, R.drawable.b25, R.drawable.b26, R.drawable.b27, R.drawable.b28, R.drawable.b29, R.drawable.b30, R.drawable.b31, R.drawable.b32, R.drawable.b33, R.drawable.b34, R.drawable.b35, R.drawable.b36, R.drawable.b37, R.drawable.b38, R.drawable.b39, R.drawable.b40, R.drawable.b41, R.drawable.b42, R.drawable.b43, R.drawable.b44, R.drawable.b45, R.drawable.b46, R.drawable.b47, R.drawable.b48, R.drawable.b49, R.drawable.b50, R.drawable.b51, R.drawable.b52, R.drawable.b53, R.drawable.b54, R.drawable.b55, R.drawable.b56, R.drawable.b57, R.drawable.b58, R.drawable.b59, R.drawable.b60};
    public static int[] Imageid1 = new int[]{R.drawable.t1, R.drawable.t2, R.drawable.t3, R.drawable.t4, R.drawable.t5, R.drawable.t6, R.drawable.t7, R.drawable.t8, R.drawable.t9, R.drawable.t10, R.drawable.t11, R.drawable.t12, R.drawable.t13, R.drawable.t14, R.drawable.t15, R.drawable.t16, R.drawable.t17, R.drawable.t18, R.drawable.t19, R.drawable.t20, R.drawable.t21, R.drawable.t22, R.drawable.t23, R.drawable.t24, R.drawable.t25, R.drawable.t26, R.drawable.t27, R.drawable.t28, R.drawable.t29, R.drawable.t30, R.drawable.t31, R.drawable.t32, R.drawable.t33, R.drawable.t34, R.drawable.t35, R.drawable.t36, R.drawable.t37, R.drawable.t38, R.drawable.t39, R.drawable.t40, R.drawable.t41, R.drawable.t42, R.drawable.t43, R.drawable.t44, R.drawable.t45, R.drawable.t46, R.drawable.t47, R.drawable.t48, R.drawable.t49, R.drawable.t50, R.drawable.t51, R.drawable.t52, R.drawable.t53, R.drawable.t54, R.drawable.t55, R.drawable.t56, R.drawable.t57, R.drawable.t58, R.drawable.t59, R.drawable.t60};
    public static int[] Imageid_st1 = new int[]{R.drawable.a_1, R.drawable.a_2, R.drawable.a_3, R.drawable.a_4, R.drawable.a_5, R.drawable.a_6, R.drawable.a_7, R.drawable.a_8, R.drawable.a_9, R.drawable.a_10, R.drawable.a_11, R.drawable.a_12, R.drawable.a_13, R.drawable.a_14, R.drawable.a_15, R.drawable.a_16, R.drawable.a_17, R.drawable.a_18, R.drawable.a_19, R.drawable.a_20, R.drawable.a_21, R.drawable.a_22, R.drawable.a_23, R.drawable.a_24, R.drawable.a_25};
    public static int[] Imageid_st10 = new int[]{R.drawable.j_1, R.drawable.j_2, R.drawable.j_3, R.drawable.j_4, R.drawable.j_5, R.drawable.j_6, R.drawable.j_7, R.drawable.j_8, R.drawable.j_9, R.drawable.j_10, R.drawable.j_11, R.drawable.j_12, R.drawable.j_13, R.drawable.j_14};
    public static int[] Imageid_st11 = new int[]{R.drawable.k_1, R.drawable.k_2, R.drawable.k_3, R.drawable.k_4, R.drawable.k_5, R.drawable.k_6, R.drawable.k_7, R.drawable.k_8, R.drawable.k_9, R.drawable.k_10, R.drawable.k_11, R.drawable.k_12, R.drawable.k_13, R.drawable.k_14, R.drawable.k_15, R.drawable.k_16, R.drawable.k_17, R.drawable.k_18, R.drawable.k_19, R.drawable.k_20, R.drawable.k_21, R.drawable.k_22, R.drawable.k_23, R.drawable.k_24, R.drawable.k_25, R.drawable.k_26, R.drawable.k_27, R.drawable.k_28, R.drawable.k_29, R.drawable.k_30, R.drawable.k_31, R.drawable.k_32, R.drawable.k_33, R.drawable.k_34, R.drawable.k_35, R.drawable.k_36, R.drawable.k_37};
    public static int[] Imageid_st12 = new int[]{R.drawable.l_1, R.drawable.l_2, R.drawable.l_3, R.drawable.l_4, R.drawable.l_5, R.drawable.l_6, R.drawable.l_7, R.drawable.l_8, R.drawable.l_9, R.drawable.l_10, R.drawable.l_11, R.drawable.l_12};
    public static int[] Imageid_st13 = new int[]{R.drawable.m_1, R.drawable.m_2, R.drawable.m_3, R.drawable.m_4, R.drawable.m_5, R.drawable.m_6, R.drawable.m_7, R.drawable.m_8, R.drawable.m_9, R.drawable.m_10, R.drawable.m_11, R.drawable.m_12, R.drawable.m_13, R.drawable.m_14, R.drawable.m_15, R.drawable.m_16, R.drawable.m_17};
    public static int[] Imageid_st14 = new int[]{R.drawable.n_1, R.drawable.n_2, R.drawable.n_3, R.drawable.n_4, R.drawable.n_5, R.drawable.n_6, R.drawable.n_7, R.drawable.n_8, R.drawable.n_9, R.drawable.n_10, R.drawable.n_11};
    public static int[] Imageid_st15 = new int[]{R.drawable.o_1, R.drawable.o_2, R.drawable.o_3, R.drawable.o_4, R.drawable.o_5, R.drawable.o_6, R.drawable.o_7, R.drawable.o_8, R.drawable.o_9, R.drawable.o_10, R.drawable.o_11, R.drawable.o_12, R.drawable.o_13, R.drawable.o_14, R.drawable.o_15};
    public static int[] Imageid_st16 = new int[]{R.drawable.p_1, R.drawable.p_2, R.drawable.p_3, R.drawable.p_4, R.drawable.p_5, R.drawable.p_6, R.drawable.p_7, R.drawable.p_8, R.drawable.p_9, R.drawable.p_10, R.drawable.p_11, R.drawable.p_12, R.drawable.p_13, R.drawable.p_14, R.drawable.p_15, R.drawable.p_16};
    public static int[] Imageid_st17 = new int[]{R.drawable.q_1, R.drawable.q_2, R.drawable.q_3, R.drawable.q_4, R.drawable.q_5, R.drawable.q_6, R.drawable.q_7, R.drawable.q_8, R.drawable.q_9, R.drawable.q_10};
    public static int[] Imageid_st18 = new int[]{R.drawable.r_1, R.drawable.r_2, R.drawable.r_3, R.drawable.r_4, R.drawable.r_5, R.drawable.r_6, R.drawable.r_7, R.drawable.r_8, R.drawable.r_9, R.drawable.r_10, R.drawable.r_11, R.drawable.r_12, R.drawable.r_13, R.drawable.r_14, R.drawable.r_15, R.drawable.r_16, R.drawable.r_17, R.drawable.r_18, R.drawable.r_19, R.drawable.r_20, R.drawable.r_21, R.drawable.r_22, R.drawable.r_23, R.drawable.r_24, R.drawable.r_25, R.drawable.r_26, R.drawable.r_27};
    public static int[] Imageid_st19 = new int[]{R.drawable.s_1, R.drawable.s_2, R.drawable.s_3, R.drawable.s_4, R.drawable.s_5, R.drawable.s_6, R.drawable.s_7, R.drawable.s_8, R.drawable.s_9, R.drawable.s_10, R.drawable.s_11, R.drawable.s_12, R.drawable.s_13, R.drawable.s_14, R.drawable.s_15, R.drawable.s_16, R.drawable.s_17, R.drawable.s_18, R.drawable.s_19, R.drawable.s_20, R.drawable.s_21, R.drawable.s_22, R.drawable.s_23, R.drawable.s_24, R.drawable.s_25, R.drawable.s_26, R.drawable.s_27, R.drawable.s_28, R.drawable.s_29, R.drawable.s_30, R.drawable.s_31, R.drawable.s_32, R.drawable.s_33, R.drawable.s_34, R.drawable.s_35, R.drawable.s_36};
    public static int[] Imageid_st2 = new int[]{R.drawable.b_1, R.drawable.b_2, R.drawable.b_3, R.drawable.b_4, R.drawable.b_5, R.drawable.b_6, R.drawable.b_7, R.drawable.b_8, R.drawable.b_9, R.drawable.b_10, R.drawable.b_11, R.drawable.b_12, R.drawable.b_13, R.drawable.b_14, R.drawable.b_15, R.drawable.b_16, R.drawable.b_17, R.drawable.b_18, R.drawable.b_19, R.drawable.b_20, R.drawable.b_21, R.drawable.b_22, R.drawable.b_23, R.drawable.b_24, R.drawable.b_25};
    public static int[] Imageid_st20 = new int[]{R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32, R.drawable.t_33, R.drawable.t_34};
    public static int[] Imageid_st21 = new int[]{R.drawable.u_1, R.drawable.u_2, R.drawable.u_3, R.drawable.u_4, R.drawable.u_5, R.drawable.u_6, R.drawable.u_7, R.drawable.u_8, R.drawable.u_9, R.drawable.u_10, R.drawable.u_11, R.drawable.u_12, R.drawable.u_13, R.drawable.u_14, R.drawable.u_15, R.drawable.u_16, R.drawable.u_17, R.drawable.u_18, R.drawable.u_19, R.drawable.u_20, R.drawable.u_21, R.drawable.u_22, R.drawable.u_23, R.drawable.u_24, R.drawable.u_25, R.drawable.u_26, R.drawable.u_27, R.drawable.u_28, R.drawable.u_29, R.drawable.u_30, R.drawable.u_31, R.drawable.u_32, R.drawable.u_33, R.drawable.u_34, R.drawable.u_35, R.drawable.u_36, R.drawable.u_37, R.drawable.u_38, R.drawable.u_39, R.drawable.u_40, R.drawable.u_40, R.drawable.u_41, R.drawable.u_42};
    public static int[] Imageid_st22 = new int[]{R.drawable.t_1, R.drawable.t_2, R.drawable.t_3, R.drawable.t_4, R.drawable.t_5, R.drawable.t_6, R.drawable.t_7, R.drawable.t_8, R.drawable.t_9, R.drawable.t_10, R.drawable.t_11, R.drawable.t_12, R.drawable.t_13, R.drawable.t_14, R.drawable.t_15, R.drawable.t_16, R.drawable.t_17, R.drawable.t_18, R.drawable.t_19, R.drawable.t_20, R.drawable.t_21, R.drawable.t_22, R.drawable.t_23, R.drawable.t_24, R.drawable.t_25, R.drawable.t_26, R.drawable.t_27, R.drawable.t_28, R.drawable.t_29, R.drawable.t_30, R.drawable.t_31, R.drawable.t_32, R.drawable.t_33, R.drawable.t_34};
    public static int[] Imageid_st23 = new int[]{R.drawable.sh1, R.drawable.sh2, R.drawable.sh3, R.drawable.sh4, R.drawable.sh5, R.drawable.sh6, R.drawable.sh7, R.drawable.sh8, R.drawable.sh9, R.drawable.sh10, R.drawable.sh11, R.drawable.sh12, R.drawable.sh13, R.drawable.sh14, R.drawable.sh15, R.drawable.sh16, R.drawable.sh17, R.drawable.sh18, R.drawable.sh19, R.drawable.sh20, R.drawable.sh21, R.drawable.sh22, R.drawable.sh23, R.drawable.sh24, R.drawable.sh25, R.drawable.sh26, R.drawable.sh27, R.drawable.sh28, R.drawable.sh29, R.drawable.sh30, R.drawable.sh31, R.drawable.sh32, R.drawable.sh33, R.drawable.sh34, R.drawable.sh35, R.drawable.sh36, R.drawable.sh37, R.drawable.sh38, R.drawable.sh39, R.drawable.sh40, R.drawable.sh41, R.drawable.sh42};
    public static int[] Imageid_st3 = new int[]{R.drawable.c_1, R.drawable.c_2, R.drawable.c_3, R.drawable.c_4, R.drawable.c_5, R.drawable.c_6, R.drawable.c_7, R.drawable.c_8, R.drawable.c_9, R.drawable.c_10, R.drawable.c_11, R.drawable.c_12};
    public static int[] Imageid_st4 = new int[]{R.drawable.d_1, R.drawable.d_2, R.drawable.d_3, R.drawable.d_4, R.drawable.d_5, R.drawable.d_6, R.drawable.d_7, R.drawable.d_8, R.drawable.d_9, R.drawable.d_10, R.drawable.d_11, R.drawable.d_12, R.drawable.d_13, R.drawable.d_14, R.drawable.d_15, R.drawable.d_16, R.drawable.d_17, R.drawable.d_18};
    public static int[] Imageid_st5 = new int[]{R.drawable.e_1, R.drawable.e_2, R.drawable.e_3, R.drawable.e_4, R.drawable.e_5, R.drawable.e_6, R.drawable.e_7, R.drawable.e_8, R.drawable.e_9, R.drawable.e_10};
    public static int[] Imageid_st6 = new int[]{R.drawable.f_1, R.drawable.f_2, R.drawable.f_3, R.drawable.f_4, R.drawable.f_5, R.drawable.f_6, R.drawable.f_7, R.drawable.f_8, R.drawable.f_9, R.drawable.f_10, R.drawable.f_11, R.drawable.f_12, R.drawable.f_13, R.drawable.f_14, R.drawable.f_15, R.drawable.f_16, R.drawable.f_17, R.drawable.f_18, R.drawable.f_19, R.drawable.f_20, R.drawable.f_21, R.drawable.f_22, R.drawable.f_23, R.drawable.f_24, R.drawable.f_25, R.drawable.f_26};
    public static int[] Imageid_st7 = new int[]{R.drawable.g_1, R.drawable.g_2, R.drawable.g_3, R.drawable.g_4, R.drawable.g_5, R.drawable.g_6, R.drawable.g_7, R.drawable.g_8, R.drawable.g_9, R.drawable.g_10, R.drawable.g_11, R.drawable.g_12, R.drawable.g_13, R.drawable.g_14, R.drawable.g_15, R.drawable.g_16, R.drawable.g_17, R.drawable.g_18, R.drawable.g_19, R.drawable.g_20, R.drawable.g_21, R.drawable.g_22, R.drawable.g_23, R.drawable.g_24, R.drawable.g_25, R.drawable.g_26, R.drawable.g_27, R.drawable.g_28, R.drawable.g_29, R.drawable.g_30};
    public static int[] Imageid_st8 = new int[]{R.drawable.h_1, R.drawable.h_2, R.drawable.h_3, R.drawable.h_4, R.drawable.h_5, R.drawable.h_6, R.drawable.h_7, R.drawable.h_8, R.drawable.h_9, R.drawable.h_10, R.drawable.h_11, R.drawable.h_12, R.drawable.h_13};
    public static int[] Imageid_st9 = new int[]{R.drawable.i_1, R.drawable.i_2, R.drawable.i_3, R.drawable.i_4, R.drawable.i_5, R.drawable.i_6, R.drawable.i_7, R.drawable.i_8, R.drawable.i_9, R.drawable.i_10, R.drawable.i_11, R.drawable.i_12, R.drawable.i_13, R.drawable.i_14, R.drawable.i_15, R.drawable.i_16, R.drawable.i_17};
    private static int sh = 1920;
    private static int sw = 1080;

    public static Bitmap merge(Bitmap bitmap1, Bitmap a1, int alpha) {
        Bitmap bit = Bitmap.createBitmap(bitmap1.getWidth(), bitmap1.getHeight(), Config.ARGB_8888);
        Drawable[] layers = new Drawable[]{new BitmapDrawable(bitmap1), new BitmapDrawable(a1)};
        layers[1].setAlpha(alpha);
        LayerDrawable layerDrawable = new LayerDrawable(layers);
        Canvas c = new Canvas(bit);
        layerDrawable.setBounds(0, 0, c.getWidth(), c.getHeight());
        layerDrawable.draw(c);
        return bit;
    }

    public static CharSequence getSpannableString(Context ctx, Typeface ttf, int stringId) {
        SpannableStringBuilder builder = new SpannableStringBuilder().append(new SpannableString(ctx.getResources().getString(stringId)));
        return builder.subSequence(0, builder.length());
    }

    public static Bitmap resizeBitmap(Bitmap bit, int width, int height) {
        float wr = (float) width;
        float hr = (float) height;
        float wd = (float) bit.getWidth();
        float he = (float) bit.getHeight();
        Log.i("testings", wr + "  " + hr + "  and  " + wd + "  " + he);
        float rat1 = wd / he;
        float rat2 = he / wd;
        if (wd > wr) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", "if (wd > wr) " + wd + "  " + he);
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (he > hr) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            if (wd > wr) {
                wd = wr;
                he = wd * rat2;
            } else {
                Log.i("testings", " in else " + wd + "  " + he);
            }
        } else if (rat1 > 0.75f) {
            wd = wr;
            he = wd * rat2;
            Log.i("testings", " if (rat1 > .75f) ");
        } else if (rat2 > 1.5f) {
            he = hr;
            wd = he * rat1;
            Log.i("testings", " if (rat2 > 1.5f) ");
        } else {
            he = wr * rat2;
            Log.i("testings", " in else ");
            if (he > hr) {
                he = hr;
                wd = he * rat1;
                Log.i("testings", "  if (he > hr) " + wd + "  " + he);
            } else {
                wd = wr;
                he = wd * rat2;
                Log.i("testings", " in else " + wd + "  " + he);
            }
        }
        return Bitmap.createScaledBitmap(bit, (int) wd, (int) he, false);
    }

    public static Bitmap getBitmapFromUri(Context context, Uri uri, float screenWidth, float screenHeight) throws IOException {
        try {
            ParcelFileDescriptor parcelFileDescriptor = context.getContentResolver().openFileDescriptor(uri, "r");
            FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
            Options bfo = new Options();
            bfo.inJustDecodeBounds = true;
            BitmapFactory.decodeFileDescriptor(fileDescriptor, null, bfo);
            Options optsDownSample = new Options();
            if (screenWidth <= screenHeight) {
                screenWidth = screenHeight;
            }
            int maxDim = (int) screenWidth;
            optsDownSample.inSampleSize = ImageUtils.getClosestResampleSize(bfo.outWidth, bfo.outHeight, maxDim);
            Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor, null, optsDownSample);
            Matrix m = new Matrix();
            if (image.getWidth() > maxDim || image.getHeight() > maxDim) {
                Options optsScale = ImageUtils.getResampling(image.getWidth(), image.getHeight(), maxDim);
                m.postScale(((float) optsScale.outWidth) / ((float) image.getWidth()), ((float) optsScale.outHeight) / ((float) image.getHeight()));
            }
            String pathInput = ImageUtils.getRealPathFromURI(uri, context);
            if (new Integer(VERSION.SDK).intValue() > 4) {
                int rotation = ExifUtils.getExifRotation(pathInput);
                if (rotation != 0) {
                    m.postRotate((float) rotation);
                }
            }
            image = Bitmap.createBitmap(image, 0, 0, image.getWidth(), image.getHeight(), m, true);
            parcelFileDescriptor.close();
            return image;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }
    }

    public static int getClosestResampleSize(int cx, int cy, int maxDim) {
        int max = Math.max(cx, cy);
        int resample = 1;
        while (resample < R_D.changed_1) {
            if (resample * maxDim > max) {
                resample--;
                break;
            }
            resample++;
        }
        return resample > 0 ? resample : 1;
    }

    public static Typeface getHeaderTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "impact.ttf");
    }

    public static Typeface getTextTypeface(Activity activity) {
        return Typeface.createFromAsset(activity.getAssets(), "VERDANA.ttf");
    }

    public static Typeface getTextTypeface(Context activity) {
        return Typeface.createFromAsset(activity.getAssets(), "VERDANA.ttf");
    }

    public static Typeface getTextTypefaceFont(Activity activity, String fonts) {
        return Typeface.createFromAsset(activity.getAssets(), fonts);
    }

    public static Animation getAnimUp(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_up);
    }

    public static Animation getAnimDown(Activity activity) {
        return AnimationUtils.loadAnimation(activity, R.anim.slide_down);
    }

    public static File getSaveFileLocation(String folderName) {
        return new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Poster Maker/.Poster Maker Stickers/" + folderName);
    }

    public static boolean saveBitmapObject(Activity activity, Bitmap btmSimple, String pathIs) {
        Bitmap bitmap = btmSimple.copy(btmSimple.getConfig(), true);
        File pictureFile = new File(pathIs);
        try {
            if (!pictureFile.exists()) {
                pictureFile.createNewFile();
            }
            FileOutputStream ostream = new FileOutputStream(pictureFile);
            boolean saved = bitmap.compress(CompressFormat.PNG, 100, ostream);
            ostream.flush();
            ostream.close();
            bitmap.recycle();
            activity.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(pictureFile)));
            return saved;
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("testing", "Exception" + e.getMessage());
            return false;
        }
    }

    public static String saveBitmapObject1(Bitmap bitmap) {
        String temp_path = "";
        File myDir = getSaveFileLocation("category1" );
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

    public static String saveBitmapObject(Activity activity, Bitmap bitmap) {
        File myDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "/"+activity.getString(R.string.app_name)+"/Design");
        myDir.mkdirs();
        long n = System.currentTimeMillis();
        File file = new File(myDir, "raw-" + n + ".ser");
        File file1 = new File(myDir, "raw1-" + n + ".png");
        if (file.exists()) {
            file.delete();
        }
        try {
            bitmap.compress(CompressFormat.PNG, 100, new FileOutputStream(file1));
            FileOutputStream out = new FileOutputStream(file);
            ObjectOutputStream os = new ObjectOutputStream(out);
            BitmapDataObject bdo = new BitmapDataObject();
            bdo.imageByteArray = getBytesFromBitmap(bitmap);
            os.writeObject(bdo);
            os.close();
            out.close();
            return file.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            Log.i("MAINACTIVITY", "Exception" + e.getMessage());
            Toast.makeText(activity, activity.getResources().getString(R.string.save_err), Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public static byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap guidelines_bitmap(Activity context, int w, int h) {
        Bitmap bitmap = Bitmap.createBitmap(w, h, Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);
        Paint paint = new Paint();
        paint.setColor(-1);
        paint.setStrokeWidth((float) ImageUtils.dpToPx(context, 2));
        paint.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint.setStyle(Style.STROKE);
        Paint paint1 = new Paint();
        paint1.setColor(ViewCompat.MEASURED_STATE_MASK);
        paint1.setStrokeWidth((float) ImageUtils.dpToPx(context, 2));
        paint1.setPathEffect(new DashPathEffect(new float[]{5.0f, 5.0f}, 1.0f));
        paint1.setStyle(Style.STROKE);
        c.drawLine((float) (w / 4), 0.0f, (float) (w / 4), (float) h, paint);
        c.drawLine((float) (w / 2), 0.0f, (float) (w / 2), (float) h, paint);
        c.drawLine((float) ((w * 3) / 4), 0.0f, (float) ((w * 3) / 4), (float) h, paint);
        c.drawLine(0.0f, (float) (h / 4), (float) w, (float) (h / 4), paint);
        c.drawLine(0.0f, (float) (h / 2), (float) w, (float) (h / 2), paint);
        c.drawLine(0.0f, (float) ((h * 3) / 4), (float) w, (float) ((h * 3) / 4), paint);
        c.drawLine((float) ((w / 4) + 2), 0.0f, (float) ((w / 4) + 2), (float) h, paint1);
        c.drawLine((float) ((w / 2) + 2), 0.0f, (float) ((w / 2) + 2), (float) h, paint1);
        c.drawLine((float) (((w * 3) / 4) + 2), 0.0f, (float) (((w * 3) / 4) + 2), (float) h, paint1);
        c.drawLine(0.0f, (float) ((h / 4) + 2), (float) w, (float) ((h / 4) + 2), paint1);
        c.drawLine(0.0f, (float) ((h / 2) + 2), (float) w, (float) ((h / 2) + 2), paint1);
        c.drawLine(0.0f, (float) (((h * 3) / 4) + 2), (float) w, (float) (((h * 3) / 4) + 2), paint1);
        return bitmap;
    }

    public static Bitmap getTiledBitmap(Activity activity, int resId, Bitmap imgBtmap, SeekBar seek_tailys) {
        Rect rect = new Rect(0, 0, imgBtmap.getWidth(), imgBtmap.getHeight());
        Paint paint = new Paint();
        int prog = seek_tailys.getProgress() + 10;
        Options options = new Options();
        options.inScaled = false;
        paint.setShader(new BitmapShader(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(activity.getResources(), resId, options), prog, prog, true), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(imgBtmap.getWidth(), imgBtmap.getHeight(), Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }

    public static Bitmap getTiledBitmap(Context ctx, int resId, int width, int height) {
        Rect rect = new Rect(0, 0, width, height);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(BitmapFactory.decodeResource(ctx.getResources(), resId, new Options()), TileMode.REPEAT, TileMode.REPEAT));
        Bitmap b = Bitmap.createBitmap(width, height, Config.ARGB_8888);
        new Canvas(b).drawRect(rect, paint);
        return b;
    }

    public static float getNewX(float x) {
        return ((float) MainActivity.width) * (x / ((float) sw));
    }

    public static float getNewY(float y) {
        sh = (int) (((float) sw) / MainActivity.ratio);
        return ((float) MainActivity.height1) * (y / ((float) sh));
    }

    public static int getNewWidth(float width) {
        return (int) (((float) MainActivity.width) * (width / ((float) sw)));
    }

    public static int getNewHeight(float height) {
        sh = (int) (((float) sw) / MainActivity.ratio);
        return (int) (((float) MainActivity.height1) * (height / ((float) sh)));
    }

    public static float getNewSize(Context context, float size) {
        return (context.getResources().getDisplayMetrics().density / 3.0f) * size;
    }
}
