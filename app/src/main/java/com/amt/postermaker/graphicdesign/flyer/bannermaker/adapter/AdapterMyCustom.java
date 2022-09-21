package com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.List;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.models.BitmapDataObject;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.TemplateInfo;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.CustomSquareFrameLayout;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.CustomSquareImageView;

public class AdapterMyCustom extends ArrayAdapter<TemplateInfo> {
    String catName;
    Context context;
    SharedPreferences prefs;

    class ViewHolder {
        CustomSquareImageView mThumbnail;
        CustomSquareFrameLayout root;
        Uri uri;

        public ViewHolder(View view) {
            this.root = (CustomSquareFrameLayout) view.findViewById(R.id.root);
            this.mThumbnail = (CustomSquareImageView) view.findViewById(R.id.thumbnail_image);
        }
    }

    public AdapterMyCustom(Context context, List<TemplateInfo> images, String cateNameIs, SharedPreferences prefs1) {
        super(context, 0, images);
        this.context = context;
        this.catName = cateNameIs;
        this.prefs = prefs1;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.picker_default_thumbnail, null);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        TemplateInfo templateInfo = (TemplateInfo) getItem(position);
        if (this.catName.equals("MY_TEMP")) {
            holder.mThumbnail.setImageBitmap(BitmapFactory.decodeStream(new ByteArrayInputStream(getBitmapDataObject(Uri.parse(templateInfo.getTHUMB_URI()).getPath()).imageByteArray)));
        } else {
            holder.mThumbnail.setImageBitmap(BitmapFactory.decodeResource(this.context.getResources(), this.context.getResources().getIdentifier(templateInfo.getTHUMB_URI(), "drawable", this.context.getPackageName())));
        }
        return convertView;
    }

    private BitmapDataObject getBitmapDataObject(String path) {
        try {
            return (BitmapDataObject) new ObjectInputStream(new FileInputStream(new File(path))).readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        } catch (ClassNotFoundException e3) {
            e3.printStackTrace();
        }
        return null;
    }
}
