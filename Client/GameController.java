import java.awt.event.*;

/**
 * Game controller to handle events during a game.
 *
 */
public class GameController implements KeyListener, GuitarListener {
  public GameView view;
  public GameModel model;
  public int notePressed;

  public GameController(GameView view, GameModel model) {
    this.view = view;
    this.model = model;

    view.addKeyListener(this);
  }

  public boolean isNoteButton(int button) {
    for(int i = 0; i <= 5; i++) {
      if(button == i) {
        return true;
      }
    }
    return false;
  }

  @Override
  public void GuitarPressed(float val, int button) {
    if (val == 1.0 && isNoteButton(button)) {
      notePressed = button;
    }

    if( (val == 1.0 || val == -1.0) && button == Constants.STRUM_BAR) {
      model.strumPressed(notePressed);
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();

    if(key == KeyEvent.VK_SPACE) {
        model.noteHit();
      }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

}
