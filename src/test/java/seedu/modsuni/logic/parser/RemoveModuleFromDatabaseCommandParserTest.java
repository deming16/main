package seedu.modsuni.logic.parser;

import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.commands.RemoveModuleFromDatabaseCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;

public class RemoveModuleFromDatabaseCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private RemoveModuleFromDatabaseCommandParser parser = new RemoveModuleFromDatabaseCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveModuleFromDatabaseCommand() {
        assertParseSuccess(parser, "CS1010", new RemoveModuleFromDatabaseCommand("CS1010"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        parser.parse("#CS1010");
    }
}
