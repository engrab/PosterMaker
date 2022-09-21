package com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.main.Constants;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.CustomSquareFrameLayout;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.CustomSquareImageView;

public class FrameAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private Context mContext;
    SharedPreferences prefs;
    String val;

    public class ViewHolder {
        ImageView img_lock;
        CustomSquareImageView mThumbnail;
        CustomSquareFrameLayout root;
    }

    public FrameAdapter(Context context, String val, SharedPreferences prefs1) {
        this.mContext = context;
        this.val = val;
        this.prefs = prefs1;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.val.equals("Background")) {
            return Constants.Imageid0.length;
        }
        if (this.val.equals("Texture")) {
            return Constants.Imageid1.length;
        }
        return 0;
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
            convertView = LayoutInflater.from(this.mContext).inflate(R.layout.picker_grid_item_gallery_thumbnail, null);
            holder = new ViewHolder();
            holder.root = (CustomSquareFrameLayout) convertView.findViewById(R.id.root);
            holder.mThumbnail = (CustomSquareImageView) convertView.findViewById(R.id.thumbnail_image);
            holder.img_lock = (ImageView) convertView.findViewById(R.id.img_lock);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.val.equals("Background")) {
            Glide.with(this.mContext).load(Integer.valueOf(Constants.Imageid0[position])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.mThumbnail);
            if (position <= 8 || position >= 15) {
                if (position <= 14) {
                    holder.img_lock.setVisibility(View.GONE);
                } else if (this.prefs.getString("purchase", null) == null) {
                    holder.img_lock.setVisibility(View.VISIBLE);
                } else {
                    holder.img_lock.setVisibility(View.GONE);
                }
            } else if (this.prefs.getString("rating123", null) == null) {
                holder.img_lock.setVisibility(View.VISIBLE);
            } else {
                holder.img_lock.setVisibility(View.GONE);
            }
        } else if (this.val.equals("Texture")) {
            Glide.with(this.mContext).load(Integer.valueOf(Constants.Imageid1[position])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.mThumbnail);
            if (position <= 8 || position >= 15) {
                if (position <= 14) {
                    holder.img_lock.setVisibility(View.GONE);
                } else if (this.prefs.getString("purchase", null) == null) {
                    holder.img_lock.setVisibility(View.VISIBLE);
                } else {
                    holder.img_lock.setVisibility(View.GONE);
                }
            } else if (this.prefs.getString("rating123", null) == null) {
                holder.img_lock.setVisibility(View.VISIBLE);
            } else {
                holder.img_lock.setVisibility(View.GONE);
            }
        }
        return convertView;
    }
}
