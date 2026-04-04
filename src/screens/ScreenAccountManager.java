package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UI.Button;
import UI.ColorPalette;
import UI.FontPalette;

public class ScreenAccountManager extends JPanel implements Screen{

    public ScreenAccountManager() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));
        
        topPanel.add(back);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel manageAccount = new JLabel("Manage Account");
        manageAccount.setFont(FontPalette.TITLE);
        manageAccount.setForeground(ColorPalette.WHITE);
        manageAccount.setAlignmentX(CENTER_ALIGNMENT);

        Button changeCredentials = new Button("<html>Change<br>Credentials</html>");
        changeCredentials.setAlignmentX(CENTER_ALIGNMENT);
        changeCredentials.setMaximumSize(new Dimension(160, 60));
        changeCredentials.setPreferredSize(new Dimension(160, 60));
        changeCredentials.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.UPDATE_ACCOUNT));

        Button deleteAccount = new Button("<html>Delete<br>Account</html>");
        deleteAccount.setAlignmentX(CENTER_ALIGNMENT);
        deleteAccount.setMaximumSize(new Dimension(160, 60));
        deleteAccount.setPreferredSize(new Dimension(160, 60));
        deleteAccount.putClientProperty("destructive", true);
        deleteAccount.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.DELETE_ACCOUNT));

        middlePanel.add(manageAccount);
        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(changeCredentials);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(deleteAccount);

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
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(Box.createVerticalGlue());
        this.add(bottomPanel);

    }

    @Override
    public void onShow() {

    }
}
