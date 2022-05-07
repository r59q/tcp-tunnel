import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ServerTunnel {
    public static void main(String[] args) {
        int listenPort = Integer.parseInt(args[0]);
        String destinationHost = args[1];
        int destinationPort = Integer.parseInt(args[2]);
        try {
            ServerSocket serverSocket = new ServerSocket(listenPort);

            while (true) {
                try {

                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected");

                Socket destinationSocket = new Socket(destinationHost, destinationPort);
                Thread thread = new ClientHandler(clientSocket, destinationSocket);
                thread.start();
                } catch (IOException e) {
                    e.printStackTrace();
                    serverSocket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ClientHandler extends Thread {
    private final Socket clientSocket;
    private final InputStream clientInStream;
    private final OutputStream clientOutStream;
    private final Socket destinationSocket;

    public ClientHandler(Socket clientSocket, Socket destinationSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.destinationSocket = destinationSocket;
        this.clientInStream = clientSocket.getInputStream();
        this.clientOutStream = clientSocket.getOutputStream();
    }

    @Override
    public void run() {
        int fromClient, fromDestination;
        while (true) {
            try {
                // Read incoming data from client
                fromClient = this.clientInStream.read();
                System.out.println("Client: " + fromClient);
                // Relay to destination
                OutputStream destinationOutStream = destinationSocket.getOutputStream();
                destinationOutStream.write(fromClient);
                // Read from destination
                InputStream destinationInStream = destinationSocket.getInputStream();
                fromDestination = destinationInStream.read();
                System.out.println("fromDestination: " + fromDestination);
                // Relay to client
                clientOutStream.write(fromDestination);

                if (fromClient == -1) {
                    System.out.println("Client disconnected");
                    cleanUp();break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                cleanUp();break;
            }
        }
    }

    private void cleanUp() {
        try {
            this.clientSocket.close();
            this.destinationSocket.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}