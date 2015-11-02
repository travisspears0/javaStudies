package graphs;

import java.util.PriorityQueue;

public class GraphDijkstraQueue extends Graph {
    
    @Override
    public int getRealShortestPath() {
        return this.getRealShortestPath(this.getStartPoint(), this.getEndPoint());
    }
    
    @Override
    public int getRealShortestPath(GraphPoint start, GraphPoint end) {
        for( GraphPoint point : this.points ) {
            point.setValue(0);
            point.setVisited(false);
        }
        try {
            GraphPointComaparator comparator = new GraphPointComaparator();
            PriorityQueue<GraphPoint> freePoints = new PriorityQueue<>(comparator);
            freePoints.addAll(this.points);
            PriorityQueue<GraphPoint> visitedPoints = new PriorityQueue<>(comparator);
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
    
    private GraphPoint getNextGraphPoint(PriorityQueue<GraphPoint> points) {
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
