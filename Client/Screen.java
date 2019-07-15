/*
 * @version 1.00, February 2019.
 */
public interface Screen {
    // Interface to allow Event Listeners between screens and carousel buttons
    public void performAction(Carousel.MenuButton btn);
}
