package seedu.modsuni.model.user.student;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.modsuni.model.credential.Username;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.UniqueModuleList;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Role;
import seedu.modsuni.model.user.User;

/**
 * Represents a Student User.
 */
public class Student extends User {
    protected EnrollmentDate enrollmentDate;
    protected List<String> major;
    protected List<String> minor;

    protected UniqueModuleList modulesTaken;
    protected UniqueModuleList modulesStaged;

    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     */
    public Student(Username username, Name name, Role role,
                   EnrollmentDate enrollmentDate,
                   List<String> major, List<String> minor) {
        super(username, name, role);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.minor = minor;
        this.modulesTaken = new UniqueModuleList();
        this.modulesStaged = new UniqueModuleList();
    }

    /**
     * Constructor method of User
     *
     * @param username         The username of the user.
     * @param name             The name of the user.
     * @param role             The role of the user.
     * @param modulesTaken     The list of modules taken.
     */
    public Student(Username username, Name name, Role role, EnrollmentDate enrollmentDate,
                   List<String> major, List<String> minor, UniqueModuleList modulesTaken,
                   UniqueModuleList modulesStaged) {
        super(username, name, role);
        this.enrollmentDate = enrollmentDate;
        this.major = major;
        this.minor = minor;
        this.modulesTaken = modulesTaken;
        this.modulesStaged = modulesStaged;
    }

    /**
     * Returns true if both student's profile contains the module and false otherwise.
     */
    public boolean hasModulesTaken(Module module) {
        return modulesTaken.contains(module);
    }

    /**
     * Removes a module inside the module list the student has already taken.
     *
     * @param module
     */
    public void removeModulesTaken(Module module) {
        modulesTaken.remove(module);
    }

    /**
     * Adds a module inside the module list the student has already taken.
     *
     * @param module
     */
    public void addModulesTaken(Module module) {
        modulesTaken.add(module);
    }

    /**
     * Returns true if both student's profile contains the module and false otherwise.
     */
    public boolean hasModulesStaged(Module module) {
        return modulesStaged.contains(module);
    }

    public void removeModulesStaged(Module module) {
        modulesStaged.remove(module);
    }

    public void addModulesStaged(Module module) {
        modulesStaged.add(module);
    }

    public UniqueModuleList getModulesStaged() {
        return modulesStaged;
    }

    public UniqueModuleList getModulesTaken() {
        return modulesTaken;
    }

    public EnrollmentDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public List<String> getMajor() {
        return major;
    }

    public List<String> getMinor() {
        return minor;
    }

    /**
     * Returns true if the student has added modules to take and false if otherwise.
     */
    public boolean hasModuleToTake() {
        return modulesStaged.hasModules();
    }

    /**
     * Returns a list of code containing modules to be taken and staged.
     */
    public List<Code> getTakenAndStageCode() {
        List<Code> codeChecklist = new ArrayList<>();
        codeChecklist.addAll(modulesTaken.getAllCode());
        codeChecklist.addAll(modulesStaged.getAllCode());
        return codeChecklist;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Student)) {
            return false;
        }

        Student otherStudent = (Student) other;
        return otherStudent.getUsername().equals(getUsername());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(username);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Student Username: ")
            .append(getUsername().getUsername());
        return builder.toString();
    }

    /**
     * Returns a String used to display an Student in the user interface.
     */
    public String toDisplayUi() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Username: ")
            .append(getUsername().toString())
            .append("\nName: ")
            .append(getName().toString())
            .append("\nRole: ")
            .append(getRole().toString())
            .append("\nEnrollment date: ")
            .append(enrollmentDate.toString())
            .append("\nMajor: ")
            .append(major.toString())
            .append("\nMinor: ")
            .append(minor.toString())
            .append("\nModules taken: ")
            .append(modulesTaken.toString())
            .append("\nModules staged: ")
            .append(modulesStaged.toString());
        return builder.toString();
    }
}
