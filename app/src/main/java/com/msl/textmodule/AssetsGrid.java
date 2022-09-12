package com.msl.textmodule;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import ab.cd.ef.postermaker.R;

public class AssetsGrid extends BaseAdapter {
    private final String[] Imageid;
    private Context mContext;

    public class ViewHolder {
        TextView txtView;
    }

    public AssetsGrid(Context c, String[] imageid) {
        this.mContext = c;
        this.Imageid = imageid;
    }

    public int getCount() {
        return this.Imageid.length;
    }

    public Object getItem(int position) {
        return this.Imageid[position];
    }

    public long getItemId(int position) {
        return 0;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.libtext_grid_assets, null);
            holder = new ViewHolder();
            holder.txtView = (TextView) convertView.findViewById(R.id.grid_text);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtView.setTypeface(Typeface.createFromAsset(this.mContext.getAssets(), this.Imageid[position]));
        return convertView;
    }
}
