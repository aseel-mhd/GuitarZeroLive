import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.io.InputStream;
import java.io.IOException;
/*
 * SelectMode.
 *
 * Adds the cover art of the titles to be selected and continuing to slash mode
 *
 * @version 1.00, March 2019.
 */
public class SelectMode extends JFrame implements Screen {
    ArrayList<Carousel.MenuButton> buttons;
    Carousel                       carousel;
    CarouselController             controller;
    CarouselModel                  model;
    Carousel.MenuButton            songPic;
    ArrayList<ArrayList<File>>     allSongFiles;
    String                         selectedPNG;
    String                         selectedMidi;
    String                         selectedNotes;
    JPanel                         background;

    /*
     * Constructor to set up the GUI
     */
    public SelectMode() {
        model = new CarouselModel();
        carousel = new Carousel(model);
        controller = new CarouselController(model, carousel);

        allSongFiles = getAllSongs();

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(false);

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

        startCarousel();
    }

    public ArrayList<ArrayList<File>> getAllSongs() {
      allSongFiles = new ArrayList<ArrayList<File>>();
      File[] songFolders = new File("Music Bundles").listFiles();

      for (File folder : songFolders) {
        if (folder.isDirectory()) {
          ArrayList<File> singleSong = new ArrayList<File>();
          singleSong.add(folder);
          for (File file : folder.listFiles()) {
            singleSong.add(file);
          }
          allSongFiles.add(singleSong);
        }
      }

      return allSongFiles;
    }


    /*
     * Function to add the song buttons to the frame
     */
    public void startCarousel() {
        buttons = new ArrayList<Carousel.MenuButton>();

        for (ArrayList<File> songFiles : allSongFiles) {
          for (File file : songFiles) {
            if (file.getName().endsWith(".png")) {
              songPic = carousel.new MenuButton("Music Bundles/" + songFiles.get(0).getName() + "/" + file.getName());
              buttons.add(songPic);
            }
          }
        }

        model.addButtons(buttons);
        carousel.createButtons();

        controller.listen(this); // Adds listener for button clicking actions
    }

    public void setSelected() {
      String songFolderName = model.optionList.get(2).name.split("/")[1];
      File[] selectedFiles = new File("Music Bundles/" + songFolderName).listFiles();

      for (File file : selectedFiles) {
        if (file.getName().endsWith(".png")) {
          selectedPNG = "Music Bundles/" + songFolderName + "/" + file.getName();
        }
        else if (file.getName().endsWith(".mid")) {
          selectedMidi = "Music Bundles/" + songFolderName + "/" + file.getName();
        }
        else if (file.getName().endsWith(".txt")) {
          selectedNotes = "Music Bundles/" + songFolderName + "/" + file.getName();
        }
      }
    }

    /*
     * Function for implementing the actions of when a button is selected
     * @param btn The button that is in the middle of the coursel when event was sent
     */
    @Override
    public void performAction(Carousel.MenuButton btn) {
        setSelected();

        this.setVisible(false);
        carousel.setVisible(false);
        this.dispose();
        carousel.dispose();
    }
}
