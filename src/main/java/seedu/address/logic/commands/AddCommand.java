package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.Module;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one or several modules to your profile. "
            + "Parameters: "
            + "MOD_CODE "
            + "[MORE_MOD_CODE]...\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103T "
            + "CS2101";

    public static final String MESSAGE_SUCCESS = "New modules added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in your profile: %1$s";

    private final Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code modules}
     */
    public AddCommand(Module modules) {
        requireNonNull(modules);
        toAdd = modules;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
            if (model.hasModule(toAdd)) {
                throw new CommandException(MESSAGE_DUPLICATE_MODULE);
            }

            model.addModule(toAdd);
            model.commitAddressBook();
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddCommand) other).toAdd));
    }
}
