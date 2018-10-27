package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
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

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.UserTabChangedEvent;
import seedu.modsuni.commons.util.DataSecurityUtil;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.user.User;


/**
 * Adds a new Credential to the Credential Store.
 */
public class RegisterCommand extends Command {

    public static final String COMMAND_WORD = "register";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Registers a new Student Account. "
        + "Parameters: "
        + PREFIX_USERNAME + "USERNAME "
        + PREFIX_PASSWORD + "PASSWORD "
        + PREFIX_NAME + "NAME "
        + PREFIX_PATH_TO_PIC + "PATH "
        + PREFIX_STUDENT_ENROLLMENT_DATE + "DD/MM/YYYY "
        + PREFIX_STUDENT_MAJOR + "MAJORCODE_1 "
        + PREFIX_STUDENT_MAJOR + "MAJORCODE_2 "
        + PREFIX_STUDENT_MINOR + "MINORCODE_1 "
        + PREFIX_STUDENT_MINOR + "MINORCODE_2";

    public static final String MESSAGE_SUCCESS = "New Account created added: "
        + "%1$s\n"
        + "Temp Save Path: %2$s\n";
    public static final String MESSAGE_DUPLICATE_USERNAME = "This username already exists in the database";
    public static final String MESSAGE_ALREADY_LOGGED_IN = "You are already "
        + "logged in";
    public static final String MESSAGE_REGISTER_FAILURE = "Registering failed. "
        + "Please try again.";

    private final Credential toRegister;
    private final User user;

    /**
     * Creates an RegisterCommand to add the specified {@code Credential}
     */
    public RegisterCommand(Credential newCredential, User newUser) {
        requireAllNonNull(newCredential, newUser);
        toRegister = newCredential;
        user = newUser;
    }

    /**
     * Generates a temporary save Path
     */
    private Path generateTempSavePath() throws CommandException {
        StringBuilder pathBuilder = new StringBuilder();
        pathBuilder.append(user.getUsername().getUsername());
        pathBuilder.append("_");
        try {
            pathBuilder.append(DataSecurityUtil.randomSha1());
        } catch (NoSuchAlgorithmException e) {
            throw new CommandException(MESSAGE_REGISTER_FAILURE);
        }
        pathBuilder.append(".xml");
        return Paths.get(pathBuilder.toString());
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentUser() != null) {
            throw new CommandException(MESSAGE_ALREADY_LOGGED_IN);
        }

        if (model.hasCredential(toRegister)) {
            throw new CommandException(MESSAGE_DUPLICATE_USERNAME);
        }

        model.addCredential(toRegister);
        model.setCurrentUser(user);

        Path tempSavePath = generateTempSavePath();

        EventsCenter.getInstance().post(new UserTabChangedEvent(model.getCurrentUser()));
        model.saveUserFile(model.getCurrentUser(), tempSavePath);
        return new CommandResult(String.format(MESSAGE_SUCCESS, user, tempSavePath));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof RegisterCommand // instanceof handles nulls
            && toRegister.equals(((RegisterCommand) other).toRegister))
            && user.equals(((RegisterCommand) other).user);
    }

}
