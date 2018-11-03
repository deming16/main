package seedu.modsuni.storage;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class XmlSerializableUserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "XmlSerializableUserTest");
    private static final Path VALID_USER_ADMIN_DATA_FILE =
            TEST_DATA_FOLDER.resolve("validAdminUserData.xml");
    private static final Path VALID_USER_STUDENT_DATA_FILE =
            TEST_DATA_FOLDER.resolve("validStudentUserData.xml");

    private static final String VALID_PASSWORD_HASH =
            "2b005cc8b610fa5899a9f9e592671bba9776a0e778c7f88db9b54eef48490e94";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_validUserAdminDataFile_success() throws Exception {
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(VALID_USER_ADMIN_DATA_FILE);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelType_validUserStudentDataFile_success() throws Exception {
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(VALID_USER_STUDENT_DATA_FILE);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }
}
