package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_EMPLOYMENT_DATE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_SALARY;

import java.util.Optional;

import seedu.modsuni.commons.core.EventsCenter;
import seedu.modsuni.commons.events.ui.UserTabChangedEvent;
import seedu.modsuni.commons.util.CollectionUtil;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.Salary;

/**
 * Command to allow students to edit their profiles.
 */
public class EditAdminCommand extends Command {

    public static final String COMMAND_WORD = "editAdmin";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit current"
            + " admin account with the given parameters.\n"
            + "Parameters:\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_SALARY + "SALARY] "
            + "[" + PREFIX_EMPLOYMENT_DATE + "DD/MM/YYYY]\n"
            + "Example: " + COMMAND_WORD
            + PREFIX_NAME + "Max Emilian Verstappen "
            + PREFIX_EMPLOYMENT_DATE + "11/03/2015";

    public static final String MESSAGE_EDIT_ADMIN_SUCCESS = "Edit "
            + "Successfully!\n%1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to "
            + "edit must be provided.";

    public static final String MESSAGE_NOT_LOGGED_IN = "Unable to edit, please log in first.";

    public static final String MESSAGE_NOT_ADMIN = "Only an admin user can execute this command";

    private final EditAdminDescriptor editAdminDescriptor;

    public EditAdminCommand(EditAdminDescriptor editAdminDescriptor) {
        requireAllNonNull(editAdminDescriptor);
        this.editAdminDescriptor = editAdminDescriptor;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) throws CommandException {
        requireNonNull(model);

        if (model.getCurrentUser() == null) {
            throw new CommandException(MESSAGE_NOT_LOGGED_IN);
        }

        if (model.getCurrentUser().getRole() != Role.ADMIN) {
            throw new CommandException(MESSAGE_NOT_ADMIN);
        }

        Admin editedAdmin =
                createEditedStudent((Admin) model.getCurrentUser(),
                        editAdminDescriptor);

        model.setCurrentUser(editedAdmin);
        EventsCenter.getInstance().post(new UserTabChangedEvent(editedAdmin));
        return new CommandResult(String.format(MESSAGE_EDIT_ADMIN_SUCCESS,
                editedAdmin.toString()));
    }

    /**
     * Creates and returns a {@code Admin} with the details of {@code
     * adminToEdit }
     * edited with {@code editAdminDescriptor}.
     */
    private static Admin createEditedStudent(Admin adminToEdit,
                                               EditAdminDescriptor editAdminDescriptor) {
        assert adminToEdit != null;

        Name updatedName =
                editAdminDescriptor.getName().orElse(adminToEdit.getName());
        Salary updatedSalary =
                editAdminDescriptor.getSalary().orElse(adminToEdit.getSalary());
        EmployDate updatedEmplomentDate =
                editAdminDescriptor.getEmploymenttDate().orElse(adminToEdit.getEmploymentDate());

        return new Admin(
                adminToEdit.getUsername(),
                updatedName,
                adminToEdit.getRole(),
                updatedSalary,
                updatedEmplomentDate);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditAdminCommand)) {
            return false;
        }

        // state check
        EditAdminCommand e = (EditAdminCommand) other;
        return editAdminDescriptor.equals(e.editAdminDescriptor);
    }

    /**
     * Stores the details to edit the admin with. Each non-empty field value
     * will replace the corresponding field value of the admin.
     */
    public static class EditAdminDescriptor {
        private Name name;
        private Salary salary;
        private EmployDate employDate;

        public EditAdminDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditAdminDescriptor(EditAdminDescriptor toCopy) {
            setName(toCopy.name);
            setSalary(toCopy.salary);
            setEmploymentDate(toCopy.employDate);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(name, salary, employDate);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setSalary(Salary salary) {
            this.salary = salary;
        }

        public Optional<Salary> getSalary() {
            return Optional.ofNullable(salary);
        }

        public void setEmploymentDate(EmployDate employDate) {
            this.employDate = employDate;
        }

        public Optional<EmployDate> getEmploymenttDate() {
            return Optional.ofNullable(employDate);
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAdminDescriptor)) {
                return false;
            }

            // state check
            EditAdminDescriptor e = (EditAdminDescriptor) other;

            return getName().equals(e.getName())
                    && getSalary().equals(e.getSalary())
                    && getEmploymenttDate().equals(e.getEmploymenttDate());
        }
    }
}
