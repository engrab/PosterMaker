package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.util.ArrayList;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter.AdapterMyCustom;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.db.DatabaseHandler;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.interfaces.OnDataChanged;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.TemplateInfo;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.activities.PosterActivity;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.activities.TemplatesActivity;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;

public class FragmentDefault extends Fragment implements OnDataChanged {
    private Animation animSlideDown;
    private Animation animSlideUp;
    LinearLayout btn_inApp;
    String catName;
    private Editor editor;
    private GridView gridView;
    RelativeLayout lay_dialog;
    LordDataOperationAsync loadDataAsync = null;
    private AdapterMyCustom adapterMyCustom;
    SharedPreferences prefs;
    ProgressBar progress_bar;
    private ArrayList<TemplateInfo> templateList = new ArrayList();
    Typeface ttf;
    TextView txt_dialog;


    public class LordDataOperationAsync extends AsyncTask<String, Void, String> {
        protected void onPreExecute() {
            FragmentDefault.this.progress_bar.setVisibility(View.VISIBLE);
        }

        protected String doInBackground(String... params) {
            try {
                FragmentDefault.this.templateList.clear();
                DatabaseHandler dh = DatabaseHandler.getDbHandler(FragmentDefault.this.getActivity());
//                if (FragmentDefault.this.catName.equals("MY_TEMP")) {
//                    FragmentDefault.this.templateList = dh.getTemplateListDes("USER");
//                } else
                    if (FragmentDefault.this.catName.equals("FREE_TEMP")) {
                    FragmentDefault.this.templateList = dh.getTemplateList("FREESTYLE");
                } else if (FragmentDefault.this.catName.equals("FRIDAY_TEMP")) {
                    FragmentDefault.this.templateList = dh.getTemplateList("FRIDAY");
                }
                dh.close();
            } catch (NullPointerException e) {
            }
            return "yes";
        }

        protected void onPostExecute(String result) {
            FragmentDefault.this.progress_bar.setVisibility(View.GONE);
            Log.e("size is", "" + FragmentDefault.this.templateList.size());
            if (FragmentDefault.this.templateList.size() != 0) {
                FragmentDefault.this.adapterMyCustom = new AdapterMyCustom(FragmentDefault.this.getActivity(), FragmentDefault.this.templateList, FragmentDefault.this.catName, FragmentDefault.this.prefs);
                FragmentDefault.this.gridView.setAdapter(FragmentDefault.this.adapterMyCustom);
            }
            if (!FragmentDefault.this.catName.equals("MY_TEMP")) {
                return;
            }
            if (FragmentDefault.this.templateList.size() == 0) {
                FragmentDefault.this.txt_dialog.setText(FragmentDefault.this.getResources().getString(R.string.NoDesigns));
                FragmentDefault.this.lay_dialog.setVisibility(View.VISIBLE);
                FragmentDefault.this.lay_dialog.startAnimation(FragmentDefault.this.animSlideUp);
            } else if (FragmentDefault.this.templateList.size() <= 4) {
                FragmentDefault.this.txt_dialog.setText(FragmentDefault.this.getResources().getString(R.string.DesignOptionsInstruction));
                FragmentDefault.this.lay_dialog.setVisibility(View.VISIBLE);
                FragmentDefault.this.lay_dialog.startAnimation(FragmentDefault.this.animSlideUp);
            } else if (FragmentDefault.this.lay_dialog.getVisibility() == 0) {
                FragmentDefault.this.lay_dialog.startAnimation(FragmentDefault.this.animSlideDown);
                FragmentDefault.this.lay_dialog.setVisibility(View.GONE);
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_template, container, false);
        this.catName = getArguments().getString("categoryName");
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        this.progress_bar = (ProgressBar) view.findViewById(R.id.progress_bar);
        this.progress_bar.setVisibility(View.GONE);
        this.lay_dialog = (RelativeLayout) view.findViewById(R.id.lay_dialog);
        this.txt_dialog = (TextView) view.findViewById(R.id.txt_dialog);
      /////  this.btn_inApp = (LinearLayout) view.findViewById(R.id.lay_inApp);
        this.animSlideUp = Constants.getAnimUp(getActivity());
        this.animSlideDown = Constants.getAnimDown(getActivity());
        this.ttf = Constants.getTextTypeface(getActivity());
        this.txt_dialog.setTypeface(this.ttf);
        this.loadDataAsync = new LordDataOperationAsync();
        this.loadDataAsync.execute(new String[]{""});
        this.gridView.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (FragmentDefault.this.catName.equals("FRIDAY_TEMP")) {
                    Intent intent = new Intent(FragmentDefault.this.getActivity(), PosterActivity.class);
                    intent.putExtra("position", i);
                    intent.putExtra("loadUserFrame", false);
                    intent.putExtra("Temp_Type", FragmentDefault.this.catName);
                    FragmentDefault.this.getActivity().startActivityForResult(intent, TemplatesActivity.OPEN_UPDATE_ACITIVITY_TEMP);
                    return;
                }
                Intent  intent = new Intent(FragmentDefault.this.getActivity(), PosterActivity.class);
                intent.putExtra("position", i);
                intent.putExtra("loadUserFrame", false);
                intent.putExtra("Temp_Type", FragmentDefault.this.catName);
                FragmentDefault.this.getActivity().startActivityForResult(intent, TemplatesActivity.OPEN_UPDATE_ACITIVITY_TEMP);

            }
        });


        this.gridView.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                FragmentDefault.this.showOptionsDialog(i, view);
                return false;
            }
        });

        view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (i != 4) {
                    return false;
                }
                if (FragmentDefault.this.loadDataAsync == null) {
                    return true;
                }
                FragmentDefault.this.loadDataAsync.cancel(true);
                return true;
            }
        });
        return view;
    }

    public void setMenuVisibility(boolean visible) {
        if (visible) {
            super.setMenuVisibility(visible);
        } else {
            super.setMenuVisibility(visible);
        }
    }

    private void showOptionsDialog(final int position, View view) {
        final Dialog dialog = new Dialog(getActivity());
        dialog.getWindow().requestFeature(1);
        dialog.setContentView(R.layout.options_dialog);
        ((TextView) dialog.findViewById(R.id.open)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(FragmentDefault.this.getActivity(), PosterActivity.class);
                intent.putExtra("position", position);
                intent.putExtra("loadUserFrame", false);
                intent.putExtra("Temp_Type", FragmentDefault.this.catName);
                FragmentDefault.this.getActivity().startActivityForResult(intent, TemplatesActivity.OPEN_UPDATE_ACITIVITY_TEMP);
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.delete)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                TemplateInfo tInfo = (TemplateInfo) FragmentDefault.this.templateList.get(position);
                DatabaseHandler dh = DatabaseHandler.getDbHandler(FragmentDefault.this.getActivity());
                boolean b = dh.deleteTemplateInfo(tInfo.getTEMPLATE_ID());
                dh.close();
                if (b) {
                    Toast.makeText(FragmentDefault.this.getActivity(), FragmentDefault.this.getResources().getString(R.string.deleted), Toast.LENGTH_SHORT).show();
                    if (FragmentDefault.this.deleteFile(Uri.parse(tInfo.getTHUMB_URI()))) {
                        FragmentDefault.this.adapterMyCustom.remove(tInfo);
                        FragmentDefault.this.adapterMyCustom.notifyDataSetChanged();
                        new LordDataOperationAsync().execute(new String[]{""});
                    }
                } else {
                    Toast.makeText(FragmentDefault.this.getActivity(), FragmentDefault.this.getResources().getString(R.string.del_error_toast), Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.cancel)).setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation_;
        dialog.show();
    }

    private boolean deleteFile(Uri uri) {
        File file = new File(uri.getPath());
        if (!file.exists()) {
            return false;
        }
        boolean delete = file.delete();
        getActivity().sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file)));
        return delete;
    }

    public void updateAdapter() {
        DatabaseHandler dh = DatabaseHandler.getDbHandler(getActivity());
//        if (this.catName.equals("MY_TEMP")) {
//            this.templateList = dh.getTemplateListDes("USER");
//        } else
            if (this.catName.equals("FREE_TEMP")) {
            this.templateList = dh.getTemplateList("FREESTYLE");
        } else if (this.catName.equals("FRIDAY_TEMP")) {
            this.templateList = dh.getTemplateList("FRIDAY");
        }
        dh.close();
        this.adapterMyCustom = new AdapterMyCustom(getActivity(), this.templateList, this.catName, this.prefs);
        this.gridView.setAdapter(this.adapterMyCustom);
        Log.i("testing", "Frame Updated");
    }

    public void updateInstLay(boolean close) {
        if (this.templateList.size() == 0) {
            Log.i("testing", "Frame  LAy Updated");
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
