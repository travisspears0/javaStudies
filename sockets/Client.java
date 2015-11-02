package sockets;

import com.thoughtworks.xstream.XStream;
import graphs.Graph;
import graphs.GraphConnection;
import graphs.GraphDijkstraQueue;
import java.io.EOFException;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {
        
        int option = -1;
        Scanner scanner = new Scanner(System.in);
        Graph graph = new GraphNetwork();
        while(option!=0) {
            System.out.println("------------------------------");
            System.out.println("    what you want to do:");
            System.out.println("    [0]exit");
            System.out.println("    [1]download graph from xml server");
            System.out.println("    [2]download graph from stream server");
            System.out.println("    [3]count the shortest path of the current graph");
            System.out.println("    [4]save graph to file graph.txt");
            System.out.println("    [5]get graph from file graph.txt");
            System.out.println("------------------------------");
            option = scanner.nextInt();
            switch(option) {
                case 1: {
                    try {
                        Graph g = getGraphFromServerXml();
                        if(g != null) {
                            graph = g;
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 2: {
                    try {
                        Graph g = getGraphFromServerStream();
                        if(g != null) {
                            graph = g;
                        }
                    } catch(Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case 3: {
                    if(graph.isEmpty()) {
                        System.out.println("graph is empty");
                        break;
                    }
                    System.out.println("-Shortest path length: "+graph.getShortestPath());
                    System.out.println("-Path tracked: " + graph.trackPath());
                    break;
                }
                case 4: {
                    if(graph.isEmpty()) {
                        System.out.println("graph is empty");
                        break;
                    }
                    graph.saveGraphToFile("graph.txt");
                    System.out.println("saved graph to file graph.txt");
                    break;
                }
                case 5: {
                    graph.readGraphFromFile("graph.txt");
                    System.out.println("graph read from file graph.txt");
                    break;
                }
                default: {
                    System.out.println("no such option");
                }
            }
        }
        
    }
    
    public static Graph getGraphFromServerStream() throws Exception {
        Graph resultGraph = new GraphDijkstraQueue();
        Socket socket=null;
        int port = ServerStream.PORT;
        System.out.println("sending request to server...");
        Object ob=null;
        try {
            socket = new Socket(Server.HOST, port);
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            while(true) {
                //System.out.println("received " + line);
                try {
                    ob = ois.readObject();
                } catch(EOFException e) {
                    break;
                }
                String[] lineArr = ob.toString().split(",");
                int a = Integer.parseInt(lineArr[0]);
                int v = Integer.parseInt(lineArr[1]);
                int b = Integer.parseInt(lineArr[2]);
                resultGraph.addConnection(a,v,b);
            }
            ois.close();
        } catch(UnknownHostException uhe) {
            System.err.println("could not connect to "+Server.HOST+" on port "+port+", no such host");
            return null;
        } catch(ConnectException ce) {
            System.err.println("could not connect to the server");
            return null;
        }
        System.out.println("downloaded successfully: " + resultGraph);
        return resultGraph;
    }
    
    
    
    
    public static Graph getGraphFromServerXml() throws Exception {
        Graph resultGraph = new GraphDijkstraQueue();
        Socket socket = null;
        int port = ServerXml.PORT;
        System.out.println("sending request to server...");
        socket = new Socket(Server.HOST, port);
        XStream xStream = new XStream();
        ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
        Object ob = ois.readObject();
        System.out.println("got the response, parsing xml...");
        List<GraphConnection> connections = new ArrayList<>();
        connections = (List<GraphConnection>)xStream.fromXML(ob.toString());
        for( GraphConnection c : connections ) {
            resultGraph.addConnection(c.getPointA().getId(), c.getPointB().getValue(), c.getValue());
        }
        
        ois.close();
        System.out.println("downloaded successfully: " + resultGraph);
        return resultGraph;
    }
    
}
