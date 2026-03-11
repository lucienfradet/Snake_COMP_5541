import javax.swing.JFrame;

import db.UserDB;

public class Main {

	public static void main(String[] args) {
    // linux specific config. Might remove.
    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
      System.setProperty("sun.java2d.opengl", "true");
    }

		// //Creating the window with all its awesome snaky features
		// Window f1= new Window();
		//
		// //Setting up the window settings
		// f1.setTitle("Snake");
		// f1.setSize(300,300);
		// f1.setVisible(true);
		// f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);             

    UserDB.createDatabase();
	}
}
