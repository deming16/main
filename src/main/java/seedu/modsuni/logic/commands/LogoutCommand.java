package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.UserTabLogoutEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;

/**
 * Command to allow Users to logout and reset the application
 * data.
 */
public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Logout from "
        + "the account of the current user. All user related data would be "
        + "reset.\n";

    public static final String MESSAGE_SUCCESS = "Logout Successfully!";

    public static final String MESSAGE_NOT_LOGGED_IN = "You are not logged in.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }
        model.resetCurrentUser();
        history.resetHistory();
        EventsCenter.getInstance().post(new UserTabLogoutEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
