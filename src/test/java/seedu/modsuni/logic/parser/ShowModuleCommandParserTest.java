package seedu.modsuni.logic.parser;

import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.commands.ShowModuleCommand;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.module.Code;

public class ShowModuleCommandParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ShowModuleCommandParser parser = new ShowModuleCommandParser();

    @Test
    public void parse_validArgs_returnsShowModuleCommand() {
        assertParseSuccess(parser, "CS1010", new ShowModuleCommand(new Code("CS1010")));
    }

    @Test
    public void constructor_nullArgs_throwsNullPointerException() throws ParseException {
        thrown.expect(NullPointerException.class);
        parser.parse(null);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, " ", Code.MESSAGE_CODE_CONSTRAINTS);
        assertParseFailure(parser, "CS2103 T", Code.MESSAGE_CODE_CONSTRAINTS);
    }
}
