package app;

import javax.swing.JFrame;

import db.UserDB;
import db.UserData;
import screens.ScreenAccountManager;
import screens.ScreenDeleteAccount;
import screens.ScreenGameOver;
import screens.ScreenLogin;
import screens.ScreenMainMenu;
import screens.ScreenManager;
import screens.ScreenMapSelect;
import screens.ScreenPause;
import screens.ScreenRegister;
import screens.ScreenStartMenu;
import screens.ScreenStats;
import screens.ScreenUpdateAccount;

public class Main {
  public static UserData loginUser = null;

  public static void startGame() {

    JFrame window = new JFrame();
    window.setTitle("Snake Game");
    window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    window.setSize(500, 500);
    window.setLocationRelativeTo(null);
    window.setResizable(false);

    ScreenManager manager = ScreenManager.getInstance();
    manager.setFrame(window);
    manager.addScreen(ScreenManager.START_MENU, ScreenStartMenu::new);
    manager.addScreen(ScreenManager.LOGIN, ScreenLogin::new);
    manager.addScreen(ScreenManager.REGISTER, ScreenRegister::new);
    manager.addScreen(ScreenManager.MAIN_MENU, ScreenMainMenu::new);
    manager.addScreen(ScreenManager.MAP_SELECT, ScreenMapSelect::new);
    manager.addScreen(ScreenManager.ACCOUNT_MANAGER, ScreenAccountManager::new);
    manager.addScreen(ScreenManager.UPDATE_ACCOUNT, ScreenUpdateAccount::new);
    manager.addScreen(ScreenManager.DELETE_ACCOUNT, ScreenDeleteAccount::new);
    manager.addScreen(ScreenManager.STATS, ScreenStats::new);
    manager.addScreen(ScreenManager.GAME, screens.ScreenGameSidePanel::new);
    manager.addScreen(ScreenManager.PAUSE, ScreenPause::new);
    manager.addScreen(ScreenManager.GAME_OVER, ScreenGameOver::new);

    window.setVisible(true);
    manager.showScreen(ScreenManager.START_MENU);
  }

  public static void main(String[] args) {
    // linux specific config.
    if (System.getProperty("os.name").toLowerCase().contains("linux")) {
      System.setProperty("sun.java2d.opengl", "true");
    }

    // Make sure the Database is initialize (has tables and everything needed)
    try{
      UserDB.init();
    }
    catch (Exception e){
      System.err.println("Could not initialize Database, exiting program");
      System.exit(0);
    }

    AudioManager.playLoop(AudioManager.MENU_MUSIC);
    startGame();
  }
}
