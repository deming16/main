package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SWITCH_TAB;

import java.util.stream.Stream;

import seedu.modsuni.logic.commands.SwitchTabCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SwitchTabCommand object
 */
public class SwitchTabCommandParser implements Parser<SwitchTabCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the SwitchTabCommand
     * and returns an SwitchTabCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SwitchTabCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_SWITCH_TAB);

        if (!arePrefixesPresent(argMultimap, PREFIX_SWITCH_TAB)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));
        }

        String switchToTab = ParserUtil.parseSwitchTab(argMultimap.getValue(PREFIX_SWITCH_TAB).get());

        return new SwitchTabCommand(switchToTab);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
