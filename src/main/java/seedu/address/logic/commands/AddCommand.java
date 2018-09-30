package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import java.util.Optional;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.Module;

/**
 * Adds a person to the address book.
 */
public class AddCommand extends Command {

    public static final String COMMAND_WORD = "add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds one module to your profile. "
            + "Parameters: "
            + "MOD_CODE(case insensitive)\n"
            + "Example: " + COMMAND_WORD + " "
            + "CS2103T ";

    public static final String MESSAGE_SUCCESS = "New module added: %1$s";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exists in your profile: %1$s";
    public static final String MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE = "This module does not exist in our database";

    private Module toAdd;

    /**
     * Creates an AddCommand to add the specified {@code module}
     */
    public AddCommand(Module module) {
        requireNonNull(module);
        toAdd = module;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);
        Optional<Module> optionalModule = model.searchModuleInModuleList(toAdd);
        if(!optionalModule.isPresent()) {
            toAdd = optionalModule.get();
        } else {
            throw new CommandException(MESSAGE_MODULE_NOT_EXISTS_IN_DATABASE);
        }
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
