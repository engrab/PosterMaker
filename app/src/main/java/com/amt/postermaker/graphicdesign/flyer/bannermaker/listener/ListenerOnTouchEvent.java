package com.amt.postermaker.graphicdesign.flyer.bannermaker.listener;

import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;

public class ListenerOnTouchEvent implements OnTouchListener {
    private final OnClickListener clickListener;
    private View downView;
    private Handler handler = new Handler();
    private Runnable handlerRunnable = new C02451();
    private int initialInterval;
    private final int normalInterval;

    class C02451 implements Runnable {
        C02451() {
        }

        public void run() {
            ListenerOnTouchEvent.this.handler.postDelayed(this, (long) ListenerOnTouchEvent.this.normalInterval);
            ListenerOnTouchEvent.this.clickListener.onClick(ListenerOnTouchEvent.this.downView);
        }
    }

    public ListenerOnTouchEvent(int initialInterval, int normalInterval, OnClickListener clickListener) {
        if (clickListener == null) {
            throw new IllegalArgumentException("null runnable");
        } else if (initialInterval < 0 || normalInterval < 0) {
            throw new IllegalArgumentException("negative interval");
        } else {
            this.initialInterval = initialInterval;
            this.normalInterval = normalInterval;
            this.clickListener = clickListener;
        }
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (motionEvent.getAction()) {
            case 0:
                this.handler.removeCallbacks(this.handlerRunnable);
                this.handler.postDelayed(this.handlerRunnable, (long) this.initialInterval);
                this.downView = view;
                this.downView.setPressed(true);
                this.clickListener.onClick(view);
                return true;
            case 1:
            case 3:
                this.handler.removeCallbacks(this.handlerRunnable);
                this.downView.setPressed(false);
                this.downView = null;
                return true;
            default:
                return false;
        }
    }
}
