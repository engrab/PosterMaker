package ab.cd.ef.postermaker.crop;

public class EdgePair {
    public Edge primary;
    public Edge secondary;

    public EdgePair(Edge edge1, Edge edge2) {
        this.primary = edge1;
        this.secondary = edge2;
    }
}
