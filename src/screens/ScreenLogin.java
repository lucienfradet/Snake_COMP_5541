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

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.add(back);

        
        JLabel login = new JLabel("Login");
        login.setAlignmentX(CENTER_ALIGNMENT);
        login.setFont(FontPalette.TITLE);
        login.setForeground(ColorPalette.WHITE);

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);

        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(ColorPalette.WHITE);

        JPanel usernamePanel = new JPanel();
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setPreferredSize(new Dimension(300, 40));
        usernamePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        usernamePanel.setBackground(ColorPalette.BLACK);
        usernamePanel.setBorder(null);
        usernamePanel.add(username);
        usernamePanel.add(usernameField);

        JLabel password = new JLabel("Password");
        password.setFont(FontPalette.TEXT);
        password.setForeground(ColorPalette.WHITE);

        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setBackground(ColorPalette.WHITE);

        JPanel passwordPanel = new JPanel();
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setPreferredSize(new Dimension(300, 40));
        passwordPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        passwordPanel.setBackground(ColorPalette.BLACK);
        passwordPanel.setBorder(null);
        passwordPanel.add(password);
        passwordPanel.add(passwordField);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.add(usernamePanel);
        middlePanel.add(passwordPanel);

        JLabel message = new JLabel("Error: some error message");
        message.setAlignmentX(LEFT_ALIGNMENT);
        message.setFont(FontPalette.TEXT);
        message.setForeground(ColorPalette.RED);
        middlePanel.add(message);

        Button snakeUp = new Button("Snake Up!");
        snakeUp.setMaximumSize(new Dimension(200, 40));
        snakeUp.setPreferredSize(new Dimension(200, 40));
        snakeUp.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));

        this.add(back);
        this.add(Box.createVerticalGlue());
        this.add(login);
        this.add(middlePanel);
        this.add(snakeUp);
        this.add(Box.createVerticalGlue());

    }
}
