package seedu.modsuni.logic.parser;

import static seedu.modsuni.logic.commands.CommandTestUtil.EMPLOY_DATE_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_EMPLOY_DATE_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.INVALID_SALARY_DESC;
import static seedu.modsuni.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.SALARY_DESC_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_EMPLOY_DATE_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.modsuni.logic.commands.CommandTestUtil.VALID_SALARY_AMY;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.modsuni.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.modsuni.logic.commands.EditAdminCommand;
import seedu.modsuni.logic.commands.EditAdminCommand.EditAdminDescriptor;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Salary;
import seedu.modsuni.testutil.EditAdminDescriptorBuilder;

public class EditAdminCommandParserTest {

    private EditAdminCommandParser parser = new EditAdminCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = NAME_DESC_AMY
                + SALARY_DESC_AMY
                + EMPLOY_DATE_DESC_AMY;

        EditAdminDescriptor descriptor = new EditAdminDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withSalary(VALID_SALARY_AMY)
                .withEmploymentDate(VALID_EMPLOY_DATE_AMY)
                .build();

        EditAdminCommand expectedCommand = new EditAdminCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsPresent_success() {
        String userInput = NAME_DESC_AMY
                + SALARY_DESC_AMY;

        EditAdminDescriptor descriptor = new EditAdminDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withSalary(VALID_SALARY_AMY)
                .build();

        EditAdminCommand expectedCommand = new EditAdminCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldsPresent_success() {
        String userInput = NAME_DESC_AMY;

        EditAdminDescriptor descriptor = new EditAdminDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .build();

        EditAdminCommand expectedCommand = new EditAdminCommand(descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidFields_failure() {
        assertParseFailure(parser, INVALID_NAME_DESC, Name.MESSAGE_NAME_CONSTRAINTS);
        assertParseFailure(parser, INVALID_SALARY_DESC, Salary.MESSAGE_SALARY_CONSTRAINTS);
        assertParseFailure(parser, INVALID_EMPLOY_DATE_DESC, EmployDate.MESSAGE_DATE_CONSTRAINTS);
    }
}
