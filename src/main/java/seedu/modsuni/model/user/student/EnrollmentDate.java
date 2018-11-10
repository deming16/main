package seedu.modsuni.model.user.student;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.AppUtil.checkArgument;

/**
 * Represents a Student's enrollment date;
 */
public class EnrollmentDate {
    public static final String MESSAGE_DATE_CONSTRAINTS =
        "Enrollment date should be in this format: DD/MM/YYYY";

    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)\\d{2})$";

    public final String enrollmentDate;

    public EnrollmentDate(String enrollmentDate) {
        requireNonNull(enrollmentDate);
        checkArgument(isValidEnrollmentDate(enrollmentDate),
            MESSAGE_DATE_CONSTRAINTS);
        this.enrollmentDate = enrollmentDate;
    }

    public static boolean isValidEnrollmentDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return enrollmentDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof EnrollmentDate // instanceof handles nulls
            && enrollmentDate.equals(((EnrollmentDate) other).enrollmentDate)); //
    }

    @Override
    public int hashCode() {
        return enrollmentDate.hashCode();
    }
}
