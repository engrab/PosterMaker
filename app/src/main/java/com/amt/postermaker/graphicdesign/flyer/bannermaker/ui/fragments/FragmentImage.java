package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.utility.Constants;

public class FragmentImage extends Fragment {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    File file;



    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        view.findViewById(R.id.btn_cam).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra("output", Uri.fromFile(file));
                getActivity().startActivityForResult(intent, FragmentImage.SELECT_PICTURE_FROM_CAMERA);
            }
        });
        view.findViewById(R.id.btn_gal).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                getActivity().startActivityForResult(Intent.createChooser(intent, getString(R.string.select_picture)), FragmentImage.SELECT_PICTURE_FROM_GALLERY);

            }
        });
        ((TextView) view.findViewById(R.id.textH)).setTypeface(Constants.getHeaderTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.txtCam)).setTypeface(Constants.getTextTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.txtGal)).setTypeface(Constants.getTextTypeface(getActivity()));
        return view;
    }
}
