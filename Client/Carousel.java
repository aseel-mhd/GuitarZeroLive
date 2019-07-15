import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
/*
 * Carousel.
 *
 * Abstract so can be used for multiple instances of different carousel.
 *
 * @version 1.01, February 2019.
 */
public class Carousel extends JFrame {
  CarouselModel model;
  JLabel border;

 /**
 * The constructor which sets up the GUI
 *
 */
 public Carousel(CarouselModel model) {
   this.model = model;
   setLayout(null);
   this.setFocusable(true);
   createGUI();
 }

 /**
 * Sets up the basic GUI options
 */
 public void createGUI() {
   this.setSize(500, 100);
   this.setUndecorated(true);
   this.setLocationRelativeTo(null);
   this.setVisible(true);
   this.setResizable(false);
 }

 /**
 * This function essentially redraws the carousel.
 * Will only add the first five buttons in the list of buttons
 * and will hide the rest.
 */
 public void createButtons() {
   ArrayList<MenuButton> optionList = model.getOptionList();
   for (MenuButton btn : optionList) {
     remove(btn);
     if (optionList.indexOf(btn) < 5) {
       btn.setBounds((optionList.indexOf(btn) * 100), 1, 100, 100);
       add(btn);
     }
   }
   repaint();
   revalidate();
 }

 public class MenuButton extends JButton {
   public String name;
   MenuButton(String s) {
     // Class for MenuButtons existing within Carousels
     name = s;
     ImageIcon icon = new ImageIcon(s);
     Image img = icon.getImage();
     Image newImg = img.getScaledInstance(100, 100, java.awt.Image.SCALE_SMOOTH);
     ImageIcon newIcon = new ImageIcon(newImg);
     setIcon(newIcon);
     setBorder(null);
   }
  }
}
