package com.amt.postermaker.graphicdesign.flyer.bannermaker.utility;


import android.view.View;

import androidx.viewpager.widget.ViewPager;

public abstract class BaseTransformer implements ViewPager.PageTransformer {
    protected abstract void onTransform(View view, float f);

    public void transformPage(View view, float position) {
        onPreTransform(view, position);
        onTransform(view, position);
        onPostTransform(view, position);
    }

    protected boolean hideOffscreenPages() {
        return true;
    }

    protected boolean isPagingEnabled() {
        return false;
    }

    protected void onPreTransform(View view, float position) {
        float f = 0.0f;
        float f2 = 0.0f;
        float width = (float) view.getWidth();
        view.setRotationX(0.0f);
        view.setRotationY(0.0f);
        view.setRotation(0.0f);
        view.setScaleX(1.0f);
        view.setScaleY(1.0f);
        view.setPivotX(0.0f);
        view.setPivotY(0.0f);
        view.setTranslationY(0.0f);
        if (!isPagingEnabled()) {
            f = (-width) * position;
        }
        view.setTranslationX(f);
        if (hideOffscreenPages()) {
            if (position > -1.0f && position < 1.0f) {
                f2 = 1.0f;
            }
            view.setAlpha(f2);
            return;
        }
        view.setAlpha(1.0f);
    }

    protected void onPostTransform(View view, float position) {
    }
}
