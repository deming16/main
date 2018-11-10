package seedu.modsuni.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_AVAILABLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CREDIT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DEPARTMENT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DESCRIPTION;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_PREREQ;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SAVE_PATH;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_ENROLLMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MAJOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MINOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERDATA;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;
import static seedu.modsuni.testutil.TypicalAdmins.ALICE;
import static seedu.modsuni.testutil.TypicalAdmins.BRAD;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.CS1010;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_SEB;

import java.util.Arrays;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.CodeContainsKeywordsPredicate;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.testutil.EditAdminDescriptorBuilder;
import seedu.modsuni.testutil.EditModuleDescriptorBuilder;
import seedu.modsuni.testutil.EditStudentDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String EMPTY = "";

    public static final String VALID_NAME_AMY = "Amy Bee";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names

    public static final String VALID_USERNAME = "validusername";
    public static final String VALID_PASSWORD = "#Qwerty123";
    public static final String VALID_USERDATA = "dummy.xml";
    public static final String LOGIN_USERNAME_DESC =
        " " + PREFIX_USERNAME + VALID_USERNAME;
    public static final String LOGIN_PASSWORD_DESC =
        " " + PREFIX_PASSWORD + VALID_PASSWORD;
    public static final String LOGIN_USERDATA_DESC =
        " " + PREFIX_USERDATA + VALID_USERDATA;

    public static final String INVALID_USERNAME_DESC =
        " " + PREFIX_USERNAME + "!3123asd";
    public static final String INVALID_PASSWORD_DESC =
        " " + PREFIX_PASSWORD + "qwerty123";
    public static final String INVALID_USERDATA_DESC =
        " " + PREFIX_USERDATA + "invalid.extension";

    public static final String VALID_PATH_TO_PIC = "valid.img";
    public static final String VALID_PATH = "validconfig";
    public static final String VALID_ENROLLMENT = "08/08/2017";
    public static final String VALID_MAJOR = "CS";
    public static final String VALID_MINOR = "MA";

    public static final String VALID_TAB = "database";
    public static final String INVALID_TAB = "database1";

    public static final String ENROLLMENT_DESC =
        " " + PREFIX_STUDENT_ENROLLMENT_DATE + VALID_ENROLLMENT;
    public static final String MAJOR_DESC =
        " " + PREFIX_STUDENT_MAJOR + VALID_MAJOR;
    public static final String MINOR_DESC =
        " " + PREFIX_STUDENT_MINOR + VALID_MINOR;

    public static final String INVALID_ENROLLMENT_DESC =
        " " + PREFIX_STUDENT_ENROLLMENT_DATE + "11/11/11";

    public static final String VALID_SAVE_PATH =
            " " + PREFIX_SAVE_PATH + "validPath.xml";
    public static final String VALID_SAVE_PATH_NAME = "validPath.xml";
    public static final String INVALID_SAVE_PATH = "userdata"; // missing xml extension

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditStudentCommand.EditStudentDescriptor DESC_MAX;
    public static final EditStudentCommand.EditStudentDescriptor DESC_SEB;
    public static final EditAdminCommand.EditAdminDescriptor DESC_ALICE;
    public static final EditAdminCommand.EditAdminDescriptor DESC_BRAD;
    public static final EditModuleCommand.EditModuleDescriptor DESC_CS1010;
    public static final EditModuleCommand.EditModuleDescriptor DESC_ACC1002;

    public static final String VALID_SALARY_AMY = "5000";
    public static final String VALID_EMPLOY_DATE_AMY = "09/09/2018";
    public static final String VALID_PATH_AMY = "amy.img";
    public static final String VALID_USERNAME_AMY = "amyusername";
    public static final String VALID_PASSWORD_AMY = VALID_PASSWORD;

    public static final String SALARY_DESC_AMY = " " + PREFIX_SALARY + VALID_SALARY_AMY;
    public static final String EMPLOY_DATE_DESC_AMY = " " + PREFIX_EMPLOYMENT_DATE + VALID_EMPLOY_DATE_AMY;
    public static final String PATH_DESC_AMY = " " + PREFIX_SAVE_PATH + VALID_PATH_AMY;
    public static final String USERNAME_DESC_AMY = " " + PREFIX_USERNAME + VALID_USERNAME_AMY;
    public static final String PASSWORD_DESC_AMY = " " + PREFIX_PASSWORD + VALID_PASSWORD_AMY;

    public static final String INVALID_EMPLOY_DATE_DESC = " " + PREFIX_EMPLOYMENT_DATE + "123455";
    public static final String INVALID_SALARY_DESC = " " + PREFIX_SALARY + "oneHundered";

    public static final String VALID_CODE_CS2109 = "CS2109";
    public static final String VALID_CREDIT_CS2109 = "6";
    public static final String VALID_DESCRIPTION_CS2109 = "description cs2109";
    public static final String VALID_DEPARTMENT_CS2109 = "department";
    public static final String VALID_TITLE_CS2109 = "the cs2109 module";
    public static final String VALID_AVAILABLE_CS2109 = "1111";
    public static final String VALID_PREREQ_CS2109 = "";
    public static final boolean[] VALID_SEMS_CS2109 = {true, true, true, true};
    public static final Code VALID_CODE_CS1010 = new Code("CS1010");
    public static final Code VALID_CODE_ACC1002 = new Code("ACC1002");
    public static final Code VALID_CODE_ACC1002X = new Code("ACC1002X");

    public static final String CODE_DESC_CS1010 = " CS1010";
    public static final String CODE_DESC_CS2103T = " CS2103T";
    public static final String CODE_DESC_ACC1002 = " ACC1002";
    public static final String CODE_DESC_ACC1002X = " ACC1002X";
    public static final String CODE_DESC_LOWER_ACC1002X = " acc1002x";
    public static final String CODE_DESC_CS9999 = " CS9999";

    public static final String CODE_DESC_CS2109 = " " + PREFIX_MODULE_CODE + VALID_CODE_CS2109;
    public static final String CREDIT_DESC_CS2109 = " " + PREFIX_MODULE_CREDIT + VALID_CREDIT_CS2109;
    public static final String DESCRIPTION_DESC_CS2109 = " " + PREFIX_MODULE_DESCRIPTION + VALID_DESCRIPTION_CS2109;
    public static final String DEPARTMENT_DESC_CS2109 = " " + PREFIX_MODULE_DEPARTMENT + VALID_DEPARTMENT_CS2109;
    public static final String TITLE_DESC_CS2109 = " " + PREFIX_MODULE_TITLE + VALID_TITLE_CS2109;
    public static final String AVAILABLE_DESC_CS2109 = " " + PREFIX_MODULE_AVAILABLE + VALID_AVAILABLE_CS2109;
    public static final String PREREQ_DESC_CS2109 = " " + PREFIX_MODULE_PREREQ + VALID_PREREQ_CS2109;
    static {
        DESC_MAX = new EditStudentDescriptorBuilder(STUDENT_MAX).build();
        DESC_SEB = new EditStudentDescriptorBuilder(STUDENT_SEB).build();
        DESC_ALICE = new EditAdminDescriptorBuilder(ALICE).build();
        DESC_BRAD = new EditAdminDescriptorBuilder(BRAD).build();
        DESC_ACC1002 = new EditModuleDescriptorBuilder(ACC1002).build();
        DESC_CS1010 = new EditModuleDescriptorBuilder(CS1010).build();
    }
    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the modsuni book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.

        CredentialStore expectedCredentialStore =
            new CredentialStore(actualModel.getCredentialStore());


        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCredentialStore, actualModel.getCredentialStore());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the module at the given {@code targetIndex} in the
     * {@code model}'s modsuni book.
     */
    public static void showModuleAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredDatabaseModuleList().size());

        Module module = model.getFilteredDatabaseModuleList().get(targetIndex.getZeroBased());
        final String[] splitName = module.getCode().code.split("\\s+");
        model.updateFilteredDatabaseModuleList(new CodeContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredDatabaseModuleList().size());
    }

}
