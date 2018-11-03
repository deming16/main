package seedu.modsuni.logic.parser;

import static seedu.modsuni.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PASSWORD;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_PATH_TO_PIC;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_ENROLLMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MAJOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_STUDENT_MINOR;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_USERNAME;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.stream.Stream;

import seedu.modsuni.commons.util.DataSecurityUtil;
import seedu.modsuni.logic.commands.RegisterCommand;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.logic.parser.exceptions.ParseException;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.PathToProfilePic;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.EnrollmentDate;
import seedu.modsuni.model.user.student.Student;

/**
 * Parses input arguments and creates a new RegisterCommand object
 */
public class RegisterCommandParser implements Parser<RegisterCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the
     * RegisterCommand and returns an RegisterCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public RegisterCommand parse(String userInput) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(userInput, PREFIX_USERNAME,
                PREFIX_PASSWORD, PREFIX_NAME, PREFIX_PATH_TO_PIC,
                PREFIX_STUDENT_ENROLLMENT_DATE, PREFIX_STUDENT_MAJOR, PREFIX_STUDENT_MINOR);

        if (!arePrefixesPresent(argMultimap, PREFIX_USERNAME, PREFIX_PASSWORD,
            PREFIX_NAME, PREFIX_PATH_TO_PIC, PREFIX_STUDENT_ENROLLMENT_DATE,
            PREFIX_STUDENT_MAJOR)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        Username username = ParserUtil.parseUsername(argMultimap.getValue(PREFIX_USERNAME).get());
        Password password = ParserUtil.parsePassword(argMultimap.getValue(PREFIX_PASSWORD).get());
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        PathToProfilePic pathToPic =
            ParserUtil.parsePathToProfilePic(argMultimap.getValue(PREFIX_PATH_TO_PIC).get());
        EnrollmentDate enrollmentDate = ParserUtil.parseEnrollmentDate(
            argMultimap.getValue(PREFIX_STUDENT_ENROLLMENT_DATE).get());
        List<String> majors = argMultimap.getAllValues(PREFIX_STUDENT_MAJOR);
        List<String> minors = argMultimap.getAllValues(PREFIX_STUDENT_MINOR);
        User newUser = new Student(username, name, Role.STUDENT, pathToPic,
            enrollmentDate, majors, minors);

        Path tempSavePath;
        try {
            tempSavePath = generateTempSavePath(newUser);
        } catch (CommandException e) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, RegisterCommand.MESSAGE_USAGE));
        }

        //TODO key to be removed
        Credential credential = new Credential(
            username,
            password);

        return new RegisterCommand(credential, newUser, tempSavePath);

    }

    /**
     * Generates a temporary save Path
     */
    public Path generateTempSavePath(User user) throws CommandException {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(user.getUsername().getUsername());
        pathBuilder.append("_");
        try {
            pathBuilder.append(DataSecurityUtil.randomSha1());
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(RegisterCommand.MESSAGE_REGISTER_FAILURE);
        }
        pathBuilder.append(".xml");
        return Paths.get(pathBuilder.toString());
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
