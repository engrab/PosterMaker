package ab.cd.ef.postermaker.main;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;

import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.io.File;

import ab.cd.ef.postermaker.R;

public class FragmentImage extends Fragment {
    private static final int SELECT_PICTURE_FROM_CAMERA = 9062;
    private static final int SELECT_PICTURE_FROM_GALLERY = 9072;
    File f9f;

    class camClick implements OnClickListener {
        camClick() {
        }

        public void onClick(View view) {
            Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
            FragmentImage.this.f9f = new File(Environment.getExternalStorageDirectory(), "temp.jpg");
            intent.putExtra("output", Uri.fromFile(FragmentImage.this.f9f));
            FragmentImage.this.getActivity().startActivityForResult(intent, FragmentImage.SELECT_PICTURE_FROM_CAMERA);
        }
    }

    class galClick implements OnClickListener {
        galClick() {
        }

        public void onClick(View view) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction("android.intent.action.GET_CONTENT");
            FragmentImage.this.getActivity().startActivityForResult(Intent.createChooser(intent, FragmentImage.this.getString(R.string.select_picture).toString()), FragmentImage.SELECT_PICTURE_FROM_GALLERY);
        }
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image, container, false);
        StrictMode.VmPolicy.Builder builder= new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        ((Button) view.findViewById(R.id.btn_cam)).setOnClickListener(new camClick());
        ((Button) view.findViewById(R.id.btn_gal)).setOnClickListener(new galClick());
        ((TextView) view.findViewById(R.id.textH)).setTypeface(Constants.getHeaderTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.txtCam)).setTypeface(Constants.getTextTypeface(getActivity()));
        ((TextView) view.findViewById(R.id.txtGal)).setTypeface(Constants.getTextTypeface(getActivity()));
        return view;
    }
}
