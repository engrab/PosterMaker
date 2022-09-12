package ab.cd.ef.postermaker.create;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import jp.co.cyberagent.android.gpuimage.GPUImage;
import jp.co.cyberagent.android.gpuimage.GPUImageGaussianBlurFilter;
import ab.cd.ef.postermaker.R;
import ab.cd.ef.postermaker.main.PosterActivity;
import ab.cd.ef.postermaker.utility.GPUImageFilterTools.FilterAdjuster;

public class BlurOperationAsync extends AsyncTask<String, Void, String> {
    ImageView background_blur;
    Bitmap btmp;
    Activity context;
    private ProgressDialog pd;

    public BlurOperationAsync(PosterActivity posterActivity, Bitmap bit, ImageView background_blur) {
        this.context = posterActivity;
        this.btmp = bit;
        this.background_blur = background_blur;
    }

    protected void onPreExecute() {
        this.pd = new ProgressDialog(this.context);
        this.pd.setMessage(this.context.getResources().getString(R.string.plzwait));
        this.pd.setCancelable(false);
        this.pd.show();
    }

    protected String doInBackground(String... params) {
        this.btmp = gaussinBlur(this.context, this.btmp);
        return "yes";
    }

    protected void onPostExecute(String result) {
        this.pd.dismiss();
        this.background_blur.setImageBitmap(this.btmp);
    }

    private Bitmap gaussinBlur(Activity activity, Bitmap bitmap) {
        GPUImage gpuImage = new GPUImage(activity);
        GPUImageGaussianBlurFilter sepiaFilter5 = new GPUImageGaussianBlurFilter();
        gpuImage.setFilter(sepiaFilter5);
        new FilterAdjuster(sepiaFilter5).adjust(100);
        gpuImage.requestRender();
        return gpuImage.getBitmapWithFilterApplied(bitmap);
    }
}
