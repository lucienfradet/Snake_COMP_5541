package db;

import java.util.ArrayList;

import enums.Difficulty;
import enums.Direction;
import game.Game;

public class UserData {
  private int id;
  private String username;
  private boolean admin;
  private ArrayList<Direction> moveHistory;
  private int totalMoveCount; // Only used when retreiving data
  private int score;
  private int snakeLength;
  private long gameTime;
  private int gameId;
  private int maze;
  private Difficulty difficulty;

  /**
   * Constructor when a user logs in.
   * Use default Difficulty and Maze values
   */
  public UserData(int id, String username, boolean admin) {
    this.id = id;
    this.username = username;
    this.admin = admin;
    this.maze = 0;
    this.difficulty = Difficulty.NORMAL;
    this.snakeLength = Game.START_SNAKE_LENGTH;
  }

  /**
   * Constructor when use to store past games in UserData
   */
  public UserData(
      String username, 
      int totalMoveCount,
      int score, 
      int snakeLength, 
      long gameTime, 
      int pastGameId, 
      int maze, 
      Difficulty difficulty
  ) {
    this.username = username;
    this.totalMoveCount = totalMoveCount;
    this.score = score;
    this.snakeLength = snakeLength;
    this.gameTime = gameTime;
    this.gameId = pastGameId;
    this.maze = maze;
    this.difficulty = difficulty;
  }

  // Getters
  public int getId() {
    return id;
  }

  public String getUsername() {
    return username;
  }

  public boolean isAdmin() {
    return admin;
  }

  public Iterable<Direction> getMoveHistory() {
    return moveHistory;
  }

  public int getTotalMoveCount() {
    return totalMoveCount;
  }

  public int getScore() {
    return score;
  }

  public int getSnakeLength() {
    return snakeLength;
  }

  public long getGameTime() {
    return gameTime;
  }

  public int getGameId() {
    return gameId;
  }

  public int getMaze() {
    return maze;
  }

  public Difficulty getDifficulty() {
    return difficulty;
  }

  // Setters
  public void setId(int id) {
    this.id = id;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public void setAdmin(boolean admin) {
    this.admin = admin;
  }

  public void setMoveHistory(ArrayList<Direction> moveHistory) {
    this.moveHistory = moveHistory;
  }

  public void setTotalMoveCount(int totalMoveCount) {
    this.totalMoveCount = totalMoveCount;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public void setSnakeLength(int snakeLength) {
    this.snakeLength = snakeLength;
  }

  public void setGameTime(long gameTime) {
    this.gameTime = gameTime;
  }

  public void setGameId(int pastGameId) {
    this.gameId = pastGameId;
  }

  public void setMaze(int maze) {
    this.maze = maze;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  // Additional methods
  public void incrementScore() throws RuntimeException {
    if (this.score == Integer.MAX_VALUE)
      throw new RuntimeException("Score has reached maximum value.");
    this.score++;
  }

  public void incrementSnakeLength() {
    this.snakeLength++;
  }

  public void addMove(Direction direction) throws IllegalArgumentException {
    if (direction == null) throw new IllegalArgumentException("Direction cannot be null");
    if (moveHistory == null) moveHistory = new ArrayList<>();
    moveHistory.add(direction);
  }

  public static void clearGameData(UserData user) {
    user.setScore(0);
    user.setSnakeLength(Game.START_SNAKE_LENGTH);
    user.setGameTime(0);
    user.setMoveHistory(null);
  }
}
