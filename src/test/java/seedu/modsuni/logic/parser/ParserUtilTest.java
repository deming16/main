package seedu.modsuni.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.modsuni.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Salary;
import seedu.modsuni.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";

    private static final String INVALID_SALARY = "OneHundred";
    private static final String INVALID_EMPLOY_DATE = "123456";
    private static final String INVALID_USERNAME = "with space";
    private static final String INVALID_CODE = "$%^&";
    private static final String INVALID_PREREQ_SPECIAL_CHARACTER = "$%";
    private static final String INVALID_PREREQ_CONSECUTIVE_PREFIX = "&&";
    private static final String INVALID_PREREQ_NO_COMMA = "&CS1010";
    private static final String INVALID_PREREQ_EMPTY_PAREN = "&cs1010,()";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_SALARY = "3000";
    private static final String VALID_EMPLOY_DATE = "01/01/2018";
    private static final String VALID_USERNAME = "username";
    private static final String VALID_CODE = "CS1010";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName(null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parseSalary_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSalary(null));
    }

    @Test
    public void parseSalary_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSalary(INVALID_SALARY));
    }

    @Test
    public void parseSalary_validValue_returnsSalary() throws Exception {
        Salary expectedSalary = new Salary(VALID_SALARY);
        assertEquals(expectedSalary, ParserUtil.parseSalary(VALID_SALARY));
    }

    @Test
    public void parseEmployDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseEmployDate(null));
    }

    @Test
    public void parseEmployDate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseEmployDate(INVALID_EMPLOY_DATE));
    }

    @Test
    public void parseEmployDate_validValue_returnsEmployDate() throws Exception {
        EmployDate expectedEmployDate = new EmployDate(VALID_EMPLOY_DATE);
        assertEquals(expectedEmployDate, ParserUtil.parseEmployDate(VALID_EMPLOY_DATE));
    }

    @Test
    public void parseUsername_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseUsername(null));
    }

    @Test
    public void parseUsername_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseUsername(INVALID_USERNAME));
    }

    @Test
    public void parseUsername_validValue_returnsUsername() throws Exception {
        Username expectedUsername = new Username(VALID_USERNAME);
        assertEquals(expectedUsername, ParserUtil.parseUsername(VALID_USERNAME));
    }

    @Test
    public void parseModuleCode_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseModuleCode(INVALID_CODE));
    }

    @Test
    public void parseModuleCode_validValue_returnsCode() throws Exception {
        Code expectedCode = new Code(VALID_CODE);
        assertEquals(expectedCode, ParserUtil.parseModuleCode(VALID_CODE));
    }

    @Test
    public void parsePrereq_specialChar_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrereq(INVALID_PREREQ_SPECIAL_CHARACTER));
    }

    @Test
    public void parsePrereq_consecutivePrefix_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrereq(INVALID_PREREQ_CONSECUTIVE_PREFIX));
    }

    @Test
    public void parsePrereq_noComma_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrereq(INVALID_PREREQ_NO_COMMA));
    }

    @Test
    public void parsePrereq_emptyParen_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrereq(INVALID_PREREQ_EMPTY_PAREN));
    }
}
