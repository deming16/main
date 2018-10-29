package seedu.modsuni.model.user.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.StudentBuilder;

public class StudentTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void equals() {
        Student max = STUDENT_MAX;

        // same values -> returns true
        Student maxCopy = new StudentBuilder(STUDENT_MAX)
            .build();
        assertTrue(max.equals(maxCopy));

        // same object -> returns true
        assertTrue(max.equals(max));

        // null -> returns false
        assertFalse(max.equals(null));

        // different type -> returns false
        assertFalse(max.equals(5));

        // different student -> returns false
        assertFalse(max.equals(STUDENT_SEB));
    }

    @Test
    public void testHashCode() {
        // same student
        Student maxCopy = new StudentBuilder(STUDENT_MAX)
            .build();
        assertTrue(STUDENT_MAX.hashCode() == maxCopy.hashCode());

        // diff student
        assertFalse(STUDENT_MAX.hashCode() == STUDENT_SEB.hashCode());
    }

    @Test
    public void toDisplayUi() {
        // same UI details
        Student maxCopy = new StudentBuilder(STUDENT_MAX)
            .build();
        assertTrue(STUDENT_MAX.toDisplayUi().equals(maxCopy.toDisplayUi()));

        // diff UI details
        assertFalse(STUDENT_MAX.toDisplayUi().equals(STUDENT_SEB.toDisplayUi()));
    }
}
