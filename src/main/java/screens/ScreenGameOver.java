package screens;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenGameOver extends JPanel implements Screen{

    public ScreenGameOver() {

        super();
        this.setBackground(ColorPalette.RED);
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JLabel gameOver = new JLabel("Game Over");
        gameOver.setFont(FontPalette.TITLE);
        gameOver.setForeground(ColorPalette.WHITE);
        gameOver.setAlignmentX(CENTER_ALIGNMENT);
        
        JButton restart = new Button("Restart");
        restart.setAlignmentX(CENTER_ALIGNMENT);
        restart.addActionListener(e -> {
            ScreenManager.getInstance().refreshScreen(ScreenManager.GAME);
            ScreenManager.getInstance().showScreen(ScreenManager.GAME);
        });

        restart.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "restart");

        restart.getActionMap().put("restart", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restart.doClick();
            }
        });

        JButton exit = new Button("Exit");
        exit.setAlignmentX(CENTER_ALIGNMENT);
        exit.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAP_SELECT));

        Dimension buttonDimension = new Dimension(140, 40);
        restart.setPreferredSize(buttonDimension);
        restart.setMaximumSize(buttonDimension);
        exit.setPreferredSize(buttonDimension);
        exit.setMaximumSize(buttonDimension);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setAlignmentX(CENTER_ALIGNMENT);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(ColorPalette.RED);
        
        buttonPanel.add(restart);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(exit);

        this.add(Box.createVerticalGlue());
        this.add(gameOver);
        this.add(Box.createVerticalStrut(20));
        this.add(buttonPanel);
        this.add(Box.createVerticalGlue());
    }
}