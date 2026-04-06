package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.Main;
import db.UserDB;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenDeleteAccount extends JPanel implements Screen {
    JLabel currentUser = new JLabel(Main.loginUser.getUsername());
    public ScreenDeleteAccount() {

        super();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(ColorPalette.BLACK);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Button back = new Button("Back");
        back.setAlignmentX(LEFT_ALIGNMENT);
        back.setMaximumSize(new Dimension(140, 40));
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.ACCOUNT_MANAGER));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        topPanel.add(back);

        JLabel deleteAccount = new JLabel("DELETE ACCOUNT");
        deleteAccount.setFont(FontPalette.TITLE);
        deleteAccount.setForeground(ColorPalette.RED);
        deleteAccount.setAlignmentX(CENTER_ALIGNMENT);

        JLabel instructions = new JLabel("Enter \"DELETE\" in the box below to confirm");
        instructions.setFont(new Font("Yoster Island", Font.BOLD, 17));
        instructions.setForeground(ColorPalette.WHITE);
        instructions.setAlignmentX(CENTER_ALIGNMENT);

        JLabel areYouSure = new JLabel("Are You Sure ?");
        areYouSure.setFont(FontPalette.TEXT);
        areYouSure.setForeground(ColorPalette.WHITE);

        JTextField confirmField = new JTextField(20);
        confirmField.setFont(FontPalette.TEXT);
        confirmField.setForeground(ColorPalette.RED);
        confirmField.setBackground(ColorPalette.WHITE);
        confirmField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        confirmField.setPreferredSize(new Dimension(300, 38));
        confirmField.setMaximumSize(new Dimension(300, 38));

        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(new BoxLayout(confirmPanel, BoxLayout.X_AXIS));
        confirmPanel.setAlignmentX(CENTER_ALIGNMENT);
        confirmPanel.setPreferredSize(new Dimension(462, 60));
        confirmPanel.setMaximumSize(new Dimension(462, 60));
        confirmPanel.setBackground(ColorPalette.BLACK);
        confirmPanel.add(areYouSure);
        confirmPanel.add(Box.createHorizontalStrut(12));
        confirmPanel.add(confirmField);

        Button delete = new Button("DELETE");
        delete.setAlignmentX(CENTER_ALIGNMENT);
        delete.setMaximumSize(new Dimension(180, 44));
        delete.setPreferredSize(new Dimension(180, 44));
        delete.putClientProperty("destructive", true);
        delete.addActionListener(e -> {
            try {
                UserDB.deleteAccount(Main.loginUser.getId());
                ScreenManager.getInstance().showScreen(ScreenManager.START_MENU);
            } catch (Exception er) {
                
            }
        });

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        middlePanel.add(Box.createVerticalStrut(78));
        middlePanel.add(deleteAccount);
        middlePanel.add(Box.createVerticalStrut(18));
        middlePanel.add(instructions);
        middlePanel.add(Box.createVerticalStrut(26));
        middlePanel.add(confirmPanel);
        middlePanel.add(Box.createVerticalStrut(22));
        middlePanel.add(delete);

        JLabel loggedInAs = new JLabel("Logged in as");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.GREEN);

        currentUser.setFont(FontPalette.TEXT);
        currentUser.setForeground(ColorPalette.WHITE);

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));
        loginInfoPanel.setBackground(ColorPalette.BLACK);
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.add(loggedInAs);
        loginInfoPanel.add(currentUser);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        bottomPanel.add(loginInfoPanel);

        add(topPanel);
        add(middlePanel);
        add(Box.createVerticalGlue());
        add(bottomPanel);
    }

    @Override
    public void onShow() {
        currentUser.setText(Main.loginUser.getUsername());
    }
}
