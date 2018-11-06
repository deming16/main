package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_TAB;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_TAB;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SWITCH_TAB;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.modsuni.logic.commands.SwitchTabCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;

public class SwitchTabCommandParserTest {
    private SwitchTabCommandParser parser = new SwitchTabCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws ParseException {
        String switchToTab = ParserUtil.parseSwitchTab(VALID_TAB);
        String validInput = " " + PREFIX_SWITCH_TAB + VALID_TAB;
        SwitchTabCommand expectedCommand = new SwitchTabCommand(switchToTab);

        assertParseSuccess(parser, validInput , expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SwitchTabCommand.MESSAGE_USAGE);

        // missing tab prefix
        assertParseFailure(parser, VALID_TAB, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid tab
        assertParseFailure(parser, INVALID_TAB,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SwitchTabCommand.MESSAGE_USAGE));

    }
}
