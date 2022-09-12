package ab.cd.ef.postermaker.main;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import ab.cd.ef.postermaker.R;


public class ShareActivity extends Activity implements View.OnClickListener {
    ImageView imageView, shareImage, exit, facebook, whatsapp, twitter, instagram;
    Uri imageUri;
    String image_path;
    private Intent share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // add menu for home button

        imageView = (ImageView) findViewById(R.id.imageView);
        shareImage = (ImageView) findViewById(R.id.shareImage);
        //exit = (ImageView) findViewById(R.id.exit);
        facebook = (ImageView) findViewById(R.id.facebook);
        whatsapp = (ImageView) findViewById(R.id.whatsapp);
        twitter = (ImageView) findViewById(R.id.twitter);
        instagram = (ImageView) findViewById(R.id.instagram);
        // exit.setOnClickListener(this);
        shareImage.setOnClickListener(this);
        facebook.setOnClickListener(this);
        whatsapp.setOnClickListener(this);
        twitter.setOnClickListener(this);
        instagram.setOnClickListener(this);

        try {
            image_path = getIntent().getStringExtra("uri");
            imageUri = Uri.parse(image_path);
            imageView.setImageURI(imageUri);
        }
        catch (Exception e){
            Toast.makeText(this, "error : "+e, Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.facebook:
                share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.setPackage("com.facebook.katana");//package name of the app

                try {
                    //startActivity(share);
                    startActivity(Intent.createChooser(share, "Share Image with Facebook"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Facebook have not been installed.", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.whatsapp:
                share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.setPackage("com.whatsapp");//package name of the app

                try {
                    //startActivity(share);
                    startActivity(Intent.createChooser(share, "Share Image with WhatsApp"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Whatsapp have not been installed.", Toast.LENGTH_SHORT).show();

                }

                break;
            case R.id.twitter:
                share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.setPackage("com.twitter.android");//package name of the app

                try {
                   // startActivity(share);
                    startActivity(Intent.createChooser(share, "Share Image with Twitter"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Twitter have not been installed.", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.instagram:
                share = new Intent(Intent.ACTION_SEND);
                share.setType("image/jpeg");
                share.putExtra(Intent.EXTRA_STREAM, imageUri);
                share.setPackage("com.instagram.android");//package name of the app

                try {
                    //startActivity(share);
                    startActivity(Intent.createChooser(share, "Share Image with Instagram"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(this, "Instagram have not been installed.", Toast.LENGTH_SHORT).show();

                }
                break;
            case R.id.shareImage:
                try {

                    Intent shareIntent = new Intent();
                    shareIntent.setAction(Intent.ACTION_SEND);
                    shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                    shareIntent.setType("image/jpeg");
                    startActivity(Intent.createChooser(shareIntent, "Share Image to Other..."));
                } catch (Exception e) {

                }
                break;


        }
    }

    private void exitto() {
        try {
            new AlertDialog.Builder(ShareActivity.this)
                    .setTitle("Exit !!!")
                    .setMessage("Do you really want to Exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
        } catch (Exception e) {


        }


    }

    private void Intentto() {
        Intent intent = new Intent(ShareActivity.this, MainActivity.class);
        startActivity(intent);
    }



    @Override
    protected void onPause() {
        super.onPause();
    }

    public boolean hasInternet() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        if (connectivityManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED || connectivityManager.getNetworkInfo(1).getState() == NetworkInfo.State.CONNECTED) {
            return true;
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        exitto();


    }

}
/*case R.id.exit:
                exitto();
                break;*/