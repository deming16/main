package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Optional;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.MainWindowClearResourceEvent;
import seedu.modsuni.commons.events.ui.NewCommandResultAvailableEvent;
import seedu.modsuni.commons.events.ui.NewGenerateResultAvailableEvent;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.Generate;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.ui.GenerateDisplay;

/**
 * Generates a schedule for the current student user.
 */
public class GenerateCommand extends Command {

    public static final String COMMAND_WORD = "generate";
    public static final String MESSAGE_SUCCESS = "Generate success!";
    public static final String MESSAGE_FAILURE = "Generate failed!";
    public static final String MESSAGE_INVALID_ROLE = "Only students can generate a schedule, please login"
            + " with a student account and try again";
    public static final String MESSAGE_NO_MODULES = "Ensure that you have added module(s) that you "
            + "would like to take before running the generate command";
    public static final String MESSAGE_ERROR = "Unable to generate. Please ensure that you are registered "
            + "or logged in.";

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_ERROR);
        }

        if (!model.isStudent()) {
            throw new CommandException(MESSAGE_INVALID_ROLE);
        }

        Student currentStudent = (Student) model.getCurrentUser();

        if (!currentStudent.hasModuleToTake()) {
            throw new CommandException(MESSAGE_NO_MODULES);
        }

        Optional<List<Code>> cannotTakeCodes = Generate.canGenerate(currentStudent);

        if (cannotTakeCodes.isPresent()) {
            return new CommandResult(MESSAGE_FAILURE + "\n" + cannotTakeCodes.get().toString());
        }

        Generate generate = new Generate(currentStudent);
        SemesterList semesterList = generate.getSchedule();

        EventsCenter.getInstance().post(new MainWindowClearResourceEvent());

        NewCommandResultAvailableEvent newCommandResultAvailableEvent = new NewCommandResultAvailableEvent();
        newCommandResultAvailableEvent.setToBeDisplayed(new GenerateDisplay());
        EventsCenter.getInstance().post(newCommandResultAvailableEvent);
        EventsCenter.getInstance().post(new NewGenerateResultAvailableEvent(semesterList));


        //NewCommandResultAvailableEvent armutEvent = new NewCommandResultAvailableEvent();
        //BrowserPanel browserPanel = new BrowserPanel(BrowserPanel.EASTER_EGG_PAGE);
        //armutEvent.setToBeDisplayed(browserPanel);
        //EventsCenter.getInstance().post(armutEvent);

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
