package db;

import enums.Direction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class UserDataTest {

    private UserData user;

    @BeforeEach
    void setUp() {
        // Uses the login constructor: id, username, isAdmin
        user = new UserData(1, "barbtarbox", false);
    }

    // ─── TC-09: isAdmin ────────────────────────────────────────────────────────

    @Test
    void isAdmin_returnsTrue_whenSetToAdmin() {
        UserData admin = new UserData(2, "adminUser", true);
        assertTrue(admin.isAdmin());
    }

    @Test
    void isAdmin_returnsFalse_whenNotAdmin() {
        assertFalse(user.isAdmin());
    }

    // ─── TC-10: incrementScore ─────────────────────────────────────────────────

    @Test
    void incrementScore_startsAtZero() {
        assertEquals(0, user.getScore());
    }

    @Test
    void incrementScore_incrementsTwice_scoreIsTwo() {
        user.incrementScore();
        user.incrementScore();
        assertEquals(2, user.getScore());
    }

    @Test
    void incrementScore_neverGoesNegative() {
        // Score starts at 0 and only increments — it should always stay >= 0
        for (int i = 0; i < 50; i++) {
            user.incrementScore();
        }
        assertTrue(user.getScore() >= 0);
    }

    @Test
    void incrementScore_atMaxValue_overflowIsDetected() {
        user.setScore(Integer.MAX_VALUE);
        assertThrows(RuntimeException.class, () -> user.incrementScore());
    }

    // ─── TC-11: addMove ────────────────────────────────────────────────────────

    @Test
    void addMove_storesMovesInOrder() {
        user.addMove(Direction.LEFT);
        user.addMove(Direction.UP);

        ArrayList<Direction> moves = new ArrayList<>();
        user.getMoveHistory().forEach(moves::add);

        assertEquals(2, moves.size());
        assertEquals(Direction.LEFT, moves.get(0));
        assertEquals(Direction.UP, moves.get(1));
    }

    @Test
    void addMove_null_throwsIllegalArgumentException() {
        // Requires the null-check fix in UserData.addMove() described above
        assertThrows(IllegalArgumentException.class, () -> user.addMove(null));
    }

    @Test
    void addMove_largeNumberOfMoves_allStored() {
        for (int i = 0; i < 100_000; i++) {
            user.addMove(Direction.UP);
        }

        ArrayList<Direction> moves = new ArrayList<>();
        user.getMoveHistory().forEach(moves::add);

        assertEquals(100_000, moves.size());
    }
}
