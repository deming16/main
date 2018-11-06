package seedu.modsuni.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.modsuni.testutil.TypicalModules.ACC1002;
import static seedu.modsuni.testutil.TypicalModules.CS1010;
import static seedu.modsuni.testutil.TypicalModules.getTypicalModuleList;
import static seedu.modsuni.testutil.TypicalPersons.getTypicalAddressBook;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;

import seedu.modsuni.logic.CommandHistory;
import seedu.modsuni.logic.Generate;
import seedu.modsuni.logic.commands.exceptions.CommandException;
import seedu.modsuni.model.Model;
import seedu.modsuni.model.ModelManager;
import seedu.modsuni.model.ReadOnlyAddressBook;
import seedu.modsuni.model.ReadOnlyModuleList;
import seedu.modsuni.model.UserPrefs;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.Prereq;
import seedu.modsuni.model.module.PrereqDetails;
import seedu.modsuni.model.person.Person;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.testutil.AdminBuilder;
import seedu.modsuni.testutil.ModuleBuilder;
import seedu.modsuni.testutil.StudentBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for GenerateCommand.
 */
public class GenerateCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;
    private Model expectedModel;
    private CommandHistory commandHistory = new CommandHistory();

    @Before
    public void setUp() {
        model = new ModelManager(
            getTypicalModuleList(),
            getTypicalAddressBook(),
            new UserPrefs(),
            new CredentialStore());
        expectedModel = new ModelManager(
            model.getModuleList(),
            model.getAddressBook(),
            new UserPrefs(),
            new CredentialStore());
    }

    @Test
    public void execute_roleNotStudent_throwsCommandException() throws Exception {
        User admin = new AdminBuilder().build();
        GenerateCommand generateCommand = new GenerateCommand();
        ModelStub modelStub = new ModelStubWithUser(admin);

        thrown.expect(CommandException.class);
        thrown.expectMessage(GenerateCommand.MESSAGE_INVALID_ROLE);
        generateCommand.execute(modelStub, commandHistory);

        /*
        Person validPerson = new PersonBuilder().build();
        AddCommand addCommand = new AddCommand(validPerson);
        ModelStub modelStub = new ModelStubWithPerson(validPerson);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddCommand.MESSAGE_DUPLICATE_PERSON);
        addCommand.execute(modelStub, commandHistory);*/
    }

    @Test
    public void execute_roleNullUser_throwsCommandException() throws Exception {
        GenerateCommand generateCommand = new GenerateCommand();
        ModelStub modelStub = new ModelStubWithNullUser();

        thrown.expect(CommandException.class);
        thrown.expectMessage(GenerateCommand.MESSAGE_ERROR);
        generateCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_studentNoModules_throwsCommandException() throws Exception {
        User student = new StudentBuilder()
                .withUsername(StudentBuilder.DEFAULT_USERNAME)
                .withName(StudentBuilder.DEFAULT_NAME)
                .withProfilePicFilePath(StudentBuilder.DEFAULT_PROFILE_PIC_FILEPATH)
                .withEnrollmentDate(StudentBuilder.DEFAULT_ENROLLMENT_DATE)
                .withMajor(StudentBuilder.DEFAULT_MAJOR)
                .withMinor(StudentBuilder.DEFAULT_MINOR)
                .build();
        GenerateCommand generateCommand = new GenerateCommand();
        ModelStub modelStub = new ModelStubWithUser(student);

        thrown.expect(CommandException.class);
        thrown.expectMessage(GenerateCommand.MESSAGE_NO_MODULES);
        generateCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void execute_generateSuccessful() throws Exception {
        Student student = new StudentBuilder().build();
        student.addModulesStaged(CS1010);
        GenerateCommand generateCommand = new GenerateCommand();
        ModelStubWithUser modelStub = new ModelStubWithUser(student);
        CommandResult commandResult = generateCommand.execute(modelStub, commandHistory);
        assertEquals(commandResult.feedbackToUser, GenerateCommand.MESSAGE_SUCCESS);
        /*
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        */
    }

    @Test
    public void execute_generateUnsuccessful() throws Exception {
        List<Code> codes = new ArrayList<>();
        codes.add(ACC1002.getCode());

        PrereqDetails prereqDetailsCode = new PrereqDetails();
        prereqDetailsCode.setCode(Optional.of(CS1010.getCode()));

        List<PrereqDetails> prereqDetailsList = new ArrayList<>();
        prereqDetailsList.add(prereqDetailsCode);

        Prereq prereq = new Prereq();
        prereq.setOr(Optional.of(prereqDetailsList));

        Module module = new ModuleBuilder(ACC1002).withPrereq(prereq).build();
        Student user = new StudentBuilder().build();
        user.addModulesStaged(module);
        GenerateCommand generateCommand = new GenerateCommand();
        ModelStubWithUser modelStub = new ModelStubWithUser(user);
        CommandResult commandResult = generateCommand.execute(modelStub, commandHistory);
        assertEquals(commandResult.feedbackToUser, GenerateCommand.MESSAGE_FAILURE + "\n" + codes);
        /*
        ModelStubAcceptingPersonAdded modelStub = new ModelStubAcceptingPersonAdded();
        Person validPerson = new PersonBuilder().build();

        CommandResult commandResult = new AddCommand(validPerson).execute(modelStub, commandHistory);

        assertEquals(String.format(AddCommand.MESSAGE_SUCCESS, validPerson), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validPerson), modelStub.personsAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
        */
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleTaken(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleStaged(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyModuleList getModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getObservableModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredStagedModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredTakenModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Module> getFilteredDatabaseModuleList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredDatabaseModuleList(Predicate<Module> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Credential getCredential(Username username) {
            throw new AssertionError("THis method should not be called.");
        }

        @Override
        public ObservableList<Username> getUsernames() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addAdmin(Admin admin, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addModuleToDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeModuleFromDatabase(Module module) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasModuleInDatabase(Module module) {
            return false;
        }

        @Override
        public void updateModule(Module target, Module editedModule) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isVerifiedCredential(Credential credential) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Password getCredentialPassword(User user) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isAdmin() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isStudent() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyCredentialStore getCredentialStore() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public User getCurrentUser() {
            throw new AssertionError("This method should not be called.");

        }

        @Override
        public void resetCurrentUser() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void setCurrentUser(User user) {
            throw new AssertionError("This method should not be called.");
        }


        @Override
        public Optional<Module> searchCodeInDatabase(Code code) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveUserFile(User user, Path savePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<User> readUserFile(Path filePath, String password) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<List<Code>> canGenerate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public SemesterList generateSchedule() {
            throw new AssertionError("This method should not be called.");
        }
    }
    /**
     * A Model stub with a user.
     */
    private class ModelStubWithUser extends ModelStub {
        private final User currentUser;

        ModelStubWithUser(User user) {
            requireNonNull(user);
            this.currentUser = user;
        }

        @Override
        public User getCurrentUser() {
            return currentUser;
        }

        @Override
        public boolean isStudent() {
            return getCurrentUser().getRole() == Role.STUDENT;
        }

        @Override
        public Optional<List<Code>> canGenerate() {
            if (isStudent()) {
                return Generate.canGenerate((Student) getCurrentUser());
            } else {
                return Optional.empty();
            }
        }

        @Override
        public SemesterList generateSchedule() {
            Generate generate = new Generate((Student) getCurrentUser());
            return generate.getSchedule();
        }
    }

    private class ModelStubWithNullUser extends ModelStub {

        @Override
        public User getCurrentUser() {
            return null;
        }
    }
}
