import java.awt.GridLayout;
import java.awt.Color;
import java.awt.event.KeyListener;

import javax.swing.JFrame;

class Window extends JFrame{

	private static final long serialVersionUID = -2542001418764869760L;
	public static GameGrid gGrid;
	public static int width = 20;
	public static int height = 20;
	public Window(){
		
		getContentPane().setLayout(new GridLayout(1, 1, 0, 0));
		gGrid = new GameGrid(300, Color.WHITE, Color.BLACK, Color.BLUE);
		getContentPane().add(gGrid);
		
		// initial position of the snake
		Tuple position = new Tuple(10,10);
		// passing this value to the controller
		ThreadsController c = new ThreadsController(position);
		//Let's start the game now..
		c.start();

		System.out.println("Hello");

		// Links the window to the keyboardlistenner.
		this.addKeyListener((KeyListener) new InputManager());
	}
}
