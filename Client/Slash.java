import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.awt.*;
import java.util.ArrayList;
/*
 * Slash.
 *
 * @version 1.00, February 2019.
 */
public class Slash extends JFrame implements Screen {
    ArrayList<Carousel.MenuButton> buttons;
    Carousel carousel;
    CarouselController controller;
    CarouselModel model;
    SelectMode select;
    Carousel.MenuButton playButton;
    Carousel.MenuButton tutorialButton;
    Carousel.MenuButton selectButton;
    Carousel.MenuButton storeButton;
    Carousel.MenuButton exitButton;
    PollGuitar guitar;
    JPanel background;

    /**
     * Constructor. Sets up the GUI and initiates the guitar
     * @param guitar An active guitar to listen for guitar events
     */
    public Slash(PollGuitar guitar) {
        model = new CarouselModel();
        carousel = new Carousel(model);
        controller = new CarouselController(model, carousel);

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

        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(false);
        this.guitar = guitar;

        guitar.addListeners(controller);
        startCarousel();
    }


    /**
     * Adds the buttons themselves to the Frame
     */
    public void startCarousel() {
        buttons = new ArrayList<Carousel.MenuButton>();

        playButton     = carousel.new MenuButton("Pics/Play_Icon.png");
        tutorialButton = carousel.new MenuButton("Pics/Tutorial_Icon.png");
        selectButton   = carousel.new MenuButton("Pics/Select_Icon.png");
        storeButton    = carousel.new MenuButton("Pics/Store_Icon.png");
        exitButton     = carousel.new MenuButton("Pics/Exit_Icon.png");

        buttons.add(playButton);
        buttons.add(tutorialButton);
        buttons.add(selectButton);
        buttons.add(storeButton);
        buttons.add(exitButton);

        model.addButtons(buttons);
        carousel.createButtons();

        // Add self as a listener to respond to button clicks
        // accordingly.
        controller.listen(this);
    }

    /**
     * Function to be called when a carousel sends a
     * button pressed event.
     * @param btn The button that was in the middle of the carousel when
     *              the event was sent.
     */
    @Override
    public void performAction(Carousel.MenuButton btn) {
        if (btn == exitButton) {
            System.exit(1);
        }

        if (btn == storeButton) {
            // Open the Store Manager GUI
            StoreMode store = new StoreMode();
        }
        if (btn == selectButton) {
           // Open the Select Mode GUI
           select = new SelectMode();
        }

        if (btn == playButton) {
          PlayMode play = new PlayMode(select.selectedNotes, select.selectedMidi, guitar);
        }
    }
}
