import javax.swing.ImageIcon;
import javax.swing.Icon;
import java.awt.Image;
import java.awt.Point;

/**
 * Represents individual notes. Holds their note, location, time in song & image.
 *
 */
public class Note {
  String note;
  Point location;
  Long time;
  ImageIcon image;
  Integer buttonToPress;
  Integer width;
  Integer height;

  public Note(String note, Long time) {
    this.note = note;
    this.time = time;
    this.width = 40;
    this.height = 40;
    generateInfo();
  }

  /**
   * Uses the information provided on construction (note and time) to
   * create an x location and image.
   * Currently 6 white notes and 6 black notes are divided across 3 columns
   */
  public void generateInfo() {
    int x = 0;
    ImageIcon white = new ImageIcon("Pics/Guitar_Pick_White.png");
    ImageIcon black = new ImageIcon("Pics/Guitar_Pick.png");

    switch(note) {
      case "D":
      case "D#":
        x = Constants.FIRST_NOTE;
        image = white;
        buttonToPress = Constants.WHITE_ONE;
        break;

      case "A":
      case "A#":
        x = Constants.SECOND_NOTE;
        image = white;
        buttonToPress = Constants.WHITE_TWO;
        break;

      case "F":
      case "F#":
        x = Constants.THIRD_NOTE;
        image = white;
        buttonToPress = Constants.WHITE_THREE;
        break;

      case "C":
      case "C#":
        x = Constants.FIRST_NOTE;
        image = black;
        buttonToPress = Constants.BLACK_ONE;
        break;

      case "B":
      case "E":
        x = Constants.SECOND_NOTE;
        image = black;
        buttonToPress = Constants.BLACK_TWO;
        break;

      case "G":
      case "G#":
        x = Constants.THIRD_NOTE;
        image = black;
        buttonToPress = Constants.BLACK_THREE;
        break;
    }

    // Move the note left on the x axis by half of its size to correct skew
    x = x - (width * 1/2);

    location = new Point(x, 10);
  }

  /**
   * Move the note along the y axis
   *
   * @param y The amount to move the note by
   */
  public void move(int y) {
    this.width = (int)(30 + location.y/20);
    this.height = (int)(30 + location.y/20);
    int x = 400;

    if(location.x > 450) {
      x = (int)(480 + location.y/20);
    }

    if(location.x < 350) {
      x = (int)(320 - location.y/20);
    }

    x = x - (width * 1/2);
    this.location = new Point(x, location.y + y);
  }

  /**
   * Returns this note's timing in the song
   */
  public Long getTime() {
    return this.time;
  }

  /**
   * Returns a scaled version of the note image
   */
  public ImageIcon getImage() {
    Image img = image.getImage();
    img = img.getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
    ImageIcon imageIcon = new ImageIcon(img);
    return imageIcon;
  }

  public Integer getButtonToPress() {
    return this.buttonToPress;
  }



}
