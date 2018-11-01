package seedu.modsuni.logic.commands;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;

/**
 * Command to allow Users to logout and reset the application
 * data.
 */
public class LogoutCommand extends Command{

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout from "
        + "the account of the current user. All user related data would be "
        + "reset.\n";

    public static final String MESSAGE_SUCCESS = "Logout Successfully!";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        return null;
    }
}
