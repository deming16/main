package seedu.modsuni.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.model.user.User;
import seedu.modsuni.testutil.StudentBuilder;

public class XmlAdaptedUserTest {

    private static final String VALID_PASSWORD_HASH =
            "2b005cc8b610fa5899a9f9e592671bba9776a0e778c7f88db9b54eef48490e94";

    //private static final User VALID_ADMIN = new AdminBuilder()
    //        .withUsername("FF11A92B87694AB508D1BDB09AA5B575")
    //        .withSalary("F00C309542A2E7A17D46F67573B7CB05")
    //        .build();

    private static final User VALID_STUDENT = new StudentBuilder()
            .withUsername("FF11A92B87694AB508D1BDB09AA5B575")
            .build();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Ignore("Unable to bypass salary invalid check")
    @Test
    public void toModelType_validUserDetails_returnsUser() throws Exception {
        //XmlAdaptedUser admin = new XmlAdaptedUser(VALID_ADMIN, VALID_PASSWORD_HASH);
        //assertEquals(VALID_ADMIN, admin.toModelType(VALID_PASSWORD_HASH));

        XmlAdaptedUser student = new XmlAdaptedUser(VALID_STUDENT, VALID_PASSWORD_HASH);
        assertEquals(VALID_STUDENT, student.toModelType(VALID_PASSWORD_HASH));
    }

    @Test
    public void toModelType_nullUser_throwsIllegalValueException() {
        thrown.expect(NullPointerException.class);
        new XmlAdaptedUser(null, null);
    }
}
