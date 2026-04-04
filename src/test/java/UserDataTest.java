import db.UserData;

class UserDataTest {

    private UserData userData;

    @BeforeEach
    void setUp() {
        userData = new UserData();
        userData.setID(1);
        userData.setUsername("alice");
    }

    // ---- LOCAL DATA STRUCTURES ----

    @Test
    void incrementScore_twice_scoreIsTwo() {
        userData.incrementScore();
        userData.incrementScore();
        assertEquals(2, userData.getScore());
    }

    // ---- BOUNDARY ----

    @Test
    void incrementScore_atMaxInt_doesNotOverflow() {
        userData.setScore(Integer.MAX_VALUE); // assuming you add a setScore for testing
        assertThrows(ArithmeticException.class, () -> userData.incrementScore());
        // or check it caps at MAX_VALUE depending on your design
    }

    // ---- INTERFACE ----

    @Test
    void addMove_validDirection_storedCorrectly() {
        userData.addMove(Direction.LEFT);
        userData.addMove(Direction.UP);

        var history = userData.getMoveHistory();
        var iter = history.iterator();

        assertEquals(Direction.LEFT, iter.next());
        assertEquals(Direction.UP, iter.next());
        assertFalse(iter.hasNext());
    }

    // ---- ERROR HANDLING ----

    @Test
    void addMove_nullDirection_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            userData.addMove(null);
        });
    }

    // ---- INDEPENDENT PATHS ----

    @Test
    void isAdmin_whenTrue_returnsTrue() {
        userData.setAdmin(true); // or however you set it
        assertTrue(userData.isAdmin());
    }

    @Test
    void isAdmin_whenFalse_returnsFalse() {
        assertFalse(userData.isAdmin()); // default
    }
}
