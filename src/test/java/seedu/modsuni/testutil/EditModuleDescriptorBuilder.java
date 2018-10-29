package seedu.modsuni.testutil;

import seedu.modsuni.logic.commands.EditModuleCommand.EditModuleDescriptor;
import seedu.modsuni.model.module.Code;
import seedu.modsuni.model.module.Module;
import seedu.modsuni.model.module.Prereq;

/**
 * A utility class to help with building EditModuleDescriptor objects.
 */
public class EditModuleDescriptorBuilder {

    private EditModuleDescriptor descriptor;

    public EditModuleDescriptorBuilder() {
        descriptor = new EditModuleDescriptor();
    }

    public EditModuleDescriptorBuilder(EditModuleDescriptor descriptor) {
        this.descriptor = new EditModuleDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditModuleDescriptor} with fields containing {@code module}'s details
     */
    public EditModuleDescriptorBuilder(Module module) {
        descriptor = new EditModuleDescriptor();
        descriptor.setCode(module.getCode());
        descriptor.setDeparment(module.getDepartment());
        descriptor.setDescription(module.getDescription());
        descriptor.setCredit(module.getCredit());
        descriptor.setTitle(module.getTitle());
        descriptor.setPrereq(module.getPrereq());
        descriptor.setSems(module.getSems());

    }

    /**
     * Sets the {@code Code} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withCode(String code) {
        descriptor.setCode(new Code(code));
        return this;
    }

    /**
     * Sets the {@code department} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withDepartment(String department) {
        descriptor.setDeparment(department);
        return this;
    }

    /**
     * Sets the {@code description} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withDescription(String description) {
        descriptor.setDescription(description);
        return this;
    }

    /**
     * Sets the {@code title} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withTitle(String title) {
        descriptor.setTitle(title);
        return this;
    }

    /**
     * Sets the {@code credit} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withCredit(int credit) {
        descriptor.setCredit(credit);
        return this;
    }

    /**
     * Sets the {@code sems} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withSems(boolean[] sems) {
        descriptor.setSems(sems);
        return this;
    }

    /**
     * Sets the {@code Prereq} of the {@code EditModuleDescriptor} that we are building.
     */
    public EditModuleDescriptorBuilder withPrereq(Prereq prereq) {
        descriptor.setPrereq(prereq);
        return this;
    }


    public EditModuleDescriptor build() {
        return descriptor;
    }
}
