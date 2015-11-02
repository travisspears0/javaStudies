package graphs;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Graph {
    
    protected final List<GraphPoint> points = new ArrayList<>();
    protected final List<GraphConnection> connections = new ArrayList<>();
    private static final Random random = new Random();
    
    protected abstract int getRealShortestPath();
    protected abstract int getRealShortestPath(GraphPoint start, GraphPoint end);

    public int getShortestPath() {
        if(this.points.isEmpty()) {
            return 0;
        }
        return this.getRealShortestPath();
    }
    
    public boolean isEmpty() {
        return this.points.isEmpty();
    }
    
    public int getShortestPath(GraphPoint start, GraphPoint end) {
        if(this.points.isEmpty()) {
            return 0;
        }
        return this.getRealShortestPath(start, end);
    }
    
    public List<GraphPoint> getPoints() {
        List<GraphPoint> copy = new ArrayList<>(this.points);
        return copy;
    }
    
    public void replace(Graph graph) {
        this.points.clear();
        this.connections.clear();
        
        this.points.addAll(graph.getPoints());
        this.connections.addAll(graph.getConnections());
    }
    
    public boolean addPoint(GraphPoint point) {
        if( this.getPointById(point.getId()) != null ) {
            return false;
        }
        this.points.add(point);
        return true;
    }

    public List<GraphConnection> getConnections() {
        List<GraphConnection> copy = new ArrayList<>(this.connections);
        return copy;
    }
    
    public void generateGraphToFile(int size, int connectionsLimit, String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName,"UTF-8");
            for( int i=0 ; i<size ; ++i ) {
                this.addPoint(new GraphPoint());
            }
            int connectionsPerPoint = connectionsLimit/size;
            int boxSize = size/connectionsPerPoint;
            for( int i=0 ; i<size ; ++i ) {
                for( int j=0, pointTo=i ; j+boxSize<size ; j+=boxSize ) {
                    while( pointTo == i ) {
                        pointTo = Math.min(random.nextInt(boxSize)+j,size-1);
                    }
                    int v = random.nextInt(100)+1;
                    this.connections.add(new GraphConnection(
                        this.getPoints().get(i), 
                        this.getPoints().get(pointTo), 
                        v));
                    writer.println(
                        this.getPoints().get(i).getId()+","+
                        v+","+
                        this.getPoints().get(pointTo).getId());
                    pointTo=i;
                }
            }
            writer.close();
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public void saveGraphToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName,"UTF-8");
            for( GraphConnection conn : this.connections ) {
                writer.println(
                    conn.getPointA().getId()+","+
                    conn.getValue()+","+
                    conn.getPointB().getId());
            }
            writer.close();
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public static Graph generateGraph(int size, int connectionsLimit) {
        Graph resultGraph = new GraphDijkstraQueue();
        for( int i=0 ; i<size ; ++i ) {
            resultGraph.addPoint(new GraphPoint());
        }
        int connectionsPerPoint = connectionsLimit/size;
        int boxSize = size/connectionsPerPoint;
        for( int i=0 ; i<size ; ++i ) {
            for( int j=0, pointTo=i ; j+boxSize<size ; j+=boxSize ) {
                while( pointTo == i ) {
                    pointTo = Math.min(random.nextInt(boxSize)+j,size-1);
                }
                int v = random.nextInt(100)+1;
                resultGraph.connections.add(
                    new GraphConnection(
                        resultGraph.getPoints().get(i), 
                        resultGraph.getPoints().get(pointTo), 
                        v));
                pointTo=i;
            }
        }
        return resultGraph;
    }
    
    public void readGraphFromFile(String fileName) {
        this.points.clear();
        this.connections.clear();
        try {
            FileInputStream fileInputStream = new FileInputStream(fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            String line;
            while((line = reader.readLine()) != null) {
                String[] lineArr = line.split(",");
                int a = Integer.parseInt(lineArr[0]);
                int value = Integer.parseInt(lineArr[1]);
                int b = Integer.parseInt(lineArr[2]);
                this.addConnection(a, b, value);
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
    public void addConnection(int a, int b, int value) {
        GraphPoint pointA = this.getPointById(a);
        GraphPoint pointB = this.getPointById(b);
        if( pointA == null ) {
            pointA = new GraphPoint(a);
            this.addPoint(pointA);
        }
        if( pointB == null ) {
            pointB = new GraphPoint(b);
            this.addPoint(pointB);
        }
        //System.out.println("adding connection " + pointA.getId() + "-" + pointB.getId() + "["+value+"]");
        this.connections.add(new GraphConnection(pointA, pointB, value));
    }
    
    public void updateNeighboursValues(GraphPoint point) {
        for( GraphConnection con : this.connections ) {
            if( con.getPointA() == point && !con.getPointB().isVisited() ) {
                if( con.getPointB().getValue() == 0 ||
                    con.getPointA().getValue()+con.getValue() < con.getPointB().getValue() ) {
                    con.getPointB().setValue(con.getPointA().getValue()+con.getValue());
                    con.getPointB().setPrev(con.getPointA());
                    /*System.out.println("setting value of " + con.getPointB().getId() + 
                        " to " + (con.getPointA().getValue()+con.getValue()) + " and prev to " + 
                        con.getPointA().getId());*/
                }
            } else if( con.getPointB() == point && !con.getPointA().isVisited() ) {
                if( con.getPointA().getValue() == 0 ||
                    con.getPointB().getValue()+con.getValue() < con.getPointA().getValue() ) {
                    con.getPointA().setValue(con.getPointB().getValue()+con.getValue());
                    con.getPointA().setPrev(con.getPointB());
                    /*System.out.println("setting value of " + con.getPointA().getId() + 
                        " to " + (con.getPointB().getValue()+con.getValue()) + " and prev to " + 
                        con.getPointB().getId());*/
                }
            }
        }
    }
    
    public GraphPoint getStartPoint() {
        return this.points.get(0);
    }
    
    public GraphPoint getEndPoint() {
        return this.points.get(this.points.size()-1);
    }
    
    public String trackPath() {
        if(this.points.isEmpty()) {
            return "empty";
        }
        return this.trackPath(this.getEndPoint());
    }
    
    public String trackPath(GraphPoint end) {
        if(this.points.isEmpty()) {
            return "empty";
        }
        String result = "";
        GraphPoint currentPoint = end;
        while(currentPoint != null) {
            result += "[" + currentPoint.getId() + "]<-" ;
            currentPoint = currentPoint.getPrev();
        }
        result = result.substring(0,result.length()-2);
        return result;
    }
    
    public GraphPoint getPointById(int id) {
        for( GraphPoint point : this.points ) {
            if( point.getId() == id ) {
                return point;
            }
        }
        return null;
    }
    
    public void writeToFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName,"UTF-8");
            for( GraphConnection con : this.connections ) {
                writer.println(
                    con.getPointA().getId() + "," + 
                    con.getValue() + "," + 
                    con.getPointB().getId());
            }
            writer.close();
        } catch(FileNotFoundException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
    
    public String getGraph() {
        String result = "";
        for(GraphConnection con : this.connections) {
            result +=   con.getPointA().getId() + "," + 
                        con.getValue() + "," + 
                        con.getPointB().getId() + Main.NEWLINE;
        }
        return result;
    }
    
    
    @Override
    public String toString() {
        return "graph, points: " + this.points.size() + ", connections: " + this.connections.size();
    }
    
}
