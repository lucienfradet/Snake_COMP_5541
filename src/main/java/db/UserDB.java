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
import java.util.ArrayList;

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
  public static void init() {
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
      + "isAdmin BOOLEAN DEFAULT 0,\n"
      + "tombstone BOOLEAN DEFAULT 0\n"
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
      + "FOREIGN KEY(userId) REFERENCES User(userId)\n"
      + ")";

    String createMovesTable =
      "CREATE TABLE Moves ("
      + "gameId INT NOT NULL,\n"
      + "direction VARCHAR(5) CHECK(direction IN ('UP', 'DOWN', 'LEFT', 'RIGHT')),\n"
      + "numMoves INT,\n"
      + "PRIMARY KEY(gameId, direction),\n"
      + "FOREIGN KEY(gameId) REFERENCES Game(gameId)\n"
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

  public static UserData login(String username, String password) {
    return null;
  }

  public static boolean newUser(String username, String password) throws Exception {
    if (!UserDB.isUniqueUsername(username))
      throw new Exception("Username is already taken.");

    String sql = "INSERT INTO User (username, password) VALUES (?, ?)";

    try {
      String hashPassword = hashPassword(password);
      Connection conn = DriverManager.getConnection(url);
      PreparedStatement pstmt = conn.prepareStatement(sql);
      pstmt.setString(1, username);
      pstmt.setString(2, hashPassword);

      int rowsInserted = pstmt.executeUpdate();
      System.out.println("User inserted successfully. Rows affected: " + rowsInserted);
      return true;
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Error hashing password: " + e.getMessage());
      throw new Exception("Hash error occured. Could not create account.");
    } catch (SQLException e) {
      System.err.println("Error inserting user: " + e.getMessage());
      throw new Exception("Error occured. Could not create account.");
    }
  }

  public static boolean updatePassword(String username, String newPassword) {
    return false;
  }

  public static boolean updateUsername(String username) {
    return false;
  }

  public static boolean isUniqueUsername(String username) {
    return false;
  }

  public static UserData[] getUserData(int id) {
    return null;
  }

  public static UserData[] getAllUserData() {
    return null;
  }

  public static boolean deleteAccount(int id) {
    return false;
  }

  public static boolean saveGame(UserData user) {
    return false;
  }

  /**
   * Example: Insert a new user into the database.
   * Demonstrates a WRITE operation using PreparedStatement.
   */
  public static void insertUser(String username, boolean admin, int score, int snakeLength) {
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

  /**
   * Example: Query all users from the database.
   * Demonstrates a READ operation using ResultSet.
   */
  public static ArrayList<UserData> getAllUsers() {
    ArrayList<UserData> users = new ArrayList<>();
    String sql = "SELECT * FROM users";

    try (Connection conn = DriverManager.getConnection(url);
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(sql)) {

      while (rs.next()) {
        UserData user = new UserData(
            rs.getInt("id"),
            rs.getString("username"),
            rs.getBoolean("admin")
            );
        users.add(user);
      }
    } catch (SQLException e) {
      System.err.println("Error querying users: " + e.getMessage());
    }
    return users;
  }

  /**
   * Example: Query a specific user by username.
   * Demonstrates a READ operation with a WHERE clause.
   */
  public static UserData getUserByUsername(String username) {
    String sql = "SELECT * FROM users WHERE username = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setString(1, username);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          UserData user = new UserData(
              rs.getInt("id"),
              rs.getString("username"),
              rs.getBoolean("admin")
              );
        }
      }
    } catch (SQLException e) {
      System.err.println("Error querying user: " + e.getMessage());
    }
    return null;
  }

  /**
   * Example: Update a user's score.
   * Demonstrates an UPDATE operation.
   */
  public static void updateUserScore(int userId, int newScore) {
    String sql = "UPDATE users SET score = ? WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, newScore);
      pstmt.setInt(2, userId);

      int rowsUpdated = pstmt.executeUpdate();
      System.out.println("User score updated. Rows affected: " + rowsUpdated);
    } catch (SQLException e) {
      System.err.println("Error updating user score: " + e.getMessage());
    }
  }

  /**
   * Example: Delete a user from the database.
   * Demonstrates a DELETE operation.
   */
  public static void deleteUser(int userId) {
    String sql = "DELETE FROM users WHERE id = ?";

    try (Connection conn = DriverManager.getConnection(url);
        PreparedStatement pstmt = conn.prepareStatement(sql)) {
      pstmt.setInt(1, userId);

      int rowsDeleted = pstmt.executeUpdate();
      System.out.println("User deleted. Rows affected: " + rowsDeleted);
    } catch (SQLException e) {
      System.err.println("Error deleting user: " + e.getMessage());
    }
  }
}
