package seedu.modsuni.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.StudentBuilder;

public class XmlAdaptedUserTest {
    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test",
            "data", "XmlAdaptedUserTest");

    // The following static variable is as follows <Validity>_<Type of user>_<Field>_<Error>
    private static final Path INVALID_USER_USERNAME_NULL =
            TEST_DATA_FOLDER.resolve("invalid_user_username_null.xml");
    private static final Path INVALID_USER_NAME_NULL =
            TEST_DATA_FOLDER.resolve("invalid_user_name_null.xml");
    private static final Path INVALID_USER_NAME_INVALID =
            TEST_DATA_FOLDER.resolve("invalid_user_name_invalid.xml");
    private static final Path INVALID_USER_ROLE_NULL =
            TEST_DATA_FOLDER.resolve("invalid_user_role_null.xml");

    private static final Path INVALID_ADMIN_SALARY_NULL =
            TEST_DATA_FOLDER.resolve("invalid_admin_salary_null.xml");
    private static final Path INVALID_ADMIN_EMPLOYMENTDATE_NULL =
            TEST_DATA_FOLDER.resolve("invalid_admin_employmentdate_null.xml");
    private static final Path INVALID_ADMIN_EMPLOYMENTDATE_INVALID =
            TEST_DATA_FOLDER.resolve("invalid_admin_employmentdate_invalid.xml");

    private static final Path INVALID_STUDENT_ENROLLMENTDATE_NULL =
            TEST_DATA_FOLDER.resolve("invalid_student_enrollmentdate_null.xml");
    private static final Path INVALID_STUDENT_ENROLLMENTDATE_INVALID =
            TEST_DATA_FOLDER.resolve("invalid_student_enrollmentdate_invalid.xml");
    private static final Path INVALID_STUDENT_MAJOR_NULL =
            TEST_DATA_FOLDER.resolve("invalid_student_major_null.xml");
    private static final Path INVALID_STUDENT_MINOR_NULL =
            TEST_DATA_FOLDER.resolve("invalid_student_minor_null.xml");


    private static final String VALID_PASSWORD_HASH =
            "2b005cc8b610fa5899a9f9e592671bba9776a0e778c7f88db9b54eef48490e94";

    private static final User VALID_STUDENT = new StudentBuilder()
            .withUsername("FF11A92B87694AB508D1BDB09AA5B575")
            .build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_validUserDetails_returnsUser() throws Exception {
        XmlAdaptedUser student = new XmlAdaptedUser(VALID_STUDENT, VALID_PASSWORD_HASH);
        assertEquals(VALID_STUDENT, student.toModelType(VALID_PASSWORD_HASH));
    }

    @Test
    public void toModelType_nullUser_throwsIllegalValueException() {
        thrown.expect(NullPointerException.class);
        new XmlAdaptedUser(null, null);
    }

    @Test
    public void toModelTypeInvalidUser_username_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_USER_USERNAME_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidUser_name_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_USER_NAME_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidUser_name_invalid() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_USER_NAME_INVALID);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidUser_role_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_USER_ROLE_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidAdmin_salary_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_ADMIN_SALARY_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidAdmin_employmentdate_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_ADMIN_EMPLOYMENTDATE_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidAdmin_employmentdate_invalid() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile =
                XmlFileStorage.loadUserDataFromSaveFile(INVALID_ADMIN_EMPLOYMENTDATE_INVALID);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidStudent_enrollmentdate_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_STUDENT_ENROLLMENTDATE_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidStudent_enrollmentdate_invalid() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile =
                XmlFileStorage.loadUserDataFromSaveFile(INVALID_STUDENT_ENROLLMENTDATE_INVALID);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidStudent_major_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_STUDENT_MAJOR_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }

    @Test
    public void toModelTypeInvalidStudent_minor_null() throws Exception {
        thrown.expect(IllegalValueException.class);
        XmlSerializableUser dataFromFile = XmlFileStorage.loadUserDataFromSaveFile(INVALID_STUDENT_MINOR_NULL);
        dataFromFile.toModelType(VALID_PASSWORD_HASH);
    }
}
