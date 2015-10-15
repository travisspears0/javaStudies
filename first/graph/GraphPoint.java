package first.graph;

public class GraphPoint {
    
    private static int CURRENT_ID=0;
    private final int id;
    private int value=0;
    private boolean visited = false;
    
    public GraphPoint() {
        this.id = ++CURRENT_ID;
    }
    
    public GraphPoint(int id) {
        this.id = id;
    }
    
    public int getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }
    
}
