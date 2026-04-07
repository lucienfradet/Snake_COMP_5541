package db;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import enums.*;

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
    // Create directories if they don't exist
    Path path = Paths.get("data/db");
    Files.createDirectories(path);

    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");

        // Create the schema (tables)
        createSchema(conn);
        // add admin user
        addDefaultAdminUser(conn);
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
      throw new Exception("Error: Can't communicate with the database.");
    } catch (Exception e) {
      throw new Exception("Error: Can't access database directory.");
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
      "CREATE TABLE IF NOT EXISTS User (\n"
      + "userId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
      + "username VARCHAR(30) NOT NULL CHECK (length(username) >= 5),\n"
      + "password VARCHAR(64),\n"
      + "isAdmin BOOLEAN DEFAULT FALSE\n"
      + ")";

    String createGameTable = 
      "CREATE TABLE IF NOT EXISTS Game (\n"
      + "userId INTEGER NOT NULL,\n"
      + "gameId INTEGER PRIMARY KEY AUTOINCREMENT,\n"
      + "score INTEGER DEFAULT 0,\n"
      + "snakeLength INTEGER DEFAULT 3,\n"
      + "time INTEGER,\n"
      + "difficulty VARCHAR(6)  NOT NULL CHECK(difficulty IN ('EASY', 'NORMAL', 'HARD')),\n"
      + "maze INTEGER,\n"
      + "FOREIGN KEY(userId) REFERENCES User(userId) ON DELETE CASCADE\n"
      + ")";

    String createMovesTable =
      "CREATE TABLE IF NOT EXISTS Moves ("
      + "gameId INTEGER NOT NULL,\n"
      + "direction VARCHAR(5) CHECK(direction IN ('UP', 'DOWN', 'LEFT', 'RIGHT')),\n"
      + "numMoves INTEGER,\n"
      + "PRIMARY KEY(gameId, direction),\n"
      + "FOREIGN KEY(gameId) REFERENCES Game(gameId) ON DELETE CASCADE\n"
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
      + "WHERE username = ? AND password = ?;";

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
    String sql = "SELECT username FROM User WHERE username = ?";
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
      sql = "SELECT username, "
        + "DENSE_RANK() OVER (PARTITION BY u.userId ORDER BY g.gameId) AS ordered_gameId, "
        + "score, snakeLength, time, difficulty, maze, "
        + "SUM(numMoves) AS totalMoves "
        + "FROM User u "
        + "INNER JOIN Game g ON g.userId = u.userId "
        + "INNER JOIN Moves m ON m.gameId = g.gameId "
        + "GROUP BY u.userId, g.gameId "
        + "ORDER BY u.userId, 2"; // 2 = ordered_gameId position
    } else {
      sql = "SELECT username, "
        + "DENSE_RANK() OVER (ORDER BY g.gameId) AS ordered_gameId, "
        + "score, snakeLength, time, difficulty, maze, "
        + "SUM(numMoves) AS totalMoves "
        + "FROM User u "
        + "INNER JOIN Game g ON g.userId = u.userId "
        + "INNER JOIN Moves m ON m.gameId = g.gameId "
        + "WHERE u.userId = ? "
        + "GROUP BY g.gameId "
        + "ORDER BY 2"; // 2 = ordered_gameId position
    }

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {

      if (!getsAllStats) {
        pstmt.setInt(1, id);
      }

      ArrayList<UserData> userList = new ArrayList<>();
      try (ResultSet rs = pstmt.executeQuery()) {
        while (rs.next()) {
          UserData user = new UserData(
              rs.getString("username"),
              rs.getInt("totalMoves"),
              rs.getInt("score"),
              rs.getInt("snakeLength"),
              rs.getLong("time"),
              rs.getInt("ordered_gameId"),
              rs.getInt("maze"),
              UserDB.convertDiffToEnum(rs.getString("difficulty"))
              );
          userList.add(user);
        }
      }
      return userList.toArray(new UserData[0]);

    } catch (SQLException e) {
      System.err.println("getUserData SQL error: " + e.getMessage());
      System.err.println("SQLState: " + e.getSQLState());
      System.err.println("ErrorCode: " + e.getErrorCode());
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

      if (rowsDeleted == 0) 
        throw new Exception("Error: User not found.");

      System.out.println("User deleted. Rows affected: " + rowsDeleted);
    } catch (SQLException e) {
      System.err.println("Error deleting user: " + e.getMessage());
      throw new Exception("Error: Could not delete user.");
    }
  }

  public static void saveGame(UserData user) throws Exception {
    String sqlGame = "INSERT INTO Game(userId, score, snakeLength, time, difficulty, maze) "
      + "VALUES (?, ?, ?, ?, ?, ?)";
    String sqlMove = "INSERT INTO Moves VALUES (last_insert_rowid(), ?, ?)";

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
  
  public static void addDefaultAdminUser(Connection conn) throws Exception {
    if (!UserDB.isUniqueUsername("admin")) {
      System.out.println("Admin user already in the database.");
      return;
    }

    String sql = "INSERT INTO User (username, password, isAdmin) VALUES (?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
      String hashPassword = hashPassword("password");

      pstmt.setString(1, "admin");
      pstmt.setString(2, hashPassword);
      pstmt.setBoolean(3, true);
      int rowsInserted = pstmt.executeUpdate();
      System.out.println("Admin user inserted successfully. Rows affected: " + rowsInserted);

    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occured. Could not create account.");
    } catch (SQLException e) {
      System.err.println("Error inserting user: " + e.getMessage());
      throw new Exception("Error occured. Could not create account.");
    }
  }
}
