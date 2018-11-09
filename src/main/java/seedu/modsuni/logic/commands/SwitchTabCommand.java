package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SWITCH_TAB;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.ShowDatabaseTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowStagedTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowTakenTabRequestEvent;
import seedu.modsuni.commons.events.ui.ShowUserTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;

/**
 * Switches to a specific tab.
 */
public class SwitchTabCommand extends Command {

    public static final String COMMAND_WORD = "switch";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Switches tabs using command line. "
            + "Parameters: "
            + PREFIX_SWITCH_TAB + "[user/staged/taken/database]\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SWITCH_TAB + "user";

    //user, staged, taken, database

    public static final String MESSAGE_SUCCESS = "Successfully switched to " + "%1$s" + " tab";

    public static final String MESSAGE_INVALID_OPTION = "Invalid option.\n" + MESSAGE_USAGE;

    private final String switchToTab;

    private static final String USER_TAB = "user";
    private static final String STAGED_TAB = "staged";
    private static final String TAKEN_TAB = "taken";
    private static final String DATABASE_TAB = "database";

    public SwitchTabCommand(String switchToTab) {
        requireAllNonNull(switchToTab);
        this.switchToTab = switchToTab;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        switch(switchToTab) {
        case USER_TAB:
            EventsCenter.getInstance().post(new ShowUserTabRequestEvent());
            break;
        case STAGED_TAB:
            EventsCenter.getInstance().post(new ShowStagedTabRequestEvent());
            break;
        case TAKEN_TAB:
            EventsCenter.getInstance().post(new ShowTakenTabRequestEvent());
            break;
        case DATABASE_TAB:
            EventsCenter.getInstance().post(new ShowDatabaseTabRequestEvent());
            break;
        default:
            throw new CommandException(MESSAGE_INVALID_OPTION);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, switchToTab));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchTabCommand // instanceof handles nulls
                && switchToTab.equals(((SwitchTabCommand) other).switchToTab));
    }
}
