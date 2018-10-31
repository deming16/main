package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.testutil.TypicalAdmins.ALICE;
import static seedu.modsuni.testutil.TypicalAdmins.BRAD;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.EditAdminCommand.EditAdminDescriptor;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.testutil.AdminBuilder;
import seedu.modsuni.testutil.EditAdminDescriptorBuilder;

public class EditAdminCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(
            getTypicalModuleList(),
            getTypicalAddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        model.setCurrentUser(new AdminBuilder().build());
    }

    @Test
    public void constructorNullDescriptorThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EditAdminCommand(null);
    }

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        EditAdminCommand editAdminCommand =
                new EditAdminCommand(new EditAdminDescriptorBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditAdminCommand.MESSAGE_NOT_LOGGED_IN);
        Model model = new ModelManager();

        editAdminCommand.execute(model, commandHistory);
    }

    @Test
    public void notAdmin_throwsCommandException() throws Exception {
        EditAdminCommand editAdminCommand =
                new EditAdminCommand(new EditAdminDescriptorBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditAdminCommand.MESSAGE_NOT_ADMIN);
        Model model = new ModelManager();
        model.setCurrentUser(new AdminBuilder().withRole(Role.STUDENT).build());

        editAdminCommand.execute(model, commandHistory);
    }

    @Test
    public void executeAllFieldsSpecifiedSuccess() {
        Admin editedAdmin = new AdminBuilder()
                .withName("Max Emilian Verstappen")
                .withSalary("3000")
                .withEmployedDate("01/01/2001")
                .build();
        EditAdminDescriptor descriptor =
                new EditAdminDescriptorBuilder(editedAdmin).build();

        EditAdminCommand editAdminCommand =
                new EditAdminCommand(descriptor);

        String expectedMessage = String.format(
                EditAdminCommand.MESSAGE_EDIT_ADMIN_SUCCESS,
                editedAdmin.toString());

        ModelManager expectedModel = new ModelManager(
                model.getModuleList(),
                model.getAddressBook(),
                new UserPrefs(),
                model.getCredentialStore());
        expectedModel.setCurrentUser(editedAdmin);

        assertCommandSuccess(
                editAdminCommand,
                model,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void executeSomeFieldsSpecifiedSuccess() {
        Admin editedAdmin = new AdminBuilder()
                .withName("Max Emilian Verstappen")
                .build();
        EditAdminDescriptor descriptor =
                new EditAdminDescriptorBuilder(editedAdmin).build();

        EditAdminCommand editAdminCommand =
                new EditAdminCommand(descriptor);

        String expectedMessage = String.format(
                EditAdminCommand.MESSAGE_EDIT_ADMIN_SUCCESS,
                editedAdmin.toString());

        ModelManager expectedModel = new ModelManager(
                model.getModuleList(),
                model.getAddressBook(),
                new UserPrefs(),
                model.getCredentialStore());
        expectedModel.setCurrentUser(editedAdmin);

        assertCommandSuccess(
                editAdminCommand,
                model,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void executeNoFieldsSpecifiedSucess() {
        EditAdminCommand editAdminCommand =
                new EditAdminCommand(new EditAdminDescriptor());

        Admin editedAdmin = (Admin) model.getCurrentUser();

        String expectedMessage = String.format(
                EditAdminCommand.MESSAGE_EDIT_ADMIN_SUCCESS,
                editedAdmin.toString());

        ModelManager expectedModel = new ModelManager(
                model.getModuleList(),
                model.getAddressBook(),
                new UserPrefs(),
                model.getCredentialStore());
        expectedModel.setCurrentUser(editedAdmin);

        assertCommandSuccess(
                editAdminCommand,
                model,
                commandHistory,
                expectedMessage,
                expectedModel);
    }

    @Test
    public void equals() {
        Admin alice = ALICE;
        Admin brad = BRAD;
        EditAdminDescriptor aliceDescriptor =
                new EditAdminDescriptorBuilder(alice).build();
        EditAdminDescriptor bradDescriptor =
                new EditAdminDescriptorBuilder(brad).build();

        EditAdminCommand editAliceCommand =
                new EditAdminCommand(aliceDescriptor);
        EditAdminCommand editBradCommand =
                new EditAdminCommand(bradDescriptor);

        // same object -> returns true
        assertTrue(editAliceCommand.equals(editAliceCommand));

        // same values -> returns true
        EditAdminCommand editAliceCommandCopy =
                new EditAdminCommand(aliceDescriptor);
        assertTrue(editAliceCommand.equals(editAliceCommandCopy));

        // different values -> returns false
        assertFalse(editAliceCommand.equals(1));

        // null -> returns false
        assertFalse(editAliceCommand.equals(null));

        // different user -> returns false
        assertFalse(editAliceCommand.equals(editBradCommand));


    }
}
