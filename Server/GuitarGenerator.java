import java.io.File;
import java.io.PrintWriter;
import javax.sound.midi.Instrument;
import javax.sound.midi.MidiEvent;
import javax.sound.midi.MidiMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.sound.midi.ShortMessage;
import javax.sound.midi.Synthesizer;
import javax.sound.midi.Track;
import java.util.ArrayList;
import java.util.*;

/**
 * GuitarGenerator takes a midi file in its constructor and creates a 'notes.txt'
 * file containing the most promenent guitar part's set of notes. This set contains
 * times the guitar is played and its pitch per each time.
 *
 * @version 1.00
 */
public class GuitarGenerator {

  /**
  * Guitar Generator Constructor
  *
  * @param FILE The midi file to be converted to notes file
  */
  public GuitarGenerator(String FILE) {
    getNotes(FILE);
  }

  /**
  * getTPS which stands for getTicksPerSeond, each song has a different tempo and
  * therefore to get timings of notes, the time of a singlular tick is needed.
  *
  * @param seq The sequence of notes that the midi file is made up of
  * @return the ticks per second in a double format
  */
  public static double getTPS(Sequence seq) {
    long secLength = seq.getMicrosecondLength()/1000; //Length in millisecs
    long tickLength = seq.getTickLength(); //Length in ticks
    double tickPerSec = (double)tickLength / secLength; //Ticks Per Second

    return tickPerSec;
  }

  /**
   * Returns the name of nth note.
   *
   * @param n the note number
   * @return  the note name
   */
  public static String noteName( int n ) {
    final String[] NAMES =         //All possible notes
      { "C", "C#", "D", "D#", "E", "F", "F#", "G", "G#", "A", "A#", "B" };
    final int note   = n % 12;
    return NAMES[ note ];
  }

  /**
  * guitarPart Goes through each track to find the first guitar part, and then
  * extracts the notes and timings of that part.
  *
  * @param seq The set of events within the whole midi file
  * @param tps The ticks per second
  * @return An arrayList containing couplets of all guitar timings with their pitch
  */
  public static ArrayList<String> guitarPart( Sequence seq, double tps ) {
    Track[]                      trks           = seq.getTracks(); //All tracks from the midi
    int                          count          = 0; //Which track is being analysed
    ArrayList<ArrayList<String>> allGuitarParts = new ArrayList<ArrayList<String>>();
    ArrayList<String>            notes          = new ArrayList<String>();
    ArrayList<String>            bestNotes      = new ArrayList<String>();
    String[]                     lastNote       = new String[]{};

    for (Track trk : trks) {
      notes.clear();
      for ( int i = 0; i < trk.size(); i = i + 1 ) {   //For each event in the track
        MidiEvent   evt  = trk.get( i );
        MidiMessage msg  = evt.getMessage();
        if ( msg instanceof ShortMessage ) {
          final long         tick = evt.getTick();
          final ShortMessage smsg = (ShortMessage) msg;
          final int          cmd  = smsg.getCommand();
          final int          dat1 = smsg.getData1();
          if (dat1 >= 25 && dat1 <= 40) {   //if between 25 and 40 then it is bass or guitar instrument
            if ( cmd == ShortMessage.NOTE_ON ) {
              if (!(notes.isEmpty())) {   //if not the first note
                lastNote = notes.get(notes.size() - 1).split(",");
                // Uses the last note to see if it is too close to next note (within 300ms)
                if ((double)tick/tps - Double.parseDouble(lastNote[0]) > 300) {
                  notes.add((double)tick/tps + "," + noteName( dat1 ));
                }
              } else {
                notes.add((double)tick/tps + "," + noteName( dat1 ));
              }
            }
          }
        }
      }

      if (notes.size() != 0) { //if guitar notes were found on the track
        ArrayList<String> current = new ArrayList<String>(notes);
        allGuitarParts.add(current);
      }
    }

    if (allGuitarParts.size() == 0) { //No guitar parts
      System.out.println("No Valid Guitar Parts");
    } else if (allGuitarParts.size() == 1) { //Only one guitar part
      notes = allGuitarParts.get(0);
    } else { //More than one guitar track
      notes = getBestPart(allGuitarParts);
    }

    return notes;
  }

  /**
  * guitarPart Goes through each track to find the first guitar part, and then
  * extracts the notes and timings of that part.
  *
  * @param allGuitarParts A list of all the possible guitar parts
  * @return A list of notes for the largest guitar part in the song
  */
  public static ArrayList<String> getBestPart(ArrayList<ArrayList<String>> allGuitarParts) {
    int bestLength = 0;
    int bestIndex = 0;
    ArrayList<String> best;
    Set<String> diffNotes = new HashSet<String>();

    for(int i = 0; i < allGuitarParts.size(); i++) { //For each guitar part, get the longest one
      diffNotes.clear();

      for(int j = 0; j < allGuitarParts.get(i).size(); j = j + 2) {
        String[] currentNote = allGuitarParts.get(i).get(j).split(",");
        diffNotes.add(currentNote[1]);
        System.out.println(diffNotes);
      }

      if (allGuitarParts.get(i).size() > bestLength && diffNotes.size() > 2) {
        bestLength = allGuitarParts.get(i).size();
        bestIndex = i;
      }
    }

    best = allGuitarParts.get(bestIndex);

    return best;
  }

  /**
  * writeToFile writes each elements of the notes list to a new line in the file.
  * Each line containing the time of occurence in milliseconds and the pitch of
  * the note turned on. Seperated by a comma
  *
  * @param notes The notes to be written to file
  * @param seq The set of events within the whole midi file
  * @param tps The ticks per second
  */
  public static void writeToFile( ArrayList<String> notes, Sequence seq, Double tps)  {
    try {
      //Creates the new file called "notes.txt"
      PrintWriter writer = new PrintWriter("notes.txt", "UTF-8");
      writer.println(tps); //First line conatins the ticksPerSec
      for (String line : notes) {
        writer.println(line); //Then each note in the list
      }
      writer.close();

    } catch ( Exception exn ) {
      System.out.println( exn ); System.exit( 1 );
    }
  }

  /**
  * writeToFile writes each elements of the notes list to a new line in the file.
  * Each line containing the time of occurence in milliseconds and the pitch of
  * the note turned on. Seperated by a comma
  *
  * @param FILE The midi file of the song in question
  */
  public static void getNotes ( String FILE ) {
    ArrayList<String> notes;
    double tps;

    try {
      Sequence seq = MidiSystem.getSequence( new File( FILE ) );
      tps = getTPS(seq);
      notes = guitarPart( seq, tps );
      writeToFile( notes, seq, tps );
    } catch ( Exception exn ) {
      System.out.println( exn ); System.exit( 1 );
    }
  }
}
