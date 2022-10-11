package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.fragments;

import android.graphics.Color;
import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.interfaces.OnGetImageOnTouch;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;

import yuku.ambilwarna.AmbilWarnaDialog;
import yuku.ambilwarna.AmbilWarnaDialog.OnAmbilWarnaListener;

public class FragmentColor extends Fragment {
    private int bColor = Color.parseColor("#4149b6");
    OnGetImageOnTouch getImage;
    String hex = "";
    ImageView img_color;
    float screenWidth;

    class listener1 implements OnClickListener {

        class listener2 implements OnAmbilWarnaListener {
            listener2() {
            }

            public void onOk(AmbilWarnaDialog dialog, int color) {
                FragmentColor.this.updateColor(color);
            }

            public void onCancel(AmbilWarnaDialog dialog) {
            }
        }

        listener1() {
        }

        public void onClick(View view) {
            new AmbilWarnaDialog(FragmentColor.this.getActivity(), FragmentColor.this.bColor, new listener2()).show();
        }
    }

    class color_click implements OnClickListener {
        color_click() {
        }

        public void onClick(View view) {
            if (!FragmentColor.this.hex.equals("")) {
                FragmentColor.this.getImage.ongetPosition(0, "Color", FragmentColor.this.hex);
            }
        }
    }

    class listener3 implements OnAmbilWarnaListener {
        listener3() {
        }

        public void onOk(AmbilWarnaDialog dialog, int color) {
            FragmentColor.this.updateColor(color);
        }

        public void onCancel(AmbilWarnaDialog dialog) {
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.getImage = (OnGetImageOnTouch) getActivity();
        View view = inflater.inflate(R.layout.fragment_color, container, false);
        this.img_color = view.findViewById(R.id.img_color);
        DisplayMetrics dimension = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dimension);
        this.screenWidth = (float) dimension.widthPixels;
        view.findViewById(R.id.img_colorPicker).setOnClickListener(new listener1());
        this.img_color.setOnClickListener(new color_click());
        ((TextView) view.findViewById(R.id.textH)).setTypeface(Constants.getHeaderTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.txtGal)).setTypeface(Constants.getTextTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.img_)).setTypeface(Constants.getTextTypeface(getActivity()));
        return view;
    }

    public void setMenuVisibility(boolean visible) {
        if (visible) {
            try {
                new AmbilWarnaDialog(getActivity(), this.bColor, new listener3()).show();
            } catch (NullPointerException e) {
            }
        }
        super.setMenuVisibility(visible);
    }

    private void updateColor(int color) {
        this.bColor = color;
        this.hex = Integer.toHexString(color);
        this.img_color.setBackgroundColor(Color.parseColor("#" + this.hex));
        this.getImage.ongetPosition(0, "Color", this.hex);
    }
}
