package com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.CustomSquareImageView;

public class StickerGrid extends BaseAdapter {
    LayoutInflater inflater;
    private Context mContext;
    ArrayList<Bitmap> thumbnails;
    String val;

    public class ViewHolder {
        CustomSquareImageView image;
    }

    public StickerGrid(Context context, String val, ArrayList<Bitmap> thumbnails1) {
        this.mContext = context;
        this.val = val;
        this.thumbnails = thumbnails1;
        this.inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        if (this.val.equals("offer")) {
            return Constants.Imageid_st1.length;
        }
        if (this.val.equals("sale")) {
            return Constants.Imageid_st2.length;
        }
        if (this.val.equals("banner")) {
            return Constants.Imageid_st3.length;
        }
        if (this.val.equals("sports")) {
            return Constants.Imageid_st4.length;
        }
        if (this.val.equals("ribbon")) {
            return Constants.Imageid_st5.length;
        }
        if (this.val.equals("birth")) {
            return Constants.Imageid_st6.length;
        }
        if (this.val.equals("decorat")) {
            return Constants.Imageid_st7.length;
        }
        if (this.val.equals("party")) {
            return Constants.Imageid_st8.length;
        }
        if (this.val.equals("music")) {
            return Constants.Imageid_st9.length;
        }
        if (this.val.equals("festival")) {
            return Constants.Imageid_st10.length;
        }
        if (this.val.equals("love")) {
            return Constants.Imageid_st11.length;
        }
        if (this.val.equals("college")) {
            return Constants.Imageid_st12.length;
        }
        if (this.val.equals("circle")) {
            return Constants.Imageid_st13.length;
        }
        if (this.val.equals("coffee")) {
            return Constants.Imageid_st14.length;
        }
        if (this.val.equals("cares")) {
            return Constants.Imageid_st15.length;
        }
        if (this.val.equals("nature")) {
            return Constants.Imageid_st16.length;
        }
        if (this.val.equals("word")) {
            return Constants.Imageid_st17.length;
        }
        if (this.val.equals("hallow")) {
            return Constants.Imageid_st18.length;
        }
        if (this.val.equals("animal")) {
            return Constants.Imageid_st19.length;
        }
        if (this.val.equals("cartoon")) {
            return Constants.Imageid_st20.length;
        }
        if (this.val.equals("shape")) {
            return Constants.Imageid_st23.length;
        }
        if (this.val.equals("white")) {
            return Constants.Imageid_st21.length;
        }
        if (this.val.equals("img")) {
            return this.thumbnails.size();
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
            convertView = ((LayoutInflater) this.mContext.getSystemService("layout_inflater")).inflate(R.layout.item_snap, null);
            holder = new ViewHolder();
            holder.image = (CustomSquareImageView) convertView.findViewById(R.id.thumbnail_image);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (this.val.equals("offer")) {
            fillImage(Constants.Imageid_st1[position], holder.image);
        } else if (this.val.equals("sale")) {
            fillImage(Constants.Imageid_st2[position], holder.image);
        } else if (this.val.equals("banner")) {
            fillImage(Constants.Imageid_st3[position], holder.image);
        } else if (this.val.equals("sports")) {
            fillImage(Constants.Imageid_st4[position], holder.image);
        } else if (this.val.equals("ribbon")) {
            fillImage(Constants.Imageid_st5[position], holder.image);
        } else if (this.val.equals("birth")) {
            fillImage(Constants.Imageid_st6[position], holder.image);
        } else if (this.val.equals("decorat")) {
            fillImage(Constants.Imageid_st7[position], holder.image);
        } else if (this.val.equals("party")) {
            fillImage(Constants.Imageid_st8[position], holder.image);
        } else if (this.val.equals("music")) {
            fillImage(Constants.Imageid_st9[position], holder.image);
        } else if (this.val.equals("festival")) {
            fillImage(Constants.Imageid_st10[position], holder.image);
        } else if (this.val.equals("love")) {
            fillImage(Constants.Imageid_st11[position], holder.image);
        } else if (this.val.equals("college")) {
            fillImage(Constants.Imageid_st12[position], holder.image);
        } else if (this.val.equals("circle")) {
            fillImage(Constants.Imageid_st13[position], holder.image);
        } else if (this.val.equals("coffee")) {
            fillImage(Constants.Imageid_st14[position], holder.image);
        } else if (this.val.equals("cares")) {
            fillImage(Constants.Imageid_st15[position], holder.image);
        } else if (this.val.equals("nature")) {
            fillImage(Constants.Imageid_st16[position], holder.image);
        } else if (this.val.equals("word")) {
            fillImage(Constants.Imageid_st17[position], holder.image);
        } else if (this.val.equals("hallow")) {
            fillImage(Constants.Imageid_st18[position], holder.image);
        } else if (this.val.equals("animal")) {
            fillImage(Constants.Imageid_st19[position], holder.image);
        } else if (this.val.equals("cartoon")) {
            fillImage(Constants.Imageid_st20[position], holder.image);
        } else if (this.val.equals("shape")) {
            fillImage(Constants.Imageid_st23[position], holder.image);
        } else if (this.val.equals("white")) {
            fillImage(Constants.Imageid_st21[position], holder.image);
        } else if (this.val.equals("img")) {
            holder.image.setImageBitmap((Bitmap) this.thumbnails.get(position));
        }
        return convertView;
    }

    void fillImage(int id, ImageView img) {
        Glide.with(this.mContext).load(Integer.valueOf(id)).thumbnail(0.1f).dontAnimate().centerCrop().placeholder(R.drawable.no_image).error(R.drawable.no_image).into(img);
    }
}
