import java.util.ArrayList;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
/*
 * Main.
 *
 * @version 1.00 February 2019
 */
public class Main extends Thread {
    public static void main(String[] args) {
        /* creates new thread to always listen for guiter buttons being pressed */
        PollGuitar guitar = new PollGuitar();
        guitar.start();

        Main main = new Main();
        main.startSlashMode(guitar);
    }

    public void startSlashMode(PollGuitar guitar) {
        Slash slash = new Slash(guitar);
    }
}
