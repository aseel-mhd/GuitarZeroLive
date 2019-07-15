import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.DataInputStream;
import java.io.BufferedOutputStream;
import java.net.Socket;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.zip.*;
import java.io.FileInputStream;

public class StoreMode extends JFrame implements Screen {
  ArrayList<Carousel.MenuButton> buttons;
  ArrayList<String>              paths;
  Carousel                       carousel;
  CarouselController             controller;
  CarouselModel                  model;
  String                         host;
  int                            port;
  JPanel                         background;

  public StoreMode() {
    model = new CarouselModel();
    carousel = new Carousel(model);
    controller = new CarouselController(model, carousel);
    buttons = new ArrayList<Carousel.MenuButton>();
    paths = new ArrayList<String>();
    host = "localhost";
    port = 6666;

    this.setSize(800, 600);
    this.setLocationRelativeTo(null);
    this.setVisible(true);
    this.setResizable(false);
    this.setFocusable(false);
    this.setDefaultCloseOperation(EXIT_ON_CLOSE);

    background = new Background();
    this.getContentPane().add(background);
    background.setLayout(null);

    ImageIcon icon = new ImageIcon("Pics/blueSelector.png");
    Image img = icon.getImage();
    Image newImg = img.getScaledInstance(520, 140, java.awt.Image.SCALE_SMOOTH);
    ImageIcon newIcon = new ImageIcon(newImg);
    JLabel selector = new JLabel(newIcon);
    selector.setBounds(140, 192, 518, 140);
    background.add(selector);

    getAllBundles();
    startCarousel();
  }

  public void getAllBundles() {
    try {
      Socket sock = new Socket(host, port);
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();

      BufferedReader read = new BufferedReader(new InputStreamReader(is));
      PrintWriter writer = new PrintWriter(os);
      writer.print("LIST\r\n");
      writer.flush();
      String line;
      boolean gettingList = true;
      while(gettingList) {
        while ((line = read.readLine()) != null) {
          if(line.equals("LIST")) {
            writer.write("READY\r\n");
            writer.flush();
          }

          if(line.contains("SEND")) {
            String path = line.split(" ")[1];
            System.out.println("Receiving file: " + path);
            readFile(sock, path);
            writer.write("READY\r\n");
            writer.flush();
          }

          if(line.equals("LIST SENT")) {
            gettingList = false;
            writer.write("QUIT");
            writer.flush();
            sock.close();
            break;
          }

        }
      }
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public void readFile(Socket sock, String path) {
    try {
      path = path.split("/")[2];
      path = "Pics/" + path;
      BufferedImage img = ImageIO.read(sock.getInputStream());
      ImageIO.write(img, "PNG", new File(path));
      paths.add(path);
    } catch (Exception e) {
      e.printStackTrace(System.out);
    }
  }

  public void getBundle(String name) {
    System.out.println("Requesting " + name);
    try {
      Socket sock = new Socket(host, port);
      InputStream is = sock.getInputStream();
      OutputStream os = sock.getOutputStream();

      BufferedReader read = new BufferedReader(new InputStreamReader(is));
      PrintWriter writer = new PrintWriter(os);
      String line;
      writer.write("DOWNLOAD " + name + "\r\n");
      writer.flush();
      boolean gettingBundle = true;
      while(gettingBundle) {
        while ((line = read.readLine()) != null) {
          System.out.println(line);
          if(line.contains("SENDING")) {
            int size = Integer.parseInt(line.split(" ")[1]);
            downloadBundle(sock, size, name);
            unzipFile(name);
            JOptionPane.showMessageDialog(null, name + " downloaded");
            this.dispose();
            carousel.dispose();
            gettingBundle = false;
            writer.write("QUIT");
            writer.flush();
            sock.close();
            break;
          }
        }
      }
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  public void downloadBundle(Socket sock, int size, String name) {
    try {
      byte[] mybytearray = new byte[size];
      InputStream is = sock.getInputStream();
      FileOutputStream fos = new FileOutputStream("Music Bundles/" + name + ".zip");
      BufferedOutputStream bos = new BufferedOutputStream(fos);
      int bytesRead = is.read(mybytearray, 0, mybytearray.length);
      bos.write(mybytearray, 0, bytesRead);
    } catch (Exception e) {
      System.out.println(e);
    }
  }

  public void unzipFile(String name) {
    try {
      File file = new File("Music Bundles/" + name + ".zip");
      new File("Music Bundles/" + name).mkdir();
      byte[] bytes = new byte[(int) file.length()];
      ZipInputStream zis = new ZipInputStream(new FileInputStream(file));
      ZipEntry entry = zis.getNextEntry();

      while (entry != null) {
        File destinationFile = new File("Music Bundles/" + name, entry.getName());
        FileOutputStream fos = new FileOutputStream(destinationFile);
        int read;

        while ((read = zis.read(bytes)) > 0) {
          fos.write(bytes, 0, read);
        }
        fos.close();
        entry = zis.getNextEntry();
      }
      zis.closeEntry();
      zis.close();
      file.delete();
    } catch(Exception e) {
      System.out.println(e);
    }
  }

  @Override
  public void performAction(Carousel.MenuButton btn) {
    String bundleName = model.optionList.get(2).name.split(".png")[0];
    bundleName = bundleName.split("/")[1];
    System.out.println(bundleName);
    getBundle(bundleName);
  }

  public void startCarousel() {
    Carousel.MenuButton button;
    for (String path : paths) {
      button = carousel.new MenuButton(path);
      buttons.add(button);
    }
    model.addButtons(buttons);
    carousel.createButtons();
    controller.listen(this);
  }
}
