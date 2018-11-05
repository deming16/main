package seedu.modsuni.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.commons.events.BaseEvent;
import seedu.modsuni.commons.events.ui.JumpToDatabaseListRequestEvent;
import seedu.modsuni.commons.events.ui.ShowDatabaseTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.ui.testutil.EventsCollectorRule;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.logic.commands.CommandTestUtil.showModuleAtIndex;
import static seedu.modsuni.testutil.TypicalCodes.CODE_FIRST_MODULE;
import static seedu.modsuni.testutil.TypicalCodes.CODE_NOT_IN_LIST;
import static seedu.modsuni.testutil.TypicalCodes.CODE_SECOND_MODULE;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectCommand}.
 */
public class ShowModuleCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private final Model model = new ModelManager(getTypicalModuleList(), getTypicalAddressBook(),
            new UserPrefs(), new CredentialStore());
    private final Model expectedModel = new ModelManager(getTypicalModuleList(), getTypicalAddressBook(),
            new UserPrefs(), new CredentialStore());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void execute_emptyCode_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ShowModuleCommand(null);
    }

    @Test
    public void execute_validModuleUnfilteredList_success() {
        assertExecutionSuccess(CODE_FIRST_MODULE, INDEX_FIRST_MODULE);
        assertExecutionSuccess(CODE_SECOND_MODULE, INDEX_SECOND_MODULE);
    }

    @Test
    public void execute_invalidModuleUnfilteredList_failure() {
        assertExecutionFailure(CODE_NOT_IN_LIST, ShowModuleCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE);
    }

    @Test
    public void execute_validModuleFilteredList_success() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        assertExecutionSuccess(CODE_FIRST_MODULE, INDEX_FIRST_MODULE);

        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        assertExecutionSuccess(CODE_SECOND_MODULE, INDEX_SECOND_MODULE);
    }

    @Test
    public void execute_invalidModuleFilteredList_failure() {
        showModuleAtIndex(model, INDEX_FIRST_MODULE);

        assertExecutionFailure(CODE_NOT_IN_LIST, ShowModuleCommand.MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE);
    }

    @Test
    public void equals() {
        ShowModuleCommand showModuleFirstCommand = new ShowModuleCommand(CODE_FIRST_MODULE);
        ShowModuleCommand showModuleSecondCommand = new ShowModuleCommand(CODE_SECOND_MODULE);

        // same object -> returns true
        assertTrue(showModuleFirstCommand.equals(showModuleFirstCommand));

        // same values -> returns true
        ShowModuleCommand showModuleFirstCommandCopy = new ShowModuleCommand(CODE_FIRST_MODULE);
        assertTrue(showModuleFirstCommand.equals(showModuleFirstCommandCopy));

        // different types -> returns false
        assertFalse(showModuleFirstCommand.equals(1));

        // null -> returns false
        assertFalse(showModuleFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(showModuleFirstCommand.equals(showModuleSecondCommand));
    }

    /**
     * Executes a {@code ShowModuleCommand} with the given {@code code}, and checks that {@code JumpToDatabaseListRequestEvent}
     * is raised with the given {@code index}.
     */
    private void assertExecutionSuccess(Code code, Index index) {
        ShowModuleCommand showModuleCommand = new ShowModuleCommand(code);
        String expectedMessage = String.format(ShowModuleCommand.MESSAGE_SUCCESS, code.toString());

        assertCommandSuccess(showModuleCommand, model, commandHistory, expectedMessage, expectedModel);

        JumpToDatabaseListRequestEvent lastEvent = (JumpToDatabaseListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        BaseEvent secondLastEvent = eventsCollectorRule.eventsCollector.getSelectedMostRecent(2);

        assertEquals(showModuleCommand.getIndex(), Index.fromZeroBased(lastEvent.targetIndex));
        assertEquals(index, Index.fromZeroBased(lastEvent.targetIndex));
        assertTrue(secondLastEvent instanceof ShowDatabaseTabRequestEvent);
    }

    /**
     * Executes a {@code ShowModuleCommand} with the given {@code code}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Code code, String expectedMessage) {
        ShowModuleCommand showModuleCommand = new ShowModuleCommand(code);
        assertCommandFailure(showModuleCommand, model, commandHistory, expectedMessage);
        assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
    }
}
