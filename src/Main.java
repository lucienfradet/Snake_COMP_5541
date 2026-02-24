import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {

		//Creating the window with all its awesome snaky features
		Window window= new Window();
		
		//Setting up the window settings
		window.setTitle("Snake");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();	//Fit window sizet to preferred JPanel sizes
		window.setLocationRelativeTo(null);
		window.setVisible(true);             

	}
}
