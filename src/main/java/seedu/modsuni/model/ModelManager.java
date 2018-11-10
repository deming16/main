package seedu.modsuni.model;

import static java.util.Objects.requireNonNull;
import static seedu.modsuni.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javax.crypto.NoSuchPaddingException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import seedu.modsuni.commons.core.ComponentManager;
import seedu.modsuni.commons.core.LogsCenter;
import seedu.modsuni.commons.core.index.Index;
import seedu.modsuni.commons.events.model.CredentialStoreChangedEvent;
import seedu.modsuni.commons.events.model.ModuleListChangedEvent;
import seedu.modsuni.commons.events.model.SaveUserChangedEvent;
import seedu.modsuni.commons.exceptions.CorruptedFileException;
import seedu.modsuni.commons.exceptions.DataConversionException;
import seedu.modsuni.commons.exceptions.InvalidPasswordException;
import seedu.modsuni.logic.Generate;
import seedu.modsuni.model.credential.Credential;
import seedu.modsuni.model.credential.CredentialStore;
import seedu.modsuni.model.credential.Password;
import seedu.modsuni.model.credential.ReadOnlyCredentialStore;
import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.semester.SemesterList;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;
import seedu.modsuni.model.user.exceptions.NotStudentUserException;
import seedu.modsuni.model.user.student.Student;
import seedu.modsuni.storage.UserStorage;
import seedu.modsuni.storage.XmlUserStorage;

/**
 * Represents the in-memory model of the modsuni book data.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);
    private final ReadOnlyModuleList databaseModuleList;
    private final ReadOnlyModuleList stagedModuleList;
    private final ReadOnlyModuleList takenModuleList;
    private final CredentialStore credentialStore;
    private FilteredList<Module> filteredDatabaseModules;
    private FilteredList<Module> filteredStagedModules;
    private FilteredList<Module> filteredTakenModules;
    private User currentUser = null;

    /**
     * Initializes a ModelManager with the given databaseModuleList, addressBook, userPrefs, credentialStore and
     * configStore.
     */
    public ModelManager(ReadOnlyModuleList databaseModuleList,
                        UserPrefs userPrefs,
                        ReadOnlyCredentialStore credentialStore) {

        requireAllNonNull(databaseModuleList, userPrefs,
            credentialStore);

        logger.fine("Initializing with modulelist: " + databaseModuleList
            + " and user prefs " + userPrefs
            + " and credential store " + credentialStore);


        this.databaseModuleList = databaseModuleList;
        this.stagedModuleList = new ModuleList();
        this.takenModuleList = new ModuleList();
        this.credentialStore = (CredentialStore) credentialStore;
        this.currentUser = null;
        this.filteredDatabaseModules =
            new FilteredList<>(databaseModuleList.getModuleList());
        this.filteredStagedModules = new FilteredList<>(stagedModuleList.getModuleList());
        this.filteredTakenModules = new FilteredList<>(takenModuleList.getModuleList());
    }

    public ModelManager() {
        this(new ModuleList(), new UserPrefs(),
            new CredentialStore());
    }

    @Override
    public ReadOnlyModuleList getModuleList() {
        return databaseModuleList;
    }

    //=========== Search Module Management =============================================================
    @Override
    public Optional<Module> searchCodeInDatabase(Code code) {
        requireNonNull(code);
        ModuleList moduleList = (ModuleList) getModuleList();
        return moduleList.searchCode(code);
    }

    @Override
    public Index searchForIndexInDatabase(Module module) {
        requireNonNull(module);
        ModuleList moduleList = (ModuleList) getModuleList();
        return moduleList.searchForIndex(module);
    }

    //=========== Student Account Management =============================================================

    @Override
    public boolean hasModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        return student.hasModulesTaken(module);
    }

    @Override
    public void removeModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.removeModulesTaken(module); (
                (ModuleList) takenModuleList).removeModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModuleTaken(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.addModulesTaken(module); (
                (ModuleList) takenModuleList).addModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public boolean hasModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        return student.hasModulesStaged(module);
    }

    @Override
    public void removeModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.removeModulesStaged(module); (
                (ModuleList) stagedModuleList).removeModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    @Override
    public void addModuleStaged(Module module) throws NotStudentUserException {
        requireNonNull(module);
        if (!isStudent()) {
            throw new NotStudentUserException();
        }

        Student student = (Student) getCurrentUser();
        student.addModulesStaged(module); (
                (ModuleList) stagedModuleList).addModule(module);

        updateFilteredDatabaseModuleList(PREDICATE_SHOW_ALL_MODULES);
    }

    //=========== Student Account Management =============================================================

    @Override
    public boolean isStudent() {
        if (currentUser == null) {
            return false;
        }
        return currentUser.getRole() == Role.STUDENT && currentUser instanceof Student;
    }

    //=========== Admin Account Management =============================================================

    @Override
    public void addAdmin(Admin admin, Path savePath) {
        requireNonNull(admin);
        saveUserFile(admin, savePath);

    }

    /**
     * Raise an event indicating that credential store has change
     */
    private void indicateModuleListChanged() {
        raise(new ModuleListChangedEvent(databaseModuleList));
    }

    @Override
    public void addModuleToDatabase(Module module) {
        requireNonNull(module); (
                (ModuleList) databaseModuleList).addModule(module);
        indicateModuleListChanged();
    }

    @Override
    public void removeModuleFromDatabase(Module module) {
        requireNonNull(module); (
                (ModuleList) databaseModuleList).removeModule(module);
        indicateModuleListChanged();
    }

    @Override
    public boolean hasModuleInDatabase(Module module) {
        requireNonNull(module);
        return ((ModuleList) databaseModuleList).hasModule(module);
    }

    @Override
    public void updateModule(Module target, Module editedModule) {
        requireAllNonNull(target, editedModule); (
                (ModuleList) databaseModuleList).updateModule(target, editedModule);
    }

    @Override
    public ObservableList<Module> getObservableModuleList() {
        ModuleList modList = (ModuleList) this.getModuleList();
        return modList.getModuleList();
    }

    @Override
    public boolean isAdmin() {
        return currentUser.getRole() == Role.ADMIN;
    }

    //=========== Filtered Module List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Module} backed by the internal list of
     */
    @Override
    public ObservableList<Module> getFilteredDatabaseModuleList() {
        return FXCollections.unmodifiableObservableList(filteredDatabaseModules);
    }

    @Override
    public ObservableList<Module> getFilteredStagedModuleList() {
        return FXCollections.unmodifiableObservableList(filteredStagedModules);
    }

    @Override
    public ObservableList<Module> getFilteredTakenModuleList() {
        return FXCollections.unmodifiableObservableList(filteredTakenModules);
    }

    @Override
    public void updateFilteredDatabaseModuleList(Predicate<Module> predicate) {
        requireNonNull(predicate);
        filteredDatabaseModules.setPredicate(predicate);
    }

    //=========== Save current User to file ====================================
    @Override
    public void saveUserFile(User user, Path savePath) {
        raise(new SaveUserChangedEvent(user, getCredentialPassword(user), savePath));
        logger.info("Event raised to Storage component");
    }

    @Override
    public Optional<User> readUserFile(Path filePath, String password)
            throws IOException, DataConversionException, NoSuchAlgorithmException, InvalidKeyException,
            InvalidPasswordException, CorruptedFileException, NoSuchPaddingException {
        logger.fine("Attempting to read data from file: " + filePath);
        UserStorage userStorage = new XmlUserStorage(filePath);
        return userStorage.readUser(filePath, password);
    }

    //============ Credential Store Methods ====================================

    /**
     * Raise an event indicating that credential store has change
     */
    private void indicateCredentialStoreChanged() {
        raise(new CredentialStoreChangedEvent(credentialStore));
    }

    @Override
    public void addCredential(Credential credential) {
        credentialStore.addCredential(credential);
        indicateCredentialStoreChanged();
    }

    @Override
    public void removeCredential(Credential credential) {
        credentialStore.removeCredential(credential);
        indicateCredentialStoreChanged();
    }

    @Override
    public Credential getCredential(Username username) {
        return credentialStore.getCredential(username);
    }

    @Override
    public boolean hasCredential(Credential credential) {
        return credentialStore.hasCredential(credential);
    }

    @Override
    public ReadOnlyCredentialStore getCredentialStore() {
        return credentialStore;
    }

    @Override
    public boolean isVerifiedCredential(Credential toVerify) {
        return credentialStore.isVerifiedCredential(toVerify);
    }

    @Override
    public Password getCredentialPassword(User user) {
        return credentialStore.getCredentialPassword(user.getUsername());
    }

    @Override
    public ObservableList<Username> getUsernames() {
        return FXCollections.unmodifiableObservableList(credentialStore.getUsernames());
    }

    //============= User Account Management Methods ============================

    @Override
    public void setCurrentUser(User user) {
        requireNonNull(user);
        currentUser = user;
        if (isStudent()) {
            Student student = (Student) getCurrentUser(); (
            (ModuleList) stagedModuleList).setModules(student.getModulesStaged().asUnmodifiableObservableList()); (
                    (ModuleList) takenModuleList).setModules(student.getModulesTaken().asUnmodifiableObservableList());
        }
    }

    @Override
    public User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void resetCurrentUser() {
        currentUser = null; (
        (ModuleList) stagedModuleList).resetData(new ModuleList()); (
                (ModuleList) takenModuleList).resetData(new ModuleList());
    }

    //============= Generate Methods ============================

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
        return generate.generateSchedule();
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
        return credentialStore.equals(other.credentialStore);
    }
}
