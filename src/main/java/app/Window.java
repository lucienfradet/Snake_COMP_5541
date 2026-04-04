package app;

import java.awt.Color;
import java.awt.event.KeyListener;
import javax.swing.JFrame;

import game.Game;
import game.ScreenGame;
import game.Tuple;
import game.InputManager;

public class Window extends JFrame{

  private static final long serialVersionUID = -2542001418764869760L;

  //Game Color Palette - To move to a separate Class/Container
  private Color emptyColor = new Color(251, 240, 216);
  private Color snakeColor = new Color(91, 112, 72);
  private Color wallColor = new Color(94, 64, 23);

  public static ScreenGame gScreen;

  public Window(){

    gScreen = new ScreenGame(15, 20, 20, emptyColor, snakeColor, wallColor);
    add(gScreen);

    // initial position of the snake
    Tuple position = new Tuple(10,10);

    //Initialize walls, load desired map layout. Map is selected by index:
    //0 - "Square" Map
    //1 - "Walls" Map
    //else - Empty Map
    Game c = new Game(0, position, 10);
    //Let's start the game now..
    c.start();

    // Links the window to the keyboardlistenner.
    this.addKeyListener((KeyListener) new InputManager());
  }
}
