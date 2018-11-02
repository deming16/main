package seedu.modsuni.testutil;

import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PASSWORD;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_PATH;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERDATA;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;

import seedu.modsuni.logic.commands.LoginCommand;
import seedu.modsuni.model.credential.Credential;

/**
 * A utility class for Admin.
 */
public class CredentialUtil {

    /**
     * Returns a login command string.
     */
    public static String getLoginCommand(Credential credential) {

        return LoginCommand.COMMAND_WORD + " "
            + PREFIX_USERNAME + credential.getUsername().toString() + " "
            + PREFIX_PASSWORD + VALID_PASSWORD + " "
            + PREFIX_USERDATA + VALID_PATH;
    }
}
