package seedu.modsuni.model.user;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.AppUtil.checkArgument;

/**
 * Represents a Admin's employment date;
 */
public class EmployDate {

    public static final String MESSAGE_DATE_CONSTRAINTS =
            "Employment date should be in this format: DD/MM/YYYY";

    public static final String DATE_VALIDATION_REGEX = "^(?:(?:31(\\/)"
        + "(?:0? [13578]|1[02]))\\1|(?:(?:29|30)(\\/)(?:0?[1,3-9]|1[0-2])\\2))"
        + "(?:(?:1[6-9]|[2-9]\\d)\\d{2})$|^(?:29(\\/)0?2\\3(?:(?:"
        + "(?:1[6-9]|[2-9]\\d)(?:0[48]|[2468][048]|[13579][26])|"
        + "(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/)"
        + "(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)\\d{2})$";

    public final String employDate;

    public EmployDate(String employDate) {
        requireNonNull(employDate);
        checkArgument(isValidEmployDate(employDate), MESSAGE_DATE_CONSTRAINTS);
        this.employDate = employDate;
    }

    public static boolean isValidEmployDate(String test) {
        return test.matches(DATE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return employDate;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EmployDate // instanceof handles nulls
                && employDate.equals(((EmployDate) other).employDate)); // state check
    }

    @Override
    public int hashCode() {
        return employDate.hashCode();
    }
}
