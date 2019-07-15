import java.util.ArrayList;

public class CarouselModel {
   ArrayList<Carousel.MenuButton> optionList;

   public CarouselModel() {
     optionList = new ArrayList<Carousel.MenuButton>();
   }

   /**
   * Function to create the buttons initially after they have
   * been passed to the object
   *
   * @param buttons A list of buttons to be displayed on the carousel
   */
   public void addButtons(ArrayList<Carousel.MenuButton> buttons) {
     for (Carousel.MenuButton btn : buttons) {
       optionList.add(btn);
     }
   }

   public void moveRight() {
     this.optionList.add(this.optionList.get(0));
     this.optionList.remove(0);
   }

   public void moveLeft() {
     int size = optionList.size();
     Carousel.MenuButton moveButton = this.optionList.get(size - 1);
     this.optionList.add(0, moveButton);
     this.optionList.remove(size);
   }

   public ArrayList<Carousel.MenuButton> getOptionList() {
     return this.optionList;
   }
}
