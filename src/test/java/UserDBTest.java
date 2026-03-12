import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserDBTest {

    private UserDB userDB;
    private SQLiteDatabase mockDB; // your DB wrapper

    @BeforeEach
    void setUp() {
        mockDB = mock(SQLiteDatabase.class);
        userDB = new UserDB(mockDB); // inject mock DB
    }

    // ---- INTERFACE ----

    @Test
    void logIn_validCredentials_returnsUserData() {
        when(mockDB.query("alice")).thenReturn(new DBRecord(1, "alice", "hashedPassword", false));

        UserData result = userDB.logIn("alice", "correctPassword");

        assertNotNull(result);
        assertEquals("alice", result.getUsername());
        assertEquals(1, result.getID());
    }

    // ---- LOCAL DATA STRUCTURES ----

    @Test
    void logIn_validCredentials_defaultFieldsCorrect() {
        when(mockDB.query("alice")).thenReturn(new DBRecord(1, "alice", "hashedPassword", false));

        UserData result = userDB.logIn("alice", "correctPassword");

        assertFalse(result.isAdmin());
        assertEquals(0, result.getScore());
        assertNotNull(result.getMoveHistory());
        assertFalse(result.getMoveHistory().iterator().hasNext()); // empty
    }

    // ---- BOUNDARY ----

    @Test
    void logIn_emptyCredentials_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userDB.logIn("", "");
        });
    }

    @Test
    void logIn_maxLengthUsername_succeeds() {
        String maxUsername = "a".repeat(50); // assuming 50 is max
        when(mockDB.query(maxUsername)).thenReturn(new DBRecord(2, maxUsername, "hashedPassword", false));

        assertDoesNotThrow(() -> userDB.logIn(maxUsername, "correctPassword"));
    }

    @Test
    void logIn_overMaxLengthUsername_throwsException() {
        String tooLong = "a".repeat(51);
        assertThrows(IllegalArgumentException.class, () -> {
            userDB.logIn(tooLong, "password");
        });
    }

    // ---- INDEPENDENT PATHS ----

    @Test
    void logIn_wrongPassword_returnsNull() {
        when(mockDB.query("alice")).thenReturn(new DBRecord(1, "alice", "hashedPassword", false));

        UserData result = userDB.logIn("alice", "wrongPassword");

        assertNull(result);
    }

    @Test
    void logIn_nonExistentUser_throwsException() {
        when(mockDB.query("ghost")).thenThrow(new UserNotFoundException("Invalid User"));

        assertThrows(UserNotFoundException.class, () -> {
            userDB.logIn("ghost", "anyPassword");
        });
    }

    // ---- ERROR HANDLING ----

    @Test
    void logIn_nullInputs_throwsIllegalArgument_notNullPointer() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userDB.logIn(null, null);
        });
        // make sure it's not a NullPointerException slipping through
        assertFalse(ex instanceof NullPointerException);
    }

    @Test
    void logIn_dbUnavailable_throwsConnectionException() {
        when(mockDB.query(any())).thenThrow(new ConnectionException("DB unavailable"));

        assertThrows(ConnectionException.class, () -> {
            userDB.logIn("alice", "correctPassword");
        });
    }
}
