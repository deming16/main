package seedu.modsuni.logic.commands;

import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.modsuni.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.modsuni.testutil.TypicalCredentials.CREDENTIAL_STUDENT_MAX;
import static seedu.modsuni.testutil.TypicalCredentials.getTypicalCredentialStore;
import static seedu.modsuni.testutil.TypicalUsers.STUDENT_MAX;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.AddressBook;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.storage.Storage;
import seedu.modsuni.storage.StorageManager;
import seedu.modsuni.storage.XmlUserStorage;
import seedu.modsuni.testutil.CredentialBuilder;


/**
 * Contains integration tests (interaction with the Model) for {@code
 * RegisterCommand}.
 */
public class LoginCommandIntegrationTest {

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
        "LoginCommandTest");

    private static final Path TYPICAL_USERDATA_FILE =
        TEST_DATA_FOLDER.resolve("typicalUserData.xml");
    private static final Path INVALID_USERDATA_FILE =
        TEST_DATA_FOLDER.resolve(
            "invalidUserData.xml");
    private static final Path CORRUPTED_USERDATA_FILE =
        TEST_DATA_FOLDER.resolve("corruptedUserData.xml");

    private static final Path TEMP_USERDATA_FILE =
        TEST_DATA_FOLDER.resolve("tempUserData.xml");


    private static Model model;
    private static Storage storage;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        storage = new StorageManager(
            null,
            null,
            null,
            null,
            new XmlUserStorage(TEMP_USERDATA_FILE));
        model = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());

        model.saveUserFile(STUDENT_MAX, TEMP_USERDATA_FILE);
    }

    @Test
    public void execute_loginSuccess() {
        Credential toVerify = CREDENTIAL_STUDENT_MAX;

        Model expectedModel = new ModelManager(
            new ModuleList(),
            new AddressBook(),
            new UserPrefs(),
            getTypicalCredentialStore());

        assertCommandSuccess(new LoginCommand(toVerify, TEMP_USERDATA_FILE), model,
            commandHistory,
            String.format(LoginCommand.MESSAGE_SUCCESS,
                toVerify.getUsername().toString()),
            expectedModel);
    }

    @Test
    public void execute_loginFailure() {
        // incorrect password
        Credential toVerify = new CredentialBuilder(CREDENTIAL_STUDENT_MAX)
            .withPassword("incorrectPassword")
            .build();
        assertCommandFailure(new LoginCommand(toVerify, TYPICAL_USERDATA_FILE),
            model,
            commandHistory,
            LoginCommand.MESSAGE_LOGIN_FAILURE);

        // invalid userdata

        // corrupted userdata
        toVerify = CREDENTIAL_STUDENT_MAX;
        assertCommandFailure(new LoginCommand(toVerify, CORRUPTED_USERDATA_FILE),
            model,
            commandHistory,
            LoginCommand.MESSAGE_UNABLE_TO_READ_FILE);
    }

    @After
    public void cleanUp() {
        File toRemove = TEMP_USERDATA_FILE.toFile();
        toRemove.delete();
    }


}
