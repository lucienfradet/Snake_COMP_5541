package db;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class UserDBIntegrationTest {

    // Must match the URL in UserDB exactly (minus the jdbc:sqlite: prefix)
    private static final Path DB_PATH = Path.of("data/db/snake_game.db");

    @BeforeEach
    void setUp() throws Exception {
        // Wipe any leftover DB so every test starts completely fresh
        Files.deleteIfExists(DB_PATH);
        Files.createDirectories(DB_PATH.getParent());
        UserDB.init(); // Creates the schema
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(DB_PATH);
    }

    // ITC-01 step 1: init() works and DB file exists
    @Test
    void init_createsDbFile_noExceptionThrown() {
        assertTrue(Files.exists(DB_PATH),
            "DB file should exist after init()");
    }

    // ITC-01 steps 2+3: data written by newUser() is readable by login()
    @Test
    void newUser_thenLogin_sameUserReturnedFromRealDB() throws Exception {
        // Write
        UserDB.newUser("barbtarbox", "password123");

        // Read
        UserData user = UserDB.login("barbtarbox", "password123");

        assertNotNull(user);
        assertEquals("barbtarbox", user.getUsername());
        assertFalse(user.isAdmin()); // Default is false
        assertTrue(user.getId() > 0, "Auto-incremented ID should be > 0");
    }

    // ITC-01 error handling: wrong password after a real insert
    @Test
    void login_wrongPasswordAfterRealInsert_throwsException() throws Exception {
        UserDB.newUser("barbtarbox", "password123");

        assertThrows(Exception.class,
            () -> UserDB.login("barbtarbox", "wrongPassword"));
    }

    // ITC-01 error handling: duplicate username is rejected
    @Test
    void newUser_duplicateUsername_throwsException() throws Exception {
        UserDB.newUser("barbtarbox", "password123");

        assertThrows(Exception.class,
            () -> UserDB.newUser("barbtarbox", "differentPassword"));
    }

    // Bonus: updatePassword change is reflected on next login
    @Test
    void updatePassword_changesArePersistedToDb() throws Exception {
        UserDB.newUser("barbtarbox", "password123");
        UserData user = UserDB.login("barbtarbox", "password123");

        UserDB.updatePassword(user.getId(), "newPassword!");

        // Old password should no longer work
        assertThrows(Exception.class,
            () -> UserDB.login("barbtarbox", "password123"));

        // New password should work
        UserData updatedUser = UserDB.login("barbtarbox", "newPassword!");
        assertNotNull(updatedUser);
        assertEquals("barbtarbox", updatedUser.getUsername());
    }
}
