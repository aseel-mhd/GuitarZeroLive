import java.util.Timer;
import java.util.TimerTask;

/**
 * The game loop clock
 * Runs every x seconds and calls the relevant model and view methods.
 * 
 */
public class GameClock {
  private GameModel model;
  private GameView view;
  private Timer timer;
  private Long tick;

  public GameClock(GameModel model, GameView view) {
    this.model = model;
    this.view = view;
    this.timer = new Timer();
    this.tick = new Long(0);

    timer.schedule(new TimerTask() {
      public void run() {
        model.run(tick);
        model.down();
        view.redraw();
        tick = new Long(tick + Constants.INTERVAL);
      }
    }, 0, Constants.INTERVAL);

  }
}
