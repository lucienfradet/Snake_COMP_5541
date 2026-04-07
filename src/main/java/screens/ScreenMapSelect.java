package screens;

import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import app.Main;
import enums.Difficulty;
import screens.UI.Button;
import screens.UI.ColorPalette;
import screens.UI.FontPalette;

public class ScreenMapSelect extends JPanel implements Screen {

    private final JPanel loginInfoPanel;

    public ScreenMapSelect() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(ColorPalette.BLACK);

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.Y_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);

        Button back = new Button("Back");
        back.setPreferredSize(new Dimension(140, 40));
        back.setMaximumSize(new Dimension(140, 40));
        back.addActionListener(e -> ScreenManager.getInstance().showScreen(ScreenManager.MAIN_MENU));

        topPanel.add(back);
        topPanel.add(Box.createHorizontalGlue());

        Button easy = new Button("Easy");
        Button normal = new Button("Medium");
        Button hard = new Button("Hard");
        Button[] difficultyButtons = { easy, normal, hard };
        for (Button button : difficultyButtons) {
            button.setPreferredSize(new Dimension(130, 40));
            button.setMaximumSize(new Dimension(130, 40));
        }
        easy.addActionListener(e -> Main.loginUser.setDifficulty(Difficulty.EASY));
        normal.addActionListener(e -> Main.loginUser.setDifficulty(Difficulty.NORMAL));
        hard.addActionListener(e -> Main.loginUser.setDifficulty(Difficulty.HARD));

        Button zero = new Button("0");
        Button one = new Button("1");
        Button two = new Button("2");
        Button three = new Button("3");
        Button[] mazeButtons = { zero, one, two, three };
        for (Button button : mazeButtons) {
            button.setPreferredSize(new Dimension(70, 40));
            button.setMaximumSize(new Dimension(70, 40));
        }
        one.addActionListener(e -> Main.loginUser.setMaze(1));
        two.addActionListener(e -> Main.loginUser.setMaze(2));
        three.addActionListener(e -> Main.loginUser.setMaze(3));
        zero.addActionListener(e -> Main.loginUser.setMaze(0));


        Button play = new Button("Play");
        play.setPreferredSize(new Dimension(140, 40));
        play.setMaximumSize(new Dimension(140, 40));
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

        JPanel difficultyRow = new JPanel();
        difficultyRow.setLayout(new BoxLayout(difficultyRow, BoxLayout.X_AXIS));
        difficultyRow.setAlignmentX(CENTER_ALIGNMENT);
        difficultyRow.setBackground(ColorPalette.BLACK);
        difficultyRow.add(easy);
        difficultyRow.add(Box.createHorizontalStrut(10));
        difficultyRow.add(normal);
        difficultyRow.add(Box.createHorizontalStrut(10));
        difficultyRow.add(hard);

        JLabel maze = new JLabel("Maze");
        maze.setFont(FontPalette.TEXT);
        maze.setForeground(ColorPalette.WHITE);
        maze.setAlignmentX(CENTER_ALIGNMENT);

        JPanel mazeRow = new JPanel();
        mazeRow.setLayout(new BoxLayout(mazeRow, BoxLayout.X_AXIS));
        mazeRow.setAlignmentX(CENTER_ALIGNMENT);
        mazeRow.setBackground(ColorPalette.BLACK);
        mazeRow.add(zero);
        mazeRow.add(Box.createHorizontalStrut(10));
        mazeRow.add(one);
        mazeRow.add(Box.createHorizontalStrut(10));
        mazeRow.add(two);
        mazeRow.add(Box.createHorizontalStrut(10));
        mazeRow.add(three);

        JPanel playPanel = new JPanel();
        playPanel.setLayout(new BoxLayout(playPanel, BoxLayout.X_AXIS));
        playPanel.setAlignmentX(CENTER_ALIGNMENT);
        playPanel.setBackground(ColorPalette.BLACK);
        playPanel.add(Box.createHorizontalStrut(10));
        playPanel.add(play);

        loginInfoPanel = ScreenManager.displayUserInfo(Main.loginUser.getUsername());
        loginInfoPanel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.add(loginInfoPanel);
        bottomPanel.add(Box.createHorizontalGlue());

        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(gameSettings);
        middlePanel.add(Box.createVerticalGlue());
        middlePanel.add(difficulty);
        middlePanel.add(difficultyRow);
        middlePanel.add(maze);
        middlePanel.add(mazeRow);
        middlePanel.add(playPanel);
        middlePanel.add(Box.createVerticalGlue());

        this.add(topPanel);
        this.add(middlePanel);
        this.add(bottomPanel);

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

        easy.putClientProperty("selected", true);
        easy.repaint();
        zero.putClientProperty("selected", true);
        zero.repaint();
    }

    @Override
    public void onShow() {
        ScreenManager.refreshUserInfoPanel(loginInfoPanel);
    }
}
