package com.amt.postermaker.graphicdesign.flyer.bannermaker.utility;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;


public class SimpleFontTextview extends androidx.appcompat.widget.AppCompatTextView {
    public SimpleFontTextview(Context context) {
        super(context);
        setTypeface(Constants.getTextTypeface(context));
    }

    public SimpleFontTextview(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        setTypeface(Constants.getTextTypeface(context));
    }

    public SimpleFontTextview(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setTypeface(Constants.getTextTypeface(context));
    }
}