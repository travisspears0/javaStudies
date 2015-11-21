package sockets;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerStream extends Server {
    
    public static final int PORT = 29000;
    private static ServerStream serverInstance;
    private SerializableGraph serializableGraph = new SerializableGraph();
    
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
            this.serializableGraph.encode(this.graph);
            oos.writeObject(this.serializableGraph);
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
