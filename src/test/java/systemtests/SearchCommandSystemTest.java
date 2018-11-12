package systemtests;

import static seedu.modsuni.commons.core.Messages.MESSAGE_MODULE_LISTED_OVERVIEW;
import static seedu.modsuni.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.ACC1002X;
import static seedu.modsuni.testutil.TypicalModules.CS2103T;
import static seedu.modsuni.testutil.TypicalModules.KEYWORD_MATCHING_ACC;

import org.junit.Test;

import seedu.modsuni.logic.commands.SearchCommand;
import seedu.modsuni.model.Model;

public class SearchCommandSystemTest extends ModsUniSystemTest {

    @Test
    public void search() {
        /* Case: search multiple modules in modsuni book, command with leading spaces and trailing spaces
         * -> 2 modules found
         */
        String command = "   " + SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_ACC + "   ";
        Model expectedModel = getModel();
        ModelHelper.setFilteredList(expectedModel, ACC1002, ACC1002X); // first names of Benson and Daniel are "Meier"
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: repeat previous find command where database module list is displaying the modules we are finding
         * -> 2 modules found
         */
        command = SearchCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_ACC;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module where database module list is not displaying the module we are finding -> 1 module found */
        command = SearchCommand.COMMAND_WORD + " CS2";
        ModelHelper.setFilteredList(expectedModel, CS2103T);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in database, 2 keywords -> 2 modules found */
        command = SearchCommand.COMMAND_WORD + " cs2103t ACC1002x";
        ModelHelper.setFilteredList(expectedModel, CS2103T, ACC1002X);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in database, 2 keywords in reversed order -> 2 modules found */
        command = SearchCommand.COMMAND_WORD + " ACC1002x cs2103t";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in database, 2 keywords with 1 repeat -> 2 modules found */
        command = SearchCommand.COMMAND_WORD + " ACC1002x cs2103t ACC1002x";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find multiple modules in address book, 2 matching keywords and 1 non-matching keyword
         * -> 2 modules found
         */
        command = SearchCommand.COMMAND_WORD + " ACC1002x cs2103t NonMatchingKeyWord";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find modules in address book, keyword is substring of name -> 0 modules found */
        command = SearchCommand.COMMAND_WORD + " 1002";
        ModelHelper.setFilteredList(expectedModel);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find module not in address book -> 0 modules found */
        command = SearchCommand.COMMAND_WORD + " CS3230";
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: mixed case command word -> rejected */
        command = "SeArcH ACC";
        assertCommandFailure(command, MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_MODULES_LISTED_OVERVIEW} with the number of people in the filtered list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class, and the
     * selected card updated accordingly, depending on {@code cardStatus}.
     * @see ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_MODULE_LISTED_OVERVIEW, expectedModel.getFilteredDatabaseModuleList().size());

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
    }
}
