package seedu.modsuni.model.user;

import java.util.Objects;

import seedu.modsuni.model.credential.Username;

/**
 * Contains all the data of a Admin user.
 */
public class Admin extends User {
    private Salary salary;
    private EmployDate employmentDate;

    /**
     * Constructor method for Admin class.
     *
     * @param salary The salary the admin receives monthly.
     * @param employmentDate The date the Admin was employed.
     */
    public Admin(Username username, Name name, Role role, Salary salary, EmployDate employmentDate) {
        super(username, name, role, new PathToProfilePic("dummy.img"));
        this.salary = salary;
        this.employmentDate = employmentDate;
    }

    public Salary getSalary() {

        return salary;
    }

    public EmployDate getEmploymentDate() {

        return employmentDate;
    }


    /**
     * Returns true if both admins of the same name have at least one other identity field that is the same.
     * This defines a weaker notion of equality between two admins.
     */
    public boolean isSameAdmin(Admin otherAdmin) {
        if (otherAdmin == this) {
            return true;
        }

        return otherAdmin != null
                && otherAdmin.getName().equals(getName())
                && otherAdmin.getEmploymentDate().equals(getEmploymentDate())
                && otherAdmin.getPathToProfilePic().equals(getPathToProfilePic())
                && otherAdmin.getSalary() == getSalary();
    }

    /**
     * Returns true if both admins have the same identity and data fields.
     * This defines a stronger notion of equality between two admins.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Admin)) {
            return false;
        }

        Admin otherPerson = (Admin) other;
        return otherPerson.getName().equals(getName())
                && otherPerson.getSalary().equals(getSalary())
                && otherPerson.getEmploymentDate().equals(getEmploymentDate())
                && otherPerson.getRole().equals(getRole());
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(name, role, pathToProfilePic, salary, employmentDate);
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name: ")
                .append(name)
                .append(" Salary: ")
                .append(getSalary())
                .append(" employmentDate: ")
                .append(getEmploymentDate());
        return builder.toString();
    }

    /**
     * Returns a String used to display an Admin in the user interface.
     */
    public String toDisplayUi() {
        final StringBuilder builder = new StringBuilder();
        builder.append("Username: ")
                .append(getUsername().toString())
                .append("\nName: ")
                .append(getName().toString())
                .append("\nRole: ")
                .append(getRole().toString())
                .append("\nPath to profile picture: ")
                .append(getPathToProfilePic().toString())
                .append("\nSalary: ")
                .append(getSalary())
                .append("\nEmployment date: ")
                .append(getEmploymentDate());
        return builder.toString();
    }

}
