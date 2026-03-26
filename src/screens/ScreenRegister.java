package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import UI.Button;
import UI.ColorPalette;
import UI.FontPalette;

public class ScreenRegister extends JPanel implements Screen{

    public ScreenRegister() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JButton back = new Button("Back");
        back.setAlignmentX(LEFT_ALIGNMENT);
        back.setPreferredSize(new Dimension(140, 40));
        back.setMaximumSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.START_MENU));

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.add(back);

        JLabel register = new JLabel("Register");
        register.setAlignmentX(CENTER_ALIGNMENT);
        register.setFont(FontPalette.TITLE);
        register.setForeground(ColorPalette.WHITE);

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);

        JTextField usernameField = new JTextField(20);
        usernameField.setBackground(ColorPalette.WHITE);

        JLabel password1 = new JLabel("Password");
        password1.setFont(FontPalette.TEXT);
        password1.setForeground(ColorPalette.WHITE);

        JPasswordField passwordField1 = new JPasswordField(20);
        passwordField1.setBackground(ColorPalette.WHITE);

        JLabel password2 = new JLabel("Confirm");
        password2.setFont(FontPalette.TEXT);
        password2.setForeground(ColorPalette.WHITE);

        JPasswordField passwordField2 = new JPasswordField(20);
        passwordField2.setBackground(ColorPalette.WHITE);

        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBackground(ColorPalette.BLACK);
        formPanel.setAlignmentX(CENTER_ALIGNMENT);

        formPanel.add(username);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(usernameField);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(password1);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField1);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(password2);
        formPanel.add(Box.createVerticalStrut(5));
        formPanel.add(passwordField2);

        JLabel message = new JLabel("Create an account to continue");
        message.setAlignmentX(CENTER_ALIGNMENT);
        message.setFont(FontPalette.TEXT);
        message.setForeground(ColorPalette.DIMMED_WHITE);

        JButton create = new Button("Create");
        create.setAlignmentX(CENTER_ALIGNMENT);
        create.setPreferredSize(new Dimension(140, 40));
        create.setMaximumSize(new Dimension(140, 40));
        create.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));

        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(register);
        this.add(Box.createVerticalStrut(20));
        this.add(formPanel);
        this.add(Box.createVerticalStrut(15));
        this.add(message);
        this.add(Box.createVerticalStrut(15));
        this.add(create);
        this.add(Box.createVerticalGlue());
    }
}
