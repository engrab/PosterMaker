package com.amt.postermaker.graphicdesign.flyer.bannermaker.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.msl.demo.view.ImageUtils;
import com.msl.demo.view.ResizableStickerView;
import com.msl.textmodule.AutoResizeTextView;
import com.msl.textmodule.AutofitTextRel;
import com.woxthebox.draglistview.DragItemAdapter;
import java.util.ArrayList;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.R;
import com.amt.postermaker.graphicdesign.flyer.bannermaker.main.PosterActivity;

public class ItemAdapter extends DragItemAdapter<Pair<Long, View>, ItemAdapter.ViewHolder> {
    Activity activity;
    public boolean mDragOnLongPress;
    public int mGrabHandleId;
    private int mLayoutId;

    class ViewHolder extends DragItemAdapter.ViewHolder {
        ImageView img_lock;
        ImageView mImage;
        TextView mText;
        AutoResizeTextView textView;

        public void onItemClicked(View view) {
        }

        public boolean onItemLongClicked(View view) {
            return true;
        }

        ViewHolder(View view) {
            super(view, ItemAdapter.this.mGrabHandleId, ItemAdapter.this.mDragOnLongPress);
            this.mText = (TextView) view.findViewById(R.id.text);
            this.mImage = (ImageView) view.findViewById(R.id.image1);
            this.img_lock = (ImageView) view.findViewById(R.id.img_lock);
            this.textView = (AutoResizeTextView) view.findViewById(R.id.auto_fit_edit_text);
        }
    }

    public ItemAdapter(Activity activity2, ArrayList<Pair<Long, View>> arrayList, int i, int i2, boolean z) {
        this.mLayoutId = i;
        this.mGrabHandleId = i2;
        this.activity = activity2;
        this.mDragOnLongPress = z;
        setItemList(arrayList);
    }

    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.mLayoutId, viewGroup, false));
    }

    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        final View view = (View) ((Pair) this.mItemList.get(i)).second;
        try {
            if (view instanceof ResizableStickerView) {
                View childAt = ((ResizableStickerView) view).getChildAt(1);
                Bitmap createBitmap = Bitmap.createBitmap(childAt.getWidth(), childAt.getHeight(), Config.ARGB_8888);
                childAt.draw(new Canvas(createBitmap));
                float[] fArr = new float[9];
                ((ImageView) childAt).getImageMatrix().getValues(fArr);
                float f = fArr[0];
                float f2 = fArr[4];
                Drawable drawable = ((ImageView) childAt).getDrawable();
                int intrinsicWidth = drawable.getIntrinsicWidth();
                int intrinsicHeight = drawable.getIntrinsicHeight();
                int round = Math.round(((float) intrinsicWidth) * f);
                int round2 = Math.round(((float) intrinsicHeight) * f2);
                viewHolder.mImage.setImageBitmap(Bitmap.createBitmap(createBitmap, (createBitmap.getWidth() - round) / 2, (createBitmap.getHeight() - round2) / 2, round, round2));
                viewHolder.mImage.setRotationY(childAt.getRotationY());
                viewHolder.mImage.setTag(this.mItemList.get(i));
                viewHolder.mImage.setAlpha(1.0f);
                viewHolder.textView.setText(" ");
            }
            if (view instanceof AutofitTextRel) {
                viewHolder.textView.setText(((AutoResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getText());
                viewHolder.textView.setTypeface(((AutoResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getTypeface());
                viewHolder.textView.setTextColor(((AutoResizeTextView) ((AutofitTextRel) view).getChildAt(2)).getTextColors());
                viewHolder.textView.setTextSize(400.0f);
                viewHolder.textView.setGravity(17);
                viewHolder.textView.setMinTextSize(10.0f);
                if (((AutofitTextRel) view).getTextInfo().getBG_COLOR() != 0) {
                    Bitmap createBitmap2 = Bitmap.createBitmap(150, 150, Config.ARGB_8888);
                    new Canvas(createBitmap2).drawColor(((AutofitTextRel) view).getTextInfo().getBG_COLOR());
                    viewHolder.mImage.setImageBitmap(createBitmap2);
                    viewHolder.mImage.setAlpha(((float) ((AutofitTextRel) view).getTextInfo().getBG_ALPHA()) / 255.0f);
                } else if (!((AutofitTextRel) view).getTextInfo().getBG_DRAWABLE().equals("0")) {
                    viewHolder.mImage.setImageBitmap(ImageUtils.getTiledBitmap(this.activity, this.activity.getResources().getIdentifier(((AutofitTextRel) view).getTextInfo().getBG_DRAWABLE(), "drawable", this.activity.getPackageName()), 150, 150));
                    viewHolder.mImage.setAlpha(((float) ((AutofitTextRel) view).getTextInfo().getBG_ALPHA()) / 255.0f);
                } else {
                    viewHolder.mImage.setAlpha(1.0f);
                    viewHolder.mImage.setImageResource(R.drawable.trans);
                }
            }
        } catch (Exception | OutOfMemoryError e) {
            e.printStackTrace();
        }
        if (view instanceof ResizableStickerView) {
            if (((ResizableStickerView) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
            }
        }
        if (view instanceof AutofitTextRel) {
            if (((AutofitTextRel) view).isMultiTouchEnabled) {
                viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
            } else {
                viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
            }
        }

        viewHolder.img_lock.setOnClickListener(new OnClickListener() {
            public void onClick(View view2)
            {
                if (view instanceof ResizableStickerView) {
                    if (((ResizableStickerView) view).isMultiTouchEnabled) {
                        ((ResizableStickerView) view).isMultiTouchEnabled = ((ResizableStickerView) view).setDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
                    } else {
                        ((ResizableStickerView) view).isMultiTouchEnabled = ((ResizableStickerView) view).setDefaultTouchListener(true);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
                    }
                }
                if (view instanceof AutofitTextRel)
                {
                    if (((AutofitTextRel) view).isMultiTouchEnabled) {
                        ((AutofitTextRel) view).isMultiTouchEnabled = ((AutofitTextRel) view).setDefaultTouchListener(false);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_lock);
                    } else {
                        ((AutofitTextRel) view).isMultiTouchEnabled = ((AutofitTextRel) view).setDefaultTouchListener(true);
                        viewHolder.img_lock.setImageResource(R.drawable.ic_unlock);
                    }
                }
                ((PosterActivity) ItemAdapter.this.activity).listFragment.getLayoutChild(false);


            }
        });
    }

    public long getUniqueItemId(int i) {
        return ((Long) ((Pair) this.mItemList.get(i)).first).longValue();
    }
}
