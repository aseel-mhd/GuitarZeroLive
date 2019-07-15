import java.io.*;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.zip.ZipFile;
import java.net.URL;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;

public class ServerClient implements Runnable {
  private Socket sock;

  public ServerClient(Socket sock) {
    this.sock = sock;
  }

  public void run() {
    try {
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();

      BufferedReader read = new BufferedReader(new InputStreamReader(is));
      PrintWriter write = new PrintWriter(os);

      String line;
      while ((line = read.readLine()) != null) {
        System.out.println(line);
        if (line.equals("LIST")) {
          write.write("LIST\r\n");
          write.flush();
          ArrayList<String> paths = getAllImages();

          int i = 0;
          while(i < paths.size()) {
            line = read.readLine();
            if (line.equals("READY")) {
              System.out.println("Client is ready");
              sendFile(paths.get(i), sock);
              i++;
            }
          }
          write.write("\r\nLIST SENT\r\n");
          write.flush();
          System.out.println("List sent");
        }

        if(line.equals("QUIT")) {
          System.out.println("Client disconnected");
        }

        if(line.contains("DOWNLOAD")) {
          System.out.println("Got download");
          String filename = line.split(" ")[1];
          File file = new File("Bundles/" + filename + "/" + filename + ".zip");
          write.write("SENDING " + file.length() + "\r\n");
          write.flush();
          sendBundle(file, sock);
        }
      }
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void sendBundle(File file, Socket sock) {
    try {
      byte[] bytes = new byte[(int) file.length()];
      BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
      bis.read(bytes, 0, bytes.length);
      OutputStream os = sock.getOutputStream();
      os.write(bytes, 0, bytes.length);
      os.flush();
    } catch (Exception e) {
      System.out.println(e);
  }
  }

  public void sendFile(String path, Socket sock) {
    try {
      PrintWriter writer = new PrintWriter(sock.getOutputStream());
      BufferedImage img = ImageIO.read(new File(path));
      writer.write("SEND " + path + "\r\n");
      writer.flush();
      ImageIO.write(img, "PNG", sock.getOutputStream());
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public ArrayList<String> getAllImages() {
    ArrayList<String> paths = new ArrayList<String>();
    File[] songFolders = new File("Bundles").listFiles();

    for (File file : songFolders) {
      String songName = file.getName();
      File[] songFolder = file.listFiles();

      for (File innerFile : songFolder) {
        if (innerFile.getName().endsWith(".png")) {
          paths.add(innerFile.getPath());
        }
      }

    }

    return paths;
  }
}
