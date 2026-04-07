package screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

import app.Main;
import db.UserDB;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenLogin extends JPanel implements Screen {


    JTextField usernameField;
    JPasswordField passwordField;
    JTextArea message;

    public ScreenLogin() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.setBackground(ColorPalette.BLACK);
        
        Button back = new Button("Back");
        back.setAlignmentX(LEFT_ALIGNMENT);
        back.setMaximumSize(new Dimension(140, 40));
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.START_MENU));
        
        topPanel.add(back);
        topPanel.add(Box.createHorizontalGlue());

        JLabel login = new JLabel("Login");
        login.setAlignmentX(LEFT_ALIGNMENT);
        login.setFont(FontPalette.TITLE);
        login.setForeground(ColorPalette.WHITE);

        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new BoxLayout(loginPanel, BoxLayout.Y_AXIS));
        loginPanel.setAlignmentX(CENTER_ALIGNMENT);
        loginPanel.setBackground(ColorPalette.BLACK);
        
        loginPanel.add(Box.createHorizontalStrut(20));
        loginPanel.add(login);
        loginPanel.add(Box.createHorizontalGlue());

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);
        username.setPreferredSize(new Dimension(120, 40));

        usernameField = new JTextField(20);
        usernameField.setFont(FontPalette.TEXT);
        usernameField.setForeground(ColorPalette.BLACK);
        usernameField.setBackground(ColorPalette.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setMaximumSize(new Dimension(200, 30));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setPreferredSize(new Dimension(452, 40));
        usernamePanel.setMaximumSize(new Dimension(452, 40));
        usernamePanel.setBackground(ColorPalette.BLACK);
        usernamePanel.setBorder(null);
        
        usernamePanel.add(Box.createHorizontalGlue());
        usernamePanel.add(username);
        usernamePanel.add(Box.createHorizontalStrut(10));
        usernamePanel.add(usernameField);
        usernamePanel.add(Box.createHorizontalGlue());

        JLabel password = new JLabel("Password");
        password.setFont(FontPalette.TEXT);
        password.setForeground(ColorPalette.WHITE);
        password.setPreferredSize(new Dimension(120, 40));

        passwordField = new JPasswordField(20);
        passwordField.setFont(FontPalette.TEXT);
        passwordField.setForeground(ColorPalette.BLACK);
        passwordField.setBackground(ColorPalette.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        passwordField.setPreferredSize(new Dimension(200, 30));
        passwordField.setMaximumSize(new Dimension(200, 30));

        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
        passwordPanel.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel.setPreferredSize(new Dimension(452, 40));
        passwordPanel.setMaximumSize(new Dimension(452, 40));
        passwordPanel.setBackground(ColorPalette.BLACK);
       
        passwordPanel.add(Box.createHorizontalGlue());
        passwordPanel.add(password);
        passwordPanel.add(Box.createHorizontalStrut(10));
        passwordPanel.add(passwordField);
        passwordPanel.add(Box.createHorizontalGlue());

        message = new JTextArea(" ");
        message.setFont(FontPalette.TEXT);
        message.setForeground(ColorPalette.RED);
        message.setBackground(ColorPalette.BLACK);
        message.setLineWrap(true);
        message.setWrapStyleWord(true);
        message.setEditable(false);
        message.setOpaque(false);
        message.setPreferredSize(new Dimension(452, 60));
        message.setMaximumSize(new Dimension(452, 60));

        JPanel messagePanel = new JPanel();
        messagePanel.setLayout(new BoxLayout(messagePanel, BoxLayout.Y_AXIS));
        messagePanel.setAlignmentX(CENTER_ALIGNMENT);
        messagePanel.setBackground(ColorPalette.BLACK);
        messagePanel.setPreferredSize(new Dimension(452, 60));
        messagePanel.setMaximumSize(new Dimension(452, 60));
        messagePanel.add(message);
        
        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(loginPanel);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(usernamePanel);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(passwordPanel);
        middlePanel.add(Box.createVerticalStrut(10));
        middlePanel.add(messagePanel);
        middlePanel.add(Box.createVerticalGlue());

        Button snakeUp = new Button("Snake Up!");
        snakeUp.setAlignmentX(CENTER_ALIGNMENT);
        snakeUp.setMaximumSize(new Dimension(200, 40));
        snakeUp.setPreferredSize(new Dimension(200, 40));
        
        snakeUp.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "login");

        snakeUp.getActionMap().put("login", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                snakeUp.doClick();
            }
        });

        snakeUp.addActionListener(e -> {
            try {
                String userUser = usernameField.getText();
                if (userUser.length() == 0) {
                    throw new Exception("Fill in username");
                }
                if (!(userUser.matches("^[a-zA-Z0-9]+$"))) {
                    throw new Exception("Username must be alphanumerical");
                }
                String userPassword = new String(passwordField.getPassword());
                if (userPassword.length() == 0) {
                    throw new Exception("Fill in Password");
                }
                if (!(userPassword.matches("^[a-zA-Z0-9]+$"))) {
                    throw new Exception("Password must be alphanumerical");
                }
                Main.loginUser = UserDB.login(userUser, userPassword);
                message.setText(" ");
                ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU);
            } catch (Exception er) {
                message.setText("Error: " + er.getMessage());
            }
        });

        bottomPanel.add(snakeUp);

        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(bottomPanel);
        this.add(Box.createVerticalGlue());
    }

    public void onShow(){
        usernameField.setText("");
        passwordField.setText("");
        message.setText("");
    }


}
