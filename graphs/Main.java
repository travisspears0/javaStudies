package graphs;

import static sockets.Server.random;

public class Main {
    
    public static String NEWLINE = "\n\r";
        
    public static void main(String args[]) {
        
        final String fileName = "graph.txt" ;
        Graph graph = new GraphDijkstraList();
        graph.generateGraphToFile(random.nextInt(800)+1200, random.nextInt(400000)+100000, fileName);
        
        System.out.println(graph);
        System.out.println("Dijkstra on list:");
        Main.testGraph(fileName, new GraphDijkstraList());
        System.out.println("Dijkstra on queue:");
        Main.testGraph(fileName, new GraphDijkstraQueue());
        System.out.println("Bellman-Ford:");
        Main.testGraph(fileName, new GraphBellmanFord());
        
    }
    
    public static void testGraph(String fileName, Graph g) {
        long time = System.nanoTime();
        g.readGraphFromFile(fileName);
        System.out.println("-Shortest path length: "+g.getShortestPath());
        System.out.println("-Path tracked: " + g.trackPath());
        long result = (System.nanoTime()-time)/1000000;
        System.out.println("-Time elapsed: " + result);
    }
    
}
