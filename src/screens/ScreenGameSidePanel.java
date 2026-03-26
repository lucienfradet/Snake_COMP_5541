package screens;

import java.awt.Component;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.KeyListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import UI.Button;
import UI.ColorPalette;
import UI.FontPalette;
import rework.Game;
import rework.InputManager;
import rework.ScreenGame;
import rework.Tuple;

public class ScreenGameSidePanel extends JPanel implements Screen{

    private Game gameThread;

    public ScreenGameSidePanel() {

        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.setBackground(ColorPalette.BLACK);
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.setPreferredSize(new java.awt.Dimension(460, 460));
        this.setFocusable(true);

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBackground(ColorPalette.BLACK);
        topPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        topPanel.setPreferredSize(new java.awt.Dimension(460, 48));
        topPanel.setMaximumSize(new java.awt.Dimension(460, 48));

        JPanel middlePanel = new JPanel();
        middlePanel.setLayout(new BoxLayout(middlePanel, BoxLayout.X_AXIS));
        middlePanel.setBackground(ColorPalette.BLACK);
        middlePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        middlePanel.setPreferredSize(new java.awt.Dimension(460, 24));
        middlePanel.setMaximumSize(new java.awt.Dimension(460, 24));

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.X_AXIS));
        bottomPanel.setBackground(ColorPalette.BLACK);
        bottomPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        JLabel snakeGame = new JLabel("Snake Game");
        snakeGame.setForeground(ColorPalette.WHITE);
        snakeGame.setFont(FontPalette.TITLE);

        Button pause = new Button("Pause");
        pause.setFont(new Font("Yoster Island", Font.BOLD, 17));
        pause.setMargin(new Insets(0, 0, 0, 0));
        pause.setMaximumSize(new java.awt.Dimension(100, 36));
        pause.setPreferredSize(new java.awt.Dimension(100, 36));
        pause.addActionListener(e -> {
            stopGame();
            ScreenManager.getInstance().showScreen(ScreenManager.PAUSE);
        });

        topPanel.add(snakeGame);
        topPanel.add(Box.createHorizontalGlue());
        topPanel.add(pause);

        JLabel difficulty = new JLabel("Difficulty");
        difficulty.setFont(FontPalette.TEXT);
        difficulty.setForeground(ColorPalette.WHITE);

        JLabel currentDifficulty= new JLabel("Med");
        currentDifficulty.setFont(FontPalette.TEXT);
        currentDifficulty.setForeground(ColorPalette.GREEN);

        JLabel maze = new JLabel("Maze");
        maze.setFont(FontPalette.TEXT);
        maze.setForeground(ColorPalette.WHITE);

        JLabel currentMaze = new JLabel("1");
        currentMaze.setFont(FontPalette.TEXT);
        currentMaze.setForeground(ColorPalette.WHITE);

        middlePanel.add(difficulty);
        middlePanel.add(Box.createHorizontalStrut(10));
        middlePanel.add(currentDifficulty);
        middlePanel.add(Box.createHorizontalGlue());
        middlePanel.add(maze);
        middlePanel.add(Box.createHorizontalStrut(10));
        middlePanel.add(currentMaze);
        middlePanel.add(Box.createHorizontalStrut(110));

        ScreenGame game = new ScreenGame(350, ColorPalette.WHITE, ColorPalette.GREEN, ColorPalette.BROWN);
        game.setPreferredSize(new java.awt.Dimension(350, 350));
        game.setMaximumSize(new java.awt.Dimension(350, 350));
        game.setMinimumSize(new java.awt.Dimension(350, 350));

        JPanel gameInfo = new JPanel();
        gameInfo.setLayout(new BoxLayout(gameInfo, BoxLayout.Y_AXIS));
        gameInfo.setBackground(ColorPalette.WHITE);
        gameInfo.setBorder(BorderFactory.createEmptyBorder(12, 10, 12, 10));
        gameInfo.setPreferredSize(new java.awt.Dimension(100, 350));
        gameInfo.setMaximumSize(new java.awt.Dimension(100, 350));
        gameInfo.setMinimumSize(new java.awt.Dimension(100, 350));

        JLabel loggedInAs = new JLabel("Logged");
        loggedInAs.setFont(FontPalette.TEXT);
        loggedInAs.setForeground(ColorPalette.BROWN);
        JLabel currentUser = new JLabel("in as");
        currentUser.setFont(FontPalette.TEXT);
        currentUser.setForeground(ColorPalette.BROWN);
        JLabel currentUserName = new JLabel("Barb");
        currentUserName.setFont(FontPalette.TEXT);
        currentUserName.setForeground(ColorPalette.GREEN);
        JLabel currentUserLast = new JLabel("Tarbox");
        currentUserLast.setFont(FontPalette.TEXT);
        currentUserLast.setForeground(ColorPalette.GREEN);

        JLabel numOfMoves = new JLabel("Nb.");
        numOfMoves.setFont(FontPalette.TEXT);
        numOfMoves.setForeground(ColorPalette.BROWN);
        JLabel moveTracker = new JLabel("Moves");
        moveTracker.setFont(FontPalette.TEXT);
        moveTracker.setForeground(ColorPalette.BROWN);
        JLabel moveValue = new JLabel("26");
        moveValue.setFont(FontPalette.TEXT);
        moveValue.setForeground(ColorPalette.GREEN);

        JLabel snakeLength = new JLabel("Snake");
        snakeLength.setFont(FontPalette.TEXT);
        snakeLength.setForeground(ColorPalette.BROWN);
        JLabel lengthTracker = new JLabel("Length");
        lengthTracker.setFont(FontPalette.TEXT);
        lengthTracker.setForeground(ColorPalette.BROWN);
        JLabel lengthValue = new JLabel("4");
        lengthValue.setFont(FontPalette.TEXT);
        lengthValue.setForeground(ColorPalette.GREEN);

        JLabel score = new JLabel("Score");
        score.setFont(FontPalette.TEXT);
        score.setForeground(ColorPalette.BROWN);
        JLabel scoreValue = new JLabel("1");
        scoreValue.setFont(FontPalette.TEXT);
        scoreValue.setForeground(ColorPalette.GREEN);

        JLabel time = new JLabel("Time");
        time.setFont(FontPalette.TEXT);
        time.setForeground(ColorPalette.BROWN);
        JLabel timeTracker = new JLabel("00:22");
        timeTracker.setFont(FontPalette.TEXT);
        timeTracker.setForeground(ColorPalette.GREEN);

        gameInfo.add(loggedInAs);
        gameInfo.add(currentUser);
        gameInfo.add(currentUserName);
        gameInfo.add(currentUserLast);
        gameInfo.add(Box.createVerticalStrut(5));
        gameInfo.add(numOfMoves);
        gameInfo.add(moveTracker);
        gameInfo.add(moveValue);
        gameInfo.add(Box.createVerticalStrut(5));
        gameInfo.add(snakeLength);
        gameInfo.add(lengthTracker);
        gameInfo.add(lengthValue);
        gameInfo.add(Box.createVerticalStrut(5));
        gameInfo.add(score);
        gameInfo.add(scoreValue);
        gameInfo.add(Box.createVerticalStrut(5));
        gameInfo.add(time);
        gameInfo.add(timeTracker);

        bottomPanel.add(game);
        bottomPanel.add(Box.createHorizontalStrut(10));
        bottomPanel.add(gameInfo);

        this.add(topPanel);
        this.add(Box.createVerticalGlue());
        this.add(middlePanel);
        this.add(Box.createVerticalGlue());
        this.add(bottomPanel);

			// initial position of the snake
			Tuple position = new Tuple(10,10);
			gameThread = new Game(position, game, () -> ScreenManager.getInstance().showScreen(ScreenManager.GAME_OVER));

			//Let's start the game now..
			gameThread.start();

		// Links the window to the keyboardlistenner.
		this.addKeyListener((KeyListener) new InputManager());

	    }

    @Override
    public void onShow() {
        javax.swing.SwingUtilities.invokeLater(this::requestFocusInWindow);
    }

    private void stopGame() {
        if (gameThread != null) {
            gameThread.gameActive = false;
        }
    }
}
