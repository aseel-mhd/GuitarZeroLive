import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CarouselController implements KeyListener, GuitarListener {
  CarouselModel model;
  Carousel carousel;
  ArrayList<Screen> listeners;
  final int SELECTED_OPTION;

  public CarouselController(CarouselModel model, Carousel carousel)  {
    this.model = model;
    this.carousel = carousel;
    SELECTED_OPTION = 2;
    listeners = new ArrayList<Screen>();
    carousel.addKeyListener(this);
  }

   /**
   * Adds listeners of type Screen to listen for events.
   * (Follows Observer pattern)
   * @param listener A Screen object to be added as a listener
   */
   public void listen(Screen listener) {
     listeners.add(listener);
   }

  /**
  * Function containing actions to be performed when a GuitarPressed
  * event is received
  * @param val The value received from the guitar button
  * @param button The specific button pressed
  */
  @Override
  public void GuitarPressed(float val, int button) {
    if (val == -1.0 && button == Constants.STRUM_BAR) { // Right or Up on Strum
      model.moveRight();
      carousel.createButtons();
    }

    if (val == 1.0 && button == Constants.STRUM_BAR) { // Left or Down on Strum
      model.moveLeft();
      carousel.createButtons();
    }

    if(val == 1.0 && button == Constants.HERO_POWER) {
      for (Screen sc : listeners) {
        sc.performAction(model.getOptionList().get(SELECTED_OPTION));
      }
    }
  }

  /**
  * Primarily for testing purposes
  * Controls the carousel with keyboard input
  */
  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if (key == KeyEvent.VK_LEFT) { // Left Key Pressed
      model.moveLeft();
      carousel.createButtons();
    }

    if (key == KeyEvent.VK_RIGHT) { // Right Key Pressed
      model.moveRight();
      carousel.createButtons();
    }

    if (key == KeyEvent.VK_ENTER) {
      for (Screen sc : listeners) {
        sc.performAction(model.getOptionList().get(SELECTED_OPTION));
      }
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

}
