package seedu.modsuni.storage;

import static java.util.Objects.requireNonNull;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.NoSuchPaddingException;
import javax.xml.bind.annotation.XmlElement;

import seedu.modsuni.MainApp;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.IllegalValueException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.commons.util.DataSecurityUtil;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.Salary;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.EnrollmentDate;
import seedu.modsuni.model.user.student.Student;

/**
 * An User that is serializable to XML format
 */
public class XmlAdaptedUser {

    private static final String MISSING_FIELD_MESSAGE_FORMAT = "User's "
            + "%s field is missing!";

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    // Must have for all users
    @XmlElement(required = true)
    private String username;
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String role;

    // Attributes for Admin
    @XmlElement
    private String salary;
    @XmlElement
    private String employmentDate;

    // Attributes for Student
    @XmlElement
    private String enrollmentDate;
    @XmlElement
    private String major;
    @XmlElement
    private String minor;
    @XmlElement
    private List<XmlAdaptedModule> modulesTaken = new ArrayList<>();
    @XmlElement
    private List<XmlAdaptedModule> modulesStaged = new ArrayList<>();

    /**
     * Creates an empty XmlAdaptedUser.
     * This empty constructor is required for marshalling.
     */
    public XmlAdaptedUser() {}

    /**
     * Converts a given User into this class for JAXB use.
     *
     * @param user future changes to this will not affect the created XmlAdaptedUser
     */
    public XmlAdaptedUser(User user, String password) {
        requireNonNull(user);
        requireNonNull(password);

        // All users
        logger.info("Converting user attributes (username, name, role)");
        this.username = DataSecurityUtil.bytesToBase64(DataSecurityUtil.encryptData(
                user.getUsername().toString().getBytes(), password));
        this.name = user.getName().toString();
        this.role = user.getRole().toString();


        // Admin
        if (user.getRole() == Role.ADMIN) {
            logger.info("Converting admin attributes (salary, employment date)");
            Admin admin = (Admin) user;
            this.salary = DataSecurityUtil.bytesToBase64(DataSecurityUtil.encryptData(
                    admin.getSalary().toString().getBytes(), password));
            this.employmentDate = admin.getEmploymentDate().toString();
        }

        // Student
        if (user.getRole() == Role.STUDENT) {
            logger.info("Converting student attributes (enrollment date, major, minor)");
            Student student = (Student) user;
            this.enrollmentDate = student.getEnrollmentDate().toString();
            this.major = student.getMajor().toString();
            this.minor = student.getMinor().toString();
            for (Module module : student.getModulesTaken()) {
                modulesTaken.add(new XmlAdaptedModule(module));
            }
            for (Module module : student.getModulesStaged()) {
                modulesStaged.add(new XmlAdaptedModule(module));
            }
        }
    }

    /**
     * Converts this User into the model's {@code User} object.
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    public User toModelType(String password) throws IllegalValueException, CorruptedFileException,
            NoSuchPaddingException, InvalidPasswordException, NoSuchAlgorithmException, InvalidKeyException {
        User user = null;
        checkMandatoryFields();

        String decryptedUsername = decryptUsername(password);

        if ("ADMIN".equals(role)) {
            checkAdminFields();
            String decryptedSalary = decryptSalary(password);
            user = new Admin(new Username(decryptedUsername), new Name(name), Role.ADMIN, new Salary(decryptedSalary),
                    new EmployDate(employmentDate));
        }

        if ("STUDENT".equals(role)) {
            checkStudentFields();
            List<String> majorConverted = Arrays.asList(major.substring(1, major.length() - 1).split(", "));
            List<String> minorConverted = Arrays.asList(minor.substring(1, minor.length() - 1).split(", "));

            UniqueModuleList modulesTakenConverted = new UniqueModuleList();
            for (XmlAdaptedModule moduleTake : modulesTaken) {
                modulesTakenConverted.add(moduleTake.toModelType());
            }

            UniqueModuleList modulesStagedConverted = new UniqueModuleList();
            for (XmlAdaptedModule moduleTake : modulesStaged) {
                modulesStagedConverted.add(moduleTake.toModelType());
            }

            user = new Student(new Username(decryptedUsername), new Name(name), Role.STUDENT,
                    new EnrollmentDate(enrollmentDate),
                    majorConverted, minorConverted, modulesTakenConverted, modulesStagedConverted);
        }

        logger.info("Conversion to XmlAdaptedUser complete");
        return user;
    }

    /**
     * Decrypts Username
     * @param password
     * @return a string of decrypted username
     */
    private String decryptUsername(String password) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidPasswordException, CorruptedFileException, NoSuchPaddingException {
        return new String(DataSecurityUtil.decryptData(
                DataSecurityUtil.base64ToBytes(username), password), StandardCharsets.UTF_8);
    }

    /**
     * Decrypts Salary
     * @param password
     * @return a string of decrypted salary
     */
    private String decryptSalary(String password) throws NoSuchAlgorithmException, InvalidKeyException,
            InvalidPasswordException, CorruptedFileException, NoSuchPaddingException {
        return new String(DataSecurityUtil.decryptData(
                DataSecurityUtil.base64ToBytes(salary), password), StandardCharsets.UTF_8);
    }

    /**
     * Checks mandatory fields of user
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private void checkMandatoryFields() throws IllegalValueException {
        logger.info("Checking mandatory fields");
        // Username
        if (username == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "Username"));
        }

        // Name
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "name"));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }

        // Role
        if (role == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "role"));
        }

    }

    /**
     * Checks mandatory fields of admin
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private void checkAdminFields() throws IllegalValueException {
        logger.info("Checking admin fields");
        // Salary
        if (salary == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "salary"));
        }

        // employment date
        if (employmentDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "employment"));
        }
        if (!EmployDate.isValidEmployDate(employmentDate)) {
            throw new IllegalValueException(EmployDate.MESSAGE_DATE_CONSTRAINTS);
        }
    }

    /**
     * Checks mandatory fields of student
     *
     * @throws IllegalValueException if there were any data constraints violated
     */
    private void checkStudentFields() throws IllegalValueException {
        logger.info("Checking student fields");
        if (enrollmentDate == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "enrollment"));
        }
        if (!EnrollmentDate.isValidEnrollmentDate(enrollmentDate)) {
            throw new IllegalValueException(EnrollmentDate.MESSAGE_DATE_CONSTRAINTS);
        }
        if (major == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "major"));
        }
        if (minor == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, "minor"));
        }
    }

}
