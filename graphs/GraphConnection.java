package graphs;

public class GraphConnection {
    
    private final GraphPoint pointA;
    private final GraphPoint pointB;
    private final int value;
    
    public GraphConnection(GraphPoint a, GraphPoint b, int value) {
        this.pointA = a;
        this.pointB = b;
        this.value = value;
    }

    public GraphPoint getPointA() {
        return pointA;
    }

    public GraphPoint getPointB() {
        return pointB;
    }

    public int getValue() {
        return value;
    }
    
}
