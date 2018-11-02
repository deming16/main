package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_AVAILABLE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CODE;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_CREDIT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DEPARTMENT;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_DESCRIPTION;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_PREREQ;
import static seedu.modsuni.logic.parser.CliSyntax.PREFIX_MODULE_TITLE;

import java.util.List;
import java.util.Optional;

import seedu.modsuni.commons.core.Messages;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.commons.util.CollectionUtil;
import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.user.Role;

/**
 * Command to allow students to edit their profiles.
 */
public class EditModuleCommand extends Command {

    public static final String COMMAND_WORD = "editModule";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edit current"
            + " admin account with the given parameters.\n"
            + "Parameters:\n"
            + "[" + PREFIX_MODULE_CODE + "CODE] "
            + "[" + PREFIX_MODULE_DEPARTMENT + "DEPARTMENT] "
            + "[" + PREFIX_MODULE_DESCRIPTION + "DESCRIPTION] "
            + "[" + PREFIX_MODULE_TITLE + "TITLE] "
            + "[" + PREFIX_MODULE_CREDIT + "CREDIT] "
            + "[" + PREFIX_MODULE_AVAILABLE + "AVAILABLE_STRING] "
            + "[" + PREFIX_MODULE_PREREQ + "PREREQ_STRING]\n"
            + "Refer to the user guide for information on the example usages.\n";

    public static final String MESSAGE_EDIT_MODULE_SUCCESS = "Edit Successfully!%1$s";

    public static final String MESSAGE_NOT_EDITED = "At least one field to "
            + "edit must be provided.";

    public static final String MESSAGE_NOT_ADMIN = "Only an admin user can execute this command";
    public static final String MESSAGE_DUPLICATE_MODULE = "This module already exist in the database.";
    public static final String MESSAGE_NOT_LOGGED_IN = "Unable to edit, please log in first.";

    private final EditModuleDescriptor editModuleDescriptor;
    private final Index index;

    public EditModuleCommand(Index index, EditModuleDescriptor editModuleDescriptor) {
        requireAllNonNull(index, editModuleDescriptor);
        this.editModuleDescriptor = editModuleDescriptor;
        this.index = index;
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
        List<Module> lastShownList = model.getFilteredDatabaseModuleList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }
        Module moduleToEdit = lastShownList.get(index.getZeroBased());
        Module editedModule =
                createEditedModule(moduleToEdit, editModuleDescriptor);
        if (!moduleToEdit.isSameModule(editedModule) && model.hasModuleInDatabase(editedModule)) {
            throw new CommandException(MESSAGE_DUPLICATE_MODULE);
        }
        model.updateModule(moduleToEdit, editedModule);
        model.updateFilteredDatabaseModuleList(Model.PREDICATE_SHOW_ALL_MODULES);
        return new CommandResult(String.format(MESSAGE_EDIT_MODULE_SUCCESS,
                editedModule.toString()));
    }

    /**
     * Creates and returns a {@code Module} with the details of {@code
     * moduleToEdit }
     * edited with {@code editModuleDescriptor}.
     */
    private static Module createEditedModule(Module moduleToEdit,
                                             EditModuleDescriptor editModuleDescriptor) {
        assert moduleToEdit != null;

        Code updatedCode =
                editModuleDescriptor.getCode().orElse(moduleToEdit.getCode());

        String updatedDepartment =
                editModuleDescriptor.getDepartment().orElse(moduleToEdit.getDepartment());

        String updatedDescription =
                editModuleDescriptor.getDescription().orElse(moduleToEdit.getDescription());

        String updatedTitle =
                editModuleDescriptor.getTitle().orElse(moduleToEdit.getTitle());

        int updatedCredit =
                editModuleDescriptor.getCredit().orElse(moduleToEdit.getCredit());

        boolean[] updatedSems =
                editModuleDescriptor.getSems().orElse(moduleToEdit.getSems());

        Prereq updatedPrereq =
                editModuleDescriptor.getPrereq().orElse(moduleToEdit.getPrereq());

        return new Module(updatedCode, updatedDepartment, updatedTitle, updatedDescription, updatedCredit,
                updatedSems[0], updatedSems[1], updatedSems[2], updatedSems[3], moduleToEdit.getLockedModules(),
                updatedPrereq);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditModuleCommand)) {
            return false;
        }

        // state check
        EditModuleCommand e = (EditModuleCommand) other;
        return index.equals(e.index)
                && editModuleDescriptor.equals(e.editModuleDescriptor);
    }

    /**
     * Stores the details to edit the module with. Each non-empty field value
     * will replace the corresponding field value of the module.
     */
    public static class EditModuleDescriptor {
        private Code code;
        private String deparment;
        private String title;
        private String description;
        private Integer credit;
        private boolean[] sems;
        private Prereq prereq;

        public EditModuleDescriptor() {
        }

        /**
         * Copy constructor.
         * A defensive copy of {@code tags} is used internally.
         */
        public EditModuleDescriptor(EditModuleDescriptor toCopy) {
            setCode(toCopy.code);
            setDeparment(toCopy.deparment);
            setTitle(toCopy.title);
            setDescription(toCopy.description);
            setCredit(toCopy.credit);
            setSems(toCopy.sems);
            setPrereq(toCopy.prereq);
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(code, deparment, title,
                    description, credit, sems, prereq);
        }

        public void setCode(Code code) {
            this.code = code;
        }

        public Optional<Code> getCode() {
            return Optional.ofNullable(code);
        }

        public void setDeparment(String deparment) {
            this.deparment = deparment;
        }

        public Optional<String> getDepartment() {
            return Optional.ofNullable(deparment);
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public Optional<String> getTitle() {
            return Optional.ofNullable(title);
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Optional<String> getDescription() {
            return Optional.ofNullable(description);
        }

        public void setCredit(int credit) {
            this.credit = credit;
        }

        public Optional<Integer> getCredit() {
            return Optional.ofNullable(credit);
        }

        public void setSems(boolean[] sems) {
            this.sems = sems;
        }

        public Optional<boolean[]> getSems() {
            return Optional.ofNullable(sems);
        }

        public void setPrereq(Prereq prereq) {
            this.prereq = prereq;
        }

        public Optional<Prereq> getPrereq() {
            return Optional.ofNullable(prereq);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditModuleDescriptor)) {
                return false;
            }

            // state check
            EditModuleDescriptor e = (EditModuleDescriptor) other;

            return getCode().equals(e.getCode())
                    && getDepartment().equals(e.getDepartment())
                    && getDescription().equals(e.getDescription())
                    && getTitle().equals(e.getTitle())
                    && getCredit().equals(e.getCredit())
                    && getSems().equals(e.getSems())
                    && getPrereq().equals(e.getPrereq());
        }
    }
}
