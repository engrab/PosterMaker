package com.amt.postermaker.graphicdesign.flyer.bannermaker.gotoCrop;

public class EdgePair {
    public Edge primary;
    public Edge secondary;

    public EdgePair(Edge edge1, Edge edge2) {
        this.primary = edge1;
        this.secondary = edge2;
    }
}
