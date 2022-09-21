package com.amt.postermaker.graphicdesign.flyer.bannermaker.crop;

import android.graphics.Rect;

abstract class HandleHelper {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1.0f;
    private EdgePair mActiveEdges = new EdgePair(this.mHorizontalEdge, this.mVerticalEdge);
    private Edge mHorizontalEdge;
    private Edge mVerticalEdge;

    abstract void updateCropWindow(float f, float f2, float f3, Rect rect, float f4);

    HandleHelper(Edge horizontalEdge, Edge verticalEdge) {
        this.mHorizontalEdge = horizontalEdge;
        this.mVerticalEdge = verticalEdge;
    }

    void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        EdgePair activeEdges = getActiveEdges();
        Edge primaryEdge = activeEdges.primary;
        Edge secondaryEdge = activeEdges.secondary;
        if (primaryEdge != null) {
            primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
        if (secondaryEdge != null) {
            secondaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, UNFIXED_ASPECT_RATIO_CONSTANT);
        }
    }

    EdgePair getActiveEdges() {
        return this.mActiveEdges;
    }

    EdgePair getActiveEdges(float x, float y, float targetAspectRatio) {
        if (getAspectRatio(x, y) > targetAspectRatio) {
            this.mActiveEdges.primary = this.mVerticalEdge;
            this.mActiveEdges.secondary = this.mHorizontalEdge;
        } else {
            this.mActiveEdges.primary = this.mHorizontalEdge;
            this.mActiveEdges.secondary = this.mVerticalEdge;
        }
        return this.mActiveEdges;
    }

    private float getAspectRatio(float x, float y) {
        float coordinate = this.mVerticalEdge == Edge.LEFT ? x : Edge.LEFT.getCoordinate();
        float coordinate2 = this.mHorizontalEdge == Edge.TOP ? y : Edge.TOP.getCoordinate();
        if (this.mVerticalEdge != Edge.RIGHT) {
            x = Edge.RIGHT.getCoordinate();
        }
        if (this.mHorizontalEdge != Edge.BOTTOM) {
            y = Edge.BOTTOM.getCoordinate();
        }
        return AspectRatioUtil.calculateAspectRatio(coordinate, coordinate2, x, y);
    }
}
