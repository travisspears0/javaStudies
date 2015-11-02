package graphs;

import java.util.Comparator;

public class GraphPointComaparator implements Comparator<GraphPoint> {

    @Override
    public int compare(GraphPoint o1, GraphPoint o2) {
        return o1.getValue()-o2.getValue();
        /*if( o1.getValue() > o2.getValue() ) {
            return 1;
        }
        if( o1.getValue() < o2.getValue() ) {
            return -1;
        }
        return 0;*/
    }
    
}
