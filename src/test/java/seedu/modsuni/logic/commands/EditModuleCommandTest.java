package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_ACC1002;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESC_CS1010;
import static seedu.modsuni.logic.commands.CommandTestUtil.TITLE_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DEPARTMENT_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_TITLE_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;

import seedu.modsuni.commons.core.Messages;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.testutil.AdminBuilder;
import seedu.modsuni.testutil.EditModuleDescriptorBuilder;
import seedu.modsuni.testutil.ModuleBuilder;

/**
 * Contains integration tests and unit tests for EditModuleCommand.
 */
public class EditModuleCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(
            getTypicalModuleList(),
            getTypicalAddressBook(),
            new UserPrefs(),
            new CredentialStore());
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setup() {
        model.setCurrentUser(new AdminBuilder().build());
    }

    @Test
    public void notLoggedIn_throwsCommandException() throws Exception {
        EditModuleCommand editModuleCommand =
                new EditModuleCommand(Index.fromZeroBased(1), new EditModuleDescriptorBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditModuleCommand.MESSAGE_NOT_LOGGED_IN);
        Model model = new ModelManager();

        editModuleCommand.execute(model, commandHistory);
    }

    @Test
    public void notAdmin_throwsCommandException() throws Exception {
        EditModuleCommand editModuleCommand =
                new EditModuleCommand(Index.fromZeroBased(1), new EditModuleDescriptorBuilder().build());

        thrown.expect(CommandException.class);
        thrown.expectMessage(EditModuleCommand.MESSAGE_NOT_ADMIN);
        Model model = new ModelManager();
        model.setCurrentUser(new AdminBuilder().withRole(Role.STUDENT).build());

        editModuleCommand.execute(model, commandHistory);
    }

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Module editedModule = new ModuleBuilder().build();
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(editedModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);
        ModelManager expectedModel = new ModelManager(model.getModuleList(), model.getAddressBook(),
                new UserPrefs(), new CredentialStore());

        expectedModel.updateModule(model.getFilteredDatabaseModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastModule = Index.fromOneBased(model.getFilteredDatabaseModuleList().size());
        Module lastModule = model.getFilteredDatabaseModuleList().get(indexLastModule.getZeroBased());

        ModuleBuilder moduleInList = new ModuleBuilder(lastModule);
        Module editedModule = moduleInList.withTitle(VALID_TITLE_CS2109).withDepartment(VALID_DEPARTMENT_CS2109)
                .withDescription(VALID_DESCRIPTION_CS2109).build();

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_CS2109)
                .withDepartment(VALID_DEPARTMENT_CS2109)
                .withDescription(VALID_DESCRIPTION_CS2109).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(indexLastModule, descriptor);

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule.toString());
        ModelManager expectedModel = new ModelManager(model.getModuleList(), model.getAddressBook(),
                new UserPrefs(), new CredentialStore());

        expectedModel.updateModule(lastModule, editedModule);

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE, new EditModuleDescriptor());
        Module editedModule = model.getFilteredDatabaseModuleList().get(INDEX_FIRST_MODULE.getZeroBased());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule);
        ModelManager expectedModel = new ModelManager(model.getModuleList(), model.getAddressBook(),
                new UserPrefs(), new CredentialStore());

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        Module moduleInFilteredList = model.getFilteredDatabaseModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        Module editedModule = new ModuleBuilder(moduleInFilteredList).withTitle(VALID_TITLE_CS2109).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_CS2109).build());

        String expectedMessage = String.format(EditModuleCommand.MESSAGE_EDIT_MODULE_SUCCESS, editedModule.toString());
        ModelManager expectedModel = new ModelManager(model.getModuleList(), model.getAddressBook(),
                new UserPrefs(), new CredentialStore());
        expectedModel.updateModule(model.getFilteredDatabaseModuleList().get(0), editedModule);

        assertCommandSuccess(editModuleCommand, model, commandHistory, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateModuleUnfilteredList_failure() {
        Module firstModule = model.getFilteredDatabaseModuleList().get(INDEX_FIRST_MODULE.getZeroBased());
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder(firstModule).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_SECOND_MODULE, descriptor);

        assertCommandFailure(editModuleCommand, model, commandHistory, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_duplicateModuleFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        // edit module in filtered list into a duplicate in modsuni book
        Module moduleInList = model.getModuleList().getModuleList().get(INDEX_SECOND_MODULE.getZeroBased());
        EditModuleCommand editModuleCommand = new EditModuleCommand(INDEX_FIRST_MODULE,
                new EditModuleDescriptorBuilder(moduleInList).build());

        assertCommandFailure(editModuleCommand, model, commandHistory, EditModuleCommand.MESSAGE_DUPLICATE_MODULE);
    }

    @Test
    public void execute_invalidModuleIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_CS2109).build();
        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editModuleCommand, model, commandHistory, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of modsuni book
     */
    @Test
    public void execute_invalidModuleIndexFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);
        Index outOfBoundIndex = INDEX_SECOND_MODULE;
        // ensures that outOfBoundIndex is still in bounds of modsuni book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getModuleList().getModuleList().size());

        EditModuleCommand editModuleCommand = new EditModuleCommand(outOfBoundIndex,
                new EditModuleDescriptorBuilder().withTitle(TITLE_DESC_CS2109).build());

        assertCommandFailure(editModuleCommand, model, commandHistory, Messages.MESSAGE_INVALID_MODULE_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditModuleCommand standardCommand = new EditModuleCommand(INDEX_FIRST_MODULE, DESC_CS1010);

        // same values -> returns true
        EditModuleDescriptor copyDescriptor = new EditModuleDescriptor(DESC_CS1010);
        EditModuleCommand commandWithSameValues = new EditModuleCommand(INDEX_FIRST_MODULE, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_SECOND_MODULE, DESC_CS1010)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditModuleCommand(INDEX_FIRST_MODULE, DESC_ACC1002)));
    }
}
