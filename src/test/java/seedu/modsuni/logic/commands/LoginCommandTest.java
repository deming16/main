package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_USERDATA;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_SEB;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.Credential;

public class LoginCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(
        getTypicalModuleList(),
        getTypicalAddressBook(),
        new UserPrefs(),
        getTypicalCredentialStore());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructorNullCredentialThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(null, Paths.get("dummy.xml"));
    }

    @Test
    public void constructorNullPathThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new LoginCommand(CREDENTIAL_STUDENT_MAX, null);
    }

    @Test
    public void executeCurrentUserNullThrowsCommandException() throws CommandException {
        Credential toVerify = CREDENTIAL_STUDENT_MAX;

        LoginCommand loginCommand =
            new LoginCommand(toVerify, Paths.get(VALID_USERDATA));

        ModelManager newModel = new ModelManager(
            model.getModuleList(),
            model.getAddressBook(),
            new UserPrefs(),
            model.getCredentialStore());
        newModel.setCurrentUser(STUDENT_SEB);

        thrown.expect(CommandException.class);
        thrown.expectMessage(LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

        loginCommand.execute(newModel, commandHistory);
    }

    @Test
    public void equals() {
        Credential maxCredential = CREDENTIAL_STUDENT_MAX;
        Credential sebCredential = CREDENTIAL_STUDENT_SEB;

        LoginCommand loginMaxCommand =
            new LoginCommand(maxCredential, Paths.get("dummy.xml"));
        LoginCommand loginSebCommand =
            new LoginCommand(sebCredential, Paths.get("dummy.xml"));

        // same object -> returns true
        assertTrue(loginMaxCommand.equals(loginMaxCommand));

        // same values -> returns true
        LoginCommand loginMaxCommandCopy =
            new LoginCommand(maxCredential, Paths.get("dummy.xml"));
        assertTrue(loginMaxCommand.equals(loginMaxCommandCopy));

        // different types -> returns false
        assertFalse(loginMaxCommand.equals(1));

        // null -> returns false
        assertFalse(loginMaxCommand.equals(null));

        // different login command -> returns false
        assertFalse(loginMaxCommand.equals(loginSebCommand));
    }
}
