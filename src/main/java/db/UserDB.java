package db;

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
  public static void createDatabase() {
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
      + "isAdmin BOOLEAN,\n"
      + "tombstone BOOLEAN\n"
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
