package db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UserDB {

  public static void createDatabase() {
    String url = "jdbc:sqlite:data/db/snake_game.db";

    try (Connection conn = DriverManager.getConnection(url)) {
      if (conn != null) {
        DatabaseMetaData meta = conn.getMetaData();
        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("A new database has been created.");
      }
    } catch (SQLException e) {
      System.err.println(e.getMessage());
    }
  }
}
