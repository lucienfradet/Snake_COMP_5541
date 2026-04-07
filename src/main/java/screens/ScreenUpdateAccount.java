package screens;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.Main;
import db.UserDB;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenUpdateAccount extends JPanel implements Screen{

    private final JPanel loginInfoPanel;
    private final JTextField usernameField;
    private final JPasswordField passwordField1;
    private final JPasswordField passwordField2;
    private final JTextArea message;


    public ScreenUpdateAccount() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        Button back = new Button("Back");
        back.setAlignmentX(LEFT_ALIGNMENT);
        back.setMaximumSize(new Dimension(140, 40));
        back.setPreferredSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.ACCOUNT_MANAGER));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setAlignmentX(CENTER_ALIGNMENT);
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.add(back);
        
        JLabel title1 = new JLabel("Enter the fields you");
        title1.setHorizontalAlignment(SwingConstants.CENTER);
        title1.setFont(new Font("Yoster Island", Font.BOLD, 30));
        title1.setForeground(ColorPalette.WHITE);

        JLabel title2 = new JLabel("wish to change");
        title2.setHorizontalAlignment(SwingConstants.CENTER);
        title2.setFont(new Font("Yoster Island", Font.BOLD, 30));
        title2.setForeground(ColorPalette.WHITE);

        JPanel titlePanel = new JPanel();
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.Y_AXIS));
        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
        titlePanel.setBackground(ColorPalette.BLACK);
        titlePanel.add(title1);
        titlePanel.add(title2);

        JLabel username = new JLabel("Username");
        username.setAlignmentY(CENTER_ALIGNMENT);
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);
        username.setPreferredSize(new Dimension(120, 40));

        usernameField = new JTextField(20);
        usernameField.setAlignmentY(CENTER_ALIGNMENT);
        usernameField.setFont(FontPalette.TEXT);
        usernameField.setForeground(ColorPalette.BLACK);
        usernameField.setBackground(ColorPalette.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        usernameField.setPreferredSize(new Dimension(200, 30));
        usernameField.setMaximumSize(new Dimension(200, 30));

        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new BoxLayout(usernamePanel, BoxLayout.X_AXIS));
        usernamePanel.setAlignmentX(CENTER_ALIGNMENT);
        usernamePanel.setPreferredSize(new Dimension(452, 60));
        usernamePanel.setMaximumSize(new Dimension(452, 60));
        usernamePanel.setBackground(ColorPalette.BLACK);
        usernamePanel.setBorder(null);
        usernamePanel.add(Box.createHorizontalGlue());
        usernamePanel.add(username);
        usernamePanel.add(Box.createHorizontalStrut(10));
        usernamePanel.add(usernameField);
        usernamePanel.add(Box.createHorizontalGlue());
        
        JLabel new_ = new JLabel("New");
        new_.setFont(FontPalette.TEXT);
        new_.setForeground(ColorPalette.WHITE);
        JLabel password = new JLabel("password");
        password.setFont(FontPalette.TEXT);
        password.setForeground(ColorPalette.WHITE);
    
        JLabel password1 = new JLabel();
        password1.setAlignmentY(CENTER_ALIGNMENT);
        password1.setLayout(new BoxLayout(password1, BoxLayout.Y_AXIS));
        password1.setBackground(ColorPalette.BLACK);
        password1.setPreferredSize(new Dimension(120, 80));
        password1.setMaximumSize(new Dimension(120, 80));
        password1.add(new_);
        password1.add(password);

        passwordField1 = new JPasswordField(20);
        passwordField1.setAlignmentY(CENTER_ALIGNMENT);
        passwordField1.setFont(FontPalette.TEXT);
        passwordField1.setForeground(ColorPalette.BLACK);
        passwordField1.setBackground(ColorPalette.WHITE);
        passwordField1.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        passwordField1.setPreferredSize(new Dimension(200, 30));
        passwordField1.setMaximumSize(new Dimension(200, 30));

        JPanel passwordPanel1 = new JPanel();
        passwordPanel1.setLayout(new BoxLayout(passwordPanel1, BoxLayout.X_AXIS));
        passwordPanel1.setBorder(null);
        passwordPanel1.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel1.setPreferredSize(new Dimension(452, 80));
        passwordPanel1.setMaximumSize(new Dimension(452, 80));
        passwordPanel1.setBackground(ColorPalette.BLACK);
        passwordPanel1.add(Box.createHorizontalGlue());
        passwordPanel1.add(password1);
        passwordPanel1.add(Box.createHorizontalStrut(10));
        passwordPanel1.add(passwordField1);
        passwordPanel1.add(Box.createHorizontalGlue());

        JLabel confirm = new JLabel("Confirm");
        confirm.setFont(FontPalette.TEXT);
        confirm.setForeground(ColorPalette.WHITE);
        JLabel password_ = new JLabel("password");
        password_.setFont(FontPalette.TEXT);
        password_.setForeground(ColorPalette.WHITE);
    
        JLabel password2 = new JLabel();
        password2.setAlignmentY(CENTER_ALIGNMENT);
        password2.setLayout(new BoxLayout(password2, BoxLayout.Y_AXIS));
        password2.setBackground(ColorPalette.BLACK);
        password2.setPreferredSize(new Dimension(120, 60));
        password2.setMaximumSize(new Dimension(120, 60));
        password2.add(confirm);
        password2.add(password_);

        passwordField2 = new JPasswordField(20);
        passwordField2.setAlignmentY(CENTER_ALIGNMENT);
        passwordField2.setFont(FontPalette.TEXT);
        passwordField2.setForeground(ColorPalette.BLACK);
        passwordField2.setBackground(ColorPalette.WHITE);
        passwordField2.setBorder(BorderFactory.createLineBorder(ColorPalette.WHITE));
        passwordField2.setPreferredSize(new Dimension(200, 30));
        passwordField2.setMaximumSize(new Dimension(200, 30));

        JPanel passwordPanel2 = new JPanel();
        passwordPanel2.setLayout(new BoxLayout(passwordPanel2, BoxLayout.X_AXIS));
        passwordPanel2.setBorder(null);
        passwordPanel2.setAlignmentX(CENTER_ALIGNMENT);
        passwordPanel2.setPreferredSize(new Dimension(452, 60));
        passwordPanel2.setMaximumSize(new Dimension(452, 60));
        passwordPanel2.setBackground(ColorPalette.BLACK);
        passwordPanel2.add(Box.createHorizontalGlue());
        passwordPanel2.add(password2);
        passwordPanel2.add(Box.createHorizontalStrut(10));
        passwordPanel2.add(passwordField2);
        passwordPanel2.add(Box.createHorizontalGlue());

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
        messagePanel.setPreferredSize(new Dimension(452, 50));
        messagePanel.setMaximumSize(new Dimension(452, 50));
        messagePanel.add(message);

        Button update = new Button("Update");
        update.setAlignmentX(CENTER_ALIGNMENT);
        update.setMaximumSize(new Dimension(140, 40));
        update.setPreferredSize(new Dimension(140, 40));
        update.addActionListener(e -> {
            try {
                String userUser = usernameField.getText();
                if (userUser.length() > 0){
                    if (userUser.length() < 5) {
                        throw new Exception("Username must be at least 5 characters long");
                    }
                    if (!(userUser.matches("^[a-zA-Z0-9]+$"))) {
                        throw new Exception("Username must be alphanumerical");
                    }
                    if (!UserDB.isUniqueUsername(userUser)) {
                        throw new Exception("Username already exists");
                    }
                }

                String userPassword = new String(passwordField1.getPassword());
                if (userPassword.length() > 0){
                    if (userPassword.length() < 8) {
                        throw new Exception("Password must be at least 8 characters long");
                    }
                    if (!(userPassword.matches("^[a-zA-Z0-9]+$"))) {
                        throw new Exception("Password must be alphanumerical");
                    }
                    String userPassword2 = new String(passwordField2.getPassword());
                    if (!userPassword.equals(userPassword2)) {
                        throw new Exception("Both passwords must be the same");
                    }
                }

                if (userUser.length() > 0){
                    JOptionPane.showMessageDialog(null, "Username Changed Successfully", "Update",
                    JOptionPane.INFORMATION_MESSAGE);
                    UserDB.updateUsername(Main.loginUser.getId(), userUser);
                    Main.loginUser.setUsername(userUser);
                }
                if (userPassword.length() > 0){
                    JOptionPane.showMessageDialog(null, "Password Changed Successfully", "Update",
                    JOptionPane.INFORMATION_MESSAGE);
                    UserDB.updatePassword(Main.loginUser.getId(), userPassword);
                }

                message.setText(" ");
                ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU);
            } catch (Exception er) {
                message.setText("Error: " + er.getMessage());
            }
        });
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.add(titlePanel);
        middlePanel.add(Box.createVerticalStrut(15));
        middlePanel.add(usernamePanel);
        middlePanel.add(Box.createVerticalStrut(15));
        middlePanel.add(passwordPanel1);
        middlePanel.add(Box.createVerticalStrut(15));
        middlePanel.add(passwordPanel2);
        middlePanel.add(Box.createVerticalStrut(15));
        middlePanel.add(messagePanel);
        middlePanel.add(update);
        middlePanel.add(Box.createVerticalGlue());

        loginInfoPanel = ScreenManager.displayUserInfo(Main.loginUser.getUsername());
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(loginInfoPanel);
        bottomPanel.add(Box.createHorizontalGlue());
        
        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);

    }

    @Override
    public void onShow() {
        ScreenManager.refreshUserInfoPanel(loginInfoPanel);
        usernameField.setText("");
        passwordField1.setText("");
        passwordField2.setText("");
        message.setText("");
    }
}
