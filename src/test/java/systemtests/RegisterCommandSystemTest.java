package systemtests;

import static seedu.modsuni.logic.commands.CommandTestUtil.ENROLLMENT_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.LOGIN_PASSWORD_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.LOGIN_USERDATA_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.MAJOR_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.MINOR_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.modsuni.logic.commands.CommandTestUtil.REGISTER_AMY_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.REGISTER_BOB_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.USERNAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.USERNAME_BOB;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_ENROLLMENT;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_MAJOR;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_MINOR;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_BOB;

import java.util.Arrays;

import org.junit.Test;

import seedu.modsuni.logic.commands.LogoutCommand;
import seedu.modsuni.logic.commands.RegisterCommand;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.StudentBuilder;
import seedu.modsuni.testutil.StudentUtil;

public class RegisterCommandSystemTest extends ModsUniSystemTest {

    @Test
    public void register() {
        Model model = getModel();

        /* ------------------------ Perform register operations ----------------------------- */

        /* Case: add a new student account non-empty modsUni credential store
         * -> new account registered
         */
        Student toAdd = new StudentBuilder()
            .withUsername(USERNAME_AMY)
            .withName(VALID_NAME_AMY)
            .withEnrollmentDate(VALID_ENROLLMENT)
            .withMajor(Arrays.asList(VALID_MAJOR))
            .withMinor(Arrays.asList(VALID_MINOR))
            .build();

        String command =
            "   " + RegisterCommand.COMMAND_WORD + "  " + REGISTER_AMY_DESC + "  "
                + LOGIN_PASSWORD_DESC + " "
                + NAME_DESC_AMY + " " + ENROLLMENT_DESC + " " + MAJOR_DESC + " "
                + MINOR_DESC + " " + LOGIN_USERDATA_DESC;
        assertCommandSuccess(command, toAdd);

        /* ----------------------------------- Perform invalid register
        operations --------------------------------------- */

        /* Case: add another student account in a non-empty modsUni credential
         * store, where user is already logged in.
         * -> rejected
         */
        toAdd = new StudentBuilder()
            .withUsername(USERNAME_BOB)
            .withName(VALID_NAME_BOB)
            .withEnrollmentDate(VALID_ENROLLMENT)
            .withMajor(Arrays.asList(VALID_MAJOR))
            .withMinor(Arrays.asList(VALID_MINOR))
            .build();

        command =
            "   " + RegisterCommand.COMMAND_WORD + "  " + REGISTER_BOB_DESC + "  " + LOGIN_PASSWORD_DESC + " "
                + NAME_DESC_BOB + " " + ENROLLMENT_DESC + " " + MAJOR_DESC + " "
                + MINOR_DESC + " " + LOGIN_USERDATA_DESC;
        assertCommandFailure(command, RegisterCommand.MESSAGE_ALREADY_LOGGED_IN);

        // logouts from currentUser
        executeCommand(LogoutCommand.COMMAND_WORD);

        /* Case: add another student account in a non-empty modsUni credential
         * store.
         * -> new student registered
         */
        assertCommandSuccess(command, toAdd);

        // logouts from currentUser
        executeCommand(LogoutCommand.COMMAND_WORD);

        /* ----------------------------------- Perform invalid register
        operations --------------------------------------- */



    }

    /**
     * Executes the {@code RegisterCommand} that adds {@code toAdd} to the
     * model and asserts that the,<br>
     * 1. Command box displays an empty string.<br>
     * 2. Command box has the default style class.<br>
     * 3. Result display box displays the success message of executing
     * {@code RegisterCommand} with the details of
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
    private void assertCommandSuccess(Student toAdd) {
        assertCommandSuccess(StudentUtil.getRegisterCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(Person)}. Executes {@code command}
     * instead.
     *
     * @see RegisterCommandSystemTest#assertCommandSuccess(Student)
     */
    private void assertCommandSuccess(String command, Student toAdd) {
        Model expectedModel = getModel();
        expectedModel.setCurrentUser(toAdd);
        String expectedResultMessage =
            String.format(RegisterCommand.MESSAGE_SUCCESS, toAdd, "dummy.xml");

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
     * @see RegisterCommandSystemTest#assertCommandSuccess(String, Student)
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
