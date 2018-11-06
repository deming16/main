package seedu.modsuni.logic;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.parser.exceptions.ParseException;

public class PrereqGeneratorTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void emptyString_noParseException() throws ParseException {
        PrereqGenerator.checkValidPrereqString("");
    }

    @Test
    public void onlyPrefix_noParseException() throws ParseException {
        PrereqGenerator.checkValidPrereqString("&");
    }

    @Test
    public void moduleWithComma_noParseException() throws ParseException {
        PrereqGenerator.checkValidPrereqString("&cs1010,cs2020,");
    }

    @Test
    public void parenthesisClosedWithList_noParseException() throws ParseException {
        PrereqGenerator.checkValidPrereqString("&cs1010,(|cs2020,cs2030,)");
    }

    @Test
    public void invalidCharacter_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("#$*");
    }

    @Test
    public void consecutiveAnd_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&&");
    }

    @Test
    public void consecutiveOr_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("||");
    }

    @Test
    public void consecutiveAndOr_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&|");
    }

    @Test
    public void consecutiveComma_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&cs1010,,");
    }

    @Test
    public void codeWithoutComma_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&cs1010");
    }

    @Test
    public void emptyParenthesis_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&()");
    }

    @Test
    public void emptyList_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&(&)");
    }

    @Test
    public void openParenLeft_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&(()");
    }

    @Test
    public void closeParenLeft_throwsParseException() throws ParseException {
        thrown.expect(ParseException.class);
        PrereqGenerator.checkValidPrereqString("&())");
    }
}
