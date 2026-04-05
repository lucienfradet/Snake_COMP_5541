package db;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import enums.Difficulty;
import enums.Direction;

/**
 * UserDB handles all database operations for the Snake game.
 * 
 * Connection and MetaData:
 * 
 * Connection: active connection to the database.
 *   - Execute SQL queries and updates
 *   - Create statements and prepared statements
 *   - Control transaction management
 *   - Close the connection when done
 *   NOTE:  we create on connection per operation to avoid thread conflicts on the
 *          same one!
 * 
 * DatabaseMetaData: information about the database itself
 *   - Driver name and version
 *   - Table names and structure
 *   - etc.
 */
public class UserDB {
  static final String url = "jdbc:sqlite:data/db/snake_game.db";
  static DatabaseMetaData meta;

  /**
   * Creates the database and initializes the schema with default tables.
   * This method establishes a connection and creates tables if they don't exist.
   */
  public static void init() throws Exception {
    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");

        // Create the schema (tables)
        createSchema(conn);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw new Exception("Error: Can't communicate with the database.");
    }
  }

  /**
   * Creates the database schema with the users and game_history tables.
   * This method is called during database initialization.
   *
   * NOTE:  SQLite don't care about VARCHAR(Limit) but we include it to stay
   *        consistant with database schema intent.
   */
  private static void createSchema(Connection conn) {
    String createUsersTable = 
      "CREATE TABLE User (\n"
      + "userId INT PRIMARY KEY AUTO_INCREMENT,\n"
      + "username VARCHAR(30) NOT NULL CHECK (char_length(username) >= 5),\n"
      + "password VARCHAR(64),\n"
      + "isAdmin BOOLEAN DEFAULT FALSE,\n"
      + "tombstone BOOLEAN DEFAULT FALSE\n"
      + ")";

    String createGameTable = 
      "CREATE TABLE Game (\n"
      + "userId INT NOT NULL,\n"
      + "gameId INT PRIMARY KEY AUTO_INCREMENT,\n"
      + "score INT DEFAULT 0,\n"
      + "snakeLength INT DEFAULT 3,\n"
      + "time LONG,\n"
      + "difficulty VARCHAR(6)  NOT NULL CHECK(difficulty IN ('EASY', 'NORMAL', 'HARD')),\n"
      + "maze INT,\n"
      + "FOREIGN KEY(userId) REFERENCES User(userId) ON CASCADE DELETE\n"
      + ")";

    String createMovesTable =
      "CREATE TABLE Moves ("
      + "gameId INT NOT NULL,\n"
      + "direction VARCHAR(5) CHECK(direction IN ('UP', 'DOWN', 'LEFT', 'RIGHT')),\n"
      + "numMoves INT,\n"
      + "PRIMARY KEY(gameId, direction),\n"
      + "FOREIGN KEY(gameId) REFERENCES Game(gameId) ON CASCADE DELETE\n"
      + ")";

    try (Statement stmt = conn.createStatement()) {
      stmt.execute(createUsersTable);
      stmt.execute(createGameTable);
      stmt.execute(createMovesTable);
      System.out.println("Schema created successfully.");
    } catch (SQLException e) {
      System.err.println("Error creating schema: " + e.getMessage());
    }
  }

  public static UserData login(String username, String password) throws Exception {
    String sql = "SELECT userId, username, isAdmin FROM User "
      + "WHERE username = ? AND password = ? AND tombstone = FALSE;";

    // NOTE:  try (...) {...} automatically closes connections on block exit!
    //        As opposed to regular try {...} catch {...}
    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      String hashPassword = hashPassword(password);
      pstmt.setString(1, username);
      pstmt.setString(2, hashPassword);
      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          return new UserData(
              rs.getInt("userId"),
              rs.getString("username"),
              rs.getBoolean("isAdmin")
              );
        } else {
          throw new Exception("Invalid username or password.");
        }
      }
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occurred.");
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      throw new Exception("Database connection error.");
    }
  }

  public static void newUser(String username, String password) throws Exception {
    if (!UserDB.isUniqueUsername(username))
      throw new Exception("Username is already taken.");

    String sql = "INSERT INTO User (username, password) VALUES (?, ?)";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      String hashPassword = hashPassword(password);

      pstmt.setString(1, username);
      pstmt.setString(2, hashPassword);
      int rowsInserted = pstmt.executeUpdate();
      System.out.println("User inserted successfully. Rows affected: " + rowsInserted);

    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occured. Could not create account.");
    } catch (SQLException e) {
      System.err.println("Error inserting user: " + e.getMessage());
      throw new Exception("Error occured. Could not create account.");
    }
  }

  public static void updatePassword(int id, String newPassword) throws Exception {
    String sql = "UPDATE User "
                  + "SET password = ? "
                  + "WHERE userId = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      String hashPassword = hashPassword(newPassword);
      pstmt.setString(1, hashPassword);
      pstmt.setInt(2, id);

      int rowsUpdated = pstmt.executeUpdate();

        if (rowsUpdated > 0) {
          System.out.println("password updated successfully.");
        } else {
          throw new Exception("Error: Password change failed. User not found.");
      }
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occurred.");
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      throw new Exception("Database connection error.");
    }
  }

  public static void updateUsername(int id, String username) throws Exception {
    if (!UserDB.isUniqueUsername(username))
      throw new Exception("Username is already taken.");

    String sql = "UPDATE User "
                  + "SET username = ? "
                  + "WHERE userId = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, username);
      pstmt.setInt(2, id);

      int rowsUpdated = pstmt.executeUpdate();

        if (rowsUpdated > 0) {
          System.out.println("Password updated successfully.");
        } else {
          throw new Exception("Error: Username change failed. User not found.");
      }
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occurred.");
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      throw new Exception("Database connection error.");
    }
  }

  public static boolean isUniqueUsername(String username) throws Exception {
    String sql = "SELECT username FROM User WHERE username = ? AND tombstone = FALSE;";
    try (
        Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)
    ) {
      pstmt.setString(1, username);
      try (ResultSet rs = pstmt.executeQuery()) {
        return !rs.next(); // false if a row exists, true if empty
      }
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      throw new Exception("Database connection error.");
    }
  }

  /**
   * Return user's past game.
   * use getUserData(user.id, false) to return only logged in user data
   * use getUserData(user.id, user.isAdmin) (if the user is admin) to return all stats of all users
   */
  public static UserData[] getUserData(int id, boolean getsAllStats) throws Exception {
    String sql;
    if (getsAllStats) {
    sql = ""
    + "SELECT username, "
           + "DENSE_RANK() OVER (PARTITION BY u.userId ORDER BY g.gameId) AS ordered_gameId, "
           + "score, snakeLength, time, difficulty, maze, "
           + "SUM(numMoves) AS totalMoves "
       + "FROM User u "
       + "INNER JOIN Game g ON g.userId = u.userId "
       + "INNER JOIN Moves m ON m.gameId = g.gameId "
       + "GROUP BY u.userId, g.gameId "
       + "ORDER BY u.userId, ordered_gameId";
    } else {
      sql = ""
      + "SELECT  username, DENSE_RANK() OVER (ORDER BY gameId) AS ordered_gameId, "
              + "score, snakeLength, time, difficulty, maze, "
              + "SUM(numMoves) AS totalMoves "
        + "FROM User NATURAL JOIN Game NATURAL JOIN Moves "
        + "WHERE userId = ? "
        + "GROUP BY gameId "
        + "ORDER BY ordered_gameId";
    }

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      if (!getsAllStats) {
        pstmt.setInt(1, id);
      }

      int rowCount = 0;
      try (ResultSet rs = pstmt.executeQuery()) {
        rs.last(); // Move to last row to get the number of rows returned
        rowCount = rs.getRow();
      }

      if (rowCount > 0) {
        try (ResultSet rs = pstmt.executeQuery()) {
          UserData[] users = new UserData[rowCount];
          for (int i = 0; i < users.length; i++) {
            rs.next();
            users[i] = new UserData(
              rs.getString("username"), 
              rs.getInt("totalMoves"),
              rs.getInt("score"), 
              rs.getInt("snakeLength"), 
              rs.getLong("time"), 
              rs.getInt("ordered_gameId"), 
              rs.getInt("maze"), 
              UserDB.convertDiffToEnum(rs.getString("difficulty"))
            );
          }
          return users;
        }
      } else {
        throw new Exception("No past games returned");
      }
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occurred.");
    } catch (SQLException e) {
      System.err.println("Error connecting to database: " + e.getMessage());
      throw new Exception("Database connection error.");
    }
  }

  public static void deleteAccount(int id) throws Exception {
    // Uses Cascade delete to also delete its data.
    String sql =  "DELETE FROM User "
                  + "WHERE userId = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, id);

      int rowsDeleted = pstmt.executeUpdate();
      System.out.println("User deleted. Rows affected: " + rowsDeleted);
    } catch (SQLException e) {
      System.err.println("Error deleting user: " + e.getMessage());
      throw new Exception("Error: Could not delete user.");
    }
  }

  public static void saveGame(UserData user) throws Exception {
    String sqlGame = "INSERT INTO Game(userId, score, snakeLength, time, difficulty, maze) "
      + "VALUES (?, ?, ?, ?, ?, ?)";
    String sqlMove = "INSERT INTO Moves VALUES (LAST_INSERT_ID(), ?, ?)";

    try (Connection conn = DriverManager.getConnection(url)) {
      conn.setAutoCommit(false); // we will do multiple write using the same connetion
      try {
        try (PreparedStatement gameStmt = conn.prepareStatement(sqlGame)) {
          gameStmt.setInt(1, user.getId());
          gameStmt.setInt(2, user.getScore());
          gameStmt.setInt(3, user.getSnakeLength());
          gameStmt.setLong(4, user.getGameTime());
          gameStmt.setString(5, user.getDifficulty().toString());
          gameStmt.setInt(6, user.getMaze());
          gameStmt.executeUpdate();
        }
        
        // count the moveHistory
        int[] directionCount = new int[4];
        for (Direction direction : user.getMoveHistory()) {
          directionCount[direction.ordinal()]++; // gets enum index
        }

        try (PreparedStatement moveStmt = conn.prepareStatement(sqlMove)) {
          for (Direction direction : Direction.values()) {
            moveStmt.setString(1, direction.toString());
            moveStmt.setInt(2, directionCount[direction.ordinal()]);
            moveStmt.addBatch();
          }
          moveStmt.executeBatch();
        }
        conn.commit();
      } catch (SQLException e) {
        conn.rollback();
        System.err.println("Error inserting user: " + e.getMessage());
        throw new Exception("Database Error.");
      }
    }
  }

  /**
   * Example: Insert a new user into the database.
   * Demonstrates a WRITE operation using PreparedStatement.
   */
  public static void insertUser(String username, boolean admin, int score, int snakeLength) throws Exception {
    String sql = "INSERT INTO users (username, admin, score, snake_length) VALUES (?, ?, ?, ?)";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, username);
      pstmt.setBoolean(2, admin);
      pstmt.setInt(3, score);
      pstmt.setInt(4, snakeLength);

      int rowsInserted = pstmt.executeUpdate();
      System.out.println("User inserted successfully. Rows affected: " + rowsInserted);
    } catch (SQLException e) {
      System.err.println("Error inserting user: " + e.getMessage());
    }
  }

  /**
   * Helper function to hash the passwords
   */
  private static String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hash = md.digest(password.getBytes());

    // Convert back to a String
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < hash.length; i++) {
      sb.append(String.format("%02x", hash[i]));
    }
    return sb.toString(); // 64 char hex string
  }

  private static Difficulty convertDiffToEnum(String diffString) {
    switch (diffString) {
      case "EASY":
        return Difficulty.EASY;
      case "NORMAL":
        return Difficulty.NORMAL;
      case "HARD":
        return Difficulty.HARD;
      default:
        return Difficulty.NORMAL;
    }
  }
}
