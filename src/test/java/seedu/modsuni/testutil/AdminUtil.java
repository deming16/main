package seedu.modsuni.testutil;

import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SALARY;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SAVE_PATH;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.modsuni.logic.commands.AddAdminCommand;
import seedu.modsuni.model.user.Admin;

/**
 * A utility class for Admin.
 */
public class AdminUtil {

    /**
     * Returns an addAdmin command string for adding the {@code admin}.
     */
    public static String getAddAdminCommand(Admin admin) {
        return AddAdminCommand.COMMAND_WORD + " "
                + PREFIX_USERNAME + admin.getUsername().getUsername() + " "
                + PREFIX_PASSWORD + VALID_PASSWORD + " "
                + getAdminDetails(admin);
    }

    /**
     * Returns the part of command string for the given {@code admin}'s details.
     */
    public static String getAdminDetails(Admin admin) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + admin.getName().fullName + " ");
        sb.append(PREFIX_SAVE_PATH + "seb5.xml" + " ");
        sb.append(PREFIX_SALARY + admin.getSalary().salary + " ");
        sb.append(PREFIX_EMPLOYMENT_DATE + admin.getEmploymentDate().employDate + " ");
        return sb.toString();
    }
}
