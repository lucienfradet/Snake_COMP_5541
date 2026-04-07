package screens;

import java.awt.Dimension;
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

public class ScreenAccountManager extends JPanel implements Screen{

    private final JPanel loginInfoPanel;

    public ScreenAccountManager() {

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
        middlePanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JButton back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));
        
        topPanel.add(back);
        topPanel.add(Box.createHorizontalGlue());

        JLabel manage = new JLabel("Manage");
        manage.setFont(FontPalette.TITLE);
        manage.setForeground(ColorPalette.WHITE);
        manage.setAlignmentX(CENTER_ALIGNMENT);

        JLabel account = new JLabel("Account");
        account.setFont(FontPalette.TITLE);
        account.setForeground(ColorPalette.WHITE);
        account.setAlignmentX(CENTER_ALIGNMENT);

        Button changeCredentials = new Button("Change Credentials");
        changeCredentials.setAlignmentX(CENTER_ALIGNMENT);
        changeCredentials.setLayout(new BoxLayout(changeCredentials, BoxLayout.Y_AXIS));
        changeCredentials.setMaximumSize(new Dimension(300, 40));
        changeCredentials.setPreferredSize(new Dimension(300, 40));
        changeCredentials.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.UPDATE_ACCOUNT));

        Button deleteAccount = new Button("Delete Account");
        deleteAccount.setAlignmentX(CENTER_ALIGNMENT);
        deleteAccount.setLayout(new BoxLayout(deleteAccount, BoxLayout.Y_AXIS));
        deleteAccount.setMaximumSize(new Dimension(300, 40));
        deleteAccount.setPreferredSize(new Dimension(300, 40));
        deleteAccount.putClientProperty("destructive", true);
        deleteAccount.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.DELETE_ACCOUNT));

        middlePanel.add(manage);
        middlePanel.add(account);
        middlePanel.add(Box.createVerticalStrut(20));
        middlePanel.add(changeCredentials);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(deleteAccount);

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
