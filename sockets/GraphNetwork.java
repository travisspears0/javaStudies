package sockets;

import graphs.Graph;
import graphs.GraphDijkstraQueue;
import graphs.GraphPoint;

public class GraphNetwork extends Graph {
    
    private final Graph graphDijkstra = new GraphDijkstraQueue();

    @Override
    protected int getRealShortestPath() {
        this.graphDijkstra.replace(this);
        return this.graphDijkstra.getShortestPath();
    }

    @Override
    protected int getRealShortestPath(GraphPoint start, GraphPoint end) {
        this.graphDijkstra.replace(this);
        return this.graphDijkstra.getShortestPath(start, end);
    }
    /*
    public boolean sendGraphToSocket(Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            int i=0;
            for(GraphConnection con : this.connections) {
                String s =  con.getPointA().getId() + "," + 
                            con.getValue() + "," + 
                            con.getPointB().getId();
                oos.writeObject(s);
                //System.out.println("sent: " + (++i) + "/" + this.connections.size());
            }
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
        return true;
    }
    */
}
