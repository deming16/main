package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SWITCH_TAB;

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
            + PREFIX_SWITCH_TAB + "TAB\n"
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_SWITCH_TAB + "user";

    //user, staged, taken, database

    public static final String MESSAGE_SUCCESS = "Tab successfully switched to " + "%1$s";

    public static final String MESSAGE_ERROR = "Unable to switch tab.";

    private final String switchToTab;

    public SwitchTabCommand(String switchToTab) {
        requireAllNonNull(switchToTab);
        this.switchToTab = switchToTab;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

//        EventsCenter.getInstance().post(new ShowStagedTabRequestEvent());
//        EventsCenter.getInstance().post(new ShowUserTabRequestEvent());
//        EventsCenter.getInstance().post(new ShowTakenTabRequestEvent());
//        EventsCenter.getInstance().post(new ShowDatabaseTabRequestEvent());
        
        return new CommandResult(String.format(MESSAGE_SUCCESS, switchToTab));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchTabCommand // instanceof handles nulls
                && switchToTab.equals(((SwitchTabCommand) other).switchToTab));
    }
}
