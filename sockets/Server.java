package sockets;

import graphs.Graph;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.Scanner;

public abstract class Server implements Runnable {
    
    public static final Random random = new Random();
    public static final String HOST = "127.0.0.1";
    //public static final int PORT = 9090;
    
    protected ServerSocket serverSocket;
    protected final GraphNetwork graph = new GraphNetwork();
    
    protected void receiveSocketAndSendResponse() throws IOException {
        System.out.println("    waiting for incoming connection...");
        Socket newSocket=null;
        try {
            newSocket = this.serverSocket.accept();
        } catch(IOException e) {
            System.err.println("could not connect with next socket");
        }
        if(newSocket != null) {
            System.out.println("connection accepted");
            System.out.println("generating new graph...");
            this.graph.replace(
                Graph.generateGraph(random.nextInt(800)+1200, random.nextInt(400000)+100000));
            System.out.println("graph generated");
            System.out.println("trying to send: " + this.graph);
            this.sendGraphToSocket(newSocket);
            System.out.println("sent: " + graph);
            newSocket.close();
        }
    }
    
    public void main() {
        System.out.println("welcome to the server, you don't have to do anything here");
        System.out.println("to shut down the server properly, press enter");
        Thread serverThread = new Thread(this);
        serverThread.start();
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();
        serverThread.interrupt();
        this.shutDownServer();
        System.out.println("goodbye");
    }
    public abstract void sendGraphToSocket(Socket socket);

    @Override
    public void run() {
        while(!Thread.interrupted()) {
            try {
                this.receiveSocketAndSendResponse();
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    public void shutDownServer() {
        try {
            this.serverSocket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
    
}
