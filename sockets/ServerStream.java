package sockets;

import graphs.GraphConnection;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStream extends Server {
    
    public static final int PORT = 29000;
    private static ServerStream serverInstance;
    
    private ServerStream() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance() {
        if( ServerStream.serverInstance ==  null ) {
            ServerStream.serverInstance = new ServerStream();
        }
        return ServerStream.serverInstance;
    }
    
    public static void main(String[] args) {
        ServerStream.getInstance().main();
    }

    @Override
    public void sendGraphToSocket(Socket socket) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            int i=0;
            for(GraphConnection con : this.graph.getConnections()) {
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
    }
    
}
