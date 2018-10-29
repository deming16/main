package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;
import static seedu.modsuni.testutil.TypicalSavePaths.PATH_USERDATA_MAX;
import static seedu.modsuni.testutil.TypicalSavePaths.PATH_USERDATA_SEB;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import java.nio.file.Path;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.CredentialBuilder;

public class RegisterCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullCredentialThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(null, STUDENT_MAX, PATH_USERDATA_MAX);
    }

    @Test
    public void constructorNullUserThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(new CredentialBuilder().build(), null, PATH_USERDATA_MAX);
    }

    @Test
    public void constructorNullPathThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new RegisterCommand(new CredentialBuilder().build(), STUDENT_MAX,
            null);
    }

    @Test
    public void equals() {
        Credential maxCredential = CREDENTIAL_STUDENT_MAX;
        Credential sebCredential = CREDENTIAL_STUDENT_SEB;
        User maxUser = STUDENT_MAX;
        User sebUser = STUDENT_SEB;
        Path maxUserdataPath = PATH_USERDATA_MAX;
        Path sebUserdataPath = PATH_USERDATA_SEB;

        RegisterCommand registerMaxCommand =
            new RegisterCommand(maxCredential, maxUser, maxUserdataPath);
        RegisterCommand registerSebCommand =
            new RegisterCommand(sebCredential, sebUser, sebUserdataPath);

        // same object -> returns true
        assertTrue(registerMaxCommand.equals(registerMaxCommand));

        // same values -> returns true
        RegisterCommand registerMaxCommandCopy =
            new RegisterCommand(maxCredential, maxUser, maxUserdataPath);
        assertTrue(registerMaxCommand.equals(registerMaxCommandCopy));

        // different types -> returns false
        assertFalse(registerMaxCommand.equals(1));

        // null -> returns false
        assertFalse(registerMaxCommand.equals(null));

        // different person -> returns false
        assertFalse(registerMaxCommand.equals(registerSebCommand));
    }
}
