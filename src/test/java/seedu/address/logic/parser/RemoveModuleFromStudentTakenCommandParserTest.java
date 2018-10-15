package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.RemoveModuleFromStudentTakenCommand;

public class RemoveModuleFromStudentTakenCommandParserTest {
    private RemoveModuleFromStudentTakenCommandParser parser = new RemoveModuleFromStudentTakenCommandParser();

    @Test
    public void parse_emptyCode_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemoveModuleFromStudentTakenCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "", expectedMessage);
    }

}
