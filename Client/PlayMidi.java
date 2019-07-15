import javax.sound.midi.MetaMessage;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import java.io.File;
import java.lang.Runnable;

/**
 * Play MIDI.
 *
 * @version 1.00, January 2019.
 */
public class PlayMidi implements Runnable {

  // final static String FILE = "midifile.mid";
  String file;
  GameView view;

  public PlayMidi(String file, GameView view) {
    this.file = file;
    this.view = view;
  }

  /*
   * Run.
   * Runs when thread starts
   *
   */
  public void run() {
    try {
      final Sequencer   sequen = MidiSystem.getSequencer();
      final Transmitter trans  = sequen.getTransmitter();

      sequen.open();

      sequen.setSequence( MidiSystem.getSequence( new File( file ) ) );

      // trans.setReceiver( new Display() );

      sequen.addMetaEventListener( new MetaEventListener() {
        public void meta( MetaMessage mesg ) {
          if ( mesg.getType() == 0x2F /* end-of-track */ ) {
            sequen.close();
            view.finished();
          }
        }
      });

      sequen.start();
    } catch ( Exception exn ) {
       System.out.println( exn ); System.exit( 1 );
    }
  }
}
