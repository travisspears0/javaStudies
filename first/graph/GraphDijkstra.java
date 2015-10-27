package first.graph;

import java.util.ArrayList;
import java.util.List;

public class GraphDijkstra extends Graph {
    
    @Override
    public int getShortestPath() {
        return this.getShortestPath(this.getStartPoint(), this.getEndPoint());
    }
    
    @Override
    public int getShortestPath(GraphPoint start, GraphPoint end) {
        for( GraphPoint point : this.points ) {
            point.setValue(0);
            point.setVisited(false);
        }
        try {
            List<GraphPoint> freePoints = new ArrayList<>(this.points);
            List<GraphPoint> visitedPoints = new ArrayList<>();
            GraphPoint currentPoint = start;
            while( currentPoint != end ) {
                currentPoint.setVisited(true);
                freePoints.remove(currentPoint);
                visitedPoints.add(currentPoint);
                this.updateNeighboursValues(currentPoint);
                currentPoint = this.getNextGraphPoint(freePoints);
                //System.out.println("switched to point " + currentPoint.getId() + "[prev="+currentPoint.getPrev().getId()+"]");
            }
            return currentPoint.getValue();
        } catch( NullPointerException e ) {
            System.out.println("graph incomplete");
            return -1;
        }
    }
    
    private GraphPoint getNextGraphPoint(List<GraphPoint> points) {
        GraphPoint next = null;
        for( GraphPoint p : points ) {
            if( next == null ) {
                if( p.getValue()!=0 )next = p;
                continue;
            }
            if( p.getValue() != 0 && p.getValue() < next.getValue() ) {
                next = p;
            }
        }
        return next;
    }
    
}
