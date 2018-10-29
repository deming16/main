package seedu.modsuni.model.credential;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_PASSWORD_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.testutil.Assert;

public class PasswordTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidPassword() {

        // null password
        Assert.assertThrows(NullPointerException.class, () -> Password.isValidPassword(null));

        // valid password -> returns true
        assertTrue(Password.isValidPassword(VALID_PASSWORD));

        // invalid password -> returns false
        assertFalse(Password.isValidPassword(INVALID_PASSWORD_DESC));
    }

    @Test
    public void equals() {
        Password maxPassword = CREDENTIAL_STUDENT_MAX.getPassword();

        // same values -> returns true
        Password maxCopy = new Password(maxPassword.getValue());
        assertTrue(maxPassword.equals(maxCopy));

        // same object -> returns true
        assertTrue(maxPassword.equals(maxPassword));

        // null -> returns false
        assertFalse(maxPassword.equals(null));

        // different type -> returns false
        assertFalse(maxPassword.equals(5));

        // different credential -> returns false
        assertFalse(maxPassword.equals(CREDENTIAL_STUDENT_SEB.getPassword()));
    }

    @Test
    public void testHashCode() {

        Password maxPassword = CREDENTIAL_STUDENT_MAX.getPassword();

        // same credential
        Password maxCopy = new Password(maxPassword.getValue());
        assertTrue(maxPassword.hashCode() == (maxCopy.hashCode()));

        // diff credential
        assertFalse(
            maxPassword.hashCode() == (CREDENTIAL_STUDENT_SEB.getPassword().hashCode()));
    }
}
