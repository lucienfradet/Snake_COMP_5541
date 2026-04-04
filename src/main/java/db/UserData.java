package db;

import java.util.ArrayList;

import enums.Difficulty;
import enums.Direction;

public class UserData {
  int id;
  String username;
  boolean admin;
  ArrayList<Direction> moveHistory;
  int score;
  int snakeLength;
  long gameTime;
  int pastGameId;
  int maze;
  Difficulty difficulty;

  public UserData(int id, String username, boolean admin) {
    this.id = id;
    this.username = username;
    this.admin = admin;
  }

  public UserData(
      int id, 
      String username, 
      boolean admin, 
      ArrayList<Direction> moveHistory, 
      int score, int snakeLength, 
      long gameTime, 
      int pastGameId, 
      int maze, 
      Difficulty difficulty
  ) {
    this.id = id;
    this.username = username;
    this.admin = admin;
    this.moveHistory = moveHistory;
    this.score = score;
    this.snakeLength = snakeLength;
    this.gameTime = gameTime;
    this.pastGameId = pastGameId;
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

  public int getScore() {
    return score;
  }

  public int getSnakeLength() {
    return snakeLength;
  }

  public long getGameTime() {
    return gameTime;
  }

  public int getPastGameId() {
    return pastGameId;
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

  public void setScore(int score) {
    this.score = score;
  }

  public void setSnakeLength(int snakeLength) {
    this.snakeLength = snakeLength;
  }

  public void setGameTime(long gameTime) {
    this.gameTime = gameTime;
  }

  public void setPastGameId(int pastGameId) {
    this.pastGameId = pastGameId;
  }

  public void setMaze(int maze) {
    this.maze = maze;
  }

  public void setDifficulty(Difficulty difficulty) {
    this.difficulty = difficulty;
  }

  // Additional methods
  public void incrementScore(int amount) {
    this.score += amount;
  }

  public void addMove(Direction direction) {
    if (moveHistory == null) {
      moveHistory = new ArrayList<>();
    }
    moveHistory.add(direction);
  }

  public static void clearGameData(UserData user) {
    user.score = 0;
    // user.snakeLength = Game.START_SNAKE_LENGTH;
  }
}
