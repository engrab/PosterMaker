package com.amt.postermaker.graphicdesign.flyer.bannermaker.gotoCrop;

import android.graphics.Rect;

public enum Handle {
    TOP_LEFT(new HelperCornerHandle(Edge.TOP, Edge.LEFT)),
    TOP_RIGHT(new HelperCornerHandle(Edge.TOP, Edge.RIGHT)),
    BOTTOM_LEFT(new HelperCornerHandle(Edge.BOTTOM, Edge.LEFT)),
    BOTTOM_RIGHT(new HelperCornerHandle(Edge.BOTTOM, Edge.RIGHT)),
    LEFT(new VerticalHandleHelper(Edge.LEFT)),
    TOP(new HorizontalHandleHelper(Edge.TOP)),
    RIGHT(new VerticalHandleHelper(Edge.RIGHT)),
    BOTTOM(new HorizontalHandleHelper(Edge.BOTTOM)),
    CENTER(new HelperCenterHandle());
    
    private HandleHelper mHelper;

    private Handle(HandleHelper helper) {
        this.mHelper = helper;
    }

    public void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, imageRect, snapRadius);
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, targetAspectRatio, imageRect, snapRadius);
    }
}
