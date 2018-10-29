package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.modsuni.logic.commands.RemoveUserCommand;
import seedu.modsuni.model.credential.Username;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveUserCommandParserTest {

    private RemoveUserCommandParser parser = new RemoveUserCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveUserCommand() {
        assertParseSuccess(parser, "username", new RemoveUserCommand(new Username("username")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "no space", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveUserCommand.MESSAGE_USAGE));
    }
}
