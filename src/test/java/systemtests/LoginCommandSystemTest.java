package systemtests;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERDATA;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.modsuni.testutil.TypicalAdmins.MASTER;
import static seedu.modsuni.testutil.TypicalAdmins.MASTER_DATA;
import static seedu.modsuni.testutil.TypicalCredentials.*;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX_DATA;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Test;

import seedu.modsuni.logic.commands.LoginCommand;
import seedu.modsuni.logic.commands.LogoutCommand;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.StudentUtil;

public class LoginCommandSystemTest extends ModsUniSystemTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "sandbox");
    private static final Path TYPICAL_CREDENTIALSTORE_FILE =
        TEST_DATA_FOLDER.resolve("sampleCredentialStore.xml");

    private static final Path STUDENT_MAX_DATA_PATH =
        TEST_DATA_FOLDER.resolve(STUDENT_MAX_DATA);

    private static final Path ADMIN_DATA_PATH =
        TEST_DATA_FOLDER.resolve(MASTER_DATA);

    @After
    public void cleanUp() {
        File toRemove = TYPICAL_CREDENTIALSTORE_FILE.toFile();
        toRemove.delete();
    }

    @Test
    public void login() {

        Model model = getModel();
        model.addCredential(CREDENTIAL_STUDENT_MAX);
        model.addCredential(CREDENTIAL_STUDENT_SEB);
        model.addCredential(CREDENTIAL_MASTER);

        /* ------------------------ Perform login operations ----------------------------- */

        /* Case: logs in into an existing student account -> login success
         */
        String command = StudentUtil.getLoginCommand(STUDENT_MAX,
            STUDENT_MAX_PASSWORD, STUDENT_MAX_DATA_PATH.toString());

        assertCommandSuccess(command, STUDENT_MAX);

        /* Case: logs in into an existing account(while logged in)
         *  -> login fail
         */
        command = StudentUtil.getLoginCommand(STUDENT_MAX,
            STUDENT_MAX_PASSWORD, STUDENT_MAX_DATA_PATH.toString());

        assertCommandFailure(command, LoginCommand.MESSAGE_ALREADY_LOGGED_IN);

        // logout
        executeCommand(LogoutCommand.COMMAND_WORD);

        /* Case: logs in into an existing admin account -> login success
         */
        String commandLoginAdmin = LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + "master" + " "
            + PREFIX_PASSWORD + "Pass#123" + " "
            + PREFIX_USERDATA + ADMIN_DATA_PATH.toString();

        assertCommandSuccess(commandLoginAdmin, MASTER);

        /* ---------------- Perform invalid login operations ---------------- */

        // without username
        command = LoginCommand.COMMAND_WORD + " "
            + PREFIX_PASSWORD + "Pass#123" + " "
            + PREFIX_USERDATA + ADMIN_DATA_PATH.toString();
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));

        // without password
        command = LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + "master" + " "
            + PREFIX_USERDATA + ADMIN_DATA_PATH.toString();
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));


        // without userdata
        command = LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + "master" + " "
            + PREFIX_PASSWORD + "Pass#123" + " ";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoginCommand.MESSAGE_USAGE));

    }

    /**
     * Executes the {@code seedu.modsuni.logic.commands.LoginCommand} that adds {@code toAdd} to the
     * model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing
     * {@code seedu.modsuni.logic.commands.LoginCommand} with the details of
     * {@code toAdd}.<br>
     * 4. {@code Storage} and {@code currentUser} equal to the corresponding
     * components in
     * the current model added with {@code toAdd}.<br>
     * 5. UI Elements other than the {@code UserTab} remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     *
     * @see ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, User toLogin) {
        Model expectedModel = getModel();
        expectedModel.setCurrentUser(toLogin);
        String expectedResultMessage =
            String.format(LoginCommand.MESSAGE_SUCCESS,
                toLogin.getUsername());

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Student)} except asserts that
     * the,<br>
     * 1. Result display box displays {@code expectedResultMessage}.<br>
     * 2. {@code Storage} and {@code currentUser} equal to the corresponding
     * components in
     * {@code expectedModel}.<br>
     *
     * @see LoginCommandSystemTest#assertCommandSuccess(String, User)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and asserts that the,<br>
     * 1. Command box displays {@code command}.<br>
     * 2. Command box has the error style class.<br>
     * 3. Result display box displays {@code expectedResultMessage}.<br>
     * 4. UI Elements other than the {@code UserTab} remains unchanged.<br>
     * Verifications 1, 3 and 4 are performed by
     * {@code ModsUniSystemTest#assertApplicationDisplaysExpected(String,
     * String, Model)}.<br>
     *
     * @see ModsUniSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
