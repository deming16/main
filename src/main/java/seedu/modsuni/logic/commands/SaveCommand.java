package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SAVE_PATH;

import java.nio.file.Path;
import java.util.logging.Logger;

import seedu.modsuni.MainApp;
import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.events.ui.NewSaveResultAvailableEvent;
import seedu.modsuni.commons.events.ui.SaveDisplayRequestEvent;
import seedu.modsuni.commons.events.ui.UserTabChangedEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.User;

/**
 * Saves the current user.
 */
public class SaveCommand extends Command {

    public static final String COMMAND_WORD = "save";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Saves current user data to a specific path. "
            + "Parameters: "
            + PREFIX_SAVE_PATH + "FILEPATH\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SAVE_PATH + "userdata.xml";

    public static final String MESSAGE_SUCCESS = "Current user data has been saved!";

    public static final String MESSAGE_ERROR = "Unable to save. Please ensure that you are registered or logged in.";

    private final Path savePath;

    private static final Logger logger = LogsCenter.getLogger(MainApp.class);

    public SaveCommand(Path savePath) {
        requireAllNonNull(savePath);
        this.savePath = savePath;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        User currentUser = model.getCurrentUser();

        if (currentUser == null) {
            logger.warning("User is not logged in");
            throw new CommandException(MESSAGE_ERROR);
        }

        model.saveUserFile(currentUser, savePath);

        EventsCenter.getInstance().post(new SaveDisplayRequestEvent());
        EventsCenter.getInstance().post(new NewSaveResultAvailableEvent(model.getCurrentUser()));
        EventsCenter.getInstance().post(new UserTabChangedEvent(model.getCurrentUser()));

        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SaveCommand // instanceof handles nulls
                && savePath.equals(((SaveCommand) other).savePath));
    }
}
