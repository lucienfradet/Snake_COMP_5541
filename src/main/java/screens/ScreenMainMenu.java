package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Main;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenMainMenu extends JPanel implements Screen {
    
    private final JPanel loginInfoPanel;

    public ScreenMainMenu() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);

        JButton logout = new Button("Logout");
        logout.setAlignmentX(LEFT_ALIGNMENT);
        logout.setPreferredSize(new Dimension(140, 40));
        logout.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.START_MENU));

        topPanel.add(logout);
        topPanel.add(Box.createHorizontalGlue());

        JLabel snakeGame = new JLabel("Snake Game");
        snakeGame.setFont(FontPalette.TITLE);
        snakeGame.setForeground(ColorPalette.WHITE);
        snakeGame.setAlignmentX(CENTER_ALIGNMENT);

        Button play = new Button("Play");
        play.setAlignmentX(CENTER_ALIGNMENT);
        play.setMaximumSize(new Dimension(140, 40));
        play.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAP_SELECT));

        Button stats = new Button("Stats");
        stats.setAlignmentX(CENTER_ALIGNMENT);
        stats.setMaximumSize(new Dimension(140, 40));
        stats.addActionListener(e -> {
            ScreenManager.getInstance().refreshScreen(ScreenManager.STATS);
            ScreenManager.getInstance().showScreen(ScreenManager.STATS);
        });

        Button manageAccount = new Button("");
        JLabel line1 = new JLabel("Manage");
        line1.setAlignmentX(CENTER_ALIGNMENT);
        line1.setForeground(ColorPalette.WHITE);
        line1.setFont(FontPalette.TEXT);
        JLabel line2 = new JLabel("Account");
        line2.setAlignmentX(CENTER_ALIGNMENT);
        line2.setForeground(ColorPalette.WHITE);
        line2.setFont(FontPalette.TEXT);
        manageAccount.add(line1);
        manageAccount.add(line2);
        manageAccount.setAlignmentX(CENTER_ALIGNMENT);
        manageAccount.setLayout(new BoxLayout(manageAccount, BoxLayout.Y_AXIS));
        manageAccount.setMaximumSize(new Dimension(140, 60));
        manageAccount.setPreferredSize(new Dimension(140, 60));
        manageAccount.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.ACCOUNT_MANAGER));

        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(snakeGame);
        middlePanel.add(Box.createVerticalStrut(20));
        middlePanel.add(play);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(stats);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(manageAccount);
        middlePanel.add(Box.createVerticalStrut(20));
        

        loginInfoPanel = ScreenManager.displayUserInfo(Main.loginUser.getUsername());
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(loginInfoPanel);
        bottomPanel.add(Box.createHorizontalGlue());


        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(Box.createVerticalGlue());
        this.add(bottomPanel);

    }
    
    @Override
    public void onShow() {
        ScreenManager.refreshUserInfoPanel(loginInfoPanel);
    }

}
