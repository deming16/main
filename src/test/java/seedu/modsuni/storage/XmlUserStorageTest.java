package seedu.modsuni.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;

import javax.crypto.NoSuchPaddingException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TemporaryFolder;

import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.AdminBuilder;
import seedu.modsuni.testutil.StudentBuilder;

public class XmlUserStorageTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data",
            "XmlUserStorageTest");

    private static final String TEST_VALID_PASSWORD = "Pass#123";
    private static final String TEST_USER_DATA = "TempUserData.xml";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    private Optional<User> readUser(Path filePath, String password)
            throws DataConversionException, IOException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidPasswordException, CorruptedFileException, NoSuchPaddingException {
        return new XmlUserStorage(filePath).readUser(addToTestDataPathIfNotNull(filePath.toString()), password);
    }

    private Path addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER.resolve(prefsFileInTestDataFolder)
                : null;
    }

    @Test
    public void readUser_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readUser(null, null);
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readUser(Paths.get("NonExistentFile.xml"), "").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readUser(Paths.get("NotXmlFormatUserData.xml"), "");
    }

    @Test
    public void readAndSaveAdmin_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve(TEST_USER_DATA);
        XmlUserStorage xmlUserStorage = new XmlUserStorage(filePath);
        User admin = new AdminBuilder().build();

        // Save and read back
        xmlUserStorage.saveUser(admin, filePath, TEST_VALID_PASSWORD);
        Optional<User> readBackUser = xmlUserStorage.readUser(filePath, TEST_VALID_PASSWORD);
        assertEquals(admin, readBackUser.get());
    }

    @Test
    public void readAndSaveStudent_allInOrder_success() throws Exception {
        Path filePath = testFolder.getRoot().toPath().resolve(TEST_USER_DATA);
        XmlUserStorage xmlUserStorage = new XmlUserStorage(filePath);
        User student = new StudentBuilder().build();

        // Save and read back
        xmlUserStorage.saveUser(student, filePath, TEST_VALID_PASSWORD);
        Optional<User> readBackUser = xmlUserStorage.readUser(filePath, TEST_VALID_PASSWORD);
        assertEquals(student, readBackUser.get());
    }
}
