package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenRegister extends JPanel implements Screen{

    public ScreenRegister() {
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
        
        JLabel register = new JLabel("Register");
        register.setHorizontalAlignment(SwingConstants.LEFT);
        register.setFont(FontPalette.TITLE);
        register.setForeground(ColorPalette.WHITE);

        JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        loginPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginPanel.setBackground(ColorPalette.BLACK);
        loginPanel.setPreferredSize(new Dimension(452, 80));
        loginPanel.setMaximumSize(new Dimension(452, 80));
        loginPanel.add(register);

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);
        username.setPreferredSize(new Dimension(120, 40));

        JTextField usernameField = new JTextField(20);
        usernameField.setFont(FontPalette.TEXT);
        usernameField.setForeground(ColorPalette.BLACK);
        usernameField.setBackground(ColorPalette.WHITE);
        usernameField.setPreferredSize(new Dimension(280, 30));
        usernameField.setMaximumSize(new Dimension(280, 30));

        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        usernamePanel.setAlignmentX(LEFT_ALIGNMENT);
        usernamePanel.setPreferredSize(new Dimension(452, 46));
        usernamePanel.setMaximumSize(new Dimension(452, 46));
        usernamePanel.setBackground(ColorPalette.BLACK);
        usernamePanel.setBorder(null);
        usernamePanel.add(username);
        usernamePanel.add(Box.createHorizontalStrut(12));
        usernamePanel.add(usernameField);

        JLabel password1 = new JLabel("Password");
        password1.setFont(FontPalette.TEXT);
        password1.setForeground(ColorPalette.WHITE);
        password1.setPreferredSize(new Dimension(120, 40));

        JPasswordField passwordField1 = new JPasswordField(20);
        passwordField1.setFont(FontPalette.TEXT);
        passwordField1.setForeground(ColorPalette.BLACK);
        passwordField1.setBackground(ColorPalette.WHITE);
        passwordField1.setPreferredSize(new Dimension(280, 30));
        passwordField1.setMaximumSize(new Dimension(280, 30));

        JPanel passwordPanel1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordPanel1.setAlignmentX(LEFT_ALIGNMENT);
        passwordPanel1.setPreferredSize(new Dimension(452, 46));
        passwordPanel1.setMaximumSize(new Dimension(452, 46));
        passwordPanel1.setBackground(ColorPalette.BLACK);
        passwordPanel1.setBorder(null);
        passwordPanel1.add(password1);
        passwordPanel1.add(Box.createHorizontalStrut(12));
        passwordPanel1.add(passwordField1);

        JLabel password2 = new JLabel("<html>Confirm<br>Password</html>");
        password2.setFont(new Font("Yoster Island", Font.BOLD, 15));
        password2.setForeground(ColorPalette.WHITE);
        password2.setPreferredSize(new Dimension(120, 40));

        JPasswordField passwordField2 = new JPasswordField(20);
        passwordField2.setFont(FontPalette.TEXT);
        passwordField2.setForeground(ColorPalette.BLACK);
        passwordField2.setBackground(ColorPalette.WHITE);
        passwordField2.setPreferredSize(new Dimension(280, 30));
        passwordField2.setMaximumSize(new Dimension(280, 30));

        JPanel passwordPanel2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        passwordPanel2.setAlignmentX(LEFT_ALIGNMENT);
        passwordPanel2.setPreferredSize(new Dimension(452, 46));
        passwordPanel2.setMaximumSize(new Dimension(452, 46));
        passwordPanel2.setBackground(ColorPalette.BLACK);
        passwordPanel2.setBorder(null);
        passwordPanel2.add(password2);
        passwordPanel2.add(Box.createHorizontalStrut(12));
        passwordPanel2.add(passwordField2);

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
        middlePanel.add(passwordPanel1);
        middlePanel.add(Box.createVerticalStrut(8));
        middlePanel.add(passwordPanel2);
        middlePanel.add(Box.createVerticalStrut(8));
        middlePanel.add(messagePanel);
        middlePanel.add(Box.createVerticalGlue());

        Button create = new Button("Create");
        create.setAlignmentX(CENTER_ALIGNMENT);
        create.setMaximumSize(new Dimension(200, 40));
        create.setPreferredSize(new Dimension(200, 40));
        create.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(create);

        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(Box.createVerticalStrut(18));
        this.add(bottomPanel);
        this.add(Box.createVerticalGlue());
    }
}
