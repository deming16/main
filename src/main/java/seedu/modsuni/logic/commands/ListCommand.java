package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.model.Model.PREDICATE_SHOW_ALL_MODULES;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.ShowDatabaseTabRequestEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.model.Model;

/**
 * Lists all persons in the modsuni book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all modules in database";


    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);
        model.updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);

        EventsCenter.getInstance().post(new ShowDatabaseTabRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
