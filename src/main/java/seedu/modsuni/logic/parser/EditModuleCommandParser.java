package seedu.modsuni.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_AVAILABLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CREDIT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DEPARTMENT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DESCRIPTION;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_PREREQ;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.commands.EditModuleCommand;
import seedu.modsuni.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.module.Code;

/**
 * Parses input arguments and creates a new EditModuleCommand obejct.
 */
public class EditModuleCommandParser implements Parser<EditModuleCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * EditAdminCommand and returns an EditAdminCommand object for
     * execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public EditModuleCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE, PREFIX_MODULE_DEPARTMENT, PREFIX_MODULE_TITLE,
                        PREFIX_MODULE_DESCRIPTION, PREFIX_MODULE_CREDIT, PREFIX_MODULE_AVAILABLE, PREFIX_MODULE_PREREQ);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditModuleCommand.MESSAGE_USAGE), pe);
        }
        EditModuleDescriptor editModuleDescriptor = new EditModuleDescriptor();
        setUpEditModuleDescriptor(editModuleDescriptor, argMultimap);
        if (!editModuleDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditModuleCommand.MESSAGE_NOT_EDITED);
        }
        return new EditModuleCommand(index, editModuleDescriptor);
    }
    private void setUpEditModuleDescriptor(EditModuleDescriptor editModuleDescriptor,
                                                ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_MODULE_CODE).isPresent()) {
            editModuleDescriptor.setCode(
                    new Code(ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULE_CODE).get())));
        }
        if (argMultimap.getValue(PREFIX_MODULE_DEPARTMENT).isPresent()) {
            editModuleDescriptor.setDeparment(argMultimap.getValue(PREFIX_MODULE_DEPARTMENT).get());
        }

        if (argMultimap.getValue(PREFIX_MODULE_DESCRIPTION).isPresent()) {
            editModuleDescriptor.setDescription(argMultimap.getValue(PREFIX_MODULE_DESCRIPTION).get());
        }

        if (argMultimap.getValue(PREFIX_MODULE_TITLE).isPresent()) {
            editModuleDescriptor.setTitle(argMultimap.getValue(PREFIX_MODULE_TITLE).get());
        }

        if (argMultimap.getValue(PREFIX_MODULE_CREDIT).isPresent()) {
            editModuleDescriptor.setCredit(Integer.parseInt(argMultimap.getValue(PREFIX_MODULE_CREDIT).get()));
        }

        if (argMultimap.getValue(PREFIX_MODULE_AVAILABLE).isPresent()) {
            editModuleDescriptor.setSems(getAvailableSems(argMultimap.getValue(PREFIX_MODULE_DEPARTMENT).get()));
        }
        if (argMultimap.getValue(PREFIX_MODULE_PREREQ).isPresent()) {
            editModuleDescriptor
                    .setPrereq(ParserUtil.parsePrereq(argMultimap.getValue(PREFIX_MODULE_DEPARTMENT).get()));
        }
    }
    /**
     * Converts the given {@code String} of available semester into its respective booleans.
     */
    private boolean[] getAvailableSems(String sem) {
        boolean[] sems = new boolean[4];

        for (int i = 0; i < sems.length; i++) {

            if (sem.charAt(i) == '1') {
                sems[i] = true;
            } else {
                sems[i] = false;
            }
        }

        return sems;
    }
}
