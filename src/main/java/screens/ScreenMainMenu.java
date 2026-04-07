package screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import app.Main;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenMainMenu extends JPanel implements Screen {
    
    private final JPanel loginInfoPanel;
    private final JPanel middlePanel;
    private final JLabel admin;
    private final Button viewAllStats;


    public ScreenMainMenu() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(ColorPalette.BLACK);

        middlePanel = new JPanel();
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

        play.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "play");

        play.getActionMap().put("play", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                play.doClick();
            }
        });

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

        viewAllStats = displayAdminButton();
        admin = new JLabel("Admin: ");
        admin.setAlignmentX(RIGHT_ALIGNMENT);
        admin.setFont(FontPalette.TEXT);
        admin.setForeground(ColorPalette.RED);

        if (Main.loginUser.isAdmin()){
            middlePanel.add(admin);
            middlePanel.add(viewAllStats);
        }

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
        updateOptions();
    }

    public Button displayAdminButton(){
        Button viewAllStats = new Button("");
        JLabel line1 = new JLabel("View All");
        line1.setAlignmentX(CENTER_ALIGNMENT);
        line1.setForeground(ColorPalette.WHITE);
        line1.setFont(FontPalette.TEXT);
        JLabel line2 = new JLabel("Stats");
        line2.setAlignmentX(CENTER_ALIGNMENT);
        line2.setForeground(ColorPalette.WHITE);
        line2.setFont(FontPalette.TEXT);
        viewAllStats.add(line1);
        viewAllStats.add(line2);
        viewAllStats.setAlignmentX(CENTER_ALIGNMENT);
        viewAllStats.setLayout(new BoxLayout(viewAllStats, BoxLayout.Y_AXIS));
        viewAllStats.setMaximumSize(new Dimension(140, 60));
        viewAllStats.setPreferredSize(new Dimension(140, 60));
        viewAllStats.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.ADMIN_STATS));
        return viewAllStats;
    }

    public void updateOptions(){
        middlePanel.remove(viewAllStats);
        middlePanel.remove(admin);
        if (Main.loginUser.isAdmin()) {
            middlePanel.add(admin);
            middlePanel.add(viewAllStats);
        }
        middlePanel.revalidate();
        middlePanel.repaint();
    }

}
