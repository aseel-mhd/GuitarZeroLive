import java.util.ArrayList;
import java.lang.Runnable;
import java.lang.Thread;

/**
 * Begin running a game, generating & reading notes then starting the actual game.
 * Currently hardcoded as a single song
 *
 */


public class PlayMode {
  /**
   * Create a notes text file, read that file to create Note objects
   * and play the game.
   */
  public PlayMode(String selectedNotes, String selectedMidi, PollGuitar guitar) {
    //Reads the notes from the slected song
    ReadNotes noteRead = new ReadNotes(selectedNotes);

    ArrayList<Note> notes = noteRead.createNotes();

    // Start the MVC classes
    GameModel model = new GameModel(notes);
    GameView view = new GameView(model);
    GameClock clock = new GameClock(model, view);
    GameController controller = new GameController(view, model);
    guitar.clearListeners();
    guitar.addListeners(controller);

    // Creates a (Runnable) class to play music in a background thread
    PlayMidi midiSong = new PlayMidi(selectedMidi, view);
    Thread songThread = new Thread(midiSong);

    // Start the thread and open the view
    songThread.start();
    view.setVisible(true);
  }
}
