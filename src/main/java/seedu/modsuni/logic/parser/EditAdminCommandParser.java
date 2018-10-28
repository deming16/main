package seedu.modsuni.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SALARY;

import seedu.modsuni.logic.commands.EditAdminCommand;
import seedu.modsuni.logic.commands.EditAdminCommand.EditAdminDescriptor;
import seedu.modsuni.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditAdminCommand object.
 */
public class EditAdminCommandParser implements Parser<EditAdminCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditAdminCommand and returns an EditAdminCommand object for
     * execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditAdminCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_SALARY, PREFIX_EMPLOYMENT_DATE);
        EditAdminDescriptor editAdminDescriptor = new EditAdminDescriptor();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            editAdminDescriptor.setName(
                    ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get()));
        }

        if (argMultimap.getValue(PREFIX_SALARY).isPresent()) {
            editAdminDescriptor.setSalary(
                    ParserUtil.parseSalary(argMultimap.getValue(PREFIX_SALARY).get()));
        }

        if (argMultimap.getValue(PREFIX_EMPLOYMENT_DATE).isPresent()) {
            editAdminDescriptor.setEmploymentDate(
                    ParserUtil.parseEmployDate(argMultimap.getValue(PREFIX_EMPLOYMENT_DATE).get()));
        }
        if (!editAdminDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditAdminCommand.MESSAGE_NOT_EDITED);
        }
        return new EditAdminCommand(editAdminDescriptor);
    }
}
