import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.*;

public class EndScreen extends JFrame implements KeyListener {
  JPanel background;
  int endScore;
  int hStreak;
  ArrayList<Screen> listeners;

  public EndScreen (int endScore, int hStreak) {
    this.endScore = endScore;
    this.hStreak = hStreak;
    display();
    listeners = new ArrayList<Screen>();
    addKeyListener(this);
  }

  public void display() {
    background = new Background();
    this.getContentPane().add(background);
    background.setLayout(null);

    JLabel totalScore = new JLabel("FINAL SCORE:  " + Integer.toString(this.endScore));
    totalScore.setForeground(Color.CYAN);
    totalScore.setBounds(150, 50, 750, 50);
    totalScore.setFont(new Font("HELVETICA", Font.ITALIC, 50));
    background.add(totalScore);

    JLabel highestStreak = new JLabel("HIGHEST STREAK:  " + Integer.toString(this.hStreak));
    highestStreak.setForeground(Color.CYAN);
    highestStreak.setBounds(150, 250, 750, 50);
    highestStreak.setFont(new Font("HELVETICA", Font.ITALIC, 50));
    background.add(highestStreak);

    JLabel enter = new JLabel("PRESS ENTER TO CONTINUE");
    enter.setForeground(Color.CYAN);
    enter.setBounds(60, 450, 750, 50);
    enter.setFont(new Font("HELVETICA", Font.ITALIC, 50));
    background.add(enter);

    this.setSize(Constants.WIDTH, Constants.HEIGHT);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setFocusable(true);
    this.setVisible(true);
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_ENTER) {
      this.setVisible(false);
      this.dispose();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }
}
