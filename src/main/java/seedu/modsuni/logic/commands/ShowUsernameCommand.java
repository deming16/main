package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.NewCommandResultAvailableEvent;
import seedu.modsuni.commons.events.ui.NewShowUsernameResultAvailableEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.ui.UsernameDisplay;


/**
 * Show all the usernames in modsuni to the admin.
 */
public class ShowUsernameCommand extends Command {

    public static final String COMMAND_WORD = "showUser";

    public static final String MESSAGE_SUCCESS = "listed all usernames";

    public static final String MESSAGE_NOT_ADMIN = "Only an admin can execute this command";
    public static final String MESSAGE_NOT_LOGGED_IN = "Unable to show, please log in first";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        if (model.getCurrentUser().getRole() != Role.ADMIN) {
            throw new CommandException(MESSAGE_NOT_ADMIN);
        }

        NewCommandResultAvailableEvent newCommandResultAvailableEvent =
                new NewCommandResultAvailableEvent();
        newCommandResultAvailableEvent.setToBeDisplayed(new UsernameDisplay());

        EventsCenter.getInstance().post(newCommandResultAvailableEvent);

        EventsCenter.getInstance().post(new NewShowUsernameResultAvailableEvent(model.getUsernames()));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
