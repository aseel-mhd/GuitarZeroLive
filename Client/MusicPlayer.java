import javax.sound.midi.MetaMessage;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.Sequencer;
import javax.sound.midi.Transmitter;
import java.io.File;
import java.util.Scanner;

public class MusicPlayer
{
     static String FILE = "";


    public static void main( String[] argv )
    {

        int song;
        Scanner SongChoice = new Scanner(System.in);

        System.out.println("Please pick a song:\n" +
                "Press 1 for Seven Nation Army\n" +
                "Press 2 for ThunderStruck\n" +
                "Press 3 for Satisfaction");
        song = SongChoice.nextInt();

        System.out.println("you entered "+ song);

        if (song == 1)
        {
            FILE = "C:\\Users\\Henry\\IdeaProjects\\CA\\Music Bundles\\SevenNationArmy.mid";
        }
        else if (song == 2)
        {
            FILE = "C:\\Users\\Henry\\IdeaProjects\\CA\\Music Bundles\\Thunderstruck.mid";
        }
        else if (song == 3)
        {
            FILE = "C:\\Users\\Henry\\IdeaProjects\\CA\\Music Bundles\\Satisfaction.mid";
        }



        try {
            final Sequencer   sequen = MidiSystem.getSequencer();
            final Transmitter trans  = sequen.getTransmitter();

            sequen.open();

            sequen.setSequence( MidiSystem.getSequence( new File( FILE ) ) );

            // trans.setReceiver( new Display() );

            sequen.addMetaEventListener( new MetaEventListener() {
                public void meta( MetaMessage mesg ) {
                    if ( mesg.getType() == 0x2F /* end-of-track */ ) {
                        sequen.close();
                    }
                }
            });

            sequen.start();
        } catch ( Exception exn ) {
            System.out.println( exn ); System.exit( 1 );
        }
    }
}
