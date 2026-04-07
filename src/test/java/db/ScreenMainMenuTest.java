package db;

import org.junit.jupiter.api.*;
import java.nio.file.*;
import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagementIntegrationTest {

    private static final Path DB_PATH = Path.of("data/db/snake_game.db");

    @BeforeEach
    void setUp() throws Exception {
        Files.deleteIfExists(DB_PATH);
        Files.createDirectories(DB_PATH.getParent());
        UserDB.init();
    }

    @AfterEach
    void tearDown() throws Exception {
        Files.deleteIfExists(DB_PATH);
    }

    // ============================================================
    // IC‑03 — LOGIN TESTS
    // ============================================================

    @Test
    void login_validCredentials_returnsCorrectUser() throws Exception {
        UserDB.newUser("TestLogin", "TestPassword");

        UserData user = UserDB.login("TestLogin", "TestPassword");

        assertNotNull(user);
        assertEquals("TestLogin", user.getUsername());
    }

    @Test
    void login_nonexistentUser_throwsError() {
        Exception ex = assertThrows(Exception.class,
                () -> UserDB.login("NoSuchUser", "SomePassword"));

        assertTrue(ex.getMessage().contains("Invalid"));
    }

    @Test
    void login_wrongPassword_throwsError() throws Exception {
        UserDB.newUser("TestLogin", "CorrectPassword");

        Exception ex = assertThrows(Exception.class,
                () -> UserDB.login("TestLogin", "WrongPassword"));

        assertTrue(ex.getMessage().contains("Invalid"));
    }

    // ============================================================
    // IC‑04 — ACCOUNT MANAGEMENT (UPDATE + DELETE)
    // ============================================================

    @Test
    void updatePassword_changesPersistAndOldPasswordFails() throws Exception {
        UserDB.newUser("UserA", "OldPassword");
        UserData user = UserDB.login("UserA", "OldPassword");

        UserDB.updatePassword(user.getId(), "NewPassword123");

        // Old password should fail
        assertThrows(Exception.class,
                () -> UserDB.login("UserA", "OldPassword"));

        // New password should succeed
        UserData updated = UserDB.login("UserA", "NewPassword123");
        assertEquals("UserA", updated.getUsername());
    }

    @Test
    void updateUsername_changesPersist() throws Exception {
        UserDB.newUser("OldName", "Password123");
        UserData user = UserDB.login("OldName", "Password123");

        UserDB.updateUsername(user.getId(), "NewName");

        // Old username should fail
        assertThrows(Exception.class,
                () -> UserDB.login("OldName", "Password123"));

        // New username should succeed
        UserData updated = UserDB.login("NewName", "Password123");
        assertEquals("NewName", updated.getUsername());
    }

    @Test
    void deleteAccount_removesUserFromDatabase() throws Exception {
        UserDB.newUser("DeleteMe", "Password123");
        UserData user = UserDB.login("DeleteMe", "Password123");

        UserDB.deleteAccount(user.getId());

        // Should not be able to log in anymore
        assertThrows(Exception.class,
                () -> UserDB.login("DeleteMe", "Password123"));
    }

    @Test
    void updateUsername_duplicateName_rejected() throws Exception {
        UserDB.newUser("User1", "Password123");
        UserDB.newUser("User2", "Password123");

        UserData user1 = UserDB.login("User1", "Password123");

        Exception ex = assertThrows(Exception.class,
                () -> UserDB.updateUsername(user1.getId(), "User2"));

        assertTrue(ex.getMessage().contains("already"));
    }
}
