package sockets;

import com.thoughtworks.xstream.XStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.ObjectOutputStream;

public class ServerXml extends Server {
    
    public static final int PORT = 29001;
    private static ServerXml serverInstance;
    
    private ServerXml() {
        try {
            this.serverSocket = new ServerSocket(PORT);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public static Server getInstance() {
        if( ServerXml.serverInstance ==  null ) {
            ServerXml.serverInstance = new ServerXml();
        }
        return ServerXml.serverInstance;
    }
    
    public static void main(String[] args) {
        ServerXml.getInstance().main();
    }

    @Override
    public void sendGraphToSocket(Socket socket) {
        XStream xStream = new XStream();
        String xmlToSend = xStream.toXML(this.graph.getConnections());
        try {
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //...?
            oos.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
        
    }
}
