import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Reads note text files and converts them into a list of Note objects
 * 
 */
public class ReadNotes {
  String notesFile;
  BufferedReader reader;

  /**
   *  Read text file containint notes and times
   * 
   * @param notesFile the location of the notes file
   */
  public ReadNotes(String notesFile) {
    this.notesFile = notesFile;
  }

  /**
   * Returns an ArrayList of Note objects from within the notes file
   * 
   */
  public ArrayList<Note> createNotes() {
    ArrayList<Note> notes = new ArrayList<Note>();

    try {
      reader = new BufferedReader(new FileReader(notesFile));
      
      // The first line of the file should be ticks per second of the song
      String line = reader.readLine();
      double tps = Double.parseDouble(line);

      // The rest of the file should Time,Note pairs on each line
      while((line = reader.readLine()) != null) {
        Double time;
        String note;

        String[] splitLine = line.split(",");

        // Get the double format time and convert it to long format
        time = Double.parseDouble(splitLine[0]);
        Long longTime = (long)Math.round(time);
        note = splitLine[1];

        System.out.println("Making new note: " + note);
        notes.add(new Note(note, longTime));
      }
    } catch(IOException e) {
      // Catches an exception
      System.out.println("Couldn't read notes file");
      System.out.println(e);
      System.exit(1);
    }
    return notes;
  }


}
