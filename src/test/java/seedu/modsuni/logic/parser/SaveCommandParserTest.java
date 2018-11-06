package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_SAVE_PATH;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SAVE_PATH;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SAVE_PATH_NAME;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.nio.file.Paths;

import org.junit.Test;

import seedu.modsuni.logic.commands.SaveCommand;

public class SaveCommandParserTest {
    private SaveCommandParser parser = new SaveCommandParser();

    @Test
    public void parse_validArgs_returnsSaveCommand() {
        SaveCommand expectedSaveCommand = new SaveCommand(Paths.get(VALID_SAVE_PATH_NAME));
        assertParseSuccess(parser, VALID_SAVE_PATH, expectedSaveCommand);
    }

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "   ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid path
        assertParseFailure(parser, INVALID_SAVE_PATH,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SaveCommand.MESSAGE_USAGE));
    }

}
