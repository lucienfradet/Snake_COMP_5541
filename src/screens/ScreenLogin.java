package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import UI.Button;
import UI.ColorPalette;
import UI.FontPalette;

public class ScreenLogin extends JPanel implements Screen {

    public ScreenLogin() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Button back = new Button("Back");
        back.setAlignmentX(LEFT_ALIGNMENT);
        back.setMaximumSize(new Dimension(140, 40));
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.START_MENU));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        topPanel.add(back);
        
        JLabel login = new JLabel("Login");
        login.setHorizontalAlignment(SwingConstants.LEFT);
        login.setFont(FontPalette.TITLE);
        login.setForeground(ColorPalette.WHITE);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        loginPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginPanel.setBackground(ColorPalette.BLACK);
        loginPanel.setPreferredSize(new Dimension(452, 80));
        loginPanel.setMaximumSize(new Dimension(452, 80));
        loginPanel.add(login);

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);
        username.setPreferredSize(new Dimension(120, 40));

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(FontPalette.TEXT);
        usernameField.setForeground(ColorPalette.BLACK);
        usernameField.setBackground(ColorPalette.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        usernameField.setPreferredSize(new Dimension(300, 30));
        usernameField.setMaximumSize(new Dimension(300, 30));

        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        usernamePanel.setAlignmentX(LEFT_ALIGNMENT);
        usernamePanel.setPreferredSize(new Dimension(452, 40));
        usernamePanel.setMaximumSize(new Dimension(452, 40));
        usernamePanel.setBackground(ColorPalette.BLACK);
        usernamePanel.setBorder(null);
        usernamePanel.add(username);
        usernamePanel.add(Box.createHorizontalStrut(12));
        usernamePanel.add(usernameField);

        JLabel password = new JLabel("Password");
        password.setFont(FontPalette.TEXT);
        password.setForeground(ColorPalette.WHITE);
        password.setPreferredSize(new Dimension(120, 40));

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setFont(FontPalette.TEXT);
        passwordField.setForeground(ColorPalette.BLACK);
        passwordField.setBackground(ColorPalette.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        passwordField.setPreferredSize(new Dimension(300, 30));
        passwordField.setMaximumSize(new Dimension(300, 30));

        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordPanel.setAlignmentX(LEFT_ALIGNMENT);
        passwordPanel.setPreferredSize(new Dimension(452, 40));
        passwordPanel.setMaximumSize(new Dimension(452, 40));
        passwordPanel.setBackground(ColorPalette.BLACK);
        passwordPanel.setBorder(null);
        passwordPanel.add(password);
        passwordPanel.add(Box.createHorizontalStrut(12));
        passwordPanel.add(passwordField);

        JLabel message = new JLabel("Error: some error message");
        message.setFont(FontPalette.TEXT);
        message.setForeground(ColorPalette.RED);

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        messagePanel.setAlignmentX(LEFT_ALIGNMENT);
        messagePanel.setBackground(ColorPalette.BLACK);
        messagePanel.setPreferredSize(new Dimension(452, 40));
        messagePanel.setMaximumSize(new Dimension(452, 40));
        messagePanel.add(message);
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setPreferredSize(new Dimension(500, 220));
        middlePanel.setMaximumSize(new Dimension(500, 220));
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        middlePanel.add(loginPanel);
        middlePanel.add(Box.createVerticalStrut(6));
        middlePanel.add(usernamePanel);
        middlePanel.add(Box.createVerticalStrut(8));
        middlePanel.add(passwordPanel);
        middlePanel.add(Box.createVerticalStrut(8));
        middlePanel.add(messagePanel);
        middlePanel.add(Box.createVerticalGlue());

        Button snakeUp = new Button("Snake Up!");
        snakeUp.setAlignmentX(CENTER_ALIGNMENT);
        snakeUp.setMaximumSize(new Dimension(200, 40));
        snakeUp.setPreferredSize(new Dimension(200, 40));
        snakeUp.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(snakeUp);

        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(Box.createVerticalStrut(18));
        this.add(bottomPanel);
        this.add(Box.createVerticalGlue());
    }
}
