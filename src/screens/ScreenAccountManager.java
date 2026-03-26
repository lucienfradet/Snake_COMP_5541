package screens;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ScreenAccountManager extends JPanel implements Screen{

    public ScreenAccountManager() {

        super();
        JButton back;
        JLabel loggedInAs;
        JLabel currentUser;

        // view 1
        JLabel manageAccount;
        JButton changeCredentials;
        JButton deleteAccount;

        // view 2
        JLabel fillTheFields;
        JLabel username;
        JTextField usernamField;
        JLabel password1;
        JPasswordField passwordField1;
        JLabel password2;
        JPasswordField passwordField2;
        JLabel confirmation;
        JLabel message;
        JButton update;

        // view 3
        JLabel deleteAccountTitle;
        JLabel areYouSure;
        JTextField delete;
        JButton deleteButton;
        

    }

    @Override
    public void onShow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'onShow'");
    }
}
