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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import app.Main;
import db.UserDB;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenUpdateAccount extends JPanel implements Screen{

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
        
        JLabel title = new JLabel("<html>Enter the fields you<br>wish to change</html>");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Yoster Island", Font.BOLD, 30));
        title.setForeground(ColorPalette.WHITE);

        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        titlePanel.setAlignmentX(CENTER_ALIGNMENT);
        titlePanel.setBackground(ColorPalette.BLACK);
        titlePanel.setPreferredSize(new Dimension(460, 110));
        titlePanel.setMaximumSize(new Dimension(460, 110));
        titlePanel.add(title);

        JLabel username = new JLabel("Username");
        username.setFont(FontPalette.TEXT);
        username.setForeground(ColorPalette.WHITE);
        username.setPreferredSize(new Dimension(120, 40));

        JTextField usernameField = new JTextField(20);
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

        JLabel password1 = new JLabel("<html> New <br> Password </html>");
        password1.setFont(FontPalette.TEXT);
        password1.setForeground(ColorPalette.WHITE);
        password1.setPreferredSize(new Dimension(120, 40));

        JPasswordField passwordField1 = new JPasswordField(20);
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
        passwordPanel1.setPreferredSize(new Dimension(452, 40));
        passwordPanel1.setMaximumSize(new Dimension(452, 40));
        passwordPanel1.setBackground(ColorPalette.BLACK);
        passwordPanel1.add(Box.createHorizontalGlue());
        passwordPanel1.add(password1);
        passwordPanel1.add(Box.createHorizontalStrut(10));
        passwordPanel1.add(passwordField1);
        passwordPanel1.add(Box.createHorizontalGlue());

        JLabel password2 = new JLabel("<html> Confirm <br> Password </html>");
        password2.setFont(FontPalette.TEXT);
        password2.setForeground(ColorPalette.WHITE);
        password2.setPreferredSize(new Dimension(120, 40));

        JPasswordField passwordField2 = new JPasswordField(20);
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
        passwordPanel2.setPreferredSize(new Dimension(452, 40));
        passwordPanel2.setMaximumSize(new Dimension(452, 40));
        passwordPanel2.setBackground(ColorPalette.BLACK);
        passwordPanel2.add(Box.createHorizontalGlue());
        passwordPanel2.add(password2);
        passwordPanel2.add(Box.createHorizontalStrut(10));
        passwordPanel2.add(passwordField2);
        passwordPanel2.add(Box.createHorizontalGlue());

        JTextArea message = new JTextArea(" ");
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
                    UserDB.updateUsername(Main.loginUser.getId(), userUser);
                    Main.loginUser.setUsername(userUser);
                }
                if (userPassword.length() > 0){UserDB.updatePassword(Main.loginUser.getId(), userPassword);}

                message.setText(" ");
                currentUser.setText(Main.loginUser.getUsername());
                ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU);
            } catch (Exception er) {
                message.setText("Error: " + er.getMessage());
            }
        });
        
        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setAlignmentX(CENTER_ALIGNMENT);
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 0, 24));
        middlePanel.add(titlePanel);
        middlePanel.add(usernamePanel);
        middlePanel.add(Box.createVerticalStrut(15));
        middlePanel.add(passwordPanel1);
        middlePanel.add(Box.createVerticalStrut(12));
        middlePanel.add(passwordPanel2);
        middlePanel.add(Box.createVerticalStrut(12));
        middlePanel.add(messagePanel);
        middlePanel.add(update);
        middlePanel.add(Box.createVerticalGlue());

        bottomPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        bottomPanel.setAlignmentX(CENTER_ALIGNMENT);
        bottomPanel.setBackground(ColorPalette.BLACK);

        JPanel loginInfoPanel = ScreenManager.displayUserInfo(Main.loginUser.getUsername());
        bottomPanel.add(loginInfoPanel);
        
        this.add(topPanel);
        this.add(Box.createVerticalStrut(24));
        this.add(middlePanel);
        this.add(Box.createVerticalGlue());
        this.add(bottomPanel);

    }

    @Override
    public void onShow() {
        currentUser.setText(Main.loginUser.getUsername());
        bottomPanel.removeAll();
        bottomPanel.add(ScreenManager.displayUserInfo(Main.loginUser.getUsername()));
        bottomPanel.revalidate();
        bottomPanel.repaint();
    }
}
