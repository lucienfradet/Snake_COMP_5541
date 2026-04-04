package screens;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenStartMenu extends JPanel implements Screen{

    public ScreenStartMenu() {

        super();
        this.setAlignmentX(CENTER_ALIGNMENT);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);

        JLabel snakeGame = new JLabel("Snake Game");
        snakeGame.setAlignmentX(CENTER_ALIGNMENT);
        snakeGame.setFont(FontPalette.TITLE);
        snakeGame.setForeground(ColorPalette.WHITE);
        
        Button login = new Button("Login");
        login.setAlignmentX(CENTER_ALIGNMENT);
        login.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.LOGIN));

        Button register = new Button("Register");
        register.setAlignmentX(CENTER_ALIGNMENT);
        register.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.REGISTER));

        Button quit = new Button("Quit");
        quit.setAlignmentX(CENTER_ALIGNMENT);
        quit.addActionListener(e -> System.exit(0));

        Dimension buttonDimension = new Dimension(140, 40);
        login.setPreferredSize(buttonDimension);
        login.setMaximumSize(buttonDimension);
        register.setPreferredSize(buttonDimension);
        register.setMaximumSize(buttonDimension);
        quit.setPreferredSize(buttonDimension);
        quit.setMaximumSize(buttonDimension);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ColorPalette.BLACK);
        
        buttonPanel.add(login);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(register);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(quit);
        

        this.add(Box.createVerticalGlue());
        this.add(snakeGame);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);
        this.add(Box.createVerticalGlue());

    }

}
