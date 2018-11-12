package systemtests;

import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_ACC1002;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_ACC1002X;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_CS1010;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_CS9999;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_LOWER_ACC1002X;
import static seedu.modsuni.logic.commands.CommandTestUtil.EMPTY;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERDATA;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.modsuni.testutil.TypicalAdmins.MASTER_DATA;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_MASTER;
import static seedu.modsuni.testutil.TypicalCredentials.STUDENT_TEST_PASSWORD;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_TEST;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_TEST_DATA;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import seedu.modsuni.logic.commands.AddModuleToStudentStagedCommand;
import seedu.modsuni.logic.commands.AddModuleToStudentStagedCommandTest;
import seedu.modsuni.logic.commands.AddModuleToStudentTakenCommand;
import seedu.modsuni.logic.commands.LoginCommand;

import seedu.modsuni.logic.commands.RemoveModuleFromStudentStagedCommand;
import seedu.modsuni.logic.commands.RemoveModuleFromStudentTakenCommand;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.testutil.StudentUtil;

public class AddModuleToStudentStagedCommandSystemTest extends ModsUniSystemTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "sandbox");
    private static final Path STUDENT_TEST_DATA_PATH =
        TEST_DATA_FOLDER.resolve(STUDENT_TEST_DATA);

    private static final Path ADMIN_DATA_PATH =
        TEST_DATA_FOLDER.resolve(MASTER_DATA);

    private static final String COMMAND_LOGOUT = "logout";

    @Test
    public void addModuleS() {

        Model model = getModel();
        model.addCredential(CREDENTIAL_MASTER);

        executeCommand(StudentUtil.getLoginCommand(STUDENT_TEST,
            STUDENT_TEST_PASSWORD, STUDENT_TEST_DATA_PATH.toString()));

        /* ------------------------ Perform addModuleS operations on the shown list ----------------------------- */

        /* Case: add a single module into staged module list
         * -> added as expected
         */

        Module toAdd = ACC1002;
        String command = AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        String expectedResultMessage = AddModuleToStudentStagedCommandTest
                        .createCommandResult(EMPTY, EMPTY, EMPTY, EMPTY, " " + toAdd.getCode().code);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* Case: add a single module into staged module list, command starts with space
         * -> added as expected
         */

        executeCommand(RemoveModuleFromStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002);

        toAdd = ACC1002;
        command = "        " + AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        expectedResultMessage = AddModuleToStudentStagedCommandTest
                .createCommandResult(EMPTY, EMPTY, EMPTY, EMPTY, " " + toAdd.getCode().code);

        assertCommandAsExpected(command, model, expectedResultMessage);

        executeCommand(AddModuleToStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002X);

        /* Case: add four modules into staged module list. One duplicates in command, one does not exist in database,
        one duplicates in staged list, one duplicates in taken list, one added successfully. -> added as expected
         */
        command = AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002 + CODE_DESC_ACC1002X
                + CODE_DESC_CS1010 + CODE_DESC_CS9999 + CODE_DESC_ACC1002;
        expectedResultMessage = AddModuleToStudentStagedCommandTest
                .createCommandResult(CODE_DESC_ACC1002, CODE_DESC_CS9999, CODE_DESC_ACC1002, CODE_DESC_ACC1002X,
                        CODE_DESC_CS1010);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* Case: add a module using lower case -> added as expected */

        executeCommand(RemoveModuleFromStudentTakenCommand.COMMAND_WORD + CODE_DESC_ACC1002X);

        command = AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_LOWER_ACC1002X;

        expectedResultMessage = AddModuleToStudentStagedCommandTest
                .createCommandResult(EMPTY, EMPTY, EMPTY, EMPTY, CODE_DESC_ACC1002X);

        assertCommandAsExpected(command, model, expectedResultMessage);

        /* --------------------------- Perform invalid addModuleS operations --------------------------------------- */

        /* Case: add without login -> rejected */
        executeCommand(COMMAND_LOGOUT);
        command = AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        assertCommandFailure(command, AddModuleToStudentStagedCommand.MESSAGE_NOT_LOGIN);

        /* Case: add without login as a student -> rejected */

        String commandLoginAdmin = LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + "master" + " "
            + PREFIX_PASSWORD + "Pass#123" + " "
            + PREFIX_USERDATA + ADMIN_DATA_PATH.toString();

        executeCommand(commandLoginAdmin);

        command = AddModuleToStudentStagedCommand.COMMAND_WORD + CODE_DESC_ACC1002;
        assertCommandFailure(command, AddModuleToStudentStagedCommand.MESSAGE_NOT_STUDENT);

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
        //assertApplicationDisplaysExpected("", expectedResultMessage,expectedModel);
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
