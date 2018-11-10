package seedu.modsuni.logic.commands;

import static junit.framework.TestCase.assertTrue;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.logic.commands.SwitchTabCommand.MESSAGE_SUCCESS;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.UserPrefs;

public class SwitchTabCommandTest {

    private Model model;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(
                new ModuleList(),
                new AddressBook(),
                new UserPrefs(),
                getTypicalCredentialStore());
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullCredentialThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new SwitchTabCommand(null);
    }

    @Test
    public void executeSwitchToUserTabSuccess() {
        String tabToSwitch = "user";
        SwitchTabCommand switchToUser = new SwitchTabCommand(tabToSwitch);
        String expectedMessage = String.format(SwitchTabCommand.MESSAGE_SUCCESS, tabToSwitch);
        assertCommandSuccess(switchToUser, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void executeSwitchToStagedTabSuccess() {
        String tabToSwitch = "staged";
        SwitchTabCommand switchToStaged = new SwitchTabCommand(tabToSwitch);
        String expectedMessage = String.format(SwitchTabCommand.MESSAGE_SUCCESS, tabToSwitch);
        assertCommandSuccess(switchToStaged, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void executeSwitchToTakenTabSuccess() {
        String tabToSwitch = "taken";
        SwitchTabCommand switchToTaken = new SwitchTabCommand(tabToSwitch);
        String expectedMessage = String.format(SwitchTabCommand.MESSAGE_SUCCESS, tabToSwitch);
        assertCommandSuccess(switchToTaken, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void executeSwitchToDatabaseTabSuccess() {
        String tabToSwitch = "database";
        SwitchTabCommand switchToDatabase = new SwitchTabCommand(tabToSwitch);
        String expectedMessage = String.format(SwitchTabCommand.MESSAGE_SUCCESS, tabToSwitch);
        assertCommandSuccess(switchToDatabase, model, commandHistory, expectedMessage, model);
    }

    @Test
    public void executeInvalidOptionException() {
        String tabToSwitch = "invalid";
        SwitchTabCommand switchToInvalid = new SwitchTabCommand(tabToSwitch);
        String expectedMessage = String.format(SwitchTabCommand.MESSAGE_INVALID_OPTION);
        assertCommandFailure(switchToInvalid, model, commandHistory, expectedMessage);
    }

    @Test
    public void equals() {

        String firstTab = "user";
        String secondTab = "a";

        SwitchTabCommand firstSwitchTab = new SwitchTabCommand(firstTab);
        SwitchTabCommand secondSwitchTab = new SwitchTabCommand(secondTab);

        // same object -> returns true
        assertTrue(firstSwitchTab.equals(firstSwitchTab));

        // same values -> returns true
        SwitchTabCommand firstSwitchTabCopy = new SwitchTabCommand(firstTab);
        assertTrue(firstSwitchTab.equals(firstSwitchTabCopy));

        // different types -> returns false
        assertFalse(firstSwitchTab.equals(1));

        // null -> returns false
        assertFalse(firstSwitchTab.equals(null));

        // different person -> returns false
        assertFalse(firstSwitchTab.equals(secondSwitchTab));
    }
}
