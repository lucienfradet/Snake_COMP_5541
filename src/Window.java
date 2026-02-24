import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

class Window extends JFrame{

	private static final long serialVersionUID = -2542001418764869760L;

	//Game Color Palette - To move to a separate Class/Container
	private Color emptyColor = new Color(251, 240, 216);
    private Color snakeColor = new Color(91, 112, 72);
    private Color foodColor = new Color(94, 64, 23);

	public static ScreenGame gScreen;

	public Window(){
		
		gScreen = new ScreenGame(300, emptyColor, snakeColor, foodColor);
		add(gScreen);

		// initial position of the snake
		Tuple position = new Tuple(10,10);
		ThreadsController c = new ThreadsController(position);
		//Let's start the game now..
		c.start();

		System.out.println("Hello");

		// Links the window to the keyboardlistenner.
		this.addKeyListener((KeyListener) new InputManager());
	}
}
