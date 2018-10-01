package seedu.address.logic.parser;

import seedu.address.logic.commands.AddOnCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.Module;
/**
 * Parses input arguments and creates a new AddOnCommand object
 */
public class AddCommandParser implements Parser<AddOnCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddOnCommand
     * and returns an AddOnCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddOnCommand parse(String args) throws ParseException {
        Module module = new Module(args.toUpperCase(), null, null, null,
                0, true, true, true, true);
        return new AddOnCommand(module);
    }

}
