package seedu.modsuni.testutil;

import seedu.modsuni.logic.commands.EditAdminCommand.EditAdminDescriptor;
import seedu.modsuni.model.user.Admin;
import seedu.modsuni.model.user.EmployDate;
import seedu.modsuni.model.user.Name;
import seedu.modsuni.model.user.Salary;

/**
 * A utility class to help with building EditAdminDescriptor objects.
 */
public class EditAdminDescriptorBuilder {

    private EditAdminDescriptor descriptor;

    public EditAdminDescriptorBuilder() {
        descriptor = new EditAdminDescriptor();
    }


    public EditAdminDescriptorBuilder(EditAdminDescriptor descriptor) {
        this.descriptor = new EditAdminDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditAdminDescriptor} with fields containing {@code
     * admin}'s details
     */
    public EditAdminDescriptorBuilder(Admin admin) {
        descriptor = new EditAdminDescriptor();
        descriptor.setName(admin.getName());
        descriptor.setSalary(admin.getSalary());
        descriptor.setEmploymentDate(admin.getEmploymentDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditAdminDescriptor} that we are building.
     */
    public EditAdminDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Salary} of the {@code EditAdminDescriptor}
     * that we are building.
     */
    public EditAdminDescriptorBuilder withSalary(String salary) {
        descriptor.setSalary(new Salary(salary));
        return this;
    }

    /**
     * Sets the {@code employmentDate} of the {@code EditAdminDescriptor} that
     * we are building.
     */
    public EditAdminDescriptorBuilder withEnrollmentDate(String employmentDate) {
        descriptor.setEmploymentDate(new EmployDate(employmentDate));
        return this;
    }

    public EditAdminDescriptor build() {
        return descriptor;
    }
}
