package sockets;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import graphs.Graph;
import graphs.GraphConnection;
import graphs.GraphDijkstraQueue;

public class SerializableGraph implements Serializable {
    
    private final List<Integer[]> connections = new ArrayList<>();
    
    public void encode(Graph graph) {
        for(GraphConnection con : graph.getConnections()) {
            Integer[] i = {con.getPointA().getId(),con.getValue(),con.getPointB().getId()};
            this.connections.add(i);
        }
        System.out.println("encoded " + this.connections.size() + "," + graph.getConnections().size());
    }
    
    public Graph decode() {
        Graph resultGraph = new GraphDijkstraQueue();
        for(int i=0 ; i<this.connections.size() ; ++i) {
            Integer[] tab = this.connections.get(i);
            resultGraph.addConnection(tab[0],tab[2],tab[1]);
        }
        return resultGraph;
    }
    
}
