package test;

import CarouselModel.java;
import CarouselController.java;
import Carousel.java;
import java.util.ArrayList;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;

 /**
  * CarouselTest Unit Testing for the Carousel
  *
  * @version 1.00
  */
public class CarouselTest {
  Carousel carousel;
  CarouselController controller;
  CarouselModel model;
  ArrayList<Carousel.MenuButton> buttons;
  MenuButton but0;
  MenuButton but1;
  MenuButton but2;
  MenuButton but3;
  MenuButton but4;

  @Before
  public void setUpBeforeClass() {
    //Model, view, controller, testing all 3 together
    model = new CarouselModel();
    carousel = new Carousel(model);
    controller = new CarouselController(model, carousel);

    buttons = new ArrayList<Carousel.MenuButton>();
    but0 = carousel.new MenuButton("TestPic"); //Creating an initial carousel to test
    but1 = carousel.new MenuButton("TestPic");
    but2 = carousel.new MenuButton("TestPic");
    but3 = carousel.new MenuButton("TestPic");
    but4 = carousel.new MenuButton("TestPic");

    buttons.add(but0);
    buttons.add(but1);
    buttons.add(but2);
    buttons.add(but3);
    buttons.add(but4);
  }

  @After
  public void tearDownAfterClass() {
    model = null;
    carousel = null;
    controller = null;
  }

  @Test //Test buttons have been added to carousel
  public void addButtonsTest() {
    CarouselModel.addButtons(buttons);
    assertequals(model.optionList.size(), 5);
  }

  @Test //Test the carousel moves right
  public void moveRightTest() {
    CarouselModel.addButtons(buttons)
    assertequals(model.optionList.get(0), but0);
    CarouselModel.moveRight();
    assertequals(model.optionList.get(0), but1);
  }

  @Test //Test the carousel moves left
  public void moveLeftTest() {
    CarouselModel.addButtons(buttons)
    assertequals(model.optionList.get(0), but0);
    CarouselModel.moveLeft();
    assertequals(model.optionList.get(0), but5);
  }

  @Test //Test to use the get method for the optionList
  public void getOptionListTest() {
    CarouselModel.addButtons(buttons)
    assertequals(model.optionList, model.getOptionList());
  }
}
