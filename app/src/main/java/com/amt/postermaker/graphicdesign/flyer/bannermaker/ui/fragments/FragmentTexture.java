package com.amt.postermaker.graphicdesign.flyer.bannermaker.ui.fragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;

import androidx.fragment.app.Fragment;

import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter.AdapterFrame;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.interfaces.OnGetImageOnTouch;

public class FragmentTexture extends Fragment {
    Editor editor;
    OnGetImageOnTouch getImage;
    private GridView gridView;
    private BroadcastReceiver myBroadcast_update = new broadcast();
    int pos = 0;
    SharedPreferences preferences;
    SharedPreferences prefs;

    class gridItem implements OnItemClickListener {
        gridItem() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentTexture.this.getImage.ongetPosition(position, "Texture", "");
        }
    }

    class broadcast extends BroadcastReceiver {
        broadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            FragmentTexture.this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (FragmentTexture.this.preferences.getBoolean("isAdsDisabled", false)) {
                FragmentTexture.this.editor.putString("rating123", "yes");
                FragmentTexture.this.editor.putString("purchase", "yes");
                FragmentTexture.this.editor.commit();
                FragmentTexture.this.gridView.setAdapter(new AdapterFrame(FragmentTexture.this.getActivity(), "Texture", FragmentTexture.this.prefs));
                FragmentTexture.this.getImage.ongetPosition(FragmentTexture.this.pos, "Texture", "");
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_backgund, container, false);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        getActivity().registerReceiver(this.myBroadcast_update, new IntentFilter("Remove_Watermark"));
        this.getImage = (OnGetImageOnTouch) getActivity();
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        this.gridView.setAdapter(new AdapterFrame(getActivity(), "Texture", this.prefs));
        this.gridView.setOnItemClickListener(new gridItem());
        return view;
    }

    public void setMenuVisibility(boolean visible) {
        if (visible) {
            try {
                this.gridView.setAdapter(new AdapterFrame(getActivity(), "Texture", this.prefs));
            } catch (NullPointerException e) {
            }
        }
        super.setMenuVisibility(visible);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(this.myBroadcast_update);
        } catch (IllegalArgumentException e5) {
            e5.printStackTrace();
        } catch (RuntimeException e32) {
            e32.printStackTrace();
        } catch (Exception e42) {
            e42.printStackTrace();
        }
    }
}
