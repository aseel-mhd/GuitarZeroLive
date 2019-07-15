import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Point;
import java.awt.Image;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.Icon;
import javax.swing.JLabel;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;
import java.io.IOException;
import java.awt.Font;

/**
 * The game view which sets up the screen and handles note tiles dropping
 *
 */

public class GameView extends JFrame {
  GameModel model;
  JPanel background;
  ArrayList<Note> notes;
  ArrayList<JButton> tiles;

  JLabel cScore = new JLabel();
  JLabel mScore = new JLabel();
  JLabel sScore = new JLabel();

  /**
   * Construct a game view GUI
   *
   * @param model A GameModel
   */
  public GameView(GameModel model) {
    this.model = model;
    this.tiles = new ArrayList<JButton>();


    // Set various GUI related settings
    background = new GameBackground();
    this.getContentPane().add(background);

    this.setSize(Constants.WIDTH, Constants.HEIGHT);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setFocusable(true);
  }

  /**
   * Called every game loop interval.
   * Gets the currently active notes and creates a button using the x,y point and image.
   *
   * Currently the source of a nasty concurrency bug where notes are being accessed in the view
   * and modified in the model at the same time.
   */
  public synchronized void redraw() {
    // TODO: Refactor to use an event system rather than accessing model's activeNotes list (should fix concurrency bug)
    List<Note> activeNotes = model.getActiveNotes();


    // Remove any tiles currently appearing
    for (JButton tile : tiles) {
      background.remove(tile);
    }

    //current score
    int currentScore = model.score;
    cScore.setForeground(Color.WHITE);
    cScore.setBounds(650, 95, 200, 100);
    cScore.setFont(new Font("HELVETICA", Font.ITALIC, 25));
    cScore.setText(Integer.toString(currentScore));
    background.add(cScore);

    //current multiplier
    int currentMultiplier = (int) model.multiplier;
    mScore.setForeground(Color.WHITE);
    mScore.setBounds(650, 195, 200, 100);
    mScore.setFont(new Font("HELVETICA", Font.ITALIC, 25));
    mScore.setText("x" + Integer.toString(currentMultiplier));
    background.add(mScore);

    //current streak
    int currentStreak = model.streak;
    sScore.setForeground(Color.WHITE);
    sScore.setBounds(650, 295, 100, 100);
    sScore.setFont(new Font("HELVETICA", Font.ITALIC, 25));
    sScore.setText(Integer.toString(currentStreak));
    background.add(sScore);

/*
    ImageIcon Icon = new ImageIcon("Client/Music Bundles/Ticket_To_Ride/Ticket_to_Ride.png");
    JLabel album = new JLabel(Icon);
    album.setBounds(0,0,200,200);
    background.add(album);
  */
    // Iterate through the active notes and create a new button on screen
    synchronized(activeNotes) {
      Iterator i = activeNotes.iterator();
      while(i.hasNext()) {
        Note note = (Note)i.next();
        ImageIcon icon = note.getImage();
        JButton tile = new JButton(icon);
        tile.setBounds(note.location.x, note.location.y, note.width, note.height);
        tile.setContentAreaFilled(false);
        tile.setFocusPainted(false);
        tile.setOpaque(false);
        tile.setBorder(null);
        background.add(tile);
        tiles.add(tile);
      }
    }
    this.repaint();
  }

  public void finished() {
    this.dispose();
    EndScreen end = new EndScreen(model.score, model.hStreak);
  }
}
