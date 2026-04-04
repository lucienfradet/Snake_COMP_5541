package rework;

import java.awt.event.KeyListener;

import javax.swing.JFrame;

import UI.ColorPalette;

public class Window extends JFrame{

	private static final long serialVersionUID = -2542001418764869760L;

	public static ScreenGame gScreen;

	public Window(){
		
		gScreen = new ScreenGame(300, ColorPalette.WHITE, ColorPalette.GREEN, ColorPalette.BROWN);

		// initial position of the snake
		Tuple position = new Tuple(10,10);
		Game c = new Game(position, gScreen, null);
		//Let's start the game now..
		c.start();

		// Links the window to the keyboardlistenner.
		this.addKeyListener((KeyListener) new InputManager());

		
	}
}
