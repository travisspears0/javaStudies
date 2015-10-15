package first.graph;

public class Main {
    
    public static final String NEWLINE = "\r\n";
    
    public static void main(String args[]) {
        
        FileManager fileManager = new FileManager("graph2.txt");
        Graph g = GraphManager.generateGraph(5, 50);
        System.out.println(g);
        fileManager.saveData(g.toString());
        String x = fileManager.readData();
        Graph readGraph = GraphManager.readGraphFromString(x);
        System.out.println(readGraph);
        System.out.println(":::"+readGraph.getShortestPath());
    }
    
}
