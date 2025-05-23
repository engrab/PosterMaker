package ab.cd.ef.postermaker.create;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import ab.cd.ef.postermaker.R;

public class ImageSdAdapter extends BaseAdapter {
    private Context mContext;
    private ArrayList<Bitmap> thumbnails;

    public class ViewHolder {
        ImageView imageView;
    }

    public ImageSdAdapter(Context c, ArrayList<Bitmap> thumbnails1) {
        this.mContext = c;
        this.thumbnails = thumbnails1;
    }

    public int getCount() {
        return this.thumbnails.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.bg_lst_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.grid_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.imageView.setImageBitmap((Bitmap) this.thumbnails.get(position));
        return convertView;
    }
}
