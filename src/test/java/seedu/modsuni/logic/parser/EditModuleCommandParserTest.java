package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.commands.CommandTestUtil.CODE_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.CREDIT_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.DEPARTMENT_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.DESCRIPTION_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.TITLE_DESC_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_CODE_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_CREDIT_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DEPARTMENT_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_DESCRIPTION_CS2109;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_TITLE_CS2109;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_FIRST_MODULE;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_SECOND_MODULE;
import static seedu.modsuni.testutil.TypicalIndexes.INDEX_THIRD_MODULE;

import org.junit.Test;

import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.logic.commands.EditModuleCommand;
import seedu.modsuni.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.modsuni.testutil.EditModuleDescriptorBuilder;

public class EditModuleCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditModuleCommand.MESSAGE_USAGE);

    private EditModuleCommandParser parser = new EditModuleCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_CS2109, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditModuleCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TITLE_CS2109, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_TITLE_CS2109, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_MODULE;
        String userInput = targetIndex.getOneBased() + CODE_DESC_CS2109 + TITLE_DESC_CS2109
                + DEPARTMENT_DESC_CS2109 + DESCRIPTION_DESC_CS2109 + CREDIT_DESC_CS2109;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withCode(VALID_CODE_CS2109)
                .withDepartment(VALID_DEPARTMENT_CS2109).withDescription(VALID_DESCRIPTION_CS2109)
                .withTitle(VALID_TITLE_CS2109).withCredit(Integer.parseInt(VALID_CREDIT_CS2109)).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + DEPARTMENT_DESC_CS2109 + TITLE_DESC_CS2109;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withDepartment(VALID_DEPARTMENT_CS2109)
                .withTitle(VALID_TITLE_CS2109).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // code
        Index targetIndex = INDEX_THIRD_MODULE;
        String userInput = targetIndex.getOneBased() + CODE_DESC_CS2109;
        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withCode(VALID_CODE_CS2109).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // department
        userInput = targetIndex.getOneBased() + DEPARTMENT_DESC_CS2109;
        descriptor = new EditModuleDescriptorBuilder().withDepartment(VALID_DEPARTMENT_CS2109).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_CS2109;
        descriptor = new EditModuleDescriptorBuilder().withDescription(VALID_DESCRIPTION_CS2109).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // title
        userInput = targetIndex.getOneBased() + TITLE_DESC_CS2109;
        descriptor = new EditModuleDescriptorBuilder().withTitle(VALID_TITLE_CS2109).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // credit
        userInput = targetIndex.getOneBased() + CREDIT_DESC_CS2109;
        descriptor = new EditModuleDescriptorBuilder().withCredit(Integer.parseInt(VALID_CREDIT_CS2109)).build();
        expectedCommand = new EditModuleCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_MODULE;
        String userInput = targetIndex.getOneBased() + CODE_DESC_CS2109 + DESCRIPTION_DESC_CS2109 + TITLE_DESC_CS2109
                + DESCRIPTION_DESC_CS2109 + TITLE_DESC_CS2109 + CODE_DESC_CS2109;

        EditModuleDescriptor descriptor = new EditModuleDescriptorBuilder().withCode(VALID_CODE_CS2109)
                .withTitle(VALID_TITLE_CS2109).withDescription(VALID_DESCRIPTION_CS2109).build();
        EditModuleCommand expectedCommand = new EditModuleCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
