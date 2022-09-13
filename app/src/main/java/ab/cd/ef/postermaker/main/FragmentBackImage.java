package ab.cd.ef.postermaker.main;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

import ab.cd.ef.postermaker.R;

public class FragmentBackImage extends Fragment {
    public static ArrayList<Bitmap> thumbnails = new ArrayList();
    Context f8c;
    OnGetImageOnTouch getImage;
    private GridView gridView;
    ImageAdapter imageAdapter;
    ArrayList<String> uri = new ArrayList();

    class listener implements OnDismissListener {
        listener() {
        }

        public void onDismiss(DialogInterface dialog) {
            FragmentBackImage.this.gridView.setAdapter(FragmentBackImage.this.imageAdapter);
        }
    }

    class ImageAdapter extends BaseAdapter {
        Context context;
        LayoutInflater mInflater = ((LayoutInflater) this.context.getSystemService("layout_inflater"));

        public ImageAdapter(Context c) {
            this.context = c;
        }

        public int getCount() {
            return FragmentBackImage.thumbnails.size();
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
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = this.mInflater.inflate(R.layout.grid_item22, null);
                holder.imageview = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.imageview.setId(position);
            holder.imageview.setImageBitmap((Bitmap) FragmentBackImage.thumbnails.get(position));
            holder.imageview.setOnClickListener(new OnClickListener() {
                public void onClick(View v) {
                    FragmentBackImage.this.getImage.ongetPosition(p, "Temp_Path", "");
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

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_backgund, container, false);
        this.getImage = (OnGetImageOnTouch) getActivity();
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        this.f8c = getActivity();
        this.imageAdapter = new ImageAdapter(this.f8c);
        return view;
    }

    public void setMenuVisibility(boolean visible) {
        if (visible) {
            try {
                final ProgressDialog ringProgressDialog = new ProgressDialog(getActivity());
                ringProgressDialog.setMessage(getResources().getString(R.string.plzwait));
                ringProgressDialog.setCancelable(false);
                ringProgressDialog.show();
                new Thread(new Runnable() {
                    public void run() {
                        try {
                            FragmentBackImage.thumbnails.clear();
                            FragmentBackImage.this.getFromSdcard();
                            FragmentBackImage.this.f8c = FragmentBackImage.this.getActivity();
                            FragmentBackImage.this.imageAdapter = new ImageAdapter(FragmentBackImage.this.f8c);
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        ringProgressDialog.dismiss();
                    }
                }).start();
                ringProgressDialog.setOnDismissListener(new listener());
            } catch (NullPointerException e) {
            }
        }
        super.setMenuVisibility(visible);
    }

    public void getFromSdcard() {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), getString(R.string.app_name)+"/.Poster Maker Stickers/category1");
        if (file.exists()) {
            int count_stkr = file.listFiles().length;
            thumbnails.clear();
            this.uri.clear();
            for (int i = 0; i < count_stkr; i++) {
                Options op = new Options();
                op.inSampleSize = 5;
                thumbnails.add(BitmapFactory.decodeFile(file.listFiles()[i].getAbsolutePath(), op));
                this.uri.add(file.listFiles()[i].getAbsolutePath());
            }
        }
    }
}
