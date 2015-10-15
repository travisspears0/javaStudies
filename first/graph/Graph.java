package first.graph;

import java.util.ArrayList;
import java.util.List;

public class Graph {
    
    private final List<GraphPoint> points = new ArrayList<>();
    private final List<GraphConnection> connections = new ArrayList<>();

    public List<GraphPoint> getPoints() {
        List<GraphPoint> copy = new ArrayList<>(this.points);
        return copy;
    }
    
    public boolean addPoint(GraphPoint point) {
        if( this.getPointById(point.getId()) != null ) {
            return false;
        }
        this.points.add(point);
        return true;
    }

    public List<GraphConnection> getConnections() {
        List<GraphConnection> copy = new ArrayList<>(this.connections);
        return copy;
    }
    
    public boolean addConnection(GraphPoint a, GraphPoint b, int value) {
        for(int i=0 ; i<this.connections.size() ; ++i) {
            GraphConnection con = this.connections.get(i);
            if( con.getPointA() == a && con.getPointB() == b ||
                con.getPointB() == a && con.getPointA() == b ) {
                return false;
            }
        }
        this.connections.add(new GraphConnection(a, b, value));
        return true;
    }
    
    public void updateNeighboursValues(GraphPoint point) {
        for( GraphConnection con : this.connections ) {
            if( con.getPointA() == point && !con.getPointB().isVisited() ) {
                if( con.getPointB().getValue() == 0 ||
                    con.getPointA().getValue()+con.getValue() < con.getPointB().getValue() ) {
                    con.getPointB().setValue(con.getPointA().getValue()+con.getValue());
                    System.out.println("setting value of " + con.getPointB().getId() + 
                        " to " + (con.getPointA().getValue()+con.getValue()));
                }
            } else if( con.getPointB() == point && !con.getPointA().isVisited() ) {
                if( con.getPointA().getValue() == 0 ||
                    con.getPointB().getValue()+con.getValue() < con.getPointA().getValue() ) {
                    con.getPointA().setValue(con.getPointB().getValue()+con.getValue());
                    System.out.println("setting value of " + con.getPointA().getId() + 
                        " to " + (con.getPointB().getValue()+con.getValue()));
                }
            }
        }
    }
    
    public GraphPoint getStartPoint() {
        return this.points.get(0);
    }
    
    public GraphPoint getEndPoint() {
        return this.points.get(this.points.size()-1);
    }
    
    public int getShortestPath(int startId, int endId) {
        return this.getShortestPath(this.getPointById(startId), this.getPointById(endId));
    }
    
    public int getShortestPath() {
        return this.getShortestPath(this.getStartPoint(), this.getEndPoint());
    }
    
    public int getShortestPath(GraphPoint start, GraphPoint end) {
        List<GraphPoint> freePoints = new ArrayList<>(this.points);
        List<GraphPoint> visitedPoints = new ArrayList<>();
        GraphPoint currentPoint = start;
        while( currentPoint != end ) {
            currentPoint.setVisited(true);
            freePoints.remove(currentPoint);
            visitedPoints.add(currentPoint);
            this.updateNeighboursValues(currentPoint);
            currentPoint = this.getNextGraphPoint(freePoints);
            System.out.println("moved to point " + currentPoint.getId());
        }
        int result = currentPoint.getValue();
        for( GraphPoint point : this.points ) {
            point.setValue(0);
            point.setVisited(false);
        }
        return result;
    }
    
    private GraphPoint getNextGraphPoint(List<GraphPoint> points) {
        GraphPoint next = null;
        for( GraphPoint p : points ) {
            if( next == null ) {
                next = p;
                continue;
            }
            if( p.getValue() != 0 && p.getValue() < next.getValue() ) {
                next = p;
            }
        }
        return next;
    }
    
    public GraphPoint getPointById(int id) {
        for( GraphPoint point : this.points ) {
            if( point.getId() == id ) {
                return point;
            }
        }
        return null;
    }
    
    @Override
    public String toString() {
        String result = "";
        for(GraphConnection con : this.connections) {
            result +=   con.getPointA().getId() + "," + 
                        con.getValue() + "," + 
                        con.getPointB().getId() + Main.NEWLINE;
        }
        return result;
    }
    
}
