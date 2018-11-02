package seedu.modsuni.logic.commands;

import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class LogoutCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalModuleList(), new AddressBook(), new UserPrefs(),
            getTypicalCredentialStore());

        expectedModel = new ModelManager(model.getModuleList(), model.getAddressBook(), new UserPrefs(),
            model.getCredentialStore());
    }

    @Test
    public void executeModelNullThrowsNullPointerException() throws NullPointerException, CommandException {

        model = null;

        LogoutCommand logoutCommand = new LogoutCommand();
        thrown.expect(NullPointerException.class);
        logoutCommand.execute(model, commandHistory);
    }

    @Test
    public void executeNullCurrentUserThrowsCommandException() throws CommandException {

        LogoutCommand logoutCommand = new LogoutCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(LogoutCommand.MESSAGE_NOT_LOGGED_IN);

        logoutCommand.execute(model, commandHistory);
    }

    @Test
    public void executeLogoutSuccess() {
        model.setCurrentUser(STUDENT_MAX);
        assertCommandSuccess(new LogoutCommand(), model, commandHistory,
            LogoutCommand.MESSAGE_SUCCESS, expectedModel);
    }


}
