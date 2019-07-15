import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import java.util.*;
/*
 * PollGuitar.
 *
 * @version 1.00, February 2019.
 */
public class PollGuitar extends Thread {
  final static int    DELAY       = 130; /* 7.69th of a second */
  ArrayList<GuitarListener> listenersArray   = new ArrayList<GuitarListener>();
  List<GuitarListener> listeners = Collections.synchronizedList(listenersArray);

  public void run() {
    ControllerEnvironment cenv  = ControllerEnvironment.getDefaultEnvironment();
    Controller[]          ctrls = cenv.getControllers();

    for ( Controller ctrl : ctrls ) {
      Component[] cmps = ctrl.getComponents(); /* all guitar buttons */
      float[]     vals = new float[  cmps.length ];

      while( true ) { /* always listening for button presses */
        if ( ctrl.poll() ) {
          for ( int i = 0; i < cmps.length; i = i + 1 ) { /* store result */
            vals[ i ] = cmps[ i ].getPollData();
          }

          for ( int i = 0; i < cmps.length; i = i + 1 ) { /* display */
            float val = vals[ i ];
            synchronized(listeners) {
              Iterator iter = listeners.iterator();
              while(iter.hasNext()) {
                GuitarListener L = (GuitarListener)iter.next();
                L.GuitarPressed(val,i); /* Sends the action to the Carousel class */
              }
            }
          }
        }

        try { /* delay */
          Thread.sleep( DELAY );
        } catch ( Exception exn ) {
          System.out.println( exn ); System.exit( 1 );
        }
      }
    }
  }

  /* adding all Carousel listeners to list */
  public void addListeners(GuitarListener name) {
    listeners.add(name);
  }

  public void clearListeners() {
    listeners.clear();
  }
}
