package first.graph;

public class Main {
    
    public static String NEWLINE = "\n\r";
        
    public static void main(String args[]) {
        
        final String fileName = "graph.txt" ;
        new GraphDijkstra().generateGraphToFile(2000, 500000, fileName);
        
        System.out.println("Dijkstra:");
        Main.testGraph(fileName, new GraphDijkstra());
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
