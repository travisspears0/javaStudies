package graphs;

public class GraphBellmanFord extends Graph {
    
    @Override
    public int getRealShortestPath() {
        return this.getRealShortestPath(this.getStartPoint(),this.getEndPoint());
    }
    
    @Override
    public int getRealShortestPath(GraphPoint start, GraphPoint b) {
        start.setVisited(true);
        boolean changed = true;
        while( changed ) {
            changed = false;
            for( GraphPoint p : this.points ) {
                if( !p.isVisited() ) {
                    continue;
                }
                changed = this.updateNeighbours(p) || changed;
            }
        }
        return this.getEndPoint().getValue();
    }
    
    private boolean updateNeighbours(GraphPoint point) {
        boolean result = false;
        //System.out.println("point " + point.getId());
        for( GraphConnection con : this.connections ) {
            if( con.getPointA() == point &&
                    (con.getPointB().getValue() > point.getValue()+con.getValue() ||
                    !con.getPointB().isVisited())) {
                con.getPointB().setValue(point.getValue()+con.getValue());
                con.getPointB().setVisited(true);
                result = true;
                con.getPointB().setPrev(point);
                /*System.out.println("setting value of " + con.getPointB().getId() + 
                    " to " + (con.getPointB().getValue()));*/
            }
            else if( con.getPointB() == point &&
                    (con.getPointA().getValue() > point.getValue()+con.getValue() ||
                    !con.getPointA().isVisited())) {
                con.getPointA().setValue(point.getValue()+con.getValue());
                con.getPointA().setVisited(true);
                result = true;
                con.getPointA().setPrev(point);
                /*System.out.println("setting value of " + con.getPointA().getId() + 
                    " to " + (con.getPointA().getValue()));*/
            }
        }
        return result;
    }
}
