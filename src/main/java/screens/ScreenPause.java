package screens;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenPause extends JPanel implements Screen{

    public ScreenPause() {

        super();
        this.setBackground(ColorPalette.GREEN);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel pause = new JLabel("Pause");
        pause.setFont(FontPalette.TITLE);
        pause.setForeground(ColorPalette.WHITE);
        pause.setAlignmentX(CENTER_ALIGNMENT);

        Button resume = new Button("Resume");
        resume.setAlignmentX(CENTER_ALIGNMENT);
        resume.addActionListener(e -> {
            ScreenGameSidePanel gameScreen = getGameScreen();
            if (gameScreen != null) {
                gameScreen.resumeGame();
            }
        });
        
        Button restart = new Button("Restart");
        restart.setAlignmentX(CENTER_ALIGNMENT);
        restart.addActionListener(e -> {
            ScreenGameSidePanel gameScreen = getGameScreen();
            if (gameScreen != null) {
                gameScreen.stopGame();
            }
            ScreenManager.getInstance().refreshScreen(ScreenManager.GAME);
            ScreenManager.getInstance().showScreen(ScreenManager.GAME);
        });

        Button exit = new Button("Exit");
        exit.setAlignmentX(CENTER_ALIGNMENT);
        exit.addActionListener(e -> {
            ScreenGameSidePanel gameScreen = getGameScreen();
            if (gameScreen != null) {
                gameScreen.stopGame();
            }
            ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU);
        });
        
        Dimension buttonDimension = new Dimension(140, 40);
        resume.setPreferredSize(buttonDimension);
        resume.setMaximumSize(buttonDimension);
        restart.setPreferredSize(buttonDimension);
        restart.setMaximumSize(buttonDimension);
        exit.setPreferredSize(buttonDimension);
        exit.setMaximumSize(buttonDimension);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ColorPalette.GREEN);
        
        buttonPanel.add(resume);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(restart);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exit);
        
        this.add(Box.createVerticalGlue());
        this.add(pause);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);
        this.add(Box.createVerticalGlue());
    }

    private ScreenGameSidePanel getGameScreen() {
        Screen screen = ScreenManager.getInstance().getScreen(ScreenManager.GAME);
        if (screen instanceof ScreenGameSidePanel gameScreen) {
            return gameScreen;
        }
        return null;
    }
}
