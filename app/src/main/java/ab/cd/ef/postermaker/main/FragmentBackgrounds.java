package ab.cd.ef.postermaker.main;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
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

import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.adapter.FrameAdapter;

public class FragmentBackgrounds extends Fragment {
    Editor editor;
    OnGetImageOnTouch getImage;
    private GridView gridView;
    int pos = 0;
    SharedPreferences preferences;
    SharedPreferences prefs;

    class clickItem implements OnItemClickListener {
        clickItem() {
        }

        public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
            FragmentBackgrounds.this.getImage.ongetPosition(position, "Background", "");
        }
    }

    class broadcast extends BroadcastReceiver {
        broadcast() {
        }

        public void onReceive(Context context, Intent intent) {
            FragmentBackgrounds.this.preferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (FragmentBackgrounds.this.preferences.getBoolean("isAdsDisabled", false)) {
                FragmentBackgrounds.this.editor.putString("rating123", "yes");
                FragmentBackgrounds.this.editor.putString("purchase", "yes");
                FragmentBackgrounds.this.editor.commit();
                FragmentBackgrounds.this.gridView.setAdapter(new FrameAdapter(FragmentBackgrounds.this.getActivity(), "Background", FragmentBackgrounds.this.prefs));
                FragmentBackgrounds.this.getImage.ongetPosition(FragmentBackgrounds.this.pos, "Background", "");
            }
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_backgund, container, false);
        this.editor = getActivity().getSharedPreferences("MY_PREFS_NAME", 0).edit();
        this.prefs = getActivity().getSharedPreferences("MY_PREFS_NAME", 0);
        this.getImage = (OnGetImageOnTouch) getActivity();
        this.gridView = (GridView) view.findViewById(R.id.gridview);
        this.gridView.setAdapter(new FrameAdapter(getActivity(), "Background", this.prefs));
        this.gridView.setOnItemClickListener(new clickItem());
        return view;
    }

    public void setMenuVisibility(boolean visible) {
        if (visible) {
            try {
                this.gridView.setAdapter(new FrameAdapter(getActivity(), "Background", this.prefs));
            } catch (NullPointerException e) {
            }
        }
        super.setMenuVisibility(visible);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    }

    public void onDestroy() {
        super.onDestroy();
    }
}
