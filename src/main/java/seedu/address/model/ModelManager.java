package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.module.Module;
import seedu.address.model.person.Person;
import seedu.address.model.user.Student;
import seedu.address.model.user.User;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static User currentUser = null;
    private final ReadOnlyModuleList moduleList;
    private final VersionedAddressBook versionedAddressBook;
    private final FilteredList<Person> filteredModule;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyModuleList moduleList, ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(moduleList, addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.moduleList = moduleList;
        versionedAddressBook = new VersionedAddressBook(addressBook);
        filteredModule = new FilteredList<>(versionedAddressBook.getPersonList());
    }

    public ModelManager() {
        this(new ModuleList(), new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        versionedAddressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return versionedAddressBook;
    }

    @Override
    public ReadOnlyModuleList getModuleList() {
        return moduleList;
    }

    @Override
    public Optional<Module> searchModuleInModuleList(Module module) {
        ModuleList moduleList = (ModuleList) getModuleList();
        return moduleList.getModuleInformation(module);
    }

    @Override
    public List<Module> searchKeyWordInModuleList(Module keyword) {
        ModuleList moduleList = (ModuleList) getModuleList();
        return moduleList.searchKeyword(keyword);
    }
    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(versionedAddressBook));
    }

    @Override
    public boolean hasPerson(Person person) {
        requireNonNull(person);
        return versionedAddressBook.hasPerson(person);
    }

    @Override
    public void deletePerson(Person target) {
        versionedAddressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public void addPerson(Person person) {
        versionedAddressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public boolean hasModule(Module module) {
        requireNonNull(module);
        Student student = (Student) getCurrentUser();
        return student.hasModule(module);
    }

    @Override
    public void removeModule(Module module) {
        requireNonNull(module);
        Student student = (Student) getCurrentUser();
        student.removeModule(module);
    }

    @Override
    public void addModule(Module module) {
        requireNonNull(module);
        Student student = (Student) getCurrentUser();
        student.addModule(module);
    }

    @Override
    public void updatePerson(Person target, Person editedPerson) {
        requireAllNonNull(target, editedPerson);

        versionedAddressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Person} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Person> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredModule);
    }

    @Override
    public void updateFilteredPersonList(Predicate<Person> predicate) {
        requireNonNull(predicate);
        filteredModule.setPredicate(predicate);
    }

    //=========== Undo/Redo =================================================================================

    @Override
    public boolean canUndoAddressBook() {
        return versionedAddressBook.canUndo();
    }

    @Override
    public boolean canRedoAddressBook() {
        return versionedAddressBook.canRedo();
    }

    @Override
    public void undoAddressBook() {
        versionedAddressBook.undo();
        indicateAddressBookChanged();
    }

    @Override
    public void redoAddressBook() {
        versionedAddressBook.redo();
        indicateAddressBookChanged();
    }

    @Override
    public void commitAddressBook() {
        versionedAddressBook.commit();
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return versionedAddressBook.equals(other.versionedAddressBook)
                && filteredModule.equals(other.filteredModule);
    }

    //============= User Account Management Methods ============================
    @Override
    public void setCurrentUser(User user) {
        requireNonNull(user);
        currentUser = user;
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
