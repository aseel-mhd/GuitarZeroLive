import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public final static int PORT = 6666;

  public static void main(String[] args) {
    try {
      ServerSocket sock = new ServerSocket(PORT);
      System.out.println("Starting Guitar Zero server..");
      while(true) {
        Socket clientSocket = sock.accept();
        System.out.println("Got new client");
        ServerClient server = new ServerClient(clientSocket);
        server.run();
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
