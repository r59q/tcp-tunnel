import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {
    public static void main(String[] args) throws IOException {
        int listenPort = Integer.parseInt(args[0]);
        ServerSocket ss = new ServerSocket(listenPort);
        while (true) {
            Socket soc = null;
            try {
                soc = ss.accept();
                System.out.println("Accepted connection from " + soc.getInetAddress());
                InputStream is = soc.getInputStream();
                OutputStream os = soc.getOutputStream();

                Thread t = new EchoClientHandler(soc, is, os);
                t.start();
            } catch (IOException e) {
                ss.close();
            }
        }
    }
}

class EchoClientHandler extends Thread {

    private final Socket soc;
    private final InputStream is;
    private final OutputStream os;

    public EchoClientHandler(Socket soc, InputStream is, OutputStream os) {
        this.soc = soc;
        this.is = is;
        this.os = os;
    }

    @Override
    public void run() {
        int recieved;
        while (true) {
            try {
                recieved = is.read();
                System.out.println("Recieved: " + recieved);
                os.write(recieved);
                if (recieved == -1) {
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}