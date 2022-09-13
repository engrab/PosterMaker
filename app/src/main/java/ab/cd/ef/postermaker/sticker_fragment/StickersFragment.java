package ab.cd.ef.postermaker.sticker_fragment;


import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.main.Constants;

public class StickersFragment extends Fragment {
    int asuncCount = 0;
    String catName;
    Editor editor;
    GridView grid;
    GetSnapListener onGetSnap;
    private ProgressDialog pdia;
    int positn;
    SharedPreferences prefs;
    int size_list;
    ArrayList<String> stkrNameList = new ArrayList();
    ArrayList<Bitmap> thumbnails = new ArrayList();
    ArrayList<String> uri = new ArrayList();

    class C03301 implements OnItemClickListener {
        C03301() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            if (StickersFragment.this.catName.equals("img")) {
                Log.e("clicked", " sticker if");
                StickersFragment.this.onGetSnap.onSnapFilter(position, StickersFragment.this.positn, (String) StickersFragment.this.uri.get(position));
                return;
            }
            Log.e("clicked", " sticker else");
            StickersFragment.this.onGetSnap.onSnapFilter(position, StickersFragment.this.positn, "");
        }
    }

    private class SaveStickersAsync extends AsyncTask<String, String, Boolean> {
        int size;

        private SaveStickersAsync() {
            this.size = 0;
        }

        protected void onPreExecute() {
            super.onPreExecute();
        }

        protected Boolean doInBackground(String... params) {
            String stkrName = params[0];
            this.size = Integer.parseInt(params[1]) + 1;
            StickersFragment.this.pdia.setProgress(this.size);
            try {
                Bitmap bitmap = BitmapFactory.decodeResource(StickersFragment.this.getResources(), StickersFragment.this.getResources().getIdentifier(stkrName, "drawable", StickersFragment.this.getActivity().getPackageName()));
                if (bitmap != null) {
                    return Boolean.valueOf(saveBitmapObject(bitmap, stkrName));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return Boolean.valueOf(false);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (isDownloaded.booleanValue()) {
                File[] files = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Post Maker/.Poster Maker Stickers/category1").listFiles();
                if (files.length >= StickersFragment.this.stkrNameList.size()) {
                    Toast.makeText(StickersFragment.this.getActivity(), StickersFragment.this.getResources().getString(R.string.downloaded), Toast.LENGTH_SHORT).show();
                    StickersFragment.this.pdia.dismiss();
                }
                for (int i = 0; i < files.length; i++) {
                    Options op = new Options();
                    op.inSampleSize = 5;
                    StickersFragment.this.thumbnails.add(BitmapFactory.decodeFile(files[i].getAbsolutePath(), op));
                    StickersFragment.this.uri.add(files[i].getAbsolutePath());
                }
                StickersFragment.this.grid.setAdapter(new StickerGrid(StickersFragment.this.getActivity(), StickersFragment.this.catName, StickersFragment.this.thumbnails));
            } else if (new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Post Maker/.Poster Maker Stickers/category1").listFiles().length >= StickersFragment.this.stkrNameList.size()) {
                StickersFragment.this.pdia.dismiss();
            }
        }

        private boolean saveBitmapObject(Bitmap bitmap, String fname) {
            File myDir = Constants.getSaveFileLocation("category1");
            myDir.mkdirs();
            File file = new File(myDir, fname + ".png");
            if (file.exists()) {
                file.delete();
            }
            try {
                FileOutputStream ostream = new FileOutputStream(file);
                boolean saved = bitmap.compress(CompressFormat.PNG, 100, ostream);
                ostream.close();
                return saved;
            } catch (Exception e) {
                e.printStackTrace();
                Log.i("testing", "Exception" + e.getMessage());
                return false;
            }
        }
    }

    private class lordStickersAsync extends AsyncTask<String, String, Boolean> {
        ProgressDialog ringProgressDialog;

        private lordStickersAsync() {
        }

        protected void onPreExecute() {
            super.onPreExecute();
            this.ringProgressDialog = new ProgressDialog(StickersFragment.this.getActivity());
            this.ringProgressDialog.setMessage(StickersFragment.this.getResources().getString(R.string.plzwait));
            this.ringProgressDialog.setCancelable(false);
            this.ringProgressDialog.show();
        }

        protected Boolean doInBackground(String... params) {
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Post Maker/.Poster Maker Stickers/category1");
            if (file.exists()) {
                File[] listFile = file.listFiles();
                int count = file.listFiles().length;
                for (int i = 0; i < count; i++) {
                    Options op = new Options();
                    op.inSampleSize = 5;
                    StickersFragment.this.thumbnails.add(BitmapFactory.decodeFile(listFile[i].getAbsolutePath(), op));
                    StickersFragment.this.uri.add(listFile[i].getAbsolutePath());
                }
            }
            return Boolean.valueOf(true);
        }

        protected void onPostExecute(Boolean isDownloaded) {
            super.onPostExecute(isDownloaded);
            if (isDownloaded.booleanValue()) {
                StickersFragment.this.grid.setAdapter(new StickerGrid(StickersFragment.this.getActivity(), StickersFragment.this.catName, StickersFragment.this.thumbnails));
            }
            this.ringProgressDialog.dismiss();
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.item_frag, container, false);
        this.catName = getArguments().getString("categoryName");
        this.positn = Integer.parseInt(getArguments().getString("positionIs"));
        this.onGetSnap = (GetSnapListener) getActivity();
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.grid = (GridView) view.findViewById(R.id.grid);
        if (this.catName.equals("img")) {
            this.stkrNameList.clear();
            for (int k = 1; k <= 12; k++) {
                this.stkrNameList.add("bh" + k);
            }
            this.thumbnails.clear();
            this.uri.clear();
            new lordStickersAsync().execute(new String[]{""});
        } else {
            this.grid.setAdapter(new StickerGrid(getActivity(), this.catName, this.thumbnails));
        }
        this.grid.setOnItemClickListener(new C03301());
        return view;
    }

    private void downloadStickers() {
        try {
            File pictureFileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Post Maker/.Poster Maker Stickers/category1");
            if (pictureFileDir.exists() || pictureFileDir.mkdirs()) {
                this.size_list = this.stkrNameList.size();
                this.pdia = new ProgressDialog(getActivity());
                this.pdia.setMessage(getActivity().getResources().getString(R.string.downloading));
                this.pdia.setIndeterminate(false);
                this.pdia.setMax(this.size_list);
                this.pdia.setProgressStyle(1);
                this.pdia.setCancelable(false);
                this.pdia.show();
                File rootFolder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
                File file = new File(rootFolder, "Post Maker/.Poster Maker Stickers/category1");
                int i;
                if (file.exists()) {
                    int count_stkr = file.listFiles().length;
                    if (count_stkr >= this.stkrNameList.size()) {
                        this.pdia.dismiss();
                        Toast.makeText(getActivity(), getResources().getString(R.string.downloaded), Toast.LENGTH_SHORT).show();
                        for (i = 0; i < count_stkr; i++) {
                            Options op = new Options();
                            op.inSampleSize = 5;
                            this.thumbnails.add(BitmapFactory.decodeFile(file.listFiles()[i].getAbsolutePath(), op));
                            this.uri.add(file.listFiles()[i].getAbsolutePath());
                        }
                        this.grid.setAdapter(new StickerGrid(getActivity(), this.catName, this.thumbnails));
                        return;
                    }
                    for (i = 0; i < this.stkrNameList.size(); i++) {
                        if (new File(rootFolder, ".Poster Maker Stickers/category1/" + ((String) this.stkrNameList.get(i)) + ".png").exists()) {
                            this.pdia.setProgress(i + 1);
                        } else {
                            new SaveStickersAsync().execute(new String[]{(String) this.stkrNameList.get(i), "" + i});
                            this.asuncCount++;
                        }
                    }
                    return;
                }
                for (i = 0; i < this.stkrNameList.size(); i++) {
                    if (new File(rootFolder, ".Poster Maker Stickers/category1/" + ((String) this.stkrNameList.get(i)) + ".png").exists()) {
                        this.pdia.setProgress(i + 1);
                    } else {
                        new SaveStickersAsync().execute(new String[]{(String) this.stkrNameList.get(i), "" + i});
                        this.asuncCount++;
                    }
                }
                return;
            }
            Log.d("", "Can't create directory to save image.");
            Toast.makeText(getActivity(), getResources().getString(R.string.create_dir_err), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
        }
    }
}
