package com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter;

import android.app.Activity;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.main.Constants;

public class BackgroundAdepter extends RecyclerView.Adapter<RecyclerViewHolder> {
    OnClickListener clickListener = new OnClick();
    Activity context;
    LayoutInflater inflater;
    String val;

    class OnClick implements OnClickListener {
        OnClick() {
        }

        public void onClick(View v) {
            int position = ((RecyclerViewHolder) v.getTag()).getPosition();
            if (!BackgroundAdepter.this.val.equals("texture")) {
            }
        }
    }

    public BackgroundAdepter(Activity context, String val) {
        this.context = context;
        this.val = val;
        this.inflater = LayoutInflater.from(context);
    }

    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new RecyclerViewHolder(this.inflater.inflate(R.layout.item_background, parent, false));
    }

    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        if (this.val.equals("background")) {
            try {
                Glide.with(this.context).load(Integer.valueOf(Constants.Imageid0[position])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.imageView);
            } catch (IllegalArgumentException e) {
                Log.wtf("Glide-tag", String.valueOf(holder.imageView.getTag()));
            }
        } else if (this.val.equals("texture")) {
            try {
                Glide.with(this.context).load(Integer.valueOf(Constants.Imageid1[position])).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(holder.imageView);
            } catch (IllegalArgumentException e2) {
                Log.wtf("Glide-tag", String.valueOf(holder.imageView.getTag()));
            }
        }
        holder.imageView.setOnClickListener(this.clickListener);
        holder.imageView.setTag(holder);
    }

    public int getItemCount() {
        if (this.val.equals("background")) {
            return Constants.Imageid0.length;
        }
        if (this.val.equals("texture")) {
            return Constants.Imageid1.length;
        }
        return 0;
    }
}
