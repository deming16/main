package seedu.modsuni.ui;

import static guitests.guihandles.UserTabHandle.DATE_LABEL_ID;
import static guitests.guihandles.UserTabHandle.DATE_TEXT_ID;
import static guitests.guihandles.UserTabHandle.NAME_LABEL_ID;
import static guitests.guihandles.UserTabHandle.NAME_TEXT_ID;
import static guitests.guihandles.UserTabHandle.USER_DETAIL_1_LABEL;
import static guitests.guihandles.UserTabHandle.USER_DETAIL_1_TEXT;
import static guitests.guihandles.UserTabHandle.USER_DETAIL_2_LABEL;
import static guitests.guihandles.UserTabHandle.USER_DETAIL_2_TEXT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.modsuni.testutil.TypicalAdmins.ALICE;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_ADMIN;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;
import static seedu.modsuni.ui.UserTab.ADMIN_DESC;
import static seedu.modsuni.ui.UserTab.ADMIN_DETAIL_1_LABEL;
import static seedu.modsuni.ui.UserTab.ADMIN_DETAIL_2_LABEL;
import static seedu.modsuni.ui.UserTab.INITIAL_DATE_LABEL;
import static seedu.modsuni.ui.UserTab.INITIAL_NAME_LABEL;
import static seedu.modsuni.ui.UserTab.LAST_SAVED_LABEL;
import static seedu.modsuni.ui.UserTab.STUDENT_DETAIL_1_LABEL;
import static seedu.modsuni.ui.UserTab.STUDENT_DETAIL_2_LABEL;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.UserTabHandle;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.LoginCommand;
import seedu.modsuni.logic.commands.LogoutCommand;
import seedu.modsuni.logic.commands.RegisterCommand;
import seedu.modsuni.logic.commands.SaveCommand;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.storage.Storage;
import seedu.modsuni.storage.StorageManager;
import seedu.modsuni.storage.XmlUserStorage;

public class UserTabTest extends GuiUnitTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "UserTabTest");
    private static final Path TYPICAL_STUDENT_DATA_FILE =
        TEST_DATA_FOLDER.resolve("studentData.xml");
    private static final Path TYPICAL_ADMIN_DATA_FILE =
        TEST_DATA_FOLDER.resolve("adminData.xml");

    private static final String INITIAL_LAST_SAVE_TEXT = "2018-01-01 00:00";

    private static Model model;
    private static Storage storage;
    private static UserTabHandle userTabHandle;

    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        UserTab userTab = new UserTab();

        uiPartRule.setUiPart(userTab);
        userTabHandle = new UserTabHandle(userTab.getRoot());

        storage = new StorageManager(
            null,
            null,
            null,
            null,
            new XmlUserStorage(Paths.get("dummy.xml")));
        model = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());

        model.saveUserFile(STUDENT_MAX, TYPICAL_STUDENT_DATA_FILE);
        model.saveUserFile(ALICE, TYPICAL_ADMIN_DATA_FILE);
    }

    @After
    public void cleanUp() {
        File toRemove = TYPICAL_STUDENT_DATA_FILE.toFile();
        toRemove.delete();
        toRemove = TYPICAL_ADMIN_DATA_FILE.toFile();
        toRemove.delete();
    }

    @Test
    public void userTab_successfulRegisterCommand() throws CommandException, InterruptedException {
        model = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            new CredentialStore());

        Credential validCredential = CREDENTIAL_STUDENT_MAX;
        User dummyUser = STUDENT_MAX;
        Path dummyPath = Paths.get("dummy.xml");

        RegisterCommand command = new RegisterCommand(validCredential,
            dummyUser, dummyPath);

        command.execute(model, commandHistory);

        HashMap<String, String> expectedTextOutput = new HashMap<>();
        expectedTextOutput.put(NAME_TEXT_ID, STUDENT_MAX.getName().toString());
        expectedTextOutput.put(DATE_TEXT_ID,
            STUDENT_MAX.getEnrollmentDate().toString());
        expectedTextOutput.put(USER_DETAIL_1_TEXT,
            STUDENT_MAX.getMajor().toString());
        expectedTextOutput.put(USER_DETAIL_2_TEXT,
            STUDENT_MAX.getMinor().toString());

        HashMap<String, String> expectedLabelOutput = new HashMap<>();
        expectedLabelOutput.put(NAME_LABEL_ID, INITIAL_NAME_LABEL);
        expectedLabelOutput.put(DATE_LABEL_ID, INITIAL_DATE_LABEL);
        expectedLabelOutput.put(USER_DETAIL_1_LABEL, STUDENT_DETAIL_1_LABEL);
        expectedLabelOutput.put(USER_DETAIL_2_LABEL, STUDENT_DETAIL_2_LABEL);

        // waits for UI to update
        Thread.sleep(500);
        assertTextEquals(expectedTextOutput);
        assertLabelEquals(expectedLabelOutput);
    }

    @Test
    public void userTab_successfulLoginCommand() throws CommandException, InterruptedException {
        model = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());

        // Student
        Credential toVerify = CREDENTIAL_STUDENT_MAX;
        LoginCommand command = new LoginCommand(toVerify, TYPICAL_STUDENT_DATA_FILE);

        command.execute(model, commandHistory);

        HashMap<String, String> expectedTextOutput =
            retrieveExpectedTextFields(STUDENT_MAX);
        HashMap<String, String> expectedLabelOutput =
            retrieveExpectedLabel(STUDENT_MAX);

        // waits for UI to update
        Thread.sleep(500);
        assertTextEquals(expectedTextOutput);
        assertLabelEquals(expectedLabelOutput);

        model.resetCurrentUser();

        // Admin
        toVerify = CREDENTIAL_ADMIN;
        command = new LoginCommand(toVerify, TYPICAL_ADMIN_DATA_FILE);

        command.execute(model, commandHistory);

        expectedTextOutput = retrieveExpectedTextFields(ALICE);
        expectedLabelOutput = retrieveExpectedLabel(ALICE);

        // waits for UI to update
        Thread.sleep(500);
        assertTextEquals(expectedTextOutput);
        assertLabelEquals(expectedLabelOutput);
    }

    @Test
    public void userTab_successfulSaveCommand() throws CommandException, InterruptedException {
        // Check only lastSavedText
        model = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());

        // Student
        Credential toVerify = CREDENTIAL_STUDENT_MAX;
        LoginCommand command = new LoginCommand(toVerify, TYPICAL_STUDENT_DATA_FILE);
        command.execute(model, commandHistory);

        String initialSaveTest = userTabHandle.getLastSaveText();

        SaveCommand saveCommand = new SaveCommand(TYPICAL_STUDENT_DATA_FILE);
        Thread.sleep(60000);
        saveCommand.execute(model, commandHistory);
        System.out.println(initialSaveTest + ": " + userTabHandle.getLastSaveText());
        assertNotEquals(initialSaveTest, userTabHandle.getLastSaveText());
    }

    @Test
    public void userTab_successfulEditCommand() {
        // Student
    }

    @Test
    public void userTab_successfulLogoutCommand() throws CommandException, InterruptedException {
        model.setCurrentUser(STUDENT_MAX);

        LogoutCommand command = new LogoutCommand();
        command.execute(model, commandHistory);

        Thread.sleep(500);
        assertLabelAndTextFieldsReset();
    }

    @Test
    public void userTab_failLogoutCommand() throws InterruptedException {
        model.resetCurrentUser();
        // Will only occur when no user is logged in.
        LogoutCommand command = new LogoutCommand();
        try {
            command.execute(model, commandHistory);
        } catch (CommandException e) {
            // Ignored since we're only checking for UI changes
        } finally {
            Thread.sleep(500);
            assertLabelAndTextFieldsReset();
        }
    }

    /**
     * Retrieves all the text fields and the corresponding UI id
     */
    private HashMap<String, String> retrieveExpectedLabel(User user) {
        HashMap<String, String> map = new HashMap<>();
        map.put(NAME_LABEL_ID, INITIAL_NAME_LABEL);
        map.put(DATE_LABEL_ID, INITIAL_DATE_LABEL);
        if (user.getRole() == Role.STUDENT) {
            map.put(USER_DETAIL_1_LABEL, STUDENT_DETAIL_1_LABEL);
            map.put(USER_DETAIL_2_LABEL, STUDENT_DETAIL_2_LABEL);
            return map;
        } else {
            map.put(USER_DETAIL_1_LABEL, ADMIN_DETAIL_1_LABEL);
            map.put(USER_DETAIL_2_LABEL, ADMIN_DETAIL_2_LABEL);
            return map;
        }
    }

    /**
     * Retrieves all the text fields and the corresponding UI id
     */
    private HashMap<String, String> retrieveExpectedTextFields(User user) {
        HashMap<String, String> map = new HashMap<>();
        if (user.getRole() == Role.STUDENT) {
            Student student = (Student) user;
            map.put(NAME_TEXT_ID, student.getName().toString());
            map.put(DATE_TEXT_ID, student.getEnrollmentDate().toString());
            map.put(USER_DETAIL_1_TEXT, student.getMajor().toString());
            map.put(USER_DETAIL_2_TEXT, student.getMinor().toString());
            return map;
        } else {
            Admin admin = (Admin) user;
            map.put(NAME_TEXT_ID, admin.getName().toString());
            map.put(DATE_TEXT_ID, admin.getEmploymentDate().toString());
            map.put(USER_DETAIL_1_TEXT, admin.getSalary().toString());
            map.put(USER_DETAIL_2_TEXT, ADMIN_DESC);
            return map;
        }
    }

    /**
     * Determines if the UserTab labels have been reset.
     */
    private void assertLabelAndTextFieldsReset() {
        // Check Labels
        assertEquals(INITIAL_NAME_LABEL, userTabHandle.getNameLabel());
        assertEquals(INITIAL_DATE_LABEL, userTabHandle.getDateLabel());
        assertEquals(STUDENT_DETAIL_1_LABEL, userTabHandle.getUserDetail1Label());
        assertEquals(STUDENT_DETAIL_2_LABEL, userTabHandle.getUserDetail2Label());
        assertEquals(LAST_SAVED_LABEL, userTabHandle.getLastSaveLabel());

        // Checks Texts
        assertTrue(userTabHandle.getNameText().isEmpty());
        assertTrue(userTabHandle.getDateText().isEmpty());
        assertTrue(userTabHandle.getUserDetail1Text().isEmpty());
        assertTrue(userTabHandle.getUserDetail2Text().isEmpty());
        assertTrue(userTabHandle.getLastSaveText().isEmpty());
    }


    /**
     * Determines if the output in the various data fields are as expected
     */
    private void assertTextEquals(HashMap expectedOutput) {
        assertEquals(expectedOutput.get(NAME_TEXT_ID), userTabHandle.getNameText());
        assertEquals(expectedOutput.get(DATE_TEXT_ID), userTabHandle.getDateText());
        assertEquals(expectedOutput.get(USER_DETAIL_1_TEXT), userTabHandle.getUserDetail1Text());
        assertEquals(expectedOutput.get(USER_DETAIL_2_TEXT), userTabHandle.getUserDetail2Text());
    }

    /**
     * Determines if the UI Field(s) are as expected
     */
    private void assertLabelEquals(HashMap expectedOutput) {
        assertEquals(expectedOutput.get(NAME_LABEL_ID), userTabHandle.getNameLabel());
        assertEquals(expectedOutput.get(DATE_LABEL_ID),
            userTabHandle.getDateLabel());
        assertEquals(expectedOutput.get(USER_DETAIL_1_LABEL),
            userTabHandle.getUserDetail1Label());
        assertEquals(expectedOutput.get(USER_DETAIL_2_LABEL),
            userTabHandle.getUserDetail2Label());
    }
}
