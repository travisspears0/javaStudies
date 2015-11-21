package sockets;

import com.thoughtworks.xstream.XStream;
import graphs.Graph;
import graphs.GraphConnection;
import graphs.GraphDijkstraQueue;
import java.io.ObjectInputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Scanner;

public class Client {
    
    public static void main(String[] args) {
        
        int option = -1;
        Scanner scanner = new Scanner(System.in);
        Graph graph = new GraphDijkstraQueue();
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
                case 0: {
                    break;
                }
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
            resultGraph = ((SerializableGraph)ois.readObject()).decode();
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
        ObjectInputStream ois = xStream.createObjectInputStream(socket.getInputStream());
        List<GraphConnection> connections = 
            (List<GraphConnection>)xStream.fromXML(ois.readObject().toString());
        System.out.println("got the response, parsing xml...");
        for( GraphConnection c : connections ) {
            resultGraph.addConnection(c.getPointA().getId(), c.getPointB().getValue(), c.getValue());
        }
        ois.close();
        System.out.println("downloaded successfully: " + resultGraph);
        return resultGraph;
    }
    
}
