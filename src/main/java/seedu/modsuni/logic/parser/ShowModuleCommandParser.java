package seedu.modsuni.logic.parser;

import seedu.modsuni.logic.commands.ShowModuleCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.module.Code;

/**
 * Parses input arguments and creates a new ShowModuleCommand object
 */
public class ShowModuleCommandParser implements Parser<ShowModuleCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SelectCommand
     * and returns an SelectCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ShowModuleCommand parse(String args) throws ParseException {
        String inputModuleCode = args.toUpperCase().trim();

        if (!Code.isValidCode(inputModuleCode)) {
            throw new ParseException(Code.MESSAGE_CODE_CONSTRAINTS);
        } else {
            return new ShowModuleCommand(new Code(inputModuleCode));
        }
    }
}
