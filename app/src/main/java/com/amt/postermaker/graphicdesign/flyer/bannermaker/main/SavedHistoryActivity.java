package com.amt.postermaker.graphicdesign.flyer.bannermaker.main;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;


import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;

public class SavedHistoryActivity extends Activity {
    static File[] listFile;
    static ArrayList<String> pathsOfFile = new ArrayList();
    static int pos;
    int count = 0;
    String[] extensions = new String[]{"jpg", "jpeg", "JPG", "JPEG"};
    Bitmap f11b;
    Context f12c;
    ImageAdapter imageAdapter;
    GridView imagegrid;
    TextView no_image;
    SharedPreferences preferences;
    RelativeLayout rel_text;
    ArrayList<Bitmap> thumbnails = new ArrayList();
    ArrayList<Bitmap> thumbnails1 = new ArrayList();
    TextView toolbar_title;
    ArrayList<String> uri = new ArrayList();
    ArrayList<String> uri1 = new ArrayList();

    class ImageAdapter extends BaseAdapter {
        Context context;
        LayoutInflater mInflater = ((LayoutInflater) this.context.getSystemService(LAYOUT_INFLATER_SERVICE));

        public ImageAdapter(Context c) {
            this.context = c;
        }

        public int getCount() {
            return SavedHistoryActivity.this.count;
        }

        public Object getItem(int position) {
            return Integer.valueOf(position);
        }

        public long getItemId(int position) {
            return (long) position;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            final int p = position;
            SavedHistoryActivity.pos = position;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = this.mInflater.inflate(R.layout.grid_item22, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageview.setId(position);
            if (SavedHistoryActivity.this.count == 0) {
                SavedHistoryActivity.this.rel_text.setVisibility(View.VISIBLE);
            } else {
                SavedHistoryActivity.this.rel_text.setVisibility(View.INVISIBLE);
            }
            holder.imageview.setImageBitmap((Bitmap) SavedHistoryActivity.this.thumbnails.get(position));
            holder.imageview.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(SavedHistoryActivity.this, ShareActivity.class);
                    i.putExtra("uri", (String) SavedHistoryActivity.this.uri.get(p));
                    i.putExtra("way", "Gallery");
                    SavedHistoryActivity.this.startActivity(i);
                }
            });
            return convertView;
        }
    }

    class ViewHolder {
        ImageView imageview;

        ViewHolder() {
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(1024, 1024);
        requestWindowFeature(1);
        setContentView(R.layout.activity_saved_history);

        LinearLayout adContainer=(LinearLayout) findViewById(R.id.adContainer);

        this.preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        this.no_image = (TextView) findViewById(R.id.no_image);
        this.rel_text = (RelativeLayout) findViewById(R.id.rel_text);
        this.no_image.setTypeface(Constants.getTextTypeface(this));
        ((TextView) findViewById(R.id.SavedPictures)).setTypeface(Constants.getHeaderTypeface(this));

        findViewById(R.id.btn_back).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SavedHistoryActivity.this.finish();
            }
        });
        findViewById(R.id.btn_home).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                SavedHistoryActivity.this.setResult(-1);
                SavedHistoryActivity.this.finish();
            }
        });



        final ProgressDialog ringProgressDialog = new ProgressDialog(this);
        ringProgressDialog.setMessage(getResources().getString(R.string.plzwait));
        ringProgressDialog.setCancelable(false);
        ringProgressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                try {
                    SavedHistoryActivity.this.getFromSdcard();
                    SavedHistoryActivity.this.f12c = SavedHistoryActivity.this;
                    SavedHistoryActivity.this.imagegrid = (GridView) SavedHistoryActivity.this.findViewById(R.id.gridView);
                    SavedHistoryActivity.this.imageAdapter = new ImageAdapter(SavedHistoryActivity.this.f12c);
                    Thread.sleep(1000);
                } catch (Exception e) {
                }
                ringProgressDialog.dismiss();
            }
        }).start();
        ringProgressDialog.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                SavedHistoryActivity.this.imagegrid.setAdapter(SavedHistoryActivity.this.imageAdapter);
            }
        });
    }

    public void getFromSdcard() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM) + "/"+getString(R.string.app_name));
        if (file.isDirectory()) {
            int i;
            listFile = file.listFiles();
            this.count = listFile.length;
            for (i = 0; i < listFile.length; i++) {
                Options op = new Options();
                op.inSampleSize = 5;
                this.f11b = BitmapFactory.decodeFile(listFile[i].getAbsolutePath(), op);
                this.thumbnails1.add(this.f11b);
                this.uri1.add(listFile[i].getAbsolutePath());
            }
            for (i = this.thumbnails1.size() - 1; i >= 0; i--) {
                this.thumbnails.add(this.thumbnails1.get(i));
                this.uri.add(this.uri1.get(i));
            }
        }
    }

    protected void onResume() {
        super.onResume();
    }

    public void onBackPressed() {
        finish();
    }

}
