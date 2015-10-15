package first.graph;

import java.util.Random;

public class GraphManager {
    
    /**
     * generates random graph
     * @param size defined number of graph points
     * @param percentageProbabability defines percentage probability(0-100) of conection between two points
     * @return 
     */
    public static Graph generateGraph(int size, int percentageProbabability) {
        Random random = new Random();
        Graph resultGraph = new Graph();
        for( int i=0 ; i<size ; ++i ) {
            resultGraph.addPoint(new GraphPoint());
        }
        for( int i=0; i<resultGraph.getPoints().size() ; ++i ) {
            for( int j=i ; j<resultGraph.getPoints().size() ; ++j ) {
                if( i == j ) {
                    continue;
                }
                if( random.nextInt(100)+1 < percentageProbabability ) {
                    int v = random.nextInt(20)+1;
                    resultGraph.addConnection(
                        resultGraph.getPoints().get(i), 
                        resultGraph.getPoints().get(j),
                        v);
                }
            }
        }
        return resultGraph;
    }
    
    public static Graph readGraphFromString(String data) {
        Graph resultGraph = new Graph();
        String[] arrayData = data.split(Main.NEWLINE);
        for( String item : arrayData ) {
            String[] arrayItem = item.split(",");
            resultGraph.addPoint(new GraphPoint(Integer.parseInt(arrayItem[0])));
            resultGraph.addPoint(new GraphPoint(Integer.parseInt(arrayItem[2])));
            
            resultGraph.addConnection(
                resultGraph.getPointById(Integer.parseInt(arrayItem[0])), 
                resultGraph.getPointById(Integer.parseInt(arrayItem[2])),
                Integer.parseInt(arrayItem[1]));
        }
        return resultGraph;
    }
}
