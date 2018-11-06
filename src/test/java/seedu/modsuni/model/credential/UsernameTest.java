package seedu.modsuni.model.credential;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_USERNAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERNAME;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.Assert;

public class UsernameTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidUsername() {
        // null username
        Assert.assertThrows(NullPointerException.class, () -> Username.isValidUsername(null));

        // valid username -> returns true
        assertTrue(Username.isValidUsername(VALID_USERNAME));

        // invalid username -> return false
        assertFalse(Username.isValidUsername(INVALID_USERNAME_DESC));
    }

    @Test
    public void equals() {

        Username maxUsername = CREDENTIAL_STUDENT_MAX.getUsername();

        // same values -> returns true
        Username maxCopy = new Username(maxUsername.getUsername());
        assertTrue(maxUsername.equals(maxCopy));

        // same object -> returns true
        assertTrue(maxUsername.equals(maxUsername));

        // null -> returns false
        assertFalse(maxUsername.equals(null));

        // different type -> returns false
        assertFalse(maxUsername.equals(5));

        // different credential -> returns false
        assertFalse(maxUsername.equals(CREDENTIAL_STUDENT_SEB.getUsername()));
    }

    @Test
    public void testHashCode() {

        Username maxUsername = CREDENTIAL_STUDENT_MAX.getUsername();

        // same credential
        Username maxCopy = new Username(maxUsername.getUsername());
        assertTrue(maxUsername.hashCode() == (maxCopy.hashCode()));

        // diff credential
        assertFalse(
            maxUsername.hashCode() == (CREDENTIAL_STUDENT_SEB.getUsername().hashCode()));
    }
}
