package seedu.modsuni.logic.commands;

import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.testutil.StudentBuilder;



public class ShowUsernameCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    private Model model;

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        ShowUsernameCommand showUsernameCommand = new ShowUsernameCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ShowUsernameCommand.MESSAGE_NOT_LOGGED_IN);
        Model model = new ModelManager();

        showUsernameCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_notAdmin_throwsCommandException() throws Exception {
        model = new ModelManager(getTypicalModuleList(), getTypicalAddressBook(), new UserPrefs(),
                new CredentialStore());
        model.setCurrentUser(new StudentBuilder().build());
        ShowUsernameCommand showUsernameCommand = new ShowUsernameCommand();

        thrown.expect(CommandException.class);
        thrown.expectMessage(ShowUsernameCommand.MESSAGE_NOT_ADMIN);
        showUsernameCommand.execute(model, commandHistory);

    }
}
