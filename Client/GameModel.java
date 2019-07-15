import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import java.util.Collections;
import java.math.*;

/**
 * The logical model for a guitar zero game
 *
 */

public class GameModel {
  // Lists to hold all notes in a song and the notes currently active (on screen)
  ArrayList<Note> allNotes;
  List<Note> activeNotes;
  double multiplier;
  int streak;
  int score;
  int hStreak;


  /**
   * Create a new game model
   *
   * @param notes The notes for the song playing
   */
  public GameModel(ArrayList<Note> notes) {
    allNotes = notes;
    multiplier = 1;
    streak = 0;
    score = 0;
    hStreak = 0;
    ArrayList<Note> arrayNotes = new ArrayList<Note>();
    activeNotes = Collections.synchronizedList(arrayNotes);

  }

  /**
   * Move the active notes down 10 pixels
   */
  public void down() {
    List list = Collections.synchronizedList(activeNotes);
    synchronized(list) {
      Iterator i = list.iterator();

      while(i.hasNext()) {
        Note note = (Note)i.next();
        note.move(10);
      }
    }
  }

  /**updateScore()
   * Method to be run every game loop interval.
   * Adds upcoming notes to the active note list.
   *
   * @param currentTick The current game tick (milliseconds)
   */
  public synchronized void run(Long currentTick) {
    synchronized(activeNotes) {
      Iterator<Note> iterator = allNotes.iterator();

      while(iterator.hasNext()) {
        Note note = iterator.next();

        // If the current note is scheduled to play within x seconds,
        // add to activeNotes to start dropping.
        if (note.getTime() < currentTick + Constants.DISPLAY_INTERVAL) {

          if(!activeNotes.contains(note)) {
            activeNotes.add(note);
            iterator.remove();
          }
        }

        this.removeMissedNotes();

      }
    }
  }

  /**
   * Remove notes that have dropped below a point and haven't been hit by the player
   */
  public synchronized void removeMissedNotes() {
    // Iterate over notes using an iterator rather than modifying list during iteration
    synchronized(activeNotes) {
      Iterator<Note> iterator = activeNotes.iterator();

      while(iterator.hasNext()) {
        // Currently an arbitrary pixel location in the bottom half of the screen (500)
        // as timings have not been fine tuned
        if(iterator.next().location.y > 500) {
          iterator.remove();
          streak = 0;
          multiplier = 1;
        }
      }
    }
  }

  public synchronized List<Note> getActiveNotes() {
    return activeNotes;
  }

  /**
   * Called when the strum bar has been hit
   * checks if the lowest note on the screen is within range of a successful hit
   */
  public synchronized void noteHit() {
    Note lowestNote = null;
    synchronized(activeNotes) {
      Iterator<Note> iterator = activeNotes.iterator();


      while(iterator.hasNext()) {
        Note note = iterator.next();
        if (lowestNote == null) {
          lowestNote = note;
        } else {
          if((lowestNote.location.y + lowestNote.height)> 500) {
            lowestNote = note;
            streak = 0;
            multiplier = 1;
          }
        }
      }

      if((lowestNote.location.y + lowestNote.height)> 450 && (lowestNote.location.y + lowestNote.height)< 500) {
        activeNotes.remove(lowestNote);
        streak += 1;
        score += (1 * multiplier);
        if (streak % 10 == 0) {
          multiplier = Math.pow(2,(streak/10));
        }
        if (streak > hStreak) {
          hStreak = streak;
        }
      } else {
        streak = 0;
        multiplier = 1;
      }
    }
  }

  public Note getLowestNote() {
    Note lowestNote = null;
    synchronized(activeNotes) {
      Iterator<Note> iterator = activeNotes.iterator();


      while(iterator.hasNext()) {
        Note note = iterator.next();
        if (lowestNote == null) {
          lowestNote = note;
        } else {
          if(note.location.y > lowestNote.location.y && lowestNote.location.y < 500) {
            lowestNote = note;
          }
        }
      }
    }
    return lowestNote;
  }

  public void strumPressed(int button) {
    Note lowestNote = getLowestNote();
    int correctButton = lowestNote.getButtonToPress();

    if(button == correctButton) {
      this.noteHit();
    }
  }

}
