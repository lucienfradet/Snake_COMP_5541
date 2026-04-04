package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenAdminMainMenu extends JPanel implements Screen {

    public ScreenAdminMainMenu() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton logout = new Button("Logout");
        logout.setPreferredSize(new Dimension(140, 40));
        logout.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.START_MENU));
        
        topPanel.add(logout);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel snakeGame = new JLabel("Snake Game");
        snakeGame.setFont(FontPalette.TITLE);
        snakeGame.setForeground(ColorPalette.WHITE);
        snakeGame.setAlignmentX(CENTER_ALIGNMENT);

        Button play = new Button("Play");
        play.setAlignmentX(CENTER_ALIGNMENT);
        play.setMaximumSize(new Dimension(140, 40));
        play.addActionListener(e -> {
            ScreenManager.getInstance().refreshScreen(ScreenManager.GAME);
            ScreenManager.getInstance().showScreen(ScreenManager.GAME);
        });

        Button stats = new Button("Stats");
        stats.setAlignmentX(CENTER_ALIGNMENT);
        stats.setMaximumSize(new Dimension(140, 40));

        Button manageAccount = new Button("<html>Manage<br>Account</html>");
        manageAccount.setAlignmentX(CENTER_ALIGNMENT);
        manageAccount.setMaximumSize(new Dimension(140, 60));
        manageAccount.setPreferredSize(new Dimension(140, 60));

        JLabel admin = new JLabel("Admin:");
        admin.setAlignmentX(RIGHT_ALIGNMENT);
        admin.setFont(FontPalette.TEXT);
        admin.setForeground(ColorPalette.RED);

        Button viewAllStats = new Button("<html>View All<br>Stats</html>");
        viewAllStats.setAlignmentX(CENTER_ALIGNMENT);
        viewAllStats.setMaximumSize(new Dimension(140, 60));
        viewAllStats.setPreferredSize(new Dimension(140, 60));

        middlePanel.add(Box.createVerticalStrut(5));
        middlePanel.add(snakeGame);
        middlePanel.add(Box.createVerticalStrut(5));
        middlePanel.add(play);
        middlePanel.add(Box.createVerticalStrut(5));
        middlePanel.add(stats);
        middlePanel.add(Box.createVerticalStrut(5));
        middlePanel.add(manageAccount);
        middlePanel.add(Box.createVerticalStrut(1));
        middlePanel.add(admin);
        middlePanel.add(Box.createVerticalStrut(1));
        middlePanel.add(viewAllStats);


        JLabel loggedInAs = new JLabel("Logged in as");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.GREEN);
        loggedInAs.setAlignmentX(CENTER_ALIGNMENT);
        loggedInAs.setAlignmentY(CENTER_ALIGNMENT);

        JLabel currentUser = new JLabel("Bard Tarbox");
        currentUser.setFont(FontPalette.TEXT);
        currentUser.setForeground(ColorPalette.WHITE);
        currentUser.setAlignmentX(CENTER_ALIGNMENT);
        currentUser.setAlignmentY(CENTER_ALIGNMENT);
        
        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));
        loginInfoPanel.setBackground(ColorPalette.BLACK);
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.setAlignmentY(CENTER_ALIGNMENT);

        loginInfoPanel.add(loggedInAs);
        loginInfoPanel.add(currentUser);
        bottomPanel.add(loginInfoPanel);

        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);

    }
}
