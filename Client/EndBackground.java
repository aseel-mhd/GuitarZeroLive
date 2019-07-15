import java.awt.Graphics;
import java.awt.Image;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.io.IOException;
import java.io.File;
import javax.imageio.*;

/**
 * A panel for adding backgrounds to frames.
 *
 */
public class EndBackground extends JPanel {
  Image image;
  public EndBackground() {
    try {
      image = ImageIO.read( new File ("Pics/Background.png"));
    } catch (IOException ex) {
      System.out.println(ex);
    }
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
  }
}
