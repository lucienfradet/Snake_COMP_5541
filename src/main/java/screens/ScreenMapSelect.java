package screens;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenMapSelect extends JPanel implements Screen {

    public ScreenMapSelect() {
        super(new BorderLayout());

        java.awt.Color defaultBackground = ColorPalette.BLACK;
        Dimension difficultyButtonSize = new Dimension(130, 40);
        Dimension mazeButtonSize = new Dimension(70, 40);

        setBackground(defaultBackground);

        Button easy = new Button("Easy");
        Button normal = new Button("Normal");
        Button hard = new Button("Hard");
        Button[] difficultyButtons = { easy, normal, hard };
        for (Button button : difficultyButtons) {
            button.setPreferredSize(difficultyButtonSize);
            button.setMaximumSize(difficultyButtonSize);
        }

        Button zero = new Button("0");
        Button one = new Button("1");
        Button two = new Button("2");
        Button three = new Button("3");
        Button[] mazeButtons = { zero, one, two, three };
        for (Button button : mazeButtons) {
            button.setPreferredSize(mazeButtonSize);
            button.setMaximumSize(mazeButtonSize);
        }

        Button back = new Button("Back");
        Button play = new Button("Play");
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));
        play.addActionListener(e -> {
            ScreenManager.getInstance().refreshScreen(ScreenManager.GAME);
            ScreenManager.getInstance().showScreen(ScreenManager.GAME);
        });

        JLabel gameSettings = new JLabel("Game Settings");
        gameSettings.setFont(FontPalette.TITLE);
        gameSettings.setForeground(ColorPalette.WHITE);
        gameSettings.setAlignmentX(CENTER_ALIGNMENT);

        JLabel difficulty = new JLabel("Difficulty");
        difficulty.setFont(FontPalette.TEXT);
        difficulty.setForeground(ColorPalette.WHITE);
        difficulty.setAlignmentX(CENTER_ALIGNMENT);

        JLabel maze = new JLabel("Maze");
        maze.setFont(FontPalette.TEXT);
        maze.setForeground(ColorPalette.WHITE);
        maze.setAlignmentX(CENTER_ALIGNMENT);

        JLabel loggedInAs = new JLabel("Logged in as");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.GREEN);
        loggedInAs.setAlignmentX(CENTER_ALIGNMENT);

        JLabel currentUser = new JLabel("Bard Tarbox");
        currentUser.setFont(FontPalette.TEXT);
        currentUser.setForeground(ColorPalette.WHITE);
        currentUser.setAlignmentX(CENTER_ALIGNMENT);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(defaultBackground);
        mainPanel.setAlignmentX(CENTER_ALIGNMENT);

        JPanel difficultyRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        difficultyRow.setBackground(defaultBackground);
        difficultyRow.setAlignmentX(CENTER_ALIGNMENT);
        for (Button button : difficultyButtons) {
            difficultyRow.add(button);
        }

        JPanel mazeRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        mazeRow.setBackground(defaultBackground);
        mazeRow.setAlignmentX(CENTER_ALIGNMENT);
        for (Button button : mazeButtons) {
            mazeRow.add(button);
        }

        JPanel actionRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        actionRow.setBackground(defaultBackground);
        actionRow.setAlignmentX(CENTER_ALIGNMENT);
        actionRow.add(back);
        actionRow.add(play);

        Dimension buttonDimension = new Dimension(140, 40);
        back.setPreferredSize(buttonDimension);
        back.setMaximumSize(buttonDimension);
        play.setPreferredSize(buttonDimension);
        play.setMaximumSize(buttonDimension);

        JPanel loginInfoPanel = new JPanel();
        loginInfoPanel.setLayout(new BoxLayout(loginInfoPanel, BoxLayout.Y_AXIS));
        loginInfoPanel.setBackground(defaultBackground);
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        loginInfoPanel.add(loggedInAs);
        loginInfoPanel.add(currentUser);
        loggedInAs.setAlignmentX(LEFT_ALIGNMENT);
        currentUser.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);
        loginInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        mainPanel.add(Box.createVerticalStrut(70));
        mainPanel.add(gameSettings);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(difficulty);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(difficultyRow);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(maze);
        mainPanel.add(Box.createVerticalStrut(5));
        mainPanel.add(mazeRow);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(actionRow);
        mainPanel.add(Box.createVerticalGlue());

        add(mainPanel, BorderLayout.CENTER);
        add(loginInfoPanel, BorderLayout.SOUTH);

        for (Button button : difficultyButtons) {
            button.addActionListener(e -> {
                for (Button current : difficultyButtons) {
                    current.putClientProperty("selected", current == button);
                    current.repaint();
                }
            });
        }

        for (Button button : mazeButtons) {
            button.addActionListener(e -> {
                for (Button current : mazeButtons) {
                    current.putClientProperty("selected", current == button);
                    current.repaint();
                }
            });
        }
    }

    @Override
    public void onShow() {
        
    }
}
