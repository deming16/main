package systemtests;

import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_ACC1002;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_ACC1002X;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_CS9999;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_LOWER_ACC1002X;
import static seedu.modsuni.logic.commands.CommandTestUtil.EMPTY;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_TEST;

import org.junit.Test;

import seedu.modsuni.logic.commands.AddModuleToStudentTakenCommand;
import seedu.modsuni.logic.commands.RemoveModuleFromStudentTakenCommand;
import seedu.modsuni.logic.commands.RemoveModuleFromStudentTakenCommandTest;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.user.student.Student;

public class RemoveModuleFromStudentTakenCommandSystemTest extends ModsUniSystemTest {
    private static final String COMMAND_LOGIN = "login user/test pass/#Qwerty4321 userdata/test_8DB6604.xml";
    private static final String COMMAND_LOGIN_AS_ADMIN = "login user/master pass/Pass#123 userdata/masterconfig.xml";
    private static final String COMMAND_LOGOUT = "logout";

    @Test
    public void removeModuleT() {

        Model model = getModel();
        Student student = STUDENT_TEST;
        model.setCurrentUser(student);
        executeCommand(COMMAND_LOGIN);

        /* ------------------------ Perform removeModuleS operations on the shown list ----------------------------- */

        /* Case: remove a single module into taken module list
         * -> removed as expected
         */
        executeCommand(AddModuleToStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002);
        Module toRemove = ACC1002;
        String command = RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        String expectedResultMessage = RemoveModuleFromStudentTakenCommandTest
                .createCommandResult(EMPTY, EMPTY, EMPTY, " " + toRemove.getCode().code);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* Case: remove a single module into taken module list, command starts with spaces
         * -> removed as expected
         */
        executeCommand(AddModuleToStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002);
        toRemove = ACC1002;
        command = "        " + RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        expectedResultMessage = RemoveModuleFromStudentTakenCommandTest
                .createCommandResult(EMPTY, EMPTY, EMPTY, " " + toRemove.getCode().code);

        assertCommandAsExpected(command, model, expectedResultMessage);

        executeCommand(AddModuleToStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002);

        /* Case: remove three modules into taken module list. One duplicates in command, one does not exist in database,
        one does not exist in taken list,one removed successfully. -> removed as expected
         */
        command = RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002 + CODE_DESC_ACC1002X
                + CODE_DESC_CS9999 + CODE_DESC_ACC1002;
        expectedResultMessage = RemoveModuleFromStudentTakenCommandTest
                .createCommandResult(CODE_DESC_ACC1002, CODE_DESC_CS9999, CODE_DESC_ACC1002X,
                        CODE_DESC_ACC1002);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* Case: remove a module using lower case -> removed as expected */

        executeCommand(AddModuleToStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002X);

        command = RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_LOWER_ACC1002X;

        expectedResultMessage = RemoveModuleFromStudentTakenCommandTest
                .createCommandResult(EMPTY, EMPTY, EMPTY, CODE_DESC_ACC1002X);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* -------------------------------- Perform invalid removeModuleS operations ------------------------------ */

        /* Case: remove without login -> rejected */
        executeCommand(COMMAND_LOGOUT);
        command = RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        assertCommandFailure(command, RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_LOGIN);

        /* Case: remove without login as a student -> rejected */
        executeCommand(COMMAND_LOGIN_AS_ADMIN);
        command = RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        assertCommandFailure(command, RemoveModuleFromStudentTakenCommand.MESSAGE_NOT_STUDENT);

    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Module)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code ModuleListPanel} equal to the corresponding components in
     * {@code expectedModel}.<br>
     */
    private void assertCommandAsExpected(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. {@code Storage} and {@code ModuleListPanel} remain unchanged.<br>
     * 5. Browser url, selected card and status bar remain unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
