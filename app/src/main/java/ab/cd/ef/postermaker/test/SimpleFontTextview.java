package ab.cd.ef.postermaker.test;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;

import ab.cd.ef.postermaker.main.Constants;

public class SimpleFontTextview extends TextView {
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
